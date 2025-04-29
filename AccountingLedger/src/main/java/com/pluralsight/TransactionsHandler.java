package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.pluralsight.Main.console;
import static com.pluralsight.Main.ledger;

public class TransactionsHandler {


    public static void addTransaction(boolean isPayment){
        // combining the methods of addDeposit and addPayment together because of similar processes
        // creates a boolean for isPayment to distinguish between both methods

        // will auto complete the date to the current date when the entry is made
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        String date = today.format(formatter);

        // will auto complete the time to the current time when the entry is made
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        String time = now.format(formatter1);

        String inputDescriptionPrompt = """
                    Add description:""";
        String description = console.promptForString(inputDescriptionPrompt);

        String inputVendorPrompt = """
                    Add vendor information:""";
        String vendor = console.promptForString(inputVendorPrompt);

        String inputAmountPrompt = """
                    Input the amount to deposit: \n""";
        double amount = console.promptForDouble(inputAmountPrompt);

        if(isPayment){
            amount = -Math.abs(amount); // this line will ensure that the amount given for a payment is negative
        }

        saveTransaction(date, time, description, vendor, amount);
    }

    public static void getDepositTransactions(List<Ledger> ledger){
        if(ledger == null || ledger.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        for(Ledger line : ledger){
            if(line.amount() > 0){
                System.out.println(line);
            }
        }
    }

    public static void getPaymentTransactions(List<Ledger> ledger){
        if(ledger == null || ledger.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        for(Ledger line : ledger){
            if(line.amount() < 0 ){
                System.out.println(line);
            }
        }
    }

    public static void saveTransaction(String date, String time, String description, String vendor, double amount){

        // creating a new Ledger entry within the ArrayList<Ledger>
        Ledger newEntry = new Ledger(date, time, description, vendor, amount);
        ledger.add(newEntry);

        String entry = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);

        // writing the new entry to the file
        try(FileWriter writer = new FileWriter("transactions.csv", true)){
            writer.write(entry + "\n");
            System.out.println("Transaction was successful!");
        } catch (IOException e) {
            System.out.println("Could not complete transaction" + e.getMessage());
        }
    }
}
