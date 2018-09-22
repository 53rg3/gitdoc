package core;

import core.Config.TocType;
import models.MarkDownFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class TocTree {

    private static final Pattern everythingButHashesPattern = Pattern.compile("[^#]");
    private static final Pattern onlyHashesPattern = Pattern.compile("[#]");
    private final List<AtomicInteger> numerationList = new ArrayList<>();
    private final List<String> toc;

    public TocTree(List<MarkDownFile> list) {
        this.resetNumerationList();

        this.toc = this.createTocTree(list);
    }

    public TocTree(MarkDownFile markDownFile) {
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

    private String createNumeration(String heading) {
        Objects.requireNonNull(heading, "heading must not be null");

        String cleanHeading = onlyHashesPattern.matcher(heading).replaceAll("").trim();
        int blocks = extractHashes(heading).length();
        StringBuilder builder = new StringBuilder();

        // Add indention
        builder.append(this.getIndention(blocks));

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

        return builder.toString();
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
                result.add(this.createNumeration(heading));
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

        return builder.toString();
    }
}
