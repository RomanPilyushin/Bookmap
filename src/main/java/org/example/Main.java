package org.example;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialize file readers and writers
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

        // Read input data
        String[] firstLine = br.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int q = Integer.parseInt(firstLine[1]);

        String S = br.readLine().trim();

        // Preprocess the string
        //Solution solution = new Solution(S);
        SolutionImproved solution = new SolutionImproved(S);

        // Process queries
        for (int i = 0; i < q; i++) {
            String[] queryParts = br.readLine().split(" ");
            int l = Integer.parseInt(queryParts[0]);
            int r = Integer.parseInt(queryParts[1]);
            int k = Integer.parseInt(queryParts[2]);

            int result = solution.processQuery(l, r, k);
            bw.write(String.valueOf(result));
            System.out.println("Case #" + (i + 1) + ": " + result);
            bw.newLine();
        }

        // Close readers and writers
        br.close();
        bw.close();
    }
}
