package tech.harish.apps.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Responsible for breaking files
 */
public class FileUtil {

    static final String TMP_FILE = "tmp" + File.separator + "smallFile";

    public FileUtil() {
        //init
        try {
            proceedToNextFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long sizeOfTemporaryFile = 0; //in bytes

    public List<File> breakLargeFile(File file) throws IOException {
        cleanTmp();
        sizeOfTemporaryFile = calculateSizeOfTemporaryFile(FileUtils.sizeOf(file));
        System.out.println("Size of temporary file : " + sizeOfTemporaryFile);
        long currentBlockSize = 0;
        long currentFileSize = 0;
        StringBuilder data = new StringBuilder();
        try (LineIterator it = FileUtils.lineIterator(file, StandardCharsets.UTF_8.name())) {
            while (it.hasNext()) {
                String line = it.nextLine() + System.lineSeparator();

                int inc = line.getBytes().length;
                currentBlockSize += inc;
                currentFileSize += inc;

                data.append(line);
                if (currentBlockSize >= (sizeOfTemporaryFile / 256)) {
                    //System.out.println("Writing to disk : " + currentBlockSize);
                    currentBlockSize = 0;
                    FileUtils.write(fileTowrite, data, StandardCharsets.UTF_8, true);
                    data = new StringBuilder();
                }
                if (currentFileSize >= sizeOfTemporaryFile) {
                    System.out.println("Writing to file : " + currentFileSize);
                    currentFileSize = 0;
                    FileUtils.write(fileTowrite, data, StandardCharsets.UTF_8, true);
                    data = new StringBuilder();
                    System.out.println("Actual file size : " + FileUtils.sizeOf(fileTowrite));
                    proceedToNextFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //there is a chance of moving the cursor to next file, but nothing to write. So, this filter
        return temporaryFiles.stream().filter(f -> f.exists()).collect(Collectors.toList());
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


    private long calculateSizeOfTemporaryFile(long bigFileSize) {
        Runtime r = Runtime.getRuntime();
        return r.freeMemory() - 10240;
    }


}
