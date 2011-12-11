/*************************************************************************
 *  Compilation:  javac HexDump.java
 *  Execution:    java HexDump < file
 *  Dependencies: BinaryStdIn.java
 *  Data file:    http://introcs.cs.princeton.edu/stdlib/abra.txt
 *  
 *  Reads in a binary file and writes out the bytes in hex, 16 per line.
 *
 *  % more abra.txt
 *  ABRACADABRA!
 *
 *  % java HexDump 16 < abra.txt
 *  41 42 52 41 43 41 44 41 42 52 41 21
 *  96 bits
 *
 *  % hexdump < abra.txt
 *
 *  % od -t x1 < abra.txt 
 *  0000000 41 42 52 41 43 41 44 41 42 52 41 21
 *  0000014
 *
 *************************************************************************/

public class HexDump {

    public static void main(String[] args) {
        int BYTES_PER_LINE = 16;
        if (args.length == 1) {
            BYTES_PER_LINE = Integer.parseInt(args[0]);
        }

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (BYTES_PER_LINE == 0) { BinaryStdIn.readChar(); continue; }
            if (i == 0) StdOut.printf("");
            else if (i % BYTES_PER_LINE == 0) StdOut.printf("\n", i);
            else StdOut.print(" ");
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (BYTES_PER_LINE != 0) StdOut.println();
        StdOut.println((i*8) + " bits");
    }
}
