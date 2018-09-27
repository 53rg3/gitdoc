package scaffolds;

import core.Config;
import core.Error;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Glossary {
    private Glossary() {
    }

    public static void create(String scaffoldName) {
        Path scaffoldPath = Paths.get(scaffoldName);
        if (!scaffoldPath.toFile().mkdir()) {
            throw new Error("Couldn't create '" + scaffoldName + "'! Does it already exists? Do you have sufficient permissions? Path must exist!");
        }

        // Root folder
        Scaffold.createFolder(scaffoldPath, "_img");
        Scaffold.createFolder(scaffoldPath, "_res");
        Scaffold.createReadme(scaffoldPath, Config.PROJECT_TOC_MARKER);
        Scaffold.createGitDocMarker(scaffoldPath, Config.GLOSSARY_FOLDER_FILE);

        createGlossaryLetter(scaffoldPath, "A");
        createGlossaryLetter(scaffoldPath, "B");
        createGlossaryLetter(scaffoldPath, "C");
        createGlossaryLetter(scaffoldPath, "D");
        createGlossaryLetter(scaffoldPath, "E");
        createGlossaryLetter(scaffoldPath, "F");
        createGlossaryLetter(scaffoldPath, "G");
        createGlossaryLetter(scaffoldPath, "H");
        createGlossaryLetter(scaffoldPath, "I");
        createGlossaryLetter(scaffoldPath, "J");
        createGlossaryLetter(scaffoldPath, "K");
        createGlossaryLetter(scaffoldPath, "L");
        createGlossaryLetter(scaffoldPath, "M");
        createGlossaryLetter(scaffoldPath, "N");
        createGlossaryLetter(scaffoldPath, "O");
        createGlossaryLetter(scaffoldPath, "P");
        createGlossaryLetter(scaffoldPath, "Q");
        createGlossaryLetter(scaffoldPath, "R");
        createGlossaryLetter(scaffoldPath, "S");
        createGlossaryLetter(scaffoldPath, "T");
        createGlossaryLetter(scaffoldPath, "U");
        createGlossaryLetter(scaffoldPath, "V");
        createGlossaryLetter(scaffoldPath, "W");
        createGlossaryLetter(scaffoldPath, "X");
        createGlossaryLetter(scaffoldPath, "Y");
        createGlossaryLetter(scaffoldPath, "Z");

    }

    protected static void createGlossaryLetter(Path path, String letter) {

        if (letter.length() != 1) {
            throw new IllegalArgumentException("Glossary letter must have length == 1. Got: " + letter);
        }

        try (BufferedWriter writer = Scaffold.createWriter(Paths.get(path.toString() + "/" + letter + ".md"))) {
            writer.write("# " + letter);
        } catch (IOException e) {
            throw new Error("Couldn't create file for scaffold: " + path);
        }
    }


}
