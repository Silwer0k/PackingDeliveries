package ru.hofftech.packingdeliveries.service.command.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;
import ru.hofftech.packingdeliveries.service.command.params.UnpackingParams;
import ru.hofftech.packingdeliveries.service.output.FileOutput;
import ru.hofftech.packingdeliveries.service.processors.Unpacking;

/**
 * Команда Telegram для процесса распаковки грузовиков.
 * <p>
 * Считывает состояние загрузки из JSON-файла, восстанавливает объекты посылок
 * и выгружает их список в указанный текстовый файл.
 */
public class UnpackingTelegramCommand extends TelegramCommand {
    private static final Logger log = LoggerFactory.getLogger(UnpackingTelegramCommand.class);

    public UnpackingTelegramCommand() {
        super(CommandProcessorType.UNPACKING, "Распаковка грузовиков");
    }

    /**
     * Выполняет чтение данных о загрузке и экспорт списка посылок.
     * <p>
     * Процесс выполнения:
     * <ol>
     *     <li>Парсинг путей к входному и выходному файлам через {@link UnpackingParams}.</li>
     *     <li>Запуск процесса восстановления данных через {@link Unpacking}.</li>
     *     <li>При успехе: запись списка извлеченных посылок в файл через {@link FileOutput}.</li>
     *     <li>Информирование пользователя о результате операции или возникших ошибках.</li>
     * </ol>
     *
     * @param absSender интерфейс для отправки ответов
     * @param user      пользователь Telegram
     * @param chat      объект чата
     * @param arguments массив строк, содержащий флаги -infile и -outfile с путями
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Вызов команды с параметрами {}", String.join(" ", arguments));

        UnpackingParams params = new UnpackingParams(String.join(" ", arguments));
        if (!params.validate()) {
            sendResponse(absSender, user, chat, "Ошибка валидации входных параметров");
            return;
        }

        Unpacking unpacking = new Unpacking(params.getInFilename());
        unpacking.doProcess();
        if (unpacking.isSuccess()) {
            FileOutput fileOutput = new FileOutput(params.getOutFilename());
            if (fileOutput.doOutput(unpacking.getResultPackages())) {
                sendResponse(
                        absSender,
                        user,
                        chat,
                        String.format("Результаты распаковки выгружены в файл %s", fileOutput.getFileName()));
            } else {
                sendResponse(
                        absSender,
                        user,
                        chat,
                        String.format("Ошибка выгрузки результатов упаковки в файл %s", fileOutput.getFileName()));
            }
        } else {
            sendResponse(absSender, user, chat, String.format("Ошибка выполнения команды %s", getCommandIdentifier()));
        }
    }
}
