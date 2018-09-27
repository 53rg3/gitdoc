package core;

import _testutils.TestHelpers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MainTest {

    @BeforeClass
    public static void before() {
        File file = getMarker();
        if (file.exists()) {
            throw new IllegalStateException("\n" +
                    "Tests were executed before and have mutated the resource files in 'target/test-classes/gitdoc_folder'.\n" +
                    "Rebuild the project, because I don't want to waste time to create a proper solution.");
        }
    }

    @AfterClass
    public static void after() {
        File file = getMarker();
        try {
            if (!file.createNewFile()) {
                throw new IllegalStateException("Attention: Couldn't create marker file: 'NEED_TO_REBUILD_PROJECT'");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't create file 'NEED_TO_REBUILD_PROJECT'", e);
        }
    }

    private static File getMarker() {
        String path = TestHelpers.getResourcePath("not_a_gitdoc_folder").toString();
        return new File(path + "/NEED_TO_REBUILD_PROJECT");
    }


    @Test
    public void call() {

        String[] args = new String[]{"-p=" + TestHelpers.getResourcePath("gitdoc_folder")};
        Main.main(args);

        boolean hasMarker = Config.projectTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/readme.md"))).find();
        assertThat(hasMarker, is(true));

        hasMarker = Config.projectTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/001_FirstChapter/readme.md"))).find();
        assertThat(hasMarker, is(false));
        hasMarker = Config.fileTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/001_FirstChapter/readme.md"))).find();
        assertThat(hasMarker, is(true));

        hasMarker = Config.projectTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/002_SecondChapter/readme.md"))).find();
        assertThat(hasMarker, is(false));
        hasMarker = Config.fileTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/002_SecondChapter/readme.md"))).find();
        assertThat(hasMarker, is(false));

        hasMarker = Config.projectTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/003_ThirdChapter/readme.md"))).find();
        assertThat(hasMarker, is(false));
        hasMarker = Config.fileTocPattern.matcher(Helpers.getFileAsString(TestHelpers.getResourcePath("gitdoc_folder/003_ThirdChapter/readme.md"))).find();
        assertThat(hasMarker, is(true));
    }

    @Test
    public void call_asGlossary() {
        String[] args = new String[]{"-p=" + TestHelpers.getResourcePath("glossary_mock")};
        Main.main(args);
        String indexReadme = Helpers.getFileAsString(TestHelpers.getResourcePath("glossary_mock/readme.md"));
        assertThat(indexReadme.contains("[A (6)](A.md)<br>"), is(true));
        assertThat(indexReadme.contains("[B (4)](B.md)<br>"), is(true));
        assertThat(indexReadme.contains("[P (0)](P.md)<br>"), is(true));
        assertThat(indexReadme.contains("[V (0)](V.md)<br>"), is(true));
    }

}
