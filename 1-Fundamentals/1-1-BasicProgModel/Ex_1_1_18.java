
public class Ex_1_1_18
{
    public static int mystery1(int a, int b)
    {
        StdOut.printf("%3d, %3d\n", a, b);
        if (b == 0) return 0;
        if (b % 2 == 0) return mystery1(a+a, b/2);
        return mystery1(a+a, b/2) + a;
    }
    
    public static int mystery2(int a, int b)
    {
        StdOut.printf("%7d, %3d\n", a, b);
        if (b == 0) return 1;
        if (b % 2 == 0) return mystery2(a*a, b/2);
        return mystery2(a*a, b/2) * a;
    }
    
    public static void main(String[] args)
    {
        StdOut.println("Result: " + mystery1(5, 7));
        StdOut.println();
        StdOut.println("Result: " + mystery2(5, 7));
    }
}
