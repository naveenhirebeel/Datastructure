package com.machinecoding.distributedQueue.model;

public class Consumer {

    String name;

    public Consumer(String name) {
        this.name = name;
    }
    public void onMessage(String message) {
        String msg = "%s received %s";
        System.out.println(String.format(msg, this.name, message));
    }
}
