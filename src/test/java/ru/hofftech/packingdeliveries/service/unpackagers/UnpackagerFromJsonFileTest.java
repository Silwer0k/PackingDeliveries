package ru.hofftech.packingdeliveries.service.unpackagers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnpackagerFromJsonFileTest {
    @Test
    void doUnpackingNotExistingFileNotThrowException() {
        UnpackagerFromJsonFile testUnpackager = new UnpackagerFromJsonFile("not_existing_file.txt");

        Assertions.assertDoesNotThrow(() -> {
            boolean factResult;
            factResult = testUnpackager.doUnpacking();
            Assertions.assertFalse(factResult);
        });
    }

    @Test
    void doUnpackingIncorrectJsonStructNotThrowException() {
        UnpackagerFromJsonFile testUnpackager =
                new UnpackagerFromJsonFile("src/test/resources/testFiles/unpackagers/incorrect_json_struct.txt");

        Assertions.assertDoesNotThrow(() -> {
            boolean factResult;
            factResult = testUnpackager.doUnpacking();
            Assertions.assertFalse(factResult);
        });
    }

    @Test
    void doUnpackingEmptyFileNotThrowException() {
        UnpackagerFromJsonFile testUnpackager =
                new UnpackagerFromJsonFile("src/test/resources/testFiles/unpackagers/empty_json.txt");

        Assertions.assertDoesNotThrow(() -> {
            boolean factResult;
            factResult = testUnpackager.doUnpacking();
            Assertions.assertFalse(factResult);
        });
    }

    @Test
    void doUnpackingEmptyListOfTruckIsNoUnpackedPackages() {
        boolean factResult;
        UnpackagerFromJsonFile testUnpackager =
                new UnpackagerFromJsonFile("src/test/resources/testFiles/unpackagers/empty_trucks_list.txt");

        factResult = testUnpackager.doUnpacking();

        Assertions.assertTrue(factResult);
        Assertions.assertEquals(0, testUnpackager.unpackedPackages.size());
    }

    @Test
    void doUnpackingEmptyTruckIsNotUnpackedPackages() {
        boolean factResult;
        UnpackagerFromJsonFile testUnpackager =
                new UnpackagerFromJsonFile("src/test/resources/testFiles/unpackagers/truck_without_packages.txt");

        factResult = testUnpackager.doUnpacking();

        Assertions.assertTrue(factResult);
        Assertions.assertEquals(0, testUnpackager.unpackedPackages.size());
    }

    @Test
    void doUnpackingOneTruckWith3PackagesIs3UnpackedPackages() {
        boolean factResult;
        UnpackagerFromJsonFile testUnpackager =
                new UnpackagerFromJsonFile("src/test/resources/testFiles/unpackagers/one_truck_3Packages.txt");

        factResult = testUnpackager.doUnpacking();

        Assertions.assertTrue(factResult);
        Assertions.assertEquals(3, testUnpackager.unpackedPackages.size());
    }

    @Test
    void doUnpacking3TrucksWith3PackagesIs9UnloadedPackages() {
        boolean factResult;
        UnpackagerFromJsonFile testUnpackager =
                new UnpackagerFromJsonFile("src/test/resources/testFiles/unpackagers/three_trucks.txt");

        factResult = testUnpackager.doUnpacking();

        Assertions.assertTrue(factResult);
        Assertions.assertEquals(9, testUnpackager.unpackedPackages.size());
    }
}
