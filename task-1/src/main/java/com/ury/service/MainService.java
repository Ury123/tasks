package com.ury.service;

import com.ury.dto.DbDto;
import com.ury.json.JsonProcessor;
import com.ury.model.Settings;
import com.ury.reader.SettingsReader;
import com.ury.report.CreditReporter;
import com.ury.scanner.FileScanner;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class MainService {
    public void run() throws IOException {
        Path dataDirectory = Path.of("task-1", "resources", "Data");
        FileScanner fileScanner = new FileScanner();

        boolean areRequiredFilesPresent = fileScanner.validateRequiredFiles(dataDirectory);
        if (!areRequiredFilesPresent) {
            log.error("Необходимо добавить обязательные файлы в папку resources.");
            return;
        }

        try {
            List<Path> dbFiles = fileScanner.findDbFiles(dataDirectory);
            log.info("Найдены файлы:");
            log.info(dbFiles.toString());
        } catch (Exception e) {
            log.error("Ошибка при сканировании директории: {}", e.getMessage());
        }

        SettingsReader settingsReader = new SettingsReader();
        Settings settings = settingsReader.readSettings();

        List<String> useDepartments = settings.getUseDepartments();

        JsonProcessor jsonProcessor = new JsonProcessor(fileScanner);

        try {
            if (fileScanner.validateRequiredFiles(dataDirectory)) {
                log.info("Начало обработки файлов");
                jsonProcessor.processFiles(dataDirectory, useDepartments);
                log.info("Файлы успешно обработаны.");
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при обработке файлов: {}", e.getMessage(), e);
        }

        DbDto mainDb = jsonProcessor.readDbFromFile(Path.of("task-1/resources/Data/db.json"));
        CreditReporter creditReporter = new CreditReporter(settings);
        creditReporter.printFormattedCredits(mainDb);
    }
}
