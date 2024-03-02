package org.paymenttools;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Customer {
    public String id;
    public ArrayList<Transaction> transactions;
    public Boolean isLoyal;

    public Customer(String id) {
        this.id = id;
        this.transactions = new ArrayList<>();
        this.isLoyal = false;
    }

    public Customer(String id, ArrayList<Transaction> transactions, Boolean isLoyal) {
        this.id = id;
        this.transactions = transactions;
        this.isLoyal = isLoyal;
    }

    @Override
    public String toString() {
        return "========= CUSTOMER ID - " + id + " =========\n" +
                transactions.stream().map(Transaction::toString)
                .collect(Collectors.joining("\n")) +
                "\nisLoyal = " + isLoyal + "\n";
    }
}
