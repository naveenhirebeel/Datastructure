package com.datastructure.stack;

import java.util.Stack;

public class ReverseStack {

    static Stack<Integer> stack = new Stack<>();
    static Stack<Integer> tempStack = new Stack<>();
    private static void reverseStack() {
        if(stack.isEmpty())
            return;

        int i = stack.pop();
        tempStack.push(i);
        reverseStack();
//        stack.push(tempStack.pop());
    }

    public static void main(String[] args) {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        reverseStack();

/*
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
*/

        while (!tempStack.isEmpty()) {
            System.out.println(tempStack.pop());
        }

    }
}
