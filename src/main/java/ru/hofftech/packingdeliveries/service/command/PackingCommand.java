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
    private final Packager packager;
    private final PackageReader packageReader;

    public PackingCommand(String[] args) {
        if (validate(args)) {
            packagesFileName = args[1];
            String packingMethodName = args[2];
            packageReader = new PackageReader();
            try {
                packager = new PackagerFactory().construct(packingMethodName, Integer.parseInt(args[3]));
            } catch (IllegalArgumentException exception) {
                log.error("Метод упаковки {} не поддерживается!", packingMethodName);
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void doProcess() {
        ArrayList<CargoPackage> packagesFromFile = packageReader.readFromFile(packagesFileName);
        if (packager.doPacking(packagesFromFile)) {
            packager.packingResultToConsole();
            packager.packingResultToJsonFile();
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
