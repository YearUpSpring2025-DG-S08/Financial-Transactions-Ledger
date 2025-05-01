package com.pluralsight;

import static com.pluralsight.Main.console;
import static com.pluralsight.Main.ledger;
import static com.pluralsight.TransactionReports.*;
import static com.pluralsight.TransactionReports.getTransactionsByPreviousYear;
import static com.pluralsight.TransactionReports.getTransactionsByVendor;
import static com.pluralsight.TransactionsHandler.*;
import static com.pluralsight.StyledMenus.*;

public class UIPrompts {

    public static void showHomeScreen() {
        String choice;
        do {
            styledHeader("Welcome to the Financial Transaction Tracker!");
            String homeScreenPrompt = """
                    Choose from the options below:
                    
                    [D] Add deposit
                    [P] Make Payment - Debit
                    [L] Ledger Menu
                    [X] Exit
                    """;
            choice = console.promptForString(homeScreenPrompt).trim().toUpperCase();

            // put the choice code within a do/while loop so that it will rerun until the desired choice to exit the app is made
            switch (choice) {
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
        } while (true);
    }

    public static void showLedgerScreen() {
        String choice;
        do {
            styledHeader("Ledger Menu");
            String welcomeToLedgerPrompt = """
                    Choose from the options below:
                    
                    [A] Display all entries
                    [D] Display deposits
                    [P] Display payments
                    [R] Run reports""";
            choice = console.promptForString(welcomeToLedgerPrompt).trim().toUpperCase();

            switch (choice) {
                case "A":
                    // this screen will show all the entries within the ledger csv file
                    getALlTransactions(ledger);
                    break;
                case "D":
                    // this screen will display ONLY the deposits into the account
                    getDepositTransactions(ledger);
                    break;
                case "P":
                    // this screen will display ONLY the negative entries (payments)
                    getPaymentTransactions(ledger);
                    break;
                case "R":
                    // this screen will allow users to run pre-defined reports or to run a custom search
                    runReportsSearch();
                    break;
                case "H":
                    // this screen will bring the users back to the home page
                    showHomeScreen();
                default:
                    System.out.println("Invalid entry. Please try again!");
            }
        } while (!choice.equals("H"));
    }

    public static void runReportsSearch() {
        String choice;
        do {
            styledHeader("Reports Search Menu");
            String inputReportsPrompt = """
                    Choose one of the search options below:
                    
                    [1] Month to Date
                    [2] Previous Month
                    [3] Year To Date
                    [4] Previous Year
                    [5] Search by Vendor
                    [6] Custom Search
                    [0] Go back to the Ledger page
                    [H] Go back to Home page""";
            choice = console.promptForString(inputReportsPrompt);

            switch (choice) {
                case "1":
                    // month to date search
                    getTransactionsByMonthToDate();
                    break;
                case "2":
                    // previous month to date search
                    getTransactionsByPreviousMonthtoDate();
                    break;
                case "3":
                    // year to date search
                    getTransactionsByYear();
                    break;
                case "4":
                    // previous year search
                    getTransactionsByPreviousYear();
                    break;
                case "5":
                    // vendor search
                    getTransactionsByVendor(ledger);
                    break;
                case "6":
                    // custom search
                    getTransactionsByCustomSearch(ledger);
                case "0":
                    showLedgerScreen();
                    break;
                case "H":
                    showHomeScreen();
                    break;
                default:
                    System.out.println("Invalid entry! Please try again!");
            }
        }
        while (!choice.equals("H"));
    }

}