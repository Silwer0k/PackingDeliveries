package ru.hofftech.packingdeliveries.service.command.console;

import ru.hofftech.packingdeliveries.service.command.params.CommandParams;

/**
 * Базовый абстрактный класс для всех консольных команд приложения.
 * <p>
 * Инкапсулирует параметры команды и определяет жизненный цикл выполнения:
 * проверка валидности данных и непосредственное выполнение логики.
 */
public abstract class ConsoleCommand {
    protected final CommandParams commandParams;

    /**
     * Создает экземпляр команды с заданными параметрами.
     *
     * @param commandParams объект, содержащий входные данные для команды
     */
    public ConsoleCommand(CommandParams commandParams) {
        this.commandParams = commandParams;
    }

    /**
     * Возвращает параметры команды, приведенные к конкретному типу.
     *
     * @param <T> целевой тип параметров (наследник {@link CommandParams})
     * @return объект параметров, приведенный к типу T
     */
    public <T> T getParams() {
        return (T) commandParams;
    }

    /**
     * Проверяет корректность параметров команды перед выполнением.
     * Делегирует логику проверки объекту {@code commandParams}.
     *
     * @return {@code true}, если параметры валидны; {@code false} в противном случае
     */
    public boolean validate() {
        return commandParams.validate();
    }

    /**
     * Выполняет основную бизнес-логику конкретной команды.
     * Метод должен быть реализован в классах-наследниках.
     */
    public abstract void execute();
}
