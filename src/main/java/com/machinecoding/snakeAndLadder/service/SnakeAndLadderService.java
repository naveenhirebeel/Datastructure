package com.machinecoding.snakeAndLadder.service;

import com.machinecoding.snakeAndLadder.model.Player;

import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class SnakeAndLadderService {
    private Random diceRoll;

    public SnakeAndLadderService() {
        diceRoll = new Random();
    }
    private int rollDice() {
        return diceRoll.nextInt(6)+1;
    }

    private static int getSnakeOrLadderPosition(int nextPosition, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        if(snakes.get(nextPosition) != null) {
            return snakes.get(nextPosition);
        } else if(ladders.get(nextPosition) != null) {
            return ladders.get(nextPosition);
        }
        return nextPosition;
    }

    public void playGame(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, Queue<Player> players) {
        Player currentPlayer = players.peek();
        while(true) {
            currentPlayer = players.poll();
            int currentPosition = currentPlayer.getPosition();
            int diceRolledValue = rollDice();
            int nextPosition = getSnakeOrLadderPosition(diceRolledValue + currentPosition, snakes, ladders);
            //        String message = "<player_name> rolled a <dice_value> and moved from <initial_position> to <final_position>";
            String message = "%s rolled a %s and moved from %s to %s";
            if(nextPosition == 100) {
                currentPlayer.setPosition(nextPosition);
                System.out.println(String.format(message, currentPlayer.getName(), diceRolledValue, currentPosition, nextPosition));
                System.out.println(String.format("Player %s won", currentPlayer.getName()));
                break;
            } else if(nextPosition > 100) {
                System.out.println(String.format(message, currentPlayer.getName(), diceRolledValue, currentPosition, currentPosition));
                players.add(currentPlayer);
                continue;
            }
            currentPlayer.setPosition(nextPosition);
            players.add(currentPlayer);
            System.out.println(String.format(message, currentPlayer.getName(), diceRolledValue, currentPosition, nextPosition));
        }
    }
}
