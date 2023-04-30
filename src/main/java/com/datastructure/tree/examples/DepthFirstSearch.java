package com.datastructure.tree.examples;

import com.datastructure.tree.Node;

public class DepthFirstSearch {

    public static void printInOrder(Node node) {
        if(node == null)
            return;

        printInOrder(node.left);
        System.out.println(node.val);
        printInOrder(node.right);
    }

    public static void printPreOrder(Node node) {
        if(node == null)
            return;

        System.out.println(node.val);
        printInOrder(node.left);
        printInOrder(node.right);
    }

    public static void printPostOrder(Node node) {
        if(node == null)
            return;

        printInOrder(node.left);
        printInOrder(node.right);
        System.out.println(node.val);
    }
}
