package ru.hofftech.packingdeliveries.service.command.telegram;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;
import ru.hofftech.packingdeliveries.service.command.params.FindAllPackagesParams;
import ru.hofftech.packingdeliveries.service.command.params.FindPackageParams;
import ru.hofftech.packingdeliveries.service.output.TelegramOutput;
import ru.hofftech.packingdeliveries.service.processors.FindAllPackages;
import ru.hofftech.packingdeliveries.service.processors.FindPackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Команда Telegram для поиска посылок в системе.
 * <p>
 * Поддерживает два режима работы:
 * <ul>
 *     <li>Поиск конкретной посылки по имени (в кавычках).</li>
 *     <li>Вывод списка всех посылок (при передаче аргумента {@code all}).</li>
 * </ul>
 * Результаты визуализируются в чате с помощью {@link TelegramOutput}.
 */
public class FindPackageTelegramCommand extends TelegramCommand {
    private static final Logger log = LoggerFactory.getLogger(FindPackageTelegramCommand.class);
    private final CargoPackageRepository packageRepository;

    public FindPackageTelegramCommand(CargoPackageRepository packageRepository) {
        super(CommandProcessorType.FIND, "Создание посылки");
        this.packageRepository = packageRepository;
    }

    /**
     * Выполняет поиск посылок на основе переданных аргументов.
     * <p>
     * Алгоритм работы:
     * <ol>
     *     <li>Проверяет соответствие аргументов паттерну {@link FindPackageParams} (поиск по имени).</li>
     *     <li>Если имя найдено, выводит форму посылки или сообщение о её отсутствии.</li>
     *     <li>Если паттерн имени не подошел, проверяет соответствие {@link FindAllPackagesParams} (команда "all").</li>
     *     <li>При соответствии выводит полный список всех зарегистрированных посылок.</li>
     *     <li>Если ни один паттерн не совпал, отправляет сообщение об ошибке валидации.</li>
     * </ol>
     *
     * @param absSender интерфейс для отправки сообщений
     * @param user      пользователь Telegram
     * @param chat      объект чата
     * @param arguments массив строк (имя в кавычках или ключевое слово 'all')
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("Вызов команды с параметрами {}", String.join(" ", arguments));
        FindPackageParams paramsOne = new FindPackageParams(String.join(" ", arguments));
        if (paramsOne.validate()) {
            FindPackage findPackage = new FindPackage(packageRepository, paramsOne.getPackageNameToFind());
            findPackage.doProcess();
            findPackage
                    .getFoundPackage()
                    .ifPresentOrElse(
                            (CargoPackage p) -> new TelegramOutput(absSender, chat).doOutput(List.of(p)),
                            () -> sendResponse(
                                    absSender,
                                    user,
                                    chat,
                                    String.format("Посылка %s не найдена", paramsOne.getPackageNameToFind())));
        } else {
            FindAllPackagesParams paramsAll = new FindAllPackagesParams(String.join(" ", arguments));
            if (!paramsAll.validate()) {
                sendResponse(absSender, user, chat, "Ошибка валидации входных параметров");
                return;
            }

            FindAllPackages findAllPackages = new FindAllPackages(packageRepository);
            findAllPackages.doProcess();
            if (!findAllPackages.getAllPackages().isEmpty()) {
                new TelegramOutput(absSender, chat).doOutput(findAllPackages.getAllPackages());
            } else {
                sendResponse(absSender, user, chat, "Найдено ни одной посылки");
            }
        }
    }
}
