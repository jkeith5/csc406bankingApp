package BankingApp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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


    public void initialize() throws IOException {
        // here we can initialize our database into the arrayList objects.
        System.out.println("initalizing");

        outputFile = new File("src/Resources/outputLog.txt");
        out = new PrintWriter(outputFile);

        String userDir = System.getProperty("user.dir");
        File f = new File(userDir+"/src");
        if(f.exists()){
            return;
        }

        File resourceDir = new File(System.getProperty("user.dir")+"/Resources");
        if(!resourceDir.exists()){
            System.out.println("Resources did not exist");
        }else{
            // if resources exist do this
            // Call the DataEntry Class methods to read the data from csv into arrays

            String[] files = {"customerData","CheckingAccounts.csv","Checks.csv","CustomersBase.csv","SavingsAccounts.csv","LoanAccounts.csv"};
            // here we read the files into the arraylist of objects






        }
    }


}

// test comment to see if i have to login every time i make a commit.