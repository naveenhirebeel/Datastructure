package com.machinecoding.expenseSplit.service;

import com.machinecoding.expenseSplit.model.Command;
import com.machinecoding.expenseSplit.model.ExpenseType;
import com.machinecoding.expenseSplit.model.User;

import java.util.*;

public class SplitService {
    Scanner sc;
    public SplitService() {
        sc = new Scanner(System.in);
    }

    Scanner getScanner() {
        return sc;
    }

    public void splitExpense(Map<String, User> userMap) {
        while(true) {
            System.out.println("Enter command");
            Scanner sc = getScanner();
            String commandType = sc.next();
            Command c;
            try {
                c = Command.valueOf(commandType.toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("Wrong Input");
            }

            switch(c) {
                case SHOW:
                    this.showExpense(userMap);
                    break;
                case EXPENSE:
                    this.executeSplitExpense(userMap);
                    break;
                case QUIT:
                    System.out.println("Default");
                    return;
            }
        }
    }

    public void showExpense(Map<String, User> userMap) {
        Scanner sc = getScanner();
        String payeeId = sc.next();
        if(payeeId.equals("all")) {
            Set<String> keys = userMap.keySet();
            for(String key : keys){
                User user = userMap.get(key);
                Set<String> owerKeys = user.getOwedMap().keySet();
                String message = "%s owes %s: %s";
                for(String owerKey : owerKeys) {
                    if(user.getOwedMap().get(owerKey) > 0) {
                        System.out.println(String.format(message, owerKey, key, user.getOwedMap().get(owerKey)));
                    }
                }
            }
        } else {
            User user = userMap.get(payeeId);
            Set<String> keys = user.getOwedMap().keySet();
            String message = "%s owes %s: %s";
            for(String key : keys) {
                System.out.println(String.format(message, key, payeeId, user.getOwedMap().get(key)));
            }
        }
    }

    public void executeSplitExpense(Map<String, User> userMap) {
        System.out.println("Enter expense");
        Scanner sc = getScanner();
        String payee = sc.next();
        Double paidAmount = sc.nextDouble();
        Integer owers = sc.nextInt();
        List<String> owerIds = new ArrayList<>();
        for(int i = 0; i < owers; i++) {
            String owerId = sc.next();
            owerIds.add(owerId);
        }
        String expenseType = sc.next();
        try {
            ExpenseType  et = ExpenseType.valueOf(expenseType.toUpperCase());
            switch (et) {
                case EQUAL:
                    equalSplit(payee, paidAmount, userMap, owerIds);
                    break;
                case EXACT:
                    exactSplit(payee, paidAmount, userMap, owerIds);
                    break;
                case PERCENT:
                    percentSplit(payee, paidAmount, userMap, owerIds);
                    break;
                default:
                    System.out.println("Not supported expense type");
            }
        } catch (Exception ex) {
            System.out.println("Wrong expenseType");
        }
        System.out.println("Executed expense");
    }

    public void equalSplit(String payee, Double paidAmount, Map<String, User> userMap, List<String> owerIds) {
        User payeeUser = userMap.get(payee);
        Double perHeadToContribute = paidAmount / owerIds.size();

        for(String owerId : owerIds) {
            Double existingOwedAmount =
                    Optional.ofNullable(payeeUser.getOwedMap()).map(map -> map.get(owerId)).get();
            Double newOwedAmount = existingOwedAmount+ perHeadToContribute;

            if(!payee.equals(owerId)) {
                User owerUser = userMap.get(owerId);
                Double owerOwedToPayee =  perHeadToContribute;
                Double owedUserExistingOwedAmount =
                        Optional.ofNullable(payeeUser.getOwedMap()).map(map -> map.get(owerId)).orElse(0d);
                Double owerOwedNewAmount = owedUserExistingOwedAmount + owerOwedToPayee;

                Double payeeOwedToUser =
                        Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
                if(Double.compare(payeeOwedToUser, owerOwedNewAmount) > 0) {
                    owerUser.getOwedMap().put(payee, payeeOwedToUser - owerOwedNewAmount);
                    payeeUser.getOwedMap().put(owerId, 0d);
                } else {
                    owerUser.getOwedMap().put(payee, 0d);
                    payeeUser.getOwedMap().put(owerId, owerOwedNewAmount - payeeOwedToUser);
                }
            }
        }
    }

    public void exactSplit(String payee, Double paidAmount, Map<String, User> userMap, List<String> owerIds) {
        Double perHeadToContribute = paidAmount /owerIds.size();
        Scanner sc = getScanner();
        List<Double> owedUsersPaidAmounts = new ArrayList<>();
        Double allOwerPaidAmount = 0d;
        for(String owerId: owerIds) {
            Double owerPaidAmount = sc.nextDouble();
            owedUsersPaidAmounts.add(owerPaidAmount);
            allOwerPaidAmount += owerPaidAmount;
        }
        if(!paidAmount.equals(allOwerPaidAmount)) {
            System.out.println("Paid amount does not match with contributed amount by contributors!");
            return;
        }
        User payeeUser = userMap.get(payee);
        for(int i = 0; i < owerIds.size(); i++) {
            String owerId = owerIds.get(i);
            if(!payee.equals(owerId)) {
                User owerUser = userMap.get(owerId);
                Double owedUserPaidAmount = owedUsersPaidAmounts.get(i);
                if(perHeadToContribute < owedUserPaidAmount ) {
                    //Case : Contributor paid more than his actual contribution share amount
                    // Here Payee should payee to contributor as he paid more
                    // than his contribution. Payee in a way receive money from other contributor.
                    Double payeeOwedToOwer = owedUserPaidAmount - perHeadToContribute;
                    Double payeeUserExistingOwedAmount =
                            Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
                    Double payeeOwedNewAmount = payeeUserExistingOwedAmount + payeeOwedToOwer;
                    owerUser.getOwedMap().put(payee, payeeOwedNewAmount);
                } else {
                    //Case : Contributor paid no or less than share amount(Payment/no of members)
                    // Here contributor should pay to Payee, as his contribution is less than shared amount.
                    Double owerOwedToPayee =  perHeadToContribute - owedUserPaidAmount;
                    Double owedUserExistingOwedAmount =
                            Optional.ofNullable(payeeUser.getOwedMap()).map(map -> map.get(owerId)).orElse(0d);
                    Double owerOwedNewAmount = owedUserExistingOwedAmount + owerOwedToPayee;

                    Double payeeOwedToUser =
                            Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
                    if(Double.compare(payeeOwedToUser, owerOwedNewAmount) > 0) {
                        owerUser.getOwedMap().put(payee, payeeOwedToUser - owerOwedNewAmount);
                        payeeUser.getOwedMap().remove(owerId, 0d);
                    } else {
                        owerUser.getOwedMap().remove(payee, 0d);
                        payeeUser.getOwedMap().put(owerId, owerOwedNewAmount - payeeOwedToUser);
                    }
                }
            }
        }
    }

    public void percentSplit(String payee, Double paidAmount, Map<String, User> userMap, List<String> owerIds) {
        Double perHeadToContribute = paidAmount / owerIds.size();
        Scanner sc = getScanner();
        List<Double> owedUsersPaidAmounts = new ArrayList<>();
        Integer owerPercentageContributions = 0;
        for(int i = 0;  i < owerIds.size(); i++) {
            int percentage = sc.nextInt();
            owedUsersPaidAmounts.add(paidAmount * (percentage/100.0));
            owerPercentageContributions+=percentage;
        }
        if(!owerPercentageContributions.equals(100)) {
            System.out.println("Paid amount does not match with contributed percentage by contributors!");
            return;
        }
        User payeeUser = userMap.get(payee);
        for(int i = 0; i < owerIds.size(); i++) {
            String owerId = owerIds.get(i);
            if(!payee.equals(owerId)) {
                User owerUser = userMap.get(owerId);
                Double owedUserPaidAmount = owedUsersPaidAmounts.get(i);
                if(perHeadToContribute < owedUserPaidAmount ) {// Here Payee should payee to contributor as he paid more
                    // than his contribution. Payee in a way receive money from other contributor.
                    Double payeeOwedToOwer = owedUserPaidAmount - perHeadToContribute;
                    Double payeeUserExistingOwedAmount =
                            Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
                    Double payeeOwedNewAmount = payeeUserExistingOwedAmount + payeeOwedToOwer;
                    owerUser.getOwedMap().put(payee, payeeOwedNewAmount);
                } else { // Here contributor should pay to Payee, as his contribution is less than shared amount.
                    Double owerOwedToPayee =  perHeadToContribute - owedUserPaidAmount;
                    Double owedUserExistingOwedAmount =
                            Optional.ofNullable(payeeUser.getOwedMap()).map(map -> map.get(owerId)).orElse(0d);
                    Double owerOwedNewAmount = owedUserExistingOwedAmount + owerOwedToPayee;

                    Double payeeOwedToUser =
                            Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
                    if(Double.compare(payeeOwedToUser, owerOwedNewAmount) > 0) {
                        owerUser.getOwedMap().put(payee, payeeOwedToUser - owerOwedNewAmount);
                        payeeUser.getOwedMap().remove(owerId, 0d);
                    } else {
                        owerUser.getOwedMap().remove(payee, 0d);
                        payeeUser.getOwedMap().put(owerId, owerOwedNewAmount - payeeOwedToUser);
                    }
                }
            }
        }

    }

}
