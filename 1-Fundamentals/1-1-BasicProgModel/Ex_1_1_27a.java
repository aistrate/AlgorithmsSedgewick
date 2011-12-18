
public class Ex_1_1_27a
{
    public static double binomial(int n, int k, double p, Counter c)
    {
        if (n == 0 && k == 0) return 1.0;
        if (n < 0 || k < 0) return 0.0;
        
        c.increment();
        
        return (1.0 - p) * binomial(n-1, k, p, c) + p * binomial(n-1, k-1, p, c);
    }
    
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]),
            k = Integer.parseInt(args[1]);
        double p = Double.parseDouble(args[2]);
        
        Counter c = new Counter("calls");
        
        double b = binomial(n, k, p, c);
        
        StdOut.println(b);
        StdOut.println(c);
        // java Ex_1_1_27a 10  5 0.5:         1,233 calls
        // java Ex_1_1_27a 20 10 0.5:     1,216,535 calls
        // java Ex_1_1_27a 30 15 0.5: 1,219,164,498 calls
    }
}
