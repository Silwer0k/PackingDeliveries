package ru.hofftech.packingdeliveries.service.processors;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.service.packagers.Packager;
import ru.hofftech.packingdeliveries.service.packagers.PackagerFactory;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Процессор бизнес-логики процесса упаковки.
 * <p>
 * Координирует работу по поиску посылок в репозитории, созданию подходящего
 * упаковщика через {@link PackagerFactory} и запуску алгоритма распределения
 * груза по грузовикам.
 */
public class Packing implements CommandProcessor {
    private static final Logger log = LoggerFactory.getLogger(Packing.class);
    private final List<String> packagesNamesToPack;
    private final CargoPackageRepository packageRepository;
    private final int countTruckToLoad;
    private final String packagingMethod;
    private boolean success = false;
    private final List<String> errors;
    private List<Truck> resultTrucks;

    /**
     * Конструирует процессор упаковки.
     *
     * @param packageRepository   источник данных о типах посылок
     * @param packagesNamesToPack список имен (идентификаторов) посылок для загрузки
     * @param countTruckToLoad    лимит доступных грузовиков
     * @param packagingMethod     строковое обозначение алгоритма упаковки
     */
    public Packing(
            CargoPackageRepository packageRepository,
            List<String> packagesNamesToPack,
            int countTruckToLoad,
            String packagingMethod) {
        this.packagesNamesToPack = packagesNamesToPack;
        this.packageRepository = packageRepository;
        this.countTruckToLoad = countTruckToLoad;
        this.packagingMethod = packagingMethod;
        errors = new ArrayList<String>();
    }

    /**
     * @return список объектов {@link Truck} с размещенными внутри посылками
     */
    public List<Truck> getResultTrucks() {
        return resultTrucks;
    }

    /**
     * Выполняет комплексный процесс упаковки.
     * <p>
     * Логика работы:
     * <ol>
     *     <li>Создает экземпляр упаковщика через {@link PackagerFactory}.</li>
     *     <li>Преобразует список имен в список объектов {@link CargoPackage},
     *         игнорируя несуществующие в репозитории элементы и фиксируя ошибки.</li>
     *     <li>Запускает процесс {@code doPacking} у выбранного упаковщика.</li>
     *     <li>При успешном размещении всех найденных посылок сохраняет результат.</li>
     * </ol>
     * Обрабатывает {@link IllegalArgumentException}, если передан неизвестный метод упаковки.
     */
    @Override
    public void doProcess() {
        try {
            Packager packager = new PackagerFactory().construct(packagingMethod, countTruckToLoad);
            ArrayList<CargoPackage> existingPackagesInRepo = new ArrayList<CargoPackage>();
            for (String packageName : packagesNamesToPack) {
                packageRepository.find(packageName).ifPresentOrElse(existingPackagesInRepo::add, () -> {
                    log.warn("Посылки с именем \"{}\" не существует в репозитории", packageName);
                    errors.add(String.format("Посылки с именем \"%s\" не существует в репозитории", packageName));
                });
            }
            if (packager.doPacking(existingPackagesInRepo)) {
                success = true;
                resultTrucks = packager.getTrucks();
            }
        } catch (IllegalArgumentException e) {
            log.error("Метод упаковки {} не поддерживается", packagingMethod);
            errors.add(String.format("Метод упаковки %s не поддерживается", packagingMethod));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuccess() {
        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getProcessErrors() {
        return errors;
    }
}
