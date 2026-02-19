package ru.hofftech.packingdeliveries.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.service.OneByOnePackager;
import ru.hofftech.packingdeliveries.service.PackageReader;

public class ConsoleListener {
    private static final Logger log = LoggerFactory.getLogger(ConsoleListener.class);
    private static final Map<Pattern, Consumer<Matcher>> commandHandlers = new HashMap<>();
    private static final OneByOnePackager  packerController = new OneByOnePackager();
    private static final PackageReader packageReader = new PackageReader();

    static {
        commandHandlers.put(Pattern.compile("^exit$"), matcher -> {
            log.info("Выход из приложения");
            System.exit(0);
        });
        commandHandlers.put(Pattern.compile("^import (.+\\.txt)$"), matcher -> {
            String packagesFilename = matcher.group(1);
            for (CargoPackage cargoPackage: packageReader.readFromFile(packagesFilename)){
                packerController.addPackage(cargoPackage);
            }
        });
        commandHandlers.put(Pattern.compile("^doPacking$"), matcher -> {
            packerController.doPacking();
            packerController.showPackingResults();
            log.info("Выход из приложения");
            System.exit(0);
        });
    }

    protected void processCommand(String command){
        boolean matched = false;
        for (Map.Entry<Pattern, Consumer<Matcher>> entry : commandHandlers.entrySet()) {
            Pattern pattern = entry.getKey();
            Consumer<Matcher> handler = entry.getValue();

            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                handler.accept(matcher);
                matched = true;
                break;
            }
        }
        if (!matched) {
            log.error("Неизвестная команда: {}", command);
        }
    }

    public void listen(){
        Scanner consoleScanner = new Scanner(System.in);
        while (consoleScanner.hasNextLine()){
            String command = consoleScanner.nextLine();
            this.processCommand(command);
        }
    }
}
