package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);
        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        Map<String, Transaction> maxMin = TransactionAnalyzer.findMaxAndMinExpenseInPeriod(transactions, "01-01-2024", "31-03-2024");
        TransactionReportGenerator.printMaxAndMinExpenseReport(maxMin);

        Map<String, Map<String, Double>> chart = TransactionAnalyzer.calculateExpensesByCategoryAndMonth(transactions);
        TransactionReportGenerator.printCategoryMonthExpenseChart(chart);
    }

}