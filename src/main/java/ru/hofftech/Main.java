package ru.hofftech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hofftech.packingdeliveries.controller.ConsoleListener;
import ru.hofftech.packingdeliveries.controller.TelegramCommandBot;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        CargoPackageRepository packageRepository = new CargoPackageRepository();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramCommandBot(packageRepository));
            log.info("Запущен телеграмм бот");
        } catch (TelegramApiException e) {
            log.error("Ошибка регистрации бота телеграмм");
        }

        ConsoleListener consoleListener = new ConsoleListener(packageRepository);
        consoleListener.listen();
    }
}
