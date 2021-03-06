package BankingApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DataEntryDriver {
    File file;//this contains the file name of the related data file
    BufferedWriter bufferedWriter;

    public DataEntryDriver(File file) {
        this.file = file;
    }

    public void printData(ArrayList<String> data){//this function pushes data to the local file as directed above

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            for (int i=0; i<data.size();i++){
                if (data.size() - 1 == i) {
                    bufferedWriter.write(data.get(i) + "\n");
                } else {
                    bufferedWriter.write(data.get(i) + ", ");
                }

            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // this method will create an ArrayList of CustomerAccount objects from the csv files located
    // in the Resources Directory and then write it as a file in the Resources folder to the
    // customerDatabase file. This does not set anything in Main it just reads the CSV files and then
    // creates CustomerAccount Objects and then writes those to a file using the Serializable interface
    // if called with the readExport as true it will attempt to read the Resources/export csv files.
    // it will likely crash if they are not there
    public static void createCustomerDatabaseFileFromCSVBaseData(boolean readExport,String prefix){
        ArrayList<CustomerAccount> result = new ArrayList<CustomerAccount>();

        if(readExport){
            String testFileName = String.format("src/Resources/export/%sLoanAccounts.csv",prefix);
            File testFile = new File(testFileName); // make a dummy test file
            if(!testFile.exists()){
                System.err.println("PLEASE USE THE EXPORT FEATURE IN THE MAIN INTERFACE BEFORE IMPORTING FROM FILES THAT DO NOT EXIST");
                return;
            }

        }

        // call each read method for the base csv file data and create arraylist from them.
        ArrayList<CustomerAccount> customerAccountsRead = readCustomerAccountsCSV(readExport,prefix);
        ArrayList<CheckingAccount> checkingAccountsRead = readCheckingAccountsToArrList(readExport,prefix);
        ArrayList<SavingsAccount> savingsAccountsRead = readSavingsAccountsToArrList(readExport,prefix);
        ArrayList<LoanAccount> loanAccountsRead = readLoanAccountsToArrList(readExport,prefix);
        ArrayList<Check> checkObjectsRead = readChecksToArrList(readExport,prefix);

        // Then loop through each arraylist to create CustomerAccount Objects to add to the ArrayList
        for(CustomerAccount customerAccount: customerAccountsRead){ // loop through each Customer account Object to add the accounts
            CustomerAccount ca = customerAccount;
            String customerAccountSSN = customerAccount.getCustID();

            for(CheckingAccount checkingAccount:checkingAccountsRead){
                if(checkingAccount.getCustID().equals(customerAccountSSN)){ // if the checking account custID is the same
                    ca.addCheckingAccount(checkingAccount); // tie them together by adding that account to customer object
                }
            }

            for(SavingsAccount savingsAccount:savingsAccountsRead){
                if(savingsAccount.getCustID().equals(customerAccountSSN)){
                    ca.addSavingsAccount(savingsAccount);
                }
            }

            for(LoanAccount loanAccount:loanAccountsRead){
                if(loanAccount.getCustID().equals(customerAccountSSN)){
                    ca.addLoanAccountObject(loanAccount);
                }
            }
            result.add(ca); // then add that entire fixed object to the result arraylist
        }

        // because the checks are not setup with a SSN we have to wait until this loop is finished to run over the
        // Array AGAIN to add the checks.
        for(CustomerAccount ca:result){
            if(ca.hasCheckingAccount()){ // don't process ca in loop if it has no checking account
                for(Check check:checkObjectsRead){ // loop through the checks that were read in
                    if(ca.getCheckingAccount().getCheckingAcctID().equals(check.getCheckingAcctID())){
                        if(check.isCheckProcessed()){ // if the check is processed add check and make transaction
                            ca.addCheckObj(check);// add check object
                            FinanceDriver.debitCheckingAccountWithCheckObject(ca,check);// make transaction and record
                        }else{// the check is not processed so just add it to the customerAccount
                            ca.addCheckObj(check);
                        }

                    }
                }
            }
        }

        if(readExport){ // if reading in exported data is true
            ArrayList<String> transactionStringArrList = readTransactionsToStringArrList(readExport,prefix);
            if(transactionStringArrList!=null){
                if(transactionStringArrList.size()!=0){ // if array was not zero which shouldn't really happen
                    for(String transactionString:transactionStringArrList){ // loop through the string array of transactions
                        String[] split = transactionString.split(","); // split on the comma
                        String custID = split[0]; // custID is the first one
                        if(custID.length()==11){ // make sure cust ID is a SSN and not say a blank line
                            for(CustomerAccount ca:result){ // loop through customer accounts in the result array
                                if(ca.getCustID().equals(custID)){ // if the customer Id matches
                                    // create a transaction object from the split array
                                    Transaction newTransaction = new Transaction(split[1],split[2],split[5],split[3],split[4]);
                                    ca.addTransactionObject(newTransaction); // add the object to the CustomerAccount
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // this writes the accounts to the Resources customerDatabase file
        serializeArrayListToFile(result); // then write the Customer Account array to the File
    }

    public static ArrayList<CustomerAccount> readCustomerAccountsCSV(boolean readExport,String prefixString){ // Reads CustomerDatabase.csv
        ArrayList<CustomerAccount> result = new ArrayList<>();

        File customerBase = null;
        if(!readExport){ // not reading export data
            customerBase = new File("src/Resources/CustomersBase.csv");
        }else{ // we are reading the exported data match prefix
            String fileNameString = String.format("src/Resources/export/%sCustomersBase.csv",prefixString);
            customerBase = new File(fileNameString);
        }

        //System.out.println("Reading in CustomerBase.csv");
        Main.printToConsoleAndLog("Reading in CustomersBase.csv");
        int id = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(customerBase));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] lineSplit = line.split(",");
                CustomerAccount ca = new CustomerAccount(lineSplit[0],lineSplit[1],lineSplit[2],lineSplit[3],lineSplit[4],
                        lineSplit[5],lineSplit[6],lineSplit[7]);

                if(!readExport){ // not reading export data readExport = false
                    //ca.setFinancialAccountID(Main.generateCustomerId());
                    ca.setFinancialAccountID(id);
                    id++;
                }


                result.add(ca);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    // reads the CheckingAccounts CSV file and returns arraylist of CheckingAccount Objects
    public static ArrayList<CheckingAccount> readCheckingAccountsToArrList(boolean readExport,String prefix){ // reads CheckingAccounts.csv
        ArrayList<CheckingAccount> result = new ArrayList<>();

        File checkingAccountsFile = null;
        if(!readExport){
            checkingAccountsFile = new File("src/Resources/CheckingAccounts.csv");
        }else{
            String fileNameString = String.format("src/Resources/export/%sCheckingAccounts.csv",prefix);
            checkingAccountsFile = new File(fileNameString);
        }


        Main.printToConsoleAndLog("Reading in CheckingAccounts.csv"); // log to console and log file
        try {
            BufferedReader br = new BufferedReader(new FileReader(checkingAccountsFile));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                String checkingAccIdString = split[1];
                boolean isNull = false; // tracks if the checking account id is null
                if(checkingAccIdString.equals("null")){
                    isNull=true;
                }

                if(!isNull){ // if checking id is not null
                    CheckingAccount ca = new CheckingAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6]);
                    try { // create and add object to arraylist
                        result.add(ca);
                    } catch (NullPointerException e){
                        //System.out.println("Null pointer for: "+ca.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{ // else the checking id in split array was null
                    Main.printToConsoleAndLog("Checking Account for CustomerID: "+split[0]+" was null. Checking Accounts " +
                            "with null values are discarded!");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // reads the SavingsAccounts.csv file and returns ArrayList of SavingsAccount objects
    public static ArrayList<SavingsAccount> readSavingsAccountsToArrList(boolean readExport,String prefix){ // Reads the SavingsAccounts.csv
        ArrayList<SavingsAccount> result = new ArrayList<>();

        File savingsAccountFile = null;

        if(!readExport){
            savingsAccountFile = new File("src/Resources/SavingsAccounts.csv");
        }else{
            String fileNameString = String.format("src/Resources/export/%sSavingsAccounts.csv",prefix);
            savingsAccountFile = new File(fileNameString);
        }


        Main.printToConsoleAndLog("Reading in SavingsAccounts.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(savingsAccountFile));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                boolean isNull = false; // tracks if the savings account is null
                if(split[0].equals("null") || split[1].equals("null") ){ // if cust id or savings id is null
                    isNull=true;
                }
                if(!isNull){ // if the cust id or savings id was not null
                    SavingsAccount sa = new SavingsAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6]);
                    result.add(sa);
                }else{ // else the savings id or cust id was null
                    Main.printToConsoleAndLog("Savings Account Entry Was Null! Line was discarded. Please Check Data.");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // reads in the LoanAccounts.csv file and returns ArrayList of LoanAccount Objects.
    public static ArrayList<LoanAccount> readLoanAccountsToArrList(boolean readExport, String prefix){ // Reads the LoanAccounts.csv
        ArrayList<LoanAccount> result = new ArrayList<>();

        File loanAccountFile = null;
        if(!readExport){
            loanAccountFile = new File("src/Resources/LoanAccounts.csv");
        }else{
            String fileNameString = String.format("src/Resources/export/%sLoanAccounts.csv",prefix);
            loanAccountFile = new File(fileNameString);
        }


        Main.printToConsoleAndLog("Reading in LoanAccounts.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(loanAccountFile));
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                boolean isNull = false; // tracks if the loan account is null
                // split 0 ssn should be 11 length. ID is [10]
                if(split[0].length() != 11 || split[10].equals("null") ){ // if cust id is not formatted as 000-00-0000 or savings id is null
                    isNull=true;
                }
                if(!isNull){ // if it was not null
                    LoanAccount la = new LoanAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6],split[7],split[8],split[9],split[10],split[11],split[12]);
                    result.add(la);
                }else{ // it was null
                    Main.printToConsoleAndLog("Loan Account CustomerID value was null or not formatted or Loan ID was null!" +
                            "Item was Discarded! Please Check Data.");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // reads the Checks.csv file to an ArrayList of Check objects
    public static ArrayList<Check> readChecksToArrList(boolean readExport, String prefix){ // Reads the Checks.csv
        ArrayList<Check> result = new ArrayList<>();
        File checkFile = null;
        if(!readExport){
            checkFile = new File("src/Resources/Checks.csv");
        }else{
            String fileNameString = String.format("src/Resources/export/%sChecks.csv",prefix);
            checkFile = new File(fileNameString);
        }


        Main.printToConsoleAndLog("Reading in Checks.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(checkFile));
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] split = line.split(",");

                boolean isNull = false; // tracks if the Check object is null
                if(split[0].equals("null") || split[1].equals("null") ){ // if check number or account id is null
                    isNull=true;
                }
                if(!isNull){ // if not null
                    Check check = new Check(split[0],split[1],split[2],split[3],split[4]);
                    result.add(check);
                }else{
                    Main.printToConsoleAndLog("Check Object had null values! Item Discarded: "+split[0]+" "+split[1]+" "+split[2]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    // ONLY CALL IF YOU ARE READING THE TRANSACTIONS FROM THE EXPORT FILE
    // first this returns a String ArrayList of the line data used to make the Transactions
    // because WE had to add the custID to link the data
    public static ArrayList<String> readTransactionsToStringArrList(boolean readExport,String prefix){ // Reads the transactions.csv export file
        ArrayList<String> result = new ArrayList<>();

        if(!readExport){
            System.err.println("Don't call this if you are not reading the exported Transactions.csv file");
            result = new ArrayList<>();
        }else{
            String fileNameString = String.format("src/Resources/export/%sTransactions.csv",prefix);
            File transactionFile = new File(fileNameString);

            try {
                BufferedReader br = new BufferedReader(new FileReader(transactionFile));
                String line;
                br.readLine();

                while((line = br.readLine()) != null){
                    String[] split = line.split(",");

                    boolean isNull = false; // tracks if the transaction object is null
                    if(split[0].equals("null") ){ // if cust id is null
                        isNull=true;
                    }
                    if(!isNull){ // if not null
                        result.add(line);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return result;
    }



    // takes the ArrayList of CustomerAccount Objects and Using Object Output Stream, Writes it to a file
    // called customerDatabase located under Resources.
    public static boolean serializeArrayListToFile(ArrayList<CustomerAccount> customerAccounts){
        System.out.println("Database Saved");
        try{
            // create the ObjectOutputStream
            // write each object in the ArrayList to the file rather than writing the actual ArrayList Object
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/Resources/customerDatabase"));
            for(CustomerAccount ca:customerAccounts){ // loop through the CustomerAccount Array
                objectOutputStream.writeObject(ca); // Write each CustomerAccount to the File
            }
            objectOutputStream.close(); // Close the Stream
            Main.out.println(Main.getDateTimeString()+"ArrayList was written to File."); // Log the action
            return true; // return true for success in writing file
        } catch (IOException e){ // something bad happened
            e.printStackTrace(); // print error
            Main.out.println(Main.getDateTimeString()+"Error in serializeArrayListToFile File was not written."); // log error
            return false; // return false
        }
    }

    public static boolean serializeCustomerDatabaseToFile(){
        boolean returnVal = false;

        if(Main.customerAccounts!=null){
            if(Main.customerAccounts.size()>0){
                returnVal = serializeArrayListToFile(Main.customerAccounts);
            }
        }else{
            System.err.println("Attempting to serialize Customer Database that is Null!");
        }
        return returnVal;
    }

    // reads the customerDatabase file and Returns and Arraylist of CustomerAccounts using
    // the Object Input Stream.
    public static ArrayList<CustomerAccount> readFileToCustomerAccountsArrayList() {
        Main.printToConsoleAndLog("Start of Read customerDatabase to ArrayList");
        ArrayList<CustomerAccount> result = new ArrayList<CustomerAccount>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/Resources/customerDatabase"))) {
            while(true){ // always loop
                Object read = objectInputStream.readObject();
                if(read == null){ // but if read is null
                    break; // then break loop
                }
                CustomerAccount customerAccountRead = (CustomerAccount) read;
                result.add(customerAccountRead);
            }
        } catch (EOFException e) { // or catch the End of File Exception
            Main.printToConsoleAndLog("End of Read customerDatabase to ArrayList");
        } catch (ClassNotFoundException e) {
            Main.printToConsoleAndLog("Error in readFileToCustomerAccountsArrayList Error: "+e.toString());
        } catch(ObjectStreamException e){
            Main.printToConsoleAndLog("Customer Database File Was Corrupted!");
        } catch (IOException e){
            Main.printToConsoleAndLog("Error in ReadFile. Error: "+e.toString());
        }


        return result;
    }


    public static CustomerAccount getCustomerAccountFromCustomerID(String customerID){
        ArrayList<CustomerAccount> accountsList = Main.customerAccounts;

        CustomerAccount result = new CustomerAccount("null");

        String searchIDStripped = stripSSN(customerID);

        for(CustomerAccount ca:accountsList){
            String custIDStripped = stripSSN(ca.getCustID());
            if(custIDStripped.equals(searchIDStripped)){
                result = ca;
                break;
            }
        }

        if(result.isNull()){
            System.out.println("no results found in getCustAcctFromID");
        }


        return result;
    }

    public static void deleteCustomerAccountsFile(){
        //
    }

    public static String getLoanFullTypeNameFromAbb(String shortLoanType){
        String returnVal = "null";
        if(shortLoanType.equals("LTL")){
            returnVal="Long Term Loan";
        }
        if(shortLoanType.equals("STL")){
            returnVal="Short Term Loan";
        }
        if(shortLoanType.equals("CCL")){
            returnVal="Credit Card Loan";
        }
        return returnVal;
    }


    public static String getLoanTypeAbbFromFullName(String loanTypeFullName){
        String returnVal = "null";
        if(loanTypeFullName.equals("Long Term Loan")){
            returnVal="LTL";
        }
        if(loanTypeFullName.equals("Short Term Loan")){
            returnVal="STL";
        }
        if(loanTypeFullName.equals("Credit Card Loan")){
            returnVal="CCL";
        }
        return returnVal;
    }

    public static String getStringFromInt(int input){
        return String.valueOf(input);
    }

    public static CustomerAccount getCustomerAccountFromCustomerAtmCardNum(String cardNum){
        ArrayList<CustomerAccount> accountsList = Main.customerAccounts;

        CustomerAccount result = new CustomerAccount("null");

        String searchCardNumStripped = stripSSN(cardNum);

        for(CustomerAccount ca:accountsList){
          String custCardNumStripped = stripSSN(ca.getAtmCardNumber());
          if(!custCardNumStripped.equals("")){
              if(searchCardNumStripped.equals(custCardNumStripped)){
                  result = ca;
                  break;
              }
          }

        }


        return result;
    }



    public static void printCustomerDatabase(){
        System.out.println("Printing customer Database\n");
        for(CustomerAccount ca:Main.customerAccounts){
            System.out.println(ca.toString());
        }
    }

    public static boolean addCustomerAccountToArrayList(CustomerAccount ca){
        try {
            Main.customerAccounts.add(ca);
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" added: "+ca.toString());
            serializeCustomerDatabaseToFile(); // writes data to file
            return true;
        } catch (Exception e) {
            Main.out.println(Main.getDateTimeString()+"Error in add customerToArray.");
            return false;
        }
    }

    public static boolean removeCustomerAccount(String ssn){
        try {
            if(ssnInDatabase(ssn)){
                int index = getIndexOfCustomerAccountInArray(ssn);
                Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" deleted: "+Main.customerAccounts.get(index).toString());
                Main.customerAccounts.remove(index);
                serializeCustomerDatabaseToFile(); // writes the changes to file
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            Main.out.println(Main.getDateTimeString()+"Error in remove customerAccount.");
            return false;
        }
    }

    public static boolean updateCustomerAccount(CustomerAccount ca,String ssn){
        boolean returnVal = false;

        if(ssnValidAndInDatabase(ssn)){
            int indexOfCa = getIndexOfCustomerAccountInArray(ssn);
            CustomerAccount oldCA = getCustomerAccountFromCustomerID(ssn);
            Main.customerAccounts.set(indexOfCa,ca);
            returnVal=true;
            serializeCustomerDatabaseToFile(); // writes changes
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+
                    " updated account: "+oldCA.toString()+" With data: "+ca.toString());

        }

        return returnVal;
    }

    public static void fixIdSystemForCustomerAccounts(){
        fixAllCustomerAccountsFinancialID(); // sets the financial id first

        // then set the financialAccount id
        if(Main.customerAccounts.size()>0){
            for(CustomerAccount ca:Main.customerAccounts){
                ca.fixIDforCustomerAccounts(); // fixes the id on the financial accounts after the customer financial id is set
            }
        }
    }

    public static void fixAllCustomerAccountsFinancialID(){
        for(CustomerAccount ca:Main.customerAccounts){
            boolean hasChecking = ca.hasCheckingAccount();
            boolean hasSavings = ca.hasSavingsAccount();
            boolean hasLoanAccounts = ca.hasLoanAccount();

            if(ca.getFinancialAccountID()==0){ // run if id is not set
                if(hasChecking){ // if they have checking accounts try to get the financial id from those
                    String financialId = ca.getCheckingAccount().getCheckingAcctID();
                    String[] split = financialId.split("-");
                    ca.setFinancialAccountID(DataEntryDriver.getIntFromString(split[0]));
                }else if(hasSavings){ // same but with savings
                    ArrayList<SavingsAccount> savingsAccounts = ca.getSavingsAccounts();
                    String finId = savingsAccounts.get(0).getSavingsAcctID();
                    String[] split = finId.split("-");
                    ca.setFinancialAccountID(DataEntryDriver.getIntFromString(split[0]));
                }else if(hasLoanAccounts){ // same but with loans
                    ArrayList<LoanAccount> loanAccounts = ca.getLoanAccounts();
                    String finId = loanAccounts.get(0).getLoanAccountID();
                    String[] split = finId.split("-");
                    ca.setFinancialAccountID(DataEntryDriver.getIntFromString(split[0]));
                }
            }


        }

        // loop again because we have to read and figure all the customer IDs first before we can start
        // generating any id for the ones that don't have accounts or we will get duplicated ids.
        // this is needed to catch any accounts that do not have financial accounts with the embedded customer ID
        // so we can just generate one that does not exist yet.
        for(CustomerAccount ca:Main.customerAccounts){
            boolean hasChecking = ca.hasCheckingAccount();
            boolean hasSavings = ca.hasSavingsAccount();
            boolean hasLoanAccounts = ca.hasLoanAccount();

            if(ca.getFinancialAccountID()==0){
                if(!hasChecking && !hasSavings && !hasLoanAccounts){
                    ca.setFinancialAccountID(Main.generateCustomerId());
                }
            }
        }

    }


    public static int getIndexOfCustomerAccountInArray(String ssn){
        int returnVal = -1;

        if(ssnInDatabase(ssn)){
            for(int i=0;i<Main.customerAccounts.size();i++){
                CustomerAccount ca = Main.customerAccounts.get(i);
                if(stripSSN(ssn).equals(stripSSN(ca.getCustID()))){
                    returnVal=i;
                }
            }
        }

        return returnVal;
    }


    public static CustomerAccount getCustomerAccountFromFinancialID(String financialID){
        CustomerAccount returnAccount = new CustomerAccount();
        int financialIDInt = getIntFromString(financialID);

        for(CustomerAccount ca:Main.customerAccounts){
            if(ca.getFinancialAccountID() == financialIDInt){
                returnAccount = ca;
                break;
            }
        }
        return returnAccount;
    }

    public static CustomerAccount getCustomerAccountFromFinancialID(int financialID){
        String finIDString = getStringFromInt(financialID);
        return getCustomerAccountFromFinancialID(finIDString);
    }



    public static <T> T getFinancialAccountObjectFromFixedID(String fixedID){
        // use caution with how and when you call this.
        String[] split = null;
        CustomerAccount ca = null;
        if(fixedID!=null){
            split = fixedID.split("-");
            ca = getCustomerAccountFromFinancialID(split[0]); // first part is always the baseID
        }else{
            ca = new CustomerAccount();
        }


        T returnValue = null;

        if(ca.hasCheckingAccount()){
            if(ca.getCheckingAccount().getCheckingAcctIDFixed().equals(fixedID)){
                returnValue =  (T) ca.getCheckingAccount();
            }
        }

        if(ca.hasSavingsAccount()){
            ArrayList<SavingsAccount> savingsAccounts = ca.getSavingsAccounts();
            for(SavingsAccount sa:savingsAccounts){
                if(sa.getSavingsAcctIDFixed().equals(fixedID)){
                    returnValue =  (T) sa;
                    break;
                }
            }

        }

        if(ca.hasLoanAccount()){
            ArrayList<LoanAccount> loanAccounts = ca.getLoanAccounts();
            for(LoanAccount la:loanAccounts){
                if(la.getLoanAccountIDFixed().equals(fixedID)){
                    returnValue =  (T) la;
                    break;
                }
            }
        }

        return returnValue;

    }


    public static String stripSSN(String ssn){
        String result = ssn.replaceAll("[^\\d]", "");
        return result;
    }



    public static String fixSSN(String ssnStripped){
        String result = ssnStripped;
        result = stripSSN(result);

        String p1;
        String p2;
        String p3;

        if(result.length()==3){
            p1 = result;
            result= p1+"-";
            return result;
        }else if(result.length()>3 && result.length()<6){
            if(result.length()==5){
                p1 = result.substring(0,3);
                p2 = result.substring(3,5);
                result= p1+"-"+p2+"-";
                return result;
            }else{
                p1 = result.substring(0,3);
                p2 = result.substring(3,result.length());
                result= p1+"-"+p2;
                return result;
            }
        }else if(result.length()>5 && result.length()<10){
            p1 = result.substring(0,3);
            p2 = result.substring(3,5);
            p3 = result.substring(p1.length()+p2.length());
            if(result.length()==9){
                return  p1+"-"+p2+"-"+p3;
            }
            result= p1+"-"+p2+"-"+p3;
            return result;
        }else if(result.length()>9){
            result = result.substring(0,9);
            return fixSSN(result);
        }else{
            return result;
        }


    }


    public static boolean ssnValidAndInDatabase(String ssnInput){
        boolean returnVal = false;

        String ssnStripped = stripSSN(ssnInput);

        if(ssnValid(ssnStripped) && ssnInDatabase(ssnStripped)){
            returnVal = true;
        }
        return returnVal;
    }

    public static boolean ssnInDatabase(String ssnInput){
        boolean returnVal = false;

        String ssnStripped = stripSSN(ssnInput);
        for(CustomerAccount ca : Main.customerAccounts){
            String custIdStripped = stripSSN(ca.getCustID());
            if(custIdStripped.equals(ssnStripped)){
                //System.out.println("ssn is in the database");
                returnVal = true;
            }
        }
        //printCustomerDatabase();
        return returnVal;
    }

    public static boolean ssnValid(String ssn){
        boolean returnval = false;
        String ssnStripped = stripSSN(ssn);
        if(ssnStripped.length()==9){
            returnval = true;
        }
        return returnval;
    }

    public static boolean zipValid(String zip){
        return zip.length() == 5;
    }

    public static void validateZipField(TextField zipField){
        zipField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,5}")) {
                    zipField.setText(oldValue);
                }
            }
        });

    }


    // USE FOR VALIDATION OF ALL BALANCE FIELDS
    public static void validateBalanceAmountField(TextField textField,boolean allowNegative) { // true to allow negative numbers
        if(allowNegative){ // if we want negative balance for some reason. like -5428.35
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("(-?\\d{0,7}([\\.]\\d{0,2})?)?")) {
                        textField.setText(oldValue);
                    }
                }
            });
        }else{ // no negative. use this one for most if not all cases.
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("(\\d{0,7}([\\.]\\d{0,2})?)?")) {
                        textField.setText(oldValue);
                    }
                }
            });
        }
    }

    // USE TO VALIDATE INTEREST RATE AS DECIMAL OR WHOLE PERCENT OR AN INTEGER VALUE ONLY
    // if interest rate type is false then its a cd type . if interest wholePercent true then 50 = .50. if false then input is in decimal 0.50
    public static void validateInterestField(TextField textField, boolean interestRateType, boolean interestWholePercent) {
        if(interestRateType){ // if true then we are validating an interest rate field
            if(interestWholePercent){ // validate for whole percent like 85.568 = 0.85568
                textField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("([123456789][0123456789]?([\\.]\\d{0,3})?)?")) {
                            textField.setText(oldValue);
                        }
                    }
                });
            }else{ // validate for decimal form like 0.85568
                textField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("([0]?([\\.]\\d{0,5})?)?")) {
                            textField.setText(oldValue);
                        }
                    }
                });
            }
        }else{ // false we are validating a cd term in whole number form.
            //System.out.println("Not of interest type so its a CD term in years");
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("([123456789][\\d]?)?")) {
                        //System.out.println("CD WDN old: "+oldValue+" new: "+newValue);
                        textField.setText(oldValue);
                    }
                }
            });

        }
    }

    public static void validateNumberField(TextField textField,int length){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(\\d{0,"+length+"})?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }


    // use to validate the Date Fields in mm/dd/yyyy format or m/d/YYYY like 9/14/2019 or 09/14/2019
    public static void validateDateField(TextField textField,boolean allowShortDate) { // true to allow negative numbers

        if(allowShortDate){ // allow format
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("(\\d{1,2}/\\d{1,2}/(\\d{4}|\\d{2}))?")) {
                        textField.setText(oldValue);
                    }
                }
            });
        }else{ // no negative. use this one for most if not all cases.
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("(\\d{2}/\\d{2}/\\d{4})?")) { // mm/dd/yyyy
                        textField.setText(oldValue);
                    }
                }
            });
        }
    }




    // Input an arrayList of CheckBoxes and will return true if any one of them is selected or false otherwise
    public static boolean isAnyCheckBoxSelected(ArrayList<CheckBox> listOfCheckBoxes){
        boolean returnVal = false;
        if(listOfCheckBoxes!=null){ // make sure its not null
            if(listOfCheckBoxes.size()>0){ // and that it has at least one item
                for(CheckBox checkBox: listOfCheckBoxes){ // loop through the array
                    if(checkBox.isSelected()){ // if checkbox is selected
                        returnVal = true; // toggle the return value to true
                    } // return value stays false otherwise
                }
            }
        }
        return returnVal;
    }

    public static boolean allTextFieldsHaveData(ArrayList<TextField> textFields){
        boolean returnVal = true;

        for(TextField textField:textFields){
            if(textField.getText().length()<1){
                returnVal=false;
            }
        }



        return returnVal;
    }


    public static double getDoubleFromTextField(TextField textField){
        double returnVal = getDoubleFromString(textField.getText());
        return returnVal;
    }

    public static double getDoubleFromString(String inputString){
        double returnVal=0.00;
        try {
            returnVal = Double.parseDouble(inputString);
        } catch (NumberFormatException e) {
            returnVal = 0.0;
        }
        return returnVal;
    }

    public static int getIntFromTextField(TextField textField){
        return getIntFromString(textField.getText());
    }

    public static int getIntFromString(String inputString){
        int returnVal = -1;
        try {
            returnVal= Integer.parseInt(inputString);
        } catch (NumberFormatException e) {
            returnVal=-1;
        }

        return returnVal;

    }


    // add a format to percision
    public static String getStringFromDouble(double input){
        try {
            double rounded = round(input);
            return String.valueOf(rounded);
        } catch (Exception e) {
            return "null";
        }
    }


    // takes a double such as 5.87999999999 and returns 5.89
    // or 500.0000000001 and gives 500.0
    public static double round(double input){
        double returnVal = 0.0;
        String formatted = String.format("%.5f",input);
        double fixed = Double.parseDouble(formatted);
        returnVal = fixed;
        return returnVal;
    }

    public static double round(double value, int places) {
        if(places < 0){
            return value;
        }else{
            BigDecimal bd = new BigDecimal(Double.toString(value));
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }



    public static String makeSSNValid(String ssn){
        String result = ssn;

        if(!ssnInDatabase(result)){
            result = stripSSN(Main.customerAccounts.get(0).getCustID());
        }

        return result;
    }

    public static void setErrLabelText(Label textField, String text){
        textField.setText(text);
    }

    public static int getRandomInt(){
        Random random = new Random();
        int randomInt = random.nextInt(10);
        return randomInt;
    }


    public static String getLocationFileName(URL inputLocation){
        String result = "";

        String locationString = String.valueOf(inputLocation);
        locationString = locationString.replace("file:/","");
        String[] locationStringSplit = locationString.split("/");

        result=locationStringSplit[locationStringSplit.length-1];




        return result;
    }

    public static String formatAccountBalance(double accountBalance){
        String result = "";
        double fixedBalance = Math.round(accountBalance*100.0)/100.0;
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        //System.out.println(currencyFormatter.format(fixedBalance));


        result = currencyFormatter.format(fixedBalance);


        return result;
    }



    // 2019-11-01T14:08:29.608

    public static String getDateString(){ // returns the current date in mm/dd/yyyy format
        String result = "";
        //System.out.println("getDateString");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output

        result = localDate.format(formatter);

        //System.out.println("get date no format: "+localDate);
        //System.out.println("get date formatted: "+result);

        return result;
    }

    public static String getStringFromLocalDateFormatted(LocalDate inputDateObject){
        String result = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output
        result = inputDateObject.format(formatter);
        return result;
    }

    public static LocalDate getCurrentDateObject(){
        //System.out.println("getCurrentDateObject");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output
        localDate.format(formatter);
        return localDate;
    }

    public static String addMonthsToDateString(String inputDate,int monthsToAdd){
        String returnVal = "null";
        String inputDateFixed = fixDateString(inputDate);

        if(!inputDateFixed.equals("null")){ // if not equal to null
            LocalDate localDate = getDateObjectFromString(inputDateFixed);
            LocalDate newDate = localDate.plusMonths(monthsToAdd);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output
            returnVal = newDate.format(formatter);
        }

        return returnVal;
    }

    public static String addDaysToDateString(String inputDate,int daysToAdd){
        String returnVal = "null";
        String inputDateFixed = fixDateString(inputDate);

        if(!inputDateFixed.equals("null")){ // if not equal to null
            LocalDate localDate = getDateObjectFromString(inputDateFixed);
            LocalDate newDate = localDate.plusDays(daysToAdd);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output
            returnVal = newDate.format(formatter);
        }

        return returnVal;
    }

    public static LocalDate getDateObjectFromString(String inputDate){
        LocalDate returnVal = LocalDate.now();
        String inputDateFixed = DataEntryDriver.fixDateString(inputDate);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        if(!inputDateFixed.equals("null")){
            returnVal = LocalDate.parse(inputDateFixed,outputFormatter);
        }

        return returnVal;

    }




    // 7/8/2008
    public static String fixDateString(String inputDateString){ // returns the current date in mm/dd/yyyy format from input string
        String result = "";
        //System.out.println("fix dateString");

        if(inputDateString.equals("null")){ // can either return null or put current date in.
            return "null";
        }
        //System.out.println("fixDateString input: "+inputDateString);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/d/yyyy"); // input formatter

        LocalDate dateInput;

        try {

            dateInput = LocalDate.parse(inputDateString,inputFormatter);

            String outputResult = dateInput.format(outputFormatter);

            result = outputResult;

            //System.out.println("formatted: "+result);


        } catch (DateTimeParseException e) {
            Main.out.println(Main.getDateTimeString()+"Error in fixDateString with input: "+inputDateString+" Error: "+e.toString());
            return "null";
        }

        return result;
    }









    // city stat combo box database related stuff

    public static Connection connectToDB(String databaseName){
        try {
            String connectString = "jdbc:sqlite:" + databaseName;
            Connection conn = DriverManager.getConnection(connectString);
            if(conn == null) {
                conn = null;
            }
            return conn;
        } catch (SQLException e) {
            try {
                Main.out.println("Error in connect to db: Error");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    public static String getZipFromCityState(String state,String city){
        // returns the zip code given the city and state
        String result = "";

        Connection conn = null;
        if(runningFromIDE()){
            conn = connectToDB("src/Resources/zipDatabase.db");
        }else{
            conn = connectToDB(System.getProperty("user.dir")+"/Resources/zipDatabase.db");
        }

        if(conn == null){
            System.exit(1);
        }

        try {
            Statement stmt = conn.createStatement();

            String queryString = String.format("Select * from zips where state like '%s' and city like '%s' and decommissioned like 'false' " +
                    "order by estimatedpopulation",state,city);//and locationtype like 'primary'
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                result = rs.getString("zipcode");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            result = "error";
        }
        closeDB(conn);
        return result;
    }

    public static void closeDB(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> createStatesArray() {
        // populates the states array for the ComboBox
        ArrayList<String> result = new ArrayList<String>();
        if(!runningFromIDE()){
            File test = new File(System.getProperty("user.dir")+"/Resources/zipDatabase.db");
            if(!test.exists()){
                try {
                    Main.out.println(Main.getDateTimeString()+" ZipDatabase does not exist in resources folder");
                    Main.out.println(Main.getDateTimeString()+" Abs path of db: "+test.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Connection conn;
        if(runningFromIDE()){
            conn = connectToDB("src/Resources/zipDatabase.db");
        }else{
            conn = connectToDB(System.getProperty("user.dir")+"/Resources/zipDatabase.db");
        }

        if(conn == null){
            return null;
        }

        try {
            Statement stmt = conn.createStatement();
            String queryString = String.format("Select * from states order by stateFull");
            ResultSet rs = stmt.executeQuery(queryString);
            while(rs.next()){
                result.add(rs.getString("stateFull"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e){
            System.out.println("something up");
        }
        closeDB(conn);
        return result;

    }


    public static String[] getCityStateFromZip(String zip){
        String[] zipInfo = new String[2];

        String userDir = System.getProperty("user.dir").replaceAll("%20"," ");
        Connection conn;
        if(runningFromIDE()){
            conn = connectToDB("src/Resources/zipDatabase.db");
        }else{
            conn = connectToDB(userDir+"/Resources/zipDatabase.db");
        }

        if(conn == null){
            System.out.println("con is NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        }

        try {
            Statement stmt = conn.createStatement();
            String queryString = String.format("Select * from zips where zipcode like '%s' and locationtype like 'PRIMARY' ",zip);
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                zipInfo[0] = rs.getString("city");
                zipInfo[1] = rs.getString("state");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            zipInfo[0]="error"; // catches errors and sets the output accordingly so I can handle it in the Controller
            zipInfo[1] = "error";
        }
        closeDB(conn);
        if(zipInfo[0] == null || zipInfo[1] == null){
            zipInfo[0] = "error";
            zipInfo[1] = "error";
        }
        return zipInfo;
    }

    public static String fullStateToAbb(String fullState) {
        // returns the state abbreviation given the full state name
        String result = "";

        Connection conn = null;
        if(runningFromIDE()){
            conn = connectToDB("src/Resources/zipDatabase.db");
        }else{
            conn = connectToDB(System.getProperty("user.dir")+"/Resources/zipDatabase.db");
        }

        if(conn == null){
            Main.out.println("CreateStatesArray Error Conn is null");
            System.exit(1);
        }

        try {
            Statement stmt = conn.createStatement();
            String queryString = String.format("Select * from states where stateFull like '%s' ",fullState);
            ResultSet rs = stmt.executeQuery(queryString);
            while(rs.next()){
                result = rs.getString("stateAbb");
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB(conn);
        return result;
    }

    public static String stateAbbToFullName(String stateAbb) {
        // returns the state abbreviation given the full state name
        String result = "";

        Connection conn = null;
        if(runningFromIDE()){
            conn = connectToDB("src/Resources/zipDatabase.db");
        }else{
            conn = connectToDB(System.getProperty("user.dir")+"/Resources/zipDatabase.db");
        }

        if(conn == null){
            Main.out.println("CreateStatesArray Error Conn is null");
            System.exit(1);
        }

        try {
            Statement stmt = conn.createStatement();
            String queryString = String.format("Select * from states where stateAbb like '%s' ",stateAbb);
            ResultSet rs = stmt.executeQuery(queryString);
            while(rs.next()){
                result = rs.getString("stateFull");
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDB(conn);
        return result;
    }


    public static boolean runningFromIDE(){
        String userDir = System.getProperty("user.dir");
        File f = new File(userDir+"/src");
        if(f.exists()){
            return true;
        }else{
            return false;
        }

    }









}
