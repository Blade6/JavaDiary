package AdvancedDataStructure;

// 红黑树
public class RedBlackTree<E extends Comparable<? super E>> {

	private RedBlackNode<E> header; // 根标记，右链指向真正的根
	private RedBlackNode<E> nullNode; // 指示一个null引用
	
	private static final int BLACK = 1;
	private static final int RED = 0;
	
	/**
	 * Construct the tree.
	 */
	public RedBlackTree() {
		nullNode = new RedBlackNode<E>(null); 
		nullNode.left = nullNode.right = nullNode;
		header = new RedBlackNode<E>(null);
		header.left = header.right = nullNode;
	}
	
	/**
	 * Internal routing that performs a single or double rotation.
	 * Because the result is attached to the parent, there are four cases.
	 * Called by handleRerient.
	 * @param item the item in handleReorient.
	 * @param parent the parent of the root of the rotated subtree.
	 * @return the root of the rotated subtree.
	 */
	private RedBlackNode<E> rotate(E item, RedBlackNode<E> parent) {
		if (compare(item, parent) < 0)
			return parent.left = compare(item, parent.left) < 0 ?
				rotateWithLeftChild(parent.left) :
				rotateWithRightChild(parent.left);
		else
			return parent.right = compare(item, parent.right) < 0 ?
				rotateWithLeftChild(parent.right) :
				rotateWithRightChild(parent.right);
	}
	
	private RedBlackNode<E> rotateWithLeftChild(RedBlackNode<E> k) {
		RedBlackNode<E> tmp = k.left;
		k.left = tmp.right;
		tmp.right = k;
		return tmp;
	}
	
	private RedBlackNode<E> rotateWithRightChild(RedBlackNode<E> k) {
		RedBlackNode<E> tmp = k.right;
		k.right = tmp.left;
		tmp.left = k;
		return tmp;
	}
	
	/**
	 * Compare item and t.element, using compareTo, with
	 * caveat that if t is header, then item is always larger.
	 * This routing is called if it is possible that t is header.
	 * If it is not possible for t to be header, use compareTo directly.
	 */
	private final int compare(E item, RedBlackNode<E> t){
		if (t == header)
			return 1;
		else
			return item.compareTo(t.element);
	}
	
	// Used in insert routine and its helpers
	private RedBlackNode<E> current;
	private RedBlackNode<E> parent; // 父亲
	private RedBlackNode<E> grand;  // 祖父
	private RedBlackNode<E> great;  // 曾祖
	
	/**
	 * Internal routine that is called during an insertion
	 * if a node has two red children. Performs flip and rotations.
	 * @param item the item being inseted. 
	 */
	private void handleReorient(E item) {
		// Do the color flip
		current.color = RED;
		current.left.color = BLACK;
		current.right.color = BLACK;
		if (parent.color == RED) {
			grand.color = RED;
			if ((compare(item, grand) < 0) != (compare(item, parent) < 0))
				parent = rotate(item, grand); // Start dbl rotate
			current = rotate(item, great);
			current.color = BLACK;
		}
		header.right.color = BLACK; // Make root black
	}
	
	/**
	 * Insert into the tree. 
	 * @param item the item to insert.
	 */
	public void insert(E item) {
		current = parent = grand = header;
		nullNode.element = item;
		
		while (compare(item, current) != 0) {
			great = grand;
			grand = parent;
			parent = current;
			current = compare(item, current) < 0 ? current.left : current.right;
			
			// Check if two red children; fix if so
			if (current.left.color == RED && current.right.color == RED)
				handleReorient(item);
		}
		
		// Insertion fails if already present
		if (current != nullNode)
			return ;
		current = new RedBlackNode<E>(item, nullNode, nullNode);
		
		// Attach to parent
		if (compare(item, parent) < 0)
			parent.left = current;
		else
			parent.right = current;
		handleReorient(item);
	}
	
	/**
	 * Print the tree contents in sorted order 
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(header.right);
	}
	
	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the subtree.
	 */
	private void printTree(RedBlackNode<E> t) {
		if (t != nullNode) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
	
	public boolean isEmpty() {
		return header.right == nullNode;
	}
	
	private static class RedBlackNode<E> {
		
		E element;
		RedBlackNode<E> left;
		RedBlackNode<E> right;
		int color;
		
		RedBlackNode(E theElement) {
			this(theElement, null, null);
		}
		
		RedBlackNode(E theElement, RedBlackNode<E> lt, RedBlackNode<E> rt) {
			element = theElement;
			left = lt;
			right = rt;
			color = RedBlackTree.BLACK;
		}
	}
	
}
