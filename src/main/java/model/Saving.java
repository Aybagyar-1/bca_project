package model;

import java.util.Date;

public class Saving {
    private int savingId;
    private int memberId;
    private double amount;
    private Date date;
    private String description;

    // Constructor for new savings entry
    public Saving(int memberId, double amount, String description) {
        this.memberId = memberId;
        this.amount = amount;
        this.description = description;
        this.date = new Date();
    }

    // Constructor for retrieving from database
    public Saving(int savingId, int memberId, double amount, Date date, String description) {
        this.savingId = savingId;
        this.memberId = memberId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public int getSavingId() {
        return savingId;
    }

    public void setSavingId(int savingId) {
        this.savingId = savingId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
