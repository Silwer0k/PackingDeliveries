package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

public abstract class Packager {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final ArrayList<Truck> trucks = new ArrayList<>();

    public void showPackingResults() {
        String truckSpaceStr;
        StringBuilder logStrBuilder = new StringBuilder();
        for (Truck truck : trucks) {
            truckSpaceStr = truck.toString();
            logStrBuilder.append(truckSpaceStr);
            System.out.println(truckSpaceStr);
        }
        if (!logStrBuilder.toString().isEmpty()) {
            log.info("Результаты упаковки:\n{}", logStrBuilder.toString());
        }
    }

    public abstract void doPacking(ArrayList<CargoPackage> packagesToPack);
}
