package ru.hofftech.packingdeliveries.model.jsonDataContract;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.Truck;

public record TruckDataContract(
        String number,
        int width,
        int depth,
        int loadedVolume,
        @JsonProperty(value = "packages") ArrayList<LoadedPackageDataContract> loadedPackages)
        implements JsonDataContract {
    @Override
    public Truck toModelObject() {
        Truck truck = new Truck();
        for (LoadedPackageDataContract loadedPackage : loadedPackages) {
            truck.placePackage(
                    loadedPackage.cargoPackage().toModelObject(), loadedPackage.rowPos(), loadedPackage.colPos());
        }
        return truck;
    }
}
