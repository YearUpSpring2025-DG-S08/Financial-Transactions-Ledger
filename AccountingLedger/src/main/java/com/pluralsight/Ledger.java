package com.pluralsight;

public class Ledger {

    private final String date;
    private final String time;
    private final String description;
    private final String vendor;
    private final double amount;


    public Ledger(String date, String time, String description, String vendor, double amount){

        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public String getFormattedLedger(){
        return
                String.format("%s|%s|%s|%s|%.3f", this.date, this.time, this.description, this.vendor, this.amount);
    }

    public String toString(){
        return String.format("%s|%s|%s|%s|%.3f"
        , this.getDate()
        , this.getTime()
        , this.getDescription()
        , this.getVendor()
        , this.getAmount());
    }
}
