<!--- PROJECT_TOC -->
[1. Run](readme.md#run)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1 Options](readme.md#options)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.1 -p / --path (Set the folder gitdoc shall run in)](readme.md#-p----path-set-the-folder-gitdoc-shall-run-in)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.2 -s / --scaffold (Create a scaffold folder)](readme.md#-s----scaffold-create-a-scaffold-folder)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.2 Using with alias](readme.md#using-with-alias)<br>
[2. Markers](readme.md#markers)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.1 .gitdoc file](readme.md#gitdoc-file)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.2 File TOC](readme.md#file-toc)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.3 Project TOC](readme.md#project-toc)<br>
[3. Understanding sorting of TOCs](readme.md#understanding-sorting-of-tocs)<br>
[4. Possible problems](readme.md#possible-problems)<br>
[5. Program Structure](001_Program_Structure/readme.md#program-structure)<br>
<!--- TOC_END -->




# Run

1. Download or build gitdoc.jar
2. Execute `java -jar gitdoc.jar`  in the folder where it should run

## Options

### -p / --path (Set the folder gitdoc shall run in)

:mag: Note: The folder must exist

```bash
java -jar gitdoc.jar -p=/absolute/path/to/gitdoc_folder
```

### -s / --scaffold (Create a scaffold folder)

Contains everything for a quick start. 

This will creates a folder with scaffold named `docs`:

```bash
java -jar gitdoc.jar -p=/absolute/path/to -s=docs
```

## Using with alias

We can create an `alias ` to use gitdoc from the current folder we are in, so that we don't need to specify the `-p` option every time.

```bash
alias gitdoc='java -jar /absolute/path/to/gitdoc.jar -p="$PWD"'
```

# Markers

gitdoc uses markers to identify the scope it is allowed to run in and where to put the TOCs.

## .gitdoc file

A gitdoc folder must contain a file named `.gitdoc`. Otherwise gitdoc will refuse to work in a defined folder.

## File TOC

This marker will create a TOC of the headings in the file it is in. gitdoc will replace the first occurrence of this marker, all other will be ignored.

:bangbang: Note: Remove the string `#REMOVE_THIS#`

```html
<!--- FILE_TOC -#REMOVE_THIS#->
```

## Project TOC

This marker will create a TOC of **ALL** headings in the files in the gitdoc folder and its subfolders. gitdoc will replace the first occurrence of this marker, all other will be ignored.

:bangbang: Note: Remove the string `#REMOVE_THIS#`

```html
<!--- PROJECT_TOC -#REMOVE_THIS#->
```

# Understanding sorting of TOCs

gitdoc will use the following procedure to collect the headings from the MarkDown files and to structure the **Project** TOC:

1. Root of the gitdoc folder is scanned, **sorted by file names**. 
2. Sub-folders of the gitdoc folder are scanned, **sorted by folder name**, then file name. You can sort folder by naming them for example 001_Intro, 002_Manual, etc.

The TOC numeration works by counting the amount of hashes (`#`) of headings. Each `#` adds another sub-level to the numeration. The numeration is reset each time gitdoc encounters a higher level header. If a file begins with a lower level header than `#` (e.g. `##`, `###`, etc) then the TOC entry will be seen as element of the last entry with the higher level header. Example:

```
File1.md:
# Main Header
## Sub Header

File2.md
# Main Header

File3.md
## Sub Header
```

Results in: 

```
1. Main Header
   1.1 Sub Header
2. Main Header
   2.1 Sub Header // Note: Although this was in File3.md
```

# Possible problems

- **Do not use a gitdoc folder inside another gitdoc folder**<br>gitdoc will scan and use the containing gitdoc folder, which leads to confusing results.
