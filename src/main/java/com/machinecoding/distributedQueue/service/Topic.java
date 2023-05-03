package com.machinecoding.distributedQueue.service;

import com.machinecoding.distributedQueue.model.Consumer;
import com.machinecoding.distributedQueue.model.Producer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Topic<T> {

    String name;

    Queue<T> queue = new LinkedList<T>();

    List<Consumer> consumers = new ArrayList<>();

    public void publish(T message) {
        queue.add(message);

        CompletableFuture.runAsync(() -> forwardToSubscribers(queue))
                .thenRun(() -> {
                    System.out.println("submitted to subscribed consumers");
                })
                .exceptionally(ex -> {
                    System.out.println("Error");
                    return null;
                }).join();
    }

    public void forwardToSubscribers(Queue queue) {
        Object message = queue.poll();
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

    public Queue<T> getQueue() {
        return queue;
    }

    public void setQueue(Queue<T> queue) {
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
