package ru.hofftech.packingdeliveries.service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.hofftech.packingdeliveries.model.CargoPackage;

class CargoPackageRepositoryTest {
    @Test
    void initDefaultRepoContainsAllDefaultPackages() {
        CargoPackageRepository testRepo = new CargoPackageRepository();
        List<CargoPackage> allDefaultPackages = List.of(
                new CargoPackage('1', 1, 1),
                new CargoPackage('2', 2, 1),
                new CargoPackage('3', 3, 1),
                new CargoPackage('4', 4, 1),
                new CargoPackage('5', 5, 1),
                new CargoPackage('6', 3, 2),
                new CargoPackage('7', 3, 2),
                new CargoPackage('8', 4, 2),
                new CargoPackage('9', 3, 3));

        assertThat(testRepo.packagesRepo.values()).containsExactlyInAnyOrderElementsOf(allDefaultPackages);
    }
}
