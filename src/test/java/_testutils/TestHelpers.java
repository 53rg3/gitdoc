package _testutils;

import java.net.URL;
import java.nio.file.*;

public class TestHelpers {

    public static final Path TEST_FILE = Paths.get("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/gitdoc_folder/002_SecondChapter/readme.md");

    public static Path getResourcePath(String pathToResource) {
        final URL path = ClassLoader.getSystemResource(pathToResource);
        if (path == null) {
            throw new IllegalStateException("\n" +
                    "Can't find '" + pathToResource + "', check existence in /test/resources!\n" +
                    "Don't use the absolute path! Use: \"some/folder/someFile.csv\"");
        }
        return Paths.get(path.getPath());
    }

}
