package BankingApp;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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


    // this method will create and return an ArrayList of CustomerAccount objects from the csv files located
    // in the Resources Directory and then save it as a file in the Resources folder
    public static void createCustomerAccountsArray(){
        ArrayList<CustomerAccount> result = new ArrayList<CustomerAccount>();
        // just creating a few temp objects for now

        CustomerAccount acc1 = new CustomerAccount("548-68-8745","Bob","Jones",
                "123 nowhere drive","WonderLand","KS","58744","1950/04/22","2899653222933490");

        CustomerAccount acc2 = new CustomerAccount("687-69-8941","Tom","Diddle",
                "7487 somewhere lane","St Mosis","MO","32568","2007/08/08","4856523569854785");

        CustomerAccount acc3 = new CustomerAccount("987-70-9747","Tim","Toe",
                "1245 anywhere street Apt. 88","Bullhead","AZ","87459","2018/12/18");

        //
        SavingsAccount saving1 = new SavingsAccount(acc1.custID,"1",457.58,0.022,"1969/02/17",true,"2022/05/18");
        SavingsAccount saving2 = new SavingsAccount(acc2.custID,"2",7485.24,0.028,"2007/08/08",true,"2019/12/11");
        SavingsAccount saving3 = new SavingsAccount(acc3.custID,"3",274.12,0.017,"2018/12/18",false);

        CheckingAccount checking1 = new CheckingAccount(acc1.custID,"1",147.58,"2019/05/05",false,true);
        CheckingAccount checking2 = new CheckingAccount(acc2.custID,"2",5787.54,acc2.dateCreated,true,true);
        CheckingAccount checking3 = new CheckingAccount(acc3.custID,"3",57.14,acc3.dateCreated,false,false,3);


        LoanAccount loan1 = new LoanAccount("CCL",acc1.custID,10000,874.58,0.018,false);
        LoanAccount loan2 = new LoanAccount("STL",acc2.custID,8750.00,1244.58,0.011,true);

        Check check1 = new Check("150","1","2019/10/20",287.89,false);
        Check check2 = new Check("478","2","2019/10/22",2145.58,true);


        // could read each file in and then do this in a loop for each account to set the data
        // would be easier to just make the checks database include the custID field instead of having to lookup the acct number

        // tie them together
        acc1.addSavingsAccount(saving1);
        acc1.addCheckingAccount(checking1);
        acc1.addLoanAccountObject(loan1);
        acc1.addCheckObj(check1);

        acc2.addSavingsAccount(saving2);
        acc2.addCheckingAccount(checking2);
        acc2.addLoanAccountObject(loan2);
        acc2.addCheckObj(check2);

        acc3.addSavingsAccount(saving3);
        acc3.addCheckingAccount(checking3);


        result.add(acc1);
        result.add(acc2);
        result.add(acc3);

        // this writes the accounts to the Resources customerDatabase file
        serializeArrayListToFile(result);
    }

    public static boolean serializeArrayListToFile(ArrayList<CustomerAccount> customerAccounts){
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/Resources/customerDatabase"));

            for(CustomerAccount ca:customerAccounts){
                objectOutputStream.writeObject(ca);
            }
            objectOutputStream.close();
            Main.out.println(Main.getDateTimeString()+"ArrayList was written to File.");
            return true;
        } catch (IOException e){
            e.printStackTrace();
            Main.out.println(Main.getDateTimeString()+"Error in serializeArrayListToFile File was not written.");
            return false;
        }

    }

    public static ArrayList<CustomerAccount> readFileToCustomerAccountsArrayList() {
        ArrayList<CustomerAccount> result = new ArrayList<CustomerAccount>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/Resources/customerDatabase"))) {
            while (true) {
                Object read = objectInputStream.readObject();
                if(read == null){
                    break;
                }
                CustomerAccount customerAccountRead = (CustomerAccount) read;
                result.add(customerAccountRead);
            }

        } catch (IOException e) {
            Main.out.println(Main.getDateTimeString()+"Error in readFile. File not read in to array.");
            System.out.println(e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        return result;
    }


    public static CustomerAccount getCustomerAccountFromCustomerID(String customerID){
        System.out.println("start of get customer acct from id");
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

    public static void printCustomerDatabase(){
        System.out.println("Printing customer Database\n");
        for(CustomerAccount ca:Main.customerAccounts){
            System.out.println(ca.toString());
        }
    }

    public static boolean addCustomerAccountToArrayList(CustomerAccount ca){
        try {
            Main.customerAccounts.add(ca);
            System.out.println("adding customer account to array");
            System.out.println(ca.toString());
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" added: "+ca.toString());
            // might run a serialize to file here
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            Main.out.println(Main.getDateTimeString()+"Error in add customerToArray.");
            return false;
        }
    }

    public static boolean removeCustomerAccount(String ssn){
        try {
            if(ssnInDatabase(stripSSN(ssn))){
                int index = getIndexOfCustomerAccountInArray(ssn);
                Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" deleted: "+Main.customerAccounts.get(index).toString());
                Main.customerAccounts.remove(index);
                System.out.println("Removed customer account");


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


    public static int getIndexOfCustomerAccountInArray(String ssn){
        int returnVal = -1;

        if(ssnInDatabase(stripSSN(ssn))){
            for(int i=0;i<Main.customerAccounts.size();i++){
                CustomerAccount ca = Main.customerAccounts.get(i);
                if(stripSSN(ssn).equals(stripSSN(ca.getCustID()))){
                    returnVal=i;
                }
            }
        }

        return returnVal;
    }

    public static String stripSSN(String ssn){
        String result = ssn.replace("-","");
        return result;
    }

    public static String fixSSN(String ssnStripped){
        String result = ssnStripped;
        result = stripSSN(result);

        if(!ssnValid(result)){
            result = makeSSNValid(result);
        }else{
            String p1 = result.substring(0,3);
            String p2 = result.substring(3,5);
            String p3 = result.substring(p1.length()+p2.length());
            result = p1+"-"+p2+"-"+p3;
        }
        return result;
    }


    public static boolean ssnInDatabase(String ssnInput){
        boolean returnVal = false;

        String ssnStripped = stripSSN(ssnInput);
        for(CustomerAccount ca : Main.customerAccounts){
            String custIdStripped = stripSSN(ca.getCustID());
            if(custIdStripped.equals(ssnStripped)){
                System.out.println("ssn is in the database");
                returnVal = true;
            }
        }



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

    public static String makeSSNValid(String ssn){
        String result = ssn;

        if(!ssnInDatabase(result)){
            result = stripSSN(Main.customerAccounts.get(0).getCustID());
        }

        return result;
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



}
