package com.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class WordLadder {

    public static void main(String[] args) {
        String startWord = "der", targetWord = "dfs";
        String[] wordList = {"des", "der", "dfr", "dgt", "dfs"};

        WordLadder wl = new WordLadder();
        int sl = wl.wordLadderLength(startWord, targetWord, wordList);
        System.out.println(sl);
    }
    class Node {
        String word;
        int depth;
        Node(String word, int depth) {
            this.word = word;
            this.depth = depth;
        }
    }

    public int wordLadderLength(String startWord, String targetWord, String[] wordList) {
        // Creating a queue ds of type {word,transitions to reach ‘word’}.
        Queue<Node> q = new LinkedList< >();

        // BFS traversal with pushing values in queue
        // when after a transformation, a word is found in wordList.
        q.add(new Node(startWord, 1));

        // Push all values of wordList into a set
        // to make deletion from it easier and in less time complexity.
        Set< String > st = new HashSet< String >();
        for(String word : wordList) {
            st.add(word);
        }
        st.remove(startWord);

        while (!q.isEmpty()) {
            Node node = q.remove();
            String word = node.word;
            int depth = node.depth;

            // we return the steps as soon as
            // the first occurence of targetWord is found.
            if (word.equals(targetWord) == true)
                return depth;

            // Now, replace each character of ‘word’ with char
            // from a-z then check if ‘word’ exists in wordList.
            for (int i = 0; i < word.length(); i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    char replacedCharArray[] = word.toCharArray();
                    replacedCharArray[i] = ch;
                    String replacedWord = new String(replacedCharArray);

                    // check if it exists in the set and push it in the queue.
                    if (st.contains(replacedWord)) {
                        st.remove(replacedWord);
                        q.add(new Node(replacedWord, depth + 1));
                    }
                }
            }
        }
        // If there is no transformation sequence possible
        return 0;
    }
}
