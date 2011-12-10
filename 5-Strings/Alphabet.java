public class Alphabet {
    public static final Alphabet BINARY         = new Alphabet("01");
    public static final Alphabet OCTAL          = new Alphabet("01234567");
    public static final Alphabet DECIMAL        = new Alphabet("0123456789");
    public static final Alphabet HEXADECIMAL    = new Alphabet("0123456789ABCDEF");
    public static final Alphabet DNA            = new Alphabet("ACTG");
    public static final Alphabet LOWERCASE      = new Alphabet("abcdefghijklmnopqrstuvwxyz");
    public static final Alphabet UPPERCASE      = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final Alphabet PROTEIN        = new Alphabet("ACDEFGHIKLMNPQRSTVWY");
    public static final Alphabet BASE64         = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    public static final Alphabet ASCII          = new Alphabet(128);
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
    public static final Alphabet UNICODE16      = new Alphabet(65536);

    private char[] alphabet;     // the characters in the alphabet
    private int[] inverse;       // indices
    private int R;               // the radix of the alphabet

    // Create a new Alphabet from chars in string.
    public Alphabet(String alpha) {

        // check that alphabet contains no duplicate chars
        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < alpha.length(); i++) {
            char c = alpha.charAt(i);
            if (unicode[c]) throw new RuntimeException("Illegal alphabet: character = '" + c + "'");
            else unicode[c] = true;
        }


        alphabet = alpha.toCharArray();
        R = alpha.length();
        inverse = new int[Character.MAX_VALUE];
        for (int i = 0; i < inverse.length; i++)
            inverse[i] = -1;

        // can't use char since R can be as big as 65,536
        for (int c = 0; c < R; c++)
            inverse[alphabet[c]] = c;
    }

    // Create a new Alphabet of Unicode chars 0 to R-1
    private Alphabet(int R) {
        alphabet = new char[R];
        inverse = new int[R];
        this.R = R;

        // can't use char since R can be as big as 65,536
        for (int i = 0; i < R; i++)
            alphabet[i] = (char) i;
        for (int i = 0; i < R; i++)
            inverse[i] = i;
    }

    // Create a new Alphabet of Unicode chars 0 to 255 (extended ASCII)
    public Alphabet() {
        this(256);
    }

    // is character c in the alphabet?
    public boolean contains(char c) {
        return inverse[c] != -1;
    }

    // return radix R
    public int R() {
        return R;
    }

    // return number of bits to represent an index
    public int lgR() {
        int lgR = 0;
        for (int t = R; t > 1; t /= 2)
            lgR++;
        return lgR;
    }

    // convert c to index between 0 and R-1.
    public int toIndex(char c) {
        if (c < 0 || c >= inverse.length || inverse[c] == -1) {
            throw new RuntimeException("Character " + c + " not in alphabet");
        }
        return inverse[c];
    }

    // convert String s over this alphabet into a base-R integer
    public int[] toIndices(String s) {
        char[] source = s.toCharArray();
        int[] target  = new int[s.length()];
        for (int i = 0; i < source.length; i++)
            target[i] = toIndex(source[i]);
        return target;
    }

    // convert an index between 0 and R-1 into a char over this alphabet
    public char toChar(int index) {
        if (index < 0 || index >= R) {
            throw new RuntimeException("Alphabet out of bounds");
        }
        return alphabet[index];
    }

    // Convert base-R integer into a String over this alphabet
    public String toChars(int[] indices) {
        StringBuilder s = new StringBuilder(indices.length);
        for (int i = 0; i < indices.length; i++)
            s.append(toChar(indices[i]));
        return s.toString();
    }


    public static void main(String[] args) {
        int[] encoded1  = Alphabet.BASE64.toIndices("NowIsTheTimeForAllGoodMen");
        String decoded1 = Alphabet.BASE64.toChars(encoded1);
        StdOut.println(decoded1);
 
        int[] encoded2  = Alphabet.DNA.toIndices("AACGAACGGTTTACCCCG");
        String decoded2 = Alphabet.DNA.toChars(encoded2);
        StdOut.println(decoded2);

        int[] encoded3 = Alphabet.DECIMAL.toIndices("01234567890123456789");
        String decoded3 = Alphabet.DECIMAL.toChars(encoded3);
        StdOut.println(decoded3);
    }
}
