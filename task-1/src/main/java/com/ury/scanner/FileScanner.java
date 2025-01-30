package com.ury.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileScanner {

    private static final String DB_FILE = "db.json";
    private static final String SETTINGS_FILE = "settings.json";

    public List<Path> findDbFiles(Path directory) throws IOException {
        return Files.walk(directory)
                .filter(path -> path.getFileName().toString().matches("db_.*\\.json"))
                .collect(Collectors.toList());
    }

    public boolean validateRequiredFiles(Path directory) {
        Path dbFile = directory.resolve(DB_FILE);
        Path settingsFile = directory.resolve(SETTINGS_FILE);

        boolean dbExists = Files.exists(dbFile);
        boolean settingsExists = Files.exists(settingsFile);

        if (!dbExists) {
            System.out.println("Ошибка: отсутствует файл db.json в директории " + directory);
        }
        if (!settingsExists) {
            System.out.println("Ошибка: отсутствует файл settings.json в директории " + directory);
        }

        return dbExists && settingsExists;
    }
}
