/*
 * 1.3.33 Deque. A double-ended queue or deque (pronounced “deck”) is like a stack or 
 * a queue but supports adding and removing items at both ends. 
 */
public class Deque<Item> {
	private class Node {
		Node previous;
		Node next;
		Item item;
	}
	
	private Node Left; //the leftmost item of the queue
	private Node Right; // the rightmost item of the queue
	private int N;
	
	Deque (){ //constructor,create an empty deque
		Left = null;
		Right = null;
		N = 0;		
	}	
	public boolean isEmpty(){ //is the deque empty?
		return N == 0;
	}
	public int size(){ //number of items in the deque
		return N;
	}
	public void pushLeft(Item item) { //add an item to the left end
		Node oldLeft = Left;
		Left = new Node();
		Left.item = item;
		Left.previous = null;
		if (isEmpty()){
			Right = Left;
		}else{
			Left.next = oldLeft;
			oldLeft.previous = Left;
		}
		N++;
	}
	public void pushRight(Item item){ //add an item to the right end
		Node oldRight = Right;
		Right = new Node();
		Right.item = item;
		Right.next = null;
		if (isEmpty()){
			Left = Right;
		}else{
			oldRight.next = Right;
			Right.previous = Right;
		}
		N++;
	}
	public Item popLeft(){ //remove an item from the left end
		if (isEmpty()){
			return null;
		}else{
			Item item = Left.item;
			Left = Left.next;
			N--;
			if(isEmpty()){
				Right = null;
			}else {
				Left.previous = null;
			}
			return item;
		}
	}
	public Item popRight(){ //remove an item from the right end
		if (isEmpty()){
			return null;
		}else{
			Item item = Right.item;
			Right = Right.next;
			N--;
			if(isEmpty()){
				Left = null;
			}else {
				Right.next = null;
			}
			return item;
		}
	}
	
	
	
}
