package BankingApp;

import java.util.ArrayList;

public class CustomerAccount {
    // This will hold all data for customer account in the ArrayList of CustomerAccouts Objects
    // will have info on all accounts savings cds Checking Credit Card all all relevant info

    public String custID;
    public String firstName;
    public String lastName;
    public String streetAddr;
    public String city;
    public String state;// 2 letter All Caps MO
    public String zip;
    public String atmCardNumber;

    public boolean hasSavingsAccount;
    public boolean hasCheckingAccount;
    public boolean hasShortTermLoan;
    public boolean hasLongTermLoan;
    public boolean hasCreditCardAcct;

    public boolean hasLoanAccount;

    public SavingsAccount savingsAccount;
    public CheckingAccount checkingAccount;

    public ArrayList<Transaction> transactions= new ArrayList<Transaction>();
    public ArrayList<LoanAccount> loanAccounts= new ArrayList<LoanAccount>();
    public ArrayList<Check> checks = new ArrayList<Check>();


    public CustomerAccount(){
        // use setters and getters
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip) {
        this.custID = custID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip, String atmCardNumber) {
        this.custID = custID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.atmCardNumber = atmCardNumber;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddr() {
        return streetAddr;
    }

    public void setStreetAddr(String streetAddr) {
        this.streetAddr = streetAddr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAtmCardNumber() {
        return atmCardNumber;
    }

    public void setAtmCardNumber(String atmCardNumber) {
        this.atmCardNumber = atmCardNumber;
    }

    public boolean isHasSavingsAccount() {
        return hasSavingsAccount;
    }

    public void setHasSavingsAccount(boolean hasSavingsAccount) {
        this.hasSavingsAccount = hasSavingsAccount;
    }

    public boolean isHasCheckingAccount() {
        return hasCheckingAccount;
    }

    public void setHasCheckingAccount(boolean hasCheckingAccount) {
        this.hasCheckingAccount = hasCheckingAccount;
    }

    public boolean isHasShortTermLoan() {
        return hasShortTermLoan;
    }

    public void setHasShortTermLoan(boolean hasShortTermLoan) {
        this.hasShortTermLoan = hasShortTermLoan;
    }

    public boolean isHasLongTermLoan() {
        return hasLongTermLoan;
    }

    public void setHasLongTermLoan(boolean hasLongTermLoan) {
        this.hasLongTermLoan = hasLongTermLoan;
    }

    public boolean isHasCreditCardAcct() {
        return hasCreditCardAcct;
    }

    public void setHasCreditCardAcct(boolean hasCreditCardAcct) {
        this.hasCreditCardAcct = hasCreditCardAcct;
    }

    public boolean isHasLoanAccount() {
        return hasLoanAccount;
    }

    public void setHasLoanAccount(boolean hasLoanAccount) {
        this.hasLoanAccount = hasLoanAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransactionObject(Transaction transactionObj) {
        this.transactions.add(transactionObj);
    }

    public void addTransaction(String transactionType,double amount,String desc,String transactionAcct){
        this.transactions.add(new Transaction(transactionType,amount,desc,transactionAcct));
    }


    public ArrayList<LoanAccount> getLoanAccounts() {
        return loanAccounts;
    }

    public void addLoanAccountObject(LoanAccount loanAccountObj) {
        this.loanAccounts.add(loanAccountObj);
    }

    public void addLoanAccount(String loanAccountType, String custID, double initialLoanAmt, double currentBalance,
                               double interestRate, String paymentDueDate, String paymentNoticeDate, double amountDue,
                               String lastPaymentDate, boolean hasMissedPayment){
        LoanAccount tempLoanAcct = new LoanAccount(loanAccountType,custID,initialLoanAmt,currentBalance,interestRate,paymentDueDate,
                paymentNoticeDate,amountDue,lastPaymentDate,hasMissedPayment);
        this.loanAccounts.add(tempLoanAcct);
    }


    public ArrayList<Check> getChecks() {
        return checks;
    }

    public void addCheckObj(Check check) {
        this.checks.add(check);
    }

    public void addCheckItem(String checkNumber, String checkingAcctID, String checkDate, double checkAmount, boolean checkProcessed){
        Check temp = new Check(checkNumber,checkingAcctID,checkDate,checkAmount,checkProcessed);
        this.checks.add(temp);
    }




}
