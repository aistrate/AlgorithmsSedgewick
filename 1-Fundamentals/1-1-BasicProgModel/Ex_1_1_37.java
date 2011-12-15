
public class Ex_1_1_37
{
    public static void badShuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniform(N);     // between 0 and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void main(String[] args)
    {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        
        // closure
        Ex_1_1_36.IShuffle shuffle = new Ex_1_1_36.IShuffle()
        {
            public void shuffle(int[] a)
            {
                badShuffle(a);
            }
        };
        
        Ex_1_1_36.ShuffleTest(shuffle, m, n);
    }
}
