package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

public class OneByOnePackager extends Packager {

    public OneByOnePackager(int countOfTrucksToUse) {
        super(countOfTrucksToUse);
    }

    public boolean doPacking(ArrayList<CargoPackage> packages) {
        log.info("Начало упаковки в грузовики");
        int truckIndex = 0;
        for (CargoPackage cargo : packages) {
            if (truckIndex >= trucks.size()) {
                log.error("Не удалось разместить все посылки в грузовики!");
                return false;
            }
            Truck truckToUse = trucks.get(truckIndex);
            truckToUse.tryPlacePackage(cargo);
            truckIndex++;
            log.info("Упаковали посылки в грузовик {}", truckToUse.getNumber());
        }
        log.info("Окончание упаковки в грузовики");
        return true;
    }
}
