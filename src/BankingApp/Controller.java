package BankingApp;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //public static Stage primaryStage = Main.primaryStage;
    //public static Placeholder placeholder = new Placeholder();

    public static boolean tellerLogIn;
    public static boolean managerLogIn;
    public static boolean tellerPendingLogin;
    public static boolean managerPendingLogin;

    public static ArrayList<CustomerAccount> customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();
    //public ArrayList<CustomerAccount> customerAccountsReadIn = DataEntryDriver.readFileToCustomerAccountsArrayList();


    @FXML TextField mainScreenTest;
    @FXML TextField tf1;
    @FXML TextField tf2;
    @FXML TextField tf3;
    @FXML TextField generalTestTextField;
    @FXML Button generalTestButton;

    @FXML Button mainScreenTestButton;



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

    @FXML Button TellerUpdateDataPreviousButton;
    @FXML Button TellerUpdateDataSaveButton;
    @FXML TextField TellerUpdateDataSSN;
    @FXML TextField TellerUpdateDataFirstName;
    @FXML TextField TellerUpdateDataLastName;
    @FXML TextField TellerUpdateDataStreetAddress;
    @FXML TextField TellerUpdateDataCity;
    @FXML TextField TellerUpdateDataState;
    @FXML TextField TellerUpdateDataZip;









    String fName, lName, socialSec, streetAddress, city, zipCode, state;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("\ninitializing controller");
        System.out.println(customerAccounts);
        System.out.println("\n\n");

        String locationString = DataEntryDriver.getLocationFileName(location);
        System.out.println(locationString);

        // Okay so note to self. Each time an interface is created from one of the
        // many buttons in this program, The

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        System.out.println("\n\nStackTrace\n");
        System.out.println("Stack Trace: "+stackTraceElements[10].toString());

        int z = -1;
        if(z!=-1){
            int i =0;
            for(StackTraceElement ste:stackTraceElements){
                //System.out.println("Calling Method Name: "+ste.getMethodName());
                //System.out.println("Line Number of Caller: "+ste.getLineNumber());
                System.out.println(String.valueOf(i)+" : "+ste.toString());
                i++;
            }
        }



        System.out.println("\nEnd Stack Trace\n");

        // so either create a seperate Controller for interfaces that need to pull dynamic data
        // or just make a bunch of if statement blocks here with the location filename and
        // initialize the data for the interface. I'll go with the second method.
        if(locationString.equals("ManageExistingUserUpdateData.fxml")){
            System.out.println("in manage existing user if block");
            //
            System.out.println(TellerUpdateDataSSN.getText());
            //TellerUpdateDataSSN.setText(DataEntryDriver.stripSSN(placeholder.getSsn()));
            System.out.println("ssn in main variable is: "+Main.currentCustomerID);
        }
        if(locationString.equals("ManageExistingUser.fxml")){
            // set to static ssn for now for testing
            ManageUserSSNField.setText("687-69-8941");
        }

        //

    }


    @FXML
    public void mainScreenTestButton(){
        System.out.println("\n\n");
        System.out.println(mainScreenTest.toString());
        System.out.println(mainScreenTest.getText());
        System.out.println("\n\n");

        String replaceText = "goodbye";

        mainScreenTest.setText("goodbye "+String.valueOf(DataEntryDriver.getRandomInt()));

    }

    @FXML
    public void generalTestButtonAction(){
        System.out.println("\n\nTestbutton action\n");
        //System.out.println(generalTestTextField.toString());
        //System.out.println(generalTestTextField.getText());
        //generalTestTextField.setText("working "+String.valueOf(DataEntryDriver.getRandomInt()));

        //System.out.println(TellerUpdateDataSSN.getText());
        //System.out.println(placeholder.getSsn());
        //TellerUpdateDataSSN.setText(placeholder.getSsn());
        //System.out.println(TellerUpdateDataFirstName.getText());
        //System.out.println(TellerUpdateDataLastName.getText());
        //System.out.println(TellerUpdateDataStreetAddress.getText());

        System.out.println("ManageUserSSNField text: "+ManageUserSSNField.getText());
        //CustomerAccount temp = DataEntryDriver.getCustomerAccountFromCustomerID(ManageUserSSNField.getText());

        //CustomerAccount temp = DataEntryDriver.getCustomerAccountFromCustomerID(ManageUserSSNField.getText());

        System.out.println(" Controller in database ssn: "+ManageUserSSNField.getText());
        try {
            System.out.println(DataEntryDriver.ssnInDatabase(ManageUserSSNField.getText()));
        } catch (Exception e) {
            System.out.println("something up");
        }


        System.out.println("\n\n");
    }



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

        printAllData();//testing

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
        System.out.println("tellerInterfaceAddNewButton");
        Parent root = null;

        printAllData();//testing

        try {
            root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
            Main.primaryStage.setTitle("Add a new user Account");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void tellerInterfaceManageButton(){
        System.out.println("tellerInterfaceManageButton");


        Parent root = null;

        printAllData();//testing

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
        System.out.println("tellerInterfaceManageLookupButton");




        String ssn = ManageUserSSNField.getText();
        String ssnStripped = DataEntryDriver.stripSSN(ssn);

        if(!DataEntryDriver.ssnValid(ssnStripped)){
            // could display a message telling user that ssn is not of valid format
            // but for now I'll just make it valid
            String validSSN = DataEntryDriver.makeSSNValid(ssnStripped);
            ssnStripped = validSSN;

        }

        // NOTE SSN IS STATICALLY SET IN THE INITIALIZE METHOD ON LINE NUMBER 153

        // pass data back to main variable
        Main.currentCustomerID = ssnStripped;

        System.out.println("Main customer ID is: "+Main.currentCustomerID);

        // lookup the account number with DataEntryDriver and set object to static variable
        //Main.customerAccount = DataEntryDriver.getCustomerAccountFromCustomerID(ssnStripped);


        System.out.println("001001001 found user "+ ssnStripped +" launching interface");

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
        System.out.println("start tell int update button method");
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserUpdateData.fxml"));
            Main.primaryStage.setTitle("Update Customer Data");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @FXML
    public void tellerInterfaceUpdateDataPreviousButton(){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));

            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();

            // add code to pull the data for the currentSSN data
            System.out.println("current ssn is: "+Main.currentCustomerID);

            // use placeholder object if needed to display data.

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void tellerInterfaceUpdateDataSaveButton(){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));

            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));


            // add code to pull the data for the currentSSN data
            System.out.println("current ssn is: "+Main.currentCustomerID);
            //System.out.println("current ssn is: "+placeholder.getSsn());
            // use placeholder object if needed to display data.

            // use the main.currentCustomerID ssn to lookup and grab object

            //String ssn = placeholder.getSsn();
            String ssn = Main.currentCustomerID;
            CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(ssn);

            System.out.println(ca.toString());

            // if enters invalid ssn provide warning for now I am setting a static ssn in the driver for testing if
            // the one entered in box does not match anything


            // pull info from fields



            // save the new data to the object in the arraylist



            Main.primaryStage.show();
        } catch (Exception e) {
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


    public void printAllData(){
        System.out.println("ToString:    "+toString());
        System.out.println("ToStringVal: "+toStringValues());
    }

    public String isNullReturn(Object o){
        if(o==null){
            return "null";
        }else{
            return String.valueOf(o);
        }
    }




    public String toStringValues(){
        try {
            return "Controller{" +
                    "tellerLogIn=" + tellerLogIn +
                    ", managerLogIn=" + managerLogIn +
                    ", tellerPendingLogin=" + tellerPendingLogin +
                    ", managerPendingLogin=" + managerPendingLogin +
                    ", fName=" + fName + ", lName=" + lName + ", socialSec='" + socialSec + '\'' +
                    ", streetAddress=" + streetAddress + ", city=" + city +
                    ", zipCode=" + zipCode +
                    ", state=" + state + " MainSSN: "+Main.currentCustomerID+
                    "Main.CustomerAccount={ "+ Main.customerAccount.toString()+ " }" +
                    '}';
        } catch (NullPointerException e) {
            return "null";
        }
    }

    @Override
    public String toString() {
        return "Controller{" +
                "tellerLogIn=" + tellerLogIn +
                ", managerLogIn=" + managerLogIn +
                ", tellerPendingLogin=" + tellerPendingLogin +
                ", managerPendingLogin=" + managerPendingLogin +
                ", fNameTextField=" + fNameTextField +
                ", lNameTextField=" + lNameTextField +
                ", socialSecTextField=" + socialSecTextField +
                ", streetAddressTextField=" + streetAddressTextField +
                ", cityTextField=" + cityTextField +
                ", zipCodeTextField=" + zipCodeTextField +
                ", stateTextField=" + stateTextField +
                ", successfulEntryLabel=" + successfulEntryLabel +
                ", LoginInterUser=" + LoginInterUser +
                ", LoginInterPass=" + LoginInterPass +
                ", LoginInterLoginButton=" + LoginInterLoginButton +
                ", LoginInterExitButton=" + LoginInterExitButton +
                ", enterButton=" + enterButton +
                ", TellerScreen=" + TellerScreen +
                ", BankManagerScreen=" + BankManagerScreen +
                ", TellerInterAddNew=" + TellerInterAddNew +
                ", TellerInterManage=" + TellerInterManage +
                ", TellerInterPrev=" + TellerInterPrev +
                ", ManageUserSSNField=" + ManageUserSSNField +
                ", ManageUserLookupButton=" + ManageUserLookupButton +
                ", ManageUserPrevButton=" + ManageUserPrevButton +
                ", AddNewUserPreviousButton=" + AddNewUserPreviousButton +
                ", BankManagerPrevButton=" + BankManagerPrevButton +
                ", ManageExistingDispDataPrevButton=" + ManageExistingDispDataPrevButton +
                ", ManageExistingTellerUpdateDataButton=" + ManageExistingTellerUpdateDataButton +
                ", ManageExistingTellerViewRecentActivityButton=" + ManageExistingTellerViewRecentActivityButton +
                ", ManageExistingTellerDebitCreditAccountButton=" + ManageExistingTellerDebitCreditAccountButton +
                ", ManageExistingTellerCheckingAccount=" + ManageExistingTellerCheckingAccount +
                ", ManageExistingTellerSavingsAccount=" + ManageExistingTellerSavingsAccount +
                ", ManageExistingTellerFundsTransferAmount=" + ManageExistingTellerFundsTransferAmount +
                ", TellerUpdateDataPreviousButton=" + TellerUpdateDataPreviousButton +
                ", TellerUpdateDataSaveButton=" + TellerUpdateDataSaveButton +
                ", TellerUpdateDataSSN=" + TellerUpdateDataSSN +
                ", TellerUpdateDataFirstName=" + TellerUpdateDataFirstName +
                ", TellerUpdateDataLastName=" + TellerUpdateDataLastName +
                ", TellerUpdateDataStreetAddress=" + TellerUpdateDataStreetAddress +
                ", TellerUpdateDataCity=" + TellerUpdateDataCity +
                ", TellerUpdateDataState=" + TellerUpdateDataState +
                ", TellerUpdateDataZip=" + TellerUpdateDataZip +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", socialSec='" + socialSec + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                '}';
    }




    // END COMMENT


}
