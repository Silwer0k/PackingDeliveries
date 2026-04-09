package ru.hofftech.packingdeliveries.service.command.console;

import ru.hofftech.packingdeliveries.service.command.params.FindAllPackagesParams;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.processors.FindAllPackages;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда консольного интерфейса для отображения всех существующих посылок.
 * <p>
 * Класс запрашивает полный список данных из репозитория и передает их
 * в стандартный консольный вывод.
 */
public class FindAllPackagesConsoleCommand extends ConsoleCommand {
    private final CargoPackageRepository packageRepository;

    /**
     * Создает экземпляр команды для поиска всех посылок.
     *
     * @param packageRepository репозиторий, из которого будут извлечены данные
     * @param commandParams параметры команды (обычно пустые для данной операции)
     */
    public FindAllPackagesConsoleCommand(
            CargoPackageRepository packageRepository, FindAllPackagesParams commandParams) {
        super(commandParams);
        this.packageRepository = packageRepository;
    }

    /**
     * Выполняет поиск и вывод данных.
     * <p>
     * Инициирует процесс получения всех объектов через {@link FindAllPackages}
     * и отображает результат с помощью {@link ConsoleOutput}.
     */
    public void execute() {
        FindAllPackages findAllPackages = new FindAllPackages(packageRepository);
        findAllPackages.doProcess();
        new ConsoleOutput().doOutput(findAllPackages.getAllPackages());
    }
}
