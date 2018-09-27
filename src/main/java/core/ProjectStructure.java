package core;

import core.Config.TocType;
import models.MarkDownFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

public class ProjectStructure {

    private final List<MarkDownFile> structure;
    private final TocTree tocTree;
    private final Report report = new Report();
    private Mode mode;

    ProjectStructure(Path gitdocFolder) {
        this.structure = this.createStructure(gitdocFolder);
        this.tocTree = new TocTree(this.structure, gitdocFolder, this.mode);
    }

    public void run() {
        for (MarkDownFile markDownFile : this.structure) {
            String fileAsString = Helpers.getFileAsString(markDownFile.getPathToFile());

            markDownFile.evaluateReferences(fileAsString, this.report);

            this.writeToc(markDownFile, fileAsString);
        }

    }

    private List<MarkDownFile> createStructure(Path gitdocFolder) {

        this.mode = this.determineGitDocMode(gitdocFolder);

        List<MarkDownFile> list = new ArrayList<>();
        Queue<Path> pathQueue = new PriorityQueue<>();

        this.evaluateAndCollectFiles(gitdocFolder, list, pathQueue);
        while (!pathQueue.isEmpty()) {
            this.evaluateAndCollectFiles(pathQueue.poll(), list, pathQueue);
        }

        return list;
    }

    private void evaluateAndCollectFiles(Path nextPath, List<MarkDownFile> markDownFiles, Queue<Path> pathQueue) {
        try (Stream<Path> stream = Files.walk(nextPath, 1)) {
            stream
                    .sorted()
                    .forEach(currPath -> {
                        if (currPath.toString().endsWith(".md")) {
                            markDownFiles.add(new MarkDownFile(currPath, this.mode));
                        }
                        if (currPath.toFile().isDirectory() && currPath.compareTo(nextPath) != 0) {
                            pathQueue.offer(currPath);
                        }
                    });
        } catch (IOException e) {
            throw new IllegalStateException("Can't walk path: " + nextPath);
        }
    }

    private Mode determineGitDocMode(Path path) {

        File file = path.toFile();
        Objects.requireNonNull(file, "path must not be null");

        if (!file.isDirectory()) {
            throw new Error("Path: '" + path + "' is not a directory");
        }

        Optional<Mode> optional = Stream.of(file.list())
                .filter(fileName -> fileName.equals(Config.GITDOC_FOLDER_FILE) || fileName.equals(Config.GLOSSARY_FOLDER_FILE))
                .map(fileName -> {
                    if (fileName.equals(Config.GITDOC_FOLDER_FILE)) {
                        return Mode.GITDOC;
                    }
                    if (fileName.equals(Config.GLOSSARY_FOLDER_FILE)) {
                        return Mode.GLOSSARY;
                    }
                    throw new IllegalArgumentException("Can't determine mode. Marker file not implemented: " + fileName);
                })
                .findFirst();
        if (!optional.isPresent()) {
            throw new Error("\n" +
                    "Directory '" + path + "' is not a gitdoc directory. \n" +
                    "Must contain a file named '" + Config.GITDOC_FOLDER_FILE + "' or '" + Config.GLOSSARY_FOLDER_FILE + "'");
        }
        return optional.get();
    }

    private void writeToc(MarkDownFile markDownFile, String fileAsString) {
        if (markDownFile.hasTocMarker()) {

            this.report.hasToc(markDownFile.getPathToFile());

            try (BufferedWriter writer = this.createWriter(markDownFile.getPathToFile())) {

                // Replace TOCs
                fileAsString = Config.fileTocPattern
                        .matcher(fileAsString)
                        .replaceFirst(markDownFile.getTocTree().getAsString(TocType.FILE_TOC));
                fileAsString = Config.projectTocPattern
                        .matcher(fileAsString)
                        .replaceFirst(this.tocTree.getAsString(TocType.PROJECT_TOC));

                writer.write(fileAsString);

            } catch (IOException e) {
                throw new IllegalStateException("Couldn't write to file: " + markDownFile.getPathToFile(), e);
            }
        } else {
            this.report.noToc(markDownFile.getPathToFile());
        }
    }

    public void printReport() {
        Helpers.print(this.report.getReport());
    }

    private BufferedWriter createWriter(Path path) {
        try {
            return Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE);
        } catch (final IOException e) {
            throw new IllegalStateException("Couldn't createStructure writer for " + path, e);
        }
    }

    public TocTree getTocTree() {
        return this.tocTree;
    }

    public List<MarkDownFile> getStructure() {
        return this.structure;
    }

    public enum Mode {
        GITDOC,
        GLOSSARY,
        DUMMY_MODE
    }
}
