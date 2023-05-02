package com.machinecoding.distributedQueue;

import com.machinecoding.distributedQueue.model.Consumer;
import com.machinecoding.distributedQueue.model.Producer;
import com.machinecoding.distributedQueue.service.Topic;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class DistributedQueue {

    private static final String TOPIC_1 = "Topic 1";
    private static final String TOPIC_2 = "Topic 2";
    private static final String PRODUCER_1 = "Producer 1";
    private static final String PRODUCER_2 = "Producer 2";
    private static final String CONSUMER_1 = "Consumer 1";
    private static final String CONSUMER_2 = "Consumer 2";
    private static final String CONSUMER_3 = "Consumer 3";
    private static final String CONSUMER_4 = "Consumer 4";
    private static final String CONSUMER_5 = "Consumer 5";

    private List<Topic> topics;
    private Map<String, Topic> topicMap;
    private Map<String, Producer> producerMap;

    public DistributedQueue(List<Topic> topics, List<Producer> producers) {
        this.topics = topics;
        topicMap = new HashMap<>();
        for(Topic topic : topics) {
            topicMap.put(topic.getName(), topic);
        }
        producerMap = new HashMap<>();
        for(Producer producer : producers) {
            producerMap.put(producer.getName(), producer);
        }
    }

    public void publishMessage(String producerName, String topicName, String message) {
        Optional<Producer> producer = Optional.ofNullable(producerMap.get(producerName));
        if(!producer.isPresent()) {
          throw new RuntimeException("Invalid Producer "+producerName);
        }

        Optional<Topic> topic = Optional.ofNullable(topicMap.get(topicName));
        if(!topic.isPresent()) {
            throw new RuntimeException("Invalid Topic "+producerName);
        }

        producer.get().produce(topic.get(), message);
    }

    public static void main(String[] args) {
        Topic topic1 = new Topic(TOPIC_1);
        Topic topic2 = new Topic(TOPIC_2);

        Producer p1 = new Producer(PRODUCER_1);
        Producer p2 = new Producer(PRODUCER_2);

        Consumer c1 = new Consumer(CONSUMER_1);
        Consumer c2 = new Consumer(CONSUMER_2);
        Consumer c3 = new Consumer(CONSUMER_3);
        Consumer c4 = new Consumer(CONSUMER_4);
        Consumer c5 = new Consumer(CONSUMER_5);

        topic1.subscribe(c1, c2, c3, c4, c5);
        topic2.subscribe(c1, c3, c4);

        DistributedQueue queue = new DistributedQueue(Arrays.asList(topic1, topic2), Arrays.asList(p1, p2));
        queue.publishMessage(PRODUCER_1, TOPIC_1, "Message 1" );
        queue.publishMessage(PRODUCER_1, TOPIC_1, "Message 2" );
        queue.publishMessage(PRODUCER_2, TOPIC_1, "Message 3" );
        queue.publishMessage(PRODUCER_1, TOPIC_2, "Message 4" );
        queue.publishMessage(PRODUCER_2, TOPIC_2, "Message 5" );

    }


}
