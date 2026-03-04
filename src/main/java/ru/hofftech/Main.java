package ru.hofftech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.PackagerFactory;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.Packager;

import java.util.ArrayList;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length == 2){
            String packagesFileName = args[0];
            String packingMethod = args[1];
            try {
                Packager packager = new PackagerFactory().construct(packingMethod);
                ArrayList<CargoPackage> packagesFromFile = new PackageReader().readFromFile(packagesFileName);
                packager.doPacking(packagesFromFile);
                packager.showPackingResults();
            }
            catch (IllegalArgumentException exception)
            {
                log.error("Метод упаковки {} не поддерживается!", packingMethod);
            }
        }
        else {
            log.error("Необходимо указать путь к файлу с посылками и методом упаковки в грузовики!");
        }
    }
}