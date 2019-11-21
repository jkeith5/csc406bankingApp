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

    //private static String customerDataDir = "src/Resources/customerData";
    //private static File customerFile = new File(customerDataDir);
    //private static DataEntryDriver customerData = new DataEntryDriver(customerFile);
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
    public static boolean taskFinished = false;
    public static boolean taskRunning = false;

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
        System.err.println("WARNING If program does not run. Go to Resources in the source directory and \n" +
                "Delete THE 'customerDatabase' FILE  NOT The CustomerBase.csv. Run Program Again.");

        for(CustomerAccount ca: customerAccounts){

            if(ca.hasCheckingAccount()){
                System.out.println(ca.getBasicDataShort());
                System.out.println("Checking bal: "+ca.getCheckingAccount().getAccountBalance());
                System.out.println("is gold: "+ca.getCheckingAccount().isGoldAccount());
                //System.out.println("checking bal: "+ca.getCheckingAccount().getAccountBalance()+" isGold: "+ca.getCheckingAccount().isGoldAccount());
                //System.out.println(generateCustomerId());
            }

        }




        // testing data start here

        System.out.println("\n\n\n\nSTART DEBUG TEST DATA:");
        for(CustomerAccount ca: customerAccounts){
            ca.printTransactions();
            ca.printChecks();
        }

        System.out.println("END DEBUG TEST DATA");










        // testing data end here



        // here we will condense the arraylist of objects back into a text file
        boolean arrayWrittenToFile = DataEntryDriver.serializeArrayListToFile(customerAccounts);
        //DataEntryDriver.serializeArrayListToFile(customerAccounts);

        if(arrayWrittenToFile){
            out.println(getDateTimeString()+"The Customer Accounts Database Was sucessfully Written to the File");
        }else{
            out.println(getDateTimeString()+"There was an error writing the Accounts to a file.");
        }





        out.close();
        outEmployee.close();







    }

    public void initialize() {
        // here we can initialize our database into the arrayList objects.
        System.out.println("initializing in Main");

        try {
            File sqliteModule = new File("src/Resources/sqlite-jdbc.jar");
            ModuleLoader.addModule(sqliteModule);



            // first initialize the PrintWriters of the log file and employee log file
            outputFile = new File("src/Resources/outputLog.txt");
            outputEmployeeRecord = new File("src/Resources/EmployeeRecord.txt");

            if(!outputFile.exists()){
                outputFile.createNewFile();
            }
            FileWriter ofw = new FileWriter(outputFile,true);
            BufferedWriter ofbw = new BufferedWriter(ofw);
            out = new PrintWriter(ofbw);

            if(!outputEmployeeRecord.exists()){
                outputEmployeeRecord.createNewFile();
            }
            FileWriter ofwEmployee = new FileWriter(outputEmployeeRecord,true);
            BufferedWriter ofbwEmployee = new BufferedWriter(ofwEmployee);
            outEmployee = new PrintWriter(ofbwEmployee);

        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(getDateTimeString()+" Initializing in Main");


        try {
            File customerDatabase = new File("src/Resources/customerDatabase");
            // if it does not exist we need to create it from the csv files. AND populate Main.customerAccounts
            // NOTE just delete the file to recreate it if needed
            if(!customerDatabase.exists()){
                DataEntryDriver.createCustomerAccountsArray();
                customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();
                out.println(getDateTimeString()+"Created Customer Database and read into list.");
            }else{
                customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();
                out.println(getDateTimeString()+"Database Already Existed. Read into list.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



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
        lastAccId = lastAccId+1;
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


    // test commit 2

    // used to delay the ui of JavaFX for a specified amount of seconds without hanging the FX Thread
    public static Task<Void> getFXSleepTask(long milliseconds){
        Task task = new Task<Void>() {
            @Override public Void call() throws Exception {
                System.out.println("TASK START OR RUNNING");
                taskRunning = true;
                taskFinished=false;
                try {
                    Thread.sleep(milliseconds);
                } catch (Exception e) {
                }
                System.out.println("FINISHED IN TASK");
                taskFinished = true;
                taskRunning=false;
                return null;
            }
        };

        return task;
    }



}

