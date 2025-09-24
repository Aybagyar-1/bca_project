package model;

import java.util.Date;

public class Loan {
    private int loanId;
    private int memberId;
    private double amountRequested;
    private Double amountApproved; // Nullable, so using Double
    private int repaymentMonths;
    private String status;
    private Date applicationDate;

    // Constructor for creating a new loan (e.g., during application)
    public Loan(int memberId, double amountRequested, int repaymentMonths) {
        this.memberId = memberId;
        this.amountRequested = amountRequested;
        this.repaymentMonths = repaymentMonths;
        this.status = "pending";
        this.applicationDate = new Date(); // Current date
    }

    // Constructor for retrieving loans from database
    public Loan(int loanId, int memberId, double amountRequested, Double amountApproved,
                int repaymentMonths, String status, Date applicationDate) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.amountRequested = amountRequested;
        this.amountApproved = amountApproved;
        this.repaymentMonths = repaymentMonths;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    // Getters and Setters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(double amountRequested) {
        this.amountRequested = amountRequested;
    }

    public Double getAmountApproved() {
        return amountApproved;
    }

    public void setAmountApproved(Double amountApproved) {
        this.amountApproved = amountApproved;
    }

    public int getRepaymentMonths() {
        return repaymentMonths;
    }

    public void setRepaymentMonths(int repaymentMonths) {
        this.repaymentMonths = repaymentMonths;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
}
