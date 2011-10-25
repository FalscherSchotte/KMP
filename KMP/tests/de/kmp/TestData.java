package de.kmp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 14:53
 */
public class TestData {
    private String testName;
    private String[] text;
    private String[] pattern;
    private int expectedIndex;
    private static final String basePath = "E:\\HsKA\\Semester2\\Algorithmen Labor\\";

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

    public void setText(String[] text) {
        this.text = text;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }

    public void setExpectedIndex(int expectedIndex) {
        this.expectedIndex = expectedIndex;
    }

    public TestData(String[] data, String[] pattern, int expectedIndex) {
        this.text = data;
        this.pattern = pattern;
        this.expectedIndex = expectedIndex;
    }


    public static TestData generateTestData(int patternLength, int textLength, int patternPos) {
        Random random = new Random(System.currentTimeMillis());
        String[] pattern = generatePattern(patternLength, random);
        String[] text = generateText(textLength, pattern, patternPos, random);
        return new TestData(text, pattern, patternPos);
    }

    private static String[] generateText(int textLength, String[] pattern, int patternPos, Random random) {
        String[] text = new String[textLength];
        int textIndex = 0;
        while (text.length > textIndex) {
            int length = getSubPatternLength(textIndex, textLength, pattern.length, patternPos, random);
            if (length == 0)
                length++;
            System.arraycopy(pattern.length > 0 ? pattern : new String[]{"0"}, 0, text, textIndex, length);
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
                return Math.min(random.nextInt(Math.max(patternLength - 1, 1)), textLength - currentTextPos);
            }
        }
    }

    private static String[] generatePattern(int patternLength, Random random) {
        String[] pattern = new String[patternLength];
        for (int iii = 0; iii < patternLength - 1; iii++) {
            pattern[iii] = random.nextInt(2) == 0 ? "0" : "1";
        }
        if (patternLength > 0)
            pattern[patternLength - 1] = "@";
        return pattern;
    }


    public static boolean generateTestDataFiles(File patternFile, long patternLength, File textFile, long patternPos) {
        try {
            Random random = new Random(System.currentTimeMillis());
            if (!createPatternFile(patternFile, patternLength, random))
                return false;
            if (!createTextFile(patternFile, patternLength, patternPos, textFile, random))
                return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean createPatternFile(File patternFile, long patternLength, Random random) throws IOException {
        InvalidPatternWriter writer = new InvalidPatternWriter(patternLength, random);
        return writer.write(patternFile);
    }

    private static boolean createTextFile(File patternFile, long patternLength, long patternPos, File textFile, Random random) throws IOException {
        InvalidFileWriter writer = new InvalidTextWriter(patternFile, patternLength, patternPos, random);
        return writer.write(textFile);
    }

    public static String[] readStringArray(File file) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                List<String> stringList = new ArrayList<String>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        stringList.add(line.substring(i, i + 1));
                    }
                }
                return stringList.toArray(new String[stringList.size()]);
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


    private static abstract class InvalidFileWriter {
        public boolean write(File file) throws IOException {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);

                String element;
                while ((element = getNextString()) != null) {
                    if(!element.equals("\u0000"))
                        bufferedWriter.write(element);
                }
                //flush?
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            } finally {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (fileWriter != null)
                    fileWriter.close();
            }
            return true;
        }

        public abstract String getNextString() throws IOException;
    }

    private static class InvalidPatternWriter extends InvalidFileWriter {
        private long patternLength;
        private long patternPosition;
        private Random random;

        public InvalidPatternWriter(long patternLength, Random random) {
            this.patternLength = patternLength;
            this.patternPosition = 0;
            this.random = random;
        }

        @Override
        public String getNextString() {
            if (patternPosition < patternLength - 1) {
                patternPosition++;
                return random.nextBoolean() == true ? "0" : "1";
            } else if (patternPosition == patternLength - 1) {
                patternPosition++;
                return "@";
            } else {
                return null;
            }
        }
    }

    private static class InvalidTextWriter extends InvalidFileWriter {
        private File patternFile;
        private long patternPlacementPos;
        private long textPos;
        private Random random;
        private int maxNext;
        private FileReader fileReader = null;
        private boolean patternHasNext = true;

        public InvalidTextWriter(File patternFile, long patternLength, long patternPos, Random random) {
            this.patternFile = patternFile;
            this.patternPlacementPos = patternPos;
            this.random = random;
            this.textPos = 0;
            this.maxNext = (int) (patternLength > Integer.MAX_VALUE ? Integer.MAX_VALUE : patternLength);
        }

        @Override
        public String getNextString() throws IOException {
            if (textPos < patternPlacementPos) {
                String nextString = Next(random.nextInt((int) Math.min(maxNext, patternPlacementPos - textPos + 1)), true);
                textPos += nextString.length();
                return nextString;
            } else {
                if (patternHasNext) {
                    return Next(1, false);
                } else {
                    fileReader.close();
                    return null;
                }
            }
        }

        private String Next(int length, boolean reset) throws IOException {
            if (fileReader == null)
                fileReader = new FileReader(patternFile);
            char[] buff = new char[length];
            patternHasNext = fileReader.read(buff, 0, length) != -1;
            if (reset) {
                fileReader.close();
                fileReader = new FileReader(patternFile);
                patternHasNext = true;
            }
            return String.valueOf(buff);
        }
    }
}
