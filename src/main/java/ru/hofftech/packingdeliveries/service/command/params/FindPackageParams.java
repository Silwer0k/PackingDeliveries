package ru.hofftech.packingdeliveries.service.command.params;

/**
 * Параметры для команды поиска конкретной посылки по её имени.
 * <p>
 * Класс извлекает название посылки из строки аргументов.
 * Название должно быть заключено в двойные кавычки, например: {@code "Посылка тип 3"}.
 */
public class FindPackageParams extends CommandParams {
    private String packageNameToFind;

    public FindPackageParams(String rawParams) {
        super(rawParams);
    }

    /**
     * @return название посылки для выполнения поиска
     */
    public String getPackageNameToFind() {
        return packageNameToFind;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String commandParamsPattern() {
        return "\"(.+)\"";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseCommandParams() {
        packageNameToFind = commandMatcher.group(1);
    }
}
