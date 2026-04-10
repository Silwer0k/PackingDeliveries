package ru.hofftech.packingdeliveries.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.service.command.params.PackingParams;

/**
 * Утилитарный класс для чтения и парсинга названий посылок из текстовых источников.
 * <p>
 * Класс сканирует содержимое файлов и с помощью регулярных выражений извлекает
 * текстовые идентификаторы посылок, подготовленные для последующей упаковки.
 */
public class PackageReader {
    private static final Logger log = LoggerFactory.getLogger(PackageReader.class);
    private static final String packagesNamesRegex = "\"(.+)\"";

    /**
     * Считывает названия посылок из указанного файла.
     * <p>
     * Метод загружает все содержимое файла целиком и находит все подстроки,
     * подходящие под паттерн {@value #packagesNamesRegex}. Из найденных строк
     * удаляются обрамляющие кавычки.
     *
     * @param fileName путь к файлу, содержащему список имен посылок
     * @return список извлеченных имен посылок; если файл не найден или пуст,
     *         возвращается пустой список
     */
    public List<String> readFromFile(String fileName) {
        List<String> packagesNames = new ArrayList<>();

        try {
            File inputFile = new File(fileName);
            String fileData = new Scanner(inputFile).useDelimiter("\\Z").next();

            log.info("Импортируем файл: {}", fileName);
            Pattern packagesPattern = Pattern.compile(packagesNamesRegex);
            Matcher matcher = packagesPattern.matcher(fileData);
            while (matcher.find()) {
                packagesNames.add(matcher.group().replace("\"", ""));
            }
        } catch (FileNotFoundException ex) {
            log.error("Файл {} не найден", fileName);
        }
        return packagesNames;
    }

    /**
     * Преобразует текстовую строку с именами посылок в список.
     * <p>
     * Метод обрабатывает экранированные символы переноса строки ({@code \\n}),
     * заменяя их на фактические переносы, и разделяет строку на элементы.
     *
     * @param packagesNamesTxt строка, содержащая имена посылок, разделенные переносом строки
     * @return список извлеченных имен посылок
     */
    public List<String> readFromText(String packagesNamesTxt) {
        return Arrays.stream(packagesNamesTxt.replace("\\n", "\n").split("\n")).toList();
    }

    /**
     * Извлекает список имен посылок на основе параметров упаковки.
     * <p>
     * Метод анализирует тип входных данных в {@link PackingParams} и делегирует
     * чтение соответствующему методу (файловому или текстовому).
     *
     * @param packingParams объект с конфигурацией входных данных
     * @return список извлеченных имен посылок; если тип данных не поддерживается
     *         или произошла ошибка чтения, возвращается пустой список
     */
    public List<String> readFromPackingParams(PackingParams packingParams) {
        switch (packingParams.getInputDataType()) {
            case FILE -> {
                return readFromFile(packingParams.getPackagesFilename());
            }
            case TEXT -> {
                return readFromFile(packingParams.getPackagesNames());
            }
            default -> {
                log.error("Тип входных данных {} не поддерживается", packingParams.getInputDataType());
                return List.of();
            }
        }
    }
}
