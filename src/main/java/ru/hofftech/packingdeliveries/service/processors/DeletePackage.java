package ru.hofftech.packingdeliveries.service.processors;

import java.util.List;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Процессор команды удаления посылки.
 * <p>
 * Выполняет удаление зарегистрированного типа посылки из репозитория по её уникальному имени.
 */
public class DeletePackage implements CommandProcessor {
    private final CargoPackageRepository packageRepository;
    private final String packageNameToDelete;
    private boolean success;

    /**
     * Конструирует процессор удаления посылки.
     *
     * @param packageRepository   репозиторий для управления данными
     * @param packageNameToDelete имя посылки для поиска и удаления
     */
    public DeletePackage(CargoPackageRepository packageRepository, String packageNameToDelete) {
        this.packageRepository = packageRepository;
        this.packageNameToDelete = packageNameToDelete;
    }

    /**
     * Выполняет процедуру удаления в репозитории.
     * <p>
     * Результат операции (найдена и удалена ли посылка) сохраняется во внутреннем флаге.
     */
    @Override
    public void doProcess() {
        success = packageRepository.remove(packageNameToDelete);
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
        return List.of();
    }
}
