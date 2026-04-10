package ru.hofftech.packingdeliveries.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Объект передачи данных (DTO) для погруженной в грузовик посылки.
 * <p>
 * Хранит координаты размещения и данные о самой посылке в формате {@link CargoPackageDto}.
 * Используется для передачи состояния загрузки кузова грузовика.
 */
public final class CargoPackagePositionDto implements BaseDto {
    private final int rowPos;
    private final int colPos;
    private final CargoPackageDto cargoPackage;

    /**
     * Создает экземпляр DTO для позиции посылки.
     *
     * @param rowPos       индекс строки (вертикальная позиция)
     * @param colPos       индекс столбца (горизонтальная позиция)
     * @param cargoPackage данные о посылке, привязанной к этой позиции
     */
    @JsonCreator
    public CargoPackagePositionDto(
            @JsonProperty("rowPos") int rowPos,
            @JsonProperty("colPos") int colPos,
            @JsonProperty("package") CargoPackageDto cargoPackage) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.cargoPackage = cargoPackage;
    }

    /**
     * @return вертикальная координата (индекс строки)
     */
    public int getRowPos() {
        return rowPos;
    }

    /**
     * @return горизонтальная координата (индекс столбца)
     */
    public int getColPos() {
        return colPos;
    }

    /**
     * @return объект {@link CargoPackageDto}, содержащий параметры посылки
     */
    public CargoPackageDto getCargoPackage() {
        return cargoPackage;
    }
}
