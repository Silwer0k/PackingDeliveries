package ru.hofftech;

import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.OneByOnePackager;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.Packager;
import ru.hofftech.packingdeliveries.service.SmartPackager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (args.length != 0){
            String packagesFileName = args[0];
            ArrayList<CargoPackage> packagesFromFile = new PackageReader().readFromFile(packagesFileName);

            Packager oneByOnePackager = new OneByOnePackager();
            oneByOnePackager.doPacking(packagesFromFile);
            oneByOnePackager.showPackingResults();

            Packager smartPackager = new SmartPackager();
            smartPackager.doPacking(packagesFromFile);
            smartPackager.showPackingResults();
        }
        else {
            System.out.println("Не указан путь к файлу с посылками для упаковки!");
        }
    }
}