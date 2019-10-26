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
    public String dateCreated;

    public boolean hasSavingsAccount=false;
    public boolean hasCheckingAccount=false;
    public boolean hasShortTermLoan=false;
    public boolean hasLongTermLoan=false;
    public boolean hasCreditCardAcct=false;

    public boolean hasLoanAccount=false;

    public SavingsAccount savingsAccount;
    public CheckingAccount checkingAccount;

    public ArrayList<Transaction> transactions= new ArrayList<Transaction>();
    public ArrayList<LoanAccount> loanAccounts= new ArrayList<LoanAccount>();
    public ArrayList<Check> checks = new ArrayList<Check>();


    public CustomerAccount(){
        // use setters and getters
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip,String dateCreated) {
        this.custID = custID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.dateCreated=dateCreated;
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip,String dateCreated, String atmCardNumber) {
        this.custID = custID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.dateCreated=dateCreated;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean hasSavingsAccount() {
        return hasSavingsAccount;
    }

    public void setHasSavingsAccount(boolean hasSavingsAccount) {
        this.hasSavingsAccount = hasSavingsAccount;
    }

    public boolean hasCheckingAccount() {
        return hasCheckingAccount;
    }

    public void setHasCheckingAccount(boolean hasCheckingAccount) {
        this.hasCheckingAccount = hasCheckingAccount;
    }

    public boolean hasShortTermLoan() {
        return hasShortTermLoan;
    }

    public void setHasShortTermLoan(boolean hasShortTermLoan) {
        this.hasShortTermLoan = hasShortTermLoan;
    }

    public boolean hasLongTermLoan() {
        return hasLongTermLoan;
    }

    public void setHasLongTermLoan(boolean hasLongTermLoan) {
        this.hasLongTermLoan = hasLongTermLoan;
    }

    public boolean hasCreditCardAcct() {
        return hasCreditCardAcct;
    }

    public void setHasCreditCardAcct(boolean hasCreditCardAcct) {
        this.hasCreditCardAcct = hasCreditCardAcct;
    }

    public boolean hasLoanAccount() {
        return hasLoanAccount;
    }

    public void setHasLoanAccount(boolean hasLoanAccount) {
        this.hasLoanAccount = hasLoanAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void addSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
        setHasSavingsAccount(true);
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void addCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
        setHasCheckingAccount(true);
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
        setHasLoanAccount(true);
        if(loanAccountObj.loanAccountType.equals("STL")){
            setHasShortTermLoan(true);
        }
        if(loanAccountObj.loanAccountType.equals("LTL")){
            setHasLongTermLoan(true);
        }
        if(loanAccountObj.loanAccountType.equals("CCL")){
            setHasCreditCardAcct(true);
        }
    }



    public void addLoanAccount(String loanAccountType, String custID, double initialLoanAmt, double currentBalance,
                               double interestRate, String paymentDueDate, String paymentNoticeDate, double amountDue,
                               String lastPaymentDate, boolean hasMissedPayment){
        LoanAccount tempLoanAcct = new LoanAccount(loanAccountType,custID,initialLoanAmt,currentBalance,interestRate,paymentDueDate,
                paymentNoticeDate,amountDue,lastPaymentDate,hasMissedPayment);
        this.loanAccounts.add(tempLoanAcct);

        setHasLoanAccount(true);
        if(tempLoanAcct.loanAccountType.equals("STL")){
            setHasShortTermLoan(true);
        }
        if(tempLoanAcct.loanAccountType.equals("LTL")){
            setHasLongTermLoan(true);
        }
        if(tempLoanAcct.loanAccountType.equals("CCL")){
            setHasCreditCardAcct(true);
        }
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


    @Override
    public String toString() {
        return "CustomerAccount{" +
                "custID='" + custID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetAddr='" + streetAddr + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", atmCardNumber='" + atmCardNumber + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", hasSavingsAccount=" + hasSavingsAccount +
                ", hasCheckingAccount=" + hasCheckingAccount +
                ", hasShortTermLoan=" + hasShortTermLoan +
                ", hasLongTermLoan=" + hasLongTermLoan +
                ", hasCreditCardAcct=" + hasCreditCardAcct +
                ", hasLoanAccount=" + hasLoanAccount +
                ", savingsAccount=" + savingsAccount.toString() +
                ", checkingAccount=" + checkingAccount.toString() +
                ", transactions=" + transactions.toString() +
                ", loanAccounts=" + loanAccounts.toString() +
                ", checks=" + checks.toString() +
                '}';
    }




}
