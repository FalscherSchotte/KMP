package de.kmp;

import java.io.File;

/**
 * User: FloLap
 * Date: 01.11.11
 * Time: 15:14
 */
public interface ISearchFile {
    /**
     * Search the specified pattern in the specified file
     *
     * @param text    The target text in which the pattern should be found
     * @param pattern The pattern to find
     * @return Position of the pattern (-1 when not found)
     */
    long search(File text, File pattern);
}
