package core;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import scaffolds.Glossary;
import scaffolds.Scaffold;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;

@Command(description = "\n" +
        "Helps with the creation of documentation for Git remote repositories:\n\n" +
        "1. Creates ToC trees in every markdown file, based on the headings\n" +
        "2. Checks validity of references for internal links and images\n\n" +
        "Options:",
        name = "gitdoc", mixinStandardHelpOptions = true, version = "gitdoc 0.1")
public class Main implements Callable<Void> {

    private static final String PARAM_NOT_SET = "PARAM_NOT_SET";

    @Option(names = {"-p", "--path"}, description = "Absolute path to your doc folder. Default is the folder from which gitdoc is run from.")
    private Path gitDocFolder = getExecutionPath();

    @Option(names = {"-s", "--scaffold"}, description = "Creates a notebook as scaffold for quick startup.")
    private String scaffoldFolderName = PARAM_NOT_SET;

    @Option(names = {"-g", "--glossary"}, description = "Creates a glossary as scaffold for quick startup.")
    private String glossaryFolderName = PARAM_NOT_SET;

    public static void main(final String[] args) {
        CommandLine.call(new Main(), args);
    }

    @Override
    public Void call() {

        this.throwIfParameterError();

        this.printRunningIn();
        if (!this.scaffoldFolderName.equals(PARAM_NOT_SET)) {
            Scaffold.create(this.gitDocFolder + "/" + this.scaffoldFolderName);
        } else if (!this.glossaryFolderName.equals(PARAM_NOT_SET)) {
            Glossary.create(this.gitDocFolder + "/" + this.glossaryFolderName);
        } else {
            ProjectStructure projectStructure = new ProjectStructure(this.gitDocFolder);
            projectStructure.run();
            projectStructure.printReport();
        }

        return null;
    }

    private void throwIfParameterError() {
        if (!this.scaffoldFolderName.equals(PARAM_NOT_SET) && !this.glossaryFolderName.equals(PARAM_NOT_SET)) {
            throw new Error("Can't run with parameters -s & -g concurrently.");
        }
    }

    private void printRunningIn() {
        Helpers.print("=====================================================");
        Helpers.print("Running in: " + this.gitDocFolder);
        Helpers.print("=====================================================");
    }

    private static Path getExecutionPath() {
        try {
            return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}
