package dataStructure;

// ��ʽ��
// ��һ�ڵ�X����·����(null path length)npl(X)����ΪX��һ���������������ӵĽڵ�����·���ĳ���
// ��һ�ڵ����·���������ĸ������ӽڵ����·��������Сֵ��1
// ����0����1�����ӵĽڵ��nplΪ0����npl(null)=-1��
// ��ʽ�����ʣ����ڶ��е�ÿһ���ڵ�X������ӵ���·�����������Ҷ��ӵ���·������ȡ�
public class LeftistHeap<E extends Comparable<? super E>> {

	private Node<E> root;
	
	public LeftistHeap() {
		root = null;
	}
	
	// ��һ���ⲿ��ʽ�Ѻͱ���ʽ�ѽ��кϲ�
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
	
	// �ݹ�ʵ�ֺϲ���ʽ��
	private Node<E> merge(Node<E> h1, Node<E> h2) {
		if (h1 == null) return h2;
		if (h2 == null) return h1;
		if (h1.element.compareTo(h2.element) < 0)
			return merge1(h1, h2);
		else
			return merge1(h2, h1);
	}
	
	private Node<E> merge1(Node<E> h1, Node<E> h2) {
		if (h1.left == null) // ���h1ֻ��һ�����ӣ����Ȼ������ӣ����û�ж��ӣ�������ӱ�Ϊ��
			h1.left = h2; // ��h1û�ж���ʱ��h2��Ϊh1�������
		else {
			h1.right = merge(h1.right, h2);
			if (h1.left.npl < h1.right.npl) // ���¸���nplֵ
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
