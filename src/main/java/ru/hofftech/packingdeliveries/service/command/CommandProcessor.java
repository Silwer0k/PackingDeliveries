package ru.hofftech.packingdeliveries.service.command;

public interface CommandProcessor {
    void doProcess();

    boolean validate(String[] args);
}
