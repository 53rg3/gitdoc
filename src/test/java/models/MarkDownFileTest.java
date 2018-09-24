package models;

import _testutils.TestHelpers;
import core.Helpers;
import core.Report;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MarkDownFileTest {

    @Test
    public void create() {

        MarkDownFile markDownFile = new MarkDownFile(TestHelpers.getGitFolder(), TestHelpers.TEST_FILE);

        // Headings
        List<String> headings = markDownFile.getHeadings();
        assertThat(headings.size(), is(4));
        assertThat(headings.get(0), is("# Second Chapter"));
        assertThat(headings.get(1), is("## Sub-Heading"));
        assertThat(headings.get(2), is("### Sub-Sub-Heading"));
        assertThat(headings.get(3), is("#### Sub-Sub-Sub-Heading"));

        // Parent folder
        assertThat(markDownFile.getParentFolder().endsWith("/gitdoc_folder/002_SecondChapter"), is(true));

    }

    @Test
    public void evaluateReferences() {
        MarkDownFile markDownFile = new MarkDownFile(TestHelpers.getGitFolder(), TestHelpers.getResourcePath("gitdoc_folder/004_Refs/readme.md"));
        Report report = new Report();
        markDownFile.evaluateReferences(Helpers.getFileAsString(markDownFile.getPathToFile()), report);
        assertThat(report.getReport().contains("Valid references  : 5"), is(true));
        assertThat(report.getReport().contains("Broken references : 2"), is(true));
    }
}
