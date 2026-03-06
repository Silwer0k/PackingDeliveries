package ru.hofftech.packingdeliveries.model;

import ru.hofftech.packingdeliveries.service.OneByOnePackager;
import ru.hofftech.packingdeliveries.service.Packager;
import ru.hofftech.packingdeliveries.service.SmartPackager;

public class PackagerFactory {
    public Packager construct(String packagingMethod) throws IllegalArgumentException {
        PackagingMethod method = PackagingMethod.valueOf(packagingMethod.toUpperCase());
        return switch (method) {
            case SMART -> new SmartPackager();
            case ONEBYONE -> new OneByOnePackager();
        };
    }
}
