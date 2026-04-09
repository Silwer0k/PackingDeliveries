package ru.hofftech.packingdeliveries.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.mapstruct.factory.Mappers;
import ru.hofftech.packingdeliveries.model.Outputable;
import ru.hofftech.packingdeliveries.model.dto.BaseDto;

/**
 * Утилитарный класс для сериализации и десериализации данных в формате JSON.
 * <p>
 * Класс инкапсулирует работу с библиотекой Jackson ({@link ObjectMapper}) и
 * маппером структур ({@link ModelDtoMapper}). Позволяет сохранять списки моделей
 * в файлы, считывать их и конвертировать между слоями DTO и бизнес-логики.
 */
public class JsonConverter {
    private static final ObjectMapper jsonMapper;
    private static final ModelDtoMapper modelDtoMapper;

    static {
        jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        modelDtoMapper = Mappers.getMapper(ModelDtoMapper.class);
    }

    /**
     * Сериализует список объектов в JSON-файл.
     * <p>
     * Перед записью объекты автоматически конвертируются в соответствующие DTO
     * через {@link ModelDtoMapper}.
     *
     * @param objectsList список объектов, реализующих {@link Outputable}
     * @param filename    имя или путь к создаваемому файлу
     * @throws IOException если возникла ошибка при записи в файл
     */
    public static void toJsonFile(List<? extends Outputable> objectsList, String filename) throws IOException {
        jsonMapper.writeValue(new File(filename), modelDtoMapper.toDtoList(objectsList));
    }

    /**
     * Считывает данные из JSON-файла и десериализует их в объект заданного типа.
     *
     * @param filename      путь к исходному JSON-файлу
     * @param typeReference спецификация типа (например, {@code new TypeReference<List<TruckDto>>() {}})
     * @param <T>           целевой тип данных
     * @return объект, заполненный данными из файла
     * @throws IOException если файл не найден или данные некорректны
     */
    public static <T> T fromJsonFile(String filename, TypeReference<T> typeReference) throws IOException {
        return jsonMapper.readValue(new File(filename), typeReference);
    }

    /**
     * Конвертирует список DTO обратно в список бизнес-моделей.
     *
     * @param dtoList     список объектов, реализующих {@link BaseDto}
     * @param targetClass целевой класс модели (например, {@code Truck.class})
     * @param <S>         тип исходного DTO
     * @param <T>         тип целевой модели
     * @return список восстановленных моделей
     */
    public static <S extends BaseDto, T extends Outputable> List<T> toModelList(List<S> dtoList, Class<T> targetClass) {
        return modelDtoMapper.toModelList(dtoList, targetClass);
    }
}
