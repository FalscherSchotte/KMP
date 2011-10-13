package de.kmp;

import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 08.10.11
 * Time: 19:51
 */
public class SearchKMPManuel implements ISearch {
    public int search(String[] data, String[] pattern) {
        return InText(data, pattern, ForPattern(pattern)).get(0);
    }

    public static List<Integer> InText(String[] text, String[] pattern, int[] prefixes) {
        List<Integer> findings = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while (i < text.length) {
            while (j >= 0 && !text[i].equals(pattern[j])) {
                j = prefixes[j];
            }
            i = i + 1;
            j = j + 1;
            if (j == pattern.length) {
                findings.add(i - pattern.length);
                j = prefixes[j];
            }
        }
        return findings;
    }

    public static int[] ForPattern(String[] pattern) {
        int[] prefixes = new int[pattern.length + 1];
        int i = 0;
        int j = -1;
        prefixes[i] = j;
        while (i < pattern.length) {
            while (j >= 0 && !pattern[j].equals(pattern[i])) {
                j = prefixes[j];
            }
            i = i + 1;
            j = j + 1;
            prefixes[i] = j;
        }
        return prefixes;
    }
}
