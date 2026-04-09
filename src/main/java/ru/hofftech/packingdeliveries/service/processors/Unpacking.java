package ru.hofftech.packingdeliveries.service.processors;

import java.util.List;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.unpackagers.UnpackagerFromJsonFile;

/**
 * Процессор команды распаковки грузовиков.
 * <p>
 * Класс отвечает за координацию процесса извлечения данных о посылках из JSON-файла.
 * Он делегирует низкоуровневую работу по десериализации объекту {@link UnpackagerFromJsonFile}
 * и предоставляет результат в виде списка объектов {@link CargoPackage}.
 */
public class Unpacking implements CommandProcessor {
    private final String trucksJsonFileName;
    private boolean success = false;
    private List<CargoPackage> resultPackages;

    /**
     * Конструирует процессор распаковки.
     *
     * @param trucksJsonFileName путь к файлу формата JSON для обработки
     */
    public Unpacking(String trucksJsonFileName) {
        this.trucksJsonFileName = trucksJsonFileName;
    }

    /**
     * @return список восстановленных объектов {@link CargoPackage}
     *         или {@code null}, если процесс не был успешно завершен
     */
    public List<CargoPackage> getResultPackages() {
        return resultPackages;
    }

    /**
     * Запускает процесс восстановления данных.
     * <p>
     * Инициализирует {@link UnpackagerFromJsonFile} и, в случае успешного выполнения
     * десериализации, сохраняет полученный список посылок во внутреннем поле.
     */
    @Override
    public void doProcess() {
        UnpackagerFromJsonFile unpackagerFromJsonFile = new UnpackagerFromJsonFile(trucksJsonFileName);
        if (unpackagerFromJsonFile.doUnpacking()) {
            resultPackages = unpackagerFromJsonFile.getUnpackedPackages();
            success = true;
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
        return List.of();
    }
}
