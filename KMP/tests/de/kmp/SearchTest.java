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

    private List<ISearch> searcherList = null;

    public SearchTest() {
        searcherList = new ArrayList<ISearch>();
        searcherList.add(new SearchNaive());
        searcherList.add(new SearchKMP());
    }

    @Test
    public void testSearchNormal() throws Exception {
        File file = new File(TestData.getBasePath() + "TestData001.txt");
        //TestData.generateTestDataFile(file, 1000, 100000, 99000);
        TestData testData = TestData.read(file);
        Assert.assertTrue(TestHelper.search(searcherList, testData, 1000, "TestData0001.txt"));
    }

    public void testSearchPatternNotIncluded() throws Exception {
//         Assert.assertTrue(TestHelper.search(searcherList, TestData.generateTestData(0, 1000, -1), 1, "Pattern not included."));
    }


}
