/*************************************************************************
 *  Compilation:  javac FlowEdge.java
 *  Execution:    java FlowEdge
 *
 *  Capacitated edge with a flow in a flow network.
 *
 *************************************************************************/

/**
 *  The <tt>FlowEdge</tt> class represents a capacitated edge with a flow
 *  in a digraph.
 *  <p>
 *  For additional documentation, see <a href="/algs4/74or">Section 7.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */


public class FlowEdge {
    private final int v;             // from
    private final int w;             // to 
    private final double capacity;   // capacity
    private double flow;             // flow

    public FlowEdge(int v, int w, double capacity) {
        if (capacity < 0) throw new RuntimeException("Negative edge capacity");
        this.v         = v;
        this.w         = w;  
        this.capacity  = capacity;
        this.flow      = 0;
    }

    public FlowEdge(int v, int w, double capacity, double flow) {
        if (capacity < 0) throw new RuntimeException("Negative edge capacity");
        this.v         = v;
        this.w         = w;  
        this.capacity  = capacity;
        this.flow      = flow;
    }

    // accessor methods
    public int from()         { return v;        }  
    public int to()           { return w;        }  
    public double capacity()  { return capacity; }
    public double flow()      { return flow;     }


    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Illegal endpoint");
    }

    public double residualCapacityTo(int vertex) {
        if      (vertex == v) return flow;
        else if (vertex == w) return capacity - flow;
        else throw new RuntimeException("Illegal endpoint");
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if      (vertex == v) flow -= delta;
        else if (vertex == w) flow += delta;
        else throw new RuntimeException("Illegal endpoint");
    }


    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }


   /**
     * Test client.
     */
    public static void main(String[] args) {
        FlowEdge e = new FlowEdge(12, 23, 3.14);
        StdOut.println(e);
    }

}
