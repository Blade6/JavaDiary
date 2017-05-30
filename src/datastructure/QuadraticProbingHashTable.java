package datastructure;

// 使用平方探测方法的散列表
// H_i = (H(key) + d_i) % m
// H_i表示第i个要插入的单元
// d_i是探测方法的第i项，平方探测为i^2
// m是表长
public class QuadraticProbingHashTable<E> {
	
	private static final int DEFAULT_TABLE_SIZE = 11;
	
	private HashEntry<E>[] array;
	private int currentSize;

	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}
	
	public QuadraticProbingHashTable(int size) {
		allocateArray(size);
		makeEmpty();
	}
	
	public void makeEmpty() {
		currentSize = 0;
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}
	
	public boolean contains(E x) {
		int currentPos = findPos(x);
		return isActive(currentPos);
	}
	
	public void insert(E x) {
		int currentPos = findPos(x);
		if (isActive(currentPos))
			return ;
		
		array[currentPos] = new HashEntry<E>(x, true);
		
		if (++currentSize > array.length / 2)
			rehash();
	}
	
	public void remove(E x) {}
	
	private static class HashEntry<E> {
		
		public E element;
		// 懒惰删除的标记
		public boolean isActive;
		
		public HashEntry(E e) {
			this(e, true);
		}
		
		public HashEntry(E e, boolean i) {
			element = e;
			isActive = i;
		}
	}
	
	private void allocateArray(int arraySize) {
		array = new HashEntry[arraySize];
	}
	
	private boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive;
	}
	
	private int findPos(E x) {
		int offset = 1;
		int currentPos = myhash(x);
		
		// 快速平方探测
		// 因为平方探测f(i) = i^2
		// 所以f(i) - f(i-1) = 2 * i - 1
		// 此方法比通常的方法要快，因为它避免了看似需要的乘法和除法
		while (array[currentPos] != null &&
			!array[currentPos].element.equals(x)) {
			currentPos += offset;
			offset += 2;
			if (currentPos >= array.length)
				currentPos -= array.length;
		}
		
		return currentPos;
	}
	
	private void rehash() {
		HashEntry<E>[] oldArray = array;
		
		allocateArray(nextPrime(2 * oldArray.length));
		currentSize = 0;
		
		for (int i = 0; i < oldArray.length; i++)
			if (oldArray[i] != null && oldArray[i].isActive)
				insert(oldArray[i].element);
	}
	
	private int myhash(E x) {
		int hashVal = x.hashCode();
		
		hashVal %= array.length;
		if (hashVal < 0)
			hashVal += array.length;
		
		return hashVal;
	}
	
	private static int nextPrime(int n) {
		if (n % 2 == 0) ++n;
		while (!isPrime(n+1)) 
			n += 2;
		return n+1;
	}
	
	private static boolean isPrime(int n) {
		if (n == 0 || n == 1 || n < 0) return false;
		if (n == 2 || n == 3) return true;
		
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) return true;
		}
		
		return false;
	}
	
}
