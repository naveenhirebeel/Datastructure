package com.machinecoding.expenseSplit.service;

import com.machinecoding.expenseSplit.model.Command;
import com.machinecoding.expenseSplit.model.ExpenseType;
import com.machinecoding.expenseSplit.model.User;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;

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
                    equalExpenseSplit(payee, paidAmount, userMap, owerIds);
                    break;
                case EXACT:
                    exactExpenseSplit(payee, paidAmount, userMap, owerIds);
                    break;
                case PERCENT:
                    percentExpenseSplit(payee, paidAmount, userMap, owerIds);
                    break;
                default:
                    System.out.println("Not supported expense type");
            }
        } catch (Exception ex) {
            System.out.println("Wrong expenseType");
        }
        System.out.println("Executed expense");
    }

    public void equalExpenseSplit(String payee, Double paidAmount, Map<String, User> userMap, List<String> owerIds) {
        User payeeUser = userMap.get(payee);
        Double woedAmount = paidAmount / owerIds.size();

        for(String owerId : owerIds) {
            Optional<Double> existingOwedAmount = Optional.ofNullable(payeeUser.getOwedMap().get(owerId));
            Double owedAmount = existingOwedAmount.map(v -> v + woedAmount).orElse(woedAmount);

            if(!payee.equals(owerId)) {

                //Logic to check for reverse owedAmount -VVIMP
                Double remainingOwedAmount;
                User ower = userMap.get(owerId);
                Optional<Double> payeeOwedAmountToOwerOptional = Optional.ofNullable(ower.getOwedMap().get(payee));
                Double payeeOwedToOwer = payeeOwedAmountToOwerOptional.map(v -> v).orElse(0d);

                updatePayeeAndOwedUserAmount(userMap, payee, owerId, owedAmount);

                /*if(Double.compare(payeeOwedToOwer, owedAmount) > 0) {
                    ower.getOwedMap().put(payee, payeeOwedToOwer - owedAmount);
                    payeeUser.getOwedMap().put(owerId, 0d);
                } else if(Double.compare(payeeOwedToOwer, owedAmount) < 0) {
                    ower.getOwedMap().put(payee, 0d);
                    payeeUser.getOwedMap().put(owerId, owedAmount - payeeOwedToOwer);
                } else {
                    ower.getOwedMap().put(payee, 0d);
                    payeeUser.getOwedMap().put(owerId, owedAmount - payeeOwedToOwer);
                }*/
            }
        }
    }

    public void exactExpenseSplit(String payee, Double paidAmount, Map<String, User> userMap, List<String> owerIds) {
        User payeeUser = userMap.get(payee);
        Scanner sc = getScanner();
        List<Double> owedAmounts = new ArrayList<>();
        Double allOwerPaidAmount = 0d;
        for(String owerId: owerIds) {
            Double owerPaidAmount = sc.nextDouble();
            owedAmounts.add(owerPaidAmount);
            allOwerPaidAmount += owerPaidAmount;
        }

        if(!paidAmount.equals(allOwerPaidAmount)) {
            System.out.println("Paid amount does not match with contributed amount by contributors!");
            return;
        }


        for(int i = 0; i < owerIds.size(); i++) {
            String owerId = owerIds.get(i);
            if(!payee.equals(owerId)) {
                Double userExistingOwedAmount =
                        Optional.ofNullable(payeeUser.getOwedMap()).map(map -> map.get(owerId)).orElse(0d);
                Double owedAmount = userExistingOwedAmount + owedAmounts.get(i);

                updatePayeeAndOwedUserAmount(userMap, payee, owerId, owedAmount);

                /*//Get payee owed amount to user.
                User owerUser = userMap.get(owerId);
                Double payeeOwedToUser =
                        Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
                if(Double.compare(payeeOwedToUser, owedAmount) > 0) {
                    owerUser.getOwedMap().put(payee, payeeOwedToUser - owedAmount);
                    payeeUser.getOwedMap().put(owerId, 0d);
                } else {
                    owerUser.getOwedMap().put(payee, 0d);
                    payeeUser.getOwedMap().put(owerId, owedAmount - payeeOwedToUser);
                }*/
            }
        }
    }

    private void updatePayeeAndOwedUserAmount(Map<String, User> userMap, String payee, String owerId, Double owedAmount) {
        User owerUser = userMap.get(owerId);
        User payeeUser = userMap.get(payee);

        //Get payee owed amount to user.
        Double payeeOwedToUser =
                Optional.ofNullable(owerUser.getOwedMap()).map(map -> map.get(payee)).orElse(0d);
        if(Double.compare(payeeOwedToUser, owedAmount) > 0) {
            owerUser.getOwedMap().put(payee, payeeOwedToUser - owedAmount);
            payeeUser.getOwedMap().put(owerId, 0d);
        } else {
            owerUser.getOwedMap().put(payee, 0d);
            payeeUser.getOwedMap().put(owerId, owedAmount - payeeOwedToUser);
        }
    }

    public void percentExpenseSplit(String payee, Double paidAmount, Map<String, User> userMap, List<String> owerIds) {
        Scanner sc = getScanner();
        List<Integer> percentages = new ArrayList<>();
        for(int i = 0;  i < owerIds.size(); i++) {
            percentages.add(sc.nextInt());
        }

        User payeeUser = userMap.get(payee);
        for(int i = 0; i < owerIds.size(); i++) {
            String owerId = owerIds.get(i);
            if(!owerId.equals(payee)) {
                Double userExistingOwedAmount =
                        Optional.ofNullable(payeeUser.getOwedMap()).map(map -> map.get(owerId)).orElse(0d);
                Double owedAmount = userExistingOwedAmount + paidAmount * (percentages.get(i)/100.0);

                updatePayeeAndOwedUserAmount(userMap, payee, owerId, owedAmount);

                /*//Get Payee owed to ower
                User owerUser = userMap.get(owerId);
                Double payeeOwedToOwer =
                        Optional.ofNullable(userMap.get(owerId).getOwedMap()).map(map -> map.get(payee)).orElse(0d);

                if(Double.compare(payeeOwedToOwer, owedAmount) > 0) {
                    owerUser.getOwedMap().put(payee, payeeOwedToOwer - owedAmount);
                    payeeUser.getOwedMap().remove(owerId);
                } else {
                    owerUser.getOwedMap().remove(payee);
                    payeeUser.getOwedMap().put(owerId, owedAmount - payeeOwedToOwer);
                }*/
            }
        }

    }
}
