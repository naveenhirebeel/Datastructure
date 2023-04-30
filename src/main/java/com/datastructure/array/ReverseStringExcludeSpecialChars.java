package com.datastructure.array;

public class ReverseStringExcludeSpecialChars {
    public static void main(String[] args) {
//        String str = "abcd*&ehd";
        String str = "a!!!b.c.d,e'f,ghi";

        int l = 0;
        int r = str.length()-1;
        char[] s = str.toCharArray();
        while(l < r ) {
            if(!Character.isAlphabetic(s[l])) {
                l++;
            } else if(!Character.isAlphabetic(s[r])) {
                r--;
            } else {
                char t = s[l];
                s[l] = s[r];
                s[r] = t;
                l++; r--;
            }
        }
        System.out.println(s);
    }
}
