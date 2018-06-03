package tech.harish.apps;

import tech.harish.apps.util.FileUtil;
import tech.harish.apps.util.Stack;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Harish Reddy M
 */
public class App {
    private static String outputFile = "output" + File.separator + "sorted.txt";

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide a file name as input");
            return;
        } else {
            File inputFile = new File(args[0]);
            System.out.println("Sorting the file : " + inputFile.getAbsolutePath());
            List<File> smallFiles = new FileUtil().breakLargeFile(inputFile);
            new App().mergeInOrder(smallFiles, new File(outputFile));
        }
    }


    /**
     * This method will produce a big file with all the contents from sorted small files
     * <p>
     * Not handling empty files
     *
     * @param sortedSmallFiles files with contents sorted already
     * @param outputFile       merged contents are written to this file
     */
    void mergeInOrder(List<File> sortedSmallFiles, File outputFile) throws FileNotFoundException {
        Writer wr;
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), Charset.defaultCharset().name()));
            PriorityQueue<Stack> q = new PriorityQueue<>(sortedSmallFiles.size());
            for (File file : sortedSmallFiles) {
                Stack pks = new Stack(file);
                q.add(pks);
            }

            //The stack with smallestStack integer on top is labelled as smallestStack
            //The compare method of stack takes care of polling the correct stack
            Stack smallestStack = q.poll();

            while (smallestStack != null) {
                if (smallestStack.peek() != null) {
                    wr.write(smallestStack.next() + System.lineSeparator());
                    if (smallestStack.hasNext()) {
                        q.add(smallestStack);
                    }
                }
                smallestStack = q.poll();
            }
            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
