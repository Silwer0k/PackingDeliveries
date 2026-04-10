package ru.hofftech.packingdeliveries.model.enums;

/**
 * Определяет типы входных данных для процесса упаковки.
 * <p>
 * Используется для выбора соответствующего механизма чтения (парсера)
 * входной информации о посылках.
 */
public enum PackingInputDataType {
    FILE,
    TEXT
}
