package ru.hofftech.packingdeliveries.service.command;

import ru.hofftech.packingdeliveries.service.unpackagers.UnpackagerFromJsonFile;

public class UnpackingCommand implements CommandProcessor {
    private final String trucksJsonFilename;

    UnpackingCommand(String[] args) {
        if (validate(args)) {
            trucksJsonFilename = args[1];
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void doProccess() {
        UnpackagerFromJsonFile unpackagerFromJsonFile = new UnpackagerFromJsonFile(trucksJsonFilename);
        if (unpackagerFromJsonFile.doUnpacking()) {
            unpackagerFromJsonFile.unpackingResultToConsole();
            unpackagerFromJsonFile.unpackingResultToFile();
        }
    }

    @Override
    public boolean validate(String[] args) {
        if (args.length != 2) {
            return false;
        }
        return true;
    }
}
