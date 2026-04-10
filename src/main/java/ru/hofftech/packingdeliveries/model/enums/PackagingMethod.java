package ru.hofftech.packingdeliveries.model.enums;

/**
 * Определяет доступные методы (стратегии) упаковки посылок в грузовик.
 * <p>
 * Используется для выбора алгоритма при заполнении грузового пространства.
 */
public enum PackagingMethod {
    ONEBYONE,
    SMART,
    EVENLOAD
}
