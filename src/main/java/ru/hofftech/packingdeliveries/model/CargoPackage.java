package ru.hofftech.packingdeliveries.model;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoPackage implements Outputable {
    private static final Logger log = LoggerFactory.getLogger(CargoPackage.class);
    private final char marker;
    private final int width;
    private final int depth;
    private final char[][] packageMatrix;

    public char getMarker() {
        return marker;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public String packageShape() {
        StringBuilder shape = new StringBuilder();
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                shape.append(packageMatrix[row][col]);
            }
            shape.append('\n');
        }
        return shape.toString();
    }

    public CargoPackage(char marker, int width, int depth) {
        this.marker = marker;
        this.width = width;
        this.depth = depth;
        int weight = Character.getNumericValue(this.marker);

        int curWeight = 0;
        packageMatrix = new char[this.depth][this.width];
        for (int row = this.depth - 1; row >= 0; row--) {
            for (int col = 0; col < this.width; col++) {
                curWeight++;
                packageMatrix[row][col] = (curWeight <= weight) ? this.marker : Truck.emptySpaceMarker;
            }
        }
        log.info(
                "Создан объект посылки с параметрами: Marker = {}, Width = {}, Depth = {}",
                this.marker,
                this.width,
                this.depth);
    }

    public int volume() {
        return (int) Arrays.stream(packageMatrix)
                .flatMapToInt(row -> new String(row).chars())
                .mapToObj(cell -> (char) cell)
                .filter(cell -> !cell.equals(Truck.emptySpaceMarker))
                .count();
    }

    public char[] packageMatrixRow(int row) {
        return packageMatrix[row];
    }

    public String toString() {
        return String.format("%c:%d,%d", marker, width, depth);
    }

    @Override
    public String toOutputValue() {
        return packageShape();
    }
}
