package com.datastructure;

public class Conversion {

    public static void main(String[] args) {
        String binary = numberToBinary(5);
        System.out.println("Binary "+ binary);
        int decimal = binaryToDecimal(Integer.valueOf(binary));
        System.out.println("Decimal "+decimal);
    }

    private static String numberToBinary(int num) {
        System.out.println("Num: "+num);
        StringBuffer binary = new StringBuffer("");
        while (num > 0) {
            binary.append(num % 2);
            num = num / 2;
        }
        return binary.reverse().toString();
    }

    private static int binaryToDecimal(int n) {
        int decimal = 0;
        int i = 0;
        while ( n > 0) {
            int reminder = n % 10;
            n = n / 10;
            decimal += reminder * Math.pow(2, i);
            i++;
        }
        return decimal;
    }


}

