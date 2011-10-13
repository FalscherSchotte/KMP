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
    private String[] text;
    private String[] pattern;
    private int expectedIndex;
    //    private static final String stringBase = "01234";
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
                while ((line = bufferedReader.readLine()) != null) {
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

                    return new TestData(data, pattern, Integer.valueOf(index));
                }
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

    public static boolean generateTestData(File file, int patternLength, int textLength, int patternPos) {
        try {
            Random random = new Random(System.currentTimeMillis());
            String[] pattern = generatePattern(patternLength, random);
            String[] text = generateText(textLength, pattern, patternPos);
            saveTestData(file, new TestData(text, pattern, patternPos));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private static String[] generateText(int textLength, String[] pattern, int patternPos) {
        String[] text = new String[textLength];
        int indexCtr = 0;
        while (text.length < textLength) {
            int length = getSubPatternLength(text.length, textLength, pattern, patternPos);
            System.arraycopy(pattern, 0, text, indexCtr, length);
            indexCtr += length;
        }
        return text;
    }

    private static int getSubPatternLength(int currentTextLength, int textLength, String[] pattern, int patternPos) {


        return 10;
    }

    private static String[] generatePattern(int patternLength, Random random) {
        String[] pattern = new String[patternLength];
        for (int iii = 0; iii < patternLength - 1; iii++) {
            pattern[iii] = random.nextInt(2) == 0 ? "0" : "1";
        }
        pattern[patternLength - 1] = "@";
        return pattern;
    }

//    public static boolean generateTestData(File file, int numberOfEntities, int maxDataLength) {
//        try {
//            Random generator = new Random(System.currentTimeMillis());
//            List<TestData> testData = new ArrayList<TestData>();
//            for (int iii = 0; iii < numberOfEntities; iii++) {
//                testData.add(generateDataEntity(generator, maxDataLength));
//            }
//            saveTestData(file, testData);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    private static TestData generateDataEntity(Random random, int maxDataLength) {
//        String[] dataArray = new String[random.nextInt(maxDataLength) + 1];
//        String[] patternArray = new String[random.nextInt(dataArray.length) / 50 + 1];
//        int matchIndex = dataArray.length - patternArray.length == 0 ? 0 : random.nextInt(dataArray.length - patternArray.length);
//
//        for (int iii = 0; iii < dataArray.length; iii++) {
//            dataArray[iii] = iii == matchIndex ? "@" : String.valueOf(stringBase.charAt(random.nextInt(stringBase.length())));
//            if (iii >= matchIndex && iii < matchIndex + patternArray.length)
//                patternArray[iii - matchIndex] = dataArray[iii];
//        }
//
//        return new TestData(dataArray, patternArray, matchIndex);
//    }

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
