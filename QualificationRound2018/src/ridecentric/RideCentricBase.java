package ridecentric;

import base.RidesSolver;
import io.InputFile;
import model.Ride;
import output.SelfDrivingSolution;

import java.util.ArrayList;
import java.util.List;

public abstract class RideCentricBase extends RidesSolver {

    public RideCentricBase(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }

    @Override
    public SelfDrivingSolution solve() {
        List<List<Ride>> ridesForCab = new ArrayList<>(grid.F);
        for (int i = 0; i < grid.F; i++) {
            ridesForCab.add(new ArrayList<>());
        }
        preprocess();
        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(this.grid);

        while (!isDone()) {
            Ride nextRide = pickRide();
            int matchedCab = matchCab(nextRide);
            if (matchedCab >= 0) {
                ridesForCab.get(matchedCab).add(nextRide);
            }
            // TODO: keep track of non-matched rides for in the future?
        }
        for (int i = 0; i < grid.F; i++) {
            builder.addVehicle(ridesForCab.get(i));
        }
        return builder.build(true);
    }

    abstract void preprocess();

    abstract Ride pickRide();

    abstract int matchCab(Ride ride);

    abstract boolean isDone();
}
