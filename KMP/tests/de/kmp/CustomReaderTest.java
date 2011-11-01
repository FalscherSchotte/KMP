package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 16:04
 */
public class CustomReaderTest {
    private static File testFile = new File(TestData.getBasePath() + "TestFile.txt");

    public CustomReaderTest() throws IOException {
        createTestFile();
    }

    public static void createTestFile() throws IOException {
        FileWriter writer = new FileWriter(testFile);
        //0123456789
        for (int i = 0; i < 10; i++) {
            writer.write(String.valueOf(i));
            writer.flush();
        }
        writer.close();
    }

    @Test
    public void testReadJumpForward() throws IOException {
        CustomReader reader = null;
        try {
            reader = new CustomReader(testFile);
            Assert.assertEquals("4", reader.read(4));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("5", reader.read(5));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("7", reader.read(7));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("9", reader.read(9));
            Assert.assertEquals(true, reader.getSize() == reader.getPosition());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        } finally {
            assert reader != null;
            reader.close();
        }
    }

    @Test
    public void testReadJumpAround() throws IOException {
        CustomReader reader = null;
        try {
            reader = new CustomReader(testFile);
            Assert.assertEquals("4", reader.read(4));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("0", reader.read(0));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("1", reader.read(1));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("4", reader.read(4));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("4", reader.read(4));
            Assert.assertEquals(false, reader.getSize() == reader.getPosition());
            Assert.assertEquals("9", reader.read(9));
            Assert.assertEquals(true, reader.getSize() == reader.getPosition());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        } finally {
            assert reader != null;
            reader.close();
        }
    }
}
