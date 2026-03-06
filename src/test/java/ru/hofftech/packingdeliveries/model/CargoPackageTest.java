package ru.hofftech.packingdeliveries.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CargoPackageTest {

    @Test
    public void testPackage7Structure() {
        int width = 4;
        int depth = 2;
        char marker = '7';
        char[][] expectedStructure = {
            {'7', '7', '7', ' '},
            {'7', '7', '7', '7'}
        };
        CargoPackage cargoPackage = new CargoPackage(marker, width, depth);

        for (int i = 0; i < depth; i++) {
            Assertions.assertArrayEquals(expectedStructure[i], cargoPackage.packageMatrixRow(i));
        }
    }

    @Test
    public void testPackage3Structure() {
        int width = 3;
        int depth = 1;
        char marker = '3';
        char[][] expectedStructure = {{'3', '3', '3'}};
        CargoPackage cargoPackage = new CargoPackage(marker, width, depth);

        for (int i = 0; i < depth; i++) {
            Assertions.assertArrayEquals(expectedStructure[i], cargoPackage.packageMatrixRow(i));
        }
    }

    @Test
    public void testPackage5Structure() {
        int width = 5;
        int depth = 1;
        char marker = '5';
        char[][] expectedStructure = {{'5', '5', '5', '5', '5'}};
        CargoPackage cargoPackage = new CargoPackage(marker, width, depth);

        for (int i = 0; i < depth; i++) {
            Assertions.assertArrayEquals(expectedStructure[i], cargoPackage.packageMatrixRow(i));
        }
    }
}
