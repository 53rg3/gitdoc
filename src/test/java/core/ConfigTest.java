package core;

import org.junit.Test;

import java.util.regex.Matcher;

import static core.Config.FILE_TOC_MARKER;
import static core.Config.PROJECT_TOC_MARKER;
import static core.Config.TOC_END_MARKER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void glossaryTermPattern() {
        String text = "" +
                "* **Binary file**<br>\n" +
                "A binary file is a computer ...\n" +
                "* **BLOB - Binary Large OBject**<br>\n" +
                "A Binary Large OBject (BLOB) ...\n" +
                "- **Bloom filter**<br>\n" +
                "A Bloom filter is a space-efficient ...\n" +
                "- **Bubble sort**<br>\n" +
                "Bubble sort, sometimes referred to ..." +
                "";

        int count = 0;
        Matcher matcher = Config.glossaryTermPattern.matcher(text);
        while(matcher.find()) {
            count++;
        }
        assertThat(count, is(4));
    }


    @Test
    public void tocPatterns() {

        String expectedResult = "" +
                "lorem ipsum\n" +
                "::::OK::::\n" +
                "lorem ipsum";
        String replacement = "::::OK::::";

        String textWithMarker = "lorem ipsum\n" +
                ""+FILE_TOC_MARKER+"\n" +
                "1. sadfh\n" +
                "2. asdfjh\n" +
                "3. kasdjfh\n" +
                ""+TOC_END_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.fileTocPattern.matcher(textWithMarker).replaceAll(replacement), is(expectedResult));

        textWithMarker = "lorem ipsum\n" +
                ""+ FILE_TOC_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.fileTocPattern.matcher(textWithMarker).replaceAll(replacement), is(expectedResult));


        textWithMarker = "lorem ipsum\n" +
                ""+PROJECT_TOC_MARKER+"\n" +
                "1. sadfh\n" +
                "2. asdfjh\n" +
                "3. kasdjfh\n" +
                ""+ TOC_END_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.projectTocPattern.matcher(textWithMarker).replaceAll(replacement), is(expectedResult));

        textWithMarker = "lorem ipsum\n" +
                ""+ PROJECT_TOC_MARKER+"\n" +
                "lorem ipsum";
        assertThat(Config.projectTocPattern.matcher(textWithMarker).replaceAll(replacement), is(expectedResult));

        assertThat(Config.tocFinderPattern.matcher(textWithMarker).find(), is(true));

    }

}
