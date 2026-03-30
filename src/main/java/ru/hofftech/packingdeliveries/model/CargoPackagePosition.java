package ru.hofftech.packingdeliveries.model;

public class CargoPackagePosition implements Outputable {
    private final int rowPos;
    private final int colPos;
    private final CargoPackage cargoPackage;

    public CargoPackagePosition(int rowPos, int colPos, CargoPackage cargoPackage) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.cargoPackage = cargoPackage;
    }

    public CargoPackage getCargoPackage() {
        return cargoPackage;
    }

    public int getColPos() {
        return colPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    @Override
    public String toOutputValue() {
        return "";
    }
}
