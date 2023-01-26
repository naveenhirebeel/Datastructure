package com;

import com.oops.MySingleton;
import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String str = "APPLEE";
        switch (str) {
            case "APPLE" :
                System.out.println("This is Apple");
                break;
            case "ORANGE" :
                System.out.println("This is Orange");
                break;
            case "BANANA" :
                System.out.println("This is Banana");
                break;
            default:
                System.out.println("This is default");
        }


//        List<String> sampleList = Arrays.asList("Java", null);
//        String resultString = sampleList.stream()
//                .map((@NotNull String x) -> x.toUpperCase())
//                .collect(Collectors.joining(", "));
//        System.out.println(resultString);

//        List<Integer> list1 = Arrays.asList(1,2,4);
//        List<Integer> list2 = Arrays.asList(1,2,3,4);
//`
//        int r1 = list1.stream().reduce(0, (a, b) -> a ^ b);
//        int r2 = list2.stream().reduce(0, (a, b) -> a ^ b);
//        System.out.println(r1 ^ r2);

//        String str = "  ab  cd ";
//        System.out.println(str);
//        System.out.println(str.trim());
//        Consumer c = s -> System.out.println(s);
//        Arrays.asList(1,2,3,4).stream().forEach(c);


//        MySingleton o1 = MySingleton.getSingleton();
//        MySingleton o2 = MySingleton.getSingleton();
//
//        System.out.println(o1 == o2);

    }

}
