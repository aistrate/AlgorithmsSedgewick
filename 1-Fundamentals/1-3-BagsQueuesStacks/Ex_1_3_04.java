
/*************************************************************************
 * 
 * % java Ex_1_3_04
 * [()]{}{[()()]()}
 * true
 * 
 * % java Ex_1_3_04
 * [(])
 * false
 * 
 *************************************************************************/

public class Ex_1_3_04
{
    public static boolean isBalanced(String s)
    {
        Stack<Character> open = new Stack<Character>();
        int n = s.length();
        
        for (int i = 0; i < n; i++)
        {
            char c = s.charAt(i);
            
            if (c == '(' || c == '[' || c == '{')
                open.push(c);
            else if ((c == ')' && (open.isEmpty() || open.pop() != '(')) ||
                     (c == ']' && (open.isEmpty() || open.pop() != '[')) ||
                     (c == '}' && (open.isEmpty() || open.pop() != '{')))
                return false;
        }
        
        return open.isEmpty();
    }
    
    public static void main(String[] args)
    {
        String s = StdIn.readAll().trim();
        
        StdOut.println(isBalanced(s));
    }
}
