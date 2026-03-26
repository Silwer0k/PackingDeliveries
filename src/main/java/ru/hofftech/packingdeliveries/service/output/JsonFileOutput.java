package ru.hofftech.packingdeliveries.service.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.Outputable;
import ru.hofftech.packingdeliveries.service.JsonConverter;

public class JsonFileOutput extends FileOutput {
    protected final Logger log = LoggerFactory.getLogger(JsonFileOutput.class);

    public JsonFileOutput(String fileName) {
        super(fileName);
    }

    @Override
    public boolean doOutput(List<? extends Outputable> toOutput) {
        try {
            JsonConverter.toJsonFile(toOutput, fileName);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации!");
            return false;
        } catch (IOException e) {
            log.error("Ошибка экспорта в файл {}!", fileName);
            return false;
        }
        return true;
    }
}
