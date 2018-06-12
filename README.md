# REQUIREMENTS

We have 4 files. Each file contains a random numbers of signed integers that are separated through semicolon (;). 
Between semicolon can be any character except semicolon(;), not only numbers. 

And the problem. Find maximum number from each file. 
Then from each maximum (there will be 4) find again the maximum one and write it to a file along with the file where it was found. 
It should look like this:

```
Maximum: 425867
Location: /home/testuser/input4
```

Requirements:

Each file should be processed on a separate thread and four maximum resulted should be processed on another thread.

Program reads the file location from a config file, named max.conf that will be near it. File contains full location to file, like this:

```
/home/testuser/input1
/home/testuser/input2
/home/testuser/input3
/home/testuser/input4
```

File where it writes the result will be also located in the same folder.

Deliverables: source code, input files, output files. May be on a git hub location, a zip file or whatever is fine for you.

# RUN

* generate test files with org.bittwit.fileparser.generator.TestGenerator.main
    * currently this works from an IDE
    * you can tune org.bittwit.fileparser.generator.TestGenerator.INTEGER_LIMIT to create different size files
* use Makefile targets to run tests

    ```
    make build
    ```