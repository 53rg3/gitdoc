package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helpers {
    private Helpers() {
    }

    public static String getFileAsString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read file: " + path, e);
        }
    }

}
