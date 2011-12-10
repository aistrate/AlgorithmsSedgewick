
/*************************************************************************
 *  Compilation:  javac FarthestPair.java
 *  Execution:    java FarthestPair < input.txt
 *  Dependencies: GrahamScan.java Point2D.java
 *  
 *  Given a set of N points in the plane, find the farthest pair
 *  (equivalently, compute the diameter of the set of points).
 *
 *  Computes the convex hull of the set of points and using the
 *  rotating callipers method to find all antipodal point pairs
 *  and the farthest pair.
 *
 *************************************************************************/

public class FarthestPair {

    // farthest pair of points and distance
    private Point2D best1, best2;
    private double bestDistance = Double.NEGATIVE_INFINITY;

    public FarthestPair(Point2D[] points) {
        GrahamScan graham = new GrahamScan(points);

        // single point
        if (points.length <= 1) return;

        // number of points on the hull
        int M = 0;
        for (Point2D p : graham.hull())
            M++;

        // the hull, in counterclockwise order
        Point2D[] hull = new Point2D[M+1];
        int m = 1;
        for (Point2D p : graham.hull()) {
            hull[m++] = p;
        }

        // all points are equal
        if (M == 1) return;

        // points are collinear
        if (M == 2) {
            best1 = hull[1];
            best2 = hull[2];
            bestDistance = best1.distanceTo(best2);
            return;
        }

        // k = farthest vertex from edge from hull[1] to hull[M]
        int k = 2;
        while (Point2D.area2(hull[M], hull[k+1], hull[1]) > Point2D.area2(hull[M], hull[k], hull[1])) {
            k++;
        }

        int j = k;
        for (int i = 1; i <= k; i++) {
            // StdOut.println("hull[i] + " and " + hull[j] + " are antipodal");
            if (hull[i].distanceTo(hull[j]) > bestDistance) {
                best1 = hull[i];
                best2 = hull[j];
                bestDistance = hull[i].distanceTo(hull[j]);
            }
            while ((j < M) && Point2D.area2(hull[i], hull[j+1], hull[i+1]) > Point2D.area2(hull[i], hull[j], hull[i+1])) {
                j++;
                // StdOut.println(hull[i] + " and " + hull[j] + " are antipodal");
                double distance = hull[i].distanceTo(hull[j]);
                if (distance > bestDistance) {
                    best1 = hull[i];
                    best2 = hull[j];
                    bestDistance = hull[i].distanceTo(hull[j]);
                }
            }
        }
    }

    public Point2D either()    { return best1;        }
    public Point2D other()     { return best2;        }
    public double distance()   { return bestDistance; }


    public static void main(String[] args) {
        int N = StdIn.readInt();
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point2D(x, y);
        }
        FarthestPair farthest = new FarthestPair(points);
        StdOut.println(farthest.distance() + " from " + farthest.either() + " to " + farthest.other());
    }

}
