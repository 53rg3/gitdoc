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

public class GlossaryTest {

    @AfterClass
    public static void tearDown() {
//        try {
//            Files.walk(Paths.get(TestHelpers.getResourcePath(".").toString() + "/glossary"))
//                    .sorted(Comparator.reverseOrder())
//                    .forEach(path -> {
//                        path.toFile().delete();
//                    });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void create() {
        String[] args = new String[]{"-g=test-classes/glossary"};
        Main.main(args);
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary/readme.md").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary/A.md").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary/F.md").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary/M.md").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary/S.md").exists(), is(true));
        assertThat(new File("/home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/glossary/Z.md").exists(), is(true));
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
