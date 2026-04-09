package ru.hofftech.packingdeliveries.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Объект передачи данных (DTO) для сущности "Грузовик".
 * <p>
 * Предоставляет полную информацию о состоянии транспортного средства,
 * включая его габариты, идентификатор и список всех загруженных посылок
 * в формате {@link CargoPackagePositionDto}.
 */
public final class TruckDto implements BaseDto {
    private final String number;
    private final int width;
    private final int depth;
    private final int loadedVolume;
    private final ArrayList<CargoPackagePositionDto> loadedPackages;

    /**
     * Создает экземпляр DTO грузовика.
     *
     * @param number         уникальный номер грузовика
     * @param width          ширина грузового отсека
     * @param depth          глубина (высота) грузового отсека
     * @param loadedVolume   суммарный объем фактически занятых ячеек
     * @param loadedPackages список объектов {@link CargoPackagePositionDto},
     *                       описывающих размещение посылок
     */
    @JsonCreator
    public TruckDto(
            @JsonProperty("number") String number,
            @JsonProperty("width") int width,
            @JsonProperty("depth") int depth,
            @JsonProperty("loadedVolume") int loadedVolume,
            @JsonProperty(value = "packages") ArrayList<CargoPackagePositionDto> loadedPackages) {
        this.number = number;
        this.width = width;
        this.depth = depth;
        this.loadedVolume = loadedVolume;
        this.loadedPackages = loadedPackages;
    }

    /**
     * @return идентификационный номер грузовика
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return ширина кузова
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return глубина (высота) кузова
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return текущий объем занятого пространства
     */
    public int getLoadedVolume() {
        return loadedVolume;
    }

    /**
     * @return список загруженных посылок с их координатами
     */
    public ArrayList<CargoPackagePositionDto> getLoadedPackages() {
        return loadedPackages;
    }
}
