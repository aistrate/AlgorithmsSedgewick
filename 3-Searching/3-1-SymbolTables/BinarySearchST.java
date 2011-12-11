/*************************************************************************
 *  Compilation:  javac BinarySearchST.java
 *  Execution:    java BinarySearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Symbol table implementation with binary search in an ordered array.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java BinarySearchST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/


public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int N = 0;

    // create an empty symbol table with default initial capacity
    public BinarySearchST() { this(INIT_CAPACITY); }   

    // create an empty symbol table with given initial capacity
    public BinarySearchST(int capacity) { 
        keys = (Key[]) new Comparable[capacity]; 
        vals = (Value[]) new Object[capacity]; 
    }   

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= N;
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }


    // is the key in the table?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // number of key-value pairs in the table
    public int size() {
        return N;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the value associated with the given key, or null if no such key
    public Value get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key); 
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    } 

    // return the number of keys in the table that are smaller than given key
    public int rank(Key key) {
        int lo = 0, hi = N-1; 
        while (lo <= hi) { 
            int m = lo + (hi - lo) / 2; 
            int cmp = key.compareTo(keys[m]); 
            if      (cmp < 0) hi = m - 1; 
            else if (cmp > 0) lo = m + 1; 
            else return m; 
        } 
        return lo;
    } 


    // Search for key. Update value if found; grow table if new. 
    public void put(Key key, Value val)  {
        if (val == null) { delete(key); return; }

        int i = rank(key);

        // key is already in table
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        // insert new key-value pair
        if (N == keys.length) resize(2*keys.length);

        for (int j = N; j > i; j--)  {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;

        assert check();
    } 


    // Remove the key-value pair if present
    public void delete(Key key)  {
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);

        // key not in table
        if (i == N || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < N-1; j++)  {
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }

        N--;
        keys[N] = null;  // to avoid loitering
        vals[N] = null;

        // resize if 1/4 full
        if (N > 0 && N == keys.length/4) resize(keys.length/2);

        assert check();
    } 

    // delete the minimum key and its associated value
    public void deleteMin() {
        if (isEmpty()) throw new RuntimeException("Symbol table underflow error");
        delete(min());
    }

    // delete the maximum key and its associated value
    public void deleteMax() {
        if (isEmpty()) throw new RuntimeException("Symbol table underflow error");
        delete(max());
    }


   /*****************************************************************************
    *  Ordered symbol table methods
    *****************************************************************************/
    public Key min() {
        if (isEmpty()) return null;
        return keys[0]; 
    }

    public Key max() {
        if (isEmpty()) return null;
        return keys[N-1];
    }

    public Key select(int k) {
        if (k < 0 || k >= N) return null;
        return keys[k];
    }

    public Key floor(Key key) {
        int i = rank(key);
        if (i < N && key.compareTo(keys[i]) == 0) return keys[i];
        if (i == 0) return null;
        else return keys[i-1];
    }

    public Key ceiling(Key key) {
        int i = rank(key);
        if (i == N) return null; 
        else return keys[i];
    }

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>(); 
        if (lo == null && hi == null) return queue;
        if (lo == null) throw new RuntimeException("lo is null in keys()");
        if (hi == null) throw new RuntimeException("hi is null in keys()");
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++) 
            queue.enqueue(keys[i]);
        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
        return queue; 
    }


   /*****************************************************************************
    *  Check internal invariants
    *****************************************************************************/

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i-1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }


   /*****************************************************************************
    *  Test client
    *****************************************************************************/
    public static void main(String[] args) { 
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
