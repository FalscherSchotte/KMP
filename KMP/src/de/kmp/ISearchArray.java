package de.kmp;

/**
 * User: FloLap
 * Date: 06.10.11
 * Time: 14:40
 */
public interface ISearchArray {
    //TODO: was ist wenn null übergeben wird?
    //TODO: Interface beschreiben

    /**
     * Search the specified pattern in the specified file
     *
     * @param text    The target text in which the pattern should be found
     * @param pattern The pattern to find
     * @return Position of the pattern (-1 when not found)
     */
    int search(String[] text, String[] pattern);
}
