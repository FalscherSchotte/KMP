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
public class SearchTest {

    @Test
    public void testSearch() throws Exception {
        List<ISearch> searcherList = new ArrayList<ISearch>();
        searcherList.add(new SearchNaive());
        searcherList.add(new SearchKMP());

        File file = new File(TestData.getBasePath() + "TestData01.txt");
        TestData.generateTestData(file, 1000, 100000, 99000);
        TestData testData = TestData.read(file);

        boolean success = TestHelper.search(searcherList, testData);
        Assert.assertTrue(success);
    }
}
