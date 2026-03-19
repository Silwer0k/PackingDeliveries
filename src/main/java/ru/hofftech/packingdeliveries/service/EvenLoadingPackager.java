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
        boolean packingResult = true;
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
            packingResult = packingResult && isPlaced;
        }
        log.info("Окончание упаковки в грузовики");
        if (!packingResult) {
            log.error("Не удалось разместить все посылки в грузовики!");
        }
        return packingResult;
    }

    private void calcApproxValuesForPacking(ArrayList<CargoPackage> packagesToPack) {
        int volumePackages =
                packagesToPack.stream().mapToInt(CargoPackage::volume).sum();

        if (volumePackages > Truck.getAllVolume() && !trucks.isEmpty()) {
            approxTargetTrackLoadVolume = (int) Math.ceil((double) volumePackages / trucks.size());
        } else {
            approxTargetTrackLoadVolume = Truck.getAllVolume();
        }
        log.info("Ориентировочная загрузка грузовиков: {}", approxTargetTrackLoadVolume);
    }
}
