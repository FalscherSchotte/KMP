package de.kmp;

import java.io.File;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 25.10.11
 * Time: 20:30
 */
public class SearchInfiniteKMP {

    public long search(File textFile, File patternFile) {
        CustomReader patternReader = null;
        CustomReader textReader = null;
        PrefixFileAccessor prefixWrapper = null;

        try {
            try {
                patternReader = new CustomReader(patternFile);
                textReader = new CustomReader(textFile);
                prefixWrapper = new PrefixFileAccessor(getPrefixFile(patternFile));

                createPrefixFile(patternReader, prefixWrapper);
                patternReader.reset();
                prefixWrapper.reset();

                return kmpSearch(textReader, patternReader, prefixWrapper);
            } finally {
                assert patternReader != null;
                patternReader.close();
                assert textReader != null;
                textReader.close();
                assert prefixWrapper != null;
                prefixWrapper.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }


//        File prefixFile = getPrefixFile(patternFile);
//        if (!createPrefixFile(patternFile, prefixFile))
//            return -1;
//        return kmpSearch(textFile, patternFile, prefixFile);
    }

    private static File getPrefixFile(File patternFile) {
        return new File(patternFile.getPath().substring(0,
                patternFile.getPath().lastIndexOf(File.separator) + 1) + "PrefixFile.txt");
    }

    //    public boolean createPrefixFile(File patternFile, File prefixFile) {
    public void createPrefixFile(CustomReader patternReader, PrefixFileAccessor prefixWrapper) throws IOException {
//        try {
//            PrefixFileAccessor prefixFileAccessor = null;
//            CustomReader patternReader = null;
//            try {
        long patternPos = 0;
        long prefixLength = -1;
//                prefixFileAccessor = new PrefixFileAccessor(prefixFile);
//                patternReader = new CustomReader(patternFile);


        prefixWrapper.write(prefixLength);
        while (patternReader.hasNext()) {
            while (prefixLength >= 0 && !patternReader.read(prefixLength).equals(patternReader.read(patternPos))) {
                prefixLength = prefixWrapper.read(prefixLength);
            }
            patternPos++;
            prefixLength++;
            prefixWrapper.write(prefixLength);
        }
//                return true;
//            } finally {
//                prefixFileAccessor.close();
//                patternReader.close();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
    }

    //    public long kmpSearch(File textFile, File patternFile, File prefixFile) {
    public long kmpSearch(CustomReader textReader, CustomReader patternReader, PrefixFileAccessor prefixWrapper) {


        int textPosition = 0;
        int patternPosition = 0;

        return -1;


//        while (textPosition < textToAnalyze.length) { //until end of text
//            while (patternPosition >= 0 && !textToAnalyze[textPosition].equals(pattern[patternPosition])) {
//                //Move pattern until text and pattern match at i,j
//                patternPosition = prefixValueArray[patternPosition];
//            }
//
//            //move to next position
//            textPosition++;
//            patternPosition++;
//
//            if (patternPosition == pattern.length)
//                return textPosition - pattern.length; //match
//        }
//        return -1; //No match found
    }
}