package ru.hofftech.packingdeliveries.service.command.telegram;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;
import ru.hofftech.packingdeliveries.service.PackageReader;
import ru.hofftech.packingdeliveries.service.command.params.PackingParams;
import ru.hofftech.packingdeliveries.service.output.JsonFileOutput;
import ru.hofftech.packingdeliveries.service.output.TelegramOutput;
import ru.hofftech.packingdeliveries.service.processors.Packing;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда Telegram для запуска процесса упаковки посылок в грузовики.
 * <p>
 * Команда выполняет полный цикл обработки:
 * <ol>
 *     <li>Парсинг и валидация параметров через {@link PackingParams}.</li>
 *     <li>Считывание списка посылок из файла или текста через {@link PackageReader}.</li>
 *     <li>Инициализация и запуск бизнес-процесса {@link Packing} с выбранной стратегией.</li>
 *     <li>Вывод результата в чат Telegram или экспорт в JSON-файл.</li>
 * </ol>
 */
public class PackingTelegramCommand extends TelegramCommand {
    private static final Logger log = LoggerFactory.getLogger(PackingTelegramCommand.class);
    private final CargoPackageRepository packageRepository;
    private final PackageReader packageReader;

    public PackingTelegramCommand(CargoPackageRepository packageRepository) {
        super(CommandProcessorType.PACKING, "Упаковка посылок в грузовики");
        this.packageRepository = packageRepository;
        packageReader = new PackageReader();
    }

    /**
     * Выполняет многошаговый процесс упаковки на основе команд пользователя.
     * <p>
     * Особенности реализации:
     * <ul>
     *     <li>Поддерживает ввод имен посылок через разделитель {@code \n} или из внешнего файла.</li>
     *     <li>Взаимодействует с {@link JsonFileOutput} для сохранения состояния кузовов.</li>
     *     <li>Использует {@link TelegramOutput} для отрисовки графических схем грузовиков в чате.</li>
     *     <li>Аккумулирует и выводит ошибки парсинга, чтения и процесса упаковки.</li>
     * </ul>
     *
     * @param absSender интерфейс для отправки ответов и файлов
     * @param user      пользователь, вызвавший команду
     * @param chat      объект чата
     * @param arguments массив аргументов (флаги ввода, количество машин, метод, флаги вывода)
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Вызов команды с параметрами {}", String.join(" ", arguments));
        PackingParams params = new PackingParams(String.join(" ", arguments));
        if (!params.validate()) {
            sendResponse(absSender, user, chat, "Ошибка валидации входных параметров");
            return;
        }

        List<String> packagesNameToPack = packageReader.readFromPackingParams(params);
        if (packagesNameToPack.isEmpty()) {
            log.error("Список посылок для упаковки пуст, упаковка не выполнена");
            sendResponse(absSender, user, chat, "Список посылок для упаковки пуст, упаковка не выполнена");
            return;
        }

        Packing packingProcessor = new Packing(
                packageRepository, packagesNameToPack, params.getCountTrucksToUse(), params.getPackagingMethodName());
        packingProcessor.doProcess();

        List<String> errors = new ArrayList<>(packingProcessor.getProcessErrors());
        if (packingProcessor.isSuccess()) {
            switch (params.getOutputDataType()) {
                case JSONFILE -> {
                    JsonFileOutput jsonFileOutput = new JsonFileOutput(params.getOutputFilename());
                    if (jsonFileOutput.doOutput(packingProcessor.getResultTrucks())) {
                        sendResponse(
                                absSender,
                                user,
                                chat,
                                String.format(
                                        "Результаты упаковки выгружены в json файл %s", jsonFileOutput.getFileName()));
                    } else {
                        sendResponse(
                                absSender,
                                user,
                                chat,
                                String.format(
                                        "Ошибка выгрузки результатов упаковки в json файл %s",
                                        jsonFileOutput.getFileName()));
                    }
                }
                case TEXT -> new TelegramOutput(absSender, chat).doOutput(packingProcessor.getResultTrucks());
            }
        } else {
            errors.addFirst(String.format("Ошибка выполнения команды %s", getCommandIdentifier()));
        }
        if (!errors.isEmpty()) {
            sendResponse(absSender, user, chat, String.join("\n", errors));
        }
    }
}
