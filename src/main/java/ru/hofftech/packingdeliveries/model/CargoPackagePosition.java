package ru.hofftech.packingdeliveries.model;

/**
 * Представляет позицию конкретной посылки в кузове грузовика.
 * <p>
 * Класс связывает объект {@link CargoPackage} с его координатами в кузове грузовика (строка и столбец).
 */
public class CargoPackagePosition implements Outputable {
    private final int rowPos;
    private final int colPos;
    private final CargoPackage cargoPackage;

    /**
     * Создает объект с указанием координат и самой посылки.
     *
     * @param rowPos       индекс начальной строки (вертикальная позиция)
     * @param colPos       индекс начального столбца (горизонтальная позиция)
     * @param cargoPackage объект посылки, размещенный по данным координатам
     */
    public CargoPackagePosition(int rowPos, int colPos, CargoPackage cargoPackage) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.cargoPackage = cargoPackage;
    }

    /**
     * @return объект посылки {@link CargoPackage}
     */
    public CargoPackage getCargoPackage() {
        return cargoPackage;
    }

    /**
     * @return индекс столбца (X-координата) расположения посылки
     */
    public int getColPos() {
        return colPos;
    }

    /**
     * @return индекс строки (Y-координата) расположения посылки
     */
    public int getRowPos() {
        return rowPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toOutputValue() {
        return "";
    }
}
