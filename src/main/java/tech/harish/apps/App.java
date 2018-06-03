package tech.harish.apps;

import org.apache.commons.io.FileUtils;
import tech.harish.apps.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

/**
 * @author Harish Reddy M
 */
public class App {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide a file name as input");
            return;
        } else {
            File inputFile = new File(args[0]);
            System.out.println("Sorting the file : " + inputFile.getAbsolutePath());
            List<File> smallFiles = new FileUtil().breakLargeFile(inputFile);
            System.gc();
            List<File> sortedSmallFiles = smallFiles.stream().map(f -> sortFile(f)).collect(toList());
            mergeInOrder(sortedSmallFiles);
        }
    }


    /**
     * This method will produce a big file with all the contents from sorted small files
     *
     * @param sortedSmallFiles
     */
    private static void mergeInOrder(List<File> sortedSmallFiles) {
    }


    /**
     * It is safe to load the small file into memory as the file is created after considering the memory constraints
     *
     * @param file
     * @return file with contents sorted
     */
    private static File sortFile(File file) {
        File sortedFile = new File("sorted_tmp" + File.separator + file.getName());
        try (Stream<String> lines = Files.lines(file.toPath())) {
            List<Integer> numbers = lines.mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
            Integer[] sorted = numbers.toArray(new Integer[]{0});
            Arrays.sort(sorted);
            for (Integer i : sorted) {
                try {
                    FileUtils.write(sortedFile, String.valueOf(i), UTF_8, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedFile;
    }
}
