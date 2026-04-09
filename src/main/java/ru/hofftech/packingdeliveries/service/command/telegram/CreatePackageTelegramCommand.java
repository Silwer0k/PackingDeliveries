package ru.hofftech.packingdeliveries.service.command.telegram;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;
import ru.hofftech.packingdeliveries.service.command.params.CreatePackageParams;
import ru.hofftech.packingdeliveries.service.output.TelegramOutput;
import ru.hofftech.packingdeliveries.service.processors.CreatePackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда Telegram-бота для создания новой посылки в системе.
 * <p>
 * Класс обрабатывает входящее сообщение, извлекает аргументы для {@link CreatePackageParams}
 * и инициирует бизнес-процесс {@link CreatePackage}. Результат выполнения (успех или ошибки)
 * отправляется обратно пользователю в чат.
 */
public class CreatePackageTelegramCommand extends TelegramCommand {
    private final CargoPackageRepository packageRepository;

    public CreatePackageTelegramCommand(CargoPackageRepository packageRepository) {
        super(CommandProcessorType.CREATE, "Создание посылки");
        this.packageRepository = packageRepository;
    }

    /**
     * Основной метод выполнения команды при получении сообщения из Telegram.
     * <p>
     * Процесс выполнения:
     * <ol>
     *     <li>Объединение аргументов в строку и валидация через {@link CreatePackageParams}.</li>
     *     <li>Запуск бизнес-процесса {@link CreatePackage}.</li>
     *     <li>При успехе: вывод данных новой посылки через {@link TelegramOutput}.</li>
     *     <li>При ошибке: отправка списка причин неудачного выполнения пользователю.</li>
     * </ol>
     *
     * @param absSender объект для отправки ответных сообщений
     * @param user      пользователь, инициировавший команду
     * @param chat      чат, в котором была вызвана команда
     * @param arguments массив строк с параметрами (name, form, symbol)
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        CreatePackageParams params = new CreatePackageParams(String.join(" ", arguments));
        if (!params.validate()) {
            sendResponse(absSender, user, chat, "Ошибка валидации входных параметров");
            return;
        }

        CreatePackage createPackage = new CreatePackage(
                packageRepository, params.getPackageName(), params.getPackageForm(), params.getPackageMarker());
        createPackage.doProcess();
        if (createPackage.isSuccess()) {
            new TelegramOutput(absSender, chat).doOutput(List.of(createPackage.getNewPackage()));
        } else {
            List<String> errors = createPackage.getProcessErrors();
            errors.addFirst(String.format("Ошибка выполнения команды %s", getCommandIdentifier()));
            sendResponse(absSender, user, chat, String.join("\n", errors));
        }
    }
}
