package dataStructure;

// 左式堆
// 任一节点X的零路径长(null path length)npl(X)定义为X到一个不具有两个儿子的节点的最短路径的长。
// 任一节点的零路径长比它的各个儿子节点的零路径长的最小值大1
// 具有0个或1个儿子的节点的npl为0，而npl(null)=-1。
// 左式堆性质：对于堆中的每一个节点X，左儿子的零路径长至少与右儿子的零路径长相等。
public class LeftistHeap<E extends Comparable<? super E>> {

	private Node<E> root;
	
	public LeftistHeap() {
		root = null;
	}
	
	// 把一个外部左式堆和本左式堆进行合并
	public void merge(LeftistHeap<E> rhs) {
		if (this == rhs) return ;
		
		root = merge(root, rhs.root);
		rhs.root = null;
	}
	
	public void insert(E x) {
		root = merge(new Node<E>(x), root);
	}
	
	public E findMin() {
		return root.element;
	}
	
	public E deleteMin() {
		if (isEmpty())
			throw new NullPointerException();
		
		E minItem = root.element;
		root = merge(root.left, root.right);
		
		return minItem;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void makeEmpty() {
		root = null;
	}
	
	// 递归实现合并左式堆
	private Node<E> merge(Node<E> h1, Node<E> h2) {
		if (h1 == null) return h2;
		if (h2 == null) return h1;
		if (h1.element.compareTo(h2.element) < 0)
			return merge1(h1, h2);
		else
			return merge1(h2, h1);
	}
	
	private Node<E> merge1(Node<E> h1, Node<E> h2) {
		if (h1.left == null) // 如果h1只有一个儿子，则必然是左儿子，如果没有儿子，则左儿子必为空
			h1.left = h2; // 当h1没有儿子时，h2成为h1的左儿子
		else {
			h1.right = merge(h1.right, h2);
			if (h1.left.npl < h1.right.npl) // 更新根的npl值
				swapChildren(h1);
			h1.npl = h1.right.npl + 1;
		}
		return h1;
	}
	
	private void swapChildren(Node<E> t) {
		Node<E> tmp = t.left;
		t.left = t.right;
		t.right = tmp;
	}
	
	private static class Node<E> {
		
		E element; // The data in the node
		Node<E> left; // Left child
		Node<E> right; // Right child
		int npl; // null path length
		
		Node(E theElement) {
			this(theElement, null, null);
		}
		
		Node(E theElement, Node<E> lt, Node<E> rt) {
			element = theElement;
			left = lt;
			right = rt;
			npl = 0;
		}
	}
}
