
// Exercise 1.2.18
public class VarianceAccumulator
{
    private double m;
    private double s;
    private int n;

    public void addDataValue(double x)
    {
        n++;
        s = s + 1.0 * (n-1) / n * (x - m) * (x - m);
        m = m + (x - m) / n;
    }

    public double mean()
    {
        return m;
    }
    
    public double var()
    {
        return s/(n - 1);
    }
    
    public double stddev()
    {
        return Math.sqrt(var());
    }

    public String toString()
    {
        return "Mean (" + n + " values): " + String.format("%7.5f", mean());
    }
    
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        
        VarianceAccumulator a = new VarianceAccumulator();
        
        double[] v = new double[n];
        double total = 0;
        
        for (int i = 0; i < n; i++)
        {
            double x = StdRandom.uniform();
            v[i] = x;
            total += x;
            a.addDataValue(x);
        }
        
        double mean = total / n;
        
        double s = 0;
        for (int i = 0; i < n; i++)
        {
            double d = v[i] - mean;
            s += d * d;
        }
        
        double stddev = Math.sqrt(s / (n-1));
        
        StdOut.println(a.mean() - mean);
        StdOut.println(a.stddev() - stddev);
    }
}
