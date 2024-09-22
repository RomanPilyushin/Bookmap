package org.example;

public class SolutionImproved {
    private String S;
    private int n;
    // Arrays to store the positions of 'A's and 'B's
    private int[] posA;
    private int[] posB;
    private int countA;
    private int countB;

    public SolutionImproved(String S) {
        this.S = S;
        this.n = S.length();
        // First pass to count the number of 'A's and 'B's
        countA = 0;
        countB = 0;
        for (int i = 0; i < n; i++) {
            char c = S.charAt(i);
            if (c == 'A') countA++;
            else if (c == 'B') countB++;
        }
        // Initialize arrays with exact sizes
        posA = new int[countA];
        posB = new int[countB];
        int indexA = 0, indexB = 0;
        for (int i = 0; i < n; i++) {
            char c = S.charAt(i);
            if (c == 'A') posA[indexA++] = i + 1; // 1-based indexing
            else if (c == 'B') posB[indexB++] = i + 1; // 1-based indexing
        }
    }

    /**
     * Custom lower bound: first index where arr[idx] >= target
     */
    private int lowerBound(int[] arr, int size, int target) {
        int left = 0, right = size;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    /**
     * Custom upper bound: first index where arr[idx] > target
     */
    private int upperBound(int[] arr, int size, int target) {
        int left = 0, right = size;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    public int processQuery(int l, int r, int k) {
        int len = r - l + 1;
        if (k > len) return -1;

        int p = l + k - 1;
        if (p > r) return -1;

        char c_k = S.charAt(p - 1);
        char c_opposite = (c_k == 'A') ? 'B' : 'A';

        // Count the number of c_k in [l, p]
        int x;
        if (c_k == 'A') {
            // Binary search in posA
            x = upperBound(posA, countA, p) - lowerBound(posA, countA, l);
        } else { // c_k == 'B'
            x = upperBound(posB, countB, p) - lowerBound(posB, countB, l);
        }

        // Count the total occurrences of c_opposite in [l, r]
        int totalOccurrencesOpposite;
        if (c_opposite == 'A') {
            totalOccurrencesOpposite = upperBound(posA, countA, r) - lowerBound(posA, countA, l);
        } else { // c_opposite == 'B'
            totalOccurrencesOpposite = upperBound(posB, countB, r) - lowerBound(posB, countB, l);
        }

        if (x > totalOccurrencesOpposite) return -1;

        // Find the x-th occurrence of c_opposite in [l, r]
        int desiredIdx;
        if (c_opposite == 'A') {
            desiredIdx = lowerBound(posA, countA, l) + x - 1;
            if (desiredIdx >= countA || posA[desiredIdx] > r) return -1;
            return posA[desiredIdx] - l + 1;
        } else { // c_opposite == 'B'
            desiredIdx = lowerBound(posB, countB, l) + x - 1;
            if (desiredIdx >= countB || posB[desiredIdx] > r) return -1;
            return posB[desiredIdx] - l + 1;
        }
    }
}