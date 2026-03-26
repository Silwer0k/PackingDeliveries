package ru.hofftech;

import ru.hofftech.packingdeliveries.service.command.CommandProcessorService;

public class Main {
    public static void main(String[] args) {
        CommandProcessorService commandProcessorService = new CommandProcessorService();
        commandProcessorService.executeCommand(args);
    }
}
