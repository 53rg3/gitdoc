package core;

import org.junit.Test;

import static core.Config.FILE_TOC_MARKER;
import static core.Config.PROJECT_TOC_MARKER;
import static core.Config.TOC_END_MARKER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void tocPatterns() {

        String expectedResult = "" +
                "lorem ipsum\n" +
                "::::OK::::\n" +
                "lorem ipsum";
        String replacement = "::::OK::::";

        String existingToc = "lorem ipsum\n" +
                ""+FILE_TOC_MARKER+"\n" +
                "1. sadfh\n" +
                "2. asdfjh\n" +
                "3. kasdjfh\n" +
                ""+TOC_END_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.fileTocPattern.matcher(existingToc).replaceAll(replacement), is(expectedResult));

        existingToc = "lorem ipsum\n" +
                ""+ FILE_TOC_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.fileTocPattern.matcher(existingToc).replaceAll(replacement), is(expectedResult));


        existingToc = "lorem ipsum\n" +
                ""+PROJECT_TOC_MARKER+"\n" +
                "1. sadfh\n" +
                "2. asdfjh\n" +
                "3. kasdjfh\n" +
                ""+ TOC_END_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.projectTocPattern.matcher(existingToc).replaceAll(replacement), is(expectedResult));

        existingToc = "lorem ipsum\n" +
                ""+ PROJECT_TOC_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.projectTocPattern.matcher(existingToc).replaceAll(replacement), is(expectedResult));

        assertThat(Config.tocFinderPattern.matcher(existingToc).find(), is(true));


    }

}
