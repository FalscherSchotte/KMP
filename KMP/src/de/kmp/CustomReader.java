package de.kmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 16:03
 */

public class CustomReader {
    protected File file;
    protected FileReader reader;
    protected FileReader lookAheadReader;
    protected BufferedReader bufferedReader;
    protected long readPointer;
    protected boolean hasNext;

    public CustomReader(File file) throws IOException {
        this.file = file;
        hasNext = true;
        readPointer = 0;
        reader = new FileReader(file);
        lookAheadReader = new FileReader(file);
        bufferedReader = new BufferedReader(reader);
        lookAheadReader.read();
    }

    public void reset() throws IOException {
        close();
        reader = new FileReader(file);
        lookAheadReader = new FileReader(file);
        bufferedReader = new BufferedReader(reader);
        readPointer = 0;
        hasNext = true;
        lookAheadReader.read();
    }

    public void close() throws IOException {
        reader.close();
        bufferedReader.close();
        lookAheadReader.close();
    }

    public String readNext() throws IOException {
        char[] buffer = new char[1];
        reader.read(buffer);

        char[] lookAheadBuffer = new char[1];
        int nextResult = lookAheadReader.read(lookAheadBuffer);
        if (nextResult == -1 || String.valueOf(lookAheadBuffer).equals("\u0000"))
            hasNext = false;

        readPointer++;
        return String.valueOf(buffer);
    }

    public String read(long index) throws IOException {
        if (index < 0)
            return null;
        if (index < readPointer)
            reset();
        if (index > readPointer) {
            reader.skip(index - readPointer);
            lookAheadReader.skip(index - readPointer);
            readPointer += index - readPointer;
        }
        return readNext();
    }

    public boolean hasNext() {
        return hasNext;
    }

    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

}

