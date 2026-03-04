package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

public class OneByOnePackager extends Packager {

    public void doPacking(ArrayList<CargoPackage> packages) {
        log.info("Начало упаковки в грузовики");
        for (CargoPackage cargo : packages) {
            Truck newTruck = new Truck();
            trucks.add(newTruck);
            newTruck.placePackage(cargo, newTruck.getDepth() - 1, 0);
            log.info("Упаковали посылки в грузовик {}", newTruck.getNumber());
        }
        log.info("Окончание упаковки в грузовики");
    }
}
