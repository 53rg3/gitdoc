<!--- FILE_TOC -->
[1. Recipes](#recipes)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.1 Count lines in file](#count-lines-in-file)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.2 Truncate file by lines](#truncate-file-by-lines)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.3 Mount folder onto another folder](#mount-folder-onto-another-folder)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[1.4 Extract random lines from file](#extract-random-lines-from-file)<br>
<!--- TOC_END -->




# Recipes
## Count lines in file

```BASH
wc -l fileName
```

## Truncate file by lines

```BASH
head --lines=100 sourceFile >> outputFile
```

## Mount folder onto another folder

```BASH
sudo mount --bind /accessable_folder /target_folder
```


So you mount `/accessable_folder` which will push it's content to the `/target_folder`. It's also possible vice-versa. Useful to push data to a remote location.

## Extract random lines from file

```BASH
shuf -n3000 FILE
```

