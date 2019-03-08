/* *****************************************************************************
 *  Name: Trung Dao
 *  Date: 03/08
 *  Description: Permutation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int count = 0;
        RandomizedQueue<String> test = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            count++;
            String str = StdIn.readString();
            if (count > k && StdRandom.uniform(count) < k) {
                test.dequeue();
            }
            else if (count > k) {
                continue;
            }
            test.enqueue(str);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(test.dequeue());
        }
    }
}
