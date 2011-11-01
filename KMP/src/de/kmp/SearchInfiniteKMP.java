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

                long start = System.currentTimeMillis();
                createPrefixFile(patternReader1, patternReader2, prefixWrapper);
                long end = System.currentTimeMillis();
                System.out.println("Create prefix files took " + (end - start) + "ms.");

                patternReader1.reset();
                prefixWrapper.reset(false);

                return kmpSearch(textReader, patternReader1, prefixWrapper);
            } finally {
                patternReader1.close();
                patternReader2.close();
                textReader.close();
                prefixWrapper.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public void createPrefixFile(CustomReader patternReader1, CustomReader patternReader2, PrefixFileWrapper prefixWrapper) throws IOException {
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

    public long kmpSearch(CustomReader textReader, CustomReader patternReader, PrefixFileWrapper prefixWrapper) throws IOException {
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
        return -1; //No match found
    }
}