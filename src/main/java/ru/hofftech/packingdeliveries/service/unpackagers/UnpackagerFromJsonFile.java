package ru.hofftech.packingdeliveries.service.unpackagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.model.dto.TruckDto;
import ru.hofftech.packingdeliveries.util.JsonConverter;

public class UnpackagerFromJsonFile extends Unpackager {
    public final String trucksJsonFilename;

    public UnpackagerFromJsonFile(String trucksJsonFilename) {
        this.trucksJsonFilename = trucksJsonFilename;
    }

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
