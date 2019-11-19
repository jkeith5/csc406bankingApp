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
    public String dateCreated; // date of initial customer account
    public String pin = "";

    // this is just a base number like 5. but the different accounts will append a - and a number that
    // identifies the account type and another - and number for multiple accounts. For multiple accounts of same
    // type append an incrementing number like x-020 x-021 x-0212
    // for this system you can only have ONE checking, simple saving account, and one credit card loan. Multiple of other accounts
    public int financialAccountID; // checking= x-00  simple saving= x-01  savingCD=x-02-00  ShortTermLoan= x-03-00 LongTermLoan= x-04-00 CreditCardLoan= x-05
    // so we can split the string by "-" if len = 2 then we can only have one of that type.
    // in the id string for the accounts split [0] will be this financialID. split[1] will be the identifier of the sub type
    // and for those that allow multiple accounts like loan accounts split[2] will be the index id of that customers account
    // so If I have 3 Long term loan accounts and my base id is 12,
    // the ID for the 3rd LTL will look like: 12-04-02 the second LTL account would be 12-04-01 and the first is always 12-04-00


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
        setDateCreatedAuto();
        setPinAuto();
    }

    public CustomerAccount(String isNullString){
        if(isNullString.toLowerCase().equals("null")){
            this.isNull= true;
        }
        setDateCreatedAuto();
        setPinAuto();
    }

    // manually set cust id
    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip,String atmCardNumber) {
        // auto sets the date created to the current date created.
        setCustID(custID);
        setFirstName(firstName);
        setLastName(lastName);
        setStreetAddr(streetAddr);
        setCity(city);
        setState(state);
        setZip(zip);
        setAtmCardNumber(atmCardNumber);
        setDateCreatedAuto();
        setPinAuto();

    }

    public CustomerAccount(String custID,String firstName,String lastName,String streetAddr,String city,String state,String zip,boolean generateAtmAndFinAccountID){
        setCustID(custID);
        setFirstName(firstName);
        setLastName(lastName);
        setStreetAddr(streetAddr);
        setCity(city);
        setState(state);
        setZip(zip);
        //setAtmCardNumber(atmCardNumber);
        setDateCreatedAuto();

        if(generateAtmAndFinAccountID){
            setFinancialAccountIDAuto();
            setAtmCardNumber(Main.generateAtmCardNumber());
            setPinAuto();
        }


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
        if(state.length()>2){
            if(state.equals("null")){
                this.state="null";
            }else{
                this.state=DataEntryDriver.fullStateToAbb(state);
            }

        }else{
            this.state=state.toUpperCase();
        }
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

    public void setDateCreated(String dateCreated) { // of customer account object
        this.dateCreated = dateCreated;
    }

    public void setDateCreatedAuto(){ // sets date to today
        this.dateCreated=DataEntryDriver.getDateString();
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

    public SavingsAccount getSimpleSavingsAccount(){
        SavingsAccount result = new SavingsAccount();
        for(SavingsAccount sa: savingsAccounts){
            if(!sa.isCdAccount()){
                result = sa;
            }
        }

        return result;
    }

    public SavingsAccount getCDSavingsAccount(){
        SavingsAccount result = new SavingsAccount();

        for(SavingsAccount sa:savingsAccounts){
            if(sa.isCdAccount()){
                result =sa;
            }
        }

        return result;
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

    public String getPin(){
        return this.pin;
    }

    public void setPin(String pinInput){
        this.pin=pinInput;
    }

    public void setPinAuto(){

        if(this.atmCardNumber!=null){
            if(!this.atmCardNumber.equals("null")){
                String newPin = this.custID.substring(7);
                this.pin=newPin;
            }else{
                this.pin="null";
            }
        }else{
            this.pin="null";
        }


    }

    public int getFinancialAccountID() {
        return financialAccountID;
    }

    public void setFinancialAccountID(int financialAccountID) {
        this.financialAccountID = financialAccountID;
    }

    public void setFinancialAccountIDAuto(){
        this.financialAccountID = Main.generateCustomerId();
    }

    public void deleteCheckingAccount(){
        this.checkingAccount=null;
        this.hasCheckingAccount=false;
    }

    public void deleteSavingsAccount(){
        this.savingsAccounts = null;
        this.hasSavingsAccount=false;
    }

    public void deleteLoanAccounts(){
        this.loanAccounts=null;
        this.hasLoanAccount=false;
    }



    // prints some stats on the checks
    public double[] printStats(){
        double[] returnVal = {0.00,0.00};
        double total = 0.00;
        double unprocessed = 0.00;

        if(hasCheckingAccount()){
            total += getCheckingAccount().getAccountBalance();
        }
        if(hasSavingsAccount()){
            total += getSimpleSavingsAccount().getAccountBalance();
        }
        for(Check c:getChecks()){
            if(c.isCheckProcessed()){
                total = total - c.getCheckAmount();
            }else{
                unprocessed = unprocessed+c.getCheckAmount();
            }
        }
        System.out.println("Customer Account: "+getCustID() +" has current balance of: "+total+" with "+unprocessed+" in unprocessed checks.");
        returnVal[0] = total;
        returnVal[1] = unprocessed;
        return returnVal;
    }



    // some void methods to print the specific customer Account data


    public void printBasicDataShort(){// prints the customer first last name and acc id
        printLineBreak();
        System.out.println("Customer Account: "+getFirstName()+" "+getLastName()+" CustId: "+getCustID());
    }

    public String getBasicDataShort(){
        String result = "Customer Account: "+getFirstName()+" "+getLastName()+" CustId: "+getCustID();
        return result;
    }

    public void printLineBreak(){
        System.out.println("\n\n");
    }


    public void printTransactions(){
        printLineBreak();
        System.out.println("Transactions "+getBasicDataShort());
        for(Transaction t:this.transactions){
            System.out.println(t.toString());
        }
    }


    public void printChecks(){
        printLineBreak();
        System.out.println("Checks "+getBasicDataShort());
        for(Check c:this.checks){
            System.out.println(c.toString());
        }
    }


    public void printLoanAccounts(){
        printLineBreak();
        System.out.println("Loan Accounts "+getBasicDataShort());
        if(hasLoanAccount()){
            for(LoanAccount la:this.loanAccounts){
                System.out.println(la.toString());
            }
        }else{
            System.out.println("No loan accounts");
        }
    }

    public void printCheckingAccount(){
        printLineBreak();
        System.out.println("Checking Account "+getBasicDataShort());
        System.out.println(this.checkingAccount.toString());
    }

    public void printSavingsAccount(){
        printLineBreak();
        System.out.println("Savings Accounts "+getBasicDataShort());
        for(SavingsAccount sa:this.savingsAccounts){
            System.out.println(sa.toString());
        }
    }

    public String generateNextLoanAcctSubId(String loanAcctType){ // loanAccount type of the account to generate sub number for
        String returnVal = "null";
        int numberOfAccounts = 0;// because 00 is our starting number
        // ^^^^ Tracks the number of accounts of specified type and returns the next available account ID

        if(hasLoanAccount()){ // if they have a loan account of any type

            if(loanAcctType.equals("STL")){ // if input acct type is STL
                for(LoanAccount la:this.loanAccounts){ // Loop through the accounts
                    if(la.getLoanAccountType().equals("STL")){// finding only the STL
                        numberOfAccounts++;// and increment the number
                    }
                }
            }
            // only search these two types because theyre the only loan account we will allow multiple of.
            if(loanAcctType.equals("LTL")){
                for(LoanAccount la:this.loanAccounts){
                    if(la.getLoanAccountType().equals("LTL")){
                        numberOfAccounts++;
                    }
                }
            }
        }

        returnVal = String.format("%02d",numberOfAccounts); // pad the number as such 00 01 02 99

        return returnVal;
    }


    public String generateNextSavingsCDSubID(){
        String returnVal = "null";
        int numberOfAccounts = 0;// because 00 is our starting number
        if(hasSavingsAccount()){

            for(SavingsAccount sa:this.savingsAccounts){
                if(sa.isCdAccount()){ // searching the cd accounts
                    numberOfAccounts++;
                }
            }
        }
        returnVal = String.format("%02d",numberOfAccounts); // pad the number as such 00 01 02 99
        return returnVal;
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
                ", pin='" + pin + '\'' +
                ", financialAccountID=" + financialAccountID +
                ", hasSavingsAccount=" + hasSavingsAccount +
                ", hasCheckingAccount=" + hasCheckingAccount +
                ", hasShortTermLoan=" + hasShortTermLoan +
                ", hasLongTermLoan=" + hasLongTermLoan +
                ", hasCreditCardAcct=" + hasCreditCardAcct +
                ", hasLoanAccount=" + hasLoanAccount +
                ", savingsAccounts=" + savingsAccounts +
                ", checkingAccount=" + checkingAccount +
                ", transactions=" + transactions +
                ", loanAccounts=" + loanAccounts +
                ", checks=" + checks +
                ", isNull=" + isNull +
                '}';
    }



}
