/*************************************************************************
 *  Compilation:  javac BinaryStdOutTester.java
 *  Execution:    java BinaryStdOutTester
 *
 *************************************************************************/

public class BinaryStdOutTester {


    public static void main(String[] args) {
        boolean x1 = true;
        char    x2 = 'K';
        int     x3 = 17;
        long    x4 = 12345678901L;
        double  x5 = Math.PI;
        float   x6 = 1.125F;
        BinaryStdOut.write(x1);
        BinaryStdOut.write(x2);
        BinaryStdOut.write(x3);
        BinaryStdOut.write(x4);
        BinaryStdOut.write(x5);
        BinaryStdOut.write(x6);
        BinaryStdOut.flush();
    }
}
