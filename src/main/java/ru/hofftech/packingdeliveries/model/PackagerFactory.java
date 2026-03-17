package ru.hofftech.packingdeliveries.model;

import ru.hofftech.packingdeliveries.service.EvenLoadingPackager;
import ru.hofftech.packingdeliveries.service.OneByOnePackager;
import ru.hofftech.packingdeliveries.service.Packager;
import ru.hofftech.packingdeliveries.service.SmartPackager;

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
