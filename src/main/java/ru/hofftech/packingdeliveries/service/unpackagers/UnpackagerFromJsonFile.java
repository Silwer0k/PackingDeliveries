package ru.hofftech.packingdeliveries.service.unpackagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.model.dto.TruckDto;
import ru.hofftech.packingdeliveries.util.JsonConverter;

/**
 * Реализация распаковщика, работающая с данными в формате JSON.
 * <p>
 * Класс считывает состояние грузовиков из файла, выполняет десериализацию в DTO,
 * конвертирует их в бизнес-модели {@link Truck} и извлекает список посылок.
 */
public class UnpackagerFromJsonFile extends Unpackager {
    public final String trucksJsonFilename;

    /**
     * Создает экземпляр распаковщика для конкретного файла.
     *
     * @param trucksJsonFilename путь к файлу, содержащему сериализованные данные грузовиков
     */
    public UnpackagerFromJsonFile(String trucksJsonFilename) {
        this.trucksJsonFilename = trucksJsonFilename;
    }

    /**
     * Выполняет чтение и десериализацию данных из JSON-файла.
     * <p>
     * Процесс включает:
     * <ol>
     *     <li>Чтение файла и преобразование в список {@link TruckDto} через {@link JsonConverter}.</li>
     *     <li>Конвертацию DTO в список объектов модели {@link Truck}.</li>
     *     <li>Вызов базового метода {@link #unpackTrucks(List)} для наполнения итогового списка посылок.</li>
     * </ol>
     *
     * @return {@code true}, если файл успешно прочитан и данные корректно обработаны;
     *         {@code false} при ошибках парсинга JSON или отсутствии файла.
     */
    @Override
    public boolean doUnpacking() {
        try {
            log.info("Начало распаковки грузовиков");
            List<TruckDto> trucksDtoList = JsonConverter.fromJsonFile(trucksJsonFilename, new TypeReference<>() {});
            List<Truck> deserializedTrucks = JsonConverter.toModelList(trucksDtoList, Truck.class);
            unpackTrucks(deserializedTrucks);
            log.info("Окончание распаковки грузовиков");
        } catch (JsonProcessingException exception) {
            log.error("Ошибка десериализации при распаковке грузовиков содержимого файла {}", trucksJsonFilename);
            return false;
        } catch (IOException e) {
            log.error("Файл {} не найден!", trucksJsonFilename);
            return false;
        }
        return true;
    }
}
