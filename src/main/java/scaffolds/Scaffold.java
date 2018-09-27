package scaffolds;

import core.Config;
import core.Error;

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
            throw new Error("Couldn't create '" + scaffoldName + "'! Does it already exists? Do you have sufficient permissions? Path must exist!");
        }

        // Root folder
        createFolder(scaffoldPath, "_img");
        createFolder(scaffoldPath, "_res");
        createReadme(scaffoldPath, Config.PROJECT_TOC_MARKER);
        createGitDocMarker(scaffoldPath, Config.GITDOC_FOLDER_FILE);

        // Subfolder
        Path subPath = scaffoldPath.resolve("001_Example");
        createFolder(subPath, "");
        createFolder(subPath, "_img");
        createFolder(subPath, "_res");
        createReadme(subPath, Config.FILE_TOC_MARKER);
    }

    protected static void createFolder(Path rootPath, String folderPath) {
        Path path = rootPath.resolve(folderPath);
        if (!path.toFile().mkdir()) {
            throw new Error("Couldn't create '" + path + "'! Does it already exists? Do you have sufficient permissions? Path must exist!");
        }
    }

    protected static void createReadme(Path path, String tocMarker) {
        try (BufferedWriter writer = createWriter(Paths.get(path.toString() + "/readme.md"))) {
            writer.write(tocMarker);
        } catch (IOException e) {
            throw new Error("Couldn't create file for scaffold: " + path);
        }
    }

    protected static void createGitDocMarker(Path path, String markerFileName) {
        try (BufferedWriter writer = createWriter(Paths.get(path.toString() + "/" + markerFileName))) {
            writer.write("");
        } catch (IOException e) {
            throw new Error("Couldn't create file for scaffold: " + path);
        }
    }

    protected static BufferedWriter createWriter(Path path) {
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
