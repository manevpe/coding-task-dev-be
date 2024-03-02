package org.paymenttools;

public class Transaction {
    public String CustomerID ;
    public String Timestamp;
    public double TransactionAmount;
    public String StoreId;

    public Transaction(String customerID, String timestamp, double transactionAmount, String storeId) {
        CustomerID = customerID.trim();
        Timestamp = timestamp.trim();
        TransactionAmount = transactionAmount;
        StoreId = storeId.trim();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "CustomerID='" + CustomerID + '\'' +
                ", Timestamp='" + Timestamp + '\'' +
                ", TransactionAmount=" + TransactionAmount +
                ", StoreId='" + StoreId + '\'' +
                '}';
    }
}
