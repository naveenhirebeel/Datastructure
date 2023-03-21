package com.stringarray;

public class PallindromicPartitions {

    public static void main(String[] args) {
        String str = "gadag";
//        String str = "abba";
        int count = 0;
        if(str.length() % 2 == 0) {
            count+=  str.length();
            for(int i = 0; i < str.length(); i++){
                for(int j = 0; i+j+1 < str.length() && i-j >= 0; j++) {
                    if(str.charAt(i+j+1) == str.charAt(i-j)) {
                        count++;
                        System.out.println(str.substring(i-j, i+j+1+1));
                    } else {
                        break;
                    }
                }
            }
        } else {
            for(int i = 0; i < str.length(); i++) {
                for(int j = 0; i+j < str.length() && i-j >= 0; j++) {
                    if(str.charAt(i+j) == str.charAt(i-j)) {
                        count++;
                        System.out.println(str.substring(i-j, i+j+1));
                    } else {
                        break;
                    }
                }
            }
        }
        System.out.println("Count "+count);
    }
}
