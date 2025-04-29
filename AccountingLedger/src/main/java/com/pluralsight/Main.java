package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static Console console = new Console();

    public static void main(String[] args) {
        // Transactions should be read from and saved to a transaction file: transactions.csv
        // import information on the transactions.csv from ChatGPT

        showHomeScreen();

        // Challenge: Look into challenging myself once I finish the project
    }

    public static void showHomeScreen() {
        String choice;
        do {
            System.out.println("Welcome to the Financial Transaction Tracker");
            String homeScreenPrompt = """
                    Choose from the options below:
                    D) Add deposit
                    P) Make Payment
                    L) Ledger
                    X) Exit""";
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
            String welcomeToLedgerPrompt = """
                    Welcome to the ledger
                    Choose from the options below:
                    A) Display all entries
                    D) Display deposits
                    P) Display payments
                    R) Run reports""";
            choice = console.promptForString(welcomeToLedgerPrompt).trim().toUpperCase();

            switch (choice) {
                case "A":
                    // this screen will show all the entries within the ledger csv file
                    for (Ledger line : ledger) {
                        if (line != null) {
                            System.out.println(line);
                        }
                    }
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

    public static void getDepositTransactions(List<Ledger> ledger){
        if(ledger == null || ledger.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        for(Ledger line : ledger){
            if(line.getAmount() > 0){
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
            if(line.getAmount() < 0 ){
                System.out.println(line);
            }
        }
    }

    public static void runReportsSearch() {
        String choice;
        do {
            String inputReportsPrompt = """
                    Choose one of the search options below:
                    1) Month to Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    0) Go back to the Ledger page
                    H) Go back to Home page""";
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

    public static void getTransactionsByMonthToDate(){

        LocalDate today = LocalDate.now();
        LocalDate thisMonth = today.withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // creating a ArrayList using the List interface to hold sorted dates
        List<Ledger> sortedMonthToDate = new ArrayList<>();

        for(Ledger line : ledger){
            try{
                LocalDate day = LocalDate.parse(line.getDate(), formatter);
                if(!day.isBefore(thisMonth) && !day.isAfter(today)){
                    sortedMonthToDate.add(line);
                    // for each line in ledger, the if statement will add desired dates to the ArrayList<>()
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        sortedMonthToDate.sort(Comparator.comparing(line -> LocalDate.parse(line.getDate(), formatter)));
        // using the .sort() method to sort what the Comparator.comparing() method defines how the data
        // needs to be sorted; in this case each line of Ledger is parsed into a LocalDate and formatted by formatter
        // and sorted in chronological order
        for(Ledger line : sortedMonthToDate){
            System.out.println(line);
            // this for/each loop iterates through the ArrayList created for the sorted dates
        }

    }

    public static void getTransactionsByPreviousMonthtoDate(){

        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1).withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        List<Ledger> previousMonthToDate = new ArrayList<>();

        for(Ledger line : ledger){
            try{
                LocalDate day = LocalDate.parse(line.getDate(), formatter);
                if(!day.isBefore(lastMonth) && !day.isAfter(today)){
                    previousMonthToDate.add(line);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        previousMonthToDate.sort(Comparator.comparing(line -> LocalDate.parse(line.getDate(), formatter)));
        for(Ledger line : previousMonthToDate){
            System.out.println(line);
        }
    }

    public static void getTransactionsByYear(){

        LocalDate today = LocalDate.now();
        LocalDate thisYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        // this line of code grabs the current day, takes the year,
        // and creates a day for that year of month: 1 and day of month: 1
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Ledger> sortedYearToDate = new ArrayList<>();

        for(Ledger line : ledger){
            try{
                LocalDate day = LocalDate.parse(line.getDate(), formatter);
                if(!day.isBefore(thisYear) && !day.isAfter(today)){
                    sortedYearToDate.add(line);
                }
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        sortedYearToDate.sort(Comparator.comparing(line -> LocalDate.parse(line.getDate(), formatter)));
        for(Ledger line : sortedYearToDate){
            System.out.println(line);
        }
    }

    public static void getTransactionsByPreviousYear(){

        LocalDate today = LocalDate.now();
        LocalDate thisYear = today.minusYears(1).withDayOfMonth(1);
        // this line of code takes today and subtracts 1 year,
        // and creates a day for that year of month: 1
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Ledger> sortedPreviousYear = new ArrayList<>();

        for(Ledger line : ledger){
            try{
                LocalDate day = LocalDate.parse(line.getDate(), formatter);
                if(!day.isBefore(thisYear) && !day.isAfter(today)){
                    sortedPreviousYear.add(line);
                }
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        sortedPreviousYear.sort(Comparator.comparing(line -> LocalDate.parse(line.getDate(), formatter)));
        for(Ledger line : sortedPreviousYear){
            System.out.println(line);
        }
    }

    public static void getTransactionsByVendor(List<Ledger> ledger){
        String inputVendorSearchPrompt = """
                Please input a vendor for search:""";
        String input = console.promptForString(inputVendorSearchPrompt);

        boolean found = false;
        for(Ledger line : ledger){
            if(line != null && line.getVendor().equalsIgnoreCase(input)){
                if(!found){
                    System.out.println(line);
                    found = true;
                }
            }
        }
        if(!found){
            System.out.println("There is no history of transactions from " + input);
        }
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
    private static final List<Ledger> ledger = getFinancialTransactions();

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