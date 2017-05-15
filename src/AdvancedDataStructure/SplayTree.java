package AdvancedDataStructure;

public class SplayTree<E extends Comparable<? super E>> {

	private BinaryNode<E> root;
	private BinaryNode<E> nullNode;
	private BinaryNode<E> header = new BinaryNode<E>(null); // For splay
	private BinaryNode<E> newNode = null; // Used between different inserts
	
	public SplayTree() {
		nullNode = new BinaryNode<E>(null);
		nullNode.left = nullNode.right = nullNode;
		root = nullNode;
	}
	
	/**
	 * Internal method to perform a top-down splay.
	 * The last accessed node becomes the new root.
	 * @param x the target item to spaly around.
	 * @param t the root of the subtree to splay.
	 * @return the subtree after the splay.
	 */
	private BinaryNode<E> splay(E x, BinaryNode<E> t) {
		BinaryNode<E> leftTreeMax, rightTreeMin;
		
		header.left = header.right = nullNode;
		leftTreeMax = rightTreeMin = header;
		
		nullNode.element = x; // Guarantee a match
		
		for ( ; ; )
			if (x.compareTo(t.element) < 0) {
				if (x.compareTo(t.left.element) < 0)
					t = rotateWithLeftChild(t);
				if (t.left == nullNode)
					break;
				// Link Right
				rightTreeMin.left = t;
				rightTreeMin = t;
				t = t.left;
			}
			else if (x.compareTo(t.element) > 0) {
				if (x.compareTo(t.right.element) > 0)
					t = rotateWithRightChild(t);
				if (t.right == nullNode)
					break;
				// Link Left
				leftTreeMax.right = t;
				leftTreeMax = t;
				t = t.right;
			}
			else
				break;
		
		leftTreeMax.right = t.left;
		rightTreeMin.left = t.right;
		t.left = header.right;
		t.right = header.left;
		return t;
				
	}
	
	/**
	 * Insert into the tree.
	 * @param x the item to insert.
	 */
	public void insert(E x) {
		if (newNode == null)
			newNode = new BinaryNode<E>(null);
		newNode.element = x;
		
		if (root == nullNode) {
			newNode.left = newNode.right = nullNode;
			root = newNode;
		}
		else {
			root = splay(x, root);
			if (x.compareTo(root.element) < 0) {
				newNode.left = root.left;
				newNode.right = root;
				root = newNode;
			}
			else if (x.compareTo(root.element) > 0) {
				newNode.right = root.right;
				newNode.left = root;
				root.right = nullNode;
				root = newNode;
			}
			else
				return ;
		}
		newNode = null;
	}
	
	/**
	 * Remove from the tree.
	 * @param x the item to remove.
	 */
	public void remove(E x) {
		BinaryNode<E> newTree;
		
		// If x is found, it will be at the root
		root = splay(x, root);
		if (root.element.compareTo(x) != 0)
			return ; // Item not found; do nothing
		
		if (root.left == nullNode)
			newTree = root.right;
		else {
			// Find the maximum in the left subtree
			// Splay it to the root; and then attach right child
			newTree = root.left;
			newTree = splay(x, newTree);
			newTree.right = root.right;
		}
		root = newTree;
		
	}
	
	private BinaryNode<E> rotateWithLeftChild(BinaryNode<E> k2) {
		BinaryNode<E> tmp = k2.left;
		k2.left = tmp.right;
		tmp.right = k2;
		return tmp;
	}
	
	private BinaryNode<E> rotateWithRightChild(BinaryNode<E> k1) {
		BinaryNode<E> tmp = k1.right;
		k1.right = tmp.left;
		tmp.left = k1;
		return tmp;
	}
	
	public void makeEmpty() {
		root = nullNode;
	}
	
	public boolean isEmpty() {
		return root == nullNode;
	}
	
	// ½Úµã
	private static class BinaryNode<E> {
		BinaryNode(E theElement) {
			this(theElement, null, null);
		}
		
		BinaryNode(E theElement, BinaryNode<E> lt, BinaryNode<E> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}
		
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;
	}	
	
}
