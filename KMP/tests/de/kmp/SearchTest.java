package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 13.10.11
 * Time: 12:07
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class SearchTest {
    private List<ISearch> searcherList = null;

    public SearchTest() {
        searcherList = new ArrayList<ISearch>();
        searcherList.add(new SearchNaive());
        searcherList.add(new SearchKMP());
    }
//
//    @Test
//    public void testSearchNormal() {
//        File file = new File(TestData.getBasePath() + "TestData001.txt");
//        //TestData.generateTestDataFile(file, 1000, 100000, 99000);
//        TestData testData = TestData.read(file);
//        Assert.assertTrue(TestHelper.search(searcherList, testData, 1000, "TestData0001.txt"));
//    }

    @Test
    public void testSearchPatternAtTheBeginning() {
        Assert.assertTrue(TestHelper.search(searcherList, TestData.generateTestData(1000, 100000, 0), 1000, "Pattern at the beginning."));
    }

    @Test
    public void testSearchPatternInTheMiddle() {
        Assert.assertTrue(TestHelper.search(searcherList, TestData.generateTestData(1000, 100000, 50000), 1000, "Pattern in the middle."));
    }

    @Test
    public void testSearchPatternAtTheEnd() {
        Assert.assertTrue(TestHelper.search(searcherList, TestData.generateTestData(1000, 100000, 99000), 1000, "Pattern at the end."));
    }

    @Test
    public void testSearchPatternNotIncluded() {
        TestData testData = new TestData(new String[]{"0", "0", "0"}, new String[]{"0", "1"}, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Pattern not included."));
    }

    @Test
    public void testSearchPatternTooLarge() {
        TestData testData = new TestData(new String[]{"0", "0"}, new String[]{"0", "0", "1"}, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Pattern too large."));
    }

    @Test
    public void testSearchPatternNull() {
        TestData testData = new TestData(null, new String[]{"0"}, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Pattern null."));
    }

    @Test
    public void testSearchTextNull() {
        TestData testData = new TestData(new String[]{"0"}, null, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Text null."));
    }

    @Test
    public void testSearchPatternAndTextNull() {
        TestData testData = new TestData(null, null, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Pattern and Text null."));
    }

    @Test
    public void testSearchPatternEmpty() {
        TestData testData = new TestData(new String[]{"0", "0", "0"}, new String[]{}, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Pattern empty."));
    }

    @Test
    public void testSearchTextEmpty() {
        TestData testData = new TestData(new String[]{}, new String[]{"0", "0"}, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Text empty."));
    }

    @Test
    public void testSearchPatternAndTextEmpty() {
        TestData testData = new TestData(new String[]{}, new String[]{}, -1);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1, "Pattern and text empty."));
    }

    @Test
    public void testWriteReadInfiniteSmall() {
        File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
        File textFile = new File(TestData.getBasePath() + "Text.txt");
        long patternLength = 2;
        long patternPos = 1;

        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
        TestData testData = new TestData(TestData.readStringArray(textFile), TestData.readStringArray(patternFile), (int) patternPos);

        Assert.assertTrue(TestHelper.search(searcherList, testData, 1000,
                "File search with pattern length " + patternLength + " at pos " + patternPos + ". "));
    }

    @Test
    public void testWriteReadInfiniteMedium() {
        File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
        File textFile = new File(TestData.getBasePath() + "Text.txt");
        long patternLength = 1000;
        long patternPos = 99000;

        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
        TestData testData = new TestData(TestData.readStringArray(textFile), TestData.readStringArray(patternFile), (int) patternPos);

        Assert.assertTrue(TestHelper.search(searcherList, testData, 1000,
                "File search with pattern length " + patternLength + " at pos " + patternPos + ". "));
    }

    @Test
    public void testWriteReadSearchInfiniteSmall() {
        File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
        File textFile = new File(TestData.getBasePath() + "Text.txt");
        long patternLength = 10;
        long patternPos = 10;

        TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);
        SearchInfiniteKMP searcher = new SearchInfiniteKMP();
        long foundIndex = searcher.search(patternFile, textFile);

        Assert.assertEquals("Search infinite small.", patternPos, foundIndex);
    }
}
