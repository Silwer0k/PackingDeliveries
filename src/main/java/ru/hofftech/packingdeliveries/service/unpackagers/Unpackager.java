package ru.hofftech.packingdeliveries.service.unpackagers;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.CargoPackagePosition;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.output.FileOutput;

public abstract class Unpackager {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final ArrayList<CargoPackage> unpackedPackages = new ArrayList<>();
    private final String unpackingResultFileName = "unpacking_result";

    public abstract boolean doUnpacking();

    protected void unpackTrucks(List<Truck> trucksToUnpack) {
        for (Truck truckToUnpack : trucksToUnpack) {
            for (CargoPackagePosition cargoPackagePos : truckToUnpack.getLoadedPackages()) {
                unpackedPackages.add(cargoPackagePos.getCargoPackage());
            }
            truckToUnpack.doEmpty();
        }
    }

    public void unpackingResultToConsole() {
        new ConsoleOutput().doOutput(unpackedPackages);
    }

    public void unpackingResultToFile() {
        new FileOutput(unpackingResultFileName).doOutput(unpackedPackages);
    }
}
