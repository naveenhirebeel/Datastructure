package com.dynamicprogramming;

import java.util.Arrays;
import java.util.List;

public class Wordbreakproblem {

	List<String> dictionary = Arrays.asList("i", "like", "javaa");
	
	public static void main(String[] args) {
		String word = "ilikejava";
		System.out.println(new Wordbreakproblem().wordBreak(word, 0, 1));
	}
	
	boolean wordBreak(String word, int i, int j){
		
		boolean flag = true;
		String subWord = word.substring(i, j);
		if(dictionary.contains(subWord)) {
			System.out.println(subWord);
			if(j < word.length()) {
				i = j;
				j = j+1;
				flag =  wordBreak(word, i, j);
			}
		} else {
			j++;
			if(j >= word.length()) {
				flag =  false;
			} else {
				flag =  wordBreak(word, i, j);
			}
		}
		return flag;
	}
}

class TrieDataStructure {
	
	
	public static void main(String[] args) {
		
		System.out.println('b' - 'a');
	}
}