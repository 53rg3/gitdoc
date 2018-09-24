package models;

import core.Config;
import core.Helpers;
import core.Report;
import core.TocTree;

import java.net.MalformedURLException;
import java.net.URL;
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
    private final Path pathToFile;
    private final TocTree tocTree;
    private boolean hasTocMarker = false;

    public MarkDownFile(Path pathToFile) {
        this.pathToFile = pathToFile;
        this.parentFolder = pathToFile.getParent().toString();

        // Remove codeblocks
        String fileAsString = Helpers.getFileAsString(pathToFile);
        fileAsString = Config.codeBlockPattern
                .matcher(fileAsString)
                .replaceAll("");

        // Read line by line
        Stream.of(fileAsString.split("\n"))
                .peek(this.lookForTocMarker())
                .filter(this.doesLineStartWithHash())
                .filter(this.shouldBeExcluded())
                .forEach(this.addToHeadings());

        this.tocTree = new TocTree(this);
    }

    private Consumer<String> lookForTocMarker() {
        return line -> {
            if (Config.tocFinderPattern.matcher(line).find()) {
                this.hasTocMarker = true;
            }
        };
    }

    private Predicate<String> doesLineStartWithHash() {
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
            report.brokenRefs(this.pathToFile, brokenRefs);
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

    public Path getPathToFile() {
        return this.pathToFile;
    }

}
