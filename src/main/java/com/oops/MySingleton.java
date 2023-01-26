package com.oops;

public class MySingleton {

//    private static MySingleton singleton = new MySingleton();

    private static class SingletonHelper {
        private static MySingleton mySingleton = new MySingleton();
    }

    private MySingleton() {

    }

    public static MySingleton getSingleton() {
        return SingletonHelper.mySingleton;
    }
}

