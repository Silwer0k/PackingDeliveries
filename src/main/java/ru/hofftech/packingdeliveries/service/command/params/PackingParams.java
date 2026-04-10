package ru.hofftech.packingdeliveries.service.command.params;

import ru.hofftech.packingdeliveries.model.enums.PackingInputDataType;
import ru.hofftech.packingdeliveries.model.enums.PackingOutputDataType;

/**
 * Параметры для основной команды упаковки (PACKING).
 * <p>
 * Поддерживает гибкую настройку источников данных, количества грузовиков,
 * метода упаковки и формата вывода.
 * Пример строки: {@code -parcels-file "data.txt" -trucks "2" -type "SMART" -out text}
 */
public class PackingParams extends CommandParams {
    private PackingInputDataType inputDataType;
    private String packagesFilename;
    private String packagesNames;
    private int countTrucksToUse;
    private String packagingMethodName;
    private PackingOutputDataType outputDataType;
    private String outputFilename;

    public PackingParams(String rawParams) {
        super(rawParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String commandParamsPattern() {
        return "(-parcels-file|-parcels-text) \"(.+)\" -trucks \"(\\d)\" -type \"(.+)\" -out (text|json-file -out-filename \"(.+)\")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseCommandParams() {
        String inputDataMethodName = commandMatcher.group(1);
        if (inputDataMethodName.equals("-parcels-file")) {
            inputDataType = PackingInputDataType.FILE;
            packagesFilename = commandMatcher.group(2);
        } else if (inputDataMethodName.equals("-parcels-text")) {
            inputDataType = PackingInputDataType.TEXT;
            packagesNames = commandMatcher.group(2);
        }
        countTrucksToUse = Integer.parseInt(commandMatcher.group(3));
        packagingMethodName = commandMatcher.group(4);

        String outputDataMethodName = commandMatcher.group(5);
        if (outputDataMethodName.equals("text")) {
            outputDataType = PackingOutputDataType.TEXT;
        } else {
            outputDataType = PackingOutputDataType.JSONFILE;
            outputFilename = commandMatcher.group(6);
        }
    }

    /** @return тип источника данных (файл или текст) */
    public PackingInputDataType getInputDataType() {
        return inputDataType;
    }

    /** @return путь к файлу с данными о посылках (если выбран ввод из файла) */
    public String getPackagesFilename() {
        return packagesFilename;
    }

    /** @return строковое перечисление имен посылок (если выбран текстовый ввод) */
    public String getPackagesNames() {
        return packagesNames;
    }

    /** @return количество доступных грузовиков для распределения груза */
    public int getCountTrucksToUse() {
        return countTrucksToUse;
    }

    /** @return название выбранного метода упаковки (соответствует именам в {@link PackagingMethod}) */
    public String getPackagingMethodName() {
        return packagingMethodName;
    }

    /** @return выбранный формат вывода результатов */
    public PackingOutputDataType getOutputDataType() {
        return outputDataType;
    }

    /** @return имя файла для сохранения результата */
    public String getOutputFilename() {
        return outputFilename;
    }
}
