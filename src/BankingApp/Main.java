package BankingApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


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

    // access the customerAccount object and arraylist by all Classes
    public static CustomerAccount customerAccount;
    public static ArrayList<CustomerAccount> customerAccounts; //= DataEntryDriver.readFileToCustomerAccountsArrayList();
    public static String currentCustomerID;
    public static EmployeeAccount loggedInEmployee;

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

        System.out.println("In main start block");

    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("end");

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

    public static String getDateTimeString(){
        String result = "";
        LocalDateTime ldt = LocalDateTime.now();
        result = ldt.toString()+" ";
        return result;
    }





}

