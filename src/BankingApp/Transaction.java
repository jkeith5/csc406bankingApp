package BankingApp;

import java.io.Serializable;

public class Transaction implements Serializable {
    // this class just records a fee it does not make the actual transfer of any money or fees

    public String transactionType; // W=Withdrawal D=Deposit F=Fee I=Interest TW=Transfer Withdrawal TD=Transfer Deposit
    public double amount;
    public String description;
    public String transactionAccount; // S=Savings C=Checking CCL= Credit Card STL=Short Term Loan LTL= Long Term Loan
    public String dateTime = Main.getDateTimeString();
    public String date="";

    // make methods to complete the actual transaction using variable such as
    // Main.customerAccount


    public Transaction(){

    }

    // auto adds current date
    public Transaction(String transactionType,double amount,String desc,String transactionAcct){
        setTransactionType(transactionType);
        setAmount(amount);
        setDescription(desc);
        setTransactionAccount(transactionAcct);
        setDate(DataEntryDriver.getDateString());
    }
    // auto adds current date
    public Transaction(String transactionType,String amount,String desc,String transactionAcct){
        setTransactionType(transactionType);
        setAmount(amount);
        setDescription(desc);
        setTransactionAccount(transactionAcct);
        setDate(DataEntryDriver.getDateString());
    }

    public Transaction(String transactionType,String amount,String desc,String transactionAcct,String date){
        setTransactionType(transactionType);
        setAmount(amount);
        setDescription(desc);
        setTransactionAccount(transactionAcct);
        setDate(date);
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = DataEntryDriver.fixDateString(date);
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = DataEntryDriver.round(amount,2);
    }
    public void setAmount(String amount) {
        try {
            this.amount = DataEntryDriver.round(Double.parseDouble(amount),2);
        } catch (NumberFormatException e) {
            this.amount = 0.0;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public void setTransactionAccount(String transactionAccount) {
        this.transactionAccount = transactionAccount;
    }

    public String getType(){
        if(this.transactionType.equals("W")){
            return "Withdraw";
        }else if(this.transactionType.equals("D")){
            return "Deposit";
        }else{
            return "null";
        }
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionAccount='" + transactionAccount + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String toStringPrettyPrint(){
        return "Transaction Type: "+transactionType+
                " Amount: "+amount+
                " Description: "+description+
                " Account: "+transactionAccount+
                " Date: "+date;
    }


    // shorter to fit in the interface display area
    public String toStringPrettyPrintFormattedForInterface(){
        String returnString = String.format("Type: %.3s Amount: $%7.2f Desc: %.30s Account: %.5s Date: %.11s",transactionType,
                amount,description,transactionAccount,date);

        return returnString;
    }




}
