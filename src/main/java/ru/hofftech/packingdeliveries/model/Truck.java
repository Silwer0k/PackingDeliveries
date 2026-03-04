package ru.hofftech.packingdeliveries.model;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Truck {
    public static final char emptySpaceMarker = ' ';
    private static final char borderMarker = '+';
    private static final Logger log = LoggerFactory.getLogger(Truck.class);
    private static final Random randomizer = new Random();
    private final short width = 6;
    private final short depth = 6;
    private final char[][] cargoSpace;
    private final String number;

    public String getNumber() {
        return number;
    }

    public short getWidth() {
        return width;
    }

    public short getDepth() {
        return depth;
    }

    public Truck() {
        cargoSpace = new char[width][depth];
        number = String.valueOf(randomizer.nextInt(100));
        log.info("Создан объект грузовика {} с параметрами Width: {} Depth: {}", number, width, depth);
        doEmpty();
    }

    public boolean tryPlacePackage(CargoPackage cargoPackage) {
        for (int row = depth - 1; row >= 0; row--) {
            for (int col = 0; col < width; col++) {
                if (cargoSpace[row][col] == emptySpaceMarker
                        && checkCargoSpaceForPackage(cargoPackage, row, col)
                        && checkSupportForCargo(cargoPackage, row, col)) {
                    placePackage(cargoPackage, row, col);
                    return true;
                }
            }
        }
        return false;
    }

    public void placePackage(CargoPackage cargoPackage, int rowPos, int colPos) {
        for (int packageRow = 0; packageRow < cargoPackage.getDepth(); packageRow++) {
            System.arraycopy(
                    cargoPackage.packageMatrixRow(packageRow),
                    0,
                    cargoSpace[rowPos - (cargoPackage.getDepth() - 1) + packageRow],
                    colPos,
                    cargoPackage.getWidth());
        }
        log.info("Посылка {} размещена на позицию {},{}", cargoPackage.toString(), rowPos, colPos);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row <= depth; row++) {
            for (int column = -1; column <= width; column++) {
                str.append(isBorderCoords(row, column) ? borderMarker : cargoSpace[row][column]);
            }
            str.append('\n');
        }
        return str.toString();
    }

    private boolean isBorderCoords(int row, int column) {
        return ((row == depth) || (column == -1) || (column == width));
    }

    private void doEmpty() {
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                cargoSpace[row][col] = emptySpaceMarker;
            }
        }
        log.info("Разгрузили грузовик");
    }

    private boolean checkCargoSpaceForPackage(CargoPackage cargoPackage, int rowPos, int colPos) {
        if ((rowPos - (cargoPackage.getDepth() - 1) < 0) || (colPos + cargoPackage.getWidth() - 1 >= width)) {
            return false;
        }

        for (int row = 0; row < cargoPackage.getDepth(); row++) {
            for (int col = 0; col < cargoPackage.getWidth(); col++) {
                if (cargoSpace[rowPos - row][colPos + col] != emptySpaceMarker) return false;
            }
        }
        return true;
    }

    private boolean checkSupportForCargo(CargoPackage cargoPackage, int rowPos, int colPos) {
        if (rowPos + 1 == depth) {
            return true;
        }

        int supports = 0;
        for (int col = 0; col < cargoPackage.getWidth(); col++) {
            supports += (cargoSpace[rowPos + 1][colPos + col] == emptySpaceMarker) ? 0 : 1;
        }

        return supports > (cargoPackage.getWidth() / 2);
    }
}
