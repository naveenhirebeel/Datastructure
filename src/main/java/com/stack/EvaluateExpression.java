package com.stack;

import java.util.Stack;

public class EvaluateExpression {

    public static void main(String[] args) {
//        String exp1 = "A+B*C+D";
        String exp1 = "1+2*3+4";
//        String exp2 = "a+b*(c^d-e)^(f+g*h)-i";
        String postFix = infixToPostfix(exp1);
        System.out.println(evaluatePostFix(postFix));
    }

    private static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(c)<= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        //Very Important!
        while (!stack.isEmpty()) {
            if(stack.peek() == '(')
                throw new RuntimeException("Invalid Expression");

            result.append(stack.pop());
        }
        return result.toString();
    }

    private static Integer precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    private static Integer evaluatePostFix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            if(Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                Integer val1 = stack.pop();
                Integer val2 = stack.pop();
                switch(c)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1);
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;
                }
            }
        }
        return stack.pop();
    }

}
