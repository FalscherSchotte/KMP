package de.kmp;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 14:41
 */
public class SearchNaive implements ISearch {

    public int search(String[] text, String[] pattern) {
        if (text == null || pattern == null || text.length == 0 || pattern.length == 0)
            return -1;
        for (int baseIndex = 0; baseIndex < text.length; baseIndex++) {
            if (matches(text, pattern, baseIndex))
                return baseIndex;
        }
        return -1;
    }

    private boolean matches(String[] data, String[] pattern, int baseIndex) {
        for (int patternIndex = 0; patternIndex < pattern.length; patternIndex++) {
            if (baseIndex + patternIndex >= data.length)
                return false;
            if (!data[baseIndex + patternIndex].equals(pattern[patternIndex]))
                return false;
            if (patternIndex == pattern.length - 1)
                return true;
        }
        return false;
    }
}
