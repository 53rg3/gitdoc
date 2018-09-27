package core;

import _testutils.TestHelpers;
import core.ProjectStructure.Mode;
import models.MarkDownFile;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TocTreeTest {


    @Test
    public void extractHashes() {

        String heading1 = "#### Sub-Sub-Sub-Heading";
        String heading2 = "## Sub-Heading";

        assertThat(TocTree.extractHashes(heading1), is("####"));
        assertThat(TocTree.extractHashes(heading2), is("##"));

    }

    @Test
    public void createReference_completeProjectStructure() {
        ProjectStructure projectStructure = new ProjectStructure(TestHelpers.getResourcePath("gitdoc_folder"));
        List<String> toc = projectStructure.getTocTree().get();

        assertThat(toc.get(0),
                is("[1. Index Heading](readme.md#index-heading)<br>"));
        assertThat(toc.get(2),
                is("[2. First Chapter](001_FirstChapter/readme.md#first-chapter)<br>"));
        assertThat(toc.get(6),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2.2 Sub-Sub-Heading 2](001_FirstChapter/readme.md#sub-sub-heading-2)<br>"));
        assertThat(toc.get(9),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2.3.2 Sub-Sub-Sub-Heading 2](001_FirstChapter/readme.md#sub-sub-sub-heading-2)<br>"));
        assertThat(toc.get(11),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.3 Sub-Heading 2](001_FirstChapter/readme.md#sub-heading-2)<br>"));
        assertThat(toc.get(13),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.3.2 Sub-Sub-Heading 1](001_FirstChapter/readme.md#sub-sub-heading-1)<br>"));
        assertThat(toc.get(16),
                is("[3. Second Chapter](002_SecondChapter/readme.md#second-chapter)<br>"));
        assertThat(toc.get(22),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[4.1.1 Sub-Sub-Heading](003_ThirdChapter/readme.md#sub-sub-heading)<br>"));
        assertThat(toc.get(24),
                is("[5. References](004_Refs/readme.md#references)<br>"));
        assertThat(toc.get(25),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[5.1 File in sub folder](004_Refs/sub/file_in_sub_folder.md#file-in-sub-folder)<br>"));

    }

    @Test
    public void createReference_singleMarkDownFile() {
        MarkDownFile markDownFile = new MarkDownFile(TestHelpers.getResourcePath("gitdoc_folder/001_FirstChapter/readme.md"), Mode.GITDOC);

        assertThat(markDownFile.getTocTree().get().get(0),
                is("[1. First Chapter](#first-chapter)<br>"));
        assertThat(markDownFile.getTocTree().get().get(2),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.2 Sub-Heading 3](#sub-heading-3)<br>"));
        assertThat(markDownFile.getTocTree().get().get(6),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.2.3.1 Sub-Sub-Sub-Heading 1](#sub-sub-sub-heading-1)<br>"));
        assertThat(markDownFile.getTocTree().get().get(9),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.3 Sub-Heading 2](#sub-heading-2)<br>"));
        assertThat(markDownFile.getTocTree().get().get(10),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.3.1 Sub-Sub-Heading 1](#sub-sub-heading-1)<br>"));
        assertThat(markDownFile.getTocTree().get().get(13),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.3.2.2 Sub-Sub-Sub-Heading 3](#sub-sub-sub-heading-3)<br>"));
    }


}
