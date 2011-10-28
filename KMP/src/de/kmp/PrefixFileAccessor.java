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
            writer.writeLine(value);
        }

        public long read(long index) throws IOException {
            return Long.valueOf(reader.read(index));
        }

        public void close() throws IOException {
            writer.close();
            reader.close();
        }

        public void reset() throws IOException {
            writer.reset();
            reader.reset();
        }
    }

//    private CustomLineReader reader;
//    private CustomWriter writer;

//    public PrefixFileAccessor(File prefixFile) throws IOException {
//        writer = new CustomWriter(prefixFile);
//        reader = new CustomLineReader(prefixFile);
//    }

    List<PrefixFileInterval> prefixIntervalList = new ArrayList<PrefixFileInterval>();
    File prefixBaseFile;

    //Create a seperate file for each 10000 characters
    public PrefixFileAccessor(File prefixFile) throws IOException {
        prefixBaseFile = prefixFile;
        prefixIntervalList.add(new PrefixFileInterval(prefixFile, prefixIntervalList.size()));
    }

//    public void write(long value) throws IOException {
//        writer.writeLine(value);
//    }

    private long ctr = 0;
    private final long seperationValue = 10000;

    public void write(long value) throws IOException {
        if ((int) (ctr / seperationValue) > prefixIntervalList.size())
            prefixIntervalList.add(new PrefixFileInterval(prefixBaseFile, prefixIntervalList.size()));
        prefixIntervalList.get((int) (ctr / seperationValue)).write(value);
        ctr++;
    }


//    public long read(long index) throws IOException {
//        return Long.valueOf(reader.read(index));
//    }

    public long read(long index) throws IOException {
        return Long.valueOf(prefixIntervalList.get((int) (index / seperationValue)).read(index));
    }

//    public void close() throws IOException {
//        writer.close();
//        reader.close();
//    }

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


}
