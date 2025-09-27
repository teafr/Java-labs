package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class TransactionAnalyzerTest {
    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("2023-01-01", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("2023-01-02", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("2023-01-03", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    public void testFindTopExpenses() {
        Transaction transaction1 = new Transaction("01-02-2023", -20.0, "Витрата");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", -100.0, "Витрата");
        Transaction transaction4 = new Transaction("11-02-2023", 50.0, "Дохід");
        Transaction transaction5 = new Transaction("15-01-2023", -70.0, "Витрата");
        Transaction transaction6 = new Transaction("06-03-2023", -80.0, "Витрата");
        Transaction transaction7 = new Transaction("01-02-2023", -50.0, "Витрата");
        Transaction transaction8 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction9 = new Transaction("05-03-2023", -100.0, "Витрата");
        Transaction transaction10 = new Transaction("11-02-2023", -70.0, "Витрата");
        Transaction transaction11 = new Transaction("15-01-2023", -70.0, "Витрата");
        Transaction transaction12 = new Transaction("06-03-2023", -120.0, "Витрата");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6, transaction7, transaction8, transaction9, transaction10, transaction11, transaction12);

        List<Transaction> result = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertTrue(result.size() < 11);
        Assertions.assertEquals(-120.0, result.getFirst().getAmount());
    }
}

