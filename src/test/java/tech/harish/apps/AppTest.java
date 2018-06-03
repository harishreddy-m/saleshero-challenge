package tech.harish.apps;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Unit test for simple algo.
 */
public class AppTest {
    private Algo algo;

    @Before
    public void setup() {
        algo = new Algo();
    }

    @Test
    public void shouldMergeNonOverlappingFiles() {
        try {
            Collection<File> files = FileUtils.listFiles(new File("input" + File.separator + "test_no_overlap"), new String[]{"txt"}, false);
            File outputFile = new File("test_no_overlap_output.txt");
            algo.mergeInOrder(new ArrayList(files), outputFile);

            assertTrue(outputFile.exists());
            assertNotEquals(0, FileUtils.sizeOf(outputFile));
            assertSortedOrder(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void assertSortedOrder(File outputFile) {
        try (LineIterator it = FileUtils.lineIterator(outputFile, StandardCharsets.UTF_8.name())) {
            int minValue = Integer.parseInt(it.nextLine());
            while (it.hasNext()) {
                int value = Integer.parseInt(it.nextLine());
                if (value < minValue) {
                    fail();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void shouldMergeOverlappingFiles() {
        try {
            Collection<File> files = FileUtils.listFiles(new File("input" + File.separator + "test_overlap"), new String[]{"txt"}, false);
            File outputFile = new File("test_overlap_output.txt");
            algo.mergeInOrder(new ArrayList(files), outputFile);

            assertTrue(outputFile.exists());
            assertNotEquals(0, FileUtils.sizeOf(outputFile));
            assertSortedOrder(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void shouldMergeMixedOverlappingFiles() {
        try {
            Collection<File> files = FileUtils.listFiles(new File("input" + File.separator + "test_overlap_negative"), new String[]{"txt"}, false);
            File outputFile = new File("test_overlap_negative_output.txt");
            algo.mergeInOrder(new ArrayList(files), outputFile);

            assertTrue(outputFile.exists());
            assertNotEquals(0, FileUtils.sizeOf(outputFile));
            assertSortedOrder(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
