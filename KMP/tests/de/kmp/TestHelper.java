package de.kmp;

import java.util.List;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 15:11
 */
public abstract class TestHelper {

    public static boolean search(List<ISearch> searcherList, TestData testData) {
        boolean success = true;
        for (ISearch searcher : searcherList) {
            search(searcher, testData, success);
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
