package ru.hofftech.packingdeliveries.model.jsonDataContract;

import ru.hofftech.packingdeliveries.model.CargoPackage;

public record CargoPackageDataContract(char marker, int width, int depth, String shape) implements JsonDataContract {
    @Override
    public CargoPackage toModelObject() {
        return new CargoPackage(marker, width, depth);
    }
}
