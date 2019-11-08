package BankingApp;

import java.io.Serializable;

public class Transaction implements Serializable {

    public String transactionType; // W=Withdrawal D=Deposit F=Fee
    public double amount;
    public String description;
    public String transactionAccount; // S=Savings C=Checking

    // make methods to complete the actual transaction using variable such as
    // Main.customerAccount


    public Transaction(){
        //
    }

    public Transaction(String transactionType,double amount,String desc,String transactionAcct){
        setTransactionType(transactionType);
        setAmount(amount);
        setDescription(desc);
        setTransactionAccount(transactionAcct);
    }
    public Transaction(String transactionType,String amount,String desc,String transactionAcct){
        setTransactionType(transactionType);
        setAmount(amount);
        setDescription(desc);
        setTransactionAccount(transactionAcct);
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
        this.amount = amount;
    }
    public void setAmount(String amount) {
        try {
            this.amount = Double.parseDouble(amount);
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
                '}';
    }


}
