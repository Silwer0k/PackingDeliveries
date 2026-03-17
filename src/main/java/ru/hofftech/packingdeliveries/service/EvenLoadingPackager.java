package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import java.util.Comparator;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

public class EvenLoadingPackager extends Packager {
    private int approxTargetTrackLoadVolume;

    public EvenLoadingPackager(int countOfTrucksToUse) {
        super(countOfTrucksToUse);
    }

    @Override
    public boolean doPacking(ArrayList<CargoPackage> packagesToPack) {
        log.info("Начало упаковки в грузовики");
        calcApproxValuesForPacking(packagesToPack);

        for (CargoPackage cargo : packagesToPack) {
            boolean isPlaced = false;
            trucks.sort(Comparator.comparingInt(Truck::getLoadedVolume));
            for (Truck truck : trucks) {
                if ((truck.getLoadedVolume() + cargo.volume() <= approxTargetTrackLoadVolume)
                        && truck.tryPlacePackage(cargo)) {
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

    private void calcApproxValuesForPacking(ArrayList<CargoPackage> packagesToPack) {
        int volumePackages =
                packagesToPack.stream().mapToInt(CargoPackage::volume).sum();

        int approxTargetTrucksCount;
        if (volumePackages > Truck.getAllVolume()) {
            approxTargetTrucksCount = (int) Math.ceil((double) volumePackages / Truck.getAllVolume());
            approxTargetTrackLoadVolume = (int) Math.ceil((double) volumePackages / approxTargetTrucksCount);
        } else {
            approxTargetTrackLoadVolume = Truck.getAllVolume();
            approxTargetTrucksCount = 1;
        }
        log.info("Ориентировочное кол-во грузовиков для упаковки: {}", approxTargetTrucksCount);
        log.info("Ориентировочная загрузка грузовиков: {}", approxTargetTrackLoadVolume);
    }
}
