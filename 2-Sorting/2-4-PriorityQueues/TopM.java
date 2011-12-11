/*************************************************************************
 *  Compilation:  javac TopM.java
 *  Execution:    java TopM M < input.txt
 *  Dependencies: MinPQ.java Transaction.java StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/24pq/tinyBatch.txt
 * 
 *  Given an integer M from the command line and an input stream where
 *  each line contains a String and a long value, this MinPQ client
 *  prints the M lines whose numbers are the highest.
 * 
 *  % java TopM 5 < tinyBatch.txt 
 *  Thompson    2/27/2000  4747.08
 *  vonNeumann  2/12/1994  4732.35
 *  vonNeumann  1/11/1999  4409.74
 *  Hoare       8/18/1992  4381.21
 *  vonNeumann  3/26/2002  4121.85
 *
 *************************************************************************/

public class TopM {   

    // Print the top M lines in the input stream. 
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]); 
        MinPQ<Transaction> pq = new MinPQ<Transaction>(M+1); 

        while (StdIn.hasNextLine()) {
            // Create an entry from the next line and put on the PQ. 
            String line = StdIn.readLine();
            Transaction transaction = new Transaction(line);
            pq.insert(transaction); 

            // remove minimum if M+1 entries on the PQ
            if (pq.size() > M) 
                pq.delMin();
        }   // top M entries are on the PQ

        // print entries on PQ in reverse order
        Stack<Transaction> stack = new Stack<Transaction>();
        for (Transaction transaction : pq)
            stack.push(transaction);
        for (Transaction transaction : stack)
            StdOut.println(transaction);
    } 
} 

