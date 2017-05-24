package dataStructure;

// 完全由自己理解实现
// 伸展树
// 每访问一次，被访问的结点就会变成根节点，这个过程叫做伸展
public class MySplayTree<E extends Comparable<? super E>> {

	private int numOfElements;
	private Node<E> root;
	
	public MySplayTree(E element) {
		root = new Node(element);
		numOfElements = 1;
	}
	
	public MySplayTree(E[] items) {
		if (items.length == 0)
			throw new Error();
		root = new Node(items[0]);
		numOfElements = 1;
		for (int i = 0; i < items.length; i++) {
			insert(items[i]);
		}
	}
	
	public void insert(E element) {
		Node<E> tmp = root;
		while (tmp != null) {
			if (tmp.element.compareTo(element) == 0) 
				return ;
			else if (tmp.element.compareTo(element) > 0) {
				if(tmp.leftChild == null) {
					Node<E> insertItem = new Node(element);
					insertItem.parent = tmp;
					tmp.leftChild = insertItem;
					break;
				} else {
					tmp = tmp.leftChild;
				}
			}
			else {
				if (tmp.rightChild == null) {
					Node<E> insertItem = new Node(element);
					insertItem.parent = tmp;
					tmp.rightChild = insertItem;
					break;
				} else {
					tmp = tmp.rightChild;
				}
			}
		}
		++numOfElements;
	}
	
	public Node<E> find(E element) {
		if (numOfElements == 0)
			throw new NullPointerException();
		
		Node<E> loop = root;
		boolean isFound = false;
		while (loop != null) {
			if (loop.element.compareTo(element) < 0)
				loop = loop.rightChild;
			else if (loop.element.compareTo(element) > 0)
				loop = loop.leftChild;
			else {
				isFound = true;
				break;
			}
		}
		
		if (isFound) splaying(loop);
		return loop;
	}
	
	public void splaying(Node<E> access) {
		if (access == root)
			return ;
		
		Node<E> tmp = access.parent;
		while (access != root) {
			//print();
			//System.out.println("----------------");
			if (tmp.parent == null) {
				if (tmp.leftChild == access)
					rotateWithLeftChild(tmp);
				else
					rotateWithRightChild(tmp);
				root = access;
			}
			else {
				if (tmp.parent.leftChild == tmp) {
					if (access == tmp.rightChild) {
						rotateWithRightChild(access.parent);
						rotateWithLeftChild(access.parent);
					} else {
						rotateWithLeftChild(tmp.parent);
						rotateWithLeftChild(access.parent);
					}
				} else {
					if (access == tmp.rightChild) {
						rotateWithRightChild(tmp.parent);
						rotateWithRightChild(access.parent);
					} else {
						rotateWithLeftChild(access.parent);
						rotateWithRightChild(access.parent);
					}
				}
				tmp = access.parent;
			}
			
		}
	}
	
	public void rotateWithLeftChild(Node<E> k) {
		if (k.leftChild == null) return ;
		
		Node<E> k_parent = k.parent;
		boolean left = false;
		if (k_parent != null && k_parent.leftChild == k) left = true;
		
		Node<E> tmp = k.leftChild;
		k.leftChild = tmp.rightChild;
		if (k.leftChild != null) k.leftChild.parent = k;
		tmp.rightChild = k;
		k.parent = tmp;
		
		if (k_parent != null) {
			if (left) 
				k_parent.leftChild = tmp;	
			else 
				k_parent.rightChild = tmp;
		} else {
			root = tmp;
		}
		tmp.parent = k_parent;	
	}
	
	public void rotateWithRightChild(Node<E> k) {
		if (k.rightChild == null) return ;
		
		Node<E> k_parent = k.parent;
		boolean left = false;
		if (k_parent != null && k_parent.leftChild == k) left = true;
		
		Node<E> tmp = k.rightChild;
		k.rightChild = tmp.leftChild;
		if(k.rightChild != null) k.rightChild.parent = k;
		tmp.leftChild = k;
		k.parent = tmp;
		
		if (k_parent != null) {
			if (left) 
				k_parent.leftChild = tmp;	
			else 
				k_parent.rightChild = tmp;
		}
		else {
			root = tmp;
		}
		tmp.parent = k_parent;
	}
	
	private static class Node<E> {
		E element;
		
		public Node(E element) {
			this.element = element;
		}
		
		Node<E> parent;
		Node<E> leftChild;
		Node<E> rightChild;
	}
	
	public void print() {
		print(root);
	}
	
	private void print(Node<E> t) {
		if (t == null)
			return ;
		System.out.print(t.element + " Parent:");
		if (t.parent != null)
			System.out.print(t.parent.element);
		else
			System.out.print(".");
		if (t.leftChild != null)
			System.out.print(" LeftChild: " + t.leftChild.element);
		else
			System.out.print(" LeftChild: .");
		if (t.rightChild != null)
			System.out.print(" RightChild: " + t.rightChild.element);
		else
			System.out.print(" RightChild: .");
		System.out.println();
		print(t.leftChild);
		print(t.rightChild);
	}
	
	public static void main(String[] args) {
		String[] s = new String[]{"j","g","a","b","m","k","y"};
		MySplayTree<String> tree = new MySplayTree(s);
		System.out.println("The First Time:");
		tree.print();
		Node<String> tmp = tree.find("b");
		System.out.println("The second Time:");;
		tree.print();
		tmp = tree.find("m");
		System.out.println("The third Time:");;
		tree.print();
	}
	
}
