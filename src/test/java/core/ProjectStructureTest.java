package core;

import _testutils.TestHelpers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectStructureTest {

    @Test
    public void walkGitDoc_success() {
        ProjectStructure projectStructure = new ProjectStructure(TestHelpers.getResourcePath("gitdoc_folder"));
        assertThat(projectStructure.getStructure().size(), is(6));
    }



    @Test(expected = Error.class)
    public void walkGitDoc_notAGitDocFolder() {
        new ProjectStructure(TestHelpers.getResourcePath("not_a_gitdoc_folder"));
    }

    @Test
    public void walkGitDoc_glossaryFolder() {
        new ProjectStructure(TestHelpers.getResourcePath("glossary_folder"));
    }

    @Test(expected = Error.class)
    public void walkGitDoc_notAFolder() {
        new ProjectStructure(TestHelpers.getResourcePath("not_a_gitdoc_folder/random_file"));
    }

}
