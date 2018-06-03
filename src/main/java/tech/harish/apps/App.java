package tech.harish.apps;

import tech.harish.apps.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
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
            new App().mergeInOrder(smallFiles, new File(outputFile));
        }
    }


    /**
     * This method will produce a big file with all the contents from sorted small files
     *
     * Not handling empty files
     *
     * @param sortedSmallFiles files with contents sorted already
     * @param outputFile merged contents are written to this file
     */
    void mergeInOrder(List<File> sortedSmallFiles, File outputFile) throws FileNotFoundException {
        int[] pointers = new int[sortedSmallFiles.size()];
        int[] valueAtPointers = new int[sortedSmallFiles.size()];

        //init
        for (int i = 0; i < sortedSmallFiles.size(); i++) {
            valueAtPointers[i++] = FileUtil.getFirstInteger(sortedSmallFiles.get(i)).orElseThrow(IllegalArgumentException::new);
            pointers[i] = 0;
        }
        int minFileIndex = getMinimalIndex(valueAtPointers);


    }

    private int getMinimalIndex(int[] valueAtPointers) {
        int j, min = valueAtPointers[0], minIndex = 0;
        for (j = 0; j < valueAtPointers.length; j++) {
            if (valueAtPointers[j] <= min) {
                min = valueAtPointers[j];
                minIndex = j;
            }
        }
        return minIndex;
    }
}
