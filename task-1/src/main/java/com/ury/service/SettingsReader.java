package com.ury.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.ury.adapter.LocalDateAdapter;
import com.ury.model.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class SettingsReader {
    private static final String SETTINGS_FILE = "task-1/data/settings.json";

    private final Gson gson;

    public SettingsReader() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public Settings readSettings() throws IOException {

        Path path = Paths.get(SETTINGS_FILE);

        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, Settings.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Ошибка синтаксиса JSON в файле " + SETTINGS_FILE, e);
        }
    }
}
