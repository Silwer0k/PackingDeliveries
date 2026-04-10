package ru.hofftech.packingdeliveries.service.unpackagers;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.CargoPackagePosition;
import ru.hofftech.packingdeliveries.model.Truck;

/**
 * Базовый класс для реализации механизмов распаковки.
 * <p>
 * Класс управляет списком извлеченных посылок {@link CargoPackage} и предоставляет
 * общий метод для трансформации состояния грузовиков в плоский список объектов.
 */
public abstract class Unpackager {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final ArrayList<CargoPackage> unpackedPackages = new ArrayList<>();

    /**
     * Абстрактный метод для запуска процесса распаковки.
     * <p>
     * Конкретные реализации должны определить источник данных (например, JSON-файл)
     * и логику их первичного получения.
     *
     * @return {@code true}, если распаковка прошла успешно; {@code false} в случае ошибки
     */
    public abstract boolean doUnpacking();

    /**
     * Возвращает список всех извлеченных в процессе распаковки посылок.
     *
     * @return список объектов {@link CargoPackage}
     */
    public ArrayList<CargoPackage> getUnpackedPackages() {
        return unpackedPackages;
    }

    /**
     * Выполняет технический процесс переноса посылок из грузовиков в общий список.
     * <p>
     * Для каждого грузовика извлекаются все упакованные в него посылки, после чего
     * сам грузовик очищается с помощью метода {@link Truck#doEmpty()}.
     *
     * @param trucksToUnpack список грузовиков, которые необходимо разгрузить
     */
    protected void unpackTrucks(List<Truck> trucksToUnpack) {
        for (Truck truckToUnpack : trucksToUnpack) {
            for (CargoPackagePosition cargoPackagePos : truckToUnpack.getLoadedPackages()) {
                unpackedPackages.add(cargoPackagePos.getCargoPackage());
            }
            truckToUnpack.doEmpty();
        }
    }
}
