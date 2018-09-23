package core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Scaffold {
    private Scaffold() {
    }

    public static void create(String scaffoldName) {
        Path scaffoldPath = Paths.get(scaffoldName);
        if (!scaffoldPath.toFile().mkdir()) {
            throw new IllegalStateException("Couldn't create '" + scaffoldName + "'! Does it already exists? Do you have sufficient permissions? Path must exist!");
        }

        // Root folder
        createFolder(scaffoldPath, "_img");
        createFolder(scaffoldPath, "_res");
        createReadme(scaffoldPath, Config.PROJECT_TOC_MARKER);
        createGitDocMarker(scaffoldPath, "");

        // Subfolder
        Path subPath = scaffoldPath.resolve("001_Example");
        createFolder(subPath, "");
        createFolder(subPath, "_img");
        createFolder(subPath, "_res");
        createReadme(subPath, Config.FILE_TOC_MARKER);
    }

    private static void createFolder(Path rootPath, String folderPath) {
        Path path = rootPath.resolve(folderPath);
        if (!path.toFile().mkdir()) {
            throw new IllegalStateException("Couldn't create '" + path + "'! Does it already exists? Do you have sufficient permissions? Path must exist!");
        }
    }

    private static void createReadme(Path path, String content) {
        try (BufferedWriter writer = createWriter(Paths.get(path.toString() + "/readme.md"))) {
            writer.write(content);
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create file for scaffold: " + path);
        }
    }

    private static void createGitDocMarker(Path path, String content) {
        try (BufferedWriter writer = createWriter(Paths.get(path.toString() + "/.gitdoc"))) {
            writer.write(content);
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create file for scaffold: " + path);
        }
    }

    private static BufferedWriter createWriter(Path path) {
        try {
            return Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW);
        } catch (final IOException e) {
            throw new IllegalStateException("Couldn't create writer for " + path, e);
        }
    }


}
