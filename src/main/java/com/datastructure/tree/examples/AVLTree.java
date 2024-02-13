package com.datastructure.tree.examples;

import com.datastructure.tree.Node;

public class AVLTree {

    Node node;
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        Node node = avlTree.insertNode(null, 21);
        node = avlTree.insertNode(node, 26);
        node = avlTree.insertNode(node, 30);
        node = avlTree.insertNode(node, 9);
        node = avlTree.insertNode(node, 14);
        node = avlTree.insertNode(node, 14);
        node = avlTree.insertNode(node, 28);
        node = avlTree.insertNode(node, 18);
        node = avlTree.insertNode(node, 15);
        node = avlTree.insertNode(node, 10);
        node = avlTree.insertNode(node, 2);
        node = avlTree.insertNode(node, 3);
        node = avlTree.insertNode(node, 7);
        TreeUtil.printInOrder(node);
    }

    Node insertNode(Node node, int val) {
        if(node == null)
            return new Node(val);
        if (val < node.val) {
            node.left = insertNode(node.left, val);
        } else if (val > node.val) {
            node.right = insertNode(node.right, val);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);
        if(balance > 1 && val < node.left.val) {
            return rightRotate(node);
        } else if(balance > 1 && val < node.left.val) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        } else if(balance < -1 && val > node.right.val) {
            return leftRotate(node);
        } else if(balance < -1 && val < node.right.val) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node t = y.left;

        x.right = t;
        y.left = x;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t = x.right;

        y.left = t;
        x.right = y;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private int getBalance(Node n) {
        if(n == null)
            return 0;
        return height(n.left) - height(n.right);
    }


    private int height(Node n) {
        if(n == null)
            return 0;
        else
            return n.height;
    }
}
