package core;

import core.Config.TocType;
import core.ProjectStructure.Mode;
import models.MarkDownFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class TocTree {

    private static final Pattern everythingButHashesPattern = Pattern.compile("[^#]");
    private static final Pattern onlyHashesPattern = Pattern.compile("[#]");
    private static final Pattern specialChars = Pattern.compile("[^\\d\\w\\s-]");
    private final List<AtomicInteger> numerationList = new ArrayList<>();
    private final List<String> toc;
    private final Path gitdocFolder;
    private final Mode mode;

    public TocTree(List<MarkDownFile> list, Path gitdocFolder, Mode mode) {
        Objects.requireNonNull(mode, "'mode' must not be null.");

        this.mode = mode;
        this.gitdocFolder = gitdocFolder;
        this.resetNumerationList();
        this.toc = this.createTocTree(list);
    }

    public TocTree(MarkDownFile markDownFile) {
        this.mode = Mode.DUMMY_MODE;
        this.gitdocFolder = markDownFile.getPathToFile();
        this.resetNumerationList();
        this.toc = this.createTocTree(Collections.singletonList(markDownFile));
    }

    private void resetNumerationList() {
        this.numerationList.add(new AtomicInteger(0));
        this.numerationList.add(new AtomicInteger(0));
        this.numerationList.add(new AtomicInteger(0));
        this.numerationList.add(new AtomicInteger(0));
        this.numerationList.add(new AtomicInteger(0));
    }

    public static String extractHashes(String heading) {
        return everythingButHashesPattern.matcher(heading).replaceAll("");
    }

    /**
     * Proper GitHub compliant MarkDown reference: [1.1 Anchor Text](path/to/ref/readme.md#Anchor-Text)
     */
    private String createReference(String heading, Path path) {
        Objects.requireNonNull(heading, "heading must not be null");

        String cleanHeading = onlyHashesPattern.matcher(heading).replaceAll("").trim();
        int blocks = extractHashes(heading).length();
        StringBuilder builder = new StringBuilder();

        // Add link text
        this.createLinkText(builder, cleanHeading, blocks);

        // Add reference destination
        this.createReferenceDestination(builder, cleanHeading, path);

        // Prepend indention
        builder.insert(0, this.getIndention(blocks));

        return builder.toString();
    }

    private void createReferenceDestination(StringBuilder builder, String cleanHeading, Path path) {
        // Open reference destination bracket
        builder.append("(");

        // Create relative path from gitdocFolder
        builder.append(this.gitdocFolder.relativize(path));

        // Create link fragment
        if (!this.mode.equals(Mode.GLOSSARY)) {
            builder.append("#");
            builder.append(this.createAnchor(cleanHeading));
        }

        // Close reference destination bracket
        builder.append(")");

        // Add <br>
        builder.append("<br>");
    }

    private void createLinkText(StringBuilder builder, String cleanHeading, int blocks) {

        // Skip if glossary, because numeration is useless
        if (this.mode.equals(Mode.GLOSSARY)) {
            builder.append("[");
            builder.append(cleanHeading);
            builder.append("]");
            return;
        }

        // Open link text bracket
        builder.append("[");

        // Create numeration
        for (int index = 0; index < blocks; index++) {
            if (index == blocks - 1) {
                builder.append(this.numerationList.get(index).incrementAndGet());
            } else {
                builder.append(this.numerationList.get(index).get());
            }
            builder.append(".");
        }

        // Reset last counter block
        if (blocks < this.numerationList.size()) {
            this.numerationList.get(blocks).set(0);
        }

        // Delete last "." and add heading string
        if (blocks > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }

        // Add heading
        builder.append(" ");
        builder.append(cleanHeading);

        // Close anchor text bracket
        builder.append("]");

    }

    private String getIndention(int blocks) {
        StringBuilder indention = new StringBuilder();
        for (int i = 0; i < blocks - 1; i++) {
            indention.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        return indention.toString();
    }


    private List<String> createTocTree(List<MarkDownFile> list) {
        List<String> result = new ArrayList<>();

        for (MarkDownFile markDownFile : list) {
            for (String heading : markDownFile.getHeadings()) {
                result.add(this.createReference(heading, markDownFile.getPathToFile()));
            }
        }

        return result;
    }

    public List<String> get() {
        return this.toc;
    }

    public String getAsString(TocType tocType) {
        StringBuilder builder = new StringBuilder();
        switch (tocType) {
            case FILE_TOC:
                builder.append(Config.FILE_TOC_MARKER);
                break;
            case PROJECT_TOC:
                builder.append(Config.PROJECT_TOC_MARKER);
                break;
            default:
                throw new IllegalStateException("TocType not recognized");
        }
        builder.append("\n");

        for (String line : this.toc) {
            builder.append(line);
            builder.append("\n");
        }
        builder.append(Config.TOC_END_MARKER);
        builder.append("\n");

        return builder.toString();
    }

    private String createAnchor(String heading) {
        heading = specialChars.matcher(heading).replaceAll("");
        return heading
                .replace(".", "")
                .replace(" ", "-")
                .toLowerCase();
    }
}
