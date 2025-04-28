package com.pluralsight;

public class Ledger {

    private String date;
    private double time;
    private String vendor;
    private double amount;

    public Ledger(String date, double time, String vendor, double amount){

        this.date = date;
        this.time = time;
        this.vendor = vendor;
        this.amount = amount;

    }

    public String getDate() {
        return date;
    }

    public double getTime() {
        return time;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public String getFormattedLedger(){
        return
                String.format("%f | %f | %s | %f", this.date, this.time, this.vendor, this.amount);
    }

}
