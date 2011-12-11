/*************************************************************************
 *  Compilation:  javac AssignmentProblem.java
 *  Execution:    java AssignmentProblem N
 *  Dependencies: DijkstraSP.java DirectedEdge.java
 *
 *  Solve an N-by-N assignment problem in N^3 log N time using the
 *  successive shortest path algorithm.
 *
 *  Remark: could use dense version of Dijsktra's algorithm for
 *  improved theoretical efficiency of N^3, but it doesn't seem to
 *  help in practice.
 *
 *  Assumes N-by-N cost matrix is nonnegative.
 *
 *
 *********************************************************************/

public class AssignmentProblem {
    private static final int UNMATCHED = -1;

    private int N;              // number of rows and columns
    private double[][] weight;  // the N-by-N cost matrix
    private double[] px;        // px[i] = dual variable for row i
    private double[] py;        // py[j] = dual variable for col j
    private int[] xy;           // xy[i] = j means i-j is a match
    private int[] yx;           // yx[j] = i means i-j is a match

 
    public AssignmentProblem(double[][] weight) {
        N = weight.length;
        this.weight = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.weight[i][j] = weight[i][j];

        // dual variables
        px = new double[N];
        py = new double[N];

        // initial matching is empty
        xy = new int[N];
        yx = new int[N];
        for (int i = 0; i < N; i++) xy[i] = UNMATCHED;
        for (int j = 0; j < N; j++) yx[j] = UNMATCHED;

        // add N edges to matching
        for (int k = 0; k < N; k++) {
            StdOut.println(k);
            assert isDualFeasible();
            assert isComplementarySlack();
            augment();
        }
        assert check();
    }

    // find shortest augmenting path and upate
    private void augment() {

        // build residual graph
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2*N+2);
        int s = 2*N, t = 2*N+1;
        for (int i = 0; i < N; i++) {
            if (xy[i] == UNMATCHED) G.addEdge(new DirectedEdge(s, i, 0.0));
        }
        for (int j = 0; j < N; j++) {
            if (yx[j] == UNMATCHED) G.addEdge(new DirectedEdge(N+j, t, py[j]));
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (xy[i] == j) G.addEdge(new DirectedEdge(N+j, i, 0.0));
                else            G.addEdge(new DirectedEdge(i, N+j, reduced(i, j)));
            }
        }

        // compute shortest path from s to every other vertex
        DijkstraSP spt = new DijkstraSP(G, s);

        // augment along alternating path
        for (DirectedEdge e : spt.pathTo(t)) {
            int i = e.from(), j = e.to() - N;
            if (i < N) {
                xy[i] = j;
                yx[j] = i;
            }
        }

        // update dual variables
        for (int i = 0; i < N; i++) px[i] += spt.distTo(i);
        for (int j = 0; j < N; j++) py[j] += spt.distTo(N+j);
    }

    // reduced cost of i-j
    private double reduced(int i, int j) {
        return weight[i][j] + px[i] - py[j];
    }

    // total weight of min weight perfect matching
    public double weight() {
        double total = 0.0;
        for (int i = 0; i < N; i++) {
            if (xy[i] != UNMATCHED)
                total += weight[i][xy[i]];
        }
        return total;
    }

    public int sol(int i) {
        return xy[i];
    }

    // check that dual variables are feasible
    private boolean isDualFeasible() {
        // check that all edges have >= 0 reduced cost
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (reduced(i, j) < 0) {
                    StdOut.println("Dual variables are not feasible");
                    return false;
                }
            }
        }
        return true;
    }

    // check that primal and dual variables are complementary slack
    private boolean isComplementarySlack() {

        // check that all matched edges have 0-reduced cost
        for (int i = 0; i < N; i++) {
            if ((xy[i] != UNMATCHED) && (reduced(i, xy[i]) != 0)) {
                StdOut.println("Primal and dual variables are not complementary slack");
                return false;
            }
        }
        return true;
    }

    // check that primal variables are a perfect matching
    private boolean isPerfectMatching() {

        // check that xy[] is a perfect matching
        boolean[] perm = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (perm[xy[i]]) {
                StdOut.println("Not a perfect matching");
                return false;
            }
            perm[xy[i]] = true;
        }

        // check that xy[] and yx[] are inverses
        for (int j = 0; j < N; j++) {
            if (xy[yx[j]] != j) {
                StdOut.println("xy[] and yx[] are not inverses");
                return false;
            }
        }
        for (int i = 0; i < N; i++) {
            if (yx[xy[i]] != i) {
                StdOut.println("xy[] and yx[] are not inverses");
                return false;
            }
        }

        return true;
    }


    // check optimality conditions
    private boolean check() {
        return isPerfectMatching() && isDualFeasible() && isComplementarySlack();
    }

    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        double[][] weight = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                weight[i][j] = Math.random();

        AssignmentProblem assignment = new AssignmentProblem(weight);
        StdOut.println("weight = " + assignment.weight());
        for (int i = 0; i < N; i++)
            StdOut.println(i + "-" + assignment.sol(i));
    }

}
