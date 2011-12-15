/*************************************************
 * 
 * % java Ex_1_1_32 500 0 1000000 < largeT.txt
 *  
 *************************************************/

public class Ex_1_1_32
{
    public static void histogram(double[] values, int n, double l, double r)
    {
        int[] counts = new int[n];
        
        for (int i = 0; i < values.length; i++)
        {
            int k = getInterval(values[i], n, l, r);
            if (k >= 0)
                counts[k]++;
        }
        
        int maxCount = StdStats.max(counts);
        
        StdDraw.setCanvasSize(1024, 512);
        StdDraw.setXscale(l, r);
        StdDraw.setYscale(0, maxCount);
        
        double w = (r - l) / n;
        
        for (int i = 0; i < n; i++)
        {
            double x = l + (i + 0.5) * w,
                   y = counts[i] / 2.0,
                   rw = 0.5 * w,
                   rh = counts[i] / 2.0;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }
    
    private static int getInterval(double v, int n, double l, double r)
    {
        if (v < l || v >= r)
            return -1;
        else
            return (int)(n * (v - l) / (r - l));
    }
    
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        double l = Double.parseDouble(args[1]), 
               r = Double.parseDouble(args[2]);
        
        double[] values = In.readDoubles();
        
        histogram(values, n, l, r);
    }
}
