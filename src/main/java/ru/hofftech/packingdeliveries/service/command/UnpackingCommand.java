package ru.hofftech.packingdeliveries.service.command;

import ru.hofftech.packingdeliveries.service.unpackagers.UnpackagerFromJsonFile;

public class UnpackingCommand implements CommandProcessor {
    private final UnpackagerFromJsonFile unpackagerFromJsonFile;

    UnpackingCommand(String[] args) {
        if (validate(args)) {
            unpackagerFromJsonFile = new UnpackagerFromJsonFile(args[1]);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void doProcess() {
        if (unpackagerFromJsonFile.doUnpacking()) {
            unpackagerFromJsonFile.unpackingResultToConsole();
            unpackagerFromJsonFile.unpackingResultToFile();
        }
    }

    @Override
    public boolean validate(String[] args) {
        return args.length == 2;
    }
}
