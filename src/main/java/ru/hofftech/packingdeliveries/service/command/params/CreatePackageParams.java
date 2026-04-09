package ru.hofftech.packingdeliveries.service.command.params;

/**
 * Параметры для команды создания новой посылки.
 * <p>
 * Класс разбирает строку формата: {@code -name "название" -form "форма" -symbol "символ"}.
 * Использует регулярные выражения для извлечения параметров из групп захвата.
 */
public class CreatePackageParams extends CommandParams {
    private String packageName;
    private String packageForm;
    private char packageMarker;

    public CreatePackageParams(String rawParams) {
        super(rawParams);
    }

    /**
     * @return извлеченное из параметров команды имя посылки
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @return извлеченное из параметров команды строковое представление формы посылки
     */
    public String getPackageForm() {
        return packageForm;
    }

    /**
     * @return извлеченное из параметров команды символ-маркер, назначенный посылке
     */
    public char getPackageMarker() {
        return packageMarker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String commandParamsPattern() {
        return "-name \"(.+)\" -form \"(.+)\" -symbol \"(.)\"";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseCommandParams() {
        packageName = commandMatcher.group(1);
        packageForm = commandMatcher.group(2);
        packageMarker = commandMatcher.group(3).charAt(0);
    }
}
