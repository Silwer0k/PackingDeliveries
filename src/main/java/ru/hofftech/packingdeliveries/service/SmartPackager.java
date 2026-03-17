package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import java.util.Comparator;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

public class SmartPackager extends Packager {

    public SmartPackager(int countOfTrucksToUse) {
        super(countOfTrucksToUse);
    }

    @Override
    public boolean doPacking(ArrayList<CargoPackage> packagesToPack) {
        ArrayList<CargoPackage> sortedPackages = new ArrayList<>(packagesToPack);
        sortedPackages.sort(Comparator.comparing(CargoPackage::getMarker).reversed());

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
                log.error("Не удалось разместить все посылки в грузовики!");
                return false;
            }
        }
        log.info("Окончание упаковки в грузовики");
        return true;
    }
}
