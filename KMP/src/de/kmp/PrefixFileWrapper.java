package de.kmp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 28.10.11
 * Time: 17:30
 * Wraps Reader and Writer for the prefixanalysis
 */
public class PrefixFileWrapper {
    private List<PrefixFileInterval> prefixIntervalList = new ArrayList<PrefixFileInterval>();
    private File prefixBaseFile;
    private long ctr = 0;
    private final int separationValue = 10000;

    /**
     * Creates a new Wrapper for the prefixes
     *
     * @throws IOException
     */
    public PrefixFileWrapper() throws IOException {
        prefixBaseFile = new File(System.getProperty("java.io.tmpdir") + "kmp.prefix");
        prefixIntervalList.add(new PrefixFileInterval(prefixBaseFile, prefixIntervalList.size()));
    }

    /**
     * Write prefix value
     *
     * @param value Value to write
     * @throws IOException Exception during writing
     */
    public void write(long value) throws IOException {
        if ((int) (ctr / separationValue) >= prefixIntervalList.size())
            prefixIntervalList.add(new PrefixFileInterval(prefixBaseFile, prefixIntervalList.size()));
        prefixIntervalList.get((int) (ctr / separationValue)).write(value);
        ctr++;
    }

    /**
     * Reads the value at the given position
     *
     * @param index Index to read
     * @return Read value
     * @throws IOException Exception while reading
     */
    public long read(long index) throws IOException {
        return prefixIntervalList.get((int) (index / separationValue)).read(index - index / separationValue * separationValue);
    }

    /**
     * Closes the reader and writer
     *
     * @throws IOException Exception while closing
     */
    public void close() throws IOException {
        for (PrefixFileInterval interval : prefixIntervalList) {
            interval.close();
        }
    }

    /**
     * Resets the reader
     *
     * @throws IOException
     */
    public void reset() throws IOException {
        for (PrefixFileInterval interval : prefixIntervalList) {
            interval.reset();
        }
    }

    private class PrefixFileInterval {
        private CustomLineReader reader;
        private CustomWriter writer;

        public PrefixFileInterval(File prefixFile, long index) throws IOException {
            File intervalFile = new File(prefixFile.getAbsolutePath() + index);
            writer = new CustomWriter(intervalFile, false);
            reader = new CustomLineReader(intervalFile);
        }

        public void write(long value) throws IOException {
            writer.writeLine(value);
            writer.flush();
        }

        public long read(long index) throws IOException {
            return Long.valueOf(reader.read(index));
        }

        public void close() throws IOException {
            writer.close();
            reader.close();
        }

        public void reset() throws IOException {
            reader.reset();
        }
    }
}
