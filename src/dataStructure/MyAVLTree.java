package dataStructure;

public class MyAVLTree<E extends Comparable<? super E>> {

	private static class AvlNode<E> {
		AvlNode(E theElement) { this(theElement, null, null); }
		
		AvlNode(E theElement, AvlNode<E> lt, AvlNode<E> rt) {
			element = theElement;
			left = lt;
			right = rt;
			height = 0;
		}
		
		E element;
		AvlNode<E> left;
		AvlNode<E> right;
		int height;
	}
	
	private int height(AvlNode<E> t){
		return t == null ? -1 : t.height;
	}
	
	private AvlNode<E> insert(E x, AvlNode<E> t) {
		if (t == null) return new AvlNode<E>(x, null, null);
		
		int compareResult = x.compareTo(t.element);
		
		if (compareResult < 0) {
			t.left = insert(x, t.left);
			if (height(t.left) - height(t.right) == 2) {
				if (x.compareTo(t.left.element) < 0)
					t = rotateWithLeftChild(t);
				else
					t = doubleWithLeftChild(t);
			}
		}
		else if (compareResult > 0) {
			t.right = insert(x, t.right);
			if (height(t.right) - height(t.left) == 2) {
				if (x.compareTo(t.right.element) > 0)
					t = rotateWithRightChild(t);
				else
					t = doubleWithRightChild(t);
			}
		}
		else ;
		
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}
	
	private AvlNode<E> rotateWithLeftChild(AvlNode<E> k2) {
		AvlNode<E> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		return k1;
	}
	
	private AvlNode<E> rotateWithRightChild(AvlNode<E> k1) {
		AvlNode<E> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		return k2;
	}
	
	private AvlNode<E> doubleWithLeftChild(AvlNode<E> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}
	
	private AvlNode<E> doubleWithRightChild(AvlNode<E> k3) {
		k3.right = rotateWithLeftChild(k3.right);
		return rotateWithRightChild(k3);
	}
	
}
