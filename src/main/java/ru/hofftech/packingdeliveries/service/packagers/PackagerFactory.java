package ru.hofftech.packingdeliveries.service.packagers;

import ru.hofftech.packingdeliveries.model.enums.PackagingMethod;

/**
 * Фабрика для создания объектов упаковщиков.
 * <p>
 * Позволяет получить конкретную реализацию {@link Packager} на основе
 * названия метода упаковки и заданного количества грузовиков.
 */
public class PackagerFactory {
    /**
     * Создает экземпляр упаковщика в соответствии с выбранной стратегией.
     * <p>
     * Метод преобразует строковое название в константу перечисления {@link PackagingMethod}.
     * Регистр строки при этом не имеет значения.
     *
     * @param packagingMethod    название метода упаковки (например, "smart", "onebyone")
     * @param countOfTrucksToUse количество грузовиков, которыми будет оперировать упаковщик
     * @return конкретная реализация {@link Packager} (например, {@link SmartPackager})
     * @throws IllegalArgumentException если переданный метод не поддерживается системой
     *                                  (отсутствует в перечислении {@link PackagingMethod})
     */
    public Packager construct(String packagingMethod, int countOfTrucksToUse) throws IllegalArgumentException {
        PackagingMethod method = PackagingMethod.valueOf(packagingMethod.toUpperCase());
        return switch (method) {
            case SMART -> new SmartPackager(countOfTrucksToUse);
            case ONEBYONE -> new OneByOnePackager(countOfTrucksToUse);
            case EVENLOAD -> new EvenLoadingPackager(countOfTrucksToUse);
        };
    }
}
