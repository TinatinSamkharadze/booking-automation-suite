package ge.tbc.testautomation.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileUtil {
    public static String loadJson(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read mock JSON file", e);
        }
    }
}
