package model;

import java.util.Date;

public class Repayment {
    private int repaymentId;
    private int loanId;
    private double amount;
    private Date date;

    // Constructor for new repayment
    public Repayment(int loanId, double amount) {
        this.loanId = loanId;
        this.amount = amount;
        this.date = new Date();
    }

    // Constructor for retrieving from database
    public Repayment(int repaymentId, int loanId, double amount, Date date) {
        this.repaymentId = repaymentId;
        this.loanId = loanId;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
    public int getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(int repaymentId) {
        this.repaymentId = repaymentId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
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
}
