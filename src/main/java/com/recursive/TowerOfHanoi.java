package com.recursive;

public class TowerOfHanoi {

    public static void main(String[] args) {
        move(3, "a", "c", "b");
    }

    private static void move(int n, String source, String dest, String mediator) {
        if(n == 1) {
            System.out.println(
                    String.format("Moving disc n: %s, from tower: %s to %s", n, source, dest)
            );
        } else {
            move(n-1, source, mediator, dest);
            System.out.println(String.format("Moving disc n: %s, from tower: %s to %s", n, source, dest));
            move(n-1, mediator, dest, source);
        }
    }
}
