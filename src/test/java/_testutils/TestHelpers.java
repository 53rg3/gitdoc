package _testutils;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestHelpers {

    public static final Path TEST_FILE = Paths.get(getResourcePath("gitdoc_folder/002_SecondChapter/readme.md").toString());

    public static Path getResourcePath(String pathToResource) {
        final URL path = ClassLoader.getSystemResource(pathToResource);
        if (path == null) {
            throw new IllegalStateException("\n" +
                    "Can't find '" + pathToResource + "', check existence in /test/resources!\n" +
                    "Don't use the absolute path! Use: \"some/folder/someFile.csv\"");
        }
        return Paths.get(path.getPath());
    }

    public static Path getGitFolder() {
        return getResourcePath("gitdoc_folder");
    }

}
