package ru.hofftech.packingdeliveries.service.processors;

import java.util.List;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Процессор команды получения списка всех посылок.
 * <p>
 * Класс извлекает все доступные в системе объекты {@link CargoPackage} из репозитория.
 */
public class FindAllPackages implements CommandProcessor {
    private final CargoPackageRepository packageRepository;
    private List<CargoPackage> allPackages;

    /**
     * Конструирует процессор поиска всех посылок.
     *
     * @param packageRepository репозиторий для получения данных
     */
    public FindAllPackages(CargoPackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    /**
     * Возвращает список всех найденных посылок.
     *
     * @return список объектов {@link CargoPackage}
     */
    public List<CargoPackage> getAllPackages() {
        return allPackages;
    }

    /**
     * Извлекает полную коллекцию посылок из репозитория.
     */
    @Override
    public void doProcess() {
        allPackages = packageRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuccess() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getProcessErrors() {
        return List.of();
    }
}
