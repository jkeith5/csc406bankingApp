package BankingApp;

public class LoanAccount {
    public String loanAccountType; // STL= short term loan LTL= long term loan CCL= credit card loan
    public String custID;
    public double initialLoanAmt; // this is also the credit limit for credit card accounts
    public double currentBalance;
    public double interestRate;
    public String paymentDueDate;
    public String paymentNoticeDate;
    public double amountDue;
    public String lastPaymentDate;
    public boolean hasMissedPayment;


    public LoanAccount(){
        //
    }

    public LoanAccount(String loanAccountType, String custID, double initialLoanAmt, double currentBalance,
                       double interestRate, String paymentDueDate, String paymentNoticeDate, double amountDue,
                       String lastPaymentDate, boolean hasMissedPayment) {
        this.loanAccountType = loanAccountType;
        this.custID = custID;
        this.initialLoanAmt = initialLoanAmt;
        this.currentBalance = currentBalance;
        this.interestRate = interestRate;
        this.paymentDueDate = paymentDueDate;
        this.paymentNoticeDate = paymentNoticeDate;
        this.amountDue = amountDue;
        this.lastPaymentDate = lastPaymentDate;
        this.hasMissedPayment = hasMissedPayment;
    }

    public String getLoanAccountType() {
        return loanAccountType;
    }

    public void setLoanAccountType(String loanAccountType) {
        this.loanAccountType = loanAccountType;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public double getInitialLoanAmt() {
        return initialLoanAmt;
    }

    public void setInitialLoanAmt(double initialLoanAmt) {
        this.initialLoanAmt = initialLoanAmt;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getPaymentNoticeDate() {
        return paymentNoticeDate;
    }

    public void setPaymentNoticeDate(String paymentNoticeDate) {
        this.paymentNoticeDate = paymentNoticeDate;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public boolean isHasMissedPayment() {
        return hasMissedPayment;
    }

    public void setHasMissedPayment(boolean hasMissedPayment) {
        this.hasMissedPayment = hasMissedPayment;
    }
}
