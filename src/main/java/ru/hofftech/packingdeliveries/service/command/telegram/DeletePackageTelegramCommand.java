package ru.hofftech.packingdeliveries.service.command.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;
import ru.hofftech.packingdeliveries.service.command.params.DeletePackageParams;
import ru.hofftech.packingdeliveries.service.processors.DeletePackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда Telegram для удаления посылки из системы по её имени.
 * <p>
 * Класс извлекает параметры из сообщения с помощью {@link DeletePackageParams}
 * и запускает процесс {@link DeletePackage}. В случае успеха или отсутствия
 * объекта в базе пользователь получает соответствующее уведомление.
 */
public class DeletePackageTelegramCommand extends TelegramCommand {
    private static final Logger log = LoggerFactory.getLogger(DeletePackageTelegramCommand.class);
    private final CargoPackageRepository packageRepository;

    public DeletePackageTelegramCommand(CargoPackageRepository packageRepository) {
        super(CommandProcessorType.DELETE, "Удаление посылки");
        this.packageRepository = packageRepository;
    }

    /**
     * Выполняет процедуру удаления при получении команды от пользователя.
     * <p>
     * Шаги выполнения:
     * <ol>
     *     <li>Парсинг аргументов и поиск имени посылки в кавычках.</li>
     *     <li>Валидация входной строки.</li>
     *     <li>Вызов бизнес-процесса удаления.</li>
     *     <li>Отправка текстового подтверждения или сообщения об ошибке (если посылка не найдена).</li>
     * </ol>
     *
     * @param absSender интерфейс для отправки ответа
     * @param user      пользователь Telegram
     * @param chat      объект чата
     * @param arguments массив строк, содержащий имя посылки
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Вызов команды с параметрами {}", String.join(" ", arguments));
        DeletePackageParams params = new DeletePackageParams(String.join(" ", arguments));
        if (!params.validate()) {
            sendResponse(absSender, user, chat, "Ошибка валидации входных параметров");
            return;
        }

        DeletePackage deletePackage = new DeletePackage(packageRepository, params.getPackageNameToDelete());
        deletePackage.doProcess();
        if (deletePackage.isSuccess()) {
            sendResponse(
                    absSender, user, chat, String.format("Посылка \"%s\" удалена", params.getPackageNameToDelete()));
        } else {
            sendResponse(
                    absSender,
                    user,
                    chat,
                    String.format(
                            "Посылка \"%s\" не удалена так как посылки не существует",
                            params.getPackageNameToDelete()));
        }
    }
}
