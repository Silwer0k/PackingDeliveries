package ru.hofftech.packingdeliveries.service.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.Outputable;

/**
 * Стратегия вывода данных в текстовый файл.
 * <p>
 * Формирует текстовый отчет на основе списка объектов {@link Outputable}.
 * Имя файла автоматически дополняется текущей датой и временем в формате {@code ddMMyyyyyHHmm}.
 */
public class FileOutput implements OutputStrategy {
    private static final Logger log = LoggerFactory.getLogger(FileOutput.class);
    protected final String fileName;

    /**
     * Создает объект вывода и генерирует уникальное имя файла с временной меткой.
     *
     * @param fileName базовое имя файла (без расширения и даты)
     */
    public FileOutput(String fileName) {
        this.fileName = String.format(
                "%s_%s.%s",
                fileName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyyHHmm")), fileExt());
    }

    /**
     * @return полное имя сформированного файла с расширением
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Записывает текстовое представление объектов в файл.
     * <p>
     * Метод объединяет результаты {@link Outputable#toOutputValue()} всех объектов
     * через символ новой строки и сохраняет полученную строку по пути {@link #fileName}.
     *
     * @param toOutput список объектов для сохранения
     * @return {@code true}, если файл успешно записан;
     *         {@code false} в случае ошибки ввода-вывода (IO)
     */
    @Override
    public boolean doOutput(List<? extends Outputable> toOutput) {
        try {
            StringBuilder resultStringBuilder = new StringBuilder();
            for (Outputable outputObj : toOutput) {
                resultStringBuilder.append(outputObj.toOutputValue());
                resultStringBuilder.append('\n');
            }
            Files.writeString(Path.of(fileName), resultStringBuilder.toString());
            log.info("Создан файл результатов: {}", fileName);
        } catch (IOException e) {
            log.error("Ошибка экспорта в файл {}", fileName);
            return false;
        }
        return true;
    }

    /**
     * Определяет расширение создаваемого файла.
     *
     * @return расширение файла (по умолчанию {@code "txt"})
     */
    protected String fileExt() {
        return "txt";
    }
}
