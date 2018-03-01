#!/usr/bin/env python

import z3

class ProblemZ3(object):
    def __init__(self, graph):
        self.graph = graph
        _, self.variable_constraints = self.generate_model()

    def generate_model(self):
        variables = []
        constraints = []
        def gen_var(lower, upper, name):
            var = z3.Int(name)
            constraints.append(z3.Or(var == lower, var == upper))
            return var

        for node in self.graph.nodes():
            variables.append(node.generate_variable(gen_var))

        for node in self.graph.nodes():
            constraints.append(self.generate_constraint(node))
        thick_cons = 0
        for (u, v) in self.graph.edges():
            if v in self.graph.thick_edge[u]:
                thick_constraint = u.variable + v.variable >= 1
                constraints.append(thick_constraint)
                thick_cons += 1
        print("Num thick constraints added:", thick_cons)

        return variables, constraints

    def generate_constraint(self, node):
        assert all(v.variable is not None for v in list(self.graph.neighbors(node)) + [node])
        
        if node.status in (NodeStatus.ExternallyDominated, NodeStatus.Dominating):
            node.constraint = node.variable == node.variable # i.e. TrueConstraint
        else:
            summation = sum(n.variable for n in self.graph.neighbors(node)) + node.variable
            node.constraint = summation >= 1

        return node.constraint

    @property
    def variables(self):
        return [v.variable for v in self.graph.nodes()]

    @property
    def constraints(self):
        return [v.constraint for v in self.graph.nodes()]

    def solve(self):
        return self.solve_binary_search()

    def solve_binary_search(self):
        solver = z3.Solver()
        for c in chain(self.constraints, self.variable_constraints):
            solver.add(c)

        min_ = 0
        max_ = len(self.variables) + 1
        while min_ < max_: # calculate a fixpoint
            solver.push()
            mid = (max_ + min_) // 2
            solver.add(sum(self.variables) < mid)
            if solver.check().r == z3.Z3_L_FALSE:
                min_ = mid + 1
                solver.pop()
                print("min_k bin >", mid, min_, max_)
                continue
            if solver.check().r != z3.Z3_L_TRUE:
                raise Exception("unknown result from z3")
            print("min_k bin <=", mid, min_, max_)
            min_k = solver.model().evaluate(sum(self.variables)).as_long()
            solution = solver.model()
            solver.pop()
            max_ = mid - 1

        for vert in self.graph.nodes():
            vert.solution_value = solution.evaluate(vert.variable).as_long()
            if vert.solution_value > 0:
                vert.status = NodeStatus.Dominating

        return min_k

    def solve_fixpoint(self):
        solver = z3.Solver()
        for c in chain(self.constraints, self.variable_constraints):
            solver.add(c)

        min_k = len(self.variables) + 1
        while True: # calculate a fixpoint
            solver.push()
            solver.add(sum(self.variables) < min_k)
            print("min_k fix", min_k)
            if solver.check().r == z3.Z3_L_FALSE:
                break
            if solver.check().r != z3.Z3_L_TRUE:
                raise Exception("unknown result from z3")
            min_k = solver.model().evaluate(sum(self.variables)).as_long()
            solution = solver.model()
            solver.pop()

        for vert in self.graph.nodes():
            vert.solution_value = solution.evaluate(vert.variable).as_long()
            if vert.solution_value > 0:
                vert.status = NodeStatus.Dominating

        assert(type(min_k) == int)
        return min_k

if __name__ == '__main__':
    ProblemZ3(...).solve_fixpoint()



