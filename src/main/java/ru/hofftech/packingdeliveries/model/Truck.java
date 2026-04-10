package ru.hofftech.packingdeliveries.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.util.Default;

/**
 * Представляет грузовик для загрузки посылок.
 * <p>
 * Класс управляет грузовым пространством фиксированного размера ({@value #width}x{@value #depth}),
 * отслеживает размещенные объекты {@link CargoPackage} и проверяет физическую
 * возможность их установки (наличие опоры и свободного места).
 */
public class Truck implements Outputable {
    public static final char emptySpaceMarker = ' ';
    private static final char borderMarker = '+';
    private static final Logger log = LoggerFactory.getLogger(Truck.class);
    private static final Random randomizer = new Random();
    private static final short width = 6;
    private static final short depth = 6;
    private final char[][] cargoSpace;
    private final ArrayList<CargoPackagePosition> loadedPackages;
    private final String number;

    /**
     * @return уникальный номер грузовика
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return ширина грузового пространства
     */
    public short getWidth() {
        return width;
    }

    /**
     * @return глубина (высота) грузового пространства
     */
    public short getDepth() {
        return depth;
    }

    /**
     * Создает новый пустой грузовик со случайным номером и фиксированным размером 6х6
     */
    public Truck() {
        cargoSpace = new char[width][depth];
        number = String.valueOf(randomizer.nextInt(100));
        loadedPackages = new ArrayList<CargoPackagePosition>();
        log.info("Создан объект грузовика {} с параметрами Width: {} Depth: {}", number, width, depth);
        doEmpty();
    }

    /**
     * Создает грузовик фиксированным размером 6х6 с заданным номером и списком уже загруженных посылок.
     *
     * @param loadedPackages список позиций посылок для инициализации кузова
     * @param number         идентификатор грузовика
     */
    @Default
    public Truck(ArrayList<CargoPackagePosition> loadedPackages, String number) {
        cargoSpace = new char[width][depth];
        this.number = number;
        this.loadedPackages = new ArrayList<CargoPackagePosition>();
        doEmpty();
        loadedPackages.forEach((loadedPackage) ->
                placePackage(loadedPackage.getCargoPackage(), loadedPackage.getRowPos(), loadedPackage.getColPos()));
        log.info(
                "Создан грузовик {} Width: {} Depth: {} с загруженными посылками с параметрами ", number, width, depth);
    }

    /**
     * Пытается автоматически найти место и разместить посылку в кузове.
     * <p>
     * Поиск ведется снизу вверх, слева направо. Для успешного размещения
     * должны быть соблюдены условия свободного пространства и устойчивости (опоры).
     *
     * @param cargoPackage объект посылки для размещения
     * @return {@code true}, если место найдено и посылка размещена; {@code false} в противном случае
     */
    public boolean tryPlacePackage(CargoPackage cargoPackage) {
        for (int row = depth - 1; row >= 0; row--) {
            for (int col = 0; col < width; col++) {
                if (cargoSpace[row][col] == emptySpaceMarker
                        && checkCargoSpaceForPackage(cargoPackage, row, col)
                        && checkSupportForCargo(cargoPackage, row, col)) {
                    placePackage(cargoPackage, row, col);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Принудительно размещает посылку по указанным координатам.
     * Обновляет матрицу кузова и добавляет информацию в список загруженных посылок.
     *
     * @param cargoPackage посылка для размещения
     * @param rowPos       индекс целевой строки (нижняя точка посылки)
     * @param colPos       индекс целевого столбца (левая точка посылки)
     */
    public void placePackage(CargoPackage cargoPackage, int rowPos, int colPos) {
        for (int packageRow = 0; packageRow < cargoPackage.getDepth(); packageRow++) {
            System.arraycopy(
                    cargoPackage.packageMatrixRow(packageRow),
                    0,
                    cargoSpace[rowPos - (cargoPackage.getDepth() - 1) + packageRow],
                    colPos,
                    cargoPackage.getWidth());
        }
        loadedPackages.add(new CargoPackagePosition(rowPos, colPos, cargoPackage));
        log.info("Посылка {} размещена на позицию {},{}", cargoPackage.toString(), rowPos, colPos);
    }

    /**
     * @return список всех размещенных в данный момент посылок с их координатами
     */
    public ArrayList<CargoPackagePosition> getLoadedPackages() {
        return loadedPackages;
    }

    /**
     * Формирует визуальное представление кузова грузовика с границами.
     *
     * @return строковая схема кузова, где границы обозначены символом '+'
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row <= depth; row++) {
            for (int column = -1; column <= width; column++) {
                str.append(isBorderCoords(row, column) ? borderMarker : cargoSpace[row][column]);
            }
            str.append('\n');
        }
        return str.toString();
    }

    /**
     * Вычисляет текущий занятый объем кузова.
     * Считается как сумма всех ячеек, не являющихся пустыми (отличных от {@link #emptySpaceMarker}).
     *
     * @return количество занятых ячеек
     */
    public int getLoadedVolume() {
        return (int) Arrays.stream(cargoSpace)
                .flatMapToInt(row -> new String(row).chars())
                .mapToObj(cell -> (char) cell)
                .filter(cell -> !cell.equals(emptySpaceMarker))
                .count();
    }

    /**
     * Возвращает общую вместимость кузова (площадь матрицы).
     *
     * @return общее количество доступных ячеек
     */
    public static int getAllVolume() {
        return width * depth;
    }

    /**
     * Полностью очищает грузовой отсек.
     * Заполняет матрицу символами пустоты и удаляет все записи о посылках.
     */
    public void doEmpty() {
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                cargoSpace[row][col] = emptySpaceMarker;
            }
        }
        loadedPackages.clear();
        log.info("Разгрузили грузовик");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toOutputValue() {
        return toString();
    }

    private boolean isBorderCoords(int row, int column) {
        return ((row == depth) || (column == -1) || (column == width));
    }

    private boolean checkCargoSpaceForPackage(CargoPackage cargoPackage, int rowPos, int colPos) {
        if ((rowPos - (cargoPackage.getDepth() - 1) < 0) || (colPos + cargoPackage.getWidth() - 1 >= width)) {
            return false;
        }

        for (int row = 0; row < cargoPackage.getDepth(); row++) {
            for (int col = 0; col < cargoPackage.getWidth(); col++) {
                if (cargoSpace[rowPos - row][colPos + col] != emptySpaceMarker) return false;
            }
        }
        return true;
    }

    private boolean checkSupportForCargo(CargoPackage cargoPackage, int rowPos, int colPos) {
        if (rowPos + 1 == depth) {
            return true;
        }

        int supports = 0;
        for (int col = 0; col < cargoPackage.getWidth(); col++) {
            supports += (cargoSpace[rowPos + 1][colPos + col] == emptySpaceMarker) ? 0 : 1;
        }

        return supports > (cargoPackage.getWidth() / 2);
    }
}
