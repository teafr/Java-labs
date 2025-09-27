package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TransactionCSVReader {
    public List<Transaction> readTransactions(String filePath) {
        try {
            return readTransactionsFromUrl(new URL(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Transaction> readTransactionsFromUrl(URL url) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(convertToTransaction(line));
            }
        }
        return transactions;
    }

    public Transaction convertToTransaction(String transactionCSV) {
        String[] values = transactionCSV.split(",");
        return new Transaction(values[0], Double.parseDouble(values[1]), values[2]);
    }
}
