package DataStructure;

import java.util.LinkedList;
import java.util.List;

// 分离链接散列表：解决散列冲突
public class SeparateChainingHashTable<E> {

	private static final int DEFAULT_TABLE_SIZE = 101;
	
	private List<E> [] theList;
	private int currentSize;
	
	public SeparateChainingHashTable() {
		this( DEFAULT_TABLE_SIZE );
	}
	
	public SeparateChainingHashTable(int size) {
		theList = new LinkedList[nextPrime(size)];
		for (int i = 0; i < theList.length; i++)
			theList[i] = new LinkedList<>();
	}
	
	public void insert(E x) {
		List<E> whichList = theList[myhash(x)];
		if (!whichList.contains(x)) {
			whichList.add(x);
			
			if (++currentSize > theList.length)
				rehash();
		}
	}
	
	public void remove(E x) {
		List<E> whichList = theList[myhash(x)];
		if (whichList.contains(x)) {
			whichList.remove(x);
			currentSize--;
		}
	}
	
	public boolean contains(E x) {
		List<E> whichList = theList[myhash(x)];
		return whichList.contains(x);
	}	
	
	public void makeEmpty() {
		for (int i = 0; i < theList.length; i++)
			theList[i].clear();
		currentSize = 0;
	}
	
	private void rehash() {
		List<E>[] oldLists = theList;
		
		theList = new List[nextPrime(2 * theList.length)];
		for (int j = 0; j < theList.length; j++)
			theList[j] = new LinkedList<>();
		
		currentSize = 0;
		for (int i = 0; i < oldLists.length; i++) {
			for (E item : oldLists[i])
				insert(item);
		}
	}
	
	private int myhash(E x) {
		int hashVal = x.hashCode();
		
		hashVal %= theList.length;
		if (hashVal < 0)
			hashVal += theList.length;
		
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
