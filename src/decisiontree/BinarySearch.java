package decisiontree;// Created by mrtyormaa on 10/17/15.


import java.util.Arrays;

public class BinarySearch {

    /**
     * This class should not be instantiated.
     */
    private BinarySearch() {
    }

    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static int rank(int key, int[] a) {
        return indexOf(a, key);
    }
}
