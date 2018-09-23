<!--- PROJECT_TOC -->
[1. GitDoc](readme.md#gitdoc)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1 Run](readme.md#run)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.1 Options](readme.md#options)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.1.1 -p / --path (Set the folder gitdoc shall work in)](readme.md#-p----path-set-the-folder-gitdoc-shall-work-in)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.1.2 -s / --scaffold (Create a scaffold folder)](readme.md#-s----scaffold-create-a-scaffold-folder)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.2 Usage with alias](readme.md#usage-with-alias)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.3 Markers](readme.md#markers)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.3.1 .gitdoc file](readme.md#gitdoc-file)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.3.2 File TOC](readme.md#file-toc)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.3.3 Project TOC](readme.md#project-toc)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1.4 Understanding sorting of TOCs](readme.md#understanding-sorting-of-tocs)<br>
[2. Main Header](readme.md#main-header)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[2.1 Sub Header](readme.md#sub-header)<br>
[3. Main Header](readme.md#main-header)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[3.1 Sub Header](readme.md#sub-header)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[3.1.1 Possible problems](readme.md#possible-problems)<br>
[4. Program Structure](001_Program_Structure/readme.md#program-structure)<br>
<!--- TOC_END -->
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