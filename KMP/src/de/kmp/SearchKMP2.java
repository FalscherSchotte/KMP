package de.kmp;

/**
 * User: FloLap
 * Date: 07.10.11
 * Time: 21:38
 */
public class SearchKMP2 implements ISearch {

    public int search(String[] data, String[] pattern) {
        return searchStringWithKnuthMorrisPratt(pattern, data);
    }

    public static int searchStringWithKnuthMorrisPratt(String[] s, String[] t) {
        int m = s.length;
        int n = t.length;
        int i = 0, j = 0, k = 0;
        int[] B = new int[m + 1];

        B[0] = -1;
        B[1] = 0;
        for (int l = 2; l <= m; l++) {
            while ((k >= 0) && !(s[k].equals(s[l - 1])))
                k = B[k];
            B[l] = ++k;
        }

        while (i <= (n - m)) {
            while ((j < m) && (s[j].equals(t[i + j])))
                j++;
            if (j == m)
                return (i);
            i = i + j - B[j];
            j = Math.max(0, B[j]);
        }
        return (-1);
    }
}
