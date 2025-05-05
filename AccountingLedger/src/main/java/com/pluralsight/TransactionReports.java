package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.Main.console;
import static com.pluralsight.Main.ledger;

public class TransactionReports {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    // creates a constant DateTimeFormatter variable to be used in each method sort method

    private static List<Ledger> getSortedDates(LocalDate thisMonth, LocalDate today) {
        // creating this method to sort the dates in each method
        List<Ledger> sortedMonthToDate = new ArrayList<>();

        for (Ledger line : ledger) {
            try {
                if(!line.date().isBefore(thisMonth) && !line.date().isAfter(today)){
                    sortedMonthToDate.add(line);
                    // for each line in ledger, the if statement will add desired dates to the ArrayList<>()
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sortedMonthToDate;
    }

    public static void getTransactionsByMonthToDate() {

        LocalDate today = LocalDate.now();
        LocalDate thisMonth = today.withDayOfMonth(1);

        // creating a ArrayList using the List interface to hold sorted dates
        List<Ledger> sortedMonthToDate = getSortedDates(thisMonth, today);
        sortedMonthToDate.sort(Comparator.comparing(Ledger::date));
        // using the .sort() method to sort what the Comparator.comparing() method defines how the data
        // needs to be sorted; in this case sorting by the Ledger dates
        // and sorted in chronological order
        System.out.println(StyledUI.styledBoxTitle("Transactions for: " + thisMonth + " - " + today));
        for (Ledger line : sortedMonthToDate) {
            System.out.println(line.toString());
            // this for/each loop iterates through the ArrayList created for the sorted dates
        }

    }

    public static void getTransactionsByPreviousMonthtoDate() {

        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1).withDayOfMonth(1);

        List<Ledger> previousMonthToDate = getSortedDates(lastMonth, today);
        previousMonthToDate.sort(Comparator.comparing(Ledger::date));
        System.out.println(StyledUI.styledBoxTitle("Transactions for: " + lastMonth + " - " + today));
        for (Ledger line : previousMonthToDate) {
            System.out.println(line.getFormattedLedger());
        }
    }

    public static void getTransactionsByYear() {

        LocalDate today = LocalDate.now();
        LocalDate thisYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        // this line of code grabs the current day, takes the year,
        // and creates a day for that year of month: 1 and day of month: 1

        List<Ledger> sortedYearToDate = getSortedDates(thisYear, today);
        sortedYearToDate.sort(Comparator.comparing(Ledger::date));
        System.out.println(StyledUI.styledBoxTitle("Transactions for: " + thisYear + " - " + today));
        for (Ledger line : sortedYearToDate) {
            System.out.println(line.getFormattedLedger());
        }
    }

    public static void getTransactionsByPreviousYear() {

        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minusYears(1).withDayOfMonth(1);
        // this line of code takes today and subtracts 1 year,
        // and creates a day for that year of month: 1

        List<Ledger> sortedPreviousYear = getSortedDates(previousYear, today);
        sortedPreviousYear.sort(Comparator.comparing(Ledger::date));
        System.out.println(StyledUI.styledBoxTitle("Transactions for: " + previousYear + " - " + today));
        for (Ledger line : sortedPreviousYear) {
            System.out.println(line.getFormattedLedger());
        }
    }

    public static void getTransactionsByVendor(List<Ledger> ledger) {
        String inputVendorSearchPrompt = """
                Please input a vendor for search:""";
        String input = console.promptForString(inputVendorSearchPrompt);

        System.out.println(StyledUI.styledBoxTitle("Transactions by Vendor: " + input));
        boolean found = false;
        for (Ledger line : ledger) {
            if (line != null && line.vendor().toLowerCase().contains(input.toLowerCase())) {
                if (!found) {
                    System.out.println(line.getFormattedLedger());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("There is no history of transactions from " + input);
        }
    }

    public static void getTransactionsByCustomSearch(List<Ledger> ledger) {
        Scanner scanner = new Scanner(System.in);

        // create ArrayList<> to hold the filtered search
        List<Ledger> filteredSearch = new ArrayList<>(ledger);

        System.out.println(StyledUI.styledBoxTitle("Transactions by Custom Search"));

        // prompt user for individual search criteria
        System.out.print("Enter Start date or leave blank: ");
        String startDate = scanner.nextLine().trim();
        while (!startDate.isBlank()) {
            try {
                LocalDate start = LocalDate.parse(startDate, FORMATTER);
                filteredSearch.removeIf(entry -> entry.date().isBefore(start));
                // the .removeIf method from the List<> interface will remove any entry before the given startDate
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Try again");
                startDate = scanner.nextLine().trim();
            }
        }

        System.out.print("Enter End date or leave blank: ");
        String endDate = scanner.nextLine().trim();
        while (!endDate.isBlank()) {
            try {
                LocalDate end = LocalDate.parse(endDate, FORMATTER);
                filteredSearch.removeIf(entry -> entry.date().isAfter(end));
                // the .removeIf method from the List<> interface will remove any entry after the given endDate
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Try again.");
                endDate = scanner.nextLine().trim();
            }
        }

        System.out.print("Enter description or leave blank: ");
        String description = scanner.nextLine().toLowerCase();
        if(!description.isBlank()) {
            filteredSearch.removeIf(entry -> !entry.description().toLowerCase().contains(description));
            // the .removeIf method from the List<> interface will remove any entry
            // that does not contain what the user input
        }

        System.out.print("Enter vendor or leave blank: ");
        String vendor = scanner.nextLine().toLowerCase();
        if (!vendor.isBlank()) {
            filteredSearch.removeIf(entry -> entry == null || !entry.vendor().toLowerCase().contains(vendor));
            // the .removeIf method from the List<> interface will remove any entry
            // that does not contain what the user input
        }

            System.out.print("Enter amount or leave blank: ");
            String amount = scanner.nextLine();
            while(!amount.isBlank()){
                try {
                    double inputAmount = Double.parseDouble(amount);
                    filteredSearch.removeIf(entry -> entry == null || entry.amount() != inputAmount);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount entered. Try again");
                    amount = scanner.nextLine().trim();
                }
            }

            if (filteredSearch.isEmpty()) {
                System.out.println("There is not enough data to search");
            } else {
                System.out.println("Search results:");
                for (Ledger line : filteredSearch) {
                    System.out.println(line.getFormattedLedger());
                }
            }
        }
    }

