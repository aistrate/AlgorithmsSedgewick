/*************************************************************************
 *  Compilation:  javac Vector.java
 *  Execution:    java Vector
 *
 *  Implementation of a vector of real numbers.
 *
 *  This class is implemented to be immutable: once the client program
 *  initialize a Vector, it cannot change any of its fields
 *  (N or data[i]) either directly or indirectly. Immutability is a
 *  very desirable feature of a data type.
 *
 *  % java Vector
 *     x     = [ 1.0 2.0 3.0 4.0 ]
 *     y     = [ 5.0 2.0 4.0 1.0 ]
 *     z     = [ 6.0 4.0 7.0 5.0 ]
 *   10z     = [ 60.0 40.0 70.0 50.0 ]
 *    |x|    = 5.477225575051661
 *   <x, y>  = 25.0
 * 
 *
 *  Note that Vector is also the name of an unrelated Java library class.
 *
 *************************************************************************/

public class Vector { 

    private int N;               // length of the vector
    private double[] data;       // array of vector's components


    // create the zero vector of length n
    public Vector(int n) {
        N = n;
        data = new double[N];
    }

    // create a vector from either an array or a vararg list
    public Vector(double[] d) {
        N = d.length;

        // defensive copy so that client can't alter our copy of data[]
        data = new double[N];
        for (int i = 0; i < N; i++)
            data[i] = d[i];
    }

    // create a vector from either an array or a vararg list
    // this constructor uses Java's vararg syntax to support
    // a constructor that takes a variable number of arguments, such as
    // Vector x = new Vector(1.0, 2.0, 3.0, 4.0);
    // Vector y = new Vector(5.0, 2.0, 4.0, 1.0);
/*
    public Vector(double... d) {
        N = d.length;

        // defensive copy so that client can't alter our copy of data[]
        data = new double[N];
        for (int i = 0; i < N; i++)
            data[i] = d[i];
    }
*/
    // return the length of the vector
    public int length() {
        return N;
    }

    // return the inner product of this Vector a and b
    public double dot(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        double sum = 0.0;
        for (int i = 0; i < N; i++)
            sum = sum + (this.data[i] * that.data[i]);
        return sum;
    }

    // return the Euclidean norm of this Vector
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    // return the Euclidean distance between this and that
    public double distanceTo(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        return this.minus(that).magnitude();
    }

    // return this + that
    public Vector plus(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }

    // return this + that
    public Vector minus(Vector that) {
        if (this.N != that.N) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }

    // return the corresponding coordinate
    public double cartesian(int i) {
        return data[i];
    }

    // create and return a new object whose value is (this * factor)
    public Vector times(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = factor * data[i];
        return c;
    }


    // return the corresponding unit vector
    public Vector direction() {
        if (this.magnitude() == 0.0) throw new RuntimeException("Zero-vector has no direction");
        return this.times(1.0 / this.magnitude());
    }


    // return a string representation of the vector
    public String toString() {
        String s = "";
        for (int i = 0; i < N; i++)
            s = s + data[i] + " ";
        return s;
    }




    // test client
    public static void main(String[] args) {
        double[] xdata = { 1.0, 2.0, 3.0, 4.0 };
        double[] ydata = { 5.0, 2.0, 4.0, 1.0 };
        Vector x = new Vector(xdata);
        Vector y = new Vector(ydata);

        StdOut.println("   x       = " + x);
        StdOut.println("   y       = " + y);

        Vector z = x.plus(y);
        StdOut.println("   z       = " + z);

        z = z.times(10.0);
        StdOut.println(" 10z       = " + z);

        StdOut.println("  |x|      = " + x.magnitude());
        StdOut.println(" <x, y>    = " + x.dot(y));
        StdOut.println("dist(x, y) = " + x.distanceTo(y));
        StdOut.println("dir(x)     = " + x.direction());

    }
}
