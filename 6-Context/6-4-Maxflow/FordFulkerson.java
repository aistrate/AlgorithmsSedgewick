/*************************************************************************
 *  Compilation:  javac FordFulkerson.java
 *  Execution:    java FordFulkerson V E
 *  Dependencies: FlowNetwork.java FlowEdge.java Queue.java
 *
 *  Ford-Fulkerson algorithm for computing a max flow and 
 *  a min cut using shortest augmenthing path rule.
 *
 *********************************************************************/

public class FordFulkerson {
    private boolean[] marked;     // marked[v] = true iff s->v path in residual graph
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private double value;         // current value of max flow
  
    // max flow in flow network G from s to t
    public FordFulkerson(FlowNetwork G, int s, int t) {
        value = excess(G, t);
        if (!isFeasible(G, s, t)) {
            throw new RuntimeException("Initial flow is infeasible");
        }

        // while there exists an augmenting path, use it
        while (hasAugmentingPath(G, s, t)) {

            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle); 
            }

            value += bottle;
        }

        // check optimality conditions
        assert check(G, s, t);
    }

    // return value of max flow
    public double value()  {
        return value;
    }

    // is v in the s side of the min s-t cut?
    public boolean inCut(int v)  {
        return marked[v];
    }


    // return an augmenting path if one exists, otherwise return null
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        // breadth-first search
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.dequeue();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        edgeTo[w] = e;
                        marked[w] = true;
                        q.enqueue(w);
                    }
                }
            }
        }

        // is there an augmenting path?
        return marked[t];
    }



    // return excess flow at vertex v
    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else               excess += e.flow();
        }
        return excess;
    }

    // return excess flow at vertex v
    private boolean isFeasible(FlowNetwork G, int s, int t) {
        double EPSILON = 1E-11;

        // check that capacity constraints are satisfied
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (e.flow() < 0 || e.flow() > e.capacity()) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        // check that net flow into a vertex equals zero, except at source and sink
        if (Math.abs(value + excess(G, s)) > EPSILON) {
            System.err.println("Excess at source = " + excess(G, s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        if (Math.abs(value - excess(G, t)) > EPSILON) {
            System.err.println("Excess at sink   = " + excess(G, t));
            System.err.println("Max flow         = " + value);
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s || v == t) continue;
            else if (Math.abs(excess(G, v)) > EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        return true;
    }



    // check optimality conditions
    private boolean check(FlowNetwork G, int s, int t) {

        // check that flow is feasible
        if (!isFeasible(G, s, t)) {
            System.err.println("Flow is infeasible");
            return false;
        }

        // check that s is on the source side of min cut and that t is not on source side
        if (!inCut(s)) {
            System.err.println("source " + s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)) {
            System.err.println("sink " + t + " is on source side of min cut");
            return false;
        }

        // check that value of min cut = value of max flow
        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to()))
                    mincutValue += e.capacity();
            }
        }

        double EPSILON = 1E-11;
        if (Math.abs(mincutValue - value) > EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }

        return true;
    }


    // test client that creates random network, solves max flow, and prints results
    public static void main(String[] args) {

        // create flow network with V vertices and E edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int s = 0, t = V-1;
        FlowNetwork G = new FlowNetwork(V, E);
        StdOut.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxflow.inCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Max flow value = " +  maxflow.value());
    }

}
