package de.kmp;

import java.io.File;

/**
 * User: FloLap
 * Date: 25.10.11
 * Time: 20:30
 */
public class SearchInfiniteKMP {

    public static long search(File textFile, File patternFile) {
        File prefixFile = getPrefixFile(patternFile);

        if (!createPrefixFile(patternFile, prefixFile))
            return -1;

        return kmpSearch(textFile, patternFile, prefixFile);
    }

    public static File getPrefixFile(File patternFile) {
        return new File(patternFile.getPath().substring(0,
                patternFile.getPath().lastIndexOf(File.separator) + 1) + "PrefixFile.txt");
    }

    public static boolean createPrefixFile(File patternFile, File prefixFile) {
        try {
            CustomWriter prefixWriter = null;
            CustomReader prefixReader = null;
            CustomReader patternReader = null;
            try {
                long patternPos = 0;
                long prefixLength = -1;
                prefixWriter = new CustomWriter(prefixFile);
                prefixReader = new CustomLineReader(prefixFile);
                patternReader = new CustomReader(patternFile);

                prefixWriter.writeLine(prefixLength);
                while (patternReader.hasNext()) {
                    while (prefixLength >= 0 && !patternReader.read(prefixLength).equals(patternReader.read(patternPos))) {
                        prefixLength = Long.valueOf(prefixReader.read(prefixLength));
                    }
                    patternPos++;
                    prefixLength++;
                    prefixWriter.writeLine(prefixLength);
                }
                return true;

//                 prefixValueArray[patternPos] = prefixLength; //The first value is always -1
//                while (patternPos < pattern.length) { //Repeat until pattern end
//                    while (prefixLength >= 0 && !pattern[prefixLength].equals(pattern[patternPos])) {
//                        //if current prefix can not be extended, search for a shorter one
//                        prefixLength = prefixValueArray[prefixLength];
//                    }
//                    //here : j=-1 or pattern[patternPos] = pattern[prefixLength]
//
//                    patternPos++;
//                    prefixLength++;
//                    prefixValueArray[patternPos] = prefixLength; //fill in value in prefixArray
//                }
//                return prefixValueArray;

            } finally {
                prefixWriter.close();
                prefixReader.close();
                patternReader.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static long kmpSearch(File textFile, File patternFile, File prefixFile) {
        return 0;
    }

//    private long prefixFileIndexPointer;
//    private long patternFileIndexPointer;
//    private long textFileIndexPointer;
//    private long patternPos;
//    private long prefixLength;
//    private long textPos;
//    private boolean patternHasNext;
//    private boolean textHasNext;
//    private File prefixFile;
//    private File patternFile;
//    private File textFile;
//    private FileWriter prefixFileWriter;
//    private BufferedWriter bufferedPrefixFileWriter;
//    private BufferedReader bufferedPrefixFileReader;
//    private FileReader patternFileReader;
//    private FileReader patternNextFileReader;
//    private FileReader prefixFileReader;
//    private FileReader textFileReader;
//    private FileReader textNextFileReader;
//
//    public File getPrefixFile() {
//        return prefixFile;
//    }
//
//    public long search(File patternFile, File textFile) {
//        try {
//            try {
//                this.patternFile = patternFile;
//                this.textFile = textFile;
//
//                patternFileReader = new FileReader(patternFile);
//                patternNextFileReader = new FileReader(patternFile);
//                patternNextFileReader.read();
//
//                prefixFile = new File(patternFile.getPath().substring(0, patternFile.getPath().lastIndexOf(File.separator) + 1) + "PrefixFile.txt");
//                prefixFileWriter = new FileWriter(prefixFile);
//                bufferedPrefixFileWriter = new BufferedWriter(prefixFileWriter);
//                prefixFileReader = new FileReader(prefixFile);
//                bufferedPrefixFileReader = new BufferedReader(prefixFileReader);
//
//                textFileReader = new FileReader(textFile);
//                textNextFileReader = new FileReader(textFile);
//                textNextFileReader.read();
//
//                writePrefixFile();
//                return kmpFileSearch();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                return -1;
//            } finally {
//                prefixFileWriter.close();
//                bufferedPrefixFileWriter.close();
//                patternFileReader.close();
//                prefixFileReader.close();
//                textFileReader.close();
//                patternNextFileReader.close();
//                textNextFileReader.close();
//            }
//        } catch (Exception ex) {
//            return -1;
//        }
//    }
//
//    private void writePrefixFile() throws IOException {
//        prefixFileIndexPointer = 0;
//        patternFileIndexPointer = 0;
//
//        //Set defaults
//        patternPos = 0;
//        prefixLength = -1;
//        patternHasNext = true;
//
//        //First value is always -1
//        setPrefixValue(prefixLength);
//
//        //iterate while the end of the pattern was not reached
//        while (patternHasNext) {
//            while (prefixLength >= 0 && !getPatternAtIndex(prefixLength).equals(getPatternAtIndex(patternPos))) {
//                prefixLength = getPrefixAtIndex(prefixLength);
//            }
//            patternPos++;
//            prefixLength++;
//            setPrefixValue(prefixLength);
//        }
//    }
//
//    private long getPrefixAtIndex(long pos) throws IOException {
//        if (pos <= prefixFileIndexPointer - 1) {
//            prefixFileReader.close();
//            prefixFileReader = new FileReader(prefixFile);
//            bufferedPrefixFileReader.close();
//            bufferedPrefixFileReader = new BufferedReader(prefixFileReader);
//            prefixFileIndexPointer = 0;
//        }
//
//        if (pos - prefixFileIndexPointer > 0)
//            bufferedPrefixFileReader.skip(pos - prefixFileIndexPointer);
//
//        //Prefixes are stored line by line
//        String readValue = bufferedPrefixFileReader.readLine();
//
//        prefixFileIndexPointer = pos + 1;
//        return Long.valueOf(readValue);
//    }
//
//    private String getPatternAtIndex(long pos) throws IOException {
//        if (pos <= patternFileIndexPointer - 1) {
//            patternFileReader.close();
//            patternFileReader = new FileReader(patternFile);
//            patternNextFileReader.close();
//            patternNextFileReader = new FileReader(patternFile);
//            patternNextFileReader.read();
//            patternFileIndexPointer = 0;
//            patternHasNext = true;
//        }
//
//        if (pos - patternFileIndexPointer > 0) {
//            patternFileReader.skip(pos - patternFileIndexPointer);
//            patternNextFileReader.skip(pos - patternFileIndexPointer);
//        }
//
//        char[] readChars = new char[1];
//        patternFileReader.read(readChars);
//
//        char[] readNextChars = new char[1];
//        patternHasNext = patternNextFileReader.read(readNextChars) != -1;
//        if (String.valueOf(readNextChars).equals("\u0000"))
//            patternHasNext = false;
//
//        patternFileIndexPointer = pos + 1;
//        return String.valueOf(readChars);
//    }
//
//    private void setPrefixValue(long value) throws IOException {
//        bufferedPrefixFileWriter.write(String.valueOf(value));
//        bufferedPrefixFileWriter.newLine();
//        bufferedPrefixFileWriter.flush();
//    }
//
//    private long kmpFileSearch() throws IOException {
//        patternFileReader.close();
//        patternFileReader = new FileReader(patternFile);
//        patternNextFileReader = new FileReader(patternFile);
//        patternNextFileReader.read();
//        patternFileIndexPointer = 0;
//        textFileIndexPointer = 0;
//        patternPos = 0;
//        textPos = 0;
//        textHasNext = true;
//        patternHasNext = true;
//
//        while (textHasNext) {
//            while (patternPos >= 0 && !getTextAtIndex(textPos).equals(getPatternAtIndex(patternPos))) {
//                patternPos = getPrefixAtIndex(patternPos);
//            }
//            textPos++;
//            patternPos++;
//
//            if (!patternHasNext)
//                return textPos + 1 - patternPos;
//        }
//        return -1; //No match found
//    }
//
//    private String getTextAtIndex(long pos) throws IOException {
//        if (pos <= textFileIndexPointer - 1) {
//            textFileReader.close();
//            textFileReader = new FileReader(textFile);
//            textNextFileReader.close();
//            textNextFileReader = new FileReader(textFile);
//            textNextFileReader.read();
//            textFileIndexPointer = 0;
//            textHasNext = true;
//        }
//
//        if (pos - textFileIndexPointer > 0) {
//            textFileReader.skip(pos - textFileIndexPointer + 1);
//            textNextFileReader.skip(pos - textFileIndexPointer + 1);
//        }
//
//        char[] readChars = new char[1];
//        textFileReader.read(readChars);
//
//        char[] readNextChars = new char[1];
//        textHasNext = textNextFileReader.read(readNextChars) != -1;
//        if (String.valueOf(readNextChars).equals("\u0000"))
//            textHasNext = false;
//
//        textFileIndexPointer = pos + 1;
//        return String.valueOf(readChars);
//    }
}
