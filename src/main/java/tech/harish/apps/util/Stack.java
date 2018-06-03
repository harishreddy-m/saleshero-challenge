package tech.harish.apps.util;

import java.io.File;
import java.util.Scanner;

public class Stack implements Comparable<Stack> {
    private Scanner scan;
    private Integer next;

    public Stack(File source) throws Exception {
        scan = new Scanner(source);
        next = (scan.hasNextInt() ? scan.nextInt() : null);
    }

    public boolean hasNext() {
        return (next != null);
    }

    public Integer next() {
        Integer current = next;
        next = (scan.hasNextInt() ? scan.nextInt() : null);
        return current;
    }

    public Integer peek() {
        return next;
    }

    @Override
    public int compareTo(Stack other) {
        return peek() - other.peek();
    }
}
