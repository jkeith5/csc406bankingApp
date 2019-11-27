package BankingApp;

import java.io.Serializable;

public class CheckingAccount implements Serializable {

    public String custID;//ssn
    public String checkingAcctID; //We want this as a string object because its in format 000-00
    public String checkingAcctIDFixed; // same as above but this one will be auto set in main and not touch the id that is
    // read in by the csv files.
    public double accountBalance;
    public String dateOpened;
    public boolean isGoldAccount;
    public boolean backupSavingsEnabled;
    public int overdraftsOnAcct;
    public boolean isNull = false;



    public CheckingAccount(){
        setAllNull();

    }


    // used for reading in data
    public CheckingAccount(String custID, String checkingAcctID, String accountBalance, String dateOpened, String isGoldAccount, String backupSavingsEnabled, String overdraftsOnAcct) {
        setCustID(custID);
        setCheckingAcctID(checkingAcctID);
        setAccountBalance(accountBalance);
        setDateOpened(dateOpened);
        setGoldAccount(isGoldAccount);
        setBackupSavingsEnabled(backupSavingsEnabled);
        setOverdraftsOnAcct(overdraftsOnAcct);
        this.isNull=false;
    }

    // use for adding new accounts
    public CheckingAccount(CustomerAccount customerAccount,boolean isGoldAccount, boolean backupSavingsEnabled, double startingBalance) {
        setCustID(customerAccount.getCustID());
        setGoldAccount(isGoldAccount);
        setCheckingAccountIDAuto(customerAccount);
        setAccountBalance(startingBalance);
        setDateOpened(DataEntryDriver.getDateString());
        setBackupSavingsEnabled(backupSavingsEnabled);
        setOverdraftsOnAcct(0);
        this.isNull=false;
    }


    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = DataEntryDriver.fixSSN(custID);
        calcNullValue();

    }

    public String getCheckingAcctID() {
        return checkingAcctID;
    }

    public int getCheckingAcctIDInt(){
        int returnVal = -1;

        if(this.checkingAcctID!=null){
            if(this.checkingAcctID.contains("-")){ // for the new and improved id system
                String[] split = this.checkingAcctID.split("-");
                returnVal = Integer.parseInt(split[0]);
            }else{// for the old account id numbering system
                returnVal = Integer.parseInt(this.checkingAcctID);
            }
        }
        return returnVal;
    }


    public void setCheckingAcctID(String checkingAcctID) {
        this.checkingAcctID=checkingAcctID;
    }


    // ID should come from the CustomerAccount Object financialAccountID.
    public void setCheckingAccountIDAuto(CustomerAccount customerAccount){// adds 01 to end
        String checkingAccIDString = String.valueOf(customerAccount.getFinancialAccountID());
        String checkingAccountIdFix = checkingAccIDString+"-00";
        this.checkingAcctID = checkingAccountIdFix;
        this.checkingAcctIDFixed=checkingAccountIdFix;
    }

    public String getCheckingAccountIDAuto(CustomerAccount customerAccount){// adds 01 to end
        String checkingAccIDString = String.valueOf(customerAccount.getFinancialAccountID());
        String checkingAccountIdFix = checkingAccIDString+"-00";
        return checkingAccountIdFix;
    }

    public void setCheckingAcctIDFixed(CustomerAccount customerAccount){
        String fixedID = getCheckingAccountIDAuto(customerAccount);
        this.checkingAcctIDFixed=fixedID;
    }


    public String getCheckingAcctIDFixed(){
        return this.checkingAcctIDFixed;
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
        this.dateOpened = DataEntryDriver.fixDateString(dateOpened);
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


    public void debitCreditAccount(double amount){
        this.accountBalance = this.accountBalance+amount;
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

    public void calcNullValue() throws NullPointerException{
        this.isNull = false;

        if(custID!=null){
            if(custID.equalsIgnoreCase("null")){
                this.isNull=true;
            }
        }else{
            this.isNull=true;
        }

        if(checkingAcctID!=null){
            if(checkingAcctID.equalsIgnoreCase("null")){
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


        if(overdraftsOnAcct == -1){
            this.isNull=true;
        }



    }


    public boolean isNull(){
        calcNullValue();
        return this.isNull;
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
                ", checkingAcctIDFixed='" + checkingAcctIDFixed + '\'' +
                ", accountBalance=" + accountBalance +
                ", dateOpened='" + dateOpened + '\'' +
                ", isGoldAccount=" + isGoldAccount +
                ", backupSavingsEnabled=" + backupSavingsEnabled +
                ", overdraftsOnAcct=" + overdraftsOnAcct +
                ", isNull=" + isNull +
                '}';
    }
}
