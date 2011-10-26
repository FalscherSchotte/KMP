package de.kmp;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:04
 */
public class SearchInfiniteKMPTest {
    @Test
    public void testSearch() throws Exception {

    }

    @Test
    public void testCreatePrefixFile() throws Exception {
        //Perform the test multiple times!
        for (int repetition = 0; repetition < 50; repetition++) {
            //Setup the testdata
            File patternFile = new File(TestData.getBasePath() + "Pattern.txt");
            File textFile = new File(TestData.getBasePath() + "Text.txt");
            long patternLength = 10;
            long patternPos = 10;
            TestData.generateTestDataFiles(patternFile, patternLength, textFile, patternPos);

            //Create prefix file
            SearchInfiniteKMP.createPrefixFile(patternFile, SearchInfiniteKMP.getPrefixFile(patternFile));

            //Read the file to compare it
            String[] generatedPrefixTable = readLines(SearchInfiniteKMP.getPrefixFile(patternFile));

            System.out.println("Generated prefixtable");
            for(int i=0; i < generatedPrefixTable.length; i++){
                System.out.print(generatedPrefixTable[i]);
            }
            System.out.println("");

            //Get reference table with the correct data
            int[] referencePrefixTable = SearchKMP.analyzePrefix(read(patternFile));

            Assert.assertEquals("Length missmatch", referencePrefixTable.length, generatedPrefixTable.length);
            for (int i = 0; i < referencePrefixTable.length; i++) {
                Assert.assertEquals("Missmatch at index " + i, referencePrefixTable[i], (int) Integer.valueOf(generatedPrefixTable[i]));
            }
        }
    }

    private String[] readLines(File file) {
        try {
            String text = "";
            CustomReader reader = new CustomReader(file);
            while (reader.hasNext()) {
                text += reader.readNext();
            }
            return text.split(CustomReader.getLineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String[] read(File file) {
        try {
            List<String> data = new ArrayList<String>();
            CustomReader reader = new CustomReader(file);
            while (reader.hasNext()) {
                data.add(reader.readNext());
            }
            return data.toArray(new String[data.size()]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
