
public class Ex_1_1_31
{
    public static void drawRandConn(int N, double p)
    {
        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setScale(-1.0, 1.0);
        StdDraw.setPenRadius(.015);
        
        double[][] d = new double[N][2];
        for (int i = 0; i < N; i++)
        {
            d[i][0] = Math.cos(2 * Math.PI * i / N);
            d[i][1] = Math.sin(2 * Math.PI * i / N);
            StdDraw.point(d[i][0], d[i][1]);
        }
        
        StdDraw.setPenRadius();
        
        for (int i = 0; i < N - 1; i++)
            for (int j = i + 1; j < N; j++)
                if (StdRandom.bernoulli(p))
                    StdDraw.line(d[i][0], d[i][1], d[j][0], d[j][1]);
    }
    
    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        double p = Double.parseDouble(args[1]);
        p = Math.max(0, Math.min(1, p));
        
        drawRandConn(N, p);
    }
}
