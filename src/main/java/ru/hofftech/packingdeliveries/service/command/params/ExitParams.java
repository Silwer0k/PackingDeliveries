package ru.hofftech.packingdeliveries.service.command.params;

/**
 * Параметры для команды завершения работы приложения (EXIT).
 * <p>
 * Данная команда не предполагает наличия аргументов, поэтому класс
 * использует пустой паттерн и не выполняет логику разбора.
 */
public class ExitParams extends CommandParams {
    public ExitParams(String rawParams) {
        super(rawParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String commandParamsPattern() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseCommandParams() {}
}
