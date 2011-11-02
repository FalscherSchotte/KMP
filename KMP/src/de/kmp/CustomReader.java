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
    private File file;
    private BufferedReader bufferedReader;
    private long size;
    private long readPointer;
    private RingBuffer ringBuffer = new RingBuffer();

    public CustomReader(File file) throws IOException {
        this.file = file;
        size = file.length();
        reset();
    }

    public void reset() throws IOException {
        close();
        readPointer = 0;
        bufferedReader = new BufferedReader(new FileReader(file));
    }

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

    public long getSize() {
        return size;
    }

    public long getPosition() {
        return readPointer;
    }
}

