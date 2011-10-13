package de.kmp;

import java.util.List;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 15:11
 */
public abstract class TestHelper {

    public static boolean search(List<ISearch> searcherList, TestData testData, int loops, String testTitle) {
        System.out.println("Test " + testTitle + " (" + loops + " loops each search type)");
        boolean success = true;
        for (ISearch searcher : searcherList) {
            long start = System.currentTimeMillis();
            for (int iii = 0; iii < loops; iii++) {
                search(searcher, testData, success);
            }
            long end = System.currentTimeMillis();
            System.out.println(searcher.getClass().getName() + " mean search time: " + (end - start) / loops + "ms");
        }
        return success;
    }

    private static void search(ISearch searcher, TestData testData, boolean success) {
        try {
            int resultIndex = searcher.search(testData.getText(), testData.getPattern());
            if (resultIndex != testData.getExpectedIndex()) {
                success = false;
                System.out.println("Test failed for " + searcher.getClass().getName() + ". (should be " + testData.getExpectedIndex() + ", but was " + resultIndex + ")");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            success = false;
        }
    }
}
