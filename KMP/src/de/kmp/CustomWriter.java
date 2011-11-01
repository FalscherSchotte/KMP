package de.kmp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 16:03
 */

public class CustomWriter {
    private FileWriter writer;
    private BufferedWriter bufferedWriter;
    private boolean autoflush = false;

    public CustomWriter(File file, boolean autoflush) throws IOException {
        writer = new FileWriter(file);
        bufferedWriter = new BufferedWriter(writer);
        this.autoflush = autoflush;
    }

    public void close() throws IOException {
        bufferedWriter.flush();
        writer.close();
        bufferedWriter.close();
    }

    public void write(String value) throws IOException {
        bufferedWriter.write(value);
        if (autoflush)
            bufferedWriter.flush();
    }

    public void writeLine(String value) throws IOException {
        bufferedWriter.write(value);
        bufferedWriter.newLine();
        if (autoflush)
            bufferedWriter.flush();
    }

    public void writeLine(long value) throws IOException {
        writeLine(String.valueOf(value));
    }

    public void flush() throws IOException {
        bufferedWriter.flush();
    }
}

