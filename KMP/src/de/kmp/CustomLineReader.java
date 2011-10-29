package de.kmp;

import java.io.File;
import java.io.IOException;

/**
 * User: FloLap
 * Date: 26.10.11
 * Time: 18:54
 */
public class CustomLineReader extends CustomReader {

    public CustomLineReader(File file) throws IOException {
        super(file);
    }

    public String readNext() throws IOException {
        //Read lines instead of chars!

        String stack = "";
        while (!stack.contains(CustomReader.getLineSeparator()) && hasNext()) {
            char[] buffer = new char[1];
            reader.read(buffer);
            stack += String.valueOf(buffer);

            char[] lookAheadBuffer = new char[1];
            int nextResult = lookAheadReader.read(lookAheadBuffer);
            if (nextResult == -1 || String.valueOf(lookAheadBuffer).equals("\u0000"))
                hasNext = false;
        }

        readPointer++;
        return stack.trim();
    }

    public String read(long index) throws IOException {
        if (index < 0)
            return null;
        if (index < readPointer)
            reset();
        if (index > readPointer) {
            //skip lines, not chars
            long linesToSkip = index - readPointer;
            for(long i=0; i<linesToSkip; i++){
                readNext();
            }
        }
        return readNext();
    }
}