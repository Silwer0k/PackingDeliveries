package ru.hofftech.packingdeliveries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.core.command.annotation.EnableCommand;

@SpringBootApplication
@EnableCommand
public class Main {
    // private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        /*
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

         */
    }
}
