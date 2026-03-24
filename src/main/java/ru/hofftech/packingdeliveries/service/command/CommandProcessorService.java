package ru.hofftech.packingdeliveries.service.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.enums.CommandProcessorType;

public class CommandProcessorService {
    private static final Logger log = LoggerFactory.getLogger(CommandProcessorService.class);

    public void executeCommand(String[] args) {
        try {
            CommandProcessorType commandType = CommandProcessorType.valueOf(args[0].toUpperCase());
            switch (commandType) {
                case PACKING -> new PackingCommand(args).doProccess();
                case UNPACKING -> new UnpackingCommand(args).doProccess();
                default -> throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
            log.error("Переданная команда \"{}\" не поддерживается!", String.join(" ", args));
        }
    }
}
