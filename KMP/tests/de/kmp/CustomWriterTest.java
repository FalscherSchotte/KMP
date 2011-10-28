package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
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
            Assert.assertNotNull("Exception occured!", null);
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
            Assert.assertNotNull("Exception occured!", null);
        }
    }

    @Test
    public void testCombiStepByStep() {
        try {
            File testFile = new File(TestData.getBasePath() + "TestFile.txt");
            CustomWriter writer = new CustomWriter(testFile);
            for (int i = -1; i < 10; i++) {
                writer.writeLine(String.valueOf(i));
            }
            writer.close();

            FileReader fileReader = new FileReader(testFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> controlValues = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                controlValues.add(line);
            }

            Assert.assertEquals("Written values don't have the expected size", 11, controlValues.size());
            for (int i = 0; i < controlValues.size(); i++) {
                Assert.assertEquals(controlValues.get(i), String.valueOf(i - 1));
            }

            CustomLineReader customLineReader = new CustomLineReader(testFile);
            List<String> customValues = new ArrayList<String>();
            while (customLineReader.hasNext()) {
                customValues.add(customLineReader.readNext());
            }

            Assert.assertEquals("Read values of customLineReader.Next does not have the expected size", 11, controlValues.size());
            for (int i = 0; i < controlValues.size(); i++) {
                Assert.assertEquals(controlValues.get(i), customValues.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("Exception occured!", null);
        }
    }

    @Test
    public void testCombiWriteReadLinePosForward() {
        try {
            File testFile = new File(TestData.getBasePath() + "TestFile.txt");
            CustomWriter writer = new CustomWriter(testFile);
            for (int i = -1; i < 10; i++) {
                writer.writeLine(String.valueOf(i));
            }
            writer.close();

            CustomLineReader customLineReader = new CustomLineReader(testFile);
            Assert.assertEquals("-1",customLineReader.read(0));
            Assert.assertEquals("1",customLineReader.read(2));
            Assert.assertEquals("1",customLineReader.read(2));
            Assert.assertEquals("3",customLineReader.read(4));
            Assert.assertEquals("6",customLineReader.read(7));
            Assert.assertEquals("7",customLineReader.read(8));

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("Exception occured!", null);
        }
    }

    @Test
    public void testCombiWriteReadLineJump() {
        try {
            File testFile = new File(TestData.getBasePath() + "TestFile.txt");
            CustomWriter writer = new CustomWriter(testFile);
            for (int i = -1; i < 10; i++) {
                writer.writeLine(String.valueOf(i));
            }
            writer.close();

            CustomLineReader customLineReader = new CustomLineReader(testFile);
            Assert.assertEquals("-1",customLineReader.readNext());
            Assert.assertEquals("1",customLineReader.read(2));
            Assert.assertEquals("-1",customLineReader.read(0));
            Assert.assertEquals("3",customLineReader.read(4));
            Assert.assertEquals("4",customLineReader.readNext());
            Assert.assertEquals("7",customLineReader.read(8));
            Assert.assertEquals("-1",customLineReader.read(0));
            Assert.assertEquals("-1",customLineReader.read(0));
            Assert.assertEquals("8",customLineReader.read(9));
            Assert.assertEquals("7",customLineReader.read(8));
            Assert.assertEquals("8",customLineReader.readNext());

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertNotNull("Exception occured!", null);
        }
    }
}
