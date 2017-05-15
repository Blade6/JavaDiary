package algorithm;

import java.util.Stack;

// ��׺���ʽת��׺���ʽ
public class postfix2infix {

	// �洢�����Ķ�����
	private class BinaryNode {
		char element;
		BinaryNode left;
		BinaryNode right;
		public BinaryNode(char element) {
			this.element = element;
		}
	}
	
	// �������������ӡ������
	public void printBinaryTree(BinaryNode root) {
		if (root.left != null) printBinaryTree(root.left);
		System.out.print(root.element);
		if (root.right != null) printBinaryTree(root.right);
	}
	
	public BinaryNode trans(String s) {
		Stack<BinaryNode> stack = new Stack<>();
		char[] str = s.toCharArray();
		int index = -1;
		while (++index < str.length) {
			if (str[index] >= 'a' && str[index] <= 'z') {
				BinaryNode newNode = new BinaryNode(str[index]);
				stack.push(newNode);
			} else {
				BinaryNode newNode = new BinaryNode(str[index]);
				newNode.right = stack.pop();
				newNode.left = stack.pop();
				stack.push(newNode);
			}
		}
		return stack.pop();
	}
	
	public static void main(String[] args) {
		String s = "ab+cde+**";
		System.out.println("original: " + s);
		System.out.print("result: ");
		postfix2infix demo = new postfix2infix();
		demo.printBinaryTree(demo.trans(s));
	}
	
	

}
