
// My solution to Exercises 1.2.16 and 1.2.17
public class MyRational
{
    private long numerator;
    private long denominator;
    
    public MyRational(int numerator, int denominator)
    {
        this((long)numerator, (long)denominator);
    }
    
    private MyRational(long numerator, long denominator)
    {
        if (denominator == 0)
            throw new RuntimeException("Denominator cannot be zero.");
        
        if (denominator < 0)
        {
            numerator = -numerator;
            denominator = -denominator;
        }
        
        long g = gcd(Math.abs(numerator), denominator);
        
        this.numerator = numerator / g;
        this.denominator = denominator / g;
        
        if (Math.abs(this.numerator) > Integer.MAX_VALUE ||
            Math.abs(this.denominator) > Integer.MAX_VALUE)
            throw new RuntimeException("Overflow error.");
    }
    
    public MyRational(int n)
    {
        this.numerator = n;
        this.denominator = 1;
    }
    
    public int numerator()
    {
        return (int)numerator;
    }
    
    public int denominator()
    {
        return (int)denominator;
    }
    
    public MyRational plus(MyRational b)
    {
        return new MyRational(this.numerator * b.denominator + b.numerator * this.denominator,
                            this.denominator * b.denominator);
    }
    
    public MyRational minus(MyRational b)
    {
        return new MyRational(this.numerator * b.denominator - b.numerator * this.denominator,
                            this.denominator * b.denominator);
    }
    
    public MyRational times(MyRational b)
    {
        return new MyRational(this.numerator * b.numerator, this.denominator * b.denominator);
    }
    
    public MyRational divides(MyRational b)
    {
        return new MyRational(this.numerator * b.denominator, this.denominator * b.numerator);
    }
    
    public MyRational neg()
    {
        return new MyRational(-numerator, denominator);
    }
    
    public MyRational inverse()
    {
        return new MyRational(denominator, numerator);
    }
    
    public static MyRational zero()
    {
        return new MyRational(0, 1);
    }
    
    public boolean equals(Object x) {
        if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        MyRational that = (MyRational) x;
        return (this.numerator == that.numerator) && (this.denominator == that.denominator);
    }

    public String toString()
    {
        return numerator + "/" + denominator;
    }
    
    public double toDouble()
    {
        return ((double)numerator) / denominator;
    }
    
    private static long gcd(long p, long q)
    {
        if (q == 0) return p;
        long r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args)
    {
        StdOut.println(new MyRational(12, 28));
        StdOut.println(new MyRational(12, 28).neg());
        StdOut.println(new MyRational(12, 28).inverse());
        StdOut.println();
        
        StdOut.println(new MyRational(-12, 28));
        StdOut.println(new MyRational(12, -28));
        StdOut.println(new MyRational(-12, -28));
        StdOut.println();
        
        StdOut.println(new MyRational(5));
        StdOut.println(MyRational.zero());
        StdOut.println();
        
        StdOut.println(new MyRational(2, 3).toDouble());
        StdOut.println(new MyRational(12, 28).numerator());
        StdOut.println(new MyRational(12, 28).denominator());
        StdOut.println();
        
        MyRational r1 = new MyRational(2, 3),
                 r2 = new MyRational(4, 7);
        
        StdOut.printf("%s + %s = %s (%s)\n", r1, r2, r1.plus(r2),
                                             r1.plus(r2).equals(new MyRational(26, 21)));
        
        StdOut.printf("%s - %s = %s (%s)\n", r1, r2, r1.minus(r2),
                                             r1.minus(r2).equals(new MyRational(2, 21)));        
        
        StdOut.printf("%s * %s = %s (%s)\n", r1, r2, r1.times(r2),
                                             r1.times(r2).equals(new MyRational(8, 21)));        
        
        StdOut.printf("%s / %s = %s (%s)\n", r1, r2, r1.divides(r2),
                                             r1.divides(r2).equals(new MyRational(7, 6)));
        StdOut.println();
        
        r1 = new MyRational(Integer.MAX_VALUE, 10);
        
        r2 = new MyRational(5, 17);
        StdOut.printf("%s * %s = %s\n", r1, r2, r1.times(r2));
        
        r2 = new MyRational(101, 13);
        //StdOut.printf("%s * %s = %s\n", r1, r2, r1.times(r2));
        
        r1 = new MyRational(Integer.MAX_VALUE, Integer.MAX_VALUE - 1);
        r2 = r1.inverse();
        //StdOut.printf("%s + %s = %s\n", r1, r2, r1.plus(r2));
    }
}
