package collection;

public interface MyList<E> {
	
	public void add(E e);
	
	public void add(int index, E e);
	
	public void clear();
	
	public boolean contains(E e);
	
	public E get(int index);
	
	/** Return the index of the first matching element in this list */
	/** Return -1 if no match. */
	public int indexOf(E e);
	
	public boolean isEmpty();
	
	/** Return the index of the last matching element in this list */
	/** Return -1 if no match. */
	public int lastIndexOf(E e);
	
	public boolean remove(E e);
	
	public E remove(int index);
	
	/** Replace the element at the specified position in this list
	 *  with the specified element and return the new set. */
	public Object set(int index, E e);
	
	public int size();
	
}
