/*************************************************************************
 *  Compilation:  javac Accumulator.java
 *
 *  Mutable data type that calculates mean of data values.
 *
 *************************************************************************/


public class Accumulator {
    private double total;
    private int N;

    public void addDataValue(double val) {
        N++;
        total += val;
    }

    public double mean() {
        return total / N;
    }

    public String toString() {
        return "Mean (" + N + " values): " + String.format("%7.5f", mean());
    }
}
