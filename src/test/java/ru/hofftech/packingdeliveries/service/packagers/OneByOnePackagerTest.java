package ru.hofftech.packingdeliveries.service.packagers;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hofftech.packingdeliveries.model.CargoPackage;

class OneByOnePackagerTest {

    @Test
    void doPacking4PackagesIn4Trucks() {
        boolean factPackingResult;
        OneByOnePackager testPackager = new OneByOnePackager(4);
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('2', 2, 1),
                new CargoPackage('9', 3, 3),
                new CargoPackage('5', 5, 1),
                new CargoPackage('8', 4, 2)));
        StringBuilder expectedLoadingStructureTruck1 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+22    +\n")
                .append("++++++++\n");
        StringBuilder expectedLoadingStructureTruck2 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("++++++++\n");
        StringBuilder expectedLoadingStructureTruck3 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+55555 +\n")
                .append("++++++++\n");
        StringBuilder expectedLoadingStructureTruck4 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+8888  +\n")
                .append("+8888  +\n")
                .append("++++++++\n");

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertTrue(factPackingResult);
        Assertions.assertEquals(4, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructureTruck1.toString(),
                testPackager.trucks.getFirst().toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck2.toString(),
                testPackager.trucks.get(1).toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck3.toString(),
                testPackager.trucks.get(2).toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck4.toString(),
                testPackager.trucks.get(3).toString());
    }

    @Test
    void doPacking4PackagesIn2TrucksNotThrowOutOfBoundException() {
        boolean factPackingResult;
        OneByOnePackager testPackager = new OneByOnePackager(2);
        ArrayList<CargoPackage> testPackages = new ArrayList<>(List.of(
                new CargoPackage('2', 2, 1),
                new CargoPackage('9', 3, 3),
                new CargoPackage('5', 5, 1),
                new CargoPackage('8', 4, 2)));
        StringBuilder expectedLoadingStructureTruck1 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+22    +\n")
                .append("++++++++\n");
        StringBuilder expectedLoadingStructureTruck2 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("+999   +\n")
                .append("++++++++\n");

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertFalse(factPackingResult);
        Assertions.assertEquals(2, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructureTruck1.toString(),
                testPackager.trucks.getFirst().toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck2.toString(),
                testPackager.trucks.get(1).toString());
    }

    @Test
    void doPacking3PackagesIn4TruckIsOKAndLastIsEmpty() {
        boolean factPackingResult;
        OneByOnePackager testPackager = new OneByOnePackager(4);
        ArrayList<CargoPackage> testPackages = new ArrayList<>(
                List.of(new CargoPackage('4', 4, 1), new CargoPackage('3', 3, 1), new CargoPackage('1', 1, 1)));
        StringBuilder expectedLoadingStructureTruck1 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+4444  +\n")
                .append("++++++++\n");
        StringBuilder expectedLoadingStructureTruck2 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+333   +\n")
                .append("++++++++\n");
        StringBuilder expectedLoadingStructureTruck3 = new StringBuilder()
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+      +\n")
                .append("+1     +\n")
                .append("++++++++\n");

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertTrue(factPackingResult);
        Assertions.assertEquals(4, testPackager.trucks.size());
        Assertions.assertEquals(
                expectedLoadingStructureTruck1.toString(),
                testPackager.trucks.getFirst().toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck2.toString(),
                testPackager.trucks.get(1).toString());
        Assertions.assertEquals(
                expectedLoadingStructureTruck3.toString(),
                testPackager.trucks.get(2).toString());
        Assertions.assertEquals(0, testPackager.trucks.getLast().getLoadedVolume());
    }

    @Test
    void doPacking0PackagesToTrucksIsOkAndAllEmpty() {
        boolean factPackingResult;
        OneByOnePackager testPackager = new OneByOnePackager(4);
        ArrayList<CargoPackage> testPackages = new ArrayList<>();

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertTrue(factPackingResult);
        Assertions.assertEquals(4, testPackager.trucks.size());
        testPackager.trucks.forEach(truck -> Assertions.assertEquals(0, truck.getLoadedVolume()));
    }

    @Test
    void doPacking2PackagesTo0TrucksNotThrowExceptions() {
        boolean factPackingResult;
        OneByOnePackager testPackager = new OneByOnePackager(0);
        ArrayList<CargoPackage> testPackages =
                new ArrayList<>(List.of(new CargoPackage('4', 4, 1), new CargoPackage('3', 3, 1)));

        factPackingResult = testPackager.doPacking(testPackages);

        Assertions.assertFalse(factPackingResult);
        Assertions.assertEquals(0, testPackager.trucks.size());
    }
}
