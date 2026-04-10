package ru.hofftech.packingdeliveries.service.command.params;

/**
 * Параметры для команды поиска всех зарегистрированных посылок.
 * <p>
 * Класс проверяет наличие ключевого слова {@code all} в строке аргументов.
 * Используется, когда пользователь хочет просмотреть весь список доступных типов грузов.
 */
public class FindAllPackagesParams extends CommandParams {
    public FindAllPackagesParams(String rawParams) {
        super(rawParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String commandParamsPattern() {
        return "all";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseCommandParams() {}
}
