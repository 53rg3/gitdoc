package core;

import _testutils.TestHelpers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReportTest {

    @Test
    public void create() {

        Report report = new Report();
        report.noToc(TestHelpers.getResourcePath("gitdoc_folder/001_FirstChapter/readme.md"));
        report.hasToc(TestHelpers.getResourcePath("gitdoc_folder/003_ThirdChapter/readme.md"));
        String reportAsString = report.getReport();
        System.out.println(reportAsString);
        assertThat(reportAsString.contains("[-] No TOC marker : /home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/gitdoc_folder/001_FirstChapter/readme.md"),
                is(true));
        assertThat(reportAsString.contains("[+] Added TOC to  : /home/cc/Desktop/Programming/Repos/gitdoc/target/test-classes/gitdoc_folder/003_ThirdChapter/readme.md"),
                is(true));

    }

}
