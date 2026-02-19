package ru.hofftech.packingdeliveries.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoPackage {
    private static final Logger log = LoggerFactory.getLogger(CargoPackage.class);
    private final char marker;
    private final int width;
    private final int depth;

    public char getMarker() {
        return marker;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public CargoPackage(char packageMarker, int packageWidth, int packageDepth){
        marker = packageMarker;
        width = packageWidth;
        depth = packageDepth;

        log.info("Создан объект посылки с параметрами: Marker = {}, Width = {}, Depth = {}", marker, width, depth);
    }

    public String toString(){
        String str = "";
        for (int i = 0; i < depth; i++){
            for (int j = 0; j < width; j++){
                str += marker;
            }
            str += "\n";
        }
        return str;
    }
}
