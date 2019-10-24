package BankingApp;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static BankingApp.Main.primaryStage;

public class Controller {
    public static Stage primaryStage = Main.primaryStage;

    @FXML TextField fNameTextField;
    @FXML TextField lNameTextField;
    @FXML TextField socialSecTextField;
    @FXML TextField streetAddressTextField;
    @FXML TextField cityTextField;
    @FXML TextField zipCodeTextField;
    @FXML TextField stateTextField;
    @FXML
    Label successfulEntryLabel;
    @FXML Button enterButton;
    @FXML Button TellerScreen;
    @FXML Button BankManagerScreen;
    @FXML Button TellerInterAddNew;
    @FXML Button TellerInterManage;
    @FXML Button TellerInterPrev;
    @FXML TextField ManageUserSSNField;
    @FXML Button ManageUserLookupButton;
    @FXML Button ManageUserPrevButton;
    @FXML Button AddNewUserPreviousButton;
    @FXML Button BankManagerPrevButton;
    @FXML Button ManageExistingDispDataPrevButton;


    String fName, lName, socialSec, streetAddress, city, zipCode, state;

    @FXML
    public void handleEnterButton(javafx.event.ActionEvent event) {
        this.fName = fNameTextField.getText();
        this.lName = lNameTextField.getText();
        this.socialSec = socialSecTextField.getText();
        this.streetAddress = streetAddressTextField.getText();
        this.city = cityTextField.getText();
        this.zipCode = zipCodeTextField.getText();
        this.state = stateTextField.getText();

        Main.dataEntry(fName, lName, socialSec, streetAddress, city, zipCode, state);

        successfulEntryLabel.setText("Data storage for " + fName + " " + lName + " was successful!");
        successfulEntryLabel.visibleProperty().setValue(true);
    }

    @FXML
    public void mainInterfaceTellerButton(){
        System.out.println("hi");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("TellerInterface.fxml"));
            //stage.setTitle("Teller Interface");
            //stage.setScene(new Scene(root, 700, 500));
            //stage.show();

            //this.primaryStage = Main.getPrimaryStage();
            Main.primaryStage.setTitle("Teller Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void mainInterfaceManagerButton(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("BankManagerInterface.fxml"));
            Main.primaryStage.setTitle("Bank Manager Interface");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void tellerInterfaceAddNewButton(){
        System.out.println("hi");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
            //Stage stage = new Stage();
            Main.primaryStage.setTitle("Add a new user Account");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void tellerInterfaceManageLookupButton(){
        System.out.println("001001001 found user launching interface");
        // put code here to find user to display data on the screen that is about to be launched.

        // put if statement if ssn did not match user display error on the ManageExistingUser.fxml screen
        // else continue to launch window and display data

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayData.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    public void tellerInterfaceManageButton(){
        System.out.println("hi");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUser.fxml"));
            Main.primaryStage.setTitle("Manage existing user");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // will need this one because teller and bank manager can both see different items on user accounts
    @FXML
    public void bankManagerInterfaceManageLookupButton(){
        System.out.println("001001001 found user launching interface");
        // put code here to find user to display data on the screen that is about to be launched.

        // put if statement if ssn did not match user display error on the ManageExistingUser.fxml screen
        // else continue to launch window and display data

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayData.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void goToMainScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Main.primaryStage.setTitle("Teller Bank Application(WIP)");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToTellerScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("TellerInterface.fxml"));
            Main.primaryStage.setTitle("Teller Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToManageLookupScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUser.fxml"));
            Main.primaryStage.setTitle("Lookup User");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void closeWindow(){
        Parent root = null;
        Stage stage = (Stage) AddNewUserPreviousButton.getScene().getWindow();
        stage.close();

    }


}
