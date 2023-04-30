package com.datastructure.numbers;

public class NumbersConvertUtil {

    public static void main(String[] args) {
        convertBinaryToDecimal(Integer.parseInt(convertDecimalToBinary(9)));
    }

    public static String convertDecimalToBinary(int num) {
        StringBuilder binary = new StringBuilder();
        while (num > 0) {
            binary.append(num%2);
            num = num/2;
        }
        String result = binary.reverse().toString();
        System.out.println("binary "+result);
        return  result;
    }

    public static int convertBinaryToDecimal(int num) {
        int decimal = 0;
        int i = 0;
        while (num > 0) {
            int temp = num % 10;
            decimal += temp * Math.pow(2, i++);
            num = num /10;
        }
        System.out.println("Decimal "+decimal);
        return decimal;
    }
}
