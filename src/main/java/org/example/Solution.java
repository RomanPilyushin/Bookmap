package org.example;


public class Solution {
    private String S;
    private int n;
    // An array to store prefix sums of the number of 'A's in the string
    private int[] prefixA;
    // An array to store prefix sums of the number of 'B's in the string
    private int[] prefixB;

    public Solution(String S) {
        this.S = S;
        this.n = S.length();
        prefixA = new int[n + 1];
        prefixB = new int[n + 1];
        preprocess();
    }

    private void preprocess() {
        for (int i = 1; i <= n; i++) {
            char c = S.charAt(i - 1);
            prefixA[i] = prefixA[i - 1];
            prefixB[i] = prefixB[i - 1];

            if (c == 'A') {
                prefixA[i]++;
            } else if (c == 'B') {
                prefixB[i]++;
            }
        }
    }

    public int processQuery(int l, int r, int k) {
        int len = r - l + 1;
        if (k > len) return -1;
        // Calculates the position p of the k-th character within the range [l, r].
        int p = l + k - 1;
        // The character at position p in the string.
        char c_k = S.charAt(p - 1);
        // The opposite character of c_k
        char c_opposite = (c_k == 'A') ? 'B' : 'A';

        // The number of occurrences of c_k ('A' or 'B') in the substring from l to p.
        int x;
        if (c_k == 'A') {
            // If c_k is 'A', compute the count of 'A's from l to p using prefix sums.
            x = prefixA[p] - prefixA[l - 1];
        } else {
            // The same for 'B'
            x = prefixB[p] - prefixB[l - 1];
        }

        // Calculates the total occurrences of the opposite character (c_opposite) in the range [l, r].
        int totalOccurrencesOpposite;
        if (c_opposite == 'A') {
            totalOccurrencesOpposite = prefixA[r] - prefixA[l - 1];
        } else {
            totalOccurrencesOpposite = prefixB[r] - prefixB[l - 1];
        }

        // If the number of occurrences of c_k is greater than the total occurrences
        // of the opposite character, return -1 (no valid solution).
        if (x > totalOccurrencesOpposite) return -1;

        // The left boundary of the range
        int left = l;
        // The right boundary of the range
        int right = r;
        // Stores the position where the solution is found, initialized to -1
        int pos = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            // Calculates how many times the opposite character appears in the substring from l to mid.
            int occurrences;
            if (c_opposite == 'A') {
                occurrences = prefixA[mid] - prefixA[l - 1];
            } else {
                occurrences = prefixB[mid] - prefixB[l - 1];
            }
            // If the number of occurrences of the opposite character is greater than or equal to x,
            // update pos to mid and narrow the search by adjusting right.
            // Otherwise, adjust left to narrow the search.
            if (occurrences >= x) {
                pos = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // If no valid position was found (pos == -1), return -1.
        // Otherwise, return the position pos relative to l.
        if (pos == -1) {
            return -1;
        } else {
            return pos - l + 1;
        }
    }
}
