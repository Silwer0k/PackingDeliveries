package ru.hofftech.packingdeliveries.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.model.CargoPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageReader {
    private static final Logger log = LoggerFactory.getLogger(PackageReader.class);
    private static final String packagesRegex = "1\\r\\n|22\\r\\n|333\\r\\n|4444\\r\\n|5555\\r\\n|666\\r\\n666\\r\\n|777\\r\\n7777\\r\\n|8888\\r\\n8888\\r\\n";

    public ArrayList<CargoPackage> readFromFile(String fileName){
        ArrayList<CargoPackage> parsedPackages = new ArrayList<>();

        try {
            File inputFile = new File(fileName);
            String fileData = new Scanner(inputFile).useDelimiter("\\Z").next();

            log.info("Импортируем файл: {}", fileName);
            Pattern packagesPattern = Pattern.compile(packagesRegex);
            Matcher matcher = packagesPattern.matcher(fileData);
            while (matcher.find()){
                String packageString = matcher.group();
                char packageMarker = packageString.charAt(0);
                String[] lines = packageString.split("\\r\\n");
                short depth = (short)lines.length;
                short width = (short)lines[0].length();
                parsedPackages.add(new CargoPackage(packageMarker, width, depth));
            }
        }
        catch (FileNotFoundException ex){
            log.error("Файл {} не найден", fileName);
        }

        return parsedPackages;
    }
}
