package ru.hofftech.packingdeliveries.service.command.console;

import ru.hofftech.packingdeliveries.service.command.params.ExitParams;

/**
 * Команда консольного интерфейса для корректного завершения работы приложения.
 * <p>
 * При выполнении данной команды происходит немедленная остановка виртуальной машины Java.
 */
public class ExitConsoleCommand extends ConsoleCommand {

    /**
     * Создает экземпляр команды выхода.
     *
     * @param commandParams параметры команды (в данной реализации могут быть пустыми)
     */
    public ExitConsoleCommand(ExitParams commandParams) {
        super(commandParams);
    }

    /**
     * Завершает выполнение текущего процесса приложения с кодом статуса 0 (успех).
     */
    public void execute() {
        System.exit(0);
    }

    /**
     * Всегда возвращает {@code true}, так как для команды выхода не требуются
     * обязательные валидные аргументы.
     *
     * @return {@code true}
     */
    public boolean validate() {
        return true;
    }
}
