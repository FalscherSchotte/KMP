package de.kmp;

import java.io.File;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 07.10.11
 * Time: 19:30
 * Knuth-Morris-Pratt- / KMP-Search-Algorithm
 */
public class SearchKMP implements ISearchString, ISearchArray, ISearchFile {

    /**
     * Perform search
     *
     * @param text    The target text in which the pattern should be found
     * @param pattern The pattern to find
     * @return Index or -1 if not found
     */
    public int search(String text, String pattern) {
        if (text == null || text.length() <= 0)
            return -1;
        if (pattern == null || pattern.length() <= 0)
            return -1;

        String[] textArray = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            textArray[i] = text.substring(i, i + 1);
        }

        String[] patternArray = new String[pattern.length()];
        for (int i = 0; i < pattern.length(); i++) {
            patternArray[i] = pattern.substring(i, i + 1);
        }

        return search(textArray, patternArray);
    }

    /**
     * Perform search
     *
     * @param text    The target text in which the pattern should be found
     * @param pattern The pattern to find
     * @return Index or -1 if not found
     */
    public int search(String[] text, String[] pattern) {
        if (text == null || pattern == null || text.length == 0 || pattern.length == 0 || pattern.length > text.length)
            return -1;
        return kmpSearch(pattern, analyzePrefix(pattern), text);
    }

    private int[] analyzePrefix(String[] pattern) { //v with length n
        int patternPos = 0; //i - current Position in pattern
        int prefixLength = -1; //j - length of the found prefix
        int[] prefixValueArray = new int[pattern.length + 1]; //N[] with length n+1

        prefixValueArray[patternPos] = prefixLength; //The first value is always -1
        while (patternPos < pattern.length) { //Repeat until pattern end
            while (prefixLength >= 0 && !pattern[prefixLength].equals(pattern[patternPos])) {
                //if current prefix can not be extended, search for a shorter one
                prefixLength = prefixValueArray[prefixLength];
            }
            //here : j=-1 or pattern[patternPos] = pattern[prefixLength]

            patternPos++;
            prefixLength++;
            prefixValueArray[patternPos] = prefixLength; //fill in value in prefixArray
        }
        return prefixValueArray;
    }

    private int kmpSearch(String[] pattern, int[] prefixValueArray, String[] textToAnalyze) {
        //pattern = v with length n
        //prefixValueArray = N with length n+1
        //textToAnalyze = t with length m
        int textPosition = 0; //i
        int patternPosition = 0; //j

        while (textPosition < textToAnalyze.length) { //until end of text
            while (patternPosition >= 0 && !textToAnalyze[textPosition].equals(pattern[patternPosition])) {
                //Move pattern until text and pattern match at i,j
                patternPosition = prefixValueArray[patternPosition];
            }

            //move to next position
            textPosition++;
            patternPosition++;
            if (patternPosition == pattern.length)
                return textPosition - pattern.length; //match
        }
        return -1; //No match found
    }


    /**
     * Perform search
     *
     * @param textFile    The target text in which the pattern should be found
     * @param patternFile The pattern to find
     * @return Index or -1 if not found
     */
    public long search(File textFile, File patternFile) {
        CustomReader patternReader1 = null;
        CustomReader patternReader2 = null;
        CustomReader textReader = null;
        PrefixFileWrapper prefixWrapper = null;

        try {
            try {
                patternReader1 = new CustomReader(patternFile);
                patternReader2 = new CustomReader(patternFile);
                textReader = new CustomReader(textFile);
                prefixWrapper = new PrefixFileWrapper();

                if (patternReader1.getSize() <= 0)
                    return -1;

                createPrefixFile(patternReader1, patternReader2, prefixWrapper);

                patternReader1.reset();
                prefixWrapper.reset();

                return kmpSearch(textReader, patternReader1, prefixWrapper);
            } finally {
                if (patternReader1 != null)
                    patternReader1.close();
                if (patternReader2 != null)
                    patternReader2.close();
                if (textReader != null)
                    textReader.close();
                if (prefixWrapper != null)
                    prefixWrapper.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static void createPrefixFile(CustomReader patternReader1, CustomReader patternReader2, PrefixFileWrapper prefixWrapper) throws IOException {
        long patternPos = 0;
        long prefixLength = -1;

        prefixWrapper.write(prefixLength);
        while (patternPos < patternReader1.getSize()) {
            while (prefixLength >= 0 && !patternReader1.read(prefixLength).equals(patternReader2.read(patternPos))) {
                prefixLength = prefixWrapper.read(prefixLength);
            }
            patternPos++;
            prefixLength++;
            prefixWrapper.write(prefixLength);
        }
    }

    private static long kmpSearch(CustomReader textReader, CustomReader patternReader, PrefixFileWrapper prefixWrapper) throws IOException {
        long textPosition = 0;
        long patternPosition = 0;
        while (textPosition < textReader.getSize()) {
            while (patternPosition >= 0 && !textReader.read(textPosition).equals(patternReader.read(patternPosition))) {
                patternPosition = prefixWrapper.read(patternPosition);
            }
            textPosition++;
            patternPosition++;
            if (patternPosition == patternReader.getSize())
                return textPosition - patternReader.getSize();
        }
        return -1;
    }
}
