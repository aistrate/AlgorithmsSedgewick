/*************************************************************************
 *  Compilation:  javac BinaryStdIn.java
 *  Execution:    java BinaryStdIn < input > output
 *  
 *  Supports reading binary data from standard input.
 *
 *  % java BinaryStdIn < input.jpg > output.jpg
 *  % diff input.jpg output.jpg
 *
 *************************************************************************/

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 *  <i>Binary standard input</i>. This class provides methods for reading
 *  in bits from standard input, either one bit at a time (as a <tt>boolean</tt>),
 *  8 bits at a time (as a <tt>byte</tt> or <tt>char</tt>),
 *  16 bits at a time (as a <tt>short</tt>), 32 bits at a time
 *  (as an <tt>int</tt> or <tt>float</tt>), or 64 bits at a time (as a
 *  <tt>double</tt> or <tt>long</tt>).
 *  <p>
 *  All primitive types are assumed to be represented using their 
 *  standard Java representations, in big-endian (most significant
 *  byte first) order.
 *  <p>
 *  The client should not intermix calls to <tt>BinaryStdIn</tt> with calls
 *  to <tt>StdIn</tt> or <tt>System.in</tt>;
 *  otherwise unexpected behavior will result.
 */
public final class BinaryStdIn {
    private static BufferedInputStream in = new BufferedInputStream(System.in);
    private static final int EOF = -1;    // end of file

    private static int buffer;            // one character buffer
    private static int N;                 // number of bits left in buffer

    // static initializer
    static { fillBuffer(); }

    // singleton pattern - can't instantiate
    private BinaryStdIn() { }

    private static void fillBuffer() {
        try { buffer = in.read(); N = 8; }
        catch (IOException e) { System.out.println("EOF"); buffer = EOF; N = -1; }
    }

   /**
     * Close this input stream and release any associated system resources.
     */
    public static void close() {
        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not close BinaryStdIn");
        }
    }

   /**
     * Returns true if standard input is empty.
     * @return true if and only if standard input is empty
     */
    public static boolean isEmpty() {
        return buffer == EOF;
    }

   /**
     * Read the next bit of data from standard input and return as a boolean.
     * @return the next bit of data from standard input as a <tt>boolean</tt>
     * @throws RuntimeException if standard input is empty
     */
    public static boolean readBoolean() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        N--;
        boolean bit = ((buffer >> N) & 1) == 1;
        if (N == 0) fillBuffer();
        return bit;
    }

   /**
     * Read the next 8 bits from standard input and return as an 8-bit char.
     * Note that <tt>char</tt> is a 16-bit type;
     * to read the next 16 bits as a char, use <tt>readChar(16)</tt>
     * @return the next 8 bits of data from standard input as a <tt>char</tt>
     * @throws RuntimeException if there are fewer than 8 bits available on standard input
     */
    public static char readChar() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        // special case when aligned byte
        if (N == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        // combine last N bits of current buffer with first 8-N bits of new buffer
        int x = buffer;
        x <<= (8-N);
        int oldN = N;
        fillBuffer();
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        N = oldN;
        x |= (buffer >>> N);
        return (char) (x & 0xff);
        // the above code doesn't quite work for the last character if N = 8
        // because buffer will be -1
    }

   /**
     * Read the next r bits from standard input and return as an r-bit character.
     * @param r number of bits to read.
     * @return the next r bits of data from standard input as a <tt>char</tt>
     * @throws RuntimeException if there are fewer than r bits available on standard input
     * @throws RuntimeException unless 1 &le; r &le; 16
     */
    public static char readChar(int r) {
        if (r < 1 || r > 16) throw new RuntimeException("Illegal value of r = " + r);

        // optimize r = 8 case
        if (r == 8) return readChar();

        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

   /**
     * Read the remaining bytes of data from standard input and return as a string. 
     * @return the remaining bytes of data from standard input as a <tt>String</tt>
     * @throws RuntimeException if standard input is empty or if the number of bits
     * available on standard input is not a multiple of 8 (byte-aligned)
     */
    public static String readString() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }


   /**
     * Read the next 16 bits from standard input and return as a 16-bit short.
     * @return the next 16 bits of data from standard input as a <tt>short</tt>
     * @throws RuntimeException if there are fewer than 16 bits available on standard input
     */
    public static short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

   /**
     * Read the next 32 bits from standard input and return as a 32-bit int.
     * @return the next 32 bits of data from standard input as a <tt>int</tt>
     * @throws RuntimeException if there are fewer than 32 bits available on standard input
     */
    public static int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

   /**
     * Read the next r bits from standard input and return as an r-bit int.
     * @param r number of bits to read.
     * @return the next r bits of data from standard input as a <tt>int</tt>
     * @throws RuntimeException if there are fewer than r bits available on standard input
     * @throws RuntimeException unless 1 &le; r &le; 32
     */
    public static int readInt(int r) {
        if (r < 1 || r > 32) throw new RuntimeException("Illegal value of r = " + r);

        // optimize r = 32 case
        if (r == 32) return readInt();

        int x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) x |= 1;
        }
        return x;
    }

   /**
     * Read the next 64 bits from standard input and return as a 64-bit long.
     * @return the next 64 bits of data from standard input as a <tt>long</tt>
     * @throws RuntimeException if there are fewer than 64 bits available on standard input
     */
    public static long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }


   /**
     * Read the next 64 bits from standard input and return as a 64-bit double.
     * @return the next 64 bits of data from standard input as a <tt>double</tt>
     * @throws RuntimeException if there are fewer than 64 bits available on standard input
     */
    public static double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

   /**
     * Read the next 32 bits from standard input and return as a 32-bit float.
     * @return the next 32 bits of data from standard input as a <tt>float</tt>
     * @throws RuntimeException if there are fewer than 32 bits available on standard input
     */
    public static float readFloat() {
        return Float.intBitsToFloat(readInt());
    }


   /**
     * Read the next 8 bits from standard input and return as an 8-bit byte.
     * @return the next 8 bits of data from standard input as a <tt>byte</tt>
     * @throws RuntimeException if there are fewer than 8 bits available on standard input
     */
    public static byte readByte() {
        char c = readChar();
        byte x = (byte) (c & 0xff);
        return x;
    }
    
   /**
     * Test client. Reads in a binary input file from standard input and writes
     * it to standard output.
     */
    public static void main(String[] args) {

        // read one 8-bit char at a time
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(c);
        }
        BinaryStdOut.flush();
    }
}
