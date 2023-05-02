package com.machinecoding.distributedQueue.model;

import com.machinecoding.distributedQueue.service.Topic;

public class Producer {
    String name;

    public Producer(String name) {
        this.name = name;
    }

    public void produce(Topic topic, String message) {
        topic.publish(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
