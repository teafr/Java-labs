package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), dateFormatter);
            String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getAmount() < 0)
            .sorted(Comparator.comparing(Transaction::getAmount))
            .limit(10)
            .collect(Collectors.toList());
    }

    public static Map<String, Transaction> findMaxAndMinExpenseInPeriod(List<Transaction> transactions, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, dateFormatter);
        LocalDate end = LocalDate.parse(endDate, dateFormatter);

        List<Transaction> periodExpenses = transactions.stream()
            .filter(t -> t.getAmount() < 0)
            .filter(t -> {
                LocalDate d = LocalDate.parse(t.getDate(), dateFormatter);
                return !d.isBefore(start) && !d.isAfter(end);
            })
            .collect(Collectors.toList());

        Transaction maxExpense = periodExpenses.stream().max(Comparator.comparingDouble(Transaction::getAmount)).stream().findFirst().orElse(null);
        Transaction minExpense = periodExpenses.stream().min(Comparator.comparingDouble(Transaction::getAmount)).stream().findFirst().orElse(null);

        Map<String, Transaction> result = new HashMap<>();
        result.put("max", maxExpense);
        result.put("min", minExpense);
        return result;
    }

    public static Map<String, Map<String, Double>> calculateExpensesByCategoryAndMonth(List<Transaction> transactions) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        return transactions.stream()
            .filter(t -> t.getAmount() < 0)
            .collect(Collectors.groupingBy(t -> LocalDate.parse(t.getDate(), dateFormatter).format(monthFormatter),
                Collectors.groupingBy(Transaction::getDescription, Collectors.summingDouble(t -> -t.getAmount()))
            ));
    }
}
