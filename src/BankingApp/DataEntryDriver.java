package BankingApp;

import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
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



        ArrayList<CustomerAccount> caRead = readCustomerAccountsCSV();
        System.out.println(caRead.toString());

        //result = caRead;


        //CustomerAccount acc4 = new CustomerAccount()

        //
        SavingsAccount saving1 = new SavingsAccount(acc1.custID,"1",457.58,0.022,"1969/02/17",true,"2022/05/18");
        SavingsAccount saving2 = new SavingsAccount(acc2.custID,"2",7485.24,0.028,"2007/08/08",true,"2019/12/11");
        SavingsAccount saving3 = new SavingsAccount(acc3.custID,"3",274.12,0.017,"2018/12/18",false);

        CheckingAccount checking1 = new CheckingAccount(acc1.custID,"1",147.58,"2019/05/05",false,true);
        CheckingAccount checking2 = new CheckingAccount(acc2.custID,"2",5787.54,acc2.dateCreated,true,true);
        CheckingAccount checking3 = new CheckingAccount(acc3.custID,"3",57.14,acc3.dateCreated,false,false);


        LoanAccount loan1 = new LoanAccount(acc1.custID,10000,874.58,0.018,false,"CCL");
        LoanAccount loan2 = new LoanAccount(acc2.custID,8750.00,1244.58,0.011,true,"STL");

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

    public static ArrayList<CustomerAccount> readCustomerAccountsCSV(){ // Reads CustomerDatabase.csv
        ArrayList<CustomerAccount> result = new ArrayList<>();

        File customerBase = new File("src/Resources/CustomersBase.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(customerBase));
            String line;

            br.readLine();
            CustomerAccount caTemp;
            while((line = br.readLine()) != null){
                String[] lineSplit = line.split(",");
                System.out.println(Arrays.toString(lineSplit));
                CustomerAccount ca = new CustomerAccount(lineSplit[0],lineSplit[1],lineSplit[2],lineSplit[3],lineSplit[4],
                        lineSplit[5],lineSplit[6],lineSplit[7]);

                result.add(ca);

            }



            for(CustomerAccount caz:result){ // base data at this point
                System.out.println(caz.toString());
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static ArrayList<CheckingAccount> readCheckingAccountsToArrList(){ // reads CheckingAccounts.csv
        ArrayList<CheckingAccount> result = new ArrayList<>();
        File checkingAccountsFile = new File("src/Resources/CheckingAccounts.csv");

        try {
            BufferedReader br = new BufferedReader(new FileReader(checkingAccountsFile));
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                System.out.println(Arrays.toString(split));
                CheckingAccount ca = new CheckingAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6]);

                try {
                    result.add(ca);
                } catch (NullPointerException e){
                    System.out.println("Null pointer for: "+ca.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static ArrayList<SavingsAccount> readSavingsAccountsToArrList(){ // Reads the SavingsAccounts.csv
        ArrayList<SavingsAccount> result = new ArrayList<>();

        File savingsAccountFile = new File("src/Resources/SavingsAccounts.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(savingsAccountFile));
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                System.out.println(Arrays.toString(split));

                SavingsAccount sa = new SavingsAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6]);
                result.add(sa);
                System.out.println("Testing: print double interest rate: "+sa.getInterestRate());

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }


    public static ArrayList<LoanAccount> readLoanAccountsToArrList(){ // Reads the LoanAccounts.csv
        ArrayList<LoanAccount> result = new ArrayList<>();

        File loanAccountFile = new File("src/Resources/LoanAccounts.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(loanAccountFile));
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                System.out.println(Arrays.toString(split));

                LoanAccount la = new LoanAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6],split[7],split[8],split[9],split[10]);
                result.add(la);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }


    public static ArrayList<SavingsAccount> readChecksToArrList(){ // Reads the Checks.csv
        ArrayList<SavingsAccount> result = new ArrayList<>();

        File savingsAccountFile = new File("src/Resources/SavingsAccounts.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(savingsAccountFile));
            String line;
            br.readLine();

            while((line = br.readLine()) != null){
                String[] split = line.split(",");
                System.out.println(Arrays.toString(split));

                SavingsAccount sa = new SavingsAccount(split[0],split[1],split[2],split[3],split[4],split[5],split[6]);
                result.add(sa);
                System.out.println("Testing: print double interest rate: "+sa.getInterestRate());

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
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

        } catch (EOFException e) {
            System.out.println("");
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }catch (IOException e){
            Main.out.println(Main.getDateTimeString()+"Error in readFile. File not read in to array.");
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

    public static boolean updateCustomerAccount(CustomerAccount ca,String ssn){
        boolean returnVal = false;

        if(ssnValidAndInDatabase(ssn)){
            int indexOfCa = getIndexOfCustomerAccountInArray(ssn);
            CustomerAccount oldCA = getCustomerAccountFromCustomerID(ssn);
            Main.customerAccounts.set(indexOfCa,ca);
            returnVal=true;
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+
                    " updated account: "+oldCA.toString()+" With data: "+ca.toString());

        }



        return returnVal;
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
        String result = ssn.replaceAll("[^\\d]", "");
        return result;
    }



    public static String fixSSN(String ssnStripped){
        String result = ssnStripped;
        result = stripSSN(result);

        if(result.equalsIgnoreCase("null") || result.length()!=9){
            return "null";
        }


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

    public static void validateZip(TextField zipField){
        String result = zipField.getText().replaceAll("[^\\d]", "");
        if(result.length()>5){
            result=result.substring(0,5);
        }

        zipField.setText(result);
        zipField.positionCaret(zipField.getText().length());

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

    public static String formatAccountBalance(double accountBalance){
        String result = "";
        double fixedBalance = Math.round(accountBalance*100.0)/100.0;
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        System.out.println(currencyFormatter.format(fixedBalance));

        result = currencyFormatter.format(fixedBalance);


        return result;
    }



    // 2019-11-01T14:08:29.608

    public static String getDateString(){ // returns the current date in mm/dd/yyyy format
        String result = "";
        System.out.println("getDateString");
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output

        result = localDate.format(formatter);

        System.out.println("get date no format: "+localDate);
        System.out.println("get date formatted: "+result);

        return result;
    }


    // 7/8/2008
    public static String fixDateString(String inputDateString){ // returns the current date in mm/dd/yyyy format from input string
        String result = "";
        System.out.println("fix dateString");

        if(inputDateString.equals("null")){ // can either return null or put current date in.
            return "null";
        }

        System.out.println("fixDateString input: "+inputDateString);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // formatter for date output
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/d/yyyy"); // input formatter

        LocalDate dateInput;

        try {

            dateInput = LocalDate.parse(inputDateString,inputFormatter);

            String outputResult = dateInput.format(outputFormatter);

            result = outputResult;

            System.out.println("formatted: "+result);


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
