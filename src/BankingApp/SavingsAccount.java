package BankingApp;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.time.LocalDate;

public class SavingsAccount implements Serializable {

    private String custID;
    private String savingsAcctID;
    private double accountBalance; // current balance or initial balance for CD accounts
    private double interestRate;
    private String dateOpened; // date saving acct opened
    private boolean isCdAccount;
    private String cdCloseDate; // if cd date it closes

    private String savingsAcctIDFixed;


    public boolean isNull = false; // is the whole Object null

    public SavingsAccount(){
        setAllNull();
    }


    // used for reading in the savings account csv file
    public SavingsAccount(String custID, String savingsAcctID, String accountBalance, String interestRate, String dateOpened, String isCdAccount, String cdCloseDate) {
        setCustID(custID);
        setSavingsAcctID(savingsAcctID);
        setAccountBalance(accountBalance);
        setInterestRate(interestRate);
        setDateOpened(dateOpened);
        setCdAccount(isCdAccount);
        setCdCloseDate(cdCloseDate);
    }

    // use for adding new savings accounts. NOTE if isCdAccount is false, then it doesn't matter what you put for term
    public SavingsAccount(CustomerAccount customerAccount, boolean isCdAccount, double startingBalance,double interestRate,int termInYears) {
        setCustID(customerAccount.getCustID());
        setCdAccount(isCdAccount);
        setAccountBalance(startingBalance);
        setInterestRate(interestRate);
        setDateOpened(DataEntryDriver.getDateString());
        setSavingsAccountIDAuto(customerAccount);

        if(!isCdAccount){ // if its not a cd just set null values
            setCdCloseDate("null");
        }else{
            setCdCloseDate(DataEntryDriver.addMonthsToDateString(getDateOpened(),(termInYears*12)));
        }
    }


    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = DataEntryDriver.fixSSN(custID);
        calcNullValue();
    }

    public String getSavingsAcctID() {
        return savingsAcctID;
    }

    public void setSavingsAcctID(String savingsAcctID) {
        this.savingsAcctID = savingsAcctID;
        calcNullValue();
    }


    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public void setAccountBalance(String accountBalance) {
        try {
            this.accountBalance = Double.parseDouble(accountBalance);
        } catch (NumberFormatException e) {
            this.accountBalance = 0.0;
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
            this.interestRate = 0.0;
        }
    }


    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = DataEntryDriver.fixDateString(dateOpened);
        calcNullValue();
    }

    public boolean isCdAccount() {
        return isCdAccount;
    }

    public void setCdAccount(boolean cdAccount) {
        isCdAccount = cdAccount;
        calcNullValue();
    }
    public void setCdAccount(String cdAccount) {
        isCdAccount = Boolean.parseBoolean(cdAccount);
        calcNullValue();
    }

    public String getCdCloseDate() {
        return cdCloseDate;
    }

    public void setCdCloseDate(String cdCloseDate) {
        this.cdCloseDate = DataEntryDriver.fixDateString(cdCloseDate);
        calcNullValue();
    }

    // use this to debit and credit the account using negative for debit
    public void debitCreditAccount(double amount){
        this.accountBalance = this.accountBalance+amount;

    }


    // ASSUMING THE ACCOUNT BALANCE IS THE ORIGINAL BALANCE AND NOT THE CURRENT BALANCE FOR A CD ACCOUNT
    // CurVal = A+(Y*I*A)
    public double getCurrentCDValue(){
        double returnVal = 0.0;

        returnVal = accountBalance +(getCurrentYearsOfCD()*interestRate*accountBalance);

        return returnVal;
    }


    // Interest= Y*I*A
    public double getCurrentInterestEarnedOnCD(){
        double currInterest = 0.0;
        currInterest = (getCurrentYearsOfCD()*interestRate*accountBalance);
        return Math.round(currInterest*100.0)/100.0;
    }

    // fee = 1/2*(Interest to Date)
    public double getFeeForWithdrawalOfCDonThisDay(){
        double fee = 0.5*(getCurrentInterestEarnedOnCD());
        return Math.round(fee*100.0)/100.0;
    }

    public int getCurrentYearsOfCD(){
        LocalDate openDate = DataEntryDriver.getDateObjectFromString(dateOpened);
        LocalDate closeDate = DataEntryDriver.getDateObjectFromString(cdCloseDate);
        LocalDate today = DataEntryDriver.getCurrentDateObject();

        int yearsOfCD = 0;
        try {
            yearsOfCD = today.getYear() - openDate.getYear();
        } catch (Exception e) {
            yearsOfCD = 0;
        }


        return yearsOfCD;
    }

    public int getOriginalTermOfCD(){
        int term = 0;
        if(isCdAccount){
            LocalDate openDate = DataEntryDriver.getDateObjectFromString(dateOpened);
            LocalDate closeDate = DataEntryDriver.getDateObjectFromString(cdCloseDate);

            try{
                term = closeDate.getYear() - openDate.getYear();
            }catch (Exception e){
                term = 0;
            }

        }
        return term;
    }


    public void setAllNull(){
        this.custID = "null";
        this.savingsAcctID = "null";
        this.dateOpened = "null";
        this.cdCloseDate="null";
        this.isCdAccount = false;
        this.isNull=true;
        this.accountBalance = 0.0;
        this.interestRate = 0.0;
    }

    public void calcNullValue(){ // used to make sure all fields are set if not then make null true so we don't read
        //                          Null values and get NullPointerExceptions
        this.isNull = false; // set to false and trigger true if important data is null

        if(custID!=null){
            if(custID.equalsIgnoreCase("null")){
                this.isNull=true;
            }
        }else{
            this.isNull=true;
        }


        if(savingsAcctID!=null){
            if(savingsAcctID.equalsIgnoreCase("null")){
                this.isNull=true;
            }
        }else{
            this.isNull=true;
        }



        if(dateOpened!=null){
            if(dateOpened.equalsIgnoreCase("null")){
                this.isNull=true;
            }
        }else{
            this.isNull=true;
        }

        if(this.isCdAccount){
            if(cdCloseDate!=null){
                if(cdCloseDate.equalsIgnoreCase("null")){
                    this.isNull=true;
                }
            }else{
                this.isNull=true;
            }

        }
    }

    public boolean isNull() {
        calcNullValue();
        return isNull;
    }


    public void setSavingsAccountIDAuto(CustomerAccount customerAccount){// adds 01 to end and index number
        // sets the savingsAcctID and savingsAcctIDFixed id to the proper numbering system
        String savingsAcctIDString = String.valueOf(customerAccount.getFinancialAccountID());
        String savingsAccountIdFix = "";

        if(this.isCdAccount){// savings cd
            String subId = customerAccount.generateNextSavingsCDSubID();

            savingsAccountIdFix = savingsAcctIDString+"-02"+"-"+subId;
        }else{ // simple savings
            savingsAccountIdFix = savingsAcctIDString+"-01";
        }
        this.savingsAcctID = savingsAccountIdFix;
        this.savingsAcctIDFixed=savingsAccountIdFix;

    }

    // returns a string of what the correct saving id should be by my system from the customers financial id
    public String getSavingsAccountIDAuto(CustomerAccount customerAccount){
        String savingsAcctIDString = String.valueOf(customerAccount.getFinancialAccountID());
        String savingsAccountIdFix = "";
        if(this.isCdAccount){// savings cd
            String subId = customerAccount.generateNextSavingsCDSubID();
            savingsAccountIdFix = savingsAcctIDString+"-02"+"-"+subId;
        }else{ // simple savings
            savingsAccountIdFix = savingsAcctIDString+"-01";
        }
        return savingsAccountIdFix;
    }


    // used to set a different fixed ID because we can have multiple accounts. and I'm trying to still conform
    // to the database I was handed.
    public void setSavingsAcctIDFixed(CustomerAccount customerAccount){
        String fixedId = getSavingsAccountIDAuto(customerAccount);
        this.savingsAcctIDFixed = fixedId;
    }

    public String getSavingsAcctIDFixed(){
        if(savingsAcctIDFixed!=null){
            return this.savingsAcctIDFixed;
        }else{
            if(savingsAcctID.contains("-")){
                return savingsAcctID;
            }else{
                return "null";
            }
        }

    }



    public String getType(){
        calcNullValue();
        if(!this.isNull){
            if(this.isCdAccount){
                return "Savings CD";
            }else{
                return "Simple Savings";
            }
        }else{
            return "null";
        }
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "custID='" + custID + '\'' +
                ", savingsAcctID='" + savingsAcctID + '\'' +
                ", accountBalance=" + accountBalance +
                ", interestRate=" + interestRate +
                ", dateOpened='" + dateOpened + '\'' +
                ", isCdAccount=" + isCdAccount +
                ", cdCloseDate='" + cdCloseDate + '\'' +
                ", savingsAcctIDFixed='" + savingsAcctIDFixed + '\'' +
                ", isNull=" + isNull +
                '}';
    }

    public String toStringPrettyPrint(){
        String result = "CustID: "+custID+" FixedID: "+savingsAcctIDFixed+" balance: "+accountBalance+" interest: "+interestRate
                +" dateOpened: "+dateOpened+" isCd: "+isCdAccount+" cdCloseDate: "+cdCloseDate;
        return result;
    }

    // print in table form as would appear in the csv file.
    public String toStringTableFormat(){
        String result = String.format("CustID: %-12s SavingsAccID: %-8.8s AccBalance: %-10.2f InterestRate: %-7.2f" +
                " DateOpened: %-11.11s isCD: %-5.5b CdCloseDate: %-11.11s",custID,savingsAcctID,accountBalance,interestRate,
                dateOpened,isCdAccount,cdCloseDate);


        return result;
    }


    public String toStringCSV(){
        String result = String.format("%s,%s,%.2f,%.5f,%s,%b,%s",custID,savingsAcctIDFixed,accountBalance,interestRate,
                dateOpened,isCdAccount,cdCloseDate);
        return result;
    }


    public static String toStringCSVHeader(){
        String result = "custID,savingsAcctId,accountBalance,interest,dateOpened,isCD,cdCloseDate";
        return result;
    }





}
