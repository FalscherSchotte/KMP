package de.kmp;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 14:41
 */
public class SearchNaive implements ISearch {

    public int search(String[] data, String[] pattern) {
        for (int baseIndex = 0; baseIndex < data.length; baseIndex++) {
            if (matches(data, pattern, baseIndex))
                return baseIndex;
        }
        return -1;
    }

    private boolean matches(String[] data, String[] pattern, int baseIndex) {
        for (int patternIndex = 0; patternIndex < pattern.length; patternIndex++) {
            if(baseIndex + patternIndex >= data.length)
                return false;
            if (!data[baseIndex + patternIndex].equals(pattern[patternIndex]))
                return false;
        }
        return true;
    }
}
