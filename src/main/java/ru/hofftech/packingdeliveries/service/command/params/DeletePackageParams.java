package ru.hofftech.packingdeliveries.service.command.params;

/**
 * Параметры для команды удаления существующей посылки.
 * <p>
 * Класс предназначен для разбора строки, содержащей имя посылки в кавычках.
 * Пример ожидаемого ввода: {@code "Посылка тип 3"}.
 */
public class DeletePackageParams extends CommandParams {
    private String packageNameToDelete;

    public DeletePackageParams(String rawParams) {
        super(rawParams);
    }

    /**
     * @return имя посылки, предназначенной для удаления
     */
    public String getPackageNameToDelete() {
        return packageNameToDelete;
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
        packageNameToDelete = commandMatcher.group(1);
    }
}
