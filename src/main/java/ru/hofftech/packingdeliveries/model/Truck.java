package ru.hofftech.packingdeliveries.model;

import com.mifmif.common.regex.Generex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Truck {
    private static final char emptySpaceMarker = ' ';
    private static final char borderMarker = '+';
    private static final String numberRegex = "[A-Z] \\d\\d\\d [A-Z][A-Z]";
    private static final Logger log = LoggerFactory.getLogger(Truck.class);
    private final short width = 6;
    private final short depth = 6;
    private final char[][] cargoSpace;
    private final String number;

    public String getNumber() {
        return number;
    }

    public Truck(){
        cargoSpace = new char[width][depth];
        number = new Generex(numberRegex).random();
        log.info("Создан объект грузовика {} с параметрами Width: {} Depth: {}", number, width, depth);
        doEmpty();
    }

    public void placePackage(CargoPackage cargoPackage, int posX, int posY){
        if (checkCargoSpaceForPackage(cargoPackage, posX, posY)) {
            for (int row = posX; row < cargoPackage.getDepth() + posX; row++){
                for (int col = posY ; col < cargoPackage.getWidth() + posY; col++){
                    cargoSpace[row][col] = cargoPackage.getMarker();
                }
            }
        }
        else{
            log.error("Посылка не может быть расположена на позицию {},{}", posX, posY);
        }
    }

    public String toString(){
        String str = "";
        for (int row = depth - 1; row >= -1; row--){
            for (int column = width; column >= -1 ; column--){
                str += isBorderCoords(row, column) ? borderMarker: cargoSpace[row][column];
            }
            str += '\n';
        }
        return str;
    }

    private boolean isBorderCoords(int row, int column){
        return ((row == -1) || (column == -1) || (column == width));
    }

    private void doEmpty(){
        for (int row = 0; row < depth; row++){
            for (int col = 0 ; col < width; col++){
                cargoSpace[row][col] = emptySpaceMarker;
            }
        }
        log.info("Разгрузили грузовик");
    }

    private boolean checkCargoSpaceForPackage(CargoPackage cargoPackage, int posX, int posY){
        return !((posX + cargoPackage.getDepth() > depth - 1) || (posY + cargoPackage.getWidth() > width - 1));
    }
}
