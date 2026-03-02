package ru.hofftech.packingdeliveries.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoPackage {
    private static final Logger log = LoggerFactory.getLogger(CargoPackage.class);
    private final char marker;
    private final int width;
    private final int depth;
    private final int weight;
    private final char[][] packageMatrix;

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getWeight() {
        return weight;
    }

    public CargoPackage(char packageMarker, int packageWidth, int packageDepth){
        marker = packageMarker;
        width = packageWidth;
        depth = packageDepth;
        weight = Character.getNumericValue(marker);

        int curWeight = 0;
        packageMatrix = new char[depth][width];
        for (int row = depth - 1; row >= 0; row--){
            for (int col = 0; col < width; col++){
                curWeight++;
                packageMatrix[row][col] = (curWeight <= weight) ? marker : Truck.emptySpaceMarker;
            }
        }
        log.info("Создан объект посылки с параметрами: Marker = {}, Width = {}, Depth = {}", marker, width, depth);
    }

    public char[] packageMatrixRow(int row){
        return packageMatrix[row];
    }

    public String toString(){
        return String.format("%c:%d,%d", marker, width, depth);
    }
}
