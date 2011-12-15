
public class Ex_1_1_15
{
    public static int[] histogram(int[] a, int M)
    {
        int[] h = new int[M];
        int N = a.length;
        
        for (int i = 0; i < N; i++)
            if (a[i] < M)
                h[a[i]]++;

        return h;
    }
    
    public static void main(String[] args)
    {
        int N = 30;
        int M = 10;
        
        int[] a = new int[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(M);
        
        for (int i = 0; i < N; i++)
            StdOut.printf("%2d", a[i]);
        
        int[] h = histogram(a, M);
        
        StdOut.println("\n");
        for (int i = 0; i < M; i++)
            StdOut.printf("%4d", h[i]);
    }
}
