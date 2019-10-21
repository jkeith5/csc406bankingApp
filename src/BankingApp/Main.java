package BankingApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    private static String customerDataDir = "C:\\Users\\Owner\\IdeaProjects\\csc406bankingApp\\src\\sample\\customerData";
    private static File customerFile = new File(customerDataDir);
    private static DataEntry customerData = new DataEntry(customerFile);

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Teller Bank Application(WIP)");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
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
}

// test comment to see if i have to login every time i make a commit.