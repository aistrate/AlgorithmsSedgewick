/*************************************************************************
 *  Compilation:  javac BinaryIn.java
 *  Execution:    java BinaryIn input output
 *  
 *  This library is for reading binary data from an input stream.
 *
 *  % java BinaryIn http://introcs.cs.princeton.edu/cover.jpg output.jpg
 *
 *************************************************************************/

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;


/**
 *  <i>Binary input</i>. This class provides methods for reading
 *  in bits from a binary input stream, either
 *  one bit at a time (as a <tt>boolean</tt>),
 *  8 bits at a time (as a <tt>byte</tt> or <tt>char</tt>),
 *  16 bits at a time (as a <tt>short</tt>),
 *  32 bits at a time (as an <tt>int</tt> or <tt>float</tt>), or
 *  64 bits at a time (as a <tt>double</tt> or <tt>long</tt>).
 *  <p>
 *  The binary input stream can be from standard input, a filename,
 *  a URL name, a Socket, or an InputStream.
 *  <p>
 *  All primitive types are assumed to be represented using their 
 *  standard Java representations, in big-endian (most significant
 *  byte first) order.
 *  <p>
 *  The client should not intermix calls to <tt>BinaryIn</tt> with calls
 *  to <tt>In</tt>; otherwise unexpected behavior will result.
 */
public final class BinaryIn {
    private static final int EOF = -1;   // end of file

    private BufferedInputStream in;      // the input stream
    private int buffer;                  // one character buffer
    private int N;                       // number of bits left in buffer

   /**
     * Create a binary input stream from standard input.
     */
    public BinaryIn() {
        in = new BufferedInputStream(System.in);
        fillBuffer();
    }

   /**
     * Create a binary input stream from an InputStream.
     */
    public BinaryIn(InputStream is) {
        in = new BufferedInputStream(is);
        fillBuffer();
    }

   /**
     * Create a binary input stream from a socket.
     */
    public BinaryIn(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + socket);
        }
    }

   /**
     * Create a binary input stream from a URL.
     */
    public BinaryIn(URL url) {
        try {
            URLConnection site = url.openConnection();
            InputStream is     = site.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + url);
        }
    }

   /**
     * Create a binary input stream from a filename or URL name.
     */
    public BinaryIn(String s) {

        try {
            // first try to read file from local file system
            File file = new File(s);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                in = new BufferedInputStream(fis);
                fillBuffer();
                return;
            }

            // next try for files included in jar
            URL url = getClass().getResource(s);

            // or URL from web
            if (url == null) { url = new URL(s); }

            URLConnection site = url.openConnection();
            InputStream is     = site.getInputStream();
            in = new BufferedInputStream(is);
            fillBuffer();
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + s);
        }
    }

    private void fillBuffer() {
        try { buffer = in.read(); N = 8; }
        catch (IOException e) { System.err.println("EOF"); buffer = EOF; N = -1; }
    }

   /**
     * Does the binary input stream exist?
     */
    public boolean exists()  {
        return in != null;
    }

   /**
     * Returns true if the binary input stream is empty.
     * @return true if and only if the binary input stream is empty
     */
    public boolean isEmpty() {
        return buffer == EOF;
    }

   /**
     * Read the next bit of data from the binary input stream and return as a boolean.
     * @return the next bit of data from the binary input stream as a <tt>boolean</tt>
     * @throws RuntimeException if the input stream is empty
     */
    public boolean readBoolean() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        N--;
        boolean bit = ((buffer >> N) & 1) == 1;
        if (N == 0) fillBuffer();
        return bit;
    }

   /**
     * Read the next 8 bits from the binary input stream and return as an 8-bit char.
     * @return the next 8 bits of data from the binary input stream as a <tt>char</tt>
     * @throws RuntimeException if there are fewer than 8 bits available
     */
    public char readChar() {
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
     * Read the next r bits from the binary input stream and return as an r-bit character.
     * @param r number of bits to read.
     * @return the next r bits of data from the binary input streamt as a <tt>char</tt>
     * @throws RuntimeException if there are fewer than r bits available
     */
    public char readChar(int r) {
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
     * Read the remaining bytes of data from the binary input stream and return as a string. 
     * @return the remaining bytes of data from the binary input stream as a <tt>String</tt>
     * @throws RuntimeException if the input stream is empty or if the number of bits
     * available is not a multiple of 8 (byte-aligned)
     */
    public String readString() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }


   /**
     * Read the next 16 bits from the binary input stream and return as a 16-bit short.
     * @return the next 16 bits of data from the binary standard input as a <tt>short</tt>
     * @throws RuntimeException if there are fewer than 16 bits available
     */
    public short readShort() {
        short x = 0;
        for (int i = 0; i < 2; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

   /**
     * Read the next 32 bits from the binary input stream and return as a 32-bit int.
     * @return the next 32 bits of data from the binary input stream as a <tt>int</tt>
     * @throws RuntimeException if there are fewer than 32 bits available
     */
    public int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

   /**
     * Read the next r bits from the binary input stream return as an r-bit int.
     * @param r number of bits to read.
     * @return the next r bits of data from the binary input stream as a <tt>int</tt>
     * @throws RuntimeException if there are fewer than r bits available on standard input
     */
    public int readInt(int r) {
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
     * Read the next 64 bits from the binary input stream and return as a 64-bit long.
     * @return the next 64 bits of data from the binary input stream as a <tt>long</tt>
     * @throws RuntimeException if there are fewer than 64 bits available
     */
    public long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }

   /**
     * Read the next 64 bits from the binary input stream and return as a 64-bit double.
     * @return the next 64 bits of data from the binary input stream as a <tt>double</tt>
     * @throws RuntimeException if there are fewer than 64 bits available
     */
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

   /**
     * Read the next 32 bits from standard input and return as a 32-bit float.
     * @return the next 32 bits of data from standard input as a <tt>float</tt>
     * @throws RuntimeException if there are fewer than 32 bits available on standard input
     */
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }


   /**
     * Read the next 8 bits from the binary input stream and return as an 8-bit byte.
     * @return the next 8 bits of data from the binary input stream as a <tt>byte</tt>
     * @throws RuntimeException if there are fewer than 8 bits available
     */
    public byte readByte() {
        char c = readChar();
        byte x = (byte) (c & 0xff);
        return x;
    }
    
   /**
     * Test client. Reads in the name of a file or url (first command-line
     * argument) and writes it to a file (second command-line argument).
     */
    public static void main(String[] args) {
        BinaryIn  in  = new BinaryIn(args[0]);
        BinaryOut out = new BinaryOut(args[1]);

        // read one 8-bit char at a time
        while (!in.isEmpty()) {
            char c = in.readChar();
            out.write(c);
        }
        out.flush();
    }
}
