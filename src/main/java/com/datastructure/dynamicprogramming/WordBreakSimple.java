package com.datastructure.dynamicprogramming;

import java.util.Arrays;
import java.util.List;

public class WordBreakSimple {

    List<String> dictionary = Arrays.asList("i", "like", "java");

    public boolean wordBreak(String str) {
        if(str.length() == 0) return true;

        for(int i = 1; i <=str.length(); i++) {
            if(dictionary.contains(str.substring(0, i))) {
                System.out.println(str.substring(0, i));
                return wordBreak(str.substring(i));
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String word = "ilikejava";
        System.out.println(new WordBreakSimple().wordBreak(word));
    }
}
