package ru.hofftech.packingdeliveries.service.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.Outputable;

public class FileOutput implements OutputStrategy {
    private static final Logger log = LoggerFactory.getLogger(FileOutput.class);
    protected final String fileName;

    public FileOutput(String fileName) {
        this.fileName = String.format(
                "%s_%s.txt", fileName, LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyyHHmm")));
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean doOutput(ArrayList<? extends Outputable> toOutput) {
        try {
            StringBuilder resultStringBuilder = new StringBuilder();
            for (Outputable outputObj : toOutput) {
                resultStringBuilder.append(outputObj.toOutputValue());
                resultStringBuilder.append('\n');
            }
            Files.writeString(Path.of(fileName), resultStringBuilder.toString());
            log.info("Создан файл результатов: {}", fileName);
        } catch (IOException e) {
            log.error("Ошибка экспорта в файл {}", fileName);
            return false;
        }
        return true;
    }
}
