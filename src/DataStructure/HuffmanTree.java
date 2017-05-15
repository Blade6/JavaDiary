package DataStructure;

import java.util.LinkedList;

// ������������Ȩ·��������̵���
public class HuffmanTree<E> {

	// һ�����������������
	public static void main(String[] args) {
		Character[] codes = new Character[]{'A','B','C','D','E','F','G','H'};
		double[] probabilities = new double[]{0.05,0.29,0.07,0.14,0.23,0.03,0.11,0.08};
		HuffmanTree<Character> ht = new HuffmanTree<>(codes, probabilities);
		ht.createTree();
		ht.print("0", "1");
	}
	
	public LinkedList<Node<E>> leef; // Ҷ�ӽڵ㣬�������ú�ʧЧ
	public Node<E> root; // ���ڵ�
	
	public HuffmanTree(E[] elements, double[] weights) {
		if (elements.length != weights.length)
			throw new ArrayIndexOutOfBoundsException();
		
		leef = new LinkedList<>();
		for (int i = 0; i < elements.length; i++) {
			leef.add(new Node<E>(elements[i], weights[i]));
		}
	}
	
	public void createTree() {
		while (leef.size() > 1) {
			Node<E> minNode = leef.remove(findSmall(leef));
			Node<E> nextMinNode = leef.remove(findSmall(leef));
			Node<E> newNode = new Node<>(minNode.weight+nextMinNode.weight);
			newNode.lchild = minNode;
			newNode.rchild = nextMinNode;
			minNode.parent = newNode;
			nextMinNode.parent = newNode;
			leef.add(newNode);
		}
		root = leef.poll();
		leef = null;
	}
	
	// ������������СȨ�������±�
	public int findSmall(LinkedList<Node<E>> list) {
		int min = 0;
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).weight < list.get(min).weight)
				min = i;
		}
		return min;
	}
	
	// ��ӡ��������������ӡҶ�ӽڵ㣬�ɸ�����·�������Ϣ�Ӷ�ʵ�ֹ���������
	public void print(String left, String right) {
		print(root, left, right, "");
	}
	
	private void print(Node<E> node, String left, String right, String curInfo) {
		if (node.lchild != null) {
			print(node.lchild, left, right, curInfo+left);
			print(node.rchild, left, right, curInfo+right);
		} else {
			System.out.println(node.element + " " + curInfo);
		}
	}
	
	private class Node<E> {
		E element; // �ڵ�����
		double weight; // �ڵ��Ȩ
		Node parent;
		Node lchild;
		Node rchild;
		
		public Node(double weight) {
			this.weight = weight;
		}
		
		public Node(E element, double weight) {
			this(weight);
			this.element = element;
		}
	}
}
