package BankingApp;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    /*
    * My notes go here
    *
    *
    *
    * SAVINGS ACCOUNTS cd and simple
    * find and set interest for the savings accounts
    * methods to credit debit savings CD
    * add fee for early withdrawal
    * add methods to do the roll over
    *
    * validate the manage financial accounts window disable buttons for account types that are not there
    *
    *
    *
    *
    * */



    public static File outputFile;
    public static PrintWriter out;
    public static File outputEmployeeRecord;
    public static PrintWriter outEmployee;

    public static Stage primaryStage; // holds the currently active PRIMARY STAGE
    public static Stage activeStage=null;
    public static Button defaultSceneButton = null;

    // access the customerAccount object and arraylist by all Classes
    public static CustomerAccount customerAccount; // the currently active customer account
    public static ArrayList<CustomerAccount> customerAccounts; //= DataEntryDriver.readFileToCustomerAccountsArrayList();
    public static String currentCustomerID;
    public static EmployeeAccount loggedInEmployee;
    public static CustomerAccount loggedInCustomer;
    public static int lastAccId=1;
    private static int retry =0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        initialize();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Main.primaryStage = new Stage();
        Main.primaryStage.setTitle("Teller Bank Application(WIP)");
        Main.primaryStage.setScene(new Scene(root, 700, 500));
        Main.primaryStage.show();
        activeStage=Main.primaryStage;
        System.out.println("active set to primary in Main Start");

    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("end");


        // here we will condense the arraylist of objects back into a text file
        boolean arrayWrittenToFile = DataEntryDriver.serializeArrayListToFile(customerAccounts);

        if(arrayWrittenToFile){ // if successful then hash file and save has for next compare
            out.println(getDateTimeString()+"The Customer Accounts Database Was successfully Written to the File");
        }else{
            out.println(getDateTimeString()+"There was an error writing the Accounts to a file.");
        }

        out.close();
        outEmployee.close();
    }

    public static void initialize() {
        // here we can initialize our database into the arrayList objects.
        System.out.println("initializing in Main");
        initializeLogFiles(); // checks and creates log files if needed also loads the sqlite module.

        try { // try catch block for the customerDatabase file
            File customerDatabase = new File("src/Resources/customerDatabase");
            // if it does not exist we need to create it from the csv files. AND populate Main.customerAccounts
            if(!customerDatabase.exists()){ // if customer database file does not exist
                DataEntryDriver.createCustomerDatabaseFileFromCSVBaseData(false,""); // read csv files and make database file
                customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList(); // then reads the file
                out.println(getDateTimeString()+"Created Customer Database and read into list.");
                System.err.println("The Customer Database File Was Created.");
            }else{ // database file exist
                customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList(); // just read it into arraylist
                System.out.println(customerAccounts.size());

                if(customerAccounts.size()==0 && retry<3){ // if size is 0, objects were not read in. Declare the file corrupt and start again
                    // going to delete the file and recursive call initialize again.
                    customerDatabase.delete(); // delete the file;
                    System.err.println("THE CUSTOMER DATABASE FILE WAS DELETED DUE TO CHANGES IN THE CLASS FILES.\n" +
                            "THE DATABASE HAS BEEN REBUILT.");
                    retry++;
                    initialize();// recall the initialize method

                }else{// the file read in correctly and we can continue.
                    out.println(getDateTimeString()+"Database Already Existed. Read into list.");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // fixes id for the financial accounts so I can more easily access them in the interface
        for(CustomerAccount ca: customerAccounts){
            ca.fixIDforCustomerAccounts(ca); // sets the fixed id for the customer account objects
        }


        // loops and figures any late payments and applies that to the next payment amount
        for(CustomerAccount ca: customerAccounts){
            if(ca.hasLoanAccount()){
                ArrayList<LoanAccount> loanAccountsArr = ca.getLoanAccounts();
                for(LoanAccount la:loanAccountsArr){
                    FinanceDriver.applyLateFeeOnLoanAccount(ca,la);// should apply the late fee to the next payment if late
                }
            }
            lastAccId = ca.getFinancialAccountID(); // figure the id for when we don't read the csv data
        }
        lastAccId ++; // increment by one so that it is ready for the next account
        FinanceDriver.processChecks(customerAccounts); // sets check to processed if check date is greater than 3 days

    }

    // used to generate a dataTime String for the log output
    public static String getDateTimeString(){
        String result = "";
        LocalDateTime ldt = LocalDateTime.now();
        result = ldt.toString()+" ";
        return result;
    }

    // just adding this so I don't have to remember what class I put these methods in








    public static void printToConsoleAndLog(String message){
        String result = getDateTimeString()+message;
        out.println(result);
        System.out.println(result);
    }


    public static int generateCustomerId(){
        int returnVal= lastAccId;
        lastAccId = lastAccId+1; // should increment it so that its ready for the next id

        if(customerAccounts!=null){
            for(CustomerAccount ca:customerAccounts){
                if(ca.getFinancialAccountID()== returnVal){
                    // somehow the id was already in the database so increment it and start again.
                    lastAccId++;
                    returnVal=lastAccId;
                }
            }
        }



        return returnVal;
    }


    public static String generateNumber(int length){
        char[] digits = "0123456789".toCharArray();
        Random random = new Random();
        String result = "";

        for(int i=0;i<length;i++){
            int randomInt = random.nextInt(digits.length);
            result=result+digits[randomInt];
        }


        return result;

    }

    public static String generateAtmCardNumber(){
        return generateNumber(16);
    }


    public static void testRandomnes(){
        int n = 5;
        String test = generateNumber(n);
        String test2= generateNumber(n);

        int tries = 0;

        while(!test.equals(test2)){
            test2 = generateNumber(n);
            tries++;
        }
        System.err.println("Combinations required to Randomly generate two numbers with length of "+n+" is: "+tries);
    }



    public static long getCurrentTimeMS(){
        return System.currentTimeMillis();
    }

    public static long getTimeDiffMS(long startTime){
        long currentTime = getCurrentTimeMS();
        return  currentTime - startTime;
    }



    // making initialization of log files separate so it's easier to read the main initialize block
    private static void initializeLogFiles(){
        try { // try catch block for the log files
            File sqliteModule = new File("src/Resources/sqlite-jdbc.jar");
            if(sqliteModule.exists()){
                ModuleLoader.addModule(sqliteModule);
            }else{
                System.err.println("Please Check and add the sqlite-jdbc.jar file to Resources Folder!");
                System.exit(0);
            }

            boolean outputFileExisted = true;
            boolean employeeLogFileExisted = true;

            // first initialize the PrintWriters of the log file and employee log file
            outputFile = new File("src/Resources/outputLog.txt");
            outputEmployeeRecord = new File("src/Resources/EmployeeRecord.txt");

            if(!outputFile.exists()){ // if the output file does not exist
                outputFile.createNewFile();
                outputFileExisted = false;
                System.out.println("output log file created");
            }
            FileWriter ofw = new FileWriter(outputFile,true);
            BufferedWriter ofbw = new BufferedWriter(ofw);
            out = new PrintWriter(ofbw);

            if(!outputEmployeeRecord.exists()){// if it does not exist
                outputEmployeeRecord.createNewFile();
                employeeLogFileExisted = false;
                System.out.println("Employee Log file Created");
            }
            FileWriter ofwEmployee = new FileWriter(outputEmployeeRecord,true);
            BufferedWriter ofbwEmployee = new BufferedWriter(ofwEmployee);
            outEmployee = new PrintWriter(ofbwEmployee);

            if(!outputFileExisted){ // if the file was created for first time no line breaks
                out.println(getDateTimeString()+"Initializing in Main First Run.");
            }else{ // else put a new line to separate the program sessions
                out.println("\n"+getDateTimeString()+"Initializing in Main.");
            }
            if(!employeeLogFileExisted){
                outEmployee.println(getDateTimeString()+"New Log Session.");
            }else{
                outEmployee.println("\n"+getDateTimeString()+"New Log Session.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File createExportFile(String fileName){
        String fileNameOut = "src/Resources/export/"+fileName;
        if(!fileNameOut.contains(".csv")){ // if I forgot to add .csv to the file
            fileNameOut = fileNameOut+".csv"; // add .csv to the end
        }
        File exportDir = new File(System.getProperty("user.dir")+"/src/Resources/export");
        if(!exportDir.exists()){
            exportDir.mkdir(); // make export directory if not there
        }

        File returnFile = null;
        try {
            returnFile = new File(fileNameOut);

            if(!returnFile.exists()){ // if the file does not exist
                returnFile.createNewFile(); // create it
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnFile;


    }

    public static PrintWriter createPrintWriter(File inputFile,boolean append){
        PrintWriter returnPrintWriter = null;
        try {
            FileWriter ofw = new FileWriter(inputFile,append); // append data
            BufferedWriter ofbw = new BufferedWriter(ofw);
            returnPrintWriter = new PrintWriter(ofbw);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return returnPrintWriter;

    }









}

