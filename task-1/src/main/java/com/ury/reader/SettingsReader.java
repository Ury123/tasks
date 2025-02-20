package com.ury.reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.ury.adapter.LocalDateAdapter;
import com.ury.model.Settings;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Slf4j
public class SettingsReader {
    private static final String SETTINGS_FILE = "task-1/resources/Data/settings.json";

    private final Gson gson;

    public SettingsReader() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public Settings readSettings() throws IOException {

        Path path = Paths.get(SETTINGS_FILE);
        log.info("Чтение настроек из файла: {}", SETTINGS_FILE);

        try (FileReader reader = new FileReader(path.toFile())) {
            Settings settings = gson.fromJson(reader, Settings.class);
            log.info("Настройки успешно загружены");
            return settings;
        } catch (JsonSyntaxException e) {
            log.error("Ошибка синтаксиса JSON в файле {}", SETTINGS_FILE, e);
            throw new IOException("Ошибка синтаксиса JSON в файле " + SETTINGS_FILE, e);
        }
    }
}
