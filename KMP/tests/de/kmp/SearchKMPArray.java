package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 13.10.11
 * Time: 12:07
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class SearchKMPArray {
    private List<ISearchArray> searcherList = null;

    public SearchKMPArray() {
        searcherList = new ArrayList<ISearchArray>();
        searcherList.add(new SearchNaive());
        searcherList.add(new SearchKMP());
    }

    @Test
    public void testSearchSpecificCase() {
        String[] pattern = new String[]{"0", "1", "0", "1", "0", "1"};
        String[] text = new String[]{"0", "1", "0", "1", "0", "0", "0", "1", "0", "1", "0", "1"};
        Assert.assertEquals(6, searcherList.get(1).search(text, pattern));
    }

    @Test
    public void testSearchPatternAtTheBeginning() {
        Assert.assertTrue(search(searcherList, TestData.generateTestData(1000, 100000, 0), 1000, "Pattern at the beginning."));
    }

    @Test
    public void testSearchPatternInTheMiddle() {
        Assert.assertTrue(search(searcherList, TestData.generateTestData(1000, 100000, 50000), 1000, "Pattern in the middle."));
    }

    @Test
    public void testSearchPatternAtTheEnd() {
        Assert.assertTrue(search(searcherList, TestData.generateTestData(1000, 100000, 99000), 1000, "Pattern at the end."));
    }

    @Test
    public void testSearchPatternNotIncluded() {
        TestData testData = new TestData(new String[]{"0", "0", "0"}, new String[]{"0", "1"}, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Pattern not included."));
    }

    @Test
    public void testSearchPatternTooLarge() {
        TestData testData = new TestData(new String[]{"0", "0"}, new String[]{"0", "0", "1"}, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Pattern too large."));
    }

    @Test
    public void testSearchPatternNull() {
        TestData testData = new TestData(null, new String[]{"0"}, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Pattern null."));
    }

    @Test
    public void testSearchTextNull() {
        TestData testData = new TestData(new String[]{"0"}, null, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Text null."));
    }

    @Test
    public void testSearchPatternAndTextNull() {
        TestData testData = new TestData(null, null, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Pattern and Text null."));
    }

    @Test
    public void testSearchPatternEmpty() {
        TestData testData = new TestData(new String[]{"0", "0", "0"}, new String[]{}, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Pattern empty."));
    }

    @Test
    public void testSearchTextEmpty() {
        TestData testData = new TestData(new String[]{}, new String[]{"0", "0"}, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Text empty."));
    }

    @Test
    public void testSearchPatternAndTextEmpty() {
        TestData testData = new TestData(new String[]{}, new String[]{}, -1);
        Assert.assertTrue(search(searcherList, testData, 1, "Pattern and text empty."));
    }

    public static boolean search(List<ISearchArray> searcherList, TestData testData, int loops, String testTitle) {
        System.out.println("Test " + testTitle + " (" + loops + " loop(s) each search type)");
        Boolean success = true;
        for (ISearchArray searcher : searcherList) {
            long start = System.currentTimeMillis();
            for (int iii = 0; iii < loops; iii++) {
                success = success ? search(searcher, testData) : success;
            }
            long end = System.currentTimeMillis();
            System.out.println(searcher.getClass().getName() + " mean search time: " + (end - start) / loops + "ms");
        }
        System.out.println("");
        return success;
    }

    private static boolean search(ISearchArray searcher, TestData testData) {
        try {
            int resultIndex = searcher.search(testData.getTextArray(), testData.getPatternArray());
            if (resultIndex != testData.getExpectedIndex()) {
                System.out.println("Test failed for " + searcher.getClass().getName() + ". (should be " + testData.getExpectedIndex() + ", but was " + resultIndex + ")");
                return false;
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
