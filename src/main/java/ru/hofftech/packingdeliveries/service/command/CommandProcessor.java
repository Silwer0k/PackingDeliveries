package ru.hofftech.packingdeliveries.service.command;

public interface CommandProcessor {
    public void doProccess();

    public boolean validate(String[] args);
}
