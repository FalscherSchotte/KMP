package de.kmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 16:03
 * Reading file access to a file reading sign by sign
 */
public class CustomReader {
    private File file;
    private BufferedReader bufferedReader;
    private long size;
    private long readPointer;
    private RingBuffer ringBuffer = new RingBuffer();

    /**
     * Constructor to create a reader for the specified file
     *
     * @param file File to read
     * @throws IOException Exception while creating reader
     */
    public CustomReader(File file) throws IOException {
        this.file = file;
        size = file.length();
        reset();
    }

    /**
     * Resets the reader and previously closes it
     *
     * @throws IOException
     */
    public void reset() throws IOException {
        close();
        readPointer = 0;
        bufferedReader = new BufferedReader(new FileReader(file));
    }

    /**
     * Closes the Reader
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (bufferedReader != null)
            bufferedReader.close();
    }

    private String readNext() throws IOException {
        char[] buffer = new char[1];
        bufferedReader.read(buffer);
        ringBuffer.add(readPointer, String.valueOf(buffer));
        readPointer++;
        return String.valueOf(buffer);
    }

    /**
     * Reads the sign at the specified index
     *
     * @param index Index to read
     * @return Read value
     * @throws IOException Exception while reading
     */
    public String read(long index) throws IOException {
        if (ringBuffer.get(index) != null)
            return ringBuffer.get(index);
        if (index < readPointer) {
            reset();
        }
        if (index > readPointer) {
            long charsToSkip = index - readPointer;
            for (long i = 0; i < charsToSkip; i++) {
                readNext();
            }
        }
        return readNext();
    }

    /**
     * Size of the file
     *
     * @return size
     */
    public long getSize() {
        return size;
    }

    /**
     * Current position in the file
     *
     * @return position
     */
    public long getPosition() {
        return readPointer;
    }
}

