package com.datastructure.tree.examples;

import com.datastructure.tree.Node;

public class BSTUtil {
    public static boolean isBst(Node root, int min, int max) {
        if(root == null)
            return true;

        if (root.val < max && root.val > min
                && isBst(root.left, min, root.val)
                && isBst(root.right, root.val, max)) {
            return true;
        } else {
            return false;
        }
    }
}
