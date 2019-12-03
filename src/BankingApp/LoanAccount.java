package BankingApp;

import java.io.Serializable;
import java.time.LocalDate;

public class LoanAccount implements Serializable {

    private String custID;
    private double initialLoanAmt; // this is also the credit limit for credit card accounts
    private double currentBalance; // current loan balance. what is left on STL or LTL. or the current balance on a CCL
    private double interestRate;
    private String paymentDueDate;
    private String paymentNoticeDate;
    private double amountDue;
    private String lastPaymentDate; // don't update if making extra payments in a month
    private boolean hasMissedPayment=false;
    private String loanAccountType; // STL= short term loan LTL= long term loan CCL= credit card loan
    private String loanAccountID;
    private String loanAccountIDFixed;
    private boolean isNull = false;
    private int loanTerm; // -1 if not applicable
    private String dateOpened;

    private double totalLoanAmountPlusInterest; // this amount will not apply to CCL but will still exist


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
        setInitialLoanAmt(initialLoanAmt); // or credit limit for CCL
        setInterestRate(interestRate);
        setLoanAccountType(loanAccountType);
        setLoanAccountIDAuto(customerAccount);
        setHasMissedPayment(false);
        setDateOpened(DataEntryDriver.getDateString()); // set to current date
        setLastPaymentDate("null");

        // need to make methods to set the first due date and amount due based on interest
        // method of interest will be Pmt = (.8*B*I/12)+B*0.02 that is interest plus 2% of balance
        // methods to debit and credit loan accounts

        calculatePaymentNoticeDate(); // notices sent on the first
        calculateNextPaymentDueDate();

        if(loanAccountType.equals("CCL")){ // if credit card loan type
            setLoanTerm(-1);
            setCurrentBalance(0.0);
            setAmountDue(0.0);
        }else{ // if Short term or Long term loan type
            setLoanTerm(loanTerm);
            setCurrentBalance(initialLoanAmt);
            calculateInitialPaymentPlan();
        }




    }

    // finds and sets the monthly payment ON STL AND LTL
    private void calculateInitialPaymentPlan(){
        if(!loanAccountType.equals("CCL")){ // is a STL or LTL
            setTotalLoanAmountPlusInterest(); // sets the total loan amount plus interest
            int termMonths = getLoanTerm()*12; // converts years to months
            double paymentPlanAmt = totalLoanAmountPlusInterest/termMonths; // divide total by loan term to get total monthly pmt
            setAmountDue(paymentPlanAmt); // set that amount as the amount due. And this should be a Fixed Payment
        }
    }


    // is a CCL  using formula Pmt = (.8*B*I/12)+B*0.02 which is interest plus 2% of balance
    // the formula just includes the interest and they must also pay a set amount of the balance
    // so I am using the standard of 2% of the balance as a minimum payment
    public void calculateCreditCardPayment(){
        if(loanAccountType.equals("CCL")){
            double payment = (currentBalance*0.8*interestRate/12.0)+currentBalance*0.02;
            setAmountDue(payment);
        }
    }

    // things this needs to do. Make a payment, Return double on how much of the input payment was processed
    // so assume currBal is 300. makePayment(200) returns 200. makePayment(500) returns 300
    // set the last payment date to current date. I think that's it. The Transaction and fees should be handled
    // in finance driver which is the ONLY class that should call this
    public double makePayment(double paymentValue){
        double returnVal = paymentValue; // set it equal for now.
        double currentBal = currentBalance;
        if(paymentValue<=currentBal){// only allow to pay the balance off. NOT TO DO A CREDIT OF ANY KIND!
            setCurrentBalance(currentBalance-Math.abs(paymentValue));// make sure payment value is not negative
            // set payment date
            // only set last payment date once per month can accept multiple payments but set once
            if(!lastPaymentDate.equals("null")){ // this was not the first payment
                LocalDate lastPaymentDateObj = DataEntryDriver.getDateObjectFromString(lastPaymentDate);
                LocalDate today = DataEntryDriver.getCurrentDateObject();
                int monthOfLast = lastPaymentDateObj.getMonthValue();
                int monthOfToday = today.getMonthValue();

                if(monthOfLast!=monthOfToday){ // if the month is not the same as the last payment then update the last pmt date
                    setLastPaymentDate(DataEntryDriver.getDateString()); // set payment date
                    setNextPaymentDueDate(); // sets to the 10th or 27th of next month based on loan type
                    calculateCreditCardPayment(); // recalc credit card payment. the method checks to see if its CCL type
                }
            }else{ // this was the first payment
                setLastPaymentDate(DataEntryDriver.getDateString());
                setNextPaymentDueDate();
                calculateCreditCardPayment();
            }
        }else{ // else trying to pay more that what is left on the balance
            double paymentValueFixed = getCurrentBalance(); // get the current balance
            double paymentNotAppliedToLoan = paymentValue-paymentValueFixed; // amount over paid. WILL BE REJECTED
            // so pay off loan and inform user that portion of payment used to pay off loan and the rest was not processed
            // example currentBal = 150 payment = 200 ==== currentBal = 0.0 inform user that 50 was not processed and loan is closed.
            setCurrentBalance(0.0);
            returnVal = paymentValueFixed; // return what was actually applied to the loan so we can correct the Transaction in FinanceDriver
            System.err.printf("This Loan is now paid off. Note: $%.2f was overPaid and was not processed. Only $%.2f was " +
                    "applied to the loan.\n",paymentNotAppliedToLoan,paymentValueFixed);

        }

        return returnVal;
    }

    // Only used For Credit Card Purchases
    // POSSIBLE maybe recalc the payment due every time a debit is made so that the value for payment due is
    // always correct at any point.
    // return double of amount processed
    public double debitCCLAccount(double debitAmount){
        double amountProcessed = Math.abs(debitAmount);
        double debitAmtFixed = Math.abs(debitAmount);
        if(loanAccountType.equals("CCL")){ // only run on credit card loan
            if(currentBalance+debitAmtFixed<=initialLoanAmt){// if new balance is less than or equal to the credit limit
                currentBalance = currentBalance+debitAmtFixed;
            }else{ // reject transaction and return -1.0 to indicate rejected debit
                amountProcessed=-1.0;
            }
        }

        return amountProcessed;
    }

    public int isPaymentLate(){ // 0 not behind or int value of the months behind.
        int returnVal = 0;
        LocalDate today = DataEntryDriver.getCurrentDateObject();
        LocalDate lastPaymentDateObj = DataEntryDriver.getDateObjectFromString(getLastPaymentDate());
        LocalDate paymentDueDateObj = DataEntryDriver.getDateObjectFromString(getPaymentDueDate());
        int lateMonths = 0;

        if(currentBalance>0){ // first check if current balance is over 0 if not then they can't be late
            if(!lastPaymentDate.equals("null")){ // if last payment date is not null
                boolean beforeDueDate = today.isBefore(paymentDueDateObj);
                if(loanAccountType.equals("CCL")){ // 10th
                    if(lastPaymentDateObj.getDayOfMonth()>10){ // if last payment date was after the 10th
                        lateMonths++;
                    }

                }else{ // 27th
                    if(lastPaymentDateObj.getDayOfMonth()>27){ // last payment date was after 27 so that month was late
                        lateMonths ++;
                    }
                }
                // now because I've already accounted for the late payment in the last month they made a payment
                // I will set the new last payment temp date to the 15th of the month they last paid
                // to avoid skipping months when doing the math.
                LocalDate lastPaymentDueDateFixed = paymentDueDateObj;

                if(today.isBefore(paymentDueDateObj)){
                    // already accounted for late payment after day of due date. So now figure the months behind if any
                    // minus one because due date is one month ahead
                    lastPaymentDueDateFixed = lastPaymentDueDateFixed.withDayOfMonth(15).minusMonths(1);
                    int monthOfLastPmt = lastPaymentDateObj.getMonthValue(); // get the int value of the month
                    int monthOfFixedLastDueDate = lastPaymentDueDateFixed.getMonthValue();
                    int monthsBehind = monthOfFixedLastDueDate-monthOfLastPmt; // subtract them
                    lateMonths += monthsBehind;
                }

            }else{// last payment date was null so hasn't made one yet
                if(today.isAfter(paymentDueDateObj)){
                    lateMonths++;
                }
            }

        }
        if(lateMonths>0){
            returnVal = lateMonths;
        }else{
            returnVal = 0;
        }

        //System.out.println("\n"+toStringPrettyPrint());
        //System.out.println("late months "+lateMonths);

        return returnVal;
    }



    // call this to recalculate the payment due date
    // CCL due on 10th other on the 27th
    // MOSTLY USED WHEN CREATING A LOAN OBJECT NOT FOR MAKING PAYMENTS
    // FOR MAKING PAYMENT USE THE setNextPaymentDueDate
    // can also use this to UPDATE the payment due dates or refresh them, but does not
    // increment them. to increment them after making a payment use the method mentioned above.
    // this is more of a general fix all method and will set the date regardless of if payment was made
    public void calculateNextPaymentDueDate(){
        LocalDate today = DataEntryDriver.getCurrentDateObject();
        int todayDay = today.getDayOfMonth();
        LocalDate nextPayment = null;
        if(loanAccountType.equals("CCL")){ // ccl
            if(todayDay>10){ // if its past the 10th for ccl then set to next month on the 10th
                nextPayment = today.plusMonths(1).withDayOfMonth(10);
            }else{
                nextPayment = today.withDayOfMonth(10);
            }
        }else{ // STL LTL
            if(todayDay>27){
                nextPayment = today.plusMonths(1).withDayOfMonth(27);
            }else{
                nextPayment = today.withDayOfMonth(27);
            }
        }

        this.paymentDueDate = DataEntryDriver.getStringFromLocalDateFormatted(nextPayment);

    }

    public void setNextPaymentDueDate(){
        LocalDate today = DataEntryDriver.getCurrentDateObject();
        LocalDate nextDueDate = null;
        if(loanAccountType.equals("CCL")){
            nextDueDate = today.withDayOfMonth(15).plusMonths(1).withDayOfMonth(10);
            // using with day of month as 15 because we don't know what day they make the payment.
            // so if payment is made on January 31st we would end up skipping the month of February
            // when adding 30 days. so doing the calculations on the 15th of the month will fix this
        }else{ // else its STL or LTL
            nextDueDate = today.withDayOfMonth(15).plusMonths(1).withDayOfMonth(27);
        }
        // then set the next payment due date to the formatted mm/dd/yyyy date
        this.paymentDueDate=DataEntryDriver.getStringFromLocalDateFormatted(nextDueDate);

    }


    private void setTotalLoanAmountPlusInterest(){
        this.totalLoanAmountPlusInterest=initialLoanAmt*(1.0+interestRate); // figures total loan amount plus interest
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
        this.paymentDueDate = DataEntryDriver.fixDateString(paymentDueDate);
    }




    public String getPaymentNoticeDate() {
        return paymentNoticeDate;
    }

    public void setPaymentNoticeDate(String paymentNoticeDate) {
        this.paymentNoticeDate = DataEntryDriver.fixDateString(paymentNoticeDate);
    }


    // payment notices are automatically sent at first of month.
    public void calculatePaymentNoticeDate(){
        LocalDate today = DataEntryDriver.getCurrentDateObject(); // get current date
        this.paymentNoticeDate = DataEntryDriver.getStringFromLocalDateFormatted(today.withDayOfMonth(1));
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
        this.lastPaymentDate = DataEntryDriver.fixDateString(lastPaymentDate);
    }

    public boolean hasMissedPayment() {
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
                ", totalLoanAmountPlusInterest=" + totalLoanAmountPlusInterest +
                '}';
    }

    public String toStringPrettyPrint(){
        String result = "LoanIDFixed: "+loanAccountIDFixed+" LoanAccountType: "+loanAccountType+" DateOpened: "+dateOpened+
                " initialAmount: "+initialLoanAmt+" TotalLoanAmount: "+totalLoanAmountPlusInterest+" Interest: "+interestRate+
                " CurrentBal: "+currentBalance+" AmountDue: "+amountDue+" LoanTerm: "+loanTerm+" LastPmtDate: "+lastPaymentDate+
                " PmtNoticeDate: "+paymentNoticeDate+" PmtDueDate: "+paymentDueDate;

        return result;
    }


    // table format as would appear in csv file
    public String toStringTableFormat(){
        String result = String.format("CustID: %-12s InitLoanAmt: %-10.2f CurrentBalance: %-10.2f InterestRate: %-7.2f" +
                " PmtDueDate: %-11.11s DateOfNotice: %-11.11s AmtDue: %-10.2f LastPmtDate: %-11.11s HasMissedPmt: %-5.5b" +
                " LoanAcctType: %-4.4s LoanAcctID: %-8.8s LoanTermYears: %-3d DateOpened: %-11.11s",custID,initialLoanAmt,
                currentBalance,interestRate,paymentDueDate,paymentNoticeDate,amountDue,lastPaymentDate,hasMissedPayment,
                loanAccountType,loanAccountID,loanTerm,dateOpened);
        return result;
    }



    public String toStringCSV(){
        String result = String.format("%s,%.2f,%.2f,%.5f,%s,%s,%.2f,%s,%b,%s,%s,%d,%s",
                custID,initialLoanAmt,currentBalance,interestRate, paymentDueDate,paymentNoticeDate,
                amountDue,lastPaymentDate,hasMissedPayment, loanAccountType,loanAccountIDFixed,loanTerm,dateOpened);
        return result;
    }


    public static String toStringCSVHeader(){
        String result = "custID,initialLoanAmt,currentBalance,interestRate,pmtDueDate,pmtNoticeDate,amountDue,lastPmtDate," +
                "hasMissedPmt,loanAccType,loanAccID,loanTermYears,dateOpened";
        return result;
    }



}
