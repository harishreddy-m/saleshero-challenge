package tech.harish.apps.util;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileUtil.class)
public class FileUtilTest {
    static final String TEST_FILE_1 = "input" + File.separator + "test_12KB.txt";
    private static final String TEST_FILE_2 = "input" + File.separator + "30MB.txt";
    private FileUtil fileUtil;


    @Before
    public void setup() throws Exception {
        FileUtils.deleteDirectory(new File("tmp"));
        fileUtil = PowerMockito.spy(new FileUtil());

    }


    @Test
    public void shouldReadFile() throws Exception {
        doReturn(1024L).when(fileUtil, "calculateFreeMemory");
        List<File> result = fileUtil.breakLargeFile(new File(TEST_FILE_1));
        Assert.assertNotNull("list must not be null", result);
        Assert.assertEquals(1, result.size());
    }


    @Test
    public void shouldSplitFile() throws Exception {
        List<File> result = fileUtil.breakLargeFile(new File(TEST_FILE_2));
        Assert.assertNotNull("list must not be null", result);
        //when 2 MB is free memory and 10 MB is file size
        // 5 files must be created
        Assert.assertEquals(5, result.size());
    }


}
