package ru.hofftech.packingdeliveries.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.Outputable;
import ru.hofftech.packingdeliveries.model.jsonDataContract.JsonDataContract;

public class JsonConverter {
    private static final ObjectMapper jsonMapper;

    static {
        jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void toJsonFile(ArrayList<? extends Outputable> objectsList, String filename) throws IOException {
        jsonMapper.writeValue(new File(filename), toJsonContractsList(objectsList));
    }

    public static <T> T fromJsonFile(String filename, TypeReference<T> typeReference) throws IOException {
        return jsonMapper.readValue(new File(filename), typeReference);
    }

    private static ArrayList<JsonDataContract> toJsonContractsList(ArrayList<? extends Outputable> objectsList) {
        ArrayList<JsonDataContract> jsonDataContractList = new ArrayList<JsonDataContract>();
        for (Outputable outputableObj : objectsList) {
            jsonDataContractList.add(outputableObj.toJsonDataContract());
        }
        return jsonDataContractList;
    }
}
