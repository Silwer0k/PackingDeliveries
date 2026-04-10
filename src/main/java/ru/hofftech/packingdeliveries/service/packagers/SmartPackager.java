package ru.hofftech.packingdeliveries.service.packagers;

import java.util.ArrayList;
import java.util.Comparator;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

/**
 * Упаковщик, реализующий "умную" стратегию загрузки (SMART).
 * <p>
 * Алгоритм предварительно сортирует посылки по значению их маркера (весу/типу)
 * в обратном порядке (от больших к меньшим). Это позволяет сначала разместить
 * наиболее габаритные грузы, оптимизируя использование пространства в грузовиках.
 */
public class SmartPackager extends Packager {

    public SmartPackager(int countOfTrucksToUse) {
        super(countOfTrucksToUse);
    }

    /**
     * Выполняет упаковку с использованием алгоритма сортировки по маркеру.
     * <p>
     * Процесс работы:
     * <ol>
     *     <li>Создается копия списка посылок, которая сортируется по убыванию символа маркера.</li>
     *     <li>Для каждой посылки последовательно опрашиваются грузовики из списка {@code trucks}.</li>
     *     <li>Посылка помещается в первый грузовик, в котором нашлось подходящее место (с учетом опоры).</li>
     * </ol>
     *
     * @param packagesToPack список посылок для распределения
     * @return {@code true}, если все посылки удалось успешно разместить;
     *         {@code false}, если хотя бы для одной посылки не хватило места во всех доступных машинах
     */
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
