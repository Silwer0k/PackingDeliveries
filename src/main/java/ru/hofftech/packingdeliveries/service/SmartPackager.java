package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import java.util.Comparator;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

public class SmartPackager extends Packager {

    @Override
    public void doPacking(ArrayList<CargoPackage> packagesToPack) {
        ArrayList<CargoPackage> sortedPackages = new ArrayList<>(packagesToPack);
        sortedPackages.sort(Comparator.comparingInt(CargoPackage::getWeight).reversed());

        log.info("Начало упаковки в грузовики");
        for (CargoPackage cargo : sortedPackages) {
            boolean isPlaced = false;
            for (Truck truck : trucks) {
                if (truck.tryPlacePackage(cargo)) {
                    isPlaced = true;
                    break;
                }
            }
            if (!isPlaced) {
                Truck newTruck = new Truck();
                newTruck.placePackage(cargo, newTruck.getDepth() - 1, 0);
                trucks.add(newTruck);
            }
        }
        log.info("Окончание упаковки в грузовики");
    }
}
