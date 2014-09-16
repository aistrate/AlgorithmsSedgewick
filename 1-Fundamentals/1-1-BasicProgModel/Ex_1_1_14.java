public class Ex_1_1_14 {
    public static void main(String[] args){
	System.out.println("Hello World");
        System.out.println(lg(1));
        System.out.println(lg(2));
        System.out.println(lg(40));
        System.out.println(lg(64));
    }

    public static int lg(int val){
	int sum = 0;
	while(val>=2) {
	    val /= 2;
	    sum++;
	}
	return sum;
    }
	   
}