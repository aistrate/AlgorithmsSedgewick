/*************************************************************************
 *  Compilation:  javac StaticSetOfInts.java
 *  
 *  Data type to store a set of integers.
 *
 *************************************************************************/

import java.util.Arrays;

public class StaticSETofInts {
    private int[] a;
    public StaticSETofInts(int[] keys) {
        // defensive copy
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            a[i] = keys[i];

        Arrays.sort(a);

        // probably should check that no duplicates
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

    // Binary search.
    public int rank(int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
}
