package de.kmp;

/**
 * User: FloLap
 * Date: 01.11.11
 * Time: 11:53
 * Ringbuffer to store index value pairs
 */
public class RingBuffer {
    private int size = 20;
    private long[] indices = new long[size];
    private String[] values = new String[size];
    private int start = 0;

    /**
     * Creates a new Ringbuffer instance
     */
    public RingBuffer() {
    }

    /**
     * Add the pair to the buffer
     *
     * @param index Index to save
     * @param value Value to save
     */
    public void add(long index, String value) {
        int arrayIndex = start % size;
        indices[arrayIndex] = index;
        values[arrayIndex] = value;
        start = start % size + 1;
    }

    /**
     * Returns the value of the specified index if contained (else null)
     *
     * @param index Index of the value to return
     * @return Returns the value if contained or null
     */
    public String get(long index) {
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] == index)
                return values[i];
        }
        return null;
    }
}
