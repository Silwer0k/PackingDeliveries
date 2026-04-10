package ru.hofftech.packingdeliveries.service.command.console;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.command.params.PackingParams;
import ru.hofftech.packingdeliveries.service.output.ConsoleOutput;
import ru.hofftech.packingdeliveries.service.output.JsonFileOutput;
import ru.hofftech.packingdeliveries.service.processors.Packing;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда консольного интерфейса для запуска процесса упаковки посылок в грузовики.
 * <p>
 * Класс поддерживает чтение списка посылок из различных источников (файл или текст),
 * инициирует алгоритм упаковки и выводит результат в выбранном формате (консоль или JSON-файл).
 */
public class PackingConsoleCommand extends ConsoleCommand {
    private static final Logger log = LoggerFactory.getLogger(PackingConsoleCommand.class);
    private final CargoPackageRepository packageRepository;

    /**
     * Создает экземпляр команды упаковки.
     *
     * @param packageRepository репозиторий с данными о посылках
     * @param commandParams параметры упаковки, включая источник данных, количество машин, метод упаковки и метод вывода результата
     */
    public PackingConsoleCommand(CargoPackageRepository packageRepository, PackingParams commandParams) {
        super(commandParams);
        this.packageRepository = packageRepository;
    }

    /**
     * Выполняет полный цикл процесса упаковки.
     * <p>
     * Логика выполнения включает:
     * 1. Чтение имен посылок через {@link PackageReader} в зависимости от типа входных данных.
     * 2. Валидацию полученного списка имен.
     * 3. Запуск бизнес-логики упаковки через класс {@link Packing}.
     * 4. Вывод результата через {@link ConsoleOutput} или {@link JsonFileOutput} согласно параметрам.
     */
    public void execute() {
        PackingParams params = getParams();
        PackageReader packageReader = new PackageReader();

        List<String> packagesNameToPack = packageReader.readFromPackingParams(params);
        if (packagesNameToPack.isEmpty()) {
            log.error("Список посылок для упаковки пуст, упаковка не выполнена");
            return;
        }
        Packing packing = new Packing(
                packageRepository, packagesNameToPack, params.getCountTrucksToUse(), params.getPackagingMethodName());
        packing.doProcess();

        if (packing.isSuccess()) {
            switch (params.getOutputDataType()) {
                case JSONFILE -> {
                    new JsonFileOutput(params.getOutputFilename()).doOutput(packing.getResultTrucks());
                    log.info("Результаты упаковки выгружены в json файл");
                }
                case TEXT -> {
                    new ConsoleOutput().doOutput(packing.getResultTrucks());
                }
            }
        }
    }
}
