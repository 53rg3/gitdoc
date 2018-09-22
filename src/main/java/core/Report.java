package core;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class Report {

    private final StringBuilder tocReport = new StringBuilder();
    private final StringBuilder refReport = new StringBuilder();
    private final AtomicInteger hasTocCount = new AtomicInteger();
    private final AtomicInteger noTocCount = new AtomicInteger();

    Report() {
        this.tocReport.append(Config.tocsHeader);
        this.refReport.append(Config.refsHeader);
    }

    public void hasToc(Path path) {
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

    private String getTocReportAsString() {
        String counters = "\nCOUNTS:\n" +
                "Files with TOCs   : " + this.hasTocCount.get() + "\n" +
                "Files without TOCs: " + this.noTocCount.get() + "\n";
        this.tocReport.append(counters);
        return this.tocReport.toString();
    }

    public String getReport() {
        return this.getTocReportAsString() + this.refReport.toString();
    }
}
