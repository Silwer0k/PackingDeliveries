package ru.hofftech;

import ru.hofftech.packingdeliveries.controller.ConsoleListener;

public class Main {
    public static void main(String[] args) {
        ConsoleListener consoleListener = new ConsoleListener();
        consoleListener.listen();
    }
}