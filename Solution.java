import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;


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
     * Get the counts of the sets with min and max number of elements: O(n^2)
     */
    int[] minAndMax() {

        // **** declare and populate array of Integers with the values of the parents O(n) ****
        Integer[] arr = new Integer[this.parents.length];
        int i = 0;
        for (int val : this.parents)
            arr[i++] = val;

        // **** create a list with the values in the parents ****
        List<Integer> al = Arrays.asList(arr);

        // **** initialize the min and max values ****
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // **** traverse the array arr: O(n^2) ****
        for (int item : arr) {
            if (item != 0) {

                // **** get the frequency of item in the list: O(n) ****
                int freq = Collections.frequency(al, item);

                // ???? ????
                System.out.println("minAndMax <<< item: " + item + " freq: " + freq);

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
     * Alternate approach to compute the min and max: O(n)
     */
    int[] maxAndMin() {

        // **** ****
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();

        // **** traverse parrents array inserting into hashmap: O(n) ****
        for (int val : this.parents) {
            hm.put(val, hm.getOrDefault(val, 0) + 1);
        }

        // ???? ????
        // System.out.println("maxAndMin <<< hm: " + hm.toString());

        // **** initialize the min and max values ****
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // **** traverse hashmap looking for min and max: O(n) ****
        Iterator<Entry<Integer, Integer>> it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, Integer> e = it.next();

            // **** skip this key ****
            if (e.getKey() == 0)
                continue;

            // **** update min and max ****
            min = Math.min(e.getValue(), min);
            max = Math.max(e.getValue(), max);
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

        // **** set the modes in the parents array ****
        if (parents[x] == 0)
            parents[x] = x;

        if (parents[y] == 0)
            parents[y] = y;

        // **** update the representative element of each set ****
        if (parents[x] != 0 || parents[y] != 0) {

            // **** get the low and high from these nodes ****
            int high = Math.max(parents[x], parents[y]);
            int low = Math.min(parents[x], parents[y]);

            // ???? ????
            System.out.println("union <<< high: " + high + " low: " + low);

            // **** update to point to the proper representative ****
            for (int i = 0; i < parents.length; i++) {
                if (parents[i] == high)
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

    // **** number of total nodes in the graph (can go up to (15000 + 1) * 2) ****
    static int totalNodes = 0;


    /**
     * Complete the componentsInGraph function below.
     */
    static int[] componentsInGraph(int[][] gb) {

        // **** maximum number of nodes ****
        // final int MAX_ELEMENTS = ((15000 + 1) * 2);

        // **** instantiate the class ****
        // DisjointUnionSets dus = new DisjointUnionSets(MAX_ELEMENTS);
        DisjointUnionSets dus = new DisjointUnionSets(totalNodes);

        // **** populate unions ****
        for (int i = 0; i < gb.length; i++) {

            // **** update union ****
            dus.union(gb[i][0], gb[i][1]);

            // ???? ????
            System.out.println("componentsInGraph <<< " + gb[i][0] + " <-> " + gb[i][1]);
            System.out.println("componentsInGraph <<< dus: " + dus.toString() + "\n");
        }

        // **** compute the min and max values [1] ****
        int[] results = dus.minAndMax();

        // ???? ????
        System.out.println("componentsInGraph <<< minAndMax: " + results[0] + " " + results[1]);

        // **** compute the min and max values [2] ****
        results = dus.maxAndMin();

        // ???? ????
        System.out.println("componentsInGraph <<< maxAndMin: " + results[0] + " " + results[1]);

        // **** return results ****
        return results;
    }


    // **** open scanner ****
    private static final Scanner sc = new Scanner(System.in);


    /**
     * Test scaffolding.
     */
    public static void main(String[] args) throws IOException {

        // **** open buffered writer ****
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        // **** get the number of relations ****
        int n = Integer.parseInt(sc.nextLine().trim());

        // **** relations ****
        int[][] gb = new int[n][2];

        // **** read the relations and load the gb array ****
        for (int gbRowItr = 0; gbRowItr < n; gbRowItr++) {

            // **** read and split the values for this relation ****
            String[] gbRowItems = sc.nextLine().split(" ");

            // **** set the values in the gb array ****
            for (int gbColumnItr = 0; gbColumnItr < 2; gbColumnItr++) {

                // **** ****
                int gbItem = Integer.parseInt(gbRowItems[gbColumnItr].trim());
                gb[gbRowItr][gbColumnItr] = gbItem;

                // **** update the number of nodes ****
                totalNodes = Math.max(totalNodes, gbItem);
            }
        }

        // **** finalize the total number of nodes ****
        totalNodes = (totalNodes + 1) * 2;

        // ???? ????
        System.out.println("main <<< totalNodes: " + totalNodes);

        // **** close scanner ****
        sc.close();

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