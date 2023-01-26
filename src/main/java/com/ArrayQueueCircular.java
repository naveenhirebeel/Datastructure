package com;

public class ArrayQueueCircular {

    int capacity = 10;
    int size = 0;

    int front = -1;
    int rear = -1;

    int[] queue = new int[capacity];

    public boolean enqueue(int n) {
        if(hasNoCapacity()) {
            print();
            throw new RuntimeException("Queue is full");
        }
        front = (front + 1) % capacity;
        queue[front] = n;
        size += 1;
        return true;
    }

    public int dequeu() {
        if(isEmpty(queue)) {
            print();
            throw new RuntimeException("Queue is empty");
        } else {
            rear = (rear + 1) % capacity;
            int value = queue[rear];
            size -= 1;
            queue[rear] = 0;
            return value;
        }
    }

    public void print() {
        System.out.println("Size : "+ size);
        System.out.println("Front : "+ front);
        System.out.println("Rear : "+ rear);
        for(int i : queue) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
    }
    private boolean isEmpty(int[] queue) {
        return size == 0;
    }

    private boolean hasCapacity() {
        return size < capacity;
    }

    private boolean hasNoCapacity() {
        return !hasCapacity();
    }


    public static void main(String[] args) {
        ArrayQueueCircular q = new ArrayQueueCircular();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.print();
        System.out.println(q.dequeu());
        q.print();
        q.enqueue(4);
        q.enqueue(5);
        q.print();
        System.out.println(q.dequeu());
        q.print();
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);
        q.enqueue(10);
        q.enqueue(11);
        q.enqueue(12);
//        q.enqueue(13);
        q.print();
        System.out.println(q.dequeu());
        q.print();


    }
}
