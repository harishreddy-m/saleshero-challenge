### SalesHero Challenge

## Problem Statement
Sort a huge file with random unsorted numbers ranging from MIN_INTEGER to MAX_INTEGER
with minimal HeapSize.

**Input:**

Filname of a file in classpath.File contains lots and lots of integers.

File size can go upto 100 MB.

**Output**

A file containing all the integers in the input file but sorted.

**Constraints**

JVM has only 100 MB of heap size.


**How to run**

`mvn clean package`
`java -Xmx10m -jar target\saleshero-challenge-1.0-SNAPSHOT-jar-with-dependencies.jar input\30MB.txt`

**Brief Summary of Solution**

-  Define a array size that can fit in memory.Read numbers till that array is filled.Sort them, write the numbers one by one to another file.
-  Used a Queue of stacks for merging