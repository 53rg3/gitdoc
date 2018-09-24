package core;

import java.util.regex.Pattern;

public class Config {

    // ------------------------------------------------------------------------------------------ //
    // RANDOM PATTERNS
    // ------------------------------------------------------------------------------------------ //

    public static final Pattern refPattern = Pattern.compile("(\\[.*?\\]\\((.*?)\\))");
    public static final Pattern urlFragmentPattern = Pattern.compile("#.*$");

    // ------------------------------------------------------------------------------------------ //
    // TOC REPLACEMENT PATTERNS
    // ------------------------------------------------------------------------------------------ //
    public static final Pattern codeBlockPattern = Pattern.compile("```.*?```", Pattern.DOTALL);
    public static final Pattern fileTocPattern = Pattern.compile("<!---?\\s?FILE_TOC\\s?-->(.*?<!---?\\s?TOC_END\\s?-->)?", Pattern.DOTALL);
    public static final Pattern projectTocPattern = Pattern.compile("<!---?\\s?PROJECT_TOC\\s?-->(.*?<!---?\\s?TOC_END\\s?-->)?", Pattern.DOTALL);
    public static final Pattern tocFinderPattern = Pattern.compile("<!---?\\s?(PROJECT|FILE)_TOC\\s?-->");
    public static final String FILE_TOC_MARKER = "<!--- FILE_TOC -->";
    public static final String PROJECT_TOC_MARKER = "<!--- PROJECT_TOC -->";
    public static final String TOC_END_MARKER = "<!--- TOC_END -->";

    public enum TocType {
        FILE_TOC,
        PROJECT_TOC
    }

    // ------------------------------------------------------------------------------------------ //
    // REPORT HEADERS
    // ------------------------------------------------------------------------------------------ //


    public static final String tocsHeader = "" +
            "\n" +
            "                 _______ ____   _____                     \n" +
            "        ______  |__   __/ __ \\ / ____|       ______       \n" +
            "  _____|______|    | | | |  | | |     ___   |______|_____ \n" +
            " |______|_____     | | | |  | | |    / __|   _____|______|\n" +
            "       |______|    | | | |__| | |____\\__ \\  |______|      \n" +
            "                   |_|  \\____/ \\_____|___/                \n" +
            "                                                          \n" +
            "                                                          \n" +
            "";


    public static final String refsHeader = "" +
            "\n" +
            "                 _____       __                     \n" +
            "        ______  |  __ \\     / _|       ______       \n" +
            "  _____|______| | |__) |___| |_ ___   |______|_____ \n" +
            " |______|_____  |  _  // _ \\  _/ __|   _____|______|\n" +
            "       |______| | | \\ \\  __/ | \\__ \\  |______|      \n" +
            "                |_|  \\_\\___|_| |___/                \n" +
            "                                                    \n" +
            "                                                    \n" +
            "";

    public static final String breakdownHeader = "" +
            "\n" +
            "\n" +
            "                 ____                 _       _                                    \n" +
            "        ______  |  _ \\               | |     | |                      ______       \n" +
            "  _____|______| | |_) |_ __ ___  __ _| | ____| | _____      ___ __   |______|_____ \n" +
            " |______|_____  |  _ <| '__/ _ \\/ _` | |/ / _` |/ _ \\ \\ /\\ / / '_ \\   _____|______|\n" +
            "       |______| | |_) | | |  __/ (_| |   < (_| | (_) \\ V  V /| | | | |______|      \n" +
            "                |____/|_|  \\___|\\__,_|_|\\_\\__,_|\\___/ \\_/\\_/ |_| |_|               \n" +
            "                                                                                   \n" +
            "";

}
