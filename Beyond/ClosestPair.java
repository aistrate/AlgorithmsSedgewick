/*************************************************************************
 *  Compilation:  javac ClosestPair.java
 *  Execution:    java ClosestPair < input.txt
 *  Dependencies: Point2D.java
 *  
 *  Given N points in the plane, find the closest pair in N log N time.
 *
 *  Note: could speed it up by comparing square of Euclidean distances
 *  instead of Euclidean distances.
 *
 *************************************************************************/

import java.util.Arrays;

public class ClosestPair {

    // closest pair of points and their Euclidean distance
    private Point2D best1, best2;
    private double bestDistance = Double.POSITIVE_INFINITY;

    public ClosestPair(Point2D[] points) {
        int N = points.length;
        if (N <= 1) return;

        // sort by x-coordinate (breaking ties by y-coordinate)
        Point2D[] pointsByX = new Point2D[N];
        for (int i = 0; i < N; i++) pointsByX[i] = points[i];
        Arrays.sort(pointsByX, Point2D.X_ORDER);

        // check for coincident points
        for (int i = 0; i < N-1; i++) {
            if (pointsByX[i].equals(pointsByX[i+1])) {
                bestDistance = 0.0;
                best1 = pointsByX[i];
                best2 = pointsByX[i+1];
                return;
            }
        }

        // sort by y-coordinate (but not yet sorted) 
        Point2D[] pointsByY = new Point2D[N];
        for (int i = 0; i < N; i++) pointsByY[i] = pointsByX[i];

        // auxiliary array
        Point2D[] aux = new Point2D[N];

        closest(pointsByX, pointsByY, aux, 0, N-1);
    }

    // find closest pair of points in pointsByX[lo..hi]
    // precondition:  pointsByX[lo..hi] and pointsByY[lo..hi] are the same sequence of points
    // precondition:  pointsByX[lo..hi] sorted by x-coordinate
    // postcondition: pointsByY[lo..hi] sorted by y-coordinate
    private double closest(Point2D[] pointsByX, Point2D[] pointsByY, Point2D[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point2D median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid+1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        Merge.merge(pointsByY, aux, lo, mid, hi);

        // aux[0..M-1] = sequence of points closer than delta, sorted by y-coordinate
        int M = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x() - median.x()) < delta)
                aux[M++] = pointsByY[i];
        }

        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < M; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < M) && (aux[j].y() - aux[i].y() < delta); j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];
                        // StdOut.println("better distance = " + delta + " from " + best1 + " to " + best2);
                    }
                }
            }
        }
        return delta;
    }

    public Point2D either() { return best1; }
    public Point2D other()  { return best2; }

    public double distance() {
        return bestDistance;
    }


    public static void main(String[] args) {
        int N = StdIn.readInt();
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            points[i] = new Point2D(x, y);
        }
        ClosestPair closest = new ClosestPair(points);
        StdOut.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
    }

}
