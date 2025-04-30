package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record Ledger(LocalDate date, LocalTime time, String description, String vendor, double amount) {
// record Class erases the need to create a physical constructor + getters/setters
// this class is best when storing and retrieving data - there is no additional logic (just fields and logic)
// a full class is best to add complex logic, validation, or other methods

    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String getFormattedLedger() {
        return String.format("%-12s | %-8s | %-20s | %-10s | %8.2f\n", this.date, this.time.format(timeFormatter), this.description, this.vendor, this.amount);
    }

    public static String getFormattedLedgerTextHeader(){
        String header = String.format("%-10s | %-8s | %-20s | %-10s | %8s"
                , "DATE"
                , "TIME"
                , "DESCRIPTION"
                , "VENDOR"
                , "AMOUNT($)");
        String border = "+" + "-".repeat(header.length() + 2) + "+";
        return border + "\n" + "| " + header + " |" + "\n" + border;
    }

    public String toString() {
        return String.format("%s|%s|%s|%s|%.3f"
                , this.date()
                , this.time()
                , this.description()
                , this.vendor()
                , this.amount());
    }
}
