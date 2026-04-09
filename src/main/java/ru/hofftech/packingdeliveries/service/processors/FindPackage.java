package ru.hofftech.packingdeliveries.service.processors;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Процессор команды поиска конкретной посылки.
 * <p>
 * Выполняет запрос к репозиторию для получения объекта {@link CargoPackage}
 * по его уникальному имени.
 */
public class FindPackage implements CommandProcessor {
    private static final Logger log = LoggerFactory.getLogger(FindPackage.class);
    private final CargoPackageRepository packageRepository;
    private final String packageNameToFind;
    private Optional<CargoPackage> foundPackage;

    /**
     * Конструирует процессор поиска посылки.
     *
     * @param packageRepository  репозиторий для поиска
     * @param packageNameToFind  имя посылки (идентификатор)
     */
    public FindPackage(CargoPackageRepository packageRepository, String packageNameToFind) {
        this.packageRepository = packageRepository;
        this.packageNameToFind = packageNameToFind;
    }

    /**
     * Возвращает результат поиска в виде {@link Optional}.
     *
     * @return контейнер с найденной посылкой или пустой {@code Optional}, если посылка не найдена
     */
    public Optional<CargoPackage> getFoundPackage() {
        return foundPackage;
    }

    /**
     * Выполняет поиск посылки в репозитории по заданному имени.
     */
    @Override
    public void doProcess() {
        foundPackage = packageRepository.find(packageNameToFind);
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
