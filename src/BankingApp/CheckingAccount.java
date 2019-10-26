package BankingApp;

public class CheckingAccount {

    public String custID;
    public String checkingAcctID;
    public double accountBalance;
    public String dateOpened;
    public boolean isGoldAccount;
    public boolean backupSavingsEnabled;
    public int overdraftsOnAcct;



    public CheckingAccount(){
        //
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

    public CheckingAccount(String custID, String checkingAcctID, double accountBalance, String dateOpened, boolean isGoldAccount, boolean backupSavingsEnabled, int overdraftsOnAcct) {
        this.custID = custID;
        this.checkingAcctID = checkingAcctID;
        this.accountBalance = accountBalance;
        this.dateOpened = dateOpened;
        this.isGoldAccount = isGoldAccount;
        this.backupSavingsEnabled = backupSavingsEnabled;
        this.overdraftsOnAcct = overdraftsOnAcct;
    }



    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCheckingAcctID() {
        return checkingAcctID;
    }

    public void setCheckingAcctID(String checkingAcctID) {
        this.checkingAcctID = checkingAcctID;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public boolean isGoldAccount() {
        return isGoldAccount;
    }

    public void setGoldAccount(boolean goldAccount) {
        isGoldAccount = goldAccount;
    }

    public boolean isBackupSavingsEnabled() {
        return backupSavingsEnabled;
    }

    public void setBackupSavingsEnabled(boolean backupSavingsEnabled) {
        this.backupSavingsEnabled = backupSavingsEnabled;
    }

    public int getOverdraftsOnAcct() {
        return overdraftsOnAcct;
    }

    public void setOverdraftsOnAcct(int overdraftsOnAcct) {
        this.overdraftsOnAcct = overdraftsOnAcct;
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
