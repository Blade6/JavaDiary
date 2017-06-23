package algorithm;

import genericity.GenericStack;

// 计算表达式
public class EvaluateExpression {

	public static void main(String[] args) {
//		if (args.length < 1) {
//			System.out.println(
//				"Usage: java EvaluateExpression expressions");
//			System.exit(0);
//		}
		
		StringBuilder expression = new StringBuilder();
		for (int i = 0; i < args.length; i++)
			expression.append(args[i]);
		
		try {
			//System.out.println(evaluateExpression(expression.toString()));
			System.out.println(evaluateExpression("1+2"));
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Wrong expression");
		}
	}
	
	public static int evaluateExpression(String expression) {
		GenericStack<Integer> operandStack =
			new GenericStack<>();
		
		GenericStack<Character> operatorStack = 
			new GenericStack<>();
		
		java.util.StringTokenizer tokens = 
			new java.util.StringTokenizer(expression, "()+-/*", true);
		
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken().trim();
			if (token.length() == 0)
				continue;
			else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
				while (!operatorStack.isEmpty() &&
					(operatorStack.peek() == '+' ||
					operatorStack.peek() == '-' ||
					operatorStack.peek() == '*' ||
					operatorStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.push(token.charAt(0));
			}
			else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
				while (!operatorStack.isEmpty() &&
					(operatorStack.peek() == '*' ||
					operatorStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}
				operatorStack.push(token.charAt(0));
			}
			else if (token.trim().charAt(0) == '(')
				operatorStack.push('(');
			else if (token.trim().charAt(0) == ')') {
				while (operatorStack.peek() != '(')
					processAnOperator(operandStack, operatorStack);
				operatorStack.pop();
			}	
			else
				operandStack.push(new Integer(token));
		}
		
		while (!operatorStack.isEmpty())
			processAnOperator(operandStack, operatorStack);
		
		return operandStack.pop();
	}
	
	public static void processAnOperator(
		GenericStack<Integer> operandStack,
		GenericStack<Character> operatorStack) {
		char op = operatorStack.pop();
		int op1 = operandStack.pop();
		int op2 = operandStack.pop();
		if (op == '+')
			operandStack.push(op2 + op1);
		else if (op == '-')
			operandStack.push(op2 - op1);
		else if (op == '*')
			operandStack.push(op2 * op1);
		else if (op == '/')
			operandStack.push(op2 / op1);
	}

}
