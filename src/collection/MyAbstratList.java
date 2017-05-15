package collection;

public abstract class MyAbstratList<E> implements MyList<E> {

	protected int size = 0;
	
	protected MyAbstratList() {}
	
	protected MyAbstratList(E[] objects) {
		for (int i = 0; i < objects.length; i++)
			add(objects[i]);
	}
	
	public void add(E e) {
		add(size, e);
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean remove(E e) {
		if (indexOf(e) >= 0) {
			remove(indexOf(e));
			return true;
		}
		else
			return false;
	}
	
}
