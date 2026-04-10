package ru.hofftech.packingdeliveries.service.output;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hofftech.packingdeliveries.model.Outputable;

/**
 * Стратегия вывода данных в чат Telegram.
 * <p>
 * Формирует единое текстовое сообщение на основе списка объектов {@link Outputable}
 * и отправляет его пользователю через интерфейс {@link AbsSender}.
 */
public class TelegramOutput implements OutputStrategy {
    private static final Logger log = LoggerFactory.getLogger(TelegramOutput.class);
    private final AbsSender absSender;
    private final Chat chat;

    /**
     * Создает экземпляр стратегии вывода для конкретного чата.
     *
     * @param absSender исполнитель команд Telegram (бот)
     * @param chat      целевой чат для отправки сообщений
     */
    public TelegramOutput(AbsSender absSender, Chat chat) {
        this.absSender = absSender;
        this.chat = chat;
    }

    /**
     * Формирует и отправляет сообщение с результатами в Telegram.
     * <p>
     * Метод объединяет строковые представления всех объектов через символ новой строки.
     * Если список пуст, отправка не производится.
     *
     * @param toOutput список объектов, реализующих {@link Outputable}
     * @return {@code true}, если сообщение успешно отправлено;
     *         {@code false}, если список пуст или произошла ошибка при взаимодействии с API
     */
    @Override
    public boolean doOutput(List<? extends Outputable> toOutput) {
        if (toOutput.isEmpty()) {
            return false;
        }
        StringBuilder outputStringBuilder = new StringBuilder();
        toOutput.forEach((Outputable output) ->
                outputStringBuilder.append(output.toOutputValue()).append('\n'));

        SendMessage message = SendMessage.builder()
                .chatId(chat.getId())
                .text(outputStringBuilder.toString())
                .build();

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения пользователю при выполнении команды");
            return false;
        }
        return true;
    }
}
