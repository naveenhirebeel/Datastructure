package com.dynamicprogramming;

import java.util.*;
class EditDistance {

    //Recursive approach
    static int editDistanceRec(String s1, String s2, int m, int n) {
        if(m==0)
            return n;
        if(n==0)
            return m;
        if(s1.charAt(m-1) == s2.charAt(n-1))
            return editDistance(s1, s2, m-1, n-1);
        return 1 +Math.min(editDistanceRec(s1, s2, m, n-1),
                Math.min(editDistanceRec(s1, s2, m-1, n), editDistanceRec(s1, s2, m-1, n-1)));
    }

    //Bottum up approach
    static int editDistance(String s1, String s2, int m, int n) {
        int ed[][] = new int[m+1][n+1];
        for(int i = 0; i <=m; i++) {
            for(int j = 0; j<=n; j++) {
                if(i==0) {
                    ed[i][j] = j;
                } else if(j==0) {
                    ed[i][j] = i;
                } else if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    ed[i][j] = ed[i-1][j-1];
                } else {
                    ed[i][j] = 1 + Math.min(ed[i][j-1], Math.min(ed[i-1][j], ed[i-1][j-1]));
                }
            }
        }
        return ed[m][n];
    }



    public static void main(String[] args)
    {
//        String str1 = "voldemort";
//        String str2 = "dumbledore";
        String str1 = "cut";
        String str2 = "cup";
        int n = str1.length(), m = str2.length();
        System.out.println(editDistance(str1, str2, m, n));
        System.out.println(editDistanceRec(str1, str2, m, n));
    }
}