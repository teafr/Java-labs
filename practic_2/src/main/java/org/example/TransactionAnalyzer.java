package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionAnalyzer {
    private List<Transaction> transactions;
    private DateTimeFormatter dateFormatter;

    public TransactionAnalyzer(List<Transaction> transactions) {
        this.transactions = transactions;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public double calculateTotalBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public int countTransactionsByMonth(String monthYear) {
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

    public List<Transaction> findTopExpenses() {
        return transactions.stream()
            .filter(t -> t.getAmount() < 0)
            .sorted(Comparator.comparing(Transaction::getAmount))
            .limit(10)
            .collect(Collectors.toList());
    }

    public Map<String, Transaction> findMaxAndMinExpenseInPeriod(String startDate, String endDate) {
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

    public Map<String, Map<String, Double>> calculateExpensesByCategoryAndMonth() {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        return transactions.stream()
            .filter(t -> t.getAmount() < 0)
            .collect(Collectors.groupingBy(t -> LocalDate.parse(t.getDate(), dateFormatter).format(monthFormatter),
                Collectors.groupingBy(Transaction::getDescription, Collectors.summingDouble(t -> -t.getAmount()))
            ));
    }
}
