package ridecentric;

import base.RidesSolver;
import io.InputFile;
import model.Ride;
import output.SelfDrivingSolution;

public abstract class RideCentricBase extends RidesSolver {

    public RideCentricBase(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }

    @Override
    public SelfDrivingSolution solve() {
        preprocess();
        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(this.grid);

        while (!isDone()) {
            Ride nextRide = pickRide();
            int matchedCab = matchCab(nextRide);
            //TODO: builder.addVehicle()
        }
        return builder.build(true);
    }

    abstract void preprocess();

    abstract Ride pickRide();

    abstract int matchCab(Ride ride);

    abstract boolean isDone();
}
