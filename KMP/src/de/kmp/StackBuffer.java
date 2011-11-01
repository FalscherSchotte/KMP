package de.kmp;

/**
 * User: FloLap
 * Date: 01.11.11
 * Time: 11:53
 */
public class StackBuffer {
    private int size = 20;
    private long[] indices = new long[size];
    private String[] values = new String[size];
    private int start = 0;

    public StackBuffer() {
    }

    public void add(long index, String value) {
        int arrayIndex = start % size;
        indices[arrayIndex] = index;
        values[arrayIndex] = value;
        start = start % size + 1;
    }

    public String get(long index) {
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] == index)
                return values[i];
        }
        return null;
    }
}
