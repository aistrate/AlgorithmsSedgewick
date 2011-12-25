
/*************************************************************************
 *
 *  % java Ex_1_3_09
 *  1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )
 *  ( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )
 *
 *  % java Ex_1_3_09
 *  sqrt 1 + 2 ) )
 *  ( sqrt ( 1 + 2 ) )
 *
 *************************************************************************/

public class Ex_1_3_09
{
    public static void main(String[] args)
    { 
        Stack<String> ops  = new Stack<String>();
        Stack<String> vals = new Stack<String>();

        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            
            if      (s.equals("("))               ;
            else if (s.equals("+") ||
                     s.equals("-") ||
                     s.equals("*") ||
                     s.equals("/") ||
                     s.equals("sqrt")) ops.push(s);
            else if (s.equals(")"))
            {
                String op = ops.pop();
                String v = vals.pop();
                
                if (op.equals("+") ||
                    op.equals("-") ||
                    op.equals("*") ||
                    op.equals("/"))
                    v = String.format("( %s %s %s )", vals.pop(), op, v);
                else if (op.equals("sqrt"))
                    v = String.format("( %s %s )", op, v);
                
                vals.push(v);
            }
            else vals.push(s);
            //else vals.push(((Double)Double.parseDouble(s)).toString());
        }
        
        StdOut.println(vals.pop());
    }
}
