package ru.hofftech.packingdeliveries.model.enums;

/**
 * Определяет типы команд, доступных для обработки в системе.
 * <p>
 * Используется для маршрутизации запросов пользователя и определения
 * соответствующего процессора логики.
 */
public enum CommandProcessorType {
    PACKING,
    UNPACKING,
    EXIT,
    FIND,
    CREATE,
    DELETE
}
