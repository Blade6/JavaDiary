package DataStructure;

// 小根二叉堆，数组下标从1开始
public class BinaryHeap<E extends Comparable<? super E>> {

	private static final int DEFAULT_CAPACITY = 10;
	
	private int currentSize;
	private E[] array;
	
	public BinaryHeap() {
		this(DEFAULT_CAPACITY);
	}
	
	public BinaryHeap(int capacity) {
		array = (E[]) new Comparable[(capacity + 2) * 11 / 10];
		currentSize = 0;
	}
	
	public BinaryHeap(E[] items) {
		currentSize = items.length;
		array = (E[]) new Comparable[(currentSize + 2) * 11 / 10];
		
		int i = 1;
		for (E item : items)
			array[i++] = item;
		buildHeap();
	}
	
	public void insert(E x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);
		
		// 插入到末端，不断上滤
		int hole = ++currentSize;
		for ( ; hole > 1 && x.compareTo(array[hole / 2]) < 0; hole /= 2)
			array[hole] = array[hole/2];
		array[hole] = x;
	}
	
	public E findMin() {
		return array[1];
	}
	
	public E deleteMin() {
		if (isEmpty())
			throw new NullPointerException();
		
		// 删除最小的元素(即根)，同时把最末端的元素放到根处，让这个元素不断下滤
		E minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);
		
		return minItem;
	}
	
	public boolean isEmpty() { return currentSize == 0; }
	
	public void makeEmpty() {
		currentSize = 0;
	}
	
	private void percolateDown(int hole) {
		int child;
		E tmp = array[hole];
		
		for ( ; hole * 2 <= currentSize; hole = child) {
			child = hole * 2;
			if (child != currentSize && array[child+1].compareTo(array[child]) < 0)
				child++;
			if (array[child].compareTo(tmp) < 0)
				array[hole] = array[child];
			else 
				break;
		}
		
		array[hole] = tmp;
	}
	
	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}
	
	private void enlargeArray(int newSize) {
		E[] oldArray = array;
		array = (E[]) new Comparable[(newSize + 2) * 11 / 10];
		
		for (int i = 1; i < oldArray.length; i++)
			array[i] = oldArray[i];
		
	}
	
}
