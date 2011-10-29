package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:04
 */
public class SearchInfiniteKMPTest {

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

    @Test
    public void testSearchDifferentCases() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        for (int i = 0; i < 50; i++) {
            long patternLength = (i + 1) * 100;
            long patternPos = (i + 1) * 250;
            TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
            SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
            long start = System.currentTimeMillis();
            long index = searchInfiniteKMP.search(textFile, patternFile);
            long end = System.currentTimeMillis();
            System.out.println("Search pattern of size " + patternLength + " in text of size " + (patternLength + patternPos) +
                    " took " + (end - start) + "ms.");
            Assert.assertEquals("Position of pattern", patternPos, index);
        }
    }

    @Test
    public void testSearchLarge() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        long patternLength = 10000;
        long patternPos = 90000;
        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
        long start = System.currentTimeMillis();
        long index = searchInfiniteKMP.search(textFile, patternFile);
        long end = System.currentTimeMillis();
        System.out.println("Search pattern of size " + patternLength + " in text of size " + (patternLength + patternPos) +
                " took " + (end - start) + "ms.");
        Assert.assertEquals("Position of pattern", patternPos, index);
    }

    @Test
    public void testSearchVeryLarge() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        long patternLength = 100000;
        long patternPos = 900000;
        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
        long start = System.currentTimeMillis();
        long index = searchInfiniteKMP.search(textFile, patternFile);
        long end = System.currentTimeMillis();
        System.out.println("Search pattern of size " + patternLength + " in text of size " + (patternLength + patternPos) +
                " took " + (end - start) + "ms.");
        Assert.assertEquals("Position of pattern", patternPos, index);
    }

    @Test
    public void testSearchInsane() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        long patternLength = 100000;
        long patternPos = 10000000;
        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
        SearchInfiniteKMP searchInfiniteKMP = new SearchInfiniteKMP();
        long start = System.currentTimeMillis();
        long index = searchInfiniteKMP.search(textFile, patternFile);
        long end = System.currentTimeMillis();
        System.out.println("Search pattern of size " + patternLength + " in text of size " + (patternLength + patternPos) +
                " took " + (end - start) + "ms.");
        Assert.assertEquals("Position of pattern", patternPos, index);
    }
}
