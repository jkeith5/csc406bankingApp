package BankingApp;

public class Transaction {

    public String transactionType; // W=Withdrawal D=Deposit
    public double amount;
    public String description;
    public String transactionAccount; // S=Savings C=Checking


    public Transaction(){
        //
    }

    public Transaction(String transactionType,double amount,String desc,String transactionAcct){
        this.transactionType = transactionType;
        this.amount=amount;
        this.description=desc;
        this.transactionAccount=transactionAcct;
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
