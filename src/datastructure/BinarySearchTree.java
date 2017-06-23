package datastructure;

// 二叉查找树，也称二叉排序树
public class BinarySearchTree<E extends Comparable<? super E>> {

	// 节点
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
	
	private BinaryNode<E> root;
	
	public BinarySearchTree() { root = null; }
	
	public void makeEmpty() { root = null; }
	
	public boolean isEmpty() { return root == null; }
	
	public boolean contains(E x) { return contains(x, root); }
	
	public E findMin() {
		if (isEmpty()) throw new NullPointerException();
		return findMin(root).element;
	}
	
	public E findMax() {
		if (isEmpty()) throw new NullPointerException();
		return findMax(root).element;
	}
	
	public void insert(E x) { root = insert(x, root); }
	
	public void remove(E x) { root = remove(x, root); }
	
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}
	
	private boolean contains(E x, BinaryNode<E> t) {
		if (t == null)
			return false;
		
		int compareResult = x.compareTo(t.element);
		
		if (compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true;
	}
	
	// 递归实现
	private BinaryNode<E> findMin(BinaryNode<E> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}
	
	// 非递归实现
	private BinaryNode<E> findMax(BinaryNode<E> t) {
		if (t != null) {
			while (t.right != null)
				t = t.right;
		}
		return t;
	}
	
	private BinaryNode<E> insert(E x, BinaryNode<E> t) {
		if (t == null)
			return new BinaryNode<E>(x, null, null);
		
		int compareResult = x.compareTo(t.element);
		
		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else ;
		
		return t;
	}
	
	private BinaryNode<E> remove(E x, BinaryNode<E> t) {
		if (t == null)
			return t;
		
		int compareResult = x.compareTo(t.element);
		
		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) {
			BinaryNode<E> tmp = t.left;
			t.element = findMin(t.right).element;
			t.left = tmp;
			t.right = remove(t.element, t.right);
			// 注意：《数据结构与算法分析》一书中并没有112行与114行，我认为书中有错误。
		} else
			t = (t.left != null) ? t.left : t.right;
		
		return t;		
	}
	
	private void printTree(BinaryNode<E> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
	
}
