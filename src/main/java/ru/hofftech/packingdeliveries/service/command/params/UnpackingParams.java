package ru.hofftech.packingdeliveries.service.command.params;

/**
 * Параметры для команды распаковки (UNPACKING).
 * <p>
 * Класс обрабатывает пути к входному файлу (откуда считываются данные о загрузке)
 * и выходному файлу (куда будет записан результат).
 * Пример строки: {@code -infile "truck_state.json" -outfile "report.txt"}
 */
public class UnpackingParams extends CommandParams {
    private String inFilename;
    private String outFilename;

    public UnpackingParams(String rawParams) {
        super(rawParams);
    }

    /**
     * @return путь к исходному файлу с данными
     */
    public String getInFilename() {
        return inFilename;
    }

    /**
     * @return путь к файлу, в который будет сохранен результат распаковки
     */
    public String getOutFilename() {
        return outFilename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String commandParamsPattern() {
        return "-infile \"(.+)\" -outfile \"(.+)\"";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseCommandParams() {
        inFilename = commandMatcher.group(1);
        outFilename = commandMatcher.group(2);
    }
}
