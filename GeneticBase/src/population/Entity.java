package population;

import util.DoubleCallback;

// immutable
public abstract class Entity implements Comparable<Entity> {

    private double fitness;
    private boolean computedFitness = false;
    private int team;

    public Entity(int team) {
        this.team = team;
    }

    public boolean _hasComputedFitness() {
        return computedFitness;
    }

    public void _setFitness(double fitness) {
        if (computedFitness) {
            throw new IllegalStateException("Already computed fitness.");
        }
        this.fitness = fitness;
        this.computedFitness = true;
    }

    public void _clearFitness() {
        computedFitness = false;
        fitness = 0d;
    }

    public final double _getFitness() {
        if (!computedFitness) {
            throw new IllegalArgumentException("Compute fitness first using a Simulator.");
        }
        return fitness;
    }

    public int getTeam() {
        return team;
    }

    @Override
    public final int compareTo(Entity o) {
        if (!computedFitness || !o.computedFitness) {
            throw new IllegalStateException("Compute fitness first before comparing.");
        }
        // order from high to low fitness
        return -Double.compare(fitness, o.fitness);
    }
}
