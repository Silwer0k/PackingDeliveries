package ru.hofftech.packingdeliveries.service.packagers;

import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

/**
 * Упаковщик, реализующий стратегию загрузки одна посылка в один грузовик (ONEBYONE).
 * <p>
 * Данный алгоритм распределяет посылки линейно: каждая новая посылка
 * помещается в следующий доступный грузовик из списка.
 * Метод не пытается плотно заполнить одну машину перед переходом к другой.
 */
public class OneByOnePackager extends Packager {
    public OneByOnePackager(int countOfTrucksToUse) {
        super(countOfTrucksToUse);
    }

    /**
     * Выполняет распределение посылок по одной в каждый грузовик.
     * <p>
     * Алгоритм проходит по списку посылок и помещает каждую в грузовик
     * с соответствующим индексом. Если количество посылок превышает
     * количество грузовиков, процесс прерывается с ошибкой.
     *
     * @param packages список посылок для размещения
     * @return {@code true}, если все посылки были распределены по машинам;
     *         {@code false}, если количество посылок больше, чем количество грузовиков
     */
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
