package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.pluralsight.UIPrompts.showHomeScreen;

public class Main {
    public static Console console = new Console();

    public static void main(String[] args) {
        // Transactions should be read from and saved to a transaction file: transactions.csv
        // import information on the transactions.csv from ChatGPT

        showHomeScreen();

        // Challenge: Look into challenging myself once I finish the project
    }

    private static List<Ledger> getFinancialTransactions(){

        // creating a new ArrayList<>() to hold each financial transaction being encoded
        List<Ledger> entryList = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));

            String dataString;

            while((dataString = reader.readLine()) != null){
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

    private static Ledger getTransactionsFromEncodedString(String encodedLedger){
        // we are not using an ArrayList<> here because we are only outputting one variable at a time
        // we are NOT saving multiple entries
       String[] temp = encodedLedger.split(Pattern.quote("|"));

        if(temp.length != 5){
            throw new IllegalArgumentException("There is some information missing!");
        } else {
            String date = temp[0];
            String time = temp[1];
            String description = temp[2];
            String vendor = temp[3];
            double amount = Double.parseDouble(temp[4]);

            return new Ledger(date, time, description, vendor, amount);
        }
    }
}