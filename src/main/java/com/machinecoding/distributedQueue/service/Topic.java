package com.machinecoding.distributedQueue.service;

import com.machinecoding.distributedQueue.model.Consumer;
import com.machinecoding.distributedQueue.model.Producer;

import java.util.*;

public class Topic {

    String name;

    Queue<String> queue = new LinkedList<>();

    List<Consumer> consumers = new ArrayList<>();

    public void publish(String message) {
        queue.add(message);
        forwardToSubscribers(message);
    }

    public void forwardToSubscribers(String message) {
        for(Consumer consumer : consumers) {
            consumer.onMessage(message);
        }
    }

    public void subscribe(Consumer... consumers) {
        this.consumers.addAll(Arrays.asList(consumers));
    }

    public Topic(String name) {
        this.name = name;
    }

    public Queue<String> getQueue() {
        return queue;
    }

    public void setQueue(Queue<String> queue) {
        this.queue = queue;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
