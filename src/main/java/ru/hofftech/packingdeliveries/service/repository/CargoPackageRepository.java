package ru.hofftech.packingdeliveries.service.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;

/**
 * Репозиторий для хранения и управления объектами {@link CargoPackage}.
 * <p>
 * Обеспечивает хранение типов посылок в оперативной памяти с использованием {@link HashMap},
 * где ключом выступает уникальное имя посылки. При создании инициализируется
 * стандартным набором грузов.
 */
public class CargoPackageRepository {
    private static final Logger log = LoggerFactory.getLogger(CargoPackageRepository.class);
    protected HashMap<String, CargoPackage> packagesRepo = new HashMap<>();

    public CargoPackageRepository() {
        initDefaultRepo();
    }

    /**
     * Добавляет новую посылку в репозиторий.
     * <p>
     * Перед добавлением проверяет уникальность имени. Если посылка с таким именем
     * уже существует, операция отклоняется.
     *
     * @param cargoPackage объект посылки для сохранения (не может быть null)
     * @return {@code true}, если посылка успешно добавлена; {@code false}, если имя уже занято
     */
    public boolean add(@NotNull CargoPackage cargoPackage) {
        Optional<CargoPackage> existingPackage = find(cargoPackage.getName());
        if (existingPackage.isPresent()) {
            log.error("Посылка \"{}\" уже добавлена", cargoPackage.getName());
            return false;
        } else {
            packagesRepo.put(cargoPackage.getName(), cargoPackage);
            log.info("Посылка \"{}\" добавлена в репозиторий посылок", cargoPackage.getName());
            return true;
        }
    }

    /**
     * Удаляет посылку из репозитория по её имени.
     *
     * @param packageName имя посылки, которую необходимо удалить (не может быть null)
     * @return {@code true}, если посылка была найдена и удалена; {@code false}, если объект не найден
     */
    public boolean remove(@NotNull String packageName) {
        if (find(packageName).isPresent()) {
            packagesRepo.remove(packageName);
            log.info("Посылка \"{}\" удалена из репозитория посылок", packageName);
            return true;
        } else {
            log.warn("Посылка \"{}\" не существует", packageName);
            return false;
        }
    }

    /**
     * Выполняет поиск посылки по её уникальному имени.
     *
     * @param name имя искомой посылки
     * @return {@link Optional}, содержащий найденную посылку, или пустой {@code Optional}, если ничего не найдено
     */
    public Optional<CargoPackage> find(String name) {
        return Optional.ofNullable(packagesRepo.get(name));
    }

    /**
     * Возвращает неизменяемый список всех посылок, зарегистрированных в системе.
     *
     * @return копия списка всех объектов {@link CargoPackage}
     */
    public List<CargoPackage> findAll() {
        return List.copyOf(packagesRepo.values());
    }

    /**
     * Инициализирует репозиторий стандартным набором посылок (типы от 1 до 9).
     * <p>
     * Вызывается автоматически при создании экземпляра класса.
     */
    public void initDefaultRepo() {
        List<CargoPackage> defaultPackagesList = List.of(
                new CargoPackage('1', 1, 1),
                new CargoPackage('2', 2, 1),
                new CargoPackage('3', 3, 1),
                new CargoPackage('4', 4, 1),
                new CargoPackage('5', 5, 1),
                new CargoPackage('6', 3, 2),
                new CargoPackage('7', 3, 2),
                new CargoPackage('8', 4, 2),
                new CargoPackage('9', 3, 3));
        defaultPackagesList.forEach(this::add);
        log.info("Репозиторий посылок проинициализирован посылками по умолчанию");
    }
}
