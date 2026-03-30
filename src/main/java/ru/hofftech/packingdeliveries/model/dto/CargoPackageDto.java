package ru.hofftech.packingdeliveries.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoPackageDto implements BaseDto {
    private final char marker;
    private final int width;
    private final int depth;
    private final String shape;

    @JsonCreator
    public CargoPackageDto(
            @JsonProperty("marker") char marker,
            @JsonProperty("width") int width,
            @JsonProperty("depth") int depth,
            @JsonProperty("shape") String shape) {
        this.marker = marker;
        this.width = width;
        this.depth = depth;
        this.shape = shape;
    }

    public char getMarker() {
        return marker;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public String getShape() {
        return shape;
    }
}
