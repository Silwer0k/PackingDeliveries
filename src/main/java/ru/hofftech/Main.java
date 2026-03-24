package ru.hofftech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.service.command.CommandProcessorService;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        CommandProcessorService commandProcessorService = new CommandProcessorService();
        commandProcessorService.executeCommand(args);
    }
}
