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

    /**
     * REGRESSION_TEST
     * Issue: Markers inside code blocks are processed normally, so that hashes are recognized as headers
     */
    @Test
    public void tocPatterns_multipleOccurrences() {
        String text = "" +
                "<!--- PROJECT_TOC -->\n" +
                "aasdf\n" +
                "asdfasdf\n" +
                "sadfadsf\n" +
                "sadf\n" +
                "<!--- TOC_END -->\n" +
                "\n" +
                "THIS MUST NOT BE DELETED\n" +
                "\n" +
                "```\n\n"+
                "THIS_SHOULD_NOT_BE_HERE\n"+
                "<!--- PROJECT_TOC -->\n" +
                "\n```\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n" +
                "jdsfhgjkdfg\n";
        System.out.println(text);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


        text = Config.codeBlockPattern.matcher(text).replaceAll("");
        System.out.println(text);



    }

}
