package ru.hofftech.packingdeliveries.service.unpackagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.model.jsonDataContract.TruckDataContract;
import ru.hofftech.packingdeliveries.service.JsonConverter;

public class UnpackagerFromJsonFile extends Unpackager {
    public final String trucksJsonFilename;

    public UnpackagerFromJsonFile(String trucksJsonFilename) {
        this.trucksJsonFilename = trucksJsonFilename;
    }

    @Override
    public boolean doUnpacking() {
        try {
            log.info("Начало распаковки грузовиков");
            ArrayList<TruckDataContract> trucksDCList =
                    JsonConverter.fromJsonFile(trucksJsonFilename, new TypeReference<>() {});
            unpackTrucks(jsonTrucksListToTrucksList(trucksDCList));
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

    private ArrayList<Truck> jsonTrucksListToTrucksList(ArrayList<TruckDataContract> jsonTrucks) {
        ArrayList<Truck> trucks = new ArrayList<Truck>();
        for (TruckDataContract truckDataContract : jsonTrucks) {
            trucks.add(truckDataContract.toModelObject());
        }
        return trucks;
    }
}
