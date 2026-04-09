package ru.hofftech.packingdeliveries.service.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.Outputable;
import ru.hofftech.packingdeliveries.util.JsonConverter;

/**
 * Стратегия вывода данных в файл формата JSON.
 * <p>
 * Наследует логику формирования имен файлов с временными метками от {@link FileOutput},
 * но переопределяет расширение файла на {@code .json} и использует механизм
 * сериализации для сохранения структуры объектов.
 */
public class JsonFileOutput extends FileOutput {
    protected final Logger log = LoggerFactory.getLogger(JsonFileOutput.class);
    protected static final String fileExt = "json";

    public JsonFileOutput(String fileName) {
        super(fileName);
    }

    /**
     * Сериализует список объектов и записывает их в JSON-файл.
     * <p>
     * Для преобразования используется утилитарный класс {@code JsonConverter}.
     * Метод обрабатывает ошибки как процесса сериализации, так и записи на диск.
     *
     * @param toOutput список объектов (обычно DTO), реализующих {@link Outputable}
     * @return {@code true}, если файл успешно создан;
     *         {@code false} при ошибках сериализации или ввода-вывода
     */
    @Override
    public boolean doOutput(List<? extends Outputable> toOutput) {
        try {
            JsonConverter.toJsonFile(toOutput, fileName);
            log.info("Создан json файл: {}", fileName);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации!");
            return false;
        } catch (IOException e) {
            log.error("Ошибка экспорта в файл {}!", fileName);
            return false;
        }
        return true;
    }

    @Override
    protected String fileExt() {
        return "json";
    }
}
