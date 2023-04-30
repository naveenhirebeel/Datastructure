package com.datastructure.tree.examples;

import com.datastructure.tree.Node;

import java.util.Arrays;

public class SortedArrayToBST {

    public static void main(String[] args) {
        int[] arr = {1, 5, 9, 4, 6, 7, 2, 3, 8};
        Arrays.sort(arr);
        Node root = convertToBST(arr, 0, arr.length-1);
        TreeUtil.printLevelOrder(root);
//        TreePrinter.printTree(root);
        System.out.println(BSTUtil.isBst(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
        DepthFirstSearch.printInOrder(root);
    }
    private static Node convertToBST(int[] arr, int start, int end) {
        /* Base Case */
        if (start > end) {
            return null;
        }

        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        Node node = new Node(arr[mid]);

        /* Recursively construct the left subtree and make it
         left child of root */
        node.left = convertToBST(arr, start, mid - 1);

        /* Recursively construct the right subtree and make it
         right child of root */
        node.right = convertToBST(arr, mid + 1, end);

        return node;
    }
}
