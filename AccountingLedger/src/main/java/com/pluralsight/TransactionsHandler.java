package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.pluralsight.Main.console;
import static com.pluralsight.Main.ledger;

public class TransactionsHandler {

    public static void addTransaction(boolean isPayment) {
        // combining the methods of addDeposit and addPayment together because of similar processes
        // creates a boolean for isPayment to distinguish between both methods

        System.out.println(StyledUI.styledBoxTitle("\uD83D\uDCB0Add a Transaction\uD83D\uDCB8"));

        // prompt user to decide to make a future transaction or current
        String inputDateOfTransactionPrompt = """
                Would you like your transaction to be set in the future or current?
                [F] Future Transaction
                [C] Current Transaction""";
        String transactionDate = console.promptForString(inputDateOfTransactionPrompt);

        if (transactionDate.equals("C")) {
            // will auto complete the date to the current date when the entry is made
            transactionDate = String.valueOf(LocalDate.now());
        } else {
            transactionDate = console.promptForString("Enter the date for your future transaction: yyyy-MM-dd");
        }

        // will auto complete the time to the current time when the entry is made
        LocalTime now = LocalTime.now();

        String inputDescriptionPrompt = """
                    Add description:""";
        String description = console.promptForString(inputDescriptionPrompt);

        String inputVendorPrompt = """
                    Add vendor information:""";
        String vendor = console.promptForString(inputVendorPrompt);

        String inputAmountPrompt = """
                    Input an amount: \n""";
        double amount = console.promptForDouble(inputAmountPrompt);

        if(isPayment){
            amount = -Math.abs(amount); // this line will ensure that the amount given for a payment is negative
        }

        saveTransaction(LocalDate.parse(transactionDate), now, description, vendor, amount);
    }

    public static void getALlTransactions(List<Ledger> ledger){
        System.out.println(StyledUI.styledBoxTitle("All Transactions"
        + Ledger.getFormattedLedgerTextHeader()));
        ledger.removeIf(Objects::isNull);
        // this line will remove any instance of an object being read within the ledger as null
        ledger.sort(Comparator.comparing(Ledger::date).reversed());
        // the .sort method and the Comparator.comparing() method will sort through the entries
        // in the ArrayList<>(Ledger) and compare them by the dates: Ledger::date
        // and set them in chronological order using .reversed() puts newest entries first(top of list)

        for (Ledger line : ledger) {
            if (line != null) {
                System.out.println(line.getFormattedLedger());
            }
        }
    }

    public static void getDepositTransactions(List<Ledger> ledger){
        if(ledger == null || ledger.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        System.out.println(StyledUI.styledBoxTitle("\uD83D\uDCB0Deposit Transactions\uD83D\uDCB0"));
        System.out.println(Ledger.getFormattedLedgerTextHeader());
        ledger.sort(Comparator.comparing(Ledger::date).reversed());
        for(Ledger line : ledger){
            if(line.amount() > 0){
                System.out.println(line.getFormattedLedger());
            }
        }
    }

    public static void getPaymentTransactions(List<Ledger> ledger){
        if(ledger == null || ledger.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        System.out.println(StyledUI.styledBoxTitle("\uD83D\uDCB8Payment Transactions\uD83D\uDCB8"));
        System.out.println(Ledger.getFormattedLedgerTextHeader());
        ledger.sort(Comparator.comparing(Ledger::date).reversed());
        for(Ledger line : ledger){
            if(line.amount() < 0 ){
                System.out.println(line.getFormattedLedger());
            }
        }
    }

    public static void saveTransaction(LocalDate date, LocalTime time, String description, String vendor, double amount){

        // creating a new Ledger entry within the ArrayList<Ledger>
        Ledger newEntry = new Ledger(date, time, description, vendor, amount);
        ledger.add(newEntry);

        String entry = newEntry.toString();

        // writing the new entry to the file
        try(FileWriter writer = new FileWriter("transactions.csv", true)){
            writer.write(entry + "\n");
            System.out.println("✨✨✨ Transaction was successful! ✨✨✨");
        } catch (IOException e) {
            System.out.println("Could not complete transaction" + e.getMessage());
        }
    }
}
