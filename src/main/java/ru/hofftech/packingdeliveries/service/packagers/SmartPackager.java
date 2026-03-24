package ru.hofftech.packingdeliveries.service.packagers;

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
        boolean packingResult = true;
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
            packingResult = packingResult && isPlaced;
        }
        log.info("Окончание упаковки в грузовики");
        if (!packingResult) {
            log.error("Не удалось разместить все посылки в грузовики!");
        }
        return packingResult;
    }
}
