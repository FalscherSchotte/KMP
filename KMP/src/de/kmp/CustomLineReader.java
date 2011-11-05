package de.kmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:54
 * Reading file access to a file for reading line by line
 */
public class CustomLineReader {
    private File file;
    private BufferedReader bufferedReader;
    private long size;
    private long readPointer;
    private long linePointer;
    private static String lineSeparator;
    private RingBuffer ringBuffer = new RingBuffer();

    /**
     * Constructor for reading of the specified file
     *
     * @param file File to read
     * @throws IOException Exception while creating Reader
     */
    public CustomLineReader(File file) throws IOException {
        this.file = file;
        size = file.length();
        lineSeparator = System.getProperty("line.separator");
        reset();
    }

    /**
     * Resets the Reader and previously closes it
     *
     * @throws IOException Exception while closing
     */
    public void reset() throws IOException {
        close();
        readPointer = 0;
        linePointer = 0;
        bufferedReader = new BufferedReader(new FileReader(file));
    }

    /**
     * Closes the Reader
     *
     * @throws IOException Exception while closing
     */
    public void close() throws IOException {
        if (bufferedReader != null)
            bufferedReader.close();
    }

    private String readNext() throws IOException {
        String line = bufferedReader.readLine();
        ringBuffer.add(linePointer, line);
        linePointer++;
        readPointer += line.length() + lineSeparator.length();
        return line;
    }

    /**
     * Reads the specified line of the file
     *
     * @param lineIndex Index to read
     * @return Read value
     * @throws IOException Exception while reading file
     */
    public String read(long lineIndex) throws IOException {
        if (ringBuffer.get(lineIndex) != null)
            return ringBuffer.get(lineIndex);
        if (lineIndex < linePointer) {
            reset();
        }
        if (lineIndex > linePointer) {
            long linesToSkip = lineIndex - linePointer;
            for (long i = 0; i < linesToSkip; i++) {
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
     * Current position
     *
     * @return position
     */
    public long getPosition() {
        return readPointer;
    }
}
