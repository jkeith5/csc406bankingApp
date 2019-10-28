package BankingApp;

import java.io.Serializable;

public class CheckingAccount implements Serializable {

    public String custID;//ssn
    public String checkingAcctID;
    public double accountBalance;
    public String dateOpened;
    public boolean isGoldAccount;
    public boolean backupSavingsEnabled;
    public int overdraftsOnAcct;
    public boolean isNull = false;



    public CheckingAccount(){
        setAllNull();

    }

    public CheckingAccount(String custID, String checkingAcctID, double accountBalance, String dateOpened, boolean isGoldAccount, boolean backupSavingsEnabled) {
        this.custID = custID;
        this.checkingAcctID = checkingAcctID;
        this.accountBalance = accountBalance;
        this.dateOpened = dateOpened;
        this.isGoldAccount = isGoldAccount;
        this.backupSavingsEnabled = backupSavingsEnabled;
        this.overdraftsOnAcct=0;
    }
    public CheckingAccount(String custID, String checkingAcctID, String accountBalance, String dateOpened, String isGoldAccount, String backupSavingsEnabled) {
        this.custID = custID;
        this.checkingAcctID = checkingAcctID;
        setAccountBalance(accountBalance);
        this.dateOpened = dateOpened;
        setGoldAccount(isGoldAccount);
        setBackupSavingsEnabled(backupSavingsEnabled);
        this.overdraftsOnAcct=0;
    }

    public CheckingAccount(String custID, String checkingAcctID, double accountBalance, String dateOpened, boolean isGoldAccount, boolean backupSavingsEnabled, int overdraftsOnAcct) {
        this.custID = custID;
        this.checkingAcctID = checkingAcctID;
        this.accountBalance = accountBalance;
        this.dateOpened = dateOpened;
        this.isGoldAccount = isGoldAccount;
        this.backupSavingsEnabled = backupSavingsEnabled;
        this.overdraftsOnAcct = overdraftsOnAcct;
    }
    public CheckingAccount(String custID, String checkingAcctID, String accountBalance, String dateOpened, String isGoldAccount, String backupSavingsEnabled, String overdraftsOnAcct) {
        this.custID = custID;
        this.checkingAcctID = checkingAcctID;
        setAccountBalance(accountBalance);
        this.dateOpened = dateOpened;
        setGoldAccount(isGoldAccount);
        setBackupSavingsEnabled(backupSavingsEnabled);
        setOverdraftsOnAcct(overdraftsOnAcct);
    }


    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
        calcNullValue();
    }

    public String getCheckingAcctID() {
        return checkingAcctID;
    }

    public void setCheckingAcctID(String checkingAcctID) {
        this.checkingAcctID = checkingAcctID;
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

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
        calcNullValue();
    }

    public boolean isGoldAccount() {
        return isGoldAccount;
    }

    public void setGoldAccount(boolean goldAccount) {
        isGoldAccount = goldAccount;
    }
    public void setGoldAccount(String goldAccount) {
        isGoldAccount = Boolean.parseBoolean(goldAccount.toLowerCase());
    }

    public boolean isBackupSavingsEnabled() {
        return backupSavingsEnabled;
    }

    public void setBackupSavingsEnabled(boolean backupSavingsEnabled) {
        this.backupSavingsEnabled = backupSavingsEnabled;
    }

    public void setBackupSavingsEnabled(String backupSavingsEnabled) {
        this.backupSavingsEnabled = Boolean.parseBoolean(backupSavingsEnabled);
    }

    public int getOverdraftsOnAcct() {
        return overdraftsOnAcct;
    }

    public void setOverdraftsOnAcct(int overdraftsOnAcct) {
        this.overdraftsOnAcct = overdraftsOnAcct;
        calcNullValue();
    }

    public void setOverdraftsOnAcct(String overdraftsOnAcct) {
        String resultToParse;
        if(overdraftsOnAcct.contains(".")){
            resultToParse = overdraftsOnAcct.substring(0, overdraftsOnAcct.indexOf('.'));
        }else{
            resultToParse = overdraftsOnAcct;
        }
        try {
            this.overdraftsOnAcct = Integer.parseInt(resultToParse);
        } catch (NumberFormatException e) {
            this.overdraftsOnAcct = -1;
        }
        calcNullValue();
    }


    public void setAllNull(){

        this.custID = "null";
        this.checkingAcctID = "null";
        this.dateOpened = "null";
        this.accountBalance = 0.0;
        this.overdraftsOnAcct = -1;
        this.isNull = true;
        this.isGoldAccount = false;
        this.backupSavingsEnabled = false;
    }

    public void calcNullValue(){

        if(this.custID.equals("null") || this.checkingAcctID.equals("null") || this.dateOpened.equals("null") ||
                this.overdraftsOnAcct==-1){
            this.isNull=true;
        }else{
            this.isNull=false;
        }
    }


    public String getType(){

        if(!this.isNull){
            if(this.isGoldAccount){
                return "Gold/Diamond Checking";
            }else{
                return "That's My Bank Checking";
            }
        }else{
            return "null";
        }


    }


    @Override
    public String toString() {
        return "CheckingAccount{" +
                "custID='" + custID + '\'' +
                ", checkingAcctID='" + checkingAcctID + '\'' +
                ", accountBalance=" + accountBalance +
                ", dateOpened='" + dateOpened + '\'' +
                ", isGoldAccount=" + isGoldAccount +
                ", backupSavingsEnabled=" + backupSavingsEnabled +
                ", overdraftsOnAcct=" + overdraftsOnAcct +
                '}';
    }


}
