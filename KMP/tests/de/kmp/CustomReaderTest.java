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
    private File testFile;

    public CustomReaderTest() throws IOException {
        testFile = new File(TestData.getBasePath() + "TestFile.txt");
        createTestFile(testFile); //0123456789
    }

    public static void createTestFile(File testFile) throws IOException {
        FileWriter writer = new FileWriter(testFile);
        for (int i = 0; i < 10; i++) {
            writer.write(String.valueOf(i));
            writer.flush();
        }
        writer.close();
    }

    @Test
    public void testRead() {
        try {
            String readValues = "";
            CustomReader customReader = new CustomReader(testFile);
            while (customReader.hasNext()) {
                readValues += customReader.readNext();
            }

            Assert.assertEquals("0123456789", readValues);
            customReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadJumpBack() {
        try {
            String readValues = "";
            CustomReader customReader = new CustomReader(testFile);
            readValues += customReader.readNext();
            readValues += customReader.readNext();
            readValues += customReader.read(0);
            readValues += customReader.readNext();
            readValues += customReader.read(0);
            readValues += customReader.readNext();
            readValues += customReader.readNext();
            readValues += customReader.readNext();
            readValues += customReader.read(0);

            Assert.assertEquals("010101230", readValues);
            customReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadJumpForward() {
        try {
            String readValues = "";
            CustomReader customReader = new CustomReader(testFile);
            readValues += customReader.readNext();
            readValues += customReader.read(2);
            readValues += customReader.readNext();
            readValues += customReader.read(5);
            readValues += customReader.readNext();
            readValues += customReader.readNext();
            readValues += customReader.read(9);

            Assert.assertEquals("0235679", readValues);
            customReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadJumpAround() {
        try {
            String readValues = "";
            CustomReader customReader = new CustomReader(testFile);
            readValues += customReader.readNext();
            readValues += customReader.read(2);
            readValues += customReader.read(0);
            readValues += customReader.readNext();
            readValues += customReader.read(5);
            readValues += customReader.readNext();
            readValues += customReader.readNext();
            readValues += customReader.read(9);
            readValues += customReader.read(0);

            Assert.assertEquals("020156790", readValues);
            customReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadNegative() {
        try {
            CustomReader reader = new CustomReader(testFile);
            Assert.assertEquals("Negative index did not return null", null, reader.read(-1));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadHasNext() {
        try {
            CustomReader reader = new CustomReader(testFile);
            for (int i = 0; i < 10; i++) {
                Assert.assertEquals((i == 0 ? "Initial" : "") + "hasNext should be true", true, reader.hasNext());
                reader.readNext();
            }
            Assert.assertEquals("End hasNext should be false", false, reader.hasNext());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadHasNextJumpForward() {
        try {
            CustomReader reader = new CustomReader(testFile);
            reader.read(9);
            Assert.assertEquals("hasNext should be false", false, reader.hasNext());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }
}
