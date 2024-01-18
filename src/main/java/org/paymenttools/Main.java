package org.paymenttools;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*

TASK:
We are given two logs files, containing money transaction information in a string text format.
Each log entry contains the following info:
Customer ID, Transaction Timestamp, Transaction Amount (euro), Store ID

We want to print on the console a list of all “Loyal” customers.
A loyal customer is a customer, who:
 - made purchases on 2 separate days
 - made purchases at 2 different stores
 - spent a minimum of 100 euro

 */
public class Main {
    public static List<List<String[]>> data = new ArrayList<>();
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

    public static void printLoyalCustomer() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.println(Arrays.toString(data.get(i).get(j)));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= daysCount; i++) {
            data.add(getAllEntriesForDay(i));
        }
        printLoyalCustomer();
    }
}