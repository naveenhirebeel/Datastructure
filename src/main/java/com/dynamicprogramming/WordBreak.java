package com.dynamicprogramming;

import java.util.Arrays;
import java.util.List;

public class WordBreak {

    static List<String> disctionary = Arrays.asList("i", "like", "java");
    public static void main(String[] args) {
        System.out.println(wordBreak("ilike"));
    }
    private static boolean wordBreak(String str) {
        if(str.length() == 0) {
            return true;
        }

        for(int i = 1; i<=str.length(); i++) {
            if(disctionary.contains(str.substring(0, i))) {
                System.out.println(String.format("Found word : %s", str.substring(0, i)));
                return wordBreak(str.substring(i));
            }
        }

        return false;
    }
}
