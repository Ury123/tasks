package com.ury.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ury.adapter.LocalDateAdapter;
import com.ury.model.Db;
import com.ury.scanner.FileScanner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonProcessor {
    private final Gson gson;
    private final FileScanner fileScanner;

    public JsonProcessor(FileScanner fileScanner) {
        this.fileScanner = fileScanner;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    public Db readDbFromFile(Path filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath.toFile())) {
            return gson.fromJson(reader, Db.class);
        }
    }

    public void writeDbToFile(Path filePath, Db db) throws IOException {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(db, writer);
        }
    }

    public void clearFile(Path filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write("{}");
        }
    }

    public void processFiles(Path directory, List<String> useDepartments) throws IOException {
        List<Path> dbFiles;

        if (useDepartments == null) {

            dbFiles = fileScanner.findDbFiles(directory);
        } else if (useDepartments.isEmpty()) {

            System.out.println("useDepartments пуст, файлы не будут обработаны.");
            return;
        } else {

            dbFiles = useDepartments.stream()
                    .map(department -> directory.resolve("db_" + department + ".json"))
                    .filter(path -> Files.exists(path))
                    .collect(Collectors.toList());
        }

        if (dbFiles.isEmpty()) {
            System.out.println("Нет файлов для обработки.");
            return;
        }

        Path mainDbFilePath = directory.resolve("db.json");
        Db mainDb;

        if (Files.exists(mainDbFilePath)) {
            mainDb = readDbFromFile(mainDbFilePath);
        } else {
            mainDb = new Db(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        for (Path dbFile : dbFiles) {
            System.out.println("Обрабатывается файл: " + dbFile.getFileName());

            Db departmentDb = readDbFromFile(dbFile);

            mainDb.getUsers().addAll(departmentDb.getUsers());
            mainDb.getCredits().addAll(departmentDb.getCredits());
            mainDb.getDiscounts().addAll(departmentDb.getDiscounts());
            mainDb.getEvents().addAll(departmentDb.getEvents());
            mainDb.getTransactions().addAll(departmentDb.getTransactions());


            clearFile(dbFile);
        }

        writeDbToFile(mainDbFilePath, mainDb);

        System.out.println("Данные успешно обработаны и записаны в db.json.");
    }
}
