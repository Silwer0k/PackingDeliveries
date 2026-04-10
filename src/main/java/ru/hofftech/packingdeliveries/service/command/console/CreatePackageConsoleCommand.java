package ru.hofftech.packingdeliveries.service.command.console;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.service.command.params.CreatePackageParams;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.processors.CreatePackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда консольного интерфейса для создания новой посылки.
 * <p>
 * Данный класс связывает параметры ввода с бизнес-логикой создания объекта
 * и выводит результат в консоль в случае успешного завершения.
 */
public class CreatePackageConsoleCommand extends ConsoleCommand {
    private static final Logger log = LoggerFactory.getLogger(CreatePackageConsoleCommand.class);
    private final CargoPackageRepository packageRepository;

    /**
     * Создает экземпляр команды.
     *
     * @param packageRepository репозиторий, куда будет помещена новая посылка
     * @param commandParams параметры команды, содержащие имя, форму и маркер посылки
     */
    public CreatePackageConsoleCommand(CargoPackageRepository packageRepository, CreatePackageParams commandParams) {
        super(commandParams);
        this.packageRepository = packageRepository;
    }

    /**
     * Исполняет логику создания посылки.
     * <p>
     * Извлекает параметры, инициирует процесс создания через {@link CreatePackage}
     * и, при успешном результате, передает созданный объект в {@link ConsoleOutput} для отображения.
     */
    public void execute() {
        CreatePackageParams params = getParams();
        CreatePackage createPackage = new CreatePackage(
                packageRepository, params.getPackageName(), params.getPackageForm(), params.getPackageMarker());
        createPackage.doProcess();
        if (createPackage.isSuccess()) {
            new ConsoleOutput().doOutput(List.of(createPackage.getNewPackage()));
        }
    }
}
