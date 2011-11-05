package de.kmp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 16:03
 * Class to access a file to write values
 */
public class CustomWriter {
    private FileWriter writer;
    private BufferedWriter bufferedWriter;
    private boolean autoflush = false;

    /**
     * Creates a new Writer for the specified file
     *
     * @param file      File to read
     * @param autoflush Autoflush the written values
     * @throws IOException Exception while creating Writer
     */
    public CustomWriter(File file, boolean autoflush) throws IOException {
        writer = new FileWriter(file);
        bufferedWriter = new BufferedWriter(writer);
        this.autoflush = autoflush;
    }

    /**
     * Closes the writer and previously flushes the data
     *
     * @throws IOException
     */
    public void close() throws IOException {
        bufferedWriter.flush();
        writer.close();
        bufferedWriter.close();
    }

    /**
     * Write the given value to file
     *
     * @param value Value to write
     * @throws IOException Exception while writing
     */
    public void write(String value) throws IOException {
        bufferedWriter.write(value);
        if (autoflush)
            bufferedWriter.flush();
    }

    /**
     * Write the given value and then sets a new line
     *
     * @param value Value to write
     * @throws IOException Exception while writing
     */
    public void writeLine(String value) throws IOException {
        bufferedWriter.write(value);
        bufferedWriter.newLine();
        if (autoflush)
            bufferedWriter.flush();
    }

    /**
     * Write the given long value
     *
     * @param value Value to write
     * @throws IOException Exception while writing
     */
    public void writeLine(long value) throws IOException {
        writeLine(String.valueOf(value));
    }

    /**
     * Manually flushes the writer
     *
     * @throws IOException Exception while flushing
     */
    public void flush() throws IOException {
        bufferedWriter.flush();
    }
}

