package models;

import core.Config;
import core.Report;
import core.TocTree;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class MarkDownFile {

    private final List<String> headings = new ArrayList<>();
    private final String parentFolder;
    private final Path gitdocFolder;
    private final Path path;
    private final TocTree tocTree;
    private boolean hasTocMarker = false;

    public MarkDownFile(Path gitdocFolder, Path pathToFile) {
        this.path = pathToFile;
        this.gitdocFolder = gitdocFolder;
        this.parentFolder = pathToFile.getParent().toString();
        try (Stream<String> stream = Files.lines(pathToFile)) {
            stream
                    .peek(this.lookForTocMarker())
                    .filter(this.isLineStartingWithHash())
                    .filter(this.shouldBeExcluded())
                    .forEach(this.addToHeadings());
        } catch (IOException e) {
            throw new IllegalStateException("Can't read file: " + pathToFile, e);
        }
        this.tocTree = new TocTree(this);
    }

    private Consumer<String> lookForTocMarker() {
        return line -> {
            if (Config.tocFinderPattern.matcher(line).find()) {
                this.hasTocMarker = true;
            }
        };
    }

    private Predicate<String> isLineStartingWithHash() {
        return line -> line.trim().startsWith("#");
    }

    private Predicate<String> shouldBeExcluded() {
        // Exclude ##### headings and above
        return line -> TocTree.extractHashes(line).length() < 5;
    }

    public void evaluateReferences(String fileAsString, Report report) {
        Matcher matcher = Config.refPattern.matcher(fileAsString);
        List<String> brokenRefs = new ArrayList<>();
        while (matcher.find()) {

            String ref = Config.urlFragmentPattern.matcher(matcher.group(2)).replaceAll("");

            // Skip references to external resources
            if (this.isURL(ref)) {
                continue;
            }

            if (Paths.get(this.parentFolder, ref).toFile().exists()) {
                report.validRef();
            } else {
                brokenRefs.add(matcher.group(1));
            }
        }
        if (!brokenRefs.isEmpty()) {
            report.brokenRefs(this.path, brokenRefs);
        }
    }

    private boolean isURL(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private Consumer<String> addToHeadings() {
        return line -> this.headings.add(line.trim());
    }

    public List<String> getHeadings() {
        return this.headings;
    }

    public String getParentFolder() {
        return this.parentFolder;
    }

    public boolean hasTocMarker() {
        return this.hasTocMarker;
    }

    public TocTree getTocTree() {
        return this.tocTree;
    }

    public Path getPath() {
        return this.path;
    }

}
