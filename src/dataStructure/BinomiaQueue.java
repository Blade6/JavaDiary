package dataStructure;

// ������У��������ļ��ϣ�ÿһ���߶����������һ�ö�������
// �߶�Ϊk�Ķ�����ǡ����2^k���ڵ㣬�������d���Ľڵ����Ƕ���ϵ��(k,d)
// ����һ�ö��������ڴ洢ʱ�洢���ĸ��ڵ㣬���ڵ�û���ֵܣ���nextSibling��null
// ���ڸ��׽ڵ㣬���Ķ�������ͨ��nextSibling�������ģ������Ž���ġ�
public class BinomiaQueue<E extends Comparable<? super E>> {

	private static final int DEFAULT_TREES = 1;
	
	private int currentSize; //���ȶ��е������������нڵ���֮��
	private Node<E>[] theTrees; //�������ĸ�������
	// �����СΪ����������Ŀ��2��1������˵����������Ŀ������ĳ��ȳ���2�ټ�ȥ1���������������д洢�ǰ��߶�����ġ�
	// �����i�����������洢���Ǹ߶�Ϊi�Ķ�������
	
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
		
		// ����N�����Ķ����������� logN �ö���������Ϊ��ÿ�ö�����B(k)�к���2^k����㡣
		// �ʣ�2^0 + 2^1 + 2^2 + .... + 2^k = N���õ� k=logN��kΪ���Ŀ�����
		// ע�⵽�����ᵽ���ǡ���ࡱ ��logN �ö���������˵��һ��������п���ȱ��ĳ�� B(j) , 0<=j<=k
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
		
		// ɾ����СԪ�ĸ��ڵ����Ҫ��ԭ���Ķ�������ֳɺܶ��С�Ķ�������
		// Ȼ�����Щ�����������һ��������У�����ԭ���Ķ�����кϲ�
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
		// theTrees.length������ĳ��ȣ�������5
		// ����һλ�൱�ڳ���2
		// 2^(theTrees.length)-1�ǰ����߶�Ϊ0,1,2,3,4�Ķ��������ܱ�ʾ�����Ľڵ�����Ҳ����31���ڵ㡣
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
