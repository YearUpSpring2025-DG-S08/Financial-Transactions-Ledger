package com.pluralsight;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record Ledger(LocalDate date, LocalTime time, String description, String vendor, double amount) {
// record Class erases the need to create a physical constructor + getters/setters
// this class is best when storing and retrieving data - there is no additional logic (just fields and logic)
// a full class is best to add complex logic, validation, or other methods

    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String getFormattedLedger() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formatAmount = String.valueOf(currencyFormat.format(amount));
        String colorAmount = (amount < 0 ? ColorCodes.RED : ColorCodes.GREEN) + formatAmount + ColorCodes.RESET;
        return String.format("%-12s | %-8s | %-20s | %-10s | %8s\n"
                , this.date, this.time.format(timeFormatter), this.description, this.vendor, colorAmount);
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
        return String.format("%-12s | %-8s | %-20s | %-10s | %8.2s\n"
                , this.date
                , this.time
                , this.description
                , this.vendor
                , this.amount);
    }
}
