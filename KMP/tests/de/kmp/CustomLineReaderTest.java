package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:57
 */
public class CustomLineReaderTest {
    private static File testFile = new File(TestData.getBasePath() + "TestFile.txt");

    public CustomLineReaderTest() throws IOException {
        createTestFile();
    }

    public static void createTestFile() throws IOException {
        FileWriter writer = new FileWriter(testFile);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        //-10123456789
        for (int i = -1; i < 10; i++) {
            bufferedWriter.write(String.valueOf(i));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        writer.close();
    }

    @Test
    public void testReadJumpForward() throws IOException {
        CustomLineReader reader = null;
        try {
            reader = new CustomLineReader(testFile);
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("-1", reader.read(0));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("1", reader.read(2));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("3", reader.read(4));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("4", reader.read(5));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("8", reader.read(9));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("9", reader.read(10));
            Assert.assertEquals(true, reader.getPosition() == reader.getSize());
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
        CustomLineReader reader = null;
        try {
            reader = new CustomLineReader(testFile);
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("0", reader.read(1));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("4", reader.read(5));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("-1", reader.read(0));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("0", reader.read(1));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("0", reader.read(1));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("8", reader.read(9));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("9", reader.read(10));
            Assert.assertEquals(true, reader.getPosition() == reader.getSize());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        } finally {
            assert reader != null;
            reader.close();
        }
    }

    @Test
    public void testReadMixed() throws IOException {
        CustomLineReader reader = null;
        try {
            reader = new CustomLineReader(testFile);
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("-1", reader.readNext());
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("4", reader.read(5));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("5", reader.readNext());
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("6", reader.readNext());
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("8", reader.read(9));
            Assert.assertEquals(false, reader.getPosition() == reader.getSize());
            Assert.assertEquals("9", reader.readNext());
            Assert.assertEquals(true, reader.getPosition() == reader.getSize());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        } finally {
            assert reader != null;
            reader.close();
        }
    }

    @Test
    public void testReadNextUntilDone() throws IOException {
        CustomLineReader reader = null;
        try {
            reader = new CustomLineReader(testFile);
            int ctr = -1;
            while (reader.getPosition() != reader.getSize()) {
                Assert.assertEquals(String.valueOf(ctr), reader.readNext());
                ctr++;
            }
            Assert.assertEquals(10, ctr);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        } finally {
            assert reader != null;
            reader.close();
        }
    }
}
