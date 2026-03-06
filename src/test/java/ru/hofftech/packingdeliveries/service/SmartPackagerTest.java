package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hofftech.packingdeliveries.model.CargoPackage;

class SmartPackagerTest {

    @Test
    void doPacking4PackagesInOneTruck() {
        SmartPackager testPackager = new SmartPackager();
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('2', 2, 1),
                new CargoPackage('9', 3, 3),
                new CargoPackage('5', 5, 1),
                new CargoPackage('8', 4, 2)));
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+55555 +\n")
                .append("+8888  +\n")
                .append("+8888  +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+99922 +\n")
                .append("++++++++\n");

        testPackager.doPacking(testPackages);

        Assertions.assertEquals(1, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructure.toString(),
                testPackager.trucks.getFirst().toString());
    }

    @Test
    void doPacking1PackageInTruck() {
        SmartPackager testPackager = new SmartPackager();
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(new CargoPackage('5', 5, 1)));
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+55555 +\n")
                .append("++++++++\n");

        testPackager.doPacking(testPackages);

        Assertions.assertEquals(1, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructure.toString(),
                testPackager.trucks.getFirst().toString());
    }

    @Test
    void doPacking5Packages5AboveEachOtherInOneTruck() {
        SmartPackager testPackager = new SmartPackager();
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('5', 5, 1),
                new CargoPackage('5', 5, 1),
                new CargoPackage('5', 5, 1),
                new CargoPackage('5', 5, 1),
                new CargoPackage('5', 5, 1)));
        StringBuilder expectedLoadingStructure = new StringBuilder()
                .append("+      +\n")
                .append("+55555 +\n")
                .append("+55555 +\n")
                .append("+55555 +\n")
                .append("+55555 +\n")
                .append("+55555 +\n")
                .append("++++++++\n");

        testPackager.doPacking(testPackages);

        Assertions.assertEquals(1, testPackager.trucks.size());
        Assertions.assertEquals(testPackager.trucks.getFirst().toString(), expectedLoadingStructure.toString());
    }

    @Test
    void doPackingEmptyListOfPackagesIsNoTrucks() {
        SmartPackager testPackager = new SmartPackager();
        ArrayList<CargoPackage> emptyListOfPackages = new ArrayList<>();

        testPackager.doPacking(emptyListOfPackages);

        Assertions.assertTrue(testPackager.trucks.isEmpty());
    }
}
