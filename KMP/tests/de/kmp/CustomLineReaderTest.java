package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:57
 */
public class CustomLineReaderTest {
    private File testFile;

    public CustomLineReaderTest() throws IOException {
        testFile = new File(TestData.getBasePath() + "TestFile.txt");
        createTestFile(testFile); //-10123456789
    }

    public static void createTestFile(File testFile) throws IOException {
        FileWriter writer = new FileWriter(testFile);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (int i = -1; i < 10; i++) {
            bufferedWriter.write(String.valueOf(i));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        writer.close();
    }

    @Test
    public void testReadNext() throws IOException {
        try {
            CustomLineReader reader = new CustomLineReader(testFile);
            List<String> readData = new ArrayList<String>();
            while(reader.hasNext()){
                readData.add(reader.readNext());
            }

            Assert.assertEquals("Length missmatch", 11, readData.size());
            for(int i = -1; i < 10; i++){
                Assert.assertEquals("Missmatch at " + i, readData.get(i + 1), String.valueOf(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }

    @Test
    public void testReadJumpForward() throws IOException {
        try {
            CustomLineReader reader = new CustomLineReader(testFile);
            String line = "";
            //-10123456789
            line += reader.read(0);
            line += reader.read(2);
            line += reader.read(4);
            line += reader.read(5);
            line += reader.read(9);
            Assert.assertEquals("-11348", line);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("CustomReader exception occured!", null);
        }
    }
}
