package org.example;

import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {
    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printMaxAndMinExpenseReport(Map<String, Transaction> maxMin) {
        System.out.println("Найбільша витрата: " + maxMin.get("min").getAmount());
        System.out.println("Найменша витрата: " +  maxMin.get("max").getAmount());
    }

    public static void printCategoryMonthExpenseChart(Map<String, Map<String, Double>> expenses) {
        System.out.println("Звіт витрат по категоріях і місяцях (кожна * = 1000 грн):");
        expenses.forEach((month, categories) -> {
            System.out.println("Місяць: " + month);
            categories.forEach((category, amount) -> {
                int stars = (int) (amount / 1000);
                System.out.printf("  %-15s : %,.2f грн  %s%n", category, amount, "*".repeat(stars));
            });
        });
    }
}

