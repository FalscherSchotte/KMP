package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:04
 */
public class SearchInfiniteKMPTest {

//    @Test
//    public void testCreatePrefixFile() throws Exception {
//        //Perform the test multiple times!
//        for (int repetition = 0; repetition < 50; repetition++) {
//            //Setup the data for test
//            File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
//            File textFile = new File(TestData.getBasePath() + "Text.txt");
//            long patternLength = 10;
//            long patternPos = 10;
//            TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
//
//            //Create prefix file
//            SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
//            searchInfiniteKMP.createPrefixFile(patternFile, SearchInfiniteKMP.getPrefixFile(patternFile));
//
//            //Read the file to compare it
//            String[] generatedPrefixTable = readLines(SearchInfiniteKMP.getPrefixFile(patternFile));
//
//            //Get reference table with the correct data
//            int[] referencePrefixTable = SearchKMP.analyzePrefix(read(patternFile));
//
//            Assert.assertEquals("Length missmatch", referencePrefixTable.length, generatedPrefixTable.length);
//            for (int i = 0; i < referencePrefixTable.length; i++) {
//                Assert.assertEquals("Missmatch at index " + i, referencePrefixTable[i], (int) Integer.valueOf(generatedPrefixTable[i]));
//            }
//        }
//    }
//
//    @Test
//    public void testCreatePrefixFileLarge() throws Exception {
//        //Setup the data for test
//        File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
//        File textFile = new File(TestData.getBasePath() + "Text.txt");
//        long patternLength = 50000;
//        long patternPos = 100000;
//        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
//
//        //Create prefix file
//        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
//        long start = System.currentTimeMillis();
//        searchInfiniteKMP.createPrefixFile(patternFile, SearchInfiniteKMP.getPrefixFile(patternFile));
//        System.out.println("Creation of prefixfile with patternlength " + patternLength + " took " + (System.currentTimeMillis() - start) + "ms");
//
//        //Read the file to compare it
//        String[] generatedPrefixTable = readLines(SearchInfiniteKMP.getPrefixFile(patternFile));
//
//        //Get reference table with the correct data
//        int[] referencePrefixTable = SearchKMP.analyzePrefix(read(patternFile));
//
//        Assert.assertEquals("Length missmatch", referencePrefixTable.length, generatedPrefixTable.length);
//        for (int i = 0; i < referencePrefixTable.length; i++) {
//            Assert.assertEquals("Missmatch at index " + i, referencePrefixTable[i], (int) Integer.valueOf(generatedPrefixTable[i]));
//        }
//    }
//
//
//    public void testCreatePrefixFileInsane() throws Exception {
//        //Setup the data for test
//        File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
//        File textFile = new File(TestData.getBasePath() + "Text.txt");
//        long patternLength = 1000000;
//        long patternPos = 20000000;
//        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
//
//        //Create prefix file
//        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
//        searchInfiniteKMP.createPrefixFile(patternFile, SearchInfiniteKMP.getPrefixFile(patternFile));
//
//        //Read the file to compare it
//        String[] generatedPrefixTable = readLines(SearchInfiniteKMP.getPrefixFile(patternFile));
//
//        //Get reference table with the correct data
//        int[] referencePrefixTable = SearchKMP.analyzePrefix(read(patternFile));
//
//        Assert.assertEquals("Length missmatch", referencePrefixTable.length, generatedPrefixTable.length);
//        for (int i = 0; i < referencePrefixTable.length; i++) {
//            Assert.assertEquals("Missmatch at index " + i, referencePrefixTable[i], (int) Integer.valueOf(generatedPrefixTable[i]));
//        }
//    }
//
//    @Test
//    public void testPrefixGeneratedCases() throws IOException {
//        //Perform the test multiple times!
//        for (int repetition = 0; repetition < 50; repetition++) {
//            //Setup the data for test
//            File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
//            File textFile = new File(TestData.getBasePath() + "Text.txt");
//            long patternLength = repetition * 5;
//            long patternPos = repetition * 5;
//            TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
//
//            File prefixFile = new File(TestData.getBasePath() + "PrefixTestFile.txt");
//            SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
//            searchInfiniteKMP.createPrefixFile(patternFile, prefixFile);
//
//            String[] pattern = read(patternFile);
//            String[] text = read(textFile);
//            String[] prefixStrings = readLines(prefixFile);
//            int[] prefixes = new int[prefixStrings.length];
//            for (int i = 0; i < prefixStrings.length; i++) {
//                prefixes[i] = Integer.valueOf(prefixStrings[i]);
//            }
//            Assert.assertEquals("NormalKMP index", patternPos, SearchKMP.kmpSearch(pattern, SearchKMP.analyzePrefix(pattern), text));
//            Assert.assertEquals("InfiniteKMP index", patternPos, SearchKMP.kmpSearch(pattern, prefixes, text));
//        }
//    }

//    @Test
//    public void testPrefixSpecificCase() throws IOException {
//        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
//        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
//        String[] pattern = new String[]{"0", "0", "1", "1", "1", "1", "1", "0", "0", "@"};
//        String[] text = new String[]{"0", "0", "1", "1", "1", "0", "0", "1", "1", "1", "0", "0", "1", "1", "1", "1", "1", "0", "0", "@"};
//
//        CustomWriter writer = null;
//        try {
//            writer = new CustomWriter(patternFile);
//            for (String patternElement : pattern) {
//                writer.write(patternElement);
//            }
//            writer.close();
//            writer = new CustomWriter(textFile);
//            for (String textElement : text) {
//                writer.write(textElement);
//            }
//            writer.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            Assert.assertTrue("Could not create testfiles", false);
//        } finally {
//            assert writer != null;
//            writer.close();
//        }
//
//        File prefixFile = new File(TestData.getBasePath() + "PrefixTestFile.txt");
//        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
//        searchInfiniteKMP.createPrefixFile(patternFile, prefixFile);
//
//        prefixFile = new File(TestData.getBasePath() + "PrefixTestFile.txt0");
//        String[] prefixStrings = readLines(prefixFile);
//        int[] prefixes = new int[prefixStrings.length];
//        for (int i = 0; i < prefixStrings.length; i++) {
//            prefixes[i] = Integer.valueOf(prefixStrings[i]);
//        }
//        Assert.assertEquals("InfiniteKMP index", 10, SearchKMP.kmpSearch(pattern, prefixes, text));
//        Assert.assertEquals("NormalKMP index", 10, SearchKMP.kmpSearch(pattern, SearchKMP.analyzePrefix(pattern), text));
//
//        int[] prefixesStandard = SearchKMP.analyzePrefix(pattern);
//        for (int i = 0; i < prefixesStandard.length; i++) {
//            Assert.assertEquals("Prefix comparison at index " + i, prefixesStandard[i], prefixes[i]);
//        }
//    }

    @Test
    public void testSearch() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        long patternLength = 100;
        long patternPos = 200;
        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);

        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
        long index = searchInfiniteKMP.search(textFile, patternFile);
        Assert.assertEquals("Position of pattern", patternPos, index);
    }

    private static String[] readLines(File file) {
        try {
            String text = "";
            CustomReader reader = new CustomReader(file);
            while (reader.hasNext()) {
                text += reader.readNext();
            }
            return text.split(CustomReader.getLineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String[] read(File file) {
        try {
            List<String> data = new ArrayList<String>();
            CustomReader reader = new CustomReader(file);
            while (reader.hasNext()) {
                data.add(reader.readNext());
            }
            return data.toArray(new String[data.size()]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
