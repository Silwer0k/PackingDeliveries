package ru.hofftech.packingdeliveries.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.Truck;

import java.util.ArrayList;

public class OneByOnePackager {
    private static final Logger log = LoggerFactory.getLogger(OneByOnePackager.class);
    private final ArrayList<CargoPackage> packages = new ArrayList<>();
    private final ArrayList<Truck> trucks = new ArrayList<>();

    public void addPackage(CargoPackage cargoPackage){
        packages.add(cargoPackage);
        log.info("Добавлен груз {}:{},{} для упаковки", cargoPackage.getMarker(), cargoPackage.getWidth(), cargoPackage.getDepth());
    }

    public void doPacking(){
        log.info("Начало упаковки в грузовики");
        for (CargoPackage cargo: packages){
            Truck newTruck = new Truck();
            trucks.add(newTruck);
            newTruck.placePackage(cargo, 0, 0);
            log.info("Упаковали посылки в грузовик {}", newTruck.getNumber());
        }
        log.info("Окончание упаковки в грузовики");
    }

    public void showPackingResults(){
        for (Truck truck: trucks){
            System.out.println(truck.toString());
        }
    }
}
