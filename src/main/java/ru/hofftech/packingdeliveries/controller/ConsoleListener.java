package ru.hofftech.packingdeliveries.controller;

import java.util.Scanner;
import ru.hofftech.packingdeliveries.service.command.console.ConsoleCommandService;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Слушатель консольного ввода для управления логикой приложения.
 * <p>
 * Обеспечивает непрерывное чтение команд из стандартного потока ввода (System.in)
 * и передает их на обработку в {@link ConsoleCommandService}.
 */
public class ConsoleListener {
    private final ConsoleCommandService commandProcessorService;
    private final CargoPackageRepository packageRepository;

    /**
     * Инициализирует новый слушатель и настраивает сервис обработки команд.
     *
     * @param packageRepository репозиторий, необходимый для работы с данными в командах
     */
    public ConsoleListener(CargoPackageRepository packageRepository) {
        this.packageRepository = packageRepository;
        commandProcessorService = new ConsoleCommandService(this.packageRepository);
    }

    /**
     * Запускает бесконечный цикл чтения строк из консоли.
     * Каждая введенная строка передается в {@code commandProcessorService} до тех пор,
     * пока входной поток не будет закрыт.
     */
    public void listen() {
        Scanner consoleScanner = new Scanner(System.in);

        while (consoleScanner.hasNextLine()) {
            commandProcessorService.executeCommand(consoleScanner.nextLine());
        }
    }
}
