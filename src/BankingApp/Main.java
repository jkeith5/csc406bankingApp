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

    private static String customerDataDir = "src/Resources/customerData";
    private static File customerFile = new File(customerDataDir);
    private static DataEntryDriver customerData = new DataEntryDriver(customerFile);
    public static File outputFile;
    public static PrintWriter out;

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        initialize();
        //Parent root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Main.primaryStage = new Stage();
        Main.primaryStage.setTitle("Teller Bank Application(WIP)");
        Main.primaryStage.setScene(new Scene(root, 700, 500));
        Main.primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("end");

        // here we will condense the arraylist of objects back into a text file
        out.close();
        DataEntryDriver.serializeArrayListToFile(Controller.customerAccounts);

    }

    public static void dataEntry(String fName, String lName, String socialSec, String streetAddress, String city, String zipCode, String state) {
        ArrayList<String> tempData = new ArrayList<>();
        tempData.add(fName);
        tempData.add(lName);
        tempData.add(socialSec);
        tempData.add(streetAddress);
        tempData.add(city);
        tempData.add(zipCode);
        tempData.add(state);
        customerData.printData(tempData);

    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }


    public void initialize() {
        // here we can initialize our database into the arrayList objects.
        System.out.println("initalizing");

        outputFile = new File("src/Resources/outputLog.txt");

        try {
            File customerDatabase = new File("src/Resources/customerDatabase");
            out = new PrintWriter(outputFile);

            // if it does not exist we need to create it from the csv files. NOTE just delete the file to recreate it if needed
            if(!customerDatabase.exists()){
                DataEntryDriver.createCustomerAccountsArray();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}

