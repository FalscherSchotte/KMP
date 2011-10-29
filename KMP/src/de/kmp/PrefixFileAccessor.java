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
public class PrefixFileAccessor {

    private class PrefixFileInterval {
        private CustomLineReader reader;
        private CustomWriter writer;

        public PrefixFileInterval(File prefixFile, long index) throws IOException {
            File intervalFile = new File(prefixFile.getAbsolutePath() + index);
            writer = new CustomWriter(intervalFile);
            reader = new CustomLineReader(intervalFile);
        }

        public void write(long value) throws IOException {
            writer.write(value);
        }

        public void writeLine(long value) throws IOException {
            writer.writeLine(value);
        }

        public long read(long index) throws IOException {
            return Long.valueOf(reader.read(index));
        }

        public void close() throws IOException {
            writer.close();
            reader.close();
        }

        public void reset(boolean resetWriter) throws IOException {
            if (resetWriter)
                writer.reset();
            reader.reset();
        }
    }

    private List<PrefixFileInterval> prefixIntervalList = new ArrayList<PrefixFileInterval>();
    private File prefixBaseFile;
    private long ctr = 0;
    private final long separationValue = 10000;

    public PrefixFileAccessor(File prefixFile) throws IOException {
        prefixBaseFile = prefixFile;
        prefixIntervalList.add(new PrefixFileInterval(prefixFile, prefixIntervalList.size()));
    }

    public void write(long value) throws IOException {
        if ((int) (ctr / separationValue) >= prefixIntervalList.size())
            prefixIntervalList.add(new PrefixFileInterval(prefixBaseFile, prefixIntervalList.size()));
        if (ctr == separationValue - 1)
            prefixIntervalList.get((int) (ctr / separationValue)).write(value);
        else
            prefixIntervalList.get((int) (ctr / separationValue)).writeLine(value);
        ctr++;
    }

    public long read(long index) throws IOException {
        return prefixIntervalList.get((int) (index / separationValue)).read(index-  index / separationValue * separationValue);
    }

    public void close() throws IOException {
        for (PrefixFileInterval interval : prefixIntervalList) {
            interval.close();
        }
    }

    public void reset(boolean resetWriter) throws IOException {
        for (PrefixFileInterval interval : prefixIntervalList) {
            interval.reset(resetWriter);
        }
    }
}
