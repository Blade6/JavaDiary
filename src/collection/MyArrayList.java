package collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

// From 《数据机构与算法分析》
public class MyArrayList<E> implements Iterable<E> {

	private static final int DEFAULT_CAPACITY = 10;
	
	private int theSize;
	private E[] theItems;
	
	public MyArrayList() { clear(); }
	
	public void clear() {
		theSize = 0;
		ensureCapacity(DEFAULT_CAPACITY);
	}
	
	public int size() { return theSize; }
	public boolean isEmpty() { return size() == 0; }
	public void trimToSize() { ensureCapacity(size()); }
	
	public E get(int idx) {
		if (idx < 0 || idx >= size())
			throw new ArrayIndexOutOfBoundsException();
		return theItems[idx];
	}
	
	public E set(int idx, E newVal) {
		if (idx < 0 || idx >= size())
			throw new ArrayIndexOutOfBoundsException();
		E old = theItems[idx];
		theItems[idx] = newVal;
		return old;
	}
	
	public void ensureCapacity(int newCapacity) {
		if (newCapacity > size())
			return ;
		
		E[] old = theItems;
		theItems = (E[]) new Object[newCapacity];
		for (int i = 0; i < size(); i++)
			theItems[i] = old[i];
	}
	
	public boolean add(E x) {
		add(size(), x);
		return true;
	}
	
	public void add(int idx, E x) {
		if (theItems.length == size())
			ensureCapacity(size() * 2 + 1);
		for (int i = theSize; i > idx; i--)
			theItems[i] = theItems[i-1];
		theItems[idx] = x;
		
		theSize++;
	}
	
	public E remove(int idx) {
		E removeItem = theItems[idx];
		for (int i = idx; i < size() -1; i++)
			theItems[i] = theItems[i+1];
		
		theSize--;
		return removeItem;
	}
	
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<E> {
		private int current = 0;
		
		public boolean hasNext() {
			return current < size();
		}
		
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return theItems[current++];
		}
		
		public void remove() {
			MyArrayList.this.remove(--current);
		}
	}
	
}
