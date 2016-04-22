/*
 * Write  a class ResizingArrayDeque that uses a resizing array to implement this API.
 */
public class ResizingArrayDequue<Item> {
	private Item[] a;
	private int N =0;
	ResizingArrayDequue(){ //constructor
		a = (Item[]) new Object[1];
	}
	public boolean isEmpty(){
		return N == 0;
	}
	public int size(){
		return N;
	}
	private void resize(int max){
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0;i <N;i++){
			temp[i] = a[i];
		}
		a =temp;
	}
	public void pushLeft(Item item){
		if (N == a.length){
			resize(a.length*2);
		}
		Item[] temp = (Item[]) new Object[a.length+1];
		temp[0] = item;
		for (int i = 1;i<a.length+1;i++){
			temp[i] = a[i-1];
		}
		a = temp;
		N++;
	}
	public void pushRight(Item item){
		if (N == a.length){
			resize(a.length*2);
		}
		a[N++] = item;
	}
	public Item popLeft(){
		Item item = a[0];
		Item[] temp = (Item[]) new Object[a.length-1];
		for (int i = 0;i<a.length;i++){
			temp[i] = a[i+1];
		}
		a = temp;
		if(N > 0 && N == a.length/4){
			resize(a.length/2);
		}
		N--;
		return item;
	}
	public Item popRight(){
		Item item = a[--N];
		a[N] = null;
		if(N > 0 && N == a.length/4){
			resize(a.length/2);
		}
		return item;
	}
}