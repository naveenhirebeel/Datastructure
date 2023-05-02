package com.machinecoding.Trello.service;

import com.machinecoding.Trello.model.Board;
import com.machinecoding.Trello.model.Privacy;
import com.machinecoding.Trello.util.Util;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class BoardService {

    public Board createBoard(String name) {
        System.out.println("Board created!");
        return new Board(UUID.randomUUID().toString(), name);
    }

    public void showBoard(Map<String, Board> boardsMap, String id) {
        System.out.println(boardsMap.get(id));
    }

    public void updateName(Map<String, Board> boardsMap, String id, String name) {
        Board board = boardsMap.get(id);
        if(board == null) {
            board.setName(name);
        } else {
            System.out.println("Invalid board id");
        }
    }

    public void updatePrivacy(Map<String, Board> boardsMap, String id, String privacy) {
        Board board = boardsMap.get(id);
        Privacy p = Util.getPrivacy(privacy);

        if(board == null) {
            if(p != Privacy.ERROR) {
                board.setPrivacy(p);
            } else {
                System.out.println("Invalid privacy");

            }
        } else {
            System.out.println("Invalid board id");
        }
    }
}
