package de.kmp;

import java.io.*;

/**
 * User: FloLap
 * Date: 07.10.11
 * Time: 19:30
 * Knuth-Morris-Pratt- / KMP-Search-Algorithm
 */
public class SearchKMP implements ISearch {

    public int search(String[] text, String[] pattern) {
        if (text == null || pattern == null || text.length == 0 || pattern.length == 0 || pattern.length > text.length)
            return -1;
        return kmpSearch(pattern, analyzePrefix(pattern), text);
    }

    public static int[] analyzePrefix(String[] pattern) { //v with length n
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

    public static int kmpSearch(String[] pattern, int[] prefixValueArray, String[] textToAnalyze) {
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
}
