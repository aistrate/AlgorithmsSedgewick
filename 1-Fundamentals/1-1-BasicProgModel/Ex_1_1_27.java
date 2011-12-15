
public class Ex_1_1_27
{
    // Not working!
    
    public static double binomial(int n, int k, double p, int indent)
    {
        //StdOut.printf("%sn=%d, k=%d\n", repeat(indent, ' '), n, k);
        
        if ((n == 0) || (k < 0))
        {
            //StdOut.printf("%s[%f]\n", repeat(indent, ' '), p);
            return p;
        }
        else
            return (1.0 - p) * binomial(n-1, k, 1.0 - p, indent+1) + p * binomial(n-1, k-1, p, indent+1);
    }
    
    private static String repeat(int n, char c)
    {
        String s = "";
        for (int i = 0; i < n; i++)
            s += c;
        return s;
    }

    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]),
            k = Integer.parseInt(args[1]);
        double p = Double.parseDouble(args[2]);
        
        double b = binomial(n, k, p, 0);
        
        StdOut.println(b);
    }
}
