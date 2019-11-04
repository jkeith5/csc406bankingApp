package BankingApp;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerAccount implements Serializable {
    // This will hold all data for customer account in the ArrayList of CustomerAccouts Objects
    // will have info on all accounts savings cds Checking Credit Card all all relevant info
    // NOTE!!! CHANGING DATA HERE LIKE ADDING METHODS OR VARIABLES REQUIRES TO DELETE CUSTOMERDATABASE FILE IN
    // RESOURCES SO THE OBJECT INPUT STREAM WILL CORRECTLY READ IN THE NEW DATA INTO OBJECTS

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

    //public SavingsAccount savingsAccount;
    public ArrayList<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();


    public CheckingAccount checkingAccount;

    public ArrayList<Transaction> transactions= new ArrayList<Transaction>();
    public ArrayList<LoanAccount> loanAccounts= new ArrayList<LoanAccount>();
    public ArrayList<Check> checks = new ArrayList<Check>();

    public boolean isNull=false;


    public CustomerAccount(){
        // use setters and getters setting null pointer to null until at least a ssn is added
        this.isNull=true;
    }

    public CustomerAccount(String isNullString){
        if(isNullString.toLowerCase().equals("null")){
            this.isNull= true;
        }
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip,String atmCardNumber) {
        // auto sets the date created to the current date created.
        setCustID(custID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.atmCardNumber=atmCardNumber;
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city,
                           String state, String zip,String dateCreated, String atmCardNumber) {
        //this.custID = custID;
        setCustID(custID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.dateCreated=dateCreated;
        this.atmCardNumber = atmCardNumber;
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city,
                           String state, String zip, String atmCardNumber, String dateCreated, CheckingAccount checkingAccount) {
        //this.custID = custID;
        setCustID(custID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.atmCardNumber = atmCardNumber;
        this.dateCreated = dateCreated;
        this.checkingAccount = checkingAccount;
        this.hasCheckingAccount = true;
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city,
                           String state, String zip, String atmCardNumber, String dateCreated, SavingsAccount savingsAccount) {
        //this.custID = custID;
        setCustID(custID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.atmCardNumber = atmCardNumber;
        this.dateCreated = dateCreated;
        addSavingsAccount(savingsAccount);
        //this.savingsAccount = savingsAccount;
        this.hasSavingsAccount=true;
    }

    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city,
                           String state, String zip, String atmCardNumber, String dateCreated,
                           SavingsAccount savingsAccount, CheckingAccount checkingAccount, ArrayList<LoanAccount> loanAccounts) {
        //this.custID = custID;
        setCustID(custID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddr = streetAddr;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.atmCardNumber = atmCardNumber;
        this.dateCreated = dateCreated;
        addSavingsAccount(savingsAccount);
        //this.savingsAccount = savingsAccount;
        this.checkingAccount = checkingAccount;
        this.loanAccounts = loanAccounts;
        this.hasCheckingAccount=true;
        this.hasSavingsAccount=true;
        this.hasLoanAccount=true;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        //this.custID = custID;
        this.custID = DataEntryDriver.fixSSN(custID);
        this.isNull=false;
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

    public void setDateCreatedAuto(){ // sets date to today
        //
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

    public ArrayList<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }

    public SavingsAccount getSavingsAccount(){
        return this.savingsAccounts.get(0);
    }

    public SavingsAccount getSavingsAccountByIndex(int index){
        return this.savingsAccounts.get(index);
    }

    public void addSavingsAccount(SavingsAccount savingsAccount) {
        //this.savingsAccount = savingsAccount;
        this.savingsAccounts.add(savingsAccount);
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

    public void addLoanAccount( String custID, double initialLoanAmt, double currentBalance,
                               double interestRate, String paymentDueDate, String paymentNoticeDate, double amountDue,
                               String lastPaymentDate, boolean hasMissedPayment,String loanAccountType){
        LoanAccount tempLoanAcct = new LoanAccount(custID,initialLoanAmt,currentBalance,interestRate,paymentDueDate,
                paymentNoticeDate,amountDue,lastPaymentDate,hasMissedPayment,loanAccountType);
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

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
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
                ", savingsAccount=" + savingsAccounts +
                ", checkingAccount=" + checkingAccount +
                ", transactions=" + transactions +
                ", loanAccounts=" + loanAccounts +
                ", checks=" + checks +
                ", isNull=" + isNull +
                '}';
    }



}
