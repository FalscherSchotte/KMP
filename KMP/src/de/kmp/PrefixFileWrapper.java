package de.kmp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: FloLap
 * Date: 28.10.11
 * Time: 17:30
 */
public class PrefixFileWrapper {
    private List<PrefixFileInterval> prefixIntervalList = new ArrayList<PrefixFileInterval>();
    private File prefixBaseFile;
    private long ctr = 0;
    private final int separationValue = 10000;

    public PrefixFileWrapper() throws IOException {
        prefixBaseFile = new File(System.getProperty("java.io.tmpdir") + "kmp.prefix");
        prefixIntervalList.add(new PrefixFileInterval(prefixBaseFile, prefixIntervalList.size()));
    }

    public void write(long value) throws IOException {
        if ((int) (ctr / separationValue) >= prefixIntervalList.size())
            prefixIntervalList.add(new PrefixFileInterval(prefixBaseFile, prefixIntervalList.size()));
        prefixIntervalList.get((int) (ctr / separationValue)).write(value);
        ctr++;
    }

    public long read(long index) throws IOException {
        return prefixIntervalList.get((int) (index / separationValue)).read(index - index / separationValue * separationValue);
    }

    public void close() throws IOException {
        for (PrefixFileInterval interval : prefixIntervalList) {
            interval.close();
        }
    }

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
