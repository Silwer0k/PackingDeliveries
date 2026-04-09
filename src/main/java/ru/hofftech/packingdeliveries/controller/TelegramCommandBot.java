package ru.hofftech.packingdeliveries.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hofftech.packingdeliveries.service.command.telegram.CreatePackageTelegramCommand;
import ru.hofftech.packingdeliveries.service.command.telegram.DeletePackageTelegramCommand;
import ru.hofftech.packingdeliveries.service.command.telegram.FindPackageTelegramCommand;
import ru.hofftech.packingdeliveries.service.command.telegram.PackingTelegramCommand;
import ru.hofftech.packingdeliveries.service.command.telegram.UnpackingTelegramCommand;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Основной класс Telegram-бота для управления логистическими операциями.
 * <p>
 * Бот предоставляет интерфейс для упаковки, распаковки, поиска, добавления и удаления посылок через команды.
 * Наследует {@link TelegramLongPollingCommandBot} для автоматической диспетчеризации команд.
 */
public class TelegramCommandBot extends TelegramLongPollingCommandBot {
    private static final String token = "8691950259:AAE7wNve2IhKAk3K4lT2tPCqt9h7LsgXXTA";
    private static final String name = "PackingDeliveriesBot";
    private static final Logger log = LoggerFactory.getLogger(TelegramCommandBot.class);
    private final CargoPackageRepository packageRepository;

    /**
     * Конструктор бота. Инициализирует соединение и регистрирует обработчики команд.
     *
     * @param packageRepository репозиторий, используемый командами для работы с посылками
     */
    public TelegramCommandBot(CargoPackageRepository packageRepository) {
        super(token);
        this.packageRepository = packageRepository;

        register(new PackingTelegramCommand(this.packageRepository));
        register(new UnpackingTelegramCommand());
        register(new CreatePackageTelegramCommand(this.packageRepository));
        register(new FindPackageTelegramCommand(this.packageRepository));
        register(new DeletePackageTelegramCommand(this.packageRepository));
    }

    /**
     * Возвращает имя бота, зарегистрированное в Telegram.
     *
     * @return строковое имя бота
     */
    @Override
    public String getBotUsername() {
        return name;
    }

    /**
     * Обрабатывает входящие обновления, которые не являются командами или не распознаны.
     * Формирует и отправляет пользователю список всех доступных команд с их описанием.
     *
     * @param update объект входящего обновления от Telegram API
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        StringBuilder helpText = new StringBuilder("Доступные команды:\n");

        for (IBotCommand command : getRegisteredCommands()) {
            helpText.append("/")
                    .append(command.getCommandIdentifier())
                    .append(" — ")
                    .append(command.getDescription())
                    .append("\n");
        }

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(helpText.toString());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки доступных команд");
        }
    }
}
