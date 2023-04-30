package com.machinecoding.snakeAndLadder;

import com.machinecoding.snakeAndLadder.model.Player;
import com.machinecoding.snakeAndLadder.service.SnakeAndLadderService;

import java.util.*;

public class SnakeAndLadder {
    static Map<Integer, Integer> snakes = new HashMap<>();
    static Map<Integer, Integer> ladders = new HashMap<>();
    static Queue<Player> players = new LinkedList<>();

    private static Random diceRoll = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("How many snakes");
        int numOfSnakes = sc.nextInt();
        //System.out.println("Input every snake head and tail");
        for(int i = 0; i <numOfSnakes; i++) {
            int head = sc.nextInt();
            int tail = sc.nextInt();
           snakes.put(head, tail);
        }
        //System.out.println(snakes);

        //System.out.println("How many ladders");
        int numOfLadders = sc.nextInt();
        //System.out.println("Input every ladder start and end position");
        for(int i = 0; i <numOfLadders; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            ladders.put(start, end);
        }
        //System.out.println(ladders);

        //System.out.println("How many players");
        int numOfPlayers = sc.nextInt();
        //System.out.println(String.format("Input %s players id & name one by one", numOfPlayers));
        for(int i = 0; i <numOfPlayers; i++) {
            String name = sc.next();
            players.add(new Player(1+i, name, 0));
        }
        //System.out.println(players);
         new SnakeAndLadderService().playGame(snakes, ladders, players);
    }
}

