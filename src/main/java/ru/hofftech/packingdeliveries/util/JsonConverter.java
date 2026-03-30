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

public class JsonConverter {
    private static final ObjectMapper jsonMapper;
    private static final ModelDtoMapper modelDtoMapper;

    static {
        jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        modelDtoMapper = Mappers.getMapper(ModelDtoMapper.class);
    }

    public static void toJsonFile(List<? extends Outputable> objectsList, String filename) throws IOException {
        jsonMapper.writeValue(new File(filename), modelDtoMapper.toDtoList(objectsList));
    }

    public static <T> T fromJsonFile(String filename, TypeReference<T> typeReference) throws IOException {
        return jsonMapper.readValue(new File(filename), typeReference);
    }

    public static <S extends BaseDto, T extends Outputable> List<T> toModelList(List<S> dtoList, Class<T> targetClass) {
        return modelDtoMapper.toModelList(dtoList, targetClass);
    }
}
