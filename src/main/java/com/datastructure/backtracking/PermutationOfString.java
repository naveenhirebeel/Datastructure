package com.datastructure.backtracking;

public class PermutationOfString {
    public static void main(String[] args) {
        String str = "abc";
        permute("abc", 0, str.length()-1);
    }

    private static void permute(String str, int l, int r) {
        if(l == r) {
            System.out.println(str);
        }
        for(int i = l; i<=r; i++) {
            str = swap(str, l, i);
            permute(str, l+1, r);
            str = swap(str, l, i);
        }
    }

    private static String swap(String str, int i, int j) {
        char[] arr = str.toCharArray();
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);
    }
}
