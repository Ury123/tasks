package com.ury.scanner;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileScanner {

    private static final String DB_FILE = "db.json";
    private static final String SETTINGS_FILE = "settings.json";

    public List<Path> findDbFiles(Path directory) throws IOException {
        log.info("Поиск файлов db_*.json в директории: {}", directory);
        return Files.walk(directory)
                .filter(path -> path.getFileName().toString().matches("db_.*\\.json"))
                .collect(Collectors.toList());
    }

    public boolean validateRequiredFiles(Path directory) {
        log.info("Проверка наличия обязательных файлов в директории: {}", directory);
        Path dbFile = directory.resolve(DB_FILE);
        Path settingsFile = directory.resolve(SETTINGS_FILE);

        boolean dbExists = Files.exists(dbFile);
        boolean settingsExists = Files.exists(settingsFile);

        if (!dbExists) {
            log.error("Отсутствует файл {} в директории {}", DB_FILE, directory);
        }
        if (!settingsExists) {
            log.error("Отсутствует файл {} в директории {}", SETTINGS_FILE, directory);
        }
        if (dbExists && settingsExists) {
            log.info("Все обязательные файлы присутствуют.");
        }

        return dbExists && settingsExists;
    }
}
