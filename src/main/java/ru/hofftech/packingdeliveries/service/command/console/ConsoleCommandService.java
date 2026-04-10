package ru.hofftech.packingdeliveries.service.command.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;
import ru.hofftech.packingdeliveries.service.command.params.CreatePackageParams;
import ru.hofftech.packingdeliveries.service.command.params.DeletePackageParams;
import ru.hofftech.packingdeliveries.service.command.params.ExitParams;
import ru.hofftech.packingdeliveries.service.command.params.FindAllPackagesParams;
import ru.hofftech.packingdeliveries.service.command.params.FindPackageParams;
import ru.hofftech.packingdeliveries.service.command.params.PackingParams;
import ru.hofftech.packingdeliveries.service.command.params.UnpackingParams;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Сервис для интерпретации и выполнения текстовых команд из консоли.
 * <p>
 * Класс парсит входящую строку, сопоставляет её с перечислением {@link CommandProcessorType},
 * создает соответствующий объект команды и выполняет его после валидации параметров.
 */
public class ConsoleCommandService {
    private static final Logger log = LoggerFactory.getLogger(ConsoleCommandService.class);
    private final CargoPackageRepository cargoPackageRepository;

    /**
     * Создает экземпляр сервиса.
     *
     * @param cargoPackageRepository репозиторий, необходимый для операций с грузами
     */
    public ConsoleCommandService(CargoPackageRepository cargoPackageRepository) {
        this.cargoPackageRepository = cargoPackageRepository;
    }

    /**
     * Разбирает строку ввода и запускает соответствующую логику обработки.
     * <p>
     * Процесс включает:
     * 1. Разделение строки на имя команды и аргументы.
     * 2. Преобразование имени в {@link CommandProcessorType}.
     * 3. Инициализацию конкретной реализации {@link ConsoleCommand}.
     * 4. Проверку параметров (validate) и выполнение (execute).
     *
     * @param inputCommandLine полная строка ввода (например, "create -name "TEST" -form -symbol "@"")
     */
    public void executeCommand(String inputCommandLine) {
        if (inputCommandLine.isEmpty()) {
            log.error("Не передана команда для обработки!");
            return;
        }

        String commandName = "";
        String commandArgs = "";
        CommandProcessorType commandType;

        try {
            String[] command = inputCommandLine.split(" ", 2);
            commandName = command[0];
            if (command.length > 1) {
                commandArgs = command[1];
            }
            commandType = CommandProcessorType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
            log.error("Переданная команда \"{}\" не поддерживается!", commandName);
            return;
        }

        ConsoleCommand command;
        switch (commandType) {
            case PACKING -> {
                command = new PackingConsoleCommand(cargoPackageRepository, new PackingParams(commandArgs));
            }
            case UNPACKING -> {
                command = new UnpackingConsoleCommand(new UnpackingParams(commandArgs));
            }
            case FIND -> {
                command = new FindPackageConsoleCommand(cargoPackageRepository, new FindPackageParams(commandArgs));
                if (!command.validate()) {
                    command = new FindAllPackagesConsoleCommand(
                            cargoPackageRepository, new FindAllPackagesParams(commandArgs));
                }
            }
            case CREATE -> {
                command = new CreatePackageConsoleCommand(cargoPackageRepository, new CreatePackageParams(commandArgs));
            }
            case DELETE -> {
                command = new DeletePackageConsoleCommand(cargoPackageRepository, new DeletePackageParams(commandArgs));
            }
            case EXIT -> {
                command = new ExitConsoleCommand(new ExitParams(commandArgs));
            }
            default -> {
                log.error("Переданная команда \"{}\" не реализована!", commandName);
                return;
            }
        }

        if (command.validate()) {
            command.execute();
        } else {
            log.error("Ошибка валидации входных аргументов команды {}", commandType);
        }
    }
}
