package ru.hofftech.packingdeliveries.service.command.params;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Базовый класс для обработки и валидации параметров команд.
 * <p>
 * Обеспечивает единый механизм разбора входной строки с использованием регулярных выражений.
 * Наследники должны определить конкретный паттерн и логику извлечения данных.
 */
public abstract class CommandParams {
    protected final Matcher commandMatcher;
    protected final String rawParams;

    /**
     * Конструирует объект параметров и инициирует автоматический разбор.
     * <p>
     * Если строка {@code rawParams} проходит валидацию, вызывается метод {@link #parseCommandParams()}.
     *
     * @param rawParams сырая строка аргументов для обработки
     */
    public CommandParams(String rawParams) {
        this.rawParams = rawParams;
        commandMatcher = Pattern.compile(commandParamsPattern()).matcher(rawParams);
        if (validate()) {
            parseCommandParams();
        }
    }

    /**
     * Проверяет, соответствует ли входная строка установленному паттерну.
     *
     * @return {@code true}, если параметры валидны; {@code false} в противном случае
     */
    public boolean validate() {
        return commandMatcher.matches();
    }

    /**
     * Возвращает регулярное выражение для проверки и разбора параметров конкретной команды.
     *
     * @return строка, представляющая паттерн регулярного выражения
     */
    protected abstract String commandParamsPattern();

    /**
     * Выполняет извлечение данных из {@link #commandMatcher} во внутренние поля наследника.
     * Метод вызывается автоматически только при успешной валидации.
     */
    protected abstract void parseCommandParams();
}
