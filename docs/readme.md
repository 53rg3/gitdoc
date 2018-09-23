<!--- PROJECT_TOC -->

# GitDoc

CLI tool for automatic TOC creation and reference checking in MarkDown files. For writing docs and notebooks in Git remote repositories.

## Run

1. Download or build JAR
2. Execute via `java -jar gitdoc.jar`

### Options

#### -p / --path (Set the folder gitdoc shall work in) 

:mag:  Note: Mandatory parameter

:mag:  Note: The folder must exist.

```bash
java -jar gitdoc.jar -p=/absolute/path/to/gitdoc_folder
```

#### -s / --scaffold (Create a scaffold folder)

```bash
java -jar gitdoc.jar -p=/absolute/path/to -s=gitdoc_folder
```

### Usage with alias

Alias which automatically uses the current folder from which `gitdoc` alias is executed:

```BASH
alias gitdoc='java -jar /absolute/path/to/gitdoc.jar -p="$PWD"'
```



### Markers

#### .gitdoc file

Your gitdoc folder must contain a file named `.gitdoc`. Otherwise gitdoc will refuse to work.

#### File TOC

This will place the TOC of that file on that position.

```html
<!--- FILE_TOC -->
```

#### Project TOC

This will place a TOC of **ALL** headings in MarkDown files in the gitdoc folder. Best place is the index file of your gitdoc folder.

```html
<!--- FILE_TOC -->
```

### Understanding sorting of TOCs

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

### Possible problems

- **Do not use a gitdoc folder inside another gitdoc folder**<br>gitdoc will scan and use the containing gitdoc folder, which can lead to confusing results