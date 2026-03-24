package ru.hofftech.packingdeliveries.service.command;

public interface CommandProcessor {
    void doProccess();

    boolean validate(String[] args);
}
