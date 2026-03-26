package ru.hofftech.packingdeliveries.service.packagers;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.output.JsonFileOutput;

public abstract class Packager {
    private final String packingResultFileName = "packing_result";
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final ArrayList<Truck> trucks = new ArrayList<>();

    public Packager(int countOfTrucksToUse) {
        for (int i = 0; i < countOfTrucksToUse; i++) {
            trucks.add(new Truck());
        }
    }

    public void packingResultToConsole() {
        new ConsoleOutput().doOutput(trucks);
    }

    public void packingResultToJsonFile() {
        JsonFileOutput jsonFileOutput = new JsonFileOutput(packingResultFileName);
        if (jsonFileOutput.doOutput(trucks)) {
            log.info("Результаты упаковки выгружены в файл {}", jsonFileOutput.getFileName());
        } else {
            log.error("Ошибка выгрузки результатов упаковки в файл!");
        }
    }

    public abstract boolean doPacking(ArrayList<CargoPackage> packagesToPack);
}
