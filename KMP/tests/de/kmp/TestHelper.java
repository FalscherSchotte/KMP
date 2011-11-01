package de.kmp;

import java.util.List;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 15:11
 */
public abstract class TestHelper {

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
            int resultIndex = searcher.search(testData.getText(), testData.getPattern());
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
