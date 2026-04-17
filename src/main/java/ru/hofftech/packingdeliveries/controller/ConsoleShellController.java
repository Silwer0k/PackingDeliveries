package ru.hofftech.packingdeliveries.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import ru.hofftech.packingdeliveries.model.enums.PackingInputDataType;
import ru.hofftech.packingdeliveries.model.enums.PackingOutputDataType;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.output.JsonFileOutput;
import ru.hofftech.packingdeliveries.service.processors.Packing;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

public class ConsoleShellController {
    private static final Logger log = LoggerFactory.getLogger(ConsoleShellController.class);
    private final CargoPackageRepository packageRepository;

    public ConsoleShellController(CargoPackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Command(
            description =
                    "Выполнение упаковки заданых посылок в заданое кол-во грузовиков по определенному методу упаковки")
    public void packing(
            @Option(longName = "parcels-file", defaultValue = "") String packagesFilename,
            @Option(longName = "parcels-text", defaultValue = "") String packagesNamesStr,
            @Option(longName = "trucks", required = true) int countTrucksToUse,
            @Option(longName = "type", shortName = 't', required = true) String packingMethodName,
            @Option(longName = "out", shortName = 'o', required = true) PackingOutputDataType outputType,
            @Option(longName = "out-filename", defaultValue = "") String outputFilename) {
        PackingInputDataType inputDataType;
        if (!packagesFilename.isEmpty()) {
            inputDataType = PackingInputDataType.FILE;
        } else if (!packagesNamesStr.isEmpty()) {
            inputDataType = PackingInputDataType.TEXT;
        } else {
            log.error(
                    "Необходимо передать либо файл с посылками (-parcels-file), либо имена посылок разделенные \\n (-parcels-text)");
            return;
        }

        List<String> packagesNamesToPack;
        PackageReader packageReader = new PackageReader();
        switch (inputDataType) {
            case FILE -> {
                packagesNamesToPack = packageReader.readFromFile(packagesFilename);
            }
            case TEXT -> {
                packagesNamesToPack = packageReader.readFromText(packagesNamesStr);
            }
            default -> throw new IllegalArgumentException();
        }

        Packing packing = new Packing(packageRepository, packagesNamesToPack, countTrucksToUse, packingMethodName);
        packing.doProcess();
        if (packing.isSuccess()) {
            switch (outputType) {
                case JSONFILE -> {
                    new JsonFileOutput(outputFilename).doOutput(packing.getResultTrucks());
                }
                case TEXT -> {
                    new ConsoleOutput().doOutput(packing.getResultTrucks());
                }
            }
        } else {
            log.warn("Ошибка при выполнении упаковки в грузовики!");
        }
    }
}
