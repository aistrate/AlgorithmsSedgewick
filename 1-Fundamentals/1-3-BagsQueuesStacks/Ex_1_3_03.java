
/*************************************************************************
 *  Simulate a sequence of push and pop operations to find out
 *  whether a certain sequence of pop's can occur
 *  
 *  % java Ex_1_3_03
 *  9 8 7 6 5 4 3 2 1 0
 *  true (Unprinted: 0; Stack: [ ])
 *  
 *  4 3 2 1 0 9 8 7 6 5
 *  true (Unprinted: 0; Stack: [ ])
 *  
 *  4 6 8 7 5 3 2 9 0 1
 *  false (Unprinted: 2; Stack: [ 1 0 ])
 *  
 *  2 5 6 7 4 8 9 3 1 0
 *  true (Unprinted: 0; Stack: [ ])
 *  
 *  4 3 2 1 0 5 6 7 8 9
 *  true (Unprinted: 0; Stack: [ ])
 *  
 *  1 2 3 4 5 6 9 8 7 0
 *  true (Unprinted: 0; Stack: [ ])
 *  
 *  0 4 6 5 3 8 1 7 2 9
 *  false (Unprinted: 4; Stack: [ 9 7 2 1 ])
 *  
 *  1 4 7 9 8 6 5 3 0 2
 *  false (Unprinted: 2; Stack: [ 2 0 ])
 *  
 *  2 1 4 3 6 5 8 7 9 0
 *  true (Unprinted: 0; Stack: [ ])
 *  
 *************************************************************************/

public class Ex_1_3_03
{
    public static void checkSequence(int[] v)
    {
        Stack<Integer> s = new Stack<Integer>();
        int n = v.length;
        
        int i = 0, j = 0;
        while (i < n && j <= n)
        {
            if (!s.isEmpty() && s.peek() == v[i])
            {
                StdOut.print(s.pop() + " ");
                i++;
            }
            else
            {
                if (j < n)
                    s.push(j);
                j++;
            }
        }
        StdOut.println();
        
        StdOut.printf("%s (Unprinted: %d; Stack: [ %s])\n",
                      i == n && s.isEmpty(), n - i, s);
    }
    
    public static void main(String[] args)
    {
        String[] a = StdIn.readAll().split("\\s+");
        
        int[] v = new int[a.length];
        for (int i = 0; i < a.length; i++)
            v[i] = Integer.parseInt(a[i]);
        
        checkSequence(v);
    }
}
