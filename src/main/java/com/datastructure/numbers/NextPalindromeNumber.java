package com.datastructure.numbers;//1245
// 12 45
//        1221 < 1245
//        13 - 1331
//        //1221
//        //1331
//
//        //1331 - 1245 = 86
//
//
//
//        12345
//        12321 < 12345
//        13331
//
//        99
//        101
//        temp = 0
//        101 < 102
//        Ans - 101

import java.math.BigInteger;

/*
Write a function that takes an integer as an input and return the next smallest number that is a palindrome.

Example Input: 1000
Example Output: 1001

Example Input: 1200
Example Output: 1221
*/
public class NextPalindromeNumber {
    public static void main(String[] args) {
        System.out.println("Hello, World");

//        BigInteger input = new BigInteger("1200");
//        BigInteger input = new BigInteger("1245");
//        BigInteger input = new BigInteger("12345");
        printNextPalindrome(new BigInteger("1000"));
        printNextPalindrome(new BigInteger("1200"));
        printNextPalindrome(new BigInteger("1245"));
        printNextPalindrome(new BigInteger("12345"));
    }

    private static void printNextPalindrome(BigInteger input) {
        String str = input.toString();
        String firstHalf, secondHalf ;
        if(str.length() % 2 == 0) {
            firstHalf = str.substring(0, str.length()/2);
            secondHalf = getNextHalf(firstHalf, true);

            String finalString = firstHalf + secondHalf;
            BigInteger fn = new BigInteger(finalString);

            if(fn.compareTo(input)>0) {
                System.out.println(fn.toString() + " " + "Is new Pallindrome of "+input );
            } else {
                String nfh = new BigInteger(firstHalf.toString()).add(new BigInteger("1")).toString();
                String nsh = getNextHalf(nfh, true);
                String nfn = nfh+nsh;
                System.out.println(nfn+" is new Pallindrome of "+input);
            }
        } else {
            firstHalf = str.substring(0, str.length()/2+1);
            secondHalf = getNextHalf(firstHalf, false);
            String finalString = firstHalf + secondHalf;
            BigInteger fn = new BigInteger(finalString);

            if(fn.compareTo(input)>0) {
                System.out.println(fn.toString() + " " + "Is new Pallindrome of "+input );
            } else {
                String nfh = new BigInteger(firstHalf).add(new BigInteger("1")).toString();
                String nsh = getNextHalf(nfh, false);
                String nfn = nfh+nsh;
                System.out.println(nfn+" is new Pallindrome of "+input);
            }
        }
    }

    private static String getNextHalf(String str, Boolean even) {
        int length;
        if(even)
            length = str.length();
        else
            length = str.length() - 1;
        return new StringBuffer(str.substring(0, length)).reverse().toString();
    }

}

