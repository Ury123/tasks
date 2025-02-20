package com.ury.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ury.adapter.LocalDateAdapter;
import com.ury.dto.DbDto;
import com.ury.scanner.FileScanner;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JsonProcessor {

    private static final String MAIN_DB_FILE = "db.json";

    private final Gson gson;
    private final FileScanner fileScanner;

    public JsonProcessor(FileScanner fileScanner) {
        this.fileScanner = fileScanner;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    public DbDto readDbFromFile(Path filePath) throws IOException {
        log.info("Чтение файла базы данных: {}", filePath);
        try (FileReader reader = new FileReader(filePath.toFile())) {
            return gson.fromJson(reader, DbDto.class);
        }
    }

    public void writeDbToFile(Path filePath, DbDto db) throws IOException {
        log.info("Запись базы данных в файл: {}", filePath);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(db, writer);
        }
    }

    public void clearFile(Path filePath) throws IOException {
        log.info("Очистка файла: {}", filePath);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write("{}");
        }
    }

    public void processFiles(Path directory, List<String> useDepartments) throws IOException {
        log.info("Начало обработки файлов в директории: {}", directory);
        List<Path> dbFiles;

        if (useDepartments == null) {
            dbFiles = fileScanner.findDbFiles(directory);
            log.info("Обнаружены файлы: {}", dbFiles);
        } else if (useDepartments.isEmpty()) {
            log.info("useDepartments пуст, файлы не будут обработаны.");
            return;
        } else {

            dbFiles = useDepartments.stream()
                    .map(department -> directory.resolve("db_" + department + ".json"))
                    .filter(path -> Files.exists(path))
                    .collect(Collectors.toList());
        }

        if (dbFiles.isEmpty()) {
            log.info("Нет файлов для обработки.");
            return;
        }

        Path mainDbFilePath = directory.resolve(MAIN_DB_FILE);
        DbDto mainDb;

        if (Files.exists(mainDbFilePath)) {
            mainDb = readDbFromFile(mainDbFilePath);
        } else {
            mainDb = new DbDto(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        for (Path dbFile : dbFiles) {
            log.info("Обрабатывается файл: {}", dbFile.getFileName());

            DbDto departmentDb = readDbFromFile(dbFile);

            mainDb.getUsers().addAll(departmentDb.getUsers());
            mainDb.getCredits().addAll(departmentDb.getCredits());
            mainDb.getDiscounts().addAll(departmentDb.getDiscounts());
            mainDb.getEvents().addAll(departmentDb.getEvents());
            mainDb.getTransactions().addAll(departmentDb.getTransactions());


            clearFile(dbFile);
        }

        writeDbToFile(mainDbFilePath, mainDb);

        log.info("Данные успешно обработаны и записаны в db.json.");
    }
}
