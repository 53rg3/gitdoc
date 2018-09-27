package core;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Report {

    private final StringBuilder tocReport = new StringBuilder();
    private final StringBuilder refReport = new StringBuilder();

    private boolean hasTocs = false;
    private boolean hasRefs = false;

    private final AtomicInteger hasTocCount = new AtomicInteger();
    private final AtomicInteger noTocCount = new AtomicInteger();
    private final AtomicInteger validRefCount = new AtomicInteger();
    private final AtomicInteger brokenRefCount = new AtomicInteger();

    public void hasToc(Path path) {
        if (!this.hasTocs) {
            this.hasTocs = true;
            this.tocReport.append(Config.TOCS_HEADER);
        }
        this.hasTocCount.incrementAndGet();
        this.tocReport.append("[+] Added TOC to  : ");
        this.tocReport.append(path);
        this.tocReport.append("\n");
    }

    public void noToc(Path path) {
        this.noTocCount.incrementAndGet();
        this.tocReport.append("[-] No TOC marker : ");
        this.tocReport.append(path);
        this.tocReport.append("\n");
    }

    public void brokenRefs(Path path, List<String> brokenRefs) {
        if (!this.hasRefs) {
            this.hasRefs = true;
            this.refReport.append(Config.REFS_HEADER);
        }
        this.brokenRefCount.addAndGet(brokenRefs.size());
        this.refReport.append("\n-----> Broken Refs in ");
        this.refReport.append(path);
        this.refReport.append("\n");
        brokenRefs.forEach(ref -> {
            this.refReport.append(ref);
            this.refReport.append("\n");
        });
    }

    public void validRef() {
        this.validRefCount.incrementAndGet();
    }

    private String getBreakdownsAsString() {
        String tocBreakdown = "\nTOC BREAKDOWN:\n" +
                "Files with TOCs   : " + this.hasTocCount.get() + "\n" +
                "Files without TOCs: " + this.noTocCount.get() + "\n";
        String refBreakdown = "\nREF BREAKDOWN:\n" +
                "Valid references  : " + this.validRefCount.get() + "\n" +
                "Broken references : " + this.brokenRefCount.get() + "\n";
        return Config.BREAKDOWN_HEADER + tocBreakdown + refBreakdown;
    }

    public String getReport() {
        return this.tocReport.toString() + this.refReport.toString() + this.getBreakdownsAsString();
    }
}
