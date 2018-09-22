package core;

import _testutils.TestHelpers;
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
    public void createNumeration() {
        ProjectStructure projectStructure = new ProjectStructure(TestHelpers.getResourcePath("gitdoc_folder"));

        // Print
//        int index = 0;
//        for (String line : projectStructure.getTocTree().get()) {
//            System.out.println(index++ + " - " + line);
//        }

        List<String> toc = projectStructure.getTocTree().get();

        assertThat(toc.get(0),
                is("1. First Chapter"));
        assertThat(toc.get(2),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2 Sub-Heading 3"));
        assertThat(toc.get(5),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2.3 Sub-Sub-Heading 3"));
        assertThat(toc.get(8),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.2.3.3 Sub-Sub-Sub-Heading 3"));
        assertThat(toc.get(9),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3 Sub-Heading 2"));
        assertThat(toc.get(13),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3.2.2 Sub-Sub-Sub-Heading 3"));
        assertThat(toc.get(14),
                is("2. Second Chapter"));
        assertThat(toc.get(17),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1.1.1 Sub-Sub-Sub-Heading"));
        assertThat(toc.get(18),
                is("3. Third Chapter"));
        assertThat(toc.get(21),
                is("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.1.1.1 Sub-Sub-Sub-Heading"));

    }


}
