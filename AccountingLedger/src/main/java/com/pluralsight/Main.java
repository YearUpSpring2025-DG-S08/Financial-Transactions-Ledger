package com.pluralsight;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static Console console = new Console();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Transactions should be read from and saved to a transaction file: transactions.csv
        // import information on the transactions.csv from ChatGPT

        showHomeScreen();

        /*
        Ledger:
        A) All: display all entries
        D) Deposits: display deposits into the account
        P) Payments: display negative entries (payments)
        R) Reports: New screen that allows user to run pre-define reports or to run a custom search
            1) Month to Date
            2) Previous Month
            3) Year To Date
            4) Previous Year
            5) Search by Vendor: prompt user for the vendor name and display all entries for that vendor
            0) Back: go back to the report page
            H) Home: go back to home page
         */

        String welcomeToLedgerPrompt = """
                Welcome to the ledger
                Choose from the options below:""";
        String choice = console.promptForString(welcomeToLedgerPrompt).trim();


        switch (choice){
            case "A":
                // this screen will show all the entries within the ledger csv file
                break;
            case "D":
                // this screen will display ONLY the deposits into the account
                break;
            case "P":
                // this screen will display ONLY the negative entries (payments)
                break;
            case "R":
                // this screen will allow users to run pre-define reports or to run a custom search
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


        // Challenge: Look into challenging myself once I finish the project
    }

    public static void showHomeScreen(){
        System.out.println("Welcome to the Financial Transaction Tracker");
        String homeScreenPrompt = """
                Choose from the options below:
                D) Add deposit: prompt user for deposit information and save to csv file
                P) Make Payment Debit: prompt user for debit information and save to csv file
                L) Ledger: display the ledger screen
                X) Exit""";
        String choice = console.promptForString(homeScreenPrompt).trim().toUpperCase();

        // application will include several screens:
        /*
        Home Screen:
        Application should continue to run until User chooses to Exit
        D) Add deposit: prompt user for deposit information and save to csv file
        P) Make Payment Debit: prompt user for debit information and save to csv file
        L) Ledger: display the ledger screen
        X) Exit
         */

        // put the choice code within a do/while loop so that it will rerun until the desired choice to exit the app is made
        switch (choice){
            case "D":
                // this screen will be where the user is prompted to input deposit information which will save to the csv file
                // if it is just a deposit, should I have the user also input the rest of the data in the Ledger Class?
                int counter = 0;
                addDeposit(ledger, scanner, counter);
                break;
            case "P":
                // this screen will be where the user is prompted to input debit information and save to csv
                break;
            case "L":
                // this screen will be where the ledger history will appear
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

    public static int addDeposit(Ledger[] ledger, Scanner scanner, int counter){

        if(counter >= ledger.length){
            System.out.println("The ledger is maxed out");
            return counter;
        }

        try{
            System.out.println("Input the following information: ");
            String inputDatePrompt = "Add date of transaction: ";
            String date = console.promptForString(inputDatePrompt);
            
            DateTimeFormatter formatter;
            formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate showDate = LocalDate.parse(date, formatter);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private static Ledger[] getFinancialTransactions(){

        try{
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            Ledger[] ledgerTemp = new Ledger[1000];

            int i = 0;
            String history;

            while((history = reader.readLine()) != null){

                ledgerTemp[i] = getTransactionsFromEncodedString(history);

                i++;

            }

            return Arrays.copyOf(ledgerTemp, i);

        } catch(Exception e){
            throw new RuntimeException();
        }
    }

    private static Ledger[] ledger = getFinancialTransactions();

    private static Ledger getTransactionsFromEncodedString(String encodedLedger){

       String[] temp = encodedLedger.split(Pattern.quote("|"));

       String date = temp[0];
       double time = Integer.parseInt(temp[1]);
       String vendor = temp[2];
       double amount = Integer.parseInt(temp[3]);

        return new Ledger(date, time, vendor, amount);

    }

}