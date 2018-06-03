package tech.harish.apps;

import tech.harish.apps.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            new Algo().mergeInOrder(smallFiles, new File(outputFile));
        }
    }
}
