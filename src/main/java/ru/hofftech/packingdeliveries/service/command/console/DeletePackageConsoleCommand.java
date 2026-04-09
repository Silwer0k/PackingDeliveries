package ru.hofftech.packingdeliveries.service.command.console;

import ru.hofftech.packingdeliveries.service.command.params.DeletePackageParams;
import ru.hofftech.packingdeliveries.service.processors.DeletePackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда консольного интерфейса для удаления посылки из репозитория.
 * <p>
 * Класс извлекает имя целевой посылки из параметров и инициирует
 * процесс её удаления из репозитория.
 */
public class DeletePackageConsoleCommand extends ConsoleCommand {
    private final CargoPackageRepository packageRepository;

    /**
     * Создает экземпляр команды на удаление.
     *
     * @param packageRepository репозиторий, содержащий целевую посылку
     * @param commandParams параметры команды, содержащие идентификатор (имя) удаляемого объекта
     */
    public DeletePackageConsoleCommand(CargoPackageRepository packageRepository, DeletePackageParams commandParams) {
        super(commandParams);
        this.packageRepository = packageRepository;
    }

    /**
     * Выполняет операцию удаления.
     * <p>
     * Извлекает имя посылки через {@link DeletePackageParams} и запускает
     * процесс обработки удаления в классе {@link DeletePackage}.
     */
    public void execute() {
        DeletePackageParams params = getParams();
        new DeletePackage(packageRepository, params.getPackageNameToDelete()).doProcess();
    }
}
