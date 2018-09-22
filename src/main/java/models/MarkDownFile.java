package models;

import core.Config;
import core.TocTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MarkDownFile {

    private final List<String> headings = new ArrayList<>();
    private final List<String> refs = new ArrayList<>();
    private final Path parentFolder;
    private final Path path;
    private final TocTree tocTree;
    private boolean hasTocMarker = false;


    public MarkDownFile(Path path) {
        this.path = path;
        this.parentFolder = path.getParent();
        try (Stream<String> stream = Files.lines(path)) {
            stream
                    .peek(this.lookForTocMarker())
                    .filter(this.isLineStartingWithHash())
                    .filter(this.shouldBeExcluded())
                    .forEach(this.addToHeadings());
        } catch (IOException e) {
            throw new IllegalStateException("Can't read file: " + path, e);
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

    private Consumer<String> addToHeadings() {
        return line -> this.headings.add(line.trim());
    }

    public List<String> getHeadings() {
        return this.headings;
    }

    public Path getParentFolder() {
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
