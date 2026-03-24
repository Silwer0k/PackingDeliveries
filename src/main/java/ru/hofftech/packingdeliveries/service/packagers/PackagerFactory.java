package ru.hofftech.packingdeliveries.service.packagers;

import ru.hofftech.packingdeliveries.model.enums.PackagingMethod;

public class PackagerFactory {
    public Packager construct(String packagingMethod, int countOfTrucksToUse) throws IllegalArgumentException {
        PackagingMethod method = PackagingMethod.valueOf(packagingMethod.toUpperCase());
        return switch (method) {
            case SMART -> new SmartPackager(countOfTrucksToUse);
            case ONEBYONE -> new OneByOnePackager(countOfTrucksToUse);
            case EVENLOAD -> new EvenLoadingPackager(countOfTrucksToUse);
        };
    }
}
