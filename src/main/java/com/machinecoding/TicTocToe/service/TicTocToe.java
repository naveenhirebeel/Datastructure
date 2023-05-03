package com.machinecoding.TicTocToe.service;

import com.machinecoding.TicTocToe.model.Player;

import java.util.*;

public class TicTocToe {

    private Scanner sc = new Scanner(System.in);
    private Queue<Player> playersQueue = new LinkedList<>();

    String[][] board;
    public static void main(String[] args) {
        TicTocToe game = new TicTocToe();
        game.setUp();
        game.play();
    }

    private void setUp() {
        System.out.println("Enter Players, name and piece");
        String[] p1 = sc.nextLine().split(" ");
        String[] p2 = sc.nextLine().split(" ");

        Player player1 = new Player(p1[0], p1[1]);
        Player player2 = new Player(p2[0], p2[1]);
        playersQueue.add(player1);
        playersQueue.add(player2);
        System.out.println(playersQueue);
        System.out.println("Let's Play!");
        System.out.println();

        board = new String[3][3];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = null;
            }
        }

    }
    public void play() {
        Map<Integer, Node2D> poistionMap = buildPositionsToBoard();
        printBoard();

        while(!playersQueue.isEmpty()) {
            Player player = playersQueue.peek();

            gameContinue();
            System.out.println();
            System.out.println(String.format("%s, Enter Position you select", player.getName()));
            Node2D position = poistionMap.get(sc.nextInt());
            if(validPositionSelected(player, position)) {
                board[position.i][position.j] = player.getPiece();
                printBoard();

                playersQueue.remove(player);
                playersQueue.add(player);

                if(boardCompleted(player, position)) {
                    System.out.println(String.format("Player %s won", player.getName()));
                    break;
                }
            } else {
                System.out.println("Invalid Position selected, key in again");
            }
        }
    }

    private boolean boardCompleted(Player player, Node2D node) {

        // Horizontal check
        Boolean horizontal = true;
        for(int j = 0; j < 3; j++) {
            if(player.getPiece().equals(board[node.i][j]))
                continue;
            else {
                horizontal = false;
                break;
            }
        }

        Boolean vertical = true;
        for(int i = 0; i <= 2; i++) {
            if(player.getPiece().equals(board[i][node.j]))
                continue;
            else {
                vertical = false;
                break;
            }
        }

        Boolean ld = true;
        for(int i = 0; i <= 2; i++) {
            if(player.getPiece().equals(board[i][i]))
                continue;
            else {
                ld = false;
                break;
            }
        }

        Boolean rd = true;
        for(int i = 2; i >= 0; i--) {
            if(player.getPiece().equals(board[2-i][i]))
                continue;
            else {
                rd = false;
                break;
            }
        }
        return horizontal || vertical || ld || rd;
    }

    private void gameContinue() {
        if(boardFul()) {
            System.out.println("Game Over! restart");
            System.exit(0);
        }
    }

    private boolean boardFul() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validPositionSelected(Player player, Node2D node) {
        return board[node.i][node.j] == null;
    }

    private Map<Integer, Node2D> buildPositionsToBoard() {
        Map<Integer, Node2D> map = new HashMap<>();
        map.put(1, new Node2D(0,0));
        map.put(2, new Node2D(0,1));
        map.put(3, new Node2D(0,2));
        map.put(4, new Node2D(1,0));
        map.put(5, new Node2D(1,1));
        map.put(6, new Node2D(1,2));
        map.put(7, new Node2D(2,0));
        map.put(8, new Node2D(2,1));
        map.put(9, new Node2D(2,2));
        return map;
    }

    class Node2D {
        public int i;
        public int j;

        public Node2D(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }


    private void printBoard() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                String placeHolder = board[i][j];
                placeHolder = placeHolder == null ? " - " : " "+placeHolder+" ";
                System.out.print("|"+ placeHolder);
            }
            System.out.print("|\n");
        }
    }

}
