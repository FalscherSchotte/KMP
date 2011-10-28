package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
    public void testReadJumpAround() throws IOException {
        CustomReader customReader = null;
        try {
            customReader = new CustomReader(testFile);
            Assert.assertEquals("0", customReader.readNext());
            Assert.assertEquals("2", customReader.read(2));
            Assert.assertEquals("0", customReader.read(0));
            Assert.assertEquals("0", customReader.read(0));
            Assert.assertEquals("1", customReader.readNext());
            Assert.assertEquals("5", customReader.read(5));
            Assert.assertEquals("6", customReader.readNext());
            Assert.assertEquals("7", customReader.readNext());
            Assert.assertEquals("9", customReader.read(9));
            Assert.assertEquals("0", customReader.read(0));
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        } finally {
            assert customReader != null;
            customReader.close();
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

    @Test
    public void testSpeedJumping() {
        try {
            int size = 10000;
            writeTestFile(size);

            CustomReader reader = new CustomReader(testFile);
            Random random = new Random(System.currentTimeMillis());
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                reader.read(random.nextInt(size));
            }
            System.out.println("Test speed jumping 10000 times in " + size + " character file took " + (System.currentTimeMillis() - start) + "ms");

            Assert.assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testSpeed() {
        try {
            System.out.println("Reading of " + 100 + " elements took " + measureReadSpeed(100) + "ms");
            System.out.println("Reading of " + 1000 + " elements took " + measureReadSpeed(1000) + "ms");
            System.out.println("Reading of " + 10000 + " elements took " + measureReadSpeed(10000) + "ms");
            System.out.println("Reading of " + 100000 + " elements took " + measureReadSpeed(100000) + "ms");
            System.out.println("Reading of " + 1000000 + " elements took " + measureReadSpeed(1000000) + "ms");
            System.out.println("Reading of " + 10000000 + " elements took " + measureReadSpeed(10000000) + "ms");
            Assert.assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    private void writeTestFile(long size) throws IOException {
        FileWriter writer = new FileWriter(testFile);
        for (long i = 0; i < size; i++) {
            writer.write(String.valueOf(i));
            if (i % 10000 == 0)
                writer.flush();
        }
        writer.close();
    }

    private long measureReadSpeed(long size) throws IOException {
        writeTestFile(size);
        CustomReader reader = new CustomReader(testFile);
        long start = System.currentTimeMillis();
        while (reader.hasNext()) {
            reader.readNext();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }
}
