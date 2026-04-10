package ru.hofftech.packingdeliveries.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Объект передачи данных (DTO) для сущности "Посылка".
 * <p>
 * Используется для транспортировки данных о посылке между слоями приложения,
 * а также для интеграции с внешними сервисами через JSON.
 * Реализует маркерный интерфейс {@link BaseDto}.
 */
public class CargoPackageDto implements BaseDto {
    private final char marker;
    private final int width;
    private final int depth;
    private final String shape;
    private final String name;

    /**
     * Создает экземпляр DTO.
     * Аннотации {@link JsonCreator} и {@link JsonProperty} обеспечивают
     * корректное сопоставление полей при десериализации из JSON.
     *
     * @param marker символ-маркер посылки
     * @param width  ширина посылки
     * @param depth  глубина (высота) посылки
     * @param shape  строковое описание формы (шаблон)
     * @param name   название посылки
     */
    @JsonCreator
    public CargoPackageDto(
            @JsonProperty("marker") char marker,
            @JsonProperty("width") int width,
            @JsonProperty("depth") int depth,
            @JsonProperty("shape") String shape,
            @JsonProperty("name") String name) {
        this.marker = marker;
        this.width = width;
        this.depth = depth;
        this.shape = shape;
        this.name = name;
    }

    /**
     * @return символ посылки
     */
    public char getMarker() {
        return marker;
    }

    /**
     * @return ширина посылки
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return глубина посылки
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return строковое представление формы посылки
     */
    public String getShape() {
        return shape;
    }

    /**
     * @return название посылки
     */
    public String getName() {
        return name;
    }
}
