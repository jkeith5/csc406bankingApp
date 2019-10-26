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


    public SavingsAccount(){
        //
    }

    public SavingsAccount(String custID, String savingsAcctID, double accountBalance, double interestRate, String dateOpened, boolean isCdAccount) {
        this.custID = custID;
        this.savingsAcctID = savingsAcctID;
        this.accountBalance = accountBalance;
        this.interestRate = interestRate;
        this.dateOpened = dateOpened;
        this.isCdAccount = isCdAccount;
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

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getSavingsAcctID() {
        return savingsAcctID;
    }

    public void setSavingsAcctID(String savingsAcctID) {
        this.savingsAcctID = savingsAcctID;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public boolean isCdAccount() {
        return isCdAccount;
    }

    public void setCdAccount(boolean cdAccount) {
        isCdAccount = cdAccount;
    }

    public String getCdCloseDate() {
        return cdCloseDate;
    }

    public void setCdCloseDate(String cdCloseDate) {
        this.cdCloseDate = cdCloseDate;
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
