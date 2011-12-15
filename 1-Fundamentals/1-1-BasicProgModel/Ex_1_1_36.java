
public class Ex_1_1_36
{
    public interface IShuffle
    {
        public void shuffle(int[] a);
    }
    
    public static void ShuffleTest(IShuffle shuffle, int m, int n)
    {
        int[][] s = new int[m][m];
        
        for (int k = 0; k < n; k++)
        {
            int[] a = new int[m];
            for (int i = 0; i < m; i++)
                a[i] = i;
            
            shuffle.shuffle(a);
            
            for (int i = 0; i < m; i++)
                s[i][a[i]]++;
        }
        
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < m; j++)
                StdOut.printf("%7d", s[i][j]);
            StdOut.println();
        }
    }
    
    public static void main(String[] args)
    {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        
        // closure
        IShuffle shuffle = new IShuffle()
        {
            public void shuffle(int[] a)
            {
                StdRandom.shuffle(a);
            }
        };
        
        ShuffleTest(shuffle, m, n);
    }
}
