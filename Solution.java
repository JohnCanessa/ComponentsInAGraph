import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * Disjoint Union Sets class
 */
class DisjointUnionSets {

    // **** class members ****
    int n;
    int[] parents;

    /**
     * Constructor.
     */
    public DisjointUnionSets(int n) {
        this.n = n;
        this.parents = new int[n];
    }

    /**
     * Get the counts of the sets with min and max number of elements.
     */
    int[] minAndMax() {

        // **** declare and populate array of Integers with the values of parent ****
        Integer[] arr = new Integer[this.parents.length];
        int i = 0;
        for (int v : this.parents)
            arr[i++] = v;

        // **** ****
        List<Integer> al = Arrays.asList(arr);

        // **** ****
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // **** ****
        for (int item : arr) {
            if (item != 0) {

                // **** get the frequency of item in the list ****
                int freq = Collections.frequency(al, item);

                // **** update the min and max values ****
                min = Math.min(min, freq);
                max = Math.max(max, freq);
            }
        }

        // **** package results ****
        int[] result = { min, max };

        // **** return results ****
        return result;
    }

    /**
     * Unite the set that includes x and y.
    */
    void union(int x, int y) {

        // **** to avoid use of makeSet method (linear O(n)) ****
        if (parents[x] == 0)
            parents[x] = x;

        if (parents[y] == 0)
            parents[y] = y;

        // **** disjoin set idea, keep updating the representative element of each set ****
        if(parents[x] != 0 || parents[y] != 0) {

            // **** ****
            int low = Math.min(parents[x], parents[y]);
            int high = Math.max(parents[x], parents[y]);

            // **** ****
            for (int i = 0; i < parents.length; i++) {
                if(parents[i] == high)
                    parents[i] = low;
            }
        }
    } 

    /**
     * Return a string representation of the object.
     */
    public String toString() {

        // **** ****
        StringBuilder sb = new StringBuilder();

        sb.append("\n      n: " + this.n);
        sb.append("\nparents: " + Arrays.toString(this.parents));

        // **** ****
        return sb.toString();
    }
}

/**
 * 
 */
public class Solution {

    /*
     * Complete the componentsInGraph function below.
     */
    static int[] componentsInGraph(int[][] gb) {

        // **** ****
        final int MAX_ELEMENTS = ((15000 + 1) * 2);

        // **** instantiate the class ****
        DisjointUnionSets dus = new DisjointUnionSets(MAX_ELEMENTS);

        // **** populate unions ****
        for (int i = 0; i < gb.length; i++) {
            dus.union(gb[i][0], gb[i][1]);
        }

        // **** compute the min and max values ****
        int[] results = dus.minAndMax();

        // **** return results ****
        return results;
    }


    // **** open scanner ****
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * Test scaffolding.
     */
    public static void main(String[] args) throws IOException {

        // **** open buffered writer ****
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        // **** get the number of relations ****
        int n = Integer.parseInt(scanner.nextLine().trim());

        // **** relations ****
        int[][] gb = new int[n][2];

        // **** read the relations and load the gb array ****
        for (int gbRowItr = 0; gbRowItr < n; gbRowItr++) {

            // **** read and split the values for this relation ****
            String[] gbRowItems = scanner.nextLine().split(" ");

            // **** set the values in the gb array ****
            for (int gbColumnItr = 0; gbColumnItr < 2; gbColumnItr++) {

                // **** ****
                int gbItem = Integer.parseInt(gbRowItems[gbColumnItr].trim());
                gb[gbRowItr][gbColumnItr] = gbItem;
            }
        }

        // **** compute result ****
        int[] result = componentsInGraph(gb);

        // **** display result ****
        String s = "" + result[0] + " " + result[1];
        bufferedWriter.write(s, 0, s.length());

        // **** write new line ****
        bufferedWriter.newLine();

        // **** close buffered writter ****
        bufferedWriter.close();
    }
}