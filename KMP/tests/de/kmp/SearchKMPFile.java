package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:04
 */
public class SearchKMPFile {
    private ISearchFile kmp = new SearchKMP();
    private ISearchFile naive = new SearchNaive();
    private ISearchFile[] searchers = new ISearchFile[]{kmp, naive};

    @Test
    public void testSearchSmall() throws Exception {
        performTest(searchers, 1000, 1000);
    }

    @Test
    public void testSearchMedium() throws Exception {
        performTest(searchers, 10000, 10000);
    }

    @Test
    public void testSearchLarge() throws Exception {
        performTest(searchers, 100000, 100000);
    }

    @Test
    public void testSearchVeryLarge() throws Exception {
        performTest(searchers, 1000000, 1000000);
    }

    @Test
    public void testSearchInsane() throws Exception {
        performTest(new ISearchFile[]{kmp}, 10000000, 10000000);
    }

    @Test
    public void testSearchUnevenNumber() throws Exception {
        performTest(new ISearchFile[]{kmp}, 33333, 5432178);
    }

    @Test
    public void testSearchPatternIsText() throws Exception {
        performTest(new ISearchFile[]{kmp}, 1000, 0);
    }

    @Test
    public void testSearchPerformanceComparison() throws Exception {
        performTest(searchers, 500, 100000);
    }

    @Test
    public void testSearchSpecificCase() throws Exception {
        File pattern = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\TestFilePattern.txt");
        File text = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\TestFileText.txt");
        performSpecificTest(new ISearchFile[]{kmp}, pattern, text, 10000000);
    }

    @Test
    public void testManuel0() throws Exception {
        File pattern = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\pattern0.txt");
        File text = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\text0.txt");
        performSpecificTest(new ISearchFile[]{kmp}, pattern, text, 1000);
    }

    @Test
    public void testManuel1() throws Exception {
        File pattern = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\pattern1.txt");
        File text = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\text1.txt");
        performSpecificTest(new ISearchFile[]{kmp}, pattern, text, 10000);
    }

    @Test
    public void testManuel2() throws Exception {
        File pattern = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\pattern2.txt");
        File text = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\text2.txt");
        performSpecificTest(new ISearchFile[]{kmp}, pattern, text, 100000);
    }

    @Test
    public void testManuel3() throws Exception {
        File pattern = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\pattern3.txt");
        File text = new File("E:\\HsKA\\Semester2\\Algorithmen Labor\\KMP\\tests\\de\\kmp\\ManuelTestCases\\text3.txt");
        performSpecificTest(new ISearchFile[]{kmp}, pattern, text, 1000000);
    }

    @Test
    public void testPatternEmpty() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        TestData.generateTestDataFiles(patternFile, 10, textFile, 1000);
        FileWriter writer = new FileWriter(patternFile);
        writer.write("");
        writer.close();

        ISearchFile searcher = new SearchKMP();
        long start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        long end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 0 + " chars) in text of size "
                + (1000) + " took " + (end - start) + "ms");

        searcher = new SearchNaive();
        start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 0 + " chars) in text of size "
                + (1000) + " took " + (end - start) + "ms");
    }

    @Test
    public void testTextEmpty() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        TestData.generateTestDataFiles(patternFile, 10, textFile, 10);
        FileWriter writer = new FileWriter(textFile);
        writer.write("");
        writer.close();

        ISearchFile searcher = new SearchKMP();
        long start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        long end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 10 + " chars) in text of size "
                + (0) + " took " + (end - start) + "ms");

        searcher = new SearchNaive();
        start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 10 + " chars) in text of size "
                + (0) + " took " + (end - start) + "ms");
    }

    @Test
    public void testTextAndPatternEmpty() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        FileWriter writer = new FileWriter(patternFile);
        writer.write("");
        writer.close();
        writer = new FileWriter(textFile);
        writer.write("");
        writer.close();

        ISearchFile searcher = new SearchKMP();
        long start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        long end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 0 + " chars) in text of size "
                + (0) + " took " + (end - start) + "ms");

        searcher = new SearchNaive();
        start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 0 + " chars) in text of size "
                + (0) + " took " + (end - start) + "ms");
    }

    @Test
    public void testPatternLargerThanText() throws Exception {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        FileWriter writer = new FileWriter(patternFile);
        writer.write("12345");
        writer.close();
        writer = new FileWriter(textFile);
        writer.write("1234");
        writer.close();

        ISearchFile searcher = new SearchKMP();
        long start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        long end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 5 + " chars) in text of size "
                + (4) + " took " + (end - start) + "ms");

        searcher = new SearchNaive();
        start = System.currentTimeMillis();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
        end = System.currentTimeMillis();
        System.out.println(searcher.getClass().getName() + ": Search of pattern (" + 5 + " chars) in text of size "
                + (4) + " took " + (end - start) + "ms");
    }

    @Test
    public void testFileNotExists() {
        File patternFile = new File(TestData.getBasePath() + "FOO.txt");
        File textFile = new File(TestData.getBasePath() + "FOO2.txt");
        ISearchFile searcher = new SearchKMP();
        Assert.assertEquals("Position of pattern", -1, searcher.search(textFile, patternFile));
    }

    private void performTest(ISearchFile[] searchers, long patternLength, long patternPos) {
        File patternFile = new File(TestData.getBasePath() + "PatternTestFile.txt");
        File textFile = new File(TestData.getBasePath() + "TextTestFile.txt");
        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);

        System.out.println();
        for (ISearchFile searcher : searchers) {
            long start = System.currentTimeMillis();
            Assert.assertEquals("Position of pattern", patternPos, searcher.search(textFile, patternFile));
            long end = System.currentTimeMillis();
            System.out.println(searcher.getClass().getName() + ": Search of pattern (" + patternLength + " chars) in text of size "
                    + (patternPos + patternLength) + " took " + (end - start) + "ms");
        }
    }

    private void performSpecificTest(ISearchFile[] searchers, File pattern, File text, long expectedIndex) {
        System.out.println();
        for (ISearchFile searcher : searchers) {
            long start = System.currentTimeMillis();
            Assert.assertEquals("Position of pattern", expectedIndex, searcher.search(text, pattern));
            long end = System.currentTimeMillis();
            System.out.println(searcher.getClass().getName() + ": Search of pattern (" + pattern.getName() + ") in text ("
                    + (text.getName()) + ") took " + (end - start) + "ms");
        }
    }
}
