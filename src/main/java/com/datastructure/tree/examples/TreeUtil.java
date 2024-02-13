package com.datastructure.tree.examples;

import com.datastructure.tree.Node;

import java.util.LinkedList;
import java.util.Queue;

public class TreeUtil {

    public static int height(Node root) {
        if(root == null)
            return -1;

        int lh = height(root.left);
        int rh = height(root.right);

        return Math.max(lh, rh)+1;
    }

    public static void printLevelOrder(Node root) {
        if(root == null)
            return;

        int ht = height(root);
        for(int i=0; i<=ht; i++)
            printGivenLevel(root, i);
    }

    private static void printGivenLevel(Node root, int level) {
        if(root == null)
            return;
        if(level == 0)
            System.out.println(root.val);
        else if (level > 0) {
            printGivenLevel(root.left, level-1);
            printGivenLevel(root.right, level-1);
        }
    }

    private static void printTreeUsingQueue(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Node node = queue.remove();
            System.out.println(node.val);

            if(node.left != null)
                queue.add(node.left);

            if(node.right != null)
                queue.add(node.right);
        }
    }

    public static void printInOrder(Node node) {
        if(node == null)
            return;

        printInOrder(node.left);
        System.out.print(node.val+ " ");
        printInOrder(node.right);
    }
}
