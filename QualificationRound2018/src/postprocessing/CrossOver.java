package postprocessing;

import model.Ride;
import output.SelfDrivingSolution;
import postprocessing.genetic.SolutionCrossOver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CrossOver extends SolutionCrossOver<SelfDrivingSolution> {

    private Ride[] allRides;

    public CrossOver(Ride[] allRides) {
        this.allRides = allRides;
    }

    @Override
    protected SelfDrivingSolution crossOver(Random random, SelfDrivingSolution mom, SelfDrivingSolution dad) {
        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(mom.input, mom.grid);

        List<Ride[]> vehicleRides = random.nextBoolean() ? mom.vehicleRides : dad.vehicleRides;
        for (Ride[] rides : vehicleRides) {
            builder.addVehicle(rides);
        }

//        int nrRides = mom.grid.N;
//        int[] ridesAssignedToDriverMom = new int[nrRides];
//        int[] ridesAssignedToDriverDad = new int[nrRides];
//        Ride[] rideList = new Ride[nrRides];
//        for (int i = 0; i < nrRides; i++) {
//            ridesAssignedToDriverMom[i] = -1;
//            ridesAssignedToDriverDad[i] = -1;
//            rideList[i] = null;
//        }
//        for (int i = 0; i < mom.vehicleRides.size(); i++) {
//            for (Ride ride : mom.vehicleRides.get(i)) {
//                ridesAssignedToDriverMom[ride.id] = i;
//                rideList[ride.id] = ride;
//            }
//        }
//        for (int i = 0; i < dad.vehicleRides.size(); i++) {
//            for (Ride ride : dad.vehicleRides.get(i)) {
//                ridesAssignedToDriverDad[ride.id] = i;
//                rideList[ride.id] = ride;
//            }
//        }
//
//        int nrDrivers = mom.grid.F;
//        List<List<Ride>> rides = new ArrayList<>(nrDrivers);
//        for (int i = 0; i < nrDrivers; i++) {
//            rides.add(new ArrayList<>());
//        }
//
//        for (int ride = 0; ride < nrRides; ride++) {
//            int driver = random.nextBoolean() ? ridesAssignedToDriverMom[ride] : ridesAssignedToDriverDad[ride];
//            if (driver != -1) {
//                rides.get(driver).add(rideList[ride]);
//            }
//        }
//
//        for (List<Ride> driverRides : rides) {
//            builder.addVehicle(driverRides);
//        }

        boolean validate = random.nextDouble() < 0.05;
        return builder.build(validate);
    }

    @Override
    protected SelfDrivingSolution generateNewMutatedSolution(Random random, SelfDrivingSolution solutionToMutateFromDO_NOT_MODIFY) {
        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(solutionToMutateFromDO_NOT_MODIFY.input, solutionToMutateFromDO_NOT_MODIFY.grid);

        List<Ride[]> rides = solutionToMutateFromDO_NOT_MODIFY.vehicleRides;

        int nRides = 0;
        List<List<Ride>> newRides = new ArrayList<>();
        for (int i = 0; i < rides.size(); i++) {
            Ride[] rideList = rides.get(i);
            List<Ride> copy = new ArrayList<>(rideList.length);
            for (int j = 0; j < rideList.length; j++) {
                Ride ride = rideList[j];
                copy.add(ride);

                nRides++;
            }
            newRides.add(copy);
        }
        for (int i = 0; i < solutionToMutateFromDO_NOT_MODIFY.grid.F - rides.size(); i++) {
            // add empty vehicle rides
            newRides.add(new ArrayList<>());
        }

        if (nRides < allRides.length) {
            // find out which rides are not handled yet
            // this will only be done at the initial generation, further generations will then always have
            // all rides added.
            boolean[] rideHandled = new boolean[allRides.length];
            for (List<Ride> rideList : newRides) {
                for (Ride ride : rideList) {
                    rideHandled[ride.id] = true;
                }
            }
            // add all unhandled rides randomly to the drivers, such that they will move around during the mutate
            for (int i = 0; i < rideHandled.length; i++) {
                if (rideHandled[i]) continue;

                int d = random.nextInt(newRides.size());
                newRides.get(d).add(allRides[i]);
                rideHandled[i] = true;
            }
        }


        int ridesToMove = random.nextInt(solutionToMutateFromDO_NOT_MODIFY.grid.N / 5000);
        for (int i = 0; i < ridesToMove; i++) {
            int d1 = random.nextInt(newRides.size());
            int d2 = random.nextInt(newRides.size());

            int n1 = newRides.get(d1).size();
            int n2 = newRides.get(d2).size();
            if (n1 > 0) {
                int r1 = random.nextInt(n1); // from
                int r2 = n2 == 0 ? 0 : random.nextInt(n2); // to

                // move
                Ride toMove = newRides.get(d1).get(r1);
                newRides.get(d1).remove(r1);

                newRides.get(d2).add(r2, toMove);
            }
        }

        for (List<Ride> rideList : newRides) {
            builder.addVehicle(rideList);
        }

        boolean validate = random.nextDouble() < 0.05;
        return builder.build(validate);
    }
}
