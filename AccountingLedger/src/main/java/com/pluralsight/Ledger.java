package com.pluralsight;

public record Ledger(String date, String time, String description, String vendor, double amount) {
// record Class erases the need to create a physical constructor + getters/setters
// this class is best when storing and retrieving data - there is no additional logic (just fields and logic)
// a full class is best to add complex logic, validation, or other methods
    public String getFormattedLedger() {
        return
                String.format("%s|%s|%s|%s|%.3f", this.date, this.time, this.description, this.vendor, this.amount);
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
