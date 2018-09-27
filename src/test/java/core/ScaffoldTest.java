package core;

import _testutils.TestHelpers;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScaffoldTest {

    @AfterClass
    public static void tearDown() {
        try {
            Files.walk(Paths.get(TestHelpers.getResourcePath(".").toString() + "/scaffold"))
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        path.toFile().delete();
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        String[] args = new String[]{"-s=test-classes/scaffold"};
        Main.main(args);
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/_img").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/001_Example").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/001_Example/_img").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/001_Example/_res").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/001_Example/readme.md").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/_res").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/scaffold/readme.md").exists(), is(true));
    }

    @Test(expected = Exception.class)
    public void create_folderAlreadyExists() {
        String[] args = new String[]{"-s=test-classes/gitdoc_folder"};
        Main.main(args);
    }

    @Test
    public void asdf() {
        try {
            Files.walk(Paths.get(TestHelpers.getResourcePath(".").toString() + "/scaffold")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
