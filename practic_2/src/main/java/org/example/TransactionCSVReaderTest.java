package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionCSVReaderTest {
    @Test
    public void testConvertToTransaction() {
        String transactionCSV = "16-12-2023,25000,Фріланс";
        Transaction expactedTransaction = new Transaction("16-12-2023", 25000, "Фріланс");

        TransactionCSVReader csvReader = new TransactionCSVReader();
        Transaction transaction = csvReader.convertToTransaction(transactionCSV);

        Assertions.assertEquals(expactedTransaction.getDate(), transaction.getDate());
        Assertions.assertEquals(expactedTransaction.getAmount(), transaction.getAmount());
        Assertions.assertEquals(expactedTransaction.getDescription(), transaction.getDescription());
    }

    @Test
    public void testReadTransactions() throws IOException {
        Path tempFile = Files.createTempFile("transactions2", ".csv");
        String csvContent = "10-01-2024,500,Gift";
        Files.write(tempFile, csvContent.getBytes(StandardCharsets.UTF_8));

        TransactionCSVReader reader = new TransactionCSVReader();
        List<Transaction> transactions = reader.readTransactions(tempFile.toUri().toString());

        Assertions.assertEquals(1, transactions.size());
        Transaction t = transactions.getFirst();
        Assertions.assertEquals("10-01-2024", t.getDate());
        Assertions.assertEquals(500.0, t.getAmount());
        Assertions.assertEquals("Gift", t.getDescription());
    }
}
