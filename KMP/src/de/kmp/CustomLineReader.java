package de.kmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:54
 */
public class CustomLineReader {
    private File file;
    private BufferedReader bufferedReader;
    private long size;
    private long readPointer;
    private long linePointer;
    private static String lineSeparator;
    private StackBuffer stackBuffer = new StackBuffer();

    public CustomLineReader(File file) throws IOException {
        this.file = file;
        size = file.length();
        lineSeparator = System.getProperty("line.separator");
        reset();
    }

    public void reset() throws IOException {
        close();
        readPointer = 0;
        linePointer = 0;
        bufferedReader = new BufferedReader(new FileReader(file));
    }

    public void close() throws IOException {
        if (bufferedReader != null)
            bufferedReader.close();
    }

    private String readNext() throws IOException {
        String line = bufferedReader.readLine();
        stackBuffer.add(linePointer, line);
        linePointer++;
        readPointer += line.length() + lineSeparator.length();
        return line;
    }

    public String read(long lineIndex) throws IOException {
        if(stackBuffer.get(lineIndex) != null)
            return stackBuffer.get(lineIndex);
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

    public long getSize() {
        return size;
    }

    public long getPosition() {
        return readPointer;
    }

    public long getLineNumber() {
        return linePointer;
    }

    public static String getLineSeparator() {
        return lineSeparator;
    }
}
