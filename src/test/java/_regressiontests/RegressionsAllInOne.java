package _regressiontests;

import _testutils.TestHelpers;
import core.Helpers;
import core.Main;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RegressionsAllInOne {

    /**
     * Issue: Markers inside code blocks are processed normally, so that hashes are recognized as headers
     */
    @Test
    public void codeBlockProblem() {

        String[] args = new String[]{"-p=" + TestHelpers.getResourcePath("codeblock_bug")};
        Main.main(args);

        String indexFile = Helpers.getFileAsString(TestHelpers.getResourcePath("codeblock_bug/readme.md"));
        assertThat(indexFile.contains("readme.md#shell-commands"), is(false));
        assertThat(indexFile.contains("001_Example/readme.md#not-a-header"), is(false));

        String subFile = Helpers.getFileAsString(TestHelpers.getResourcePath("codeblock_bug/001_Example/readme.md"));
        assertThat(subFile.contains("<!--- FILE_TOC -->\n[1. header1](readme.md#header1)<br>"), is(false));
        assertThat(subFile.contains("001_Example/readme.md#not-a-header"), is(false));
    }

}
