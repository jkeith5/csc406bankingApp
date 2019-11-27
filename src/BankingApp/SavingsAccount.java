package BankingApp;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class SavingsAccount implements Serializable {

    public String custID;
    public String savingsAcctID;
    public double accountBalance;
    public double interestRate;
    public String dateOpened; // date saving acct opened
    public boolean isCdAccount;
    public String cdCloseDate; // if cd date it closes

    public String savingsAcctIDFixed;


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
    public String getSavingsAccIDString(){
        return String.valueOf(this.savingsAcctID);
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
        System.out.println("SetSavingsAccountID Auto");
        String savingsAcctIDString = String.valueOf(customerAccount.getFinancialAccountID());
        System.out.println("savingsACCt ID string: "+savingsAcctIDString);

        String savingsAccountIdFix = "";


        if(this.isCdAccount){// savings cd
            System.out.println("Is cd account");
            String subId = customerAccount.generateNextSavingsCDSubID();
            System.out.println("subId: "+subId);

            savingsAccountIdFix = savingsAcctIDString+"-02"+"-"+subId;
            System.out.println("savings acc id fixed: "+savingsAccountIdFix);

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
        return this.savingsAcctIDFixed;
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


}
