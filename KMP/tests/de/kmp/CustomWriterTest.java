package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 17:19
 */
public class CustomWriterTest {
    @Test
    public void testWrite() {
        try {
            File testFile = new File(TestData.getBasePath() + "TestFile.txt");
            CustomWriter writer = new CustomWriter(testFile);

            String[] values = new String[]{"0", "1", "2", "3", "4"};
            for (String value : values) {
                writer.write(value);
            }

            List<String> controllValues = new ArrayList<String>();
            FileReader reader = new FileReader(testFile);
            char[] buffer = new char[1];
            while ((reader.read(buffer)) != -1) {
                controllValues.add(String.valueOf(buffer));
            }

            Assert.assertEquals("Length test failed.", values.length, controllValues.size());
            for (int i = 0; i < values.length; i++) {
                Assert.assertEquals("Value did not match at index " + i, values[i], controllValues.get(i));
            }

            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("Fileaccessor exception occured!", null);
        }
    }

    @Test
    public void testWriteLine() {
        try {
            File testFile = new File(TestData.getBasePath() + "TestFile.txt");
            CustomWriter writer = new CustomWriter(testFile);

            String[] values = new String[]{"0", "1", "2", "3", "4"};
            for (String value : values) {
                writer.writeLine(value);
            }

            List<String> controllValues = new ArrayList<String>();
            FileReader reader = new FileReader(testFile);
            char[] buffer = new char[1];
            while ((reader.read(buffer)) != -1) {
                controllValues.add(String.valueOf(buffer));
            }
            String completeString = "";
            for (String value : controllValues) {
                completeString += value;
            }
            String[] filteredControllValues = completeString.split(System.getProperty("line.separator"));

            Assert.assertEquals("Length test failed.", values.length, filteredControllValues.length);
            for (int i = 0; i < values.length; i++) {
                Assert.assertEquals("Value did not match at index " + i, values[i], filteredControllValues[i]);
            }

            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("Fileaccessor exception occured!", null);
        }
    }
}
