package tech.harish.apps;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    App app;

    @Before
    public void setup() {
        app = new App();
    }

    @Test
    public void shouldMergeNonOverlappingFiles() {
        try {
            Collection<File> files = FileUtils.listFiles(new File("input" + File.separator + "test_no_overlap"), new String[]{"txt"}, false);
            File outputFile = new File("test_no_overlap_output.txt");
            app.mergeInOrder(new ArrayList(files), outputFile);

            assertTrue(outputFile.exists());
            assertNotEquals(0, FileUtils.sizeOf(outputFile));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}
