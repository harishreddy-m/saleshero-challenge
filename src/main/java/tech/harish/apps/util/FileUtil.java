package tech.harish.apps.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
 * Responsible for breaking files
 */
public class FileUtil {

    static final String TMP_FILE = "tmp" + File.separator + "smallFile";

    public FileUtil() {
        //init
        try {
            cleanTmp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long sizeOfTemporaryFile = 0; //in bytes

    public static Optional<Integer> getFirstInteger(File file) throws FileNotFoundException {
        try (Scanner sc = new Scanner(file, Charset.defaultCharset().name())) {
            if (sc.hasNextLine()) {
                return Optional.of(Integer.parseInt(sc.nextLine()));
            }
        }
        return Optional.empty();
    }

    public List<File> breakLargeFile(File file) throws IOException {

        sizeOfTemporaryFile = calculateFreeMemory();
        System.out.println("Maximum Size of temporary file can be : " + sizeOfTemporaryFile);
        int i = 0;
        int tempArraySize = Math.toIntExact(sizeOfTemporaryFile / 16);
        System.out.println("Going to sort chunk of " + tempArraySize);
        int[] unsorted = new int[tempArraySize];
        try (LineIterator it = FileUtils.lineIterator(file, StandardCharsets.UTF_8.name())) {
            while (it.hasNext()) {
                int value = Integer.parseInt(it.nextLine());
                if (i <= tempArraySize - 1) {
                    unsorted[i++] = value;
                } else {
                    sortAndWriteToFile(unsorted);
                    unsorted = new int[tempArraySize];
                    i = 0;
                }
            }
            //to handle last chunk
            sortAndWriteToFile(unsorted);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return temporaryFiles;
    }

    private void sortAndWriteToFile(int[] unsorted) throws IOException {
        proceedToNextFile();
        Arrays.sort(unsorted);
        for (int i = 0; i <= unsorted.length; i++) {
            String sortedInteger = (unsorted[i] + System.lineSeparator());
            FileUtils.write(fileTowrite, sortedInteger, Charset.defaultCharset(), true);
        }
        System.out.println("Actual file size on disk : " + FileUtils.sizeOf(fileTowrite));
        System.out.println("Free memory now : " + calculateFreeMemory());
    }

    static void cleanTmp() throws IOException {
        File toDelete = new File("tmp");
        FileUtils.deleteDirectory(toDelete);
        toDelete = new File("sorted_tmp");
        FileUtils.deleteDirectory(toDelete);
    }

    File fileTowrite;
    int fileIndex;
    List<File> temporaryFiles = new ArrayList<>();

    private void proceedToNextFile() throws IOException {
        String tmpFileName = TMP_FILE + (fileIndex++) + ".txt";
        fileTowrite = new File(tmpFileName);
        temporaryFiles.add(fileTowrite);
        System.out.println("Going to write to " + fileTowrite.getCanonicalPath());
    }


    private long calculateFreeMemory() {
        Runtime r = Runtime.getRuntime();
        return r.freeMemory();
    }


}
