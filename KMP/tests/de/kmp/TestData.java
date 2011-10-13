package de.kmp;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 14:53
 */
public class TestData {
    private String testName;
    private final String[] text;
    private final String[] pattern;
    private final int expectedIndex;
    private static final String basePath = "E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\";

    public String[] getText() {
        return text;
    }

    public String[] getPattern() {
        return pattern;
    }

    public int getExpectedIndex() {
        return expectedIndex;
    }

    public static String getBasePath() {
        return basePath;
    }

    public String getTestDataName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public TestData(String[] data, String[] pattern, int expectedIndex) {
        this.text = data;
        this.pattern = pattern;
        this.expectedIndex = expectedIndex;
    }

    public static TestData read(File file) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String line;
                if ((line = bufferedReader.readLine()) == null)
                    return null;

                String dataString = line.substring(0, line.indexOf(","));
                String[] data;
                if (dataString.length() > 1)
                    data = Arrays.copyOfRange(dataString.split(""), 1, dataString.length() + 1);
                else
                    data = new String[]{dataString.substring(0)};

                String patternString = line.substring(data.length + 1, line.lastIndexOf(","));
                String[] pattern;
                if (patternString.length() > 1)
                    pattern = Arrays.copyOfRange(patternString.split(""), 1, patternString.length() + 1);
                else
                    pattern = new String[]{patternString.substring(0)};

                String index = line.substring(line.lastIndexOf(",") + 1);

                TestData testData = new TestData(data, pattern, Integer.valueOf(index));
                testData.setTestName(file.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (fileReader != null)
                    fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TestData generateTestData(int patternLength, int textLength, int patternPos) {
        Random random = new Random(System.currentTimeMillis());
        String[] pattern = generatePattern(patternLength, random);
        String[] text = generateText(textLength, pattern, patternPos, random);
        return new TestData(text, pattern, patternPos);
    }

    public static boolean generateTestDataFile(File file, int patternLength, int textLength, int patternPos) {
        try {
            TestData testData = generateTestData(patternLength, textLength, patternPos);
            testData.setTestName(file.getName());
            saveTestData(file, testData);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private static String[] generateText(int textLength, String[] pattern, int patternPos, Random random) {
        String[] text = new String[textLength];
        int textIndex = 0;
        while (text.length > textIndex) {
            int length = getSubPatternLength(textIndex, textLength, pattern.length, patternPos, random);
            if (length == 0)
                length++;
            System.arraycopy(pattern, 0, text, textIndex, length);
            textIndex += length;
        }
        return text;
    }

    private static int getSubPatternLength(int currentTextPos, int textLength, int patternLength, int patternPos, Random random) {
        //------------|---|ppppppppppp|--------------
        if (currentTextPos < patternPos) {
            if (currentTextPos + patternLength < patternPos) {
                return random.nextInt(patternLength - 1);
            } else {
                return patternPos - currentTextPos - 1;
            }
        } else {
            if (currentTextPos == patternPos) {
                return patternLength;
            } else {
                return Math.min(random.nextInt(patternLength - 1), textLength - currentTextPos);
            }
        }
    }

    private static String[] generatePattern(int patternLength, Random random) {
        String[] pattern = new String[patternLength];
        for (int iii = 0; iii < patternLength - 1; iii++) {
            pattern[iii] = random.nextInt(2) == 0 ? "0" : "1";
        }
        pattern[patternLength - 1] = "@";
        return pattern;
    }

    private static void saveTestData(File file, TestData testData) throws IOException {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);

            for (String element : testData.getText()) {
                bufferedWriter.write(element);
            }
            bufferedWriter.write(",");
            for (String element : testData.getPattern()) {
                bufferedWriter.write(element);
            }
            bufferedWriter.write("," + testData.getExpectedIndex());
            bufferedWriter.newLine();

            bufferedWriter.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (fileWriter != null)
                fileWriter.close();
        }
    }
}
