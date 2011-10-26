package de.kmp;

import javax.sound.sampled.Line;
import java.io.*;

/**
 * User: FloLap
 * Date: 25.10.11
 * Time: 20:30
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class SearchInfiniteKMP {

    private long[] prefixCache;
    private String[] patternCache;
    private long currentPrefixPos;
    private long currentPatternPos;
    private long patternPos;
    private long prefixLength;
    private boolean patternHasNext;
    private File prefixFile;
    private File patternFile;
    private File textFile;
    private FileWriter prefixFileWriter;
    private BufferedWriter bufferedPrefixFileWriter;
    private FileReader patternFileReader;
    private FileReader prefixFileReader;
    private BufferedWriter bufferedPrefixFileReader;
    private FileReader textFileReader;

    public SearchInfiniteKMP() {
    }

    public long search(File patternFile, File textFile) {
        try {
            try {
                this.patternFile = patternFile;
                this.textFile = textFile;
                if (!writePrefixFile(patternFile))
                    return -1;



                return 0;
            } catch (Exception ex) {
                ex.printStackTrace();
                return -1;
            } finally {
                prefixFileWriter.close();
                bufferedPrefixFileWriter.close();
                patternFileReader.close();
                prefixFileReader.close();
                textFileReader.close();
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    private boolean writePrefixFile(File patternFile) throws IOException {
        prefixFile = new File(patternFile.getPath().substring(0, patternFile.getPath().lastIndexOf(File.separator) + 1) + "PrefixFile.txt");
        prefixFileWriter = new FileWriter(prefixFile);
        bufferedPrefixFileWriter = new BufferedWriter(prefixFileWriter);
        patternFileReader = new FileReader(patternFile);
        prefixFileReader = new FileReader(prefixFile);

        prefixCache = new long[10000];
        for (int i = 0; i < prefixCache.length; i++) {
            prefixCache[i] = Long.MIN_VALUE;
        }
        currentPrefixPos = 0;
        patternCache = new String[10000];
        for (int i = 0; i < patternCache.length; i++) {
            patternCache[i] = null;
        }
        currentPatternPos = 0;

        patternPos = 0;
        prefixLength = -1;
        patternHasNext = true;

        setPrefixValue(prefixLength);
        while (patternHasNext) {
            while (prefixLength >= 0 && !getPatternAtIndex(prefixLength).equals(getPatternAtIndex(patternPos))) {
                prefixLength = getPrefixAtIndex(prefixLength);
            }
            patternPos++;
            prefixLength++;
            setPrefixValue(prefixLength);
        }

        prefixFileWriter.flush();
        bufferedPrefixFileWriter.flush();
        return true;
    }

    private long getPrefixAtIndex(long pos) throws IOException {
        if (pos <= prefixCache.length) {
            if (prefixCache[(int) pos] != Long.MIN_VALUE) {
                return prefixCache[(int) pos];
            }
        }

        if (pos < currentPrefixPos) {
            prefixFileReader.close();
            prefixFileReader = new FileReader(prefixFile);
            currentPrefixPos = 0;
        }

        //prefix is stored in lines!
        //pos is line number

        if (pos - currentPrefixPos > 1)
            prefixFileReader.skip(pos - currentPrefixPos + 1);
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[1];
        while (prefixFileReader.read(buffer, 0, 1) != -1 &&
                !String.valueOf(buffer).equals("\u0085") &&
                !String.valueOf(buffer).equals("|")) {
            sb.append(buffer);
        }

        currentPrefixPos = pos;

        return Long.valueOf(sb.toString());
    }

    private String getPatternAtIndex(long pos) throws IOException {
        if (pos <= patternCache.length) {
            if (patternCache[(int) pos] != null) {
                return patternCache[(int) pos];
            }
        }

        if (pos < currentPatternPos) {
            patternFileReader.close();
            patternFileReader = new FileReader(patternFile);
            currentPatternPos = 0;
        }

        if (pos - currentPatternPos > 1)
            patternFileReader.skip(pos - currentPatternPos + 1);
        char[] readChars = new char[1];
        patternHasNext = patternFileReader.read(readChars) != -1;
        currentPatternPos = pos;

        String readValue = String.valueOf(readChars);
        if (readValue.equals("\u0000"))
            return null;

        if (pos <= patternCache.length)
            patternCache[(int) pos] = readValue;
        return readValue;
    }

    private void setPrefixValue(long value) throws IOException {
        if (patternPos != 0)
            prefixFileWriter.write("|");
        prefixFileWriter.write(String.valueOf(value));
        if (patternPos < 10000) {
            prefixCache[(int) patternPos] = value;
        }
        prefixFileWriter.flush();
    }
}
