package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.pluralsight.UIPrompts.showHomeScreen;

public class Main {
    public static Console console = new Console();

    public static void main(String[] args) {
        // Transactions should be read from and saved to a transaction file: transactions.csv
        // import information on the transactions.csv from ChatGPT

        showHomeScreen();

    }

    private static List<Ledger> getFinancialTransactions() {

        // creating a new ArrayList<>() to hold each financial transaction being encoded
        List<Ledger> entryList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));

            String dataString;

            while ((dataString = reader.readLine()) != null) {
                // adds each entry that is being read into the ArrayList<newEntry>
                entryList.add(getTransactionsFromEncodedString(dataString));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entryList;
    }

    // once the transactions are decoded, they are now saved and available across the Main Class from an ArrayList<>
    public static final List<Ledger> ledger = getFinancialTransactions();

    private static Ledger getTransactionsFromEncodedString(String encodedLedger) {
        // we are not using an ArrayList<> here because we are only outputting one variable at a time
        // we are NOT saving multiple entries
        if(encodedLedger == null || encodedLedger.trim().isEmpty()) {
            return null;
        }

        String[] temp = encodedLedger.trim().split(Pattern.quote("|"));

        if (temp.length != 5) {
            throw new IllegalArgumentException("Error occurs: " + Arrays.toString(temp));
        }

        try {
            LocalDate date = LocalDate.parse(temp[0].trim());
            LocalTime time = LocalTime.parse(temp[1].trim());
            String description = temp[2].trim().substring(0, 1).toUpperCase() + temp[2].substring(1).toLowerCase();
            String vendor = temp[3].trim().substring(0, 1).toUpperCase() + temp[3].substring(1).toLowerCase();
            double amount = Double.parseDouble(temp[4].trim());

            return new Ledger(date, time, description, vendor, amount);
        } catch (Exception e) {
            System.out.println("There was an error parsing: " + Arrays.toString(temp));
            throw new RuntimeException(e);
        }
    }
}