package ru.hofftech.packingdeliveries.service.command.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;

/**
 * Базовый класс для всех Telegram-команд бота приложения.
 * <p>
 * Расширяет возможности стандартного {@link BotCommand}, связывая его с внутренним
 * перечислением {@link CommandProcessorType} и предоставляя вспомогательный метод
 * для отправки текстовых ответов в чат.
 */
abstract class TelegramCommand extends BotCommand {
    private static final Logger log = LoggerFactory.getLogger(TelegramCommand.class);

    /**
     * Создает объект команды для регистрации в боте.
     *
     * @param commandType тип процессора команды, определяющий её строковый идентификатор
     * @param description краткое описание функции команды для меню подсказок Telegram
     */
    public TelegramCommand(CommandProcessorType commandType, String description) {
        super(commandType.toString(), description);
    }

    /**
     * Формирует и отправляет текстовое сообщение пользователю в ответ на команду.
     * <p>
     * В случае возникновения ошибки при взаимодействии с API Telegram,
     * информация о ней записывается в лог.
     *
     * @param absSender     интерфейс для отправки сообщения (исполнитель)
     * @param user          пользователь, инициировавший запрос
     * @param chat          объект чата, в который необходимо направить ответ
     * @param answerMessage текст ответного сообщения
     */
    protected void sendResponse(AbsSender absSender, User user, Chat chat, String answerMessage) {
        SendMessage message =
                SendMessage.builder().chatId(chat.getId()).text(answerMessage).build();

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения пользователю при выполнении команды");
        }
    }
}
