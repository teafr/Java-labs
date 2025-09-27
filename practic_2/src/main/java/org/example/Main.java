package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        TransactionCSVReader csvReader = new TransactionCSVReader();
        List<Transaction> transactions = csvReader.readTransactions(filePath);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        TransactionReportGenerator reportGenerator = new TransactionReportGenerator();

        double totalBalance = analyzer.calculateTotalBalance();
        reportGenerator.printBalanceReport(totalBalance);
        String monthYear = "01-2024";
        int transactionsCount = analyzer.countTransactionsByMonth(monthYear);
        reportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = analyzer.findTopExpenses();
        reportGenerator.printTopExpensesReport(topExpenses);

        Map<String, Transaction> maxMin =
                analyzer.findMaxAndMinExpenseInPeriod("01-01-2024", "31-03-2024");
        reportGenerator.printMaxAndMinExpenseReport(maxMin);

        Map<String, Map<String, Double>> chart = analyzer.calculateExpensesByCategoryAndMonth();
        reportGenerator.printCategoryMonthExpenseChart(chart);
    }

}