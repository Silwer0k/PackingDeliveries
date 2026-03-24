package ru.hofftech.packingdeliveries.model.jsonDataContract;

import ru.hofftech.packingdeliveries.model.CargoPackagePosition;

public record LoadedPackageDataContract(int rowPos, int colPos, CargoPackageDataContract cargoPackage)
        implements JsonDataContract {
    @Override
    public CargoPackagePosition toModelObject() {
        return new CargoPackagePosition(rowPos, colPos, cargoPackage.toModelObject());
    }
}
