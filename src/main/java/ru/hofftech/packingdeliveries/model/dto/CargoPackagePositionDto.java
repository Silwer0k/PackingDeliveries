package ru.hofftech.packingdeliveries.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class CargoPackagePositionDto implements BaseDto {
    private final int rowPos;
    private final int colPos;
    private final CargoPackageDto cargoPackage;

    @JsonCreator
    public CargoPackagePositionDto(
            @JsonProperty("rowPos") int rowPos,
            @JsonProperty("colPos") int colPos,
            @JsonProperty("package") CargoPackageDto cargoPackage) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.cargoPackage = cargoPackage;
    }

    public int getRowPos() {
        return rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public CargoPackageDto getCargoPackage() {
        return cargoPackage;
    }
}
