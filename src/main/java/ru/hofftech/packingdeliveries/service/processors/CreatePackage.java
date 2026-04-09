package ru.hofftech.packingdeliveries.service.processors;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.repository.CargoPackageRepository;

/**
 * Процессор команды создания новой посылки.
 * <p>
 * Класс выполняет проверку корректности переданной формы, создает объект {@link CargoPackage}
 * и регистрирует его в репозитории. В случае дублирования имени или неверного формата формы
 * фиксирует соответствующие ошибки.
 */
public class CreatePackage implements CommandProcessor {
    private static final Logger log = LoggerFactory.getLogger(CreatePackage.class);
    private final CargoPackageRepository packageRepository;
    private final String packageName;
    private final String packageShape;
    private final char packageMarker;
    private boolean success = false;
    private final List<String> errors;
    private CargoPackage newPackage;

    /**
     * Конструирует процессор создания посылки.
     *
     * @param packageRepository репозиторий для управления данными
     * @param packageName       название новой посылки
     * @param packageShape      строковый шаблон формы (с использованием 'x' и '\n')
     * @param packageMarker     символьный маркер посылки
     */
    public CreatePackage(
            CargoPackageRepository packageRepository, String packageName, String packageShape, char packageMarker) {
        this.packageRepository = packageRepository;
        this.packageName = packageName;
        this.packageShape = packageShape;
        this.packageMarker = packageMarker;
        errors = new ArrayList<String>();
    }

    /**
     * @return объект созданной посылки или {@code null}, если процесс не был запущен или завершился ошибкой
     */
    public CargoPackage getNewPackage() {
        return newPackage;
    }

    /**
     * Выполняет основной процесс создания посылки.
     * <p>
     * Логика работы:
     * <ol>
     *     <li>Проверка валидности структуры формы через {@link CargoPackage#checkCustomShape(String)}.</li>
     *     <li>Создание экземпляра {@link CargoPackage}.</li>
     *     <li>Попытка добавления в {@link CargoPackageRepository}.</li>
     *     <li>Установка флага успеха или наполнение списка {@code errors} при неудаче.</li>
     * </ol>
     */
    @Override
    public void doProcess() {
        if (CargoPackage.checkCustomShape(packageShape)) {
            CargoPackage newPackage = new CargoPackage(packageName, packageShape, packageMarker);
            if (packageRepository.add(newPackage)) {
                this.newPackage = newPackage;
                success = true;
            } else {
                errors.add(String.format("Посылка с именем %s уже существует", packageMarker));
            }
        } else {
            log.error("Форма посылки {} не соответствует правилам определения формы", packageName);
            errors.add(String.format("Форма посылки %s не соответствует правилам определения формы", packageMarker));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuccess() {
        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getProcessErrors() {
        return errors;
    }
}
