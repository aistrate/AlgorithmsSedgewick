/*************************************************************************
 *  Compilation:  javac Stats.java
 *  Execution:    java Stats < input.txt
 *  Dependencies: Bag.java StdIn.java StdOut.java
 *
 *  Reads in a sequence of real numbers from standard input and 
 *  computes their mean and standard deviation.
 * 
 *  % java Stats
 *  100 99 101 120 98 107 109 81 101 90
 *  Mean:    100.60
 *  Std dev: 10.51
 *  
 *************************************************************************/

public class Stats {
    public static void main(String[] args) {

        // read in numbers
        Bag<Double> numbers = new Bag<Double>();
        while (!StdIn.isEmpty()) {
            numbers.add(StdIn.readDouble());
        }
        int N = numbers.size();

        // compute mean
        double sum = 0.0;
        for (double x : numbers)
            sum += x;
        double mean = sum/N;

        // compute standard deviation
        sum = 0.0;
        for (double x : numbers) {
            sum += (x - mean) * (x - mean);
        }
        double std = Math.sqrt(sum/(N-1));

        StdOut.printf("Mean:    %.2f\n", mean);
        StdOut.printf("Std dev: %.2f\n", std);
    }
}
