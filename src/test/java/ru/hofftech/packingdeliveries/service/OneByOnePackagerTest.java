package ru.hofftech.packingdeliveries.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hofftech.packingdeliveries.model.CargoPackage;

class OneByOnePackagerTest {

    @Test
    void doPacking4PackagesIn4Trucks() {
        OneByOnePackager testPackager = new OneByOnePackager();
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

        testPackager.doPacking(testPackages);

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
}
