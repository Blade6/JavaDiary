package algorithm;

import java.util.HashMap;
import java.util.Stack;

// 中缀表达式转换为后缀表达式(逆波兰表达式)
// a+b*c+(d*e+f)*g 转换为 abc*+de*f+g*+
public class infix2postfix {

	private HashMap<Character, Integer> priority;
	
	public infix2postfix() {
		priority = new HashMap<>();
		priority.put('(', Integer.MAX_VALUE);
		priority.put('*', 2);
		priority.put('/', 2);
		priority.put('+', 1);
		priority.put('-', 1);
	}
	
	public String trans(String s) {
		Stack<Character> stack = new Stack<>();
		StringBuilder result = new StringBuilder();
		char[] str = s.toCharArray();
		int index = -1;
		
		while (++index < str.length) {	
			if (str[index] >= 'a' && str[index] <= 'z') {
				result.append(str[index]);
			} else {
				if (str[index] == ')') {
					while (stack.peek() != '(') {
						result.append(stack.pop());
					}	
					stack.pop();
					continue;
				}
				
				
				if (!stack.isEmpty()) {
					if (Larger(stack.peek(), str[index])) {
						result.append(stack.pop());
						while (!stack.isEmpty() && Larger(stack.peek(), str[index]))
							result.append(stack.pop());
					}
				}
				stack.push(str[index]);
			}
		}
		
		while (!stack.isEmpty()) result.append(stack.pop());
		
		return result.toString();		
	}
	
	public boolean Larger(char a, char b) {
		if (a == '(') return false;
		return priority.get(a) >= priority.get(b);
	}
	
	public static void main(String[] args) {
		String s = "a+b*c+(d*e+f)*g";
		System.out.println("original: " + s);
		infix2postfix demo = new infix2postfix();
		System.out.println("result: " + demo.trans(s));
	}
	
	
}
