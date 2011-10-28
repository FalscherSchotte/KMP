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
    private File file;
    private FileWriter writer;
    private BufferedWriter bufferedWriter;

    public CustomWriter(File file) throws IOException {
        this.file = file;
        if (!file.exists())
            file.createNewFile();

        writer = new FileWriter(file);
        bufferedWriter = new BufferedWriter(writer);
    }

    public void reset() throws IOException {
        close();
        writer = new FileWriter(file);
        bufferedWriter = new BufferedWriter(writer);
    }

    public void close() throws IOException {
        writer.close();
        bufferedWriter.close();
    }

    public void write(String value) throws IOException {
        bufferedWriter.write(value);
        bufferedWriter.flush();
    }

    public void writeLine(String value) throws IOException {
        bufferedWriter.write(value);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void write(long value) throws IOException {
        write(String.valueOf(value));
    }

    public void writeLine(long value) throws IOException {
        writeLine(String.valueOf(value));
    }

    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }
}

