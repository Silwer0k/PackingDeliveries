package ru.hofftech.packingdeliveries.service.command;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.packagers.Packager;
import ru.hofftech.packingdeliveries.service.packagers.PackagerFactory;

public class PackingCommand implements CommandProcessor {
    private static final Logger log = LoggerFactory.getLogger(PackingCommand.class);
    private final String packagesFileName;
    private final String packingMethodName;
    private final int countOfTruckToUse;

    public PackingCommand(String[] args) {
        if (validate(args)) {
            packagesFileName = args[1];
            packingMethodName = args[2];
            countOfTruckToUse = Integer.parseInt(args[3]);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void doProccess() {
        try {
            Packager packager = new PackagerFactory().construct(packingMethodName, countOfTruckToUse);
            ArrayList<CargoPackage> packagesFromFile = new PackageReader().readFromFile(packagesFileName);
            if (packager.doPacking(packagesFromFile)) {
                packager.packingResultToConsole();
                packager.packingResultToJsonFile();
            }
        } catch (IllegalArgumentException exception) {
            log.error("Метод упаковки {} не поддерживается!", packingMethodName);
        }
    }

    @Override
    public boolean validate(String[] args) {
        if (args.length != 4) {
            return false;
        }
        try {
            Integer.parseInt(args[3]);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
}
