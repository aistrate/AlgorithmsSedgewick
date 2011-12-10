/*************************************************************************
 *  Compilation:  javac BinaryStdInTester.java
 *  Execution:    java BinaryStdInTester
 *
 *  % java BinaryStdInTester
 *  true
 *  K
 *  17
 *  12345678901
 *  3.141592653589793
 *  1.125
 *
 *************************************************************************/

public class BinaryStdInTester {


    public static void main(String[] args) {
        boolean x1 = BinaryStdIn.readBoolean();
        char    x2 = BinaryStdIn.readChar();
        int     x3 = BinaryStdIn.readInt();
        long    x4 = BinaryStdIn.readLong();
        double  x5 = BinaryStdIn.readDouble();
        float   x6 = BinaryStdIn.readFloat();
        System.out.println(x1);
        System.out.println(x2);
        System.out.println(x3);
        System.out.println(x4);
        System.out.println(x5);
        System.out.println(x6);
    }
}
