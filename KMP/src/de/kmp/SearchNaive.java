package de.kmp;

import java.io.File;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 14:41
 */
public class SearchNaive implements ISearchString, ISearchArray, ISearchFile {

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

    public long search(File text, File pattern) {
        CustomReader textReader = null;
        CustomReader patternReader = null;
        try {
            try {
                textReader = new CustomReader(text);
                patternReader = new CustomReader(pattern);
                for (long baseIndex = 0; baseIndex < textReader.getSize(); baseIndex++) {
                    if (matches(textReader, patternReader, baseIndex))
                        return baseIndex;
                }
                return -1;
            } finally {
                assert textReader != null;
                textReader.close();
                assert patternReader != null;
                patternReader.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private boolean matches(CustomReader textReader, CustomReader patternReader, long baseIndex) throws IOException {
        for (long patternIndex = 0; patternIndex < patternReader.getSize(); patternIndex++) {
            if (baseIndex + patternIndex >= textReader.getSize())
                return false;
            if (!textReader.read(baseIndex + patternIndex).equals(patternReader.read(patternIndex)))
                return false;
            if (patternIndex == patternReader.getSize() - 1)
                return true;
        }
        return false;
    }
}
