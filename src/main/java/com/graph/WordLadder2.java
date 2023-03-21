package com.graph;

import java.util.*;

public class WordLadder2 {

    public static void main(String[] args) {
        String startWord = "des", targetWord = "dfs";
        String[] wordList = {
                "des", "der", "dfr", "dgt", "dfs"
        };
        List<String> wl = Arrays.asList("des", "der", "dfr", "dgt", "dfs");
        System.out.println(new WordLadder2().findLadders(startWord, startWord, wl));
    }
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        List<List<String>> res = new ArrayList<>();
        if (!wordSet.contains(endWord)) return res;

        Map<String, List<String>> map = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();

        bfs(beginWord, endWord, wordSet, map, distance);
        System.out.println(map);
        System.out.println(distance);

//        dfs(beginWord, endWord, map, distance, res, new ArrayList<>());

        return res;
    }

//    private void dfs(String curr, String end, Map<String, List<String>> map, Map<String, Integer> distance, List<List<String>> res, List<String> path) {
//        path.add(curr);
//        if (curr.equals(end)) {
//            res.add(new ArrayList<>(path));
//        } else {
//            for (String next : map.get(curr)) {
//                if (distance.get(next) == distance.get(curr) + 1) {
//                    dfs(next, end, map, distance, res, path);
//                }
//            }
//        }
//        path.remove(path.size() - 1);
//    }

    private void bfs(String begin, String end, Set<String> wordSet, Map<String, List<String>> map, Map<String, Integer> distance) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(begin);
        distance.put(begin, 0);

        for (String word : wordSet) {
            map.put(word, new ArrayList<>());
        }

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currDist = distance.get(curr);

            List<String> nextWords = getNextWords(curr, wordSet);
            for (String next : nextWords) {
                map.get(next).add(curr);
                if (!distance.containsKey(next)) {
                    distance.put(next, currDist + 1);
                    queue.offer(next);
                }
            }
        }
    }

    private List<String> getNextWords(String word, Set<String> wordSet) {
        List<String> res = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == original) continue;
                chars[i] = c;
                String newWord = new String(chars);
                if (wordSet.contains(newWord)) {
                    res.add(newWord);
                }
            }
            chars[i] = original;
        }
        return res;
    }
}
