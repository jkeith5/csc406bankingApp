package BankingApp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;


public class Main extends Application {

    //private static String customerDataDir = "src/Resources/customerData";
    //private static File customerFile = new File(customerDataDir);
    //private static DataEntryDriver customerData = new DataEntryDriver(customerFile);
    public static File outputFile;
    public static PrintWriter out;
    public static File outputEmployeeRecord;
    public static PrintWriter outEmployee;

    public static Stage primaryStage;

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
        System.out.println("in main start block");

    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("end");

        // here we will condense the arraylist of objects back into a text file
        out.close();
        outEmployee.close();
        DataEntryDriver.serializeArrayListToFile(customerAccounts);

    }

//    public static void dataEntry(String fName, String lName, String socialSec, String streetAddress, String city, String zipCode, String state) {
//        ArrayList<String> tempData = new ArrayList<>();
//        tempData.add(fName);
//        tempData.add(lName);
//        tempData.add(socialSec);
//        tempData.add(streetAddress);
//        tempData.add(city);
//        tempData.add(zipCode);
//        tempData.add(state);
//        customerData.printData(tempData);
//
//    }

    public void initialize() {
        // here we can initialize our database into the arrayList objects.
        System.out.println("initalizing in Main");

        //customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();
        outputFile = new File("src/Resources/outputLog.txt");
        outputEmployeeRecord = new File("src/Resources/EmployeeRecord.txt");

        try {
            File customerDatabase = new File("src/Resources/customerDatabase");
            out = new PrintWriter(outputFile);
            outEmployee = new PrintWriter(outputEmployeeRecord);

            // if it does not exist we need to create it from the csv files. AND populate Main.customerAccounts
            // NOTE just delete the file to recreate it if needed
            if(!customerDatabase.exists()){
                DataEntryDriver.createCustomerAccountsArray();
                customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();
            }else{
                customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}

