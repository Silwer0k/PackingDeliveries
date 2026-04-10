package ru.hofftech.packingdeliveries.service.command.console;

import ru.hofftech.packingdeliveries.service.command.params.UnpackingParams;
import ru.hofftech.packingdeliveries.service.output.FileOutput;
import ru.hofftech.packingdeliveries.service.processors.Unpacking;

/**
 * Команда консольного интерфейса для извлечения посылок из файла с результатами упаковки.
 * <p>
 * Класс считывает данные о ранее упакованных грузовиках из указанного файла,
 * получает список посылок и сохраняет их в новый текстовый файл.
 */
public class UnpackingConsoleCommand extends ConsoleCommand {
    /**
     * Создает экземпляр команды распаковки.
     *
     * @param commandParams параметры распаковки, содержащие пути к входному и выходному файлам
     */
    public UnpackingConsoleCommand(UnpackingParams commandParams) {
        super(commandParams);
    }

    /**
     * Исполняет логику распаковки.
     * <p>
     * Инициирует процесс через класс {@link Unpacking}, используя путь к файлу из параметров.
     * В случае успеха передает полученный список посылок в {@link FileOutput}
     * для записи в результирующий файл.
     */
    public void execute() {
        UnpackingParams params = getParams();
        Unpacking unpacking = new Unpacking(params.getInFilename());
        unpacking.doProcess();
        if (unpacking.isSuccess()) {
            new FileOutput(params.getOutFilename()).doOutput(unpacking.getResultPackages());
        }
    }
}
