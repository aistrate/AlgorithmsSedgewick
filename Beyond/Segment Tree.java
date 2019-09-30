//Author : @aky91

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

class Ideone{

	//segment tree

	public static Scanner scn = new Scanner(System.in);

	public static class SegmentTree{

		private int[] tree;

		public void print(){

			if(tree.length == 0)
				return;

			for(int data : tree)
				System.out.print("" + data + " ");

			System.out.println();
		}

		public void build(int[] A){

			int x = (int) (Math.ceil(Math.log(A.length) / Math.log(2)));
 
	        //Maximum size of segment tree
	        int max_size = 2 * (int) Math.pow(2, x) - 1;

			//instantiate tree array
			this.tree = new int[max_size];

			build(1, 0, A.length - 1, A);
		}

		private int build(int node, int start, int end, int[] A){

			if(start == end){
				// Leaf node will have a single element
				this.tree[node] = A[start];
				return tree[node];
			}

			int mid = (start + end) >> 1;

			int left = build(2*node, start, mid, A);
			int right = build(2*node+1, mid+1, end, A);
			this.tree[node] = Math.max(left , right);

			return this.tree[node];
		}

		private int query(int ss, int se, int qs, int qe, int index){

			// If segment of this node is a part of given range, then return the min of the segment
			if (qs <= ss && qe >= se)
				return tree[index];
	 
			// If segment of this node is outside the given range
			if (se < qs || ss > qe)
				return Integer.MIN_VALUE;
	 
			// If a part of this segment overlaps with the given range
			int mid = (ss + se) >> 1;

			return Math.max(query(ss, mid, qs, qe, 2 * index),
					query(mid + 1, se, qs, qe, 2 * index + 1));
		}
	 
		// Return minimum of elements in range from index qs (quey start) to qe (query end).
		public int query(int qs, int qe, int[] A){

			int n = A.length;

			// Check for erroneous input values
			if (qs < 0 || qe > n - 1 || qs > qe) {
				System.out.println("Invalid Input");
				return -1;
			}
	
			return query(0, n - 1, qs, qe, 1);
		}

		public void update(int idx, int val, int[] A){
			update(1, 0, A.length - 1, idx, val, A);
		}

		private int update(int node, int start, int end, int idx, int val, int[] A){

			if(start == end){
				// Leaf node
				A[idx] = val;
				this.tree[node] = val;
				return val;
			}

			int mid = (start + end) >> 1;

			if(start <= idx && idx <= mid){

				// If idx is in the left child, recurse on the left child
				int left = update(2*node, start, mid, idx, val, A);
				int right = this.tree[2*node+1]; 
				
				this.tree[node] = Math.max(left, right);

			} else {

				// if idx is in the right child, recurse on the right child
				int left = this.tree[2*node]; 
				int right = update(2*node+1, mid+1, end, idx, val, A);

				this.tree[node] = Math.max(left, right);
			}
			
			return this.tree[node];
		}
	}

	public static void main (String[] args) throws java.lang.Exception{

		SegmentTree st = new SegmentTree();

		int[] arr = {17, 18, 5, 2, 7, 11, 1, 13, 9, 16};
		st.build(arr);
		st.print();

		System.out.println("" + st.query(1, 5, arr));

		st.update(4, 20, arr);

		System.out.println("" + st.query(1, 5, arr));
	}
}


