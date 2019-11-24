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
    public String loanAccountIDFixed;
    public boolean isNull = false;
    public int loanTerm; // -1 if not applicable
    public String dateOpened;

    public double totalLoanAmountPlusInterest; // this amount will not apply to CCL but will still exist


    // Notes:
    // LTL = Long Term Mortgage Loan 15/30 year loan. Fixed interest Rate. Fixed Payment Plan (30 days).
    // if payment late add $75 lateFee to that months payment.

    // STL = Short Term Loan up to 5 year term. includes auto loans. Same specs as LTL

    // CCL = Credit Card Loan. Init loan amount is the CREDIT LIMIT. Current Balance is how much is used including interest
    // Bills sent on the first of month (NOTICE DATE). PAYMENT DUE DATE IS THE 10th of each month.
    // Bills include finance charges and all charges made during month.
    // Finance charge is calculated on average balance through the month.
    // If total balance is paid each month then NO FINANCE CHARGE IS APPLIED.



    public LoanAccount(){
        //
        isNull=true;
        setAllNull();
    }

    public LoanAccount(String custID) {
        setAllNull();
        setCustID(custID);
    }


    // used in DataEntryDriver readLoanAccountsToArrList() // manually set the loan account ID OLD SYSTEM
    //custID,initLoanAmt,CurrentBalance,InterestRate,PmtDueDate,DateOfNotice,AmtDue,LastPmtDate,HasMissedPmt,LoanAcctType
    public LoanAccount(String custID, String initialLoanAmt, String currentBalance, String interestRate,
                        String paymentDueDate, String paymentNoticeDate, String amountDue, String lastPaymentDate,
                        String hasMissedPayment, String loanAccountType, String loanAccountId,String loanTerm,String dateOpened) {
        this.isNull=false;
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
        setLoanTerm(loanTerm);
        setDateOpened(dateOpened);
        setTotalLoanAmountPlusInterest();
    }


    // used when adding a loan account
    public LoanAccount(CustomerAccount customerAccount, double initialLoanAmt, double interestRate,String loanAccountType,String loanTerm) {
        this.isNull=false;
        setCustID(customerAccount.getCustID());
        setInitialLoanAmt(initialLoanAmt);
        setCurrentBalance(initialLoanAmt);
        setInterestRate(interestRate);
        setLoanAccountType(loanAccountType);
        setLoanAccountIDAuto(customerAccount);
        setHasMissedPayment(false);
        setLastPaymentDate(DataEntryDriver.getDateString());
        setPaymentNoticeDate(DataEntryDriver.getDateString());
        setTotalLoanAmountPlusInterest();

        if(loanAccountType.equals("CCL")){
            setLoanTerm(-1);
        }else{
            setLoanTerm(loanTerm);
        }
        setDateOpened(DataEntryDriver.getDateString()); // set to current date

        // need to make methods to set the first due date and amount due based on interest
        // methods to debit and credit loan accounts

        calculateInitialPaymentPlan();


        setPaymentDueDate("12/12/2019");
        setAmountDue(1.00);
    }

    public void calculateInitialPaymentPlan(){
        setTotalLoanAmountPlusInterest();
        int termMonths = getLoanTerm()*12;
        double paymentPlanAmt = totalLoanAmountPlusInterest/termMonths;
        setAmountDue(paymentPlanAmt);

    }

    public void setPaymentDueDate(){
        // CCL due on the 10th
        // all others on the 27th

        // find this month and if past due date then increment month bit by 1
    }

    public void setTotalLoanAmountPlusInterest(){
        this.totalLoanAmountPlusInterest=initialLoanAmt+(initialLoanAmt*interestRate);
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

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public void setLoanTerm(String loanTerm){
        try {
            this.loanTerm = Integer.parseInt(loanTerm);
        } catch (NumberFormatException e) {
            this.loanTerm = -1;
        }
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        String fixedDate = DataEntryDriver.fixDateString(dateOpened);
        this.dateOpened = fixedDate;
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

    public void setLoanAccountIDAuto(CustomerAccount customerAccount){// adds 01 to end and sub id
        String loanAcctIDString = String.valueOf(customerAccount.getFinancialAccountID());
        String loanAccountIdFix = "";

        if(this.loanAccountType.equals("STL")){ // 03 short term loan
            String subId = customerAccount.generateNextLoanAcctSubId("STL");
            loanAccountIdFix = loanAcctIDString+"-03"+"-"+subId;
        }
        if(this.loanAccountType.equals("LTL")){ // 04 long term loan
            String subId = customerAccount.generateNextLoanAcctSubId("LTL");
            loanAccountIdFix = loanAcctIDString+"-04"+"-"+subId;
        }
        if(this.loanAccountType.equals("CCL")){ // 05 Credit Card loan
            loanAccountIdFix = loanAcctIDString+"-05";
        }

        this.loanAccountID=loanAccountIdFix;
        this.loanAccountIDFixed=loanAccountIdFix;

    }

    public String getLoanAccountIDAuto(CustomerAccount customerAccount){// adds 01 to end and sub id
        String loanAcctIDString = String.valueOf(customerAccount.getFinancialAccountID());
        String loanAccountIdFix = "";

        if(this.loanAccountType.equals("STL")){ // 03 short term loan
            String subId = customerAccount.generateNextLoanAcctSubId("STL");
            loanAccountIdFix = loanAcctIDString+"-03"+"-"+subId;
        }
        if(this.loanAccountType.equals("LTL")){ // 04 long term loan
            String subId = customerAccount.generateNextLoanAcctSubId("LTL");
            loanAccountIdFix = loanAcctIDString+"-04"+"-"+subId;
        }
        if(this.loanAccountType.equals("CCL")){ // 05 Credit Card loan
            loanAccountIdFix = loanAcctIDString+"-05";
        }

        return loanAccountIdFix;
    }

    public void setLoanAccountIDFixed(CustomerAccount customerAccount){
        String fixedId = getLoanAccountIDAuto(customerAccount);
        this.loanAccountIDFixed = fixedId;
    }

    public String getLoanAccountIDFixed(){
        return this.loanAccountIDFixed;
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

    public void setNextPaymentDueDate(){
        //
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
        this.loanTerm=-1;
        this.loanAccountID="null";
        this.dateOpened="null";
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

    public boolean isNull(){
        calcNullValue();
        return this.isNull;
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
                "custID='" + custID + '\'' +
                ", initialLoanAmt=" + initialLoanAmt +
                ", currentBalance=" + currentBalance +
                ", interestRate=" + interestRate +
                ", paymentDueDate='" + paymentDueDate + '\'' +
                ", paymentNoticeDate='" + paymentNoticeDate + '\'' +
                ", amountDue=" + amountDue +
                ", lastPaymentDate='" + lastPaymentDate + '\'' +
                ", hasMissedPayment=" + hasMissedPayment +
                ", loanAccountType='" + loanAccountType + '\'' +
                ", loanAccountID='" + loanAccountID + '\'' +
                ", loanAccountIDFixed='" + loanAccountIDFixed + '\'' +
                ", isNull=" + isNull +
                ", loanTerm=" + loanTerm +
                ", dateOpened='" + dateOpened + '\'' +
                '}';
    }
}
