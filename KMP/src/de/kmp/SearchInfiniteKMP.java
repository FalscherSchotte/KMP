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
                prefixWrapper.reset(false);

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
    }

    private static File getPrefixFile(File patternFile) {
        return new File(patternFile.getPath().substring(0,
                patternFile.getPath().lastIndexOf(File.separator) + 1) + "PrefixFile.txt");
    }

    public void createPrefixFile(CustomReader patternReader, PrefixFileAccessor prefixWrapper) throws IOException {
        long patternPos = 0;
        long prefixLength = -1;

        prefixWrapper.write(prefixLength);
        while (patternReader.hasNext()) {
            while (prefixLength >= 0 && !patternReader.read(prefixLength).equals(patternReader.read(patternPos))) {
                prefixLength = prefixWrapper.read(prefixLength);
            }
            patternPos++;
            prefixLength++;
            prefixWrapper.write(prefixLength);
        }
    }

    public long kmpSearch(CustomReader textReader, CustomReader patternReader, PrefixFileAccessor prefixWrapper) throws IOException {
        long textPosition = 0;
        long patternPosition = 0;
        while (textReader.hasNext()) {
            while (patternPosition >= 0 && !textReader.read(textPosition).equals(patternReader.read(patternPosition))) {
                patternPosition = prefixWrapper.read(patternPosition);
            }
            textPosition++;
            patternPosition++;
            if (!patternReader.hasNext())
                return textPosition - patternReader.getCurrentPosition();
        }
        return -1; //No match found
    }
}