package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

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
