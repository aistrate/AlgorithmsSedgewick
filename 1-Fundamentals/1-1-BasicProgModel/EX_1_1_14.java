/**
 * Created by asus on 2015/11/21.
 */
import edu.princeton.cs.algs4.StdOut;

class EX_1_1_14 {
    public static void main(String[] args){
        StdOut.println(lga(33));
    }

    public static int lga(int N) {
        int M = 1;
        int i = 0;

        while (M<N){
            M *=2;
            i++;
        }
        return i-1;
    }
}