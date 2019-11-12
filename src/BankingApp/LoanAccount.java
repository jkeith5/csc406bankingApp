package BankingApp;

import java.io.Serializable;

public class LoanAccount implements Serializable {

    public String custID;
    public double initialLoanAmt; // this is also the credit limit for credit card accounts
    public double currentBalance;
    public double interestRate;
    public String paymentDueDate;
    public String paymentNoticeDate;
    public double amountDue;
    public String lastPaymentDate;
    public boolean hasMissedPayment=false;
    public String loanAccountType; // STL= short term loan LTL= long term loan CCL= credit card loan
    public String loanAccountID;
    public boolean isNull = false;

    public LoanAccount(){
        //
        isNull=true;
        setAllNull();
    }

    public LoanAccount(String custID) {
        setAllNull();
        setCustID(custID);
    }


    public LoanAccount(String custID, double initialLoanAmt, double currentBalance, double interestRate,boolean hasMissedPayment, String loanAccountType) {
        this.custID = custID;
        this.initialLoanAmt = initialLoanAmt;
        this.currentBalance = currentBalance;
        this.interestRate = interestRate;
        this.paymentDueDate = "null";
        this.paymentNoticeDate = "null";
        this.amountDue = 0.0;
        this.lastPaymentDate = "null";
        this.hasMissedPayment=hasMissedPayment;
        this.loanAccountType = loanAccountType;
        this.loanAccountID="temp";
    }

    public LoanAccount(String custID, double initialLoanAmt, double currentBalance, double interestRate,
                       String paymentDueDate, String paymentNoticeDate, double amountDue, String lastPaymentDate,
                       boolean hasMissedPayment, String loanAccountType) {
        setCustID(custID);
        setInitialLoanAmt(initialLoanAmt);
        setCurrentBalance(currentBalance);
        setInterestRate(interestRate);
        setPaymentDueDate(paymentDueDate);
        setPaymentNoticeDate(paymentNoticeDate);
        setAmountDue(amountDue);
        setLastPaymentDate(lastPaymentDate);
        setHasMissedPayment(hasMissedPayment);
        setLoanAccountType(loanAccountType);
    }


    //custID,initLoanAmt,CurrentBalance,InterestRate,PmtDueDate,DateOfNotice,AmtDue,LastPmtDate,HasMissedPmt,LoanAcctType
    public LoanAccount(String custID, String initialLoanAmt, String currentBalance, String interestRate,
                       String paymentDueDate, String paymentNoticeDate, String amountDue, String lastPaymentDate,
                       String hasMissedPayment, String loanAccountType, String loanAccountId) {
        setCustID(custID);
        setInitialLoanAmt(initialLoanAmt);
        setCurrentBalance(currentBalance);
        setInterestRate(interestRate);
        setPaymentDueDate(paymentDueDate);
        setPaymentNoticeDate(paymentNoticeDate);
        setAmountDue(amountDue);
        setLastPaymentDate(lastPaymentDate);
        setHasMissedPayment(hasMissedPayment);
        setLoanAccountType(loanAccountType);
        setLoanAccountID(loanAccountId);
    }

    public String getLoanAccountType() {
        return loanAccountType;
    }

    public void setLoanAccountType(String loanAccountType) {
        this.loanAccountType = loanAccountType;
        calcNullValue();
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = DataEntryDriver.fixSSN(custID);
        calcNullValue();
    }

    public double getInitialLoanAmt() {
        return initialLoanAmt;
    }

    public void setInitialLoanAmt(double initialLoanAmt) {
        this.initialLoanAmt = initialLoanAmt;
    }
    public void setInitialLoanAmt(String initialLoanAmt) {
        try {
            this.initialLoanAmt = Double.parseDouble(initialLoanAmt);
        } catch (NumberFormatException e) {
            this.initialLoanAmt=0.0;
        }
    }

    public String getLoanAccountID() {
        return loanAccountID;
    }

    public void setLoanAccountID(String loanAccountID) {
        this.loanAccountID = loanAccountID;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    public void setCurrentBalance(String currentBalance) {
        try {
            this.currentBalance = Double.parseDouble(currentBalance);
        } catch (NumberFormatException e) {
            this.currentBalance=0.0;
        }
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    public void setInterestRate(String interestRate) {
        try {
            this.interestRate = Double.parseDouble(interestRate);
        } catch (NumberFormatException e) {
            this.interestRate=0.0;
        }
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
    public void setAmountDue(String amountDue) {
        try {
            this.amountDue = Double.parseDouble(amountDue);
        } catch (NumberFormatException e) {
            this.amountDue = 0.0;
        }
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
    public void setHasMissedPayment(String hasMissedPayment) {
        this.hasMissedPayment = Boolean.parseBoolean(hasMissedPayment);
    }

    public void setAllNull(){
        this.loanAccountType = "null";
        this.custID = "null";
        this.paymentDueDate = "null";
        this.paymentNoticeDate = "null";
        this.lastPaymentDate = "null";
        this.initialLoanAmt = 0.0;
        this.currentBalance = 0.0;
        this.interestRate = 0.0;
        this.amountDue = 0.0;
        this.hasMissedPayment = false;
    }

    // will use these calcNullValue methods to find if the overall object is considered Null, so we can't try to
    // display data in the GUI that has null values which would cause issues.
    public void calcNullValue(){
        this.isNull=false;

        if(loanAccountType!=null){
            if(loanAccountType.equalsIgnoreCase("null")){
                this.isNull=true;
            }
        }else{
            this.isNull=true;
        }

        if(custID!=null){
            if(custID.equalsIgnoreCase("null")){
                this.isNull=true;
            }
        }else{
            this.isNull=true;
        }

    }



    public String getType(){
        if(this.loanAccountType.equals("STL")){
            return "Short Term Loan";
        }
        if(this.loanAccountType.equals("LTL")){
            return "Long Term Loan";
        }
        if(this.loanAccountType.equals("CCL")){
            return "Credit Card";
        }else{
            return "null";
        }
    }


    @Override
    public String toString() {
        return "LoanAccount{" +
                "loanAccountType='" + loanAccountType + '\'' +
                ", custID='" + custID + '\'' +
                ", initialLoanAmt=" + initialLoanAmt +
                ", currentBalance=" + currentBalance +
                ", interestRate=" + interestRate +
                ", paymentDueDate='" + paymentDueDate + '\'' +
                ", paymentNoticeDate='" + paymentNoticeDate + '\'' +
                ", amountDue=" + amountDue +
                ", lastPaymentDate='" + lastPaymentDate + '\'' +
                ", hasMissedPayment=" + hasMissedPayment +
                '}';
    }



}
