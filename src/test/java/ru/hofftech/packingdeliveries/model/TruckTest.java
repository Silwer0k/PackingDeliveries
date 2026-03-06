package ru.hofftech.packingdeliveries.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TruckTest {

    @Test
    void tryPlacePackage9ToEmptyTruck() {
        Truck testTruck = new Truck();
        CargoPackage testCargoPackage = new CargoPackage('9', 3, 3);
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("++++++++\n");

        Assertions.assertTrue(testTruck.tryPlacePackage(testCargoPackage));
        Assertions.assertEquals(expectedLoadingStructure.toString(), testTruck.toString());
    }

    @Test
    void tryPlacePackage9ToTruckWithNoSpaceNotPossible() {
        Truck testTruck = new Truck();
        CargoPackage testCargoPackage = new CargoPackage('9', 3, 3);
        testTruck.tryPlacePackage(new CargoPackage('9', 3, 3));
        testTruck.tryPlacePackage(new CargoPackage('9', 3, 3));
        testTruck.tryPlacePackage(new CargoPackage('9', 3, 3));
        testTruck.tryPlacePackage(new CargoPackage('9', 3, 3));
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+999999+\n")
                .append("+999999+\n")
                .append("+999999+\n")
                .append("+999999+\n")
                .append("+999999+\n")
                .append("+999999+\n")
                .append("++++++++\n");

        Assertions.assertFalse(testTruck.tryPlacePackage(testCargoPackage));
        Assertions.assertEquals(expectedLoadingStructure.toString(), testTruck.toString());
    }

    @Test
    void tryPlacePackage9AbovePackage5IsPossible() {
        Truck testTruck = new Truck();
        CargoPackage testCargoPackage = new CargoPackage('9', 3, 3);
        testTruck.tryPlacePackage(new CargoPackage('5', 5, 1));
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+55555 +\n")
                .append("++++++++\n");

        Assertions.assertTrue(testTruck.tryPlacePackage(testCargoPackage));
        Assertions.assertEquals(expectedLoadingStructure.toString(), testTruck.toString());
    }

    @Test
    void tryPlacePackage5AbovePackage2NotPossible() {
        Truck testTruck = new Truck();
        CargoPackage testCargoPackage = new CargoPackage('5', 5, 1);
        testTruck.tryPlacePackage(new CargoPackage('2', 2, 1));
        StringBuilder incorrectLoadingStructure = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+55555 +\n")
                .append("+22    +\n")
                .append("++++++++\n");

        Assertions.assertFalse(testTruck.tryPlacePackage(testCargoPackage));
        Assertions.assertNotEquals(incorrectLoadingStructure.toString(), testTruck.toString());
    }

    @Test
    void tryPlacePackage5NearPackage1() {
        Truck testTruck = new Truck();
        CargoPackage testCargoPackage = new CargoPackage('5', 5, 1);
        testTruck.tryPlacePackage(new CargoPackage('1', 1, 1));
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+155555+\n")
                .append("++++++++\n");

        Assertions.assertTrue(testTruck.tryPlacePackage(testCargoPackage));
        Assertions.assertEquals(expectedLoadingStructure.toString(), testTruck.toString());
    }
}
