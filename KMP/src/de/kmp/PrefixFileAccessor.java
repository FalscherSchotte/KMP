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
        private List<Long> buffer = new ArrayList<Long>((int) separationValue);

        public PrefixFileInterval(File prefixFile, long index) throws IOException {
            File intervalFile = new File(prefixFile.getAbsolutePath() + index);
            writer = new CustomWriter(intervalFile, false);
            reader = new CustomLineReader(intervalFile);
        }

        public void write(long value) throws IOException {
            buffer.add(value);
        }

        public long read(long index) throws IOException {
            if (buffer.size() == 0)
                return Long.valueOf(reader.read(index));
            else
                return buffer.get((int) index);
        }

        public void close() throws IOException {
            flushBuffer();
            writer.close();
            reader.close();
        }

        private void flushBuffer() throws IOException {
            if (buffer.size() <= 0)
                return;
            for (int i = 0; i < buffer.size() - 1; i++) {
                writer.writeLine(buffer.get(i));
            }
            writer.write(buffer.get(buffer.size() - 1));
            writer.flush();
        }

        public void reset(boolean resetWriter) throws IOException {
            flushBuffer();
            if (resetWriter)
                writer.reset();
            reader.reset();
        }
    }

    private List<PrefixFileInterval> prefixIntervalList = new ArrayList<PrefixFileInterval>();
    private File prefixBaseFile;
    private long ctr = 0;
    private final int separationValue = 1000;

    public PrefixFileAccessor() throws IOException {
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

    public void reset(boolean resetWriter) throws IOException {
        for (PrefixFileInterval interval : prefixIntervalList) {
            interval.reset(resetWriter);
        }
    }
}
