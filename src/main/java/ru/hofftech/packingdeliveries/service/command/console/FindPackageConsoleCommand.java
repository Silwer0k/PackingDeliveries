package ru.hofftech.packingdeliveries.service.command.console;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.service.command.params.FindPackageParams;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.processors.FindPackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда консольного интерфейса для поиска конкретной посылки по её имени.
 * <p>
 * Класс выполняет запрос к репозиторию и, в случае обнаружения посылки,
 * выводит информацию о ней в консоль. Если посылка не найдена, фиксирует это в логах.
 */
public class FindPackageConsoleCommand extends ConsoleCommand {
    private static final Logger log = LoggerFactory.getLogger(FindPackageConsoleCommand.class);
    private final CargoPackageRepository packageRepository;

    /**
     * Создает экземпляр команды поиска.
     *
     * @param packageRepository репозиторий, в котором осуществляется поиск
     * @param commandParams параметры поиска, содержащие имя искомой посылки
     */
    public FindPackageConsoleCommand(CargoPackageRepository packageRepository, FindPackageParams commandParams) {
        super(commandParams);
        this.packageRepository = packageRepository;
    }

    /**
     * Исполняет логику поиска посылки.
     * <p>
     * Извлекает имя из параметров, инициирует процесс через {@link FindPackage}.
     * Если посылка найдена (isPresent), данные передаются в {@link ConsoleOutput}.
     * В противном случае выводится предупреждение в лог.
     */
    public void execute() {
        FindPackageParams params = getParams();
        FindPackage findPackage = new FindPackage(packageRepository, params.getPackageNameToFind());
        findPackage.doProcess();
        if (findPackage.getFoundPackage().isPresent()) {
            new ConsoleOutput().doOutput(List.of(findPackage.getFoundPackage().get()));
        } else {
            log.warn("Посылки с именем \"{}\" не существует", params.getPackageNameToFind());
        }
    }
}
