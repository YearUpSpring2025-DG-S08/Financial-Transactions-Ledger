package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

public class Main {
    public static Console console = new Console();

    public static void main(String[] args) {
        // Transactions should be read from and saved to a transaction file: transactions.csv
        // import information on the transactions.csv from ChatGPT

        showHomeScreen();

        // Challenge: Look into challenging myself once I finish the project
    }

    public static void showHomeScreen(){
        System.out.println("Welcome to the Financial Transaction Tracker");
        String homeScreenPrompt = """
                Choose from the options below:
                D) Add deposit
                P) Make Payment
                L) Ledger
                X) Exit""";
        String choice = console.promptForString(homeScreenPrompt).trim().toUpperCase();

        // put the choice code within a do/while loop so that it will rerun until the desired choice to exit the app is made
        switch (choice){
            case "D":
                // this screen will be where the user is prompted to input deposit information which will save to the csv file
                addTransaction(false);
                break;
            case "P":
                addTransaction(true);
                // this screen will be where the user is prompted to input debit information and save to csv
                break;
            case "L":
                // this screen will be where the ledger history will appear
                showLedgerScreen();
                break;
            case "X":
                // this choice will exit the program
                System.out.println("Thank you for using the Financial Transaction Tracker!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid entry. Please try again!");
        }
    }

    public static void showLedgerScreen(){
        String welcomeToLedgerPrompt = """
                Welcome to the ledger
                Choose from the options below:
                A) Display all entries
                D) Display deposits
                P) Display payments
                R) Run reports""";
        String choice = console.promptForString(welcomeToLedgerPrompt).trim().toUpperCase();

        switch (choice){
            case "A":
                // this screen will show all the entries within the ledger csv file
                for(Ledger line : ledger){
                    if(line != null){
                        System.out.println(line);
                    }
                }
                break;
            case "D":
                // this screen will display ONLY the deposits into the account
                getTransactionByAmount(ledger);
                break;
            case "P":
                // this screen will display ONLY the negative entries (payments)
                break;
            case "R":
                // this screen will allow users to run pre-defined reports or to run a custom search
//                1) Month to Date
//                2) Previous Month
//                3) Year To Date
//                4) Previous Year
//                5) Search by Vendor: prompt user for the vendor name and display all entries for that vendor
//                0) Back: go back to the report page
//                H) Home: go back to home page
                break;
            case "H":
                // this screen will bring the users back to the home page
            default:
                System.out.println("Invalid entry. Please try again!");
        }
    }

    public static void addTransaction(boolean isPayment){
        // combining the methods of addDeposit and addPayment together because of similar processes
        // creates a boolean for isPayment to distinguish between both methods
        int counter = 0;

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

            Ledger newEntry = new Ledger(date, time, description, vendor, amount);

            ledger[counter] = newEntry;
            counter++;

        String entry = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);


        try(FileWriter writer = new FileWriter("transactions.csv", true)){
            writer.write(entry + "\n");
            System.out.println("Transaction was successful!");
        } catch (IOException e) {
            System.out.println("Could not complete transaction" + e.getMessage());
        }
    }

    public static void getTransactionByAmount(Ledger[] ledger){
        double amount = 0;
        for(Ledger line : ledger){
            if(line.getAmount() == Math.abs(amount)){
                System.out.println(line);
            }
        }
    }

    public static void getTransactionByNegativeAmount(Ledger[] ledger){
        double amount = 0;
        for(Ledger line : ledger){
            if(line.getAmount() == Math.negateExact((int) amount)){
                System.out.println(line);
            }
        }
    }

    private static Ledger[] getFinancialTransactions(){

        try{
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            Ledger[] tempLedger = new Ledger[100];

            int size = 0;
            String dataString;

            while((dataString = reader.readLine()) != null){
                tempLedger[size] = getTransactionsFromEncodedString(dataString);
                size++;
            }

            return Arrays.copyOf(tempLedger, size);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static final Ledger[] ledger = getFinancialTransactions();

    private static Ledger getTransactionsFromEncodedString(String encodedLedger){

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