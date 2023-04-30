package com.machinecoding.expenseSplit;

import com.machinecoding.expenseSplit.model.User;
import com.machinecoding.expenseSplit.service.SplitService;

import java.util.*;

public class ExpenseSplit {

    public static void main(String[] args) {

        User u1 = new User("u1", "User1", "u1@gmail.com", "11");
        User u2 = new User("u2", "User2", "u2@gmail.com", "22");
        User u3 = new User("u3", "User3", "u3@gmail.com", "33");
        User u4 = new User("u4", "User4", "u4@gmail.com", "44");
        User u5 = new User("savi", "savi", "savi@gmail.com", "44");
        User u6 = new User("ummi", "ummi", "ummi@gmail.com", "44");
        User u7 = new User("nidhi", "nidhi", "u4@gmail.com", "44");

        Map<String, User> userMap = new HashMap<>();
        userMap.put("u1", u1);
        userMap.put("u2", u2);
        userMap.put("u3", u3);
        userMap.put("u4", u4);
        userMap.put("savi", u5);
        userMap.put("ummi", u6);
        userMap.put("nidhi", u7);

        SplitService splitService = new SplitService();
        splitService.splitExpense(userMap);

        /*Scanner sc = new Scanner(System.in);
        System.out.println("Enter expense details like, payee_name, amount, contributors, list of users, type, " +
                "ifExactThenThereContribution");
        String payee = sc.next();
        Double paidAmount = sc.nextDouble();
        int contributors = sc.nextInt();
        List<UserOld> oweList = new ArrayList<>();
        for(int i = 0; i < contributors; i++) {
            UserOld owePerson = new UserOld(sc.next(), payee, paidAmount, 0d);
            oweList.add(owePerson);
        }
        String typeOfPayment = sc.next();
        String message = "%s owes %s: %s";

        if("EXACT".equals(typeOfPayment)) {
            Double owedAmount = paidAmount / (contributors + 1);
            for(int i  = 0; i<contributors; i++) {
                UserOld user = oweList.get(i);
                user.setOwedAmount(owedAmount);
            }
        }*/

    }
}

