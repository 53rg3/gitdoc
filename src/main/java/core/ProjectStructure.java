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

    public ProjectStructure(Path gitdocFolder) {
        this.structure = this.createStructure(gitdocFolder);
        this.tocTree = new TocTree(this.structure, gitdocFolder);
    }

    public void run() {
        for (MarkDownFile markDownFile : this.structure) {
            String fileAsString = Helpers.getFileAsString(markDownFile.getPathToFile());

            markDownFile.evaluateReferences(fileAsString, this.report);

            this.writeToc(markDownFile, fileAsString);
        }

    }

    private List<MarkDownFile> createStructure(Path gitdocFolder) {

        this.throwIfNotGitDocFolder(gitdocFolder);

        List<MarkDownFile> list = new ArrayList<>();
        Set<String> exclusions = new HashSet<>();

        // Root folder
        try (Stream<Path> stream = Files.walk(gitdocFolder, 1)) {
            stream
                    .filter(currPath -> currPath.toString().endsWith(".md"))
                    .sorted()
                    .forEach(currPath -> {
                        exclusions.add(currPath.toString());
                        list.add(new MarkDownFile(gitdocFolder, currPath));
                    });
        } catch (IOException e) {
            throw new IllegalStateException("Can't walk path: " + gitdocFolder);
        }

        // Subfolders
        try (Stream<Path> stream = Files.walk(gitdocFolder)) {
            stream
                    .filter(currPath -> currPath.toString().endsWith(".md"))
                    .filter(currPath -> !exclusions.contains(currPath.toString()))
                    .sorted()
                    .forEach(currPath -> list.add(new MarkDownFile(gitdocFolder, currPath)));
        } catch (IOException e) {
            throw new IllegalStateException("Can't walk path: " + gitdocFolder);
        }

        return list;
    }

    private void throwIfNotGitDocFolder(Path path) {

        File file = path.toFile();
        Objects.requireNonNull(file, "path must not be null");

        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Path: '" + path + "' is not a directory");
        }

        Stream.of(file.list())
                .filter(fileName -> fileName.equals(".gitdoc"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("\n" +
                        "Directory '" + path + "' is not a gitdoc directory. \n" +
                        "Must contain a file named '.gitdoc'"));
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
        System.out.println(this.report.getReport());
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
}
