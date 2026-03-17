package ru.hofftech;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.PackagerFactory;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.Packager;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length == 3) {
            String packagesFileName = args[0];
            String packingMethod = args[1];
            int countOfTrucksToUser = Integer.parseInt(args[2]);
            try {
                Packager packager = new PackagerFactory().construct(packingMethod, countOfTrucksToUser);
                ArrayList<CargoPackage> packagesFromFile = new PackageReader().readFromFile(packagesFileName);
                packager.doPacking(packagesFromFile);
                packager.showPackingResults();
            } catch (IllegalArgumentException exception) {
                log.error("Метод упаковки {} не поддерживается!", packingMethod);
            }
        } else {
            log.error(
                    "Необходимо указать путь к файлу с посылками, методом упаковки в грузовики и количество грузовиков!");
        }
    }
}
