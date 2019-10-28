package BankingApp;

import java.io.Serializable;

public class SavingsAccount implements Serializable {

    public String custID;
    public String savingsAcctID;
    public double accountBalance;
    public double interestRate;
    public String dateOpened;
    public boolean isCdAccount;
    public String cdCloseDate;

    public boolean isNull = false; // is the whole Object null

    public SavingsAccount(){
        setAllNull();
    }

    public SavingsAccount(String custID, String savingsAcctID, double accountBalance, double interestRate, String dateOpened, boolean isCdAccount) {
        this.custID = custID;
        this.savingsAcctID = savingsAcctID;
        this.accountBalance = accountBalance;
        this.interestRate = interestRate;
        this.dateOpened = dateOpened;
        this.isCdAccount = isCdAccount;
        this.cdCloseDate="null";
    }

    public SavingsAccount(String custID, String savingsAcctID, double accountBalance, double interestRate, String dateOpened, boolean isCdAccount, String cdCloseDate) {
        this.custID = custID;
        this.savingsAcctID = savingsAcctID;
        this.accountBalance = accountBalance;
        this.interestRate = interestRate;
        this.dateOpened = dateOpened;
        this.isCdAccount = isCdAccount;
        this.cdCloseDate = cdCloseDate;
    }
    public SavingsAccount(String custID, String savingsAcctID, String accountBalance, String interestRate, String dateOpened, String isCdAccount, String cdCloseDate) {
        this.custID = custID;
        this.savingsAcctID = savingsAcctID;
        setAccountBalance(accountBalance);
        setInterestRate(interestRate);
        this.dateOpened = dateOpened;
        setCdAccount(isCdAccount);
        this.cdCloseDate = cdCloseDate;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
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
        this.dateOpened = dateOpened;
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
        this.cdCloseDate = cdCloseDate;
        calcNullValue();
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
        if(this.isCdAccount){
            if(this.custID.equals("null") || this.savingsAcctID.equals("null") || this.dateOpened.equals("null") ||
                    this.cdCloseDate.equals("null")){
                this.isNull = true;
            }else{
                this.isNull = false;
            }
        }else{
            if(this.custID.equals("null") || this.savingsAcctID.equals("null") || this.dateOpened.equals("null")){
                this.isNull = true;
            }else{
                this.isNull = false;
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
                '}';
    }
}
