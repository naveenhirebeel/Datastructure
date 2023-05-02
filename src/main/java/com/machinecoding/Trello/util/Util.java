package com.machinecoding.Trello.util;

import com.machinecoding.Trello.model.Command;
import com.machinecoding.Trello.model.Privacy;

import java.util.Scanner;

public class Util {
    private static Scanner sc = new Scanner(System.in);

    public static Scanner getScanner() {
        return sc;
    }

    public static Command getCommand(String ct) {
        try {
            return Command.valueOf(ct.toUpperCase());
        } catch(Exception exception) {
            return Command.ERROR;
        }
    }

    public static Privacy getPrivacy(String privacy) {
        try {
            return Privacy.valueOf(privacy.toUpperCase());
        } catch(Exception exception) {
            return Privacy.ERROR;
        }
    }
}
