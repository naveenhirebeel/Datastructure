package com.machinecoding.Trello.service;

import com.machinecoding.Trello.model.Board;
import com.machinecoding.Trello.model.Command;
import com.machinecoding.Trello.util.Util;

import java.util.*;

import static com.machinecoding.Trello.model.Command.BOARD;

public class TrelloService {

    private Scanner sc;
    private BoardService boardService;
    private Map<String, Board> boardsMap;


    public TrelloService() {
        this.sc = Util.getScanner();
        boardService = new BoardService();
        boardsMap = new HashMap<>();
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public static void main(String[] args) {
        new TrelloService().startTrello();
    }

    public void startTrello() {
        System.out.println("Trello is up!");
        while(true) {
            System.out.println("Enter Command");
            String[] inputs = sc.nextLine().split(" ");
            Command command = getCommand(inputs[0]);
            switch(command) {
                case BOARD :
                    processBoard(inputs);
                    break;
                case BOARD_LIST:
                    ProcessList();
                    break;
                case CARD:
                    processCard();
                    break;
                case SHOW:
                    show(inputs);
                    break;
                case QUIT:
                    System.out.println("Shutting down Trello!");
                    System.exit(0);
                    return;
                case ERROR:
                    System.out.println("Invalid input, try again!");
            }
        }
    }

    private void show(String[] inputs) {
        if(inputs.length == 1) {
            boardsMap.keySet();
            if(!boardsMap.isEmpty()) {
                for(Map.Entry entry : boardsMap.entrySet()) {
                    System.out.println(entry.getValue());
                }
            } else {
                System.out.println("No Boards!");
            }
        } else if(inputs.length == 3) {
            Command command = getCommand(inputs[1]);
            switch (command) {
                case BOARD :
                    boardService.showBoard(boardsMap, inputs[2]);
                    break;
            }
        } else {
            System.out.println("Invalid SHOW command");
        }
    }

    private void processCard() {
    }

    private void ProcessList() {
    }

    private void processBoard(String[] inputs) {
        String type = inputs[1].toUpperCase();
        switch(type) {
            case Board.CREATE:
                Board board = boardService.createBoard(inputs[2]);
                boardsMap.put(board.getId(), board);
                break;
            default:
                String id = inputs[1];
                String action = inputs[2].toUpperCase();
                switch (action) {
                    case "NAME":
                        boardService.updateName(boardsMap, id, inputs[3]);
                        break;
                    case "PRIVACY":
                        boardService.updatePrivacy(boardsMap, id, inputs[3]);
                        break;
                }
                System.out.println("Invalid action");
                return;
        }
    }

    private Command getCommand(String ct) {
        try {
            return Command.valueOf(ct.toUpperCase());
        } catch(Exception exception) {
            return Command.ERROR;
        }
    }
}
