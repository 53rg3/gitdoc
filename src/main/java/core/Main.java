package core;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(description = "" +
        "Helps with the creation of documentation for Git remote repositories:\n\n" +
        "1. Creates ToC trees in every markdown file, based on the headings\n" +
        "2. Checks validity of references for internal links and images\n\n" +
        "Options:",
        name = "gitdoc", mixinStandardHelpOptions = true, version = "gitdoc 0.1")
public class Main implements Callable<Void> {

    @Option(names = {"-p", "--path"}, description = "Absolute gitDocFolder to your doc folder. Default is the folder from which gitdoc is run from.")
    private Path gitDocFolder = getExecutionPath();

    public static void main(final String[] args) {
        CommandLine.call(new Main(), args);
    }

    @Override
    public Void call() {
        ProjectStructure projectStructure = new ProjectStructure(this.gitDocFolder);
        projectStructure.run();
        projectStructure.printReport();

        return null;
    }

    private static Path getExecutionPath() {
        try {
            return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}
