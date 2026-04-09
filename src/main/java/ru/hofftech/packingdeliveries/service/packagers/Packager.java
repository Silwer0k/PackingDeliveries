package ru.hofftech.packingdeliveries.service.packagers;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

/**
 * Базовый класс для реализации алгоритмов упаковки посылок в грузовики.
 * <p>
 * Класс инкапсулирует коллекцию грузовиков {@link Truck}.
 */
public abstract class Packager {
    private final String packingResultFileName = "packing_result";
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final ArrayList<Truck> trucks = new ArrayList<>();

    /**
     * Создает упаковщик и инициализирует заданное количество пустых грузовиков.
     *
     * @param countOfTrucksToUse количество грузовиков, которые будут задействованы в процессе
     */
    public Packager(int countOfTrucksToUse) {
        for (int i = 0; i < countOfTrucksToUse; i++) {
            trucks.add(new Truck());
        }
    }

    /**
     * @return список грузовиков с их текущим состоянием загрузки
     */
    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    /**
     * Абстрактный метод, реализующий конкретную логику распределения посылок по грузовикам.
     *
     * @param packagesToPack список объектов {@link CargoPackage}, которые необходимо загрузить
     * @return {@code true}, если все посылки успешно размещены;
     *         {@code false}, если места в грузовиках не хватило
     */
    public abstract boolean doPacking(ArrayList<CargoPackage> packagesToPack);
}
