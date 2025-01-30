package com.ury.service;

import com.ury.model.Db;
import com.ury.model.Settings;
import com.ury.report.CreditReporter;
import com.ury.scanner.FileScanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class MainService {
    public void run() throws IOException {
        Path dataDirectory = Path.of("task-1", "data");
        FileScanner fileScanner = new FileScanner();

        boolean areRequiredFilesPresent = fileScanner.validateRequiredFiles(dataDirectory);
        if (!areRequiredFilesPresent) {
            System.out.println("Необходимо добавить обязательные файлы в папку data.");
            return;
        }

        try {
            List<Path> dbFiles = fileScanner.findDbFiles(dataDirectory);
            System.out.println("Найдены файлы:");
            dbFiles.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Ошибка при сканировании директории: " + e.getMessage());
        }

        SettingsReader settingsReader = new SettingsReader();
        Settings settings = settingsReader.readSettings();

        List<String> useDepartments = settings.getUseDepartments();

        JsonProcessor jsonProcessor = new JsonProcessor(fileScanner);

        try {
            if (fileScanner.validateRequiredFiles(dataDirectory)) {
                jsonProcessor.processFiles(dataDirectory, useDepartments);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка при обработке файлов.");
        }

        Db mainDb = jsonProcessor.readDbFromFile(Path.of("task-1/data/db.json"));
        CreditReporter creditReporter = new CreditReporter(settings);
        creditReporter.printFormattedCredits(mainDb);
    }
}
