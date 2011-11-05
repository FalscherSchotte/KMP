package de.kmp;

/**
 * User: FloLap
 * Date: 01.11.11
 * Time: 16:13
 */
public interface ISearchString {
    /**
     * Search the specified pattern in the specified file
     *
     * @param text    The target text in which the pattern should be found
     * @param pattern The pattern to find
     * @return Position of the pattern (-1 when not found)
     */
    int search(String pattern, String text);
}
