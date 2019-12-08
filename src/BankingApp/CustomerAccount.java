package BankingApp;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerAccount implements Serializable {
    // This will hold all data for customer account in the ArrayList of CustomerAccouts Objects
    // will have info on all accounts savings cds Checking Credit Card all all relevant info
    // NOTE!!! CHANGING DATA HERE LIKE ADDING METHODS OR VARIABLES REQUIRES TO DELETE CUSTOMERDATABASE FILE IN
    // RESOURCES SO THE OBJECT INPUT STREAM WILL CORRECTLY READ IN THE NEW DATA INTO OBJECTS


    private String custID;
    private String firstName;
    private String lastName;
    private String streetAddr;
    private String city;
    private String state;// 2 letter All Caps MO
    private String zip;
    private String atmCardNumber;
    private String dateCreated; // date of initial customer account
    private String pin = "";

    // this is just a base number like 5. but the different accounts will append a - and a number that
    // identifies the account type and another - and number for multiple accounts. For multiple accounts of same
    // type append an incrementing number like x-020 x-021 x-0212
    // for this system you can only have ONE checking, simple saving account, and one credit card loan. Multiple of other accounts


    // will be 0 if not set
    public int financialAccountID; // checking= x-00  simple saving= x-01  savingCD=x-02-00  ShortTermLoan= x-03-00 LongTermLoan= x-04-00 CreditCardLoan= x-05
    // so we can split the string by "-" if len = 2 then we can only have one of that type.
    // in the id string for the accounts split [0] will be this financialID. split[1] will be the identifier of the sub type
    // and for those that allow multiple accounts like loan accounts split[2] will be the index id of that customers account
    // so If I have 3 Long term loan accounts and my base id is 12,
    // the ID for the 3rd LTL will look like: 12-04-02 the second LTL account would be 12-04-01 and the first is always 12-04-00


    private boolean hasSavingsAccount=false;
    private boolean hasSimpleSavings=false;
    private boolean hasCDSavings = false;

    private boolean hasCheckingAccount=false;

    private boolean hasLoanAccount=false;
    private boolean hasShortTermLoan=false;
    private boolean hasLongTermLoan=false;
    private boolean hasCreditCardAcct=false;



    //public SavingsAccount savingsAccount;
    private ArrayList<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();


    private CheckingAccount checkingAccount;
    private ArrayList<Transaction> transactions= new ArrayList<Transaction>();
    private ArrayList<LoanAccount> loanAccounts= new ArrayList<LoanAccount>();
    private ArrayList<Check> checks = new ArrayList<Check>();

    public boolean isNull=false;


    public CustomerAccount(){
        // use setters and getters setting null pointer to null until at least a ssn is added
        //System.out.println("construct 1");
        this.isNull=true;
        setDateCreatedAuto();
        setPinAuto();
        setAtmCardNumber(""); // randomly generates one
    }

    public CustomerAccount(String isNullString){
        //System.out.println("construct 2");
        if(isNullString.toLowerCase().equals("null")){
            this.isNull= true;
        }
        setDateCreatedAuto();
        setPinAuto();
    }

    // manually set cust id used when reading in csv files
    public CustomerAccount(String custID, String firstName, String lastName, String streetAddr, String city, String state, String zip,String atmCardNumber) {
        //System.out.println("construct 3");
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
    public void setAtmCardNumber(String atmCardNumber) { // if null random generate one if not then just set it
        if(atmCardNumber.equals("null") || atmCardNumber.equals("")){
            this.atmCardNumber=Main.generateAtmCardNumber();
        }else{
            this.atmCardNumber = atmCardNumber;
        }
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

    public boolean hasSimpleSavings() {
        return hasSimpleSavings;
    }

    public void setHasSimpleSavings(boolean hasSimpleSavings) {
        this.hasSimpleSavings = hasSimpleSavings;
    }

    public boolean hasCDSavings() {
        return hasCDSavings;
    }

    public void setHasCDSavings(boolean hasCDSavings) {
        this.hasCDSavings = hasCDSavings;
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


    // returns the arraylist of ALL savings accounts
    public ArrayList<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }

    public SavingsAccount getSimpleSavingsAccount(){ // return the one and only simple saving if it exists
        SavingsAccount result = new SavingsAccount();

        if(hasSimpleSavings()){
            for(SavingsAccount sa: savingsAccounts){
                if(!sa.isCdAccount()){
                    result = sa;
                }
            }
        }else{
            System.err.println("Warning Null savings account Object Returned.");
        }


        return result;
    }

    public ArrayList<SavingsAccount> getCDSavingsAccounts(){ // return arraylist of all the savings accounts that are not simple
        ArrayList<SavingsAccount> result = new ArrayList<>();

        if(hasSavingsAccount()){
            for(SavingsAccount sa:savingsAccounts){
                if(sa.isCdAccount()){
                    result.add(sa);
                }
            }
        }
        return result;
    }
    public SavingsAccount getSavingsAccountByIndex(int index){
        return this.savingsAccounts.get(index);
    }

    public int getSavingsAccountIndexByFixedID(String fixedId){
        int returnIndex = 0;
        if(hasSavingsAccount()){
            for(int i=0;i<savingsAccounts.size()+1;i++){
                if(savingsAccounts.get(i).getSavingsAcctIDFixed().equals(fixedId)){
                    returnIndex=i;
                    break;
                }
            }

        }

        return returnIndex;
    }

    public SavingsAccount getSavingsAccountByFixedID(String fixedID){
        int index = getSavingsAccountIndexByFixedID(fixedID);
        return getSavingsAccountByIndex(index);
    }

    public void deleteSavingsAccountByIndex(int index){
        savingsAccounts.remove(index);
    }


    public void addSavingsAccount(SavingsAccount savingsAccount) {
        //this.savingsAccount = savingsAccount;

        if(!savingsAccount.isNull()){ // if the savings object is not considered null by my terms. like no balance or id set
            this.savingsAccounts.add(savingsAccount);
            setHasSavingsAccount(true);

            if(savingsAccount.isCdAccount()){
                setHasCDSavings(true);
            }else{
                setHasSimpleSavings(true);
            }
        }else{
            System.err.println("Trying to add a null savings account to CustomerAccount: "+getCustID());
        }

    }


    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }
    public void addCheckingAccount(CheckingAccount checkingAccount) {

        if(!checkingAccount.isNull()){ // if checking acc is not null
            this.checkingAccount = checkingAccount;
            setHasCheckingAccount(true);
        }else{
            System.err.println("Trying to add a null Checking Account to Customer Account: "+getCustID());
        }


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
    public void addTransactionObjectArrayList(ArrayList<Transaction> inputTransactions){
        if(inputTransactions!=null){
            if(inputTransactions.size()!=0){
                for(Transaction transaction:inputTransactions){
                    addTransactionObject(transaction);
                }
            }
        }
    }


    public ArrayList<LoanAccount> getLoanAccounts() {
        return loanAccounts;
    }
    public void addLoanAccountObject(LoanAccount loanAccountObj) {

        if(!loanAccountObj.isNull()){ // if loan account is not null
            this.loanAccounts.add(loanAccountObj);
            setHasLoanAccount(true);
            if(loanAccountObj.getLoanAccountType().equals("STL")){
                setHasShortTermLoan(true);
            }
            if(loanAccountObj.getLoanAccountType().equals("LTL")){
                setHasLongTermLoan(true);
            }
            if(loanAccountObj.getLoanAccountType().equals("CCL")){
                setHasCreditCardAcct(true);
            }
        }else{
            System.err.println("Trying to add a null Loan Account to Customer Account: "+getCustID());
        }


    }

    public int getLoanAccountIndexByFixedID(String fixedId){
        int returnIndex = 0;
        if(hasLoanAccount()){
            for(int i=0;i<loanAccounts.size()+1;i++){
                if(loanAccounts.get(i).getLoanAccountIDFixed().equals(fixedId)){
                    returnIndex=i;
                    break;
                }
            }

        }

        return returnIndex;
    }

    public LoanAccount getLoanAccountByFixedID(String fixedID){
        int index = getLoanAccountIndexByFixedID(fixedID);
        return loanAccounts.get(index);
    }

    public LoanAccount getCreditCardLoanAccount(){
        LoanAccount returnAccount = null;
        if(hasLoanAccount()){
            for(LoanAccount loanAccount:loanAccounts){
                if(loanAccount.getLoanAccountType().equals("CCL")){
                    returnAccount = loanAccount;
                }
            }
        }else{
            System.err.println("Returning a Null CreditCard Account. Check Code");
        }

        return returnAccount;
    }


    public void deleteLoanAccountByIndex(int index){
        loanAccounts.remove(index);
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
        // we can have a pin without a card number just need a cust id first

        if(this.custID != null){ // check for not null custID object
            if(!this.custID.equals("null")){ // then make sure string isn't null
                String newPin = this.custID.substring(7);
                this.pin=newPin;
            }else{ // string was null
                this.pin="null";
                Main.out.println(Main.getDateTimeString()+"Attempting to generate pin number from a null String custID in CustomerAccount");
            }
        }else{// object was null
            this.pin=null;
            Main.out.println(Main.getDateTimeString()+"Attempting to generate pin number from a null object for custID in CustomerAccount");
        }

    }

    public int getFinancialAccountID() {
        return financialAccountID;
    }

    public void setFinancialAccountID(int financialAccountID) {
        this.financialAccountID = financialAccountID;
    }

    public void setFinancialAccountIDAuto(){
        System.out.println("Setting financial id auto:");
        this.financialAccountID = Main.generateCustomerId();
    }

    public void deleteCheckingAccount(){ // object
        this.checkingAccount= null;
        this.hasCheckingAccount=false;
        calculateBooleanValues();
    }


    public void deleteAllSavingsAccounts(){ // arraylist
        this.savingsAccounts = new ArrayList<SavingsAccount>();
        this.hasSavingsAccount=false;
        this.hasSimpleSavings=false;
        calculateBooleanValues();
    }

    public void deleteSavingsAccountObject(SavingsAccount savingsAccountObj){
        if(hasSavingsAccount){
            savingsAccounts.remove(savingsAccountObj);
            calculateBooleanValues();
        }
    }




    public void deleteLoanAccounts(){ // arraylist
        this.loanAccounts= new ArrayList<>();
        this.hasLoanAccount=false;
        this.hasCreditCardAcct=false;
        this.hasShortTermLoan=false;
        this.hasLongTermLoan=false;

    }

    public void deleteLoanAccountObject(LoanAccount loanAccountObject){
        if(hasLoanAccount){
            loanAccounts.remove(loanAccountObject);
            calculateBooleanValues();
        }
    }


    public void calculateBooleanValues(){
        if(this.checkingAccount==null){
            this.hasCheckingAccount=false;
        }else{
            this.hasCheckingAccount=true;
        }

        if(savingsAccounts!=null){
            if(savingsAccounts.size()!=0){
                hasSavingsAccount=true;
                hasSimpleSavings=false;
                hasCDSavings=false;
                for(SavingsAccount sa:savingsAccounts){
                    if(sa.isCdAccount()){
                        hasCDSavings=true;
                    }
                    if(!sa.isCdAccount()){
                        hasSimpleSavings=true;
                    }
                }
            }else{
                hasSavingsAccount=false;
                hasSimpleSavings=false;
                hasCDSavings=false;
            }
        }else{
            hasSavingsAccount=false;
            hasSimpleSavings=false;
            hasCDSavings=false;
        }

        if(loanAccounts!=null){
            if(loanAccounts.size()!=0){
                hasShortTermLoan=false;
                hasLongTermLoan=false;
                hasCreditCardAcct=false;
                hasLoanAccount=true;
                for(LoanAccount la:loanAccounts){
                    if(la.getLoanAccountType().equals("STL")){
                        hasShortTermLoan=true;
                    }
                    if(la.getLoanAccountType().equals("LTL")){
                        hasLongTermLoan=true;
                    }
                    if(la.getLoanAccountType().equals("CCL")){
                        hasCreditCardAcct=true;
                    }
                }
            }else{
                hasLoanAccount=false;
                hasShortTermLoan=false;
                hasLongTermLoan=false;
                hasCreditCardAcct=false;
            }
        }else{
            hasLoanAccount=false;
            hasShortTermLoan=false;
            hasLongTermLoan=false;
            hasCreditCardAcct=false;
        }





    }

    // used to fix the old IDs to the new format
    // this takes the financial ID and sets the FixedID value for each financial account based on the
    // financial id. Also fixes all check objects to use the new account ID
    // call this after all data has already been read in.
    public void fixIDforCustomerAccounts(){
        if(hasCheckingAccount()){
            if(checkingAccount.getCheckingAcctIDFixed()==null){ // only set if null
                this.checkingAccount.setCheckingAccountIDAuto(this);
            }

        }
        if(hasSavingsAccount()){ // if has savings of any type
            for(SavingsAccount sa:savingsAccounts){ // for each savings account in arraylist
                if(sa.getSavingsAcctIDFixed() == null){ // only run if the fixedID is not set
                    sa.setSavingsAccountIDAuto(this);
                }
            }
        }
        if(hasLoanAccount()){
            for(LoanAccount la:loanAccounts){
                if(la.getLoanAccountIDFixed() == null){
                    la.setLoanAccountIDAuto(this);
                }
            }
        }

        // fix the id for the checks as well.
        // the checking account ID has been fixed at this point, so switching all checks to use the new ID
        // should be okay, and because the way the checks are linked, we won't miss any data
        if(hasCheckingAccount()){
            if(checks.size()>0){
                for(Check c:checks){
                    if(!c.getCheckingAcctID().contains("-")){ // only change if it doesn't already use the New ID
                        c.setCheckingAcctID(getCheckingAccount().getFixedID());
                    }

                }
            }
        }



    }




    public String generateNextLoanAcctSubId(String loanAcctType){ // loanAccount type of the account to generate sub number for
        String returnVal = "null";
        int numberOfAccounts = 0;// because 00 is our starting number
        // ^^^^ Tracks the number of accounts of specified type and returns the next available account ID
        if(hasLoanAccount()){ // if they have a loan account of any type
            String tempSubID = String.format("%02d",numberOfAccounts); // pad the number as such 00 01 02 99
            boolean idExists = loanSubIDAlreadyExists(tempSubID,loanAcctType);


            while(idExists){
                numberOfAccounts++;
                tempSubID = String.format("%02d",numberOfAccounts);
                if(numberOfAccounts == 99){
                    System.err.println("NUMBER OF ACCOUNTS EXCEEDS PROGRAMMING SPECS");
                    break;
                }
                idExists = loanSubIDAlreadyExists(tempSubID,loanAcctType);
            }



        }

        returnVal = String.format("%02d",numberOfAccounts); // pad the number as such 00 01 02 99

        return returnVal;
    }

    public boolean loanSubIDAlreadyExists(String subIdOfLoanAccount,String loanType){
        boolean returnVal = false;
        int numberOfAccounts = 0;

        if(loanType.equals("STL") || loanType.equals("LTL")){
            if(loanType.equals("STL")){
                if(hasShortTermLoan){
                    for(LoanAccount la:loanAccounts){
                        if(la.getLoanAccountType().equals("STL")){
                            if(la.getLoanAccountIDFixed()!=null){
                                if(la.getLoanAccountIDFixed().split("-")[2].equals(subIdOfLoanAccount)){
                                    returnVal = true;
                                    break;
                                }
                            }

                        }
                    }
                }
            }
            if(loanType.equals("LTL")){
                if(hasLongTermLoan){
                    for(LoanAccount la:loanAccounts){
                        if(la.getLoanAccountType().equals("LTL")){
                            if(la.getLoanAccountIDFixed()!=null){
                                if(la.getLoanAccountIDFixed().split("-")[2].equals(subIdOfLoanAccount)){
                                    returnVal=true;
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }
        return returnVal;
    }


    public String generateNextSavingsCDSubID(){
        //System.out.println("IN generate Next savings cd sub id");
        String returnVal = "null";
        int numberOfAccounts = 0;// because 00 is our starting number
        //System.out.println("# of accts: "+numberOfAccounts);


        String idTemp = String.format("%02d",numberOfAccounts);
        boolean newIdExists = savingsCDSubIDAlreadyExists(idTemp);

        while(newIdExists){ // while the new id already exist
            numberOfAccounts++;
            idTemp = String.format("%02d",numberOfAccounts);
            if(numberOfAccounts == 99){
                System.err.println("NUMBER OF ACCOUNTS EXCEEDS PROGRAMMING SPECS");
                break;
            }
            newIdExists = savingsCDSubIDAlreadyExists(idTemp);
        }



        returnVal = String.format("%02d",numberOfAccounts); // pad the number as such 00 01 02 99
        return returnVal;
    }


    public boolean savingsCDSubIDAlreadyExists(String subIdOfSavings){
        boolean returnVal = false;

        if(hasCDSavings){
            for(SavingsAccount savingsAccount:getCDSavingsAccounts()){

                if(savingsAccount.getSavingsAcctIDFixed()!=null){ // this for if the IDs are already Fixed
                    if(subIdOfSavings.equals(savingsAccount.getSavingsAcctIDFixed().split("-")[2])){
                        returnVal = true;
                        break;
                    }
                }


            }
        }


        return returnVal;
    }

    public String toStringAllData(){
        String result = toStringPrettyPrint();
        result=result+getCheckingAccountString();
        result=result+getSavingsAccountString();
        result=result+getLoanAccountsString();
        result=result+getChecksString();
        result=result+getTransactionsString();
        return result;
    }


    public String getTransactionsString(){
        String result = "\nTransactions";
        for(Transaction t:this.transactions){
            result = result+"\n"+t.toString();
        }

        return result;
    }


    public String getChecksString(){
        String result = "\nChecks";
        for(Check c:this.checks){
            result = result+"\n"+c.toString();
        }
        return result;
    }


    public String getLoanAccountsString(){
        String result = "\nLoan Accounts";
        if(hasLoanAccount()){
            for(LoanAccount la:this.loanAccounts){
                result = result+"\n"+la.toString();
            }
        }

        return result;
    }

    public String getCheckingAccountString(){
        String result = "\nChecking Accounts";
        if(hasCheckingAccount()){
            result = result+"\n"+this.checkingAccount.toString();
        }else{
            result = result+"\n";
        }

        return result;
    }

    public String getSavingsAccountString(){
        String result = "\nSavings Accounts";
        if(hasSavingsAccount()){
            for(SavingsAccount sa:this.savingsAccounts){
                result = result+"\n"+sa.toString();
            }
        }else{
            result=result+"\n";
        }
        return result;
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


    public String toStringPrettyPrint(){
        String result = "Customer ID: "+getCustID()+" First: "+getFirstName()+" Last: "+getLastName()+" " +
                "Atm Card: "+getAtmCardNumber()+" pin: "+getPin()+" financialAccountID: "+getFinancialAccountID();

        return result;
    }


    // to print the data as it would appear in the CustomersBase.csv file without the csv formatting
    public String toStringBaseDataTableFormat(){
        String result = String.format("SSN: %-12s First: %-10.10s Last: %-10.10s Address: %-20.20s City: %-13.13s" +
                " State: %-4s Zip: %-5s ATM: %-18s FinancialID: %-2d hasChecking: %-5.5b hasSavings: %-5.5b hasLoan: %-5.5b",
                custID,firstName,lastName,streetAddr,city,state,zip,
                atmCardNumber,financialAccountID,hasCheckingAccount,hasSavingsAccount,hasLoanAccount);
        return result;
    }

    public String toStringCSV(){
        String result = String.format("%s,%s,%s,%s,%s,%s,%s,%s",custID,firstName,lastName,streetAddr,city,state,zip,atmCardNumber);
        return result;
    }

    public static String toStringCSVHeader(){
        String result = "custID,firstName,lastName,streetAddr,city,state,zip,atmCardNumber";
        return result;
    }




    //


}
