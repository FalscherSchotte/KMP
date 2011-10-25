package de.kmp;

import java.io.*;

/**
 * User: FloLap
 * Date: 25.10.11
 * Time: 20:30
 */
public class SearchInfiniteKMP {

    private String[] cache;

    public SearchInfiniteKMP() {
    }

    public long search(File patternFile, File textFile) {
        try {
            if (!writePrefixFile(patternFile))
                return -1;


            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private FileWriter prefixFileWriter;
    private BufferedWriter bufferedPrefixFileWriter;

    private boolean writePrefixFile(File patternFile) throws IOException {
        try {
            prefixFileWriter = new FileWriter(patternFile.getPath().substring(0, patternFile.getPath().lastIndexOf(File.separator) + 1) + "PrefixFile.txt");
            bufferedPrefixFileWriter = new BufferedWriter(prefixFileWriter);
            patternPos = 0;
            prefixLength = -1;

            bufferedPrefixFileWriter.write(String.valueOf(prefixLength));
            bufferedPrefixFileWriter.newLine();

            String line;

//            while (patternPos < pattern.length) { //Repeat until pattern end
//            while (prefixLength >= 0 && !pattern[prefixLength].equals(pattern[patternPos])) {
//                //if current prefix can not be extended, search for a shorter one
//                prefixLength = prefixValueArray[prefixLength];
//            }
//            //here : j=-1 or pattern[patternPos] = pattern[prefixLength]
//
//            patternPos++;
//            prefixLength++;
//            prefixValueArray[patternPos] = prefixLength; //fill in value in prefixArray
//        }

            while ((line = getNextPrefix(patternFile)) != null) {
                bufferedPrefixFileWriter.write(line);
                bufferedPrefixFileWriter.newLine();
                if (patternPos % 1000 == 0)
                    bufferedPrefixFileWriter.flush();
            }
            bufferedPrefixFileWriter.flush();
            return true;
        } finally {
            prefixFileWriter.close();
            bufferedPrefixFileWriter.close();
            return false;
        }
    }

    private long patternPos;
    private long prefixLength;
    private long patternLength;

    private FileReader fileReader;

    private int ctr = 0;

    private String getNextPrefix(File patternFile) {


        return null;
    }

//    private int[] analyzePrefix(String[] pattern) { //v with length n
//        while (patternPos < pattern.length) { //Repeat until pattern end
//            while (prefixLength >= 0 && !pattern[prefixLength].equals(pattern[patternPos])) {
//                //if current prefix can not be extended, search for a shorter one
//                prefixLength = prefixValueArray[prefixLength];
//            }
//            //here : j=-1 or pattern[patternPos] = pattern[prefixLength]
//
//            patternPos++;
//            prefixLength++;
//            prefixValueArray[patternPos] = prefixLength; //fill in value in prefixArray
//        }
//        return prefixValueArray;
//    }

}
