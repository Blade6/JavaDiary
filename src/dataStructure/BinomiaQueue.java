package dataStructure;

// 二项队列：二项树的集合，每一个高度上至多存在一棵二项树。
// 高度为k的二项树恰好有2^k个节点，而在深度d处的节点数是二项系数(k,d)
// 对于一棵二项树，在存储时存储它的根节点，根节点没有兄弟，故nextSibling是null
// 对于父亲节点，它的儿子们是通过nextSibling连起来的，且是排降序的。
public class BinomiaQueue<E extends Comparable<? super E>> {

	private static final int DEFAULT_TREES = 1;
	
	private int currentSize; //优先队列的项数，即所有节点数之和
	private Node<E>[] theTrees; //二项树的根的数组
	// 数组大小为二项树的数目乘2加1，或者说二项树的数目是数组的长度除以2再减去1。二项树在数组中存储是按高度排序的。
	// 数组第i号索引处，存储的是高度为i的二项树。
	
	public BinomiaQueue() {
		theTrees = new Node[DEFAULT_TREES];
		makeEmpty();
	}
	
	public BinomiaQueue(E item) {
		theTrees = (Node<E>[]) new Node[DEFAULT_TREES];
		theTrees[0] = new Node<E>(item);
		currentSize = 1;
	}
	
	public void merge(BinomiaQueue<E> rhs) {
		if (this == rhs)
			return ;
		
		currentSize += rhs.currentSize;
		
		if (currentSize > capacity()) {
			int maxLength = Math.max(theTrees.length, rhs.theTrees.length);
			expandTheTrees(maxLength+1);
		}
		
		Node<E> carry = null;
		
		// 具有N个结点的二项队列最多有 logN 棵二项树。因为，每棵二项树B(k)中含有2^k个结点。
		// 故：2^0 + 2^1 + 2^2 + .... + 2^k = N，得到 k=logN。k为树的棵数。
		// 注意到上面提到的是“最多” 有logN 棵二项树，这说明一个二项队列可以缺少某棵 B(j) , 0<=j<=k
		for (int i = 0, j = 1; j <= currentSize; i++, j*= 2) {
			Node<E> t1 = theTrees[i];
			Node<E> t2 = i < rhs.theTrees.length ? rhs.theTrees[i] : null;
			
			int whichCase = t1 == null ? 0 : 1;
			whichCase += t2 == null ? 0 : 2;
			whichCase += carry == null ? 0 : 4;
			
			switch (whichCase) {
			case 0: /* No trees */
			case 1: /* Only this */
				break;
			case 2: /* Only rhs */
				theTrees[i] = t2;
				rhs.theTrees[i] = null;
				break;
			case 4: /* Only carry */
				theTrees[i] = carry;
				carry = null;
				break;
			case 3: /* this and rhs */
				carry = combineTrees(t1, t2);
				theTrees[i] = rhs.theTrees[i] = null;
				break;
			case 5: /* this and carry */
				carry = combineTrees(t1, carry);
				theTrees[i] = null;
				break;
			case 6: /* rhs and carry */
				carry = combineTrees(t2, carry);
				rhs.theTrees[i] = null;
				break;
			case 7: /* All three */
				theTrees[i] = carry;
				carry = combineTrees(t1, t2);
				rhs.theTrees[i] = null;
				break;
			default:
				break;
			}
		}
		
		for (int k = 0; k < rhs.theTrees.length; k++) {
			rhs.theTrees[k] = null;
		}
		rhs.currentSize = 0;
	}
	
	public void insert(E x) {
		merge(new BinomiaQueue<E>(x));
	}
	
	public E findMin() {
		return theTrees[findMinIndex()].element;
	}
	
	public E deleteMin() {
		if (isEmpty())
			throw new NullPointerException();
		
		int minIndex = findMinIndex();
		E minItem = findMin();
		
		Node<E> deletedTree = theTrees[minIndex].leftChild;
		
		BinomiaQueue<E> deletedQueue = new BinomiaQueue<>();
		deletedQueue.expandTheTrees(minIndex+1);
		
		// 删除最小元的根节点后，需要把原来的二项树拆分成很多棵小的二项树，
		// 然后把这些二项树打包成一个二项队列，再与原来的二项队列合并
		deletedQueue.currentSize = (1 << minIndex ) - 1;
		for (int j = minIndex - 1; j >= 0; j--) {
			deletedQueue.theTrees[j] = deletedTree;
			deletedTree = deletedTree.nextSibling;
			deletedQueue.theTrees[j].nextSibling = null;
		}
		
		theTrees[minIndex] = null;
		currentSize -= deletedQueue.currentSize + 1;
		
		merge(deletedQueue);
		
		return minItem;
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void makeEmpty() {
		for (int i = 0; i < theTrees.length; i++)
			theTrees[i] = null;
		currentSize = 0;
	}
	
	private void expandTheTrees(int newNumTrees) {
		Node<E>[] oldTrees = theTrees;
		theTrees = new Node[newNumTrees];
		for (int i = 0; i < oldTrees.length; i++)
			theTrees[i] = oldTrees[i];
	}
	
	private Node<E> combineTrees(Node<E> t1, Node<E> t2) {
		if (t1.element.compareTo(t2.element) > 0)
			return combineTrees(t2, t1);
		t2.nextSibling = t1.leftChild;
		t1.leftChild = t2;
		return t1;
	}
	
	private int capacity() {
		// theTrees.length是数组的长度，比如是5
		// 左移一位相当于乘以2
		// 2^(theTrees.length)-1是包含高度为0,1,2,3,4的二叉树所能表示的最多的节点数，也就是31个节点。
		return (1 << theTrees.length) - 1;
	}
	
	private int findMinIndex() {
		if (isEmpty())
			throw new NullPointerException();
		
		int minIndex = 0;
		for (int i = 1; i < theTrees.length; i++) {
			if (theTrees[minIndex] == null && theTrees[i] != null) {
				minIndex = i;
				continue;
			}	
			if (theTrees[i] != null && theTrees[i].element.compareTo(theTrees[minIndex].element) < 0)
				minIndex = i;
		}
		
		return minIndex;
	}
	
	private static class Node<E> {
		E element;
		Node<E> leftChild;
		Node<E> nextSibling;
		
		Node(E theElement) {
			this(theElement, null, null);
		}
		
		Node(E theElement, Node<E> lt, Node<E> nt) {
			element = theElement;
			leftChild = lt;
			nextSibling = nt;
		}
	}
}
