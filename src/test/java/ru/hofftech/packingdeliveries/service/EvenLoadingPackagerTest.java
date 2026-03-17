package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hofftech.packingdeliveries.model.CargoPackage;

class EvenLoadingPackagerTest {

    @Test
    void doPacking4PackagesInOneTruck() {
        boolean factPackingResult;
        EvenLoadingPackager testPackager = new EvenLoadingPackager(1);
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('2', 2, 1),
                new CargoPackage('9', 3, 3),
                new CargoPackage('5', 5, 1),
                new CargoPackage('8', 4, 2)));

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertTrue(factPackingResult);
        Assertions.assertEquals(1, testPackager.trucks.size());
    }

    @Test
    void doPacking6PackagesOf9SeperatedIn2Trucks() {
        boolean factPackingResult;
        EvenLoadingPackager testPackager = new EvenLoadingPackager(2);
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('9', 3, 3),
                new CargoPackage('9', 3, 3),
                new CargoPackage('9', 3, 3),
                new CargoPackage('9', 3, 3),
                new CargoPackage('9', 3, 3),
                new CargoPackage('9', 3, 3)));
        StringBuilder expectedLoadingStructureTruck = new StringBuilder()
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+999999+\n")
                .append("+999999+\n")
                .append("+999999+\n")
                .append("++++++++\n");

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertTrue(factPackingResult);
        Assertions.assertEquals(2, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructureTruck.toString(),
                testPackager.trucks.getFirst().toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck.toString(),
                testPackager.trucks.getLast().toString());
    }

    @Test
    void doPacking5PackagesOf8NotFitIn2Trucks() {
        boolean factPackingResult;
        EvenLoadingPackager testPackager = new EvenLoadingPackager(2);
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('8', 4, 2),
                new CargoPackage('8', 4, 2),
                new CargoPackage('8', 4, 2),
                new CargoPackage('8', 4, 2),
                new CargoPackage('8', 4, 2)));
        StringBuilder expectedLoadingStructureTruck = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+8888  +\n")
                .append("+8888  +\n")
                .append("+8888  +\n")
                .append("+8888  +\n")
                .append("++++++++\n");

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertFalse(factPackingResult);
        Assertions.assertEquals(2, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructureTruck.toString(),
                testPackager.trucks.getFirst().toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck.toString(),
                testPackager.trucks.getLast().toString());
    }
}
