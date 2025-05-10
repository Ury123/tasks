package com.ury.json;

import com.ury.dto.DbDto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface JsonProcessor {

    DbDto readDbFromFile(Path filePath) throws IOException;

    void writeDbToFile(Path filePath, DbDto db) throws IOException;

    void clearFile(Path filePath) throws IOException;

    void processFiles(Path directory, List<String> useDepartments) throws IOException;
}
