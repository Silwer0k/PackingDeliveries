package ru.hofftech.packingdeliveries.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public final class TruckDto implements BaseDto {
    private final String number;
    private final int width;
    private final int depth;
    private final int loadedVolume;
    private final ArrayList<CargoPackagePositionDto> loadedPackages;

    @JsonCreator
    public TruckDto(
            @JsonProperty("number") String number,
            @JsonProperty("width") int width,
            @JsonProperty("depth") int depth,
            @JsonProperty("loadedVolume") int loadedVolume,
            @JsonProperty(value = "packages") ArrayList<CargoPackagePositionDto> loadedPackages) {
        this.number = number;
        this.width = width;
        this.depth = depth;
        this.loadedVolume = loadedVolume;
        this.loadedPackages = loadedPackages;
    }

    public String getNumber() {
        return number;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getLoadedVolume() {
        return loadedVolume;
    }

    public ArrayList<CargoPackagePositionDto> getLoadedPackages() {
        return loadedPackages;
    }
}
