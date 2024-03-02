package org.paymenttools;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*

TASK:
We are given two logs files, containing money transaction information in a string text format.
Each log entry contains the following info:
Customer ID, Transaction Timestamp, Transaction Amount (euro), Store ID

We want to print on the console a list of all “Loyal” customers.
A loyal customer is a customer, who:
 - made purchases at 2 different stores
 - spent a minimum of 100 euro

 */
public class Main {
    public static List<Transaction> data = new ArrayList<>();
    public static Map<String, Customer> customers = new HashMap<>();
    public static int daysCount = 2;

    public static List<String[]> readLineByLine(Path filePath) throws Exception {
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build()) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }
        }
        return list;
    }

    public static List<String[]> readAllLines(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build()) {
                return csvReader.readAll();
            }
        }
    }

    public static List<String[]> getAllEntriesForDay(int dayNumber) throws Exception {
        Path path = Paths.get(
                ClassLoader.getSystemResource("transaction-logs/day" + dayNumber + ".csv").toURI()
        );
        return readAllLines(path);
    }

    public static List<String> printLoyalCustomer() {
        System.out.println("Looking for loyal customers:");
        List<String> loyalCustomers = new ArrayList<>();
        customers.forEach((key, value) -> {
            if (value.isLoyal) {
                loyalCustomers.add(value.id);
                System.out.println("New loyal customer found:");
                System.out.println(value);
            }
        });
        System.out.println("Finished looking for loyal customers...");
        return loyalCustomers;
    }

    public static void main(String[] args) throws Exception {
        // Read all transactions from all days into the "data" list
        for (int i = 1; i <= daysCount; i++) {
            List<String[]> dailyTransactionData = getAllEntriesForDay(i);
            dailyTransactionData.forEach(transactionData -> {
                Transaction newTransaction = new Transaction(
                        transactionData[0],
                        transactionData[1],
                        Double.parseDouble(transactionData[2]),
                        transactionData[3]
                );
                data.add(newTransaction);
            });
        }

        // Produce the Customers structure
        customers = data.stream().collect(Collectors.toMap(t -> t.CustomerID, t -> {
            return new Customer(t.CustomerID, new ArrayList<>(List.of(t)), false);
        }, (first, second) -> {
            return new Customer(
                    first.id,
                    // Join the two transactions lists
                    new ArrayList<>(Stream.concat(first.transactions.stream(), second.transactions.stream()).toList()),
                    first.isLoyal);
        }));

        // TODO - calculate loyalty

        // Print all loyal customers
        printLoyalCustomer();
    }
}

