
/*************************************************************************
 *  Exercise 1.3.11
 *  
 *  % java EvaluatePostfix
 *  1 2 3 + 4 5 * * +
 *  101.0
 *  
 *  % java EvaluatePostfix
 *  1 5 sqrt + 2.0 /
 *  1.618033988749895
 *  
 *  % java EvaluatePostfix
 *  12 9 - 105 7 / *
 *  45.0
 *  
 *  % java InfixToPostfix | java EvaluatePostfix
 *  ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )
 *  101.0
 *  
 *  % java InfixToPostfix | java EvaluatePostfix
 *  ( ( 1 + sqrt ( 5 ) ) / 2.0 )
 *  1.618033988749895
 *  
 *************************************************************************/

public class EvaluatePostfix
{
    public static void main(String[] args)
    {
        Stack<Double> vals = new Stack<Double>();

        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            
            if      (s.equals("(") ||
                     s.equals(")")) ;
            else if (s.equals("+") ||
                     s.equals("-") ||
                     s.equals("*") ||
                     s.equals("/") ||
                     s.equals("sqrt"))
            {
                double v = vals.pop();
                
                if      (s.equals("+"))    v = vals.pop() + v;
                else if (s.equals("-"))    v = vals.pop() - v;
                else if (s.equals("*"))    v = vals.pop() * v;
                else if (s.equals("/"))    v = vals.pop() / v;
                else if (s.equals("sqrt")) v = Math.sqrt(v);
                
                vals.push(v);                
            }
            else
                vals.push(Double.parseDouble(s));
        }
        
        StdOut.println(vals.pop());
    }
}
