package BankingApp;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public static Stage primaryStage = Main.primaryStage;

    public boolean tellerLogIn;
    public boolean managerLogIn;
    public boolean tellerPendingLogin;
    public boolean managerPendingLogin;
    public Scene tempScene;

    @FXML TextField fNameTextField;
    @FXML TextField lNameTextField;
    @FXML TextField socialSecTextField;
    @FXML TextField streetAddressTextField;
    @FXML TextField cityTextField;
    @FXML TextField zipCodeTextField;
    @FXML TextField stateTextField;
    @FXML Label successfulEntryLabel;

    @FXML TextField LoginInterUser;
    @FXML TextField LoginInterPass;
    @FXML Button LoginInterLoginButton;
    @FXML Button LoginInterExitButton;
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


    // Note when I say ManageExistingTeller I mean the ManageExistingUser interface for the Teller account
    @FXML Button ManageExistingTellerUpdateDataButton;
    @FXML Button ManageExistingTellerViewRecentActivityButton;
    @FXML Button ManageExistingTellerDebitCreditAccountButton;
    @FXML RadioButton ManageExistingTellerCheckingAccount;
    @FXML RadioButton ManageExistingTellerSavingsAccount;
    @FXML TextField ManageExistingTellerFundsTransferAmount;





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
        Parent login = null;
        try {
            //stage.setTitle("Teller Interface");
            //stage.setScene(new Scene(root, 700, 500));
            //stage.show();
            //this.primaryStage = Main.getPrimaryStage();

            if(tellerLogIn){// if teller is logged in after login window closes and it recalls this method
                tellerPendingLogin=false;
                root = FXMLLoader.load(getClass().getResource("TellerInterface.fxml"));

                Main.primaryStage.setTitle("Teller Interface");
                Main.primaryStage.setScene(new Scene(root,700,500));
                Main.primaryStage.show();
            }else{
                login = FXMLLoader.load(getClass().getResource("TellerLogin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Teller Login");
                stage.setScene(new Scene(login,382,420));
                stage.show();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void tellerLoginButton(){
        tellerPendingLogin = true;
        loginInterfaceLoginButton();
    }

    @FXML
    public void ManagerLoginButton(){
        //
        managerPendingLogin=true;
        loginInterfaceLoginButton();
    }

    @FXML
    public void loginInterfaceLoginButton(){
        if(tellerPendingLogin){
            tellerLogIn = validateLoginCreds("Teller");
            if(tellerLogIn){
                tellerPendingLogin=false;
                closeWindow();
                mainInterfaceTellerButton();
            }
        }

        if(managerPendingLogin){
            managerLogIn = validateLoginCreds("Manager");
            if(managerLogIn){
                managerPendingLogin=false;
                closeWindow();
                mainInterfaceManagerButton();
            }

        }


        // now the tellerLogIn and managerLogIn booleans let us know if, and of what type, a user is logged in as.
    }

    @FXML
    public void loginInterfaceExitButton(){
        tellerLogIn=false;
        managerLogIn=false;
        Stage stage = (Stage) LoginInterExitButton.getScene().getWindow();
        stage.close();
        goToMainScene();
    }

    public boolean validateLoginCreds(String userType){// userType is either Teller or Manager
        boolean returnVal = false;
        if(userType == "Teller"){
            if(LoginInterUser.getText() == "teller" || LoginInterUser.getText().length()>0){
                // here we would validate the credintials but They're always good for now

                if(LoginInterPass.getText().length()>0){
                    // here we would validate the password for the user
                    returnVal=true;
                }else{
                    returnVal=false;
                }
            }else{
                System.out.println("not a valid username for a Teller Account");
            }
        }
        if(userType == "Manager"){
            // verify the credentials of the Manager account
            if(LoginInterUser.getText() == "manager" || LoginInterUser.getText().length()>0){
                if(LoginInterPass.getText().length()>0){
                    returnVal=true;
                }else{
                    returnVal=false;
                }
            }
        }

        return returnVal;
    }


    @FXML
    public void mainInterfaceManagerButton(){
        Parent root = null;
        Parent login = null;
        try {
//            root = FXMLLoader.load(getClass().getResource("BankManagerInterface.fxml"));
//            Main.primaryStage.setTitle("Bank Manager Interface");
//            Main.primaryStage.setScene(new Scene(root, 700, 500));
//            Main.primaryStage.show();

            if(managerLogIn){
                managerPendingLogin = false;
                root = FXMLLoader.load(getClass().getResource("BankManagerInterface.fxml"));
                Main.primaryStage.setTitle("Bank Manager Interface");
                Main.primaryStage.setScene(new Scene(root, 700, 500));
                Main.primaryStage.show();
            }else{
                managerPendingLogin =true;
                login = FXMLLoader.load(getClass().getResource("ManagerLogin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Bank Manager Login");
                stage.setScene(new Scene(login,382,420));
                stage.show();
            }



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

    @FXML
    public void tellerInterfaceManageLookupButton(){
        System.out.println("001001001 found user launching interface");
        // put code here to find user to display data on the screen that is about to be launched.

        // put if statement if ssn did not match user display error on the ManageExistingUser.fxml screen
        // else continue to launch window and display data

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void tellerInterfaceManageUpdateDataButton(){
        //
        Parent root = null;

        try {

            root = FXMLLoader.load(getClass().getResource("ManageExistingUserUpdateData.fxml"));
            Scene primarySceneUserData = root.getScene();
            tempScene = primarySceneUserData;

            Main.primaryStage.setTitle("Update Customer Data");
            Main.primaryStage.setScene(new Scene(root,700,500));
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
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));
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

            tellerLogIn = false;
            managerLogIn = false;

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
        Stage stage = (Stage) LoginInterLoginButton.getScene().getWindow();
        stage.close();

    }











    // EVERYTHING BELOW THIS LINE TO END COMMENT IS TESTING PURPOSES ONLY

    @FXML
    public void test(){
        System.out.println("TESTING");

    }

    @FXML
    public void test2(){
        System.out.println("test2");

    }



    // END COMMENT


}
