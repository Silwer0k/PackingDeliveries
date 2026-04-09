package ru.hofftech.packingdeliveries.service.packagers;

import java.util.ArrayList;
import java.util.Comparator;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

/**
 * Упаковщик, реализующий стратегию равномерной загрузки (EVENLOAD).
 * <p>
 * Алгоритм стремится распределить посылки так, чтобы объем занятого пространства
 * в каждом грузовике был примерно одинаковым. Для этого перед каждой итерацией
 * грузовики сортируются по текущей загрузке, а целевой объем ограничивается средним значением.
 */
public class EvenLoadingPackager extends Packager {
    private int approxTargetTrackLoadVolume;

    public EvenLoadingPackager(int countOfTrucksToUse) {
        super(countOfTrucksToUse);
    }

    /**
     * Выполняет упаковку посылок с соблюдением баланса загрузки.
     * <p>
     * Особенности алгоритма:
     * <ol>
     *     <li>Вычисляет средний объем груза на одну машину через {@link #calcApproxValuesForPacking}.</li>
     *     <li>Сортирует список грузовиков по возрастанию занятого объема (сначала самые пустые).</li>
     *     <li>Пытается разместить посылку в грузовик, если его текущий объем не превышает средний расчетный.</li>
     * </ol>
     *
     * @param packagesToPack список посылок для размещения
     * @return {@code true}, если все посылки удалось распределить; {@code false}, если возникли трудности с размещением
     */
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

    /**
     * Рассчитывает средний объем посылок на один грузовик.
     * <p>
     * Если суммарный объем посылок превышает вместимость одного грузовика,
     * значение устанавливается как {@code общая масса / количество машин}.
     * В противном случае лимит устанавливается равным полной вместимости одного грузовика.
     *
     * @param packagesToPack список всех упаковываемых посылок
     */
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
