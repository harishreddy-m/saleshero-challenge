package tech.harish.apps.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class TestDataGenerator {
    private static final String TEST_FILE = "input" + File.separator + "test_10MB.txt";
    public static final long REQUIRED_FILE_SIZE = 1024 * 1024 * 3L;

    /*
     * No need to run every time
     * This function creates a huge file of integers
     */
    public static void main(String[] args) {
        Random randomGenerator = new Random();
        File fileTowrite = new File(TEST_FILE);
        try {
            long size = 0L;
            StringBuffer data = new StringBuffer();
            while (size < REQUIRED_FILE_SIZE) {
                size += Integer.BYTES;
                data.append(randomGenerator.nextInt() + System.lineSeparator());
            }
            FileUtils.write(fileTowrite, data, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
