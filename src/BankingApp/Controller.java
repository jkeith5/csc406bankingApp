package BankingApp;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //public static Stage primaryStage = Main.primaryStage;
    //public static Placeholder placeholder = new Placeholder();

    public static boolean tellerLogIn;
    public static boolean managerLogIn;
    public static boolean tellerPendingLogin;
    public static boolean managerPendingLogin;
    public static ToggleGroup accTypeToggleGroup;

    //public static ArrayList<CustomerAccount> customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList();


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
    @FXML Label unsuccessfulEntryLabel;

    @FXML TextField loginInterUser;
    @FXML TextField loginInterPass;
    @FXML Button loginInterLoginButton;
    @FXML Button loginInterExitButton;
    @FXML Button addNewUserInterfaceEnterButton;
    @FXML Button tellerScreen;
    @FXML Button bankManagerScreen;
    @FXML Button tellerInterAddNew;
    @FXML Button tellerInterManage;
    @FXML Button tellerInterPrev;
    @FXML TextField manageUserSSNField;
    @FXML Button manageUserLookupButton;
    @FXML Label lookupInterErrLabel;
    @FXML Button manageUserPrevButton;
    @FXML Button addNewUserPreviousButton;
    @FXML Button bankManagerPrevButton;
    @FXML Button manageExistingDispDataPrevButton;

    @FXML Label manageDispDataSSN;
    @FXML Label manageDispDataFirst;
    @FXML Label manageDispDataLast;
    @FXML Label manageDispDataStreetAddr;
    @FXML Label manageDispDataCity;
    @FXML Label manageDispDataState;
    @FXML Label manageDispDataZip;
    @FXML Label manageDispDataAcctBalance;
    @FXML Label manageDispDataAcctStatus;
    @FXML Label manageDispDataAcctType;



    // Note when I say ManageExistingTeller I mean the ManageExistingUser interface for the Teller account
    @FXML Button manageExistingTellerUpdateDataButton;
    @FXML Button manageExistingTellerViewRecentActivityButton;
    @FXML Button manageExistingTellerDebitCreditAccountButton;
    @FXML RadioButton manageExistingTellerCheckingAccount;
    @FXML RadioButton manageExistingTellerSavingsAccount;
    @FXML TextField manageExistingTellerFundsTransferAmount;

    @FXML Button tellerUpdateDataPreviousButton;
    @FXML Button tellerUpdateDataSaveButton;
    @FXML TextField tellerUpdateDataSSN;
    @FXML TextField tellerUpdateDataFirstName;
    @FXML TextField tellerUpdateDataLastName;
    @FXML TextField tellerUpdateDataStreetAddress;
    @FXML TextField tellerUpdateDataCity;
    @FXML TextField tellerUpdateDataState;
    @FXML TextField tellerUpdateDataZip;









    String fName, lName, socialSec, streetAddress, city, zipCode, state;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("\ninitializing controller");
        System.out.println("\n\n");

        DataEntryDriver.printCustomerDatabase();

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
            System.out.println(tellerUpdateDataSSN.getText());
            //tellerUpdateDataSSN.setText(DataEntryDriver.stripSSN(placeholder.getSsn()));
            System.out.println("ssn in main variable is: "+Main.currentCustomerID);
        }
        if(locationString.equals("ManageExistingUser.fxml")){
            // set to static ssn for now for testing

            if(!DataEntryDriver.ssnInDatabase(DataEntryDriver.stripSSN(manageUserSSNField.getText()))){
                manageUserSSNField.setText("687-69-8941");
                manageUserLookupButton.setDisable(false);
            }else{
                manageUserKeyEvent(null);
            }

        }
        if(locationString.equals("ManageExistingUserDisplayDataTeller.fxml")){
            CustomerAccount ca = Main.customerAccount;
            accTypeToggleGroup = manageExistingTellerCheckingAccount.getToggleGroup();

            if(ca.hasCheckingAccount()){
                manageExistingTellerCheckingAccount.setSelected(true);
            }else{
                manageExistingTellerCheckingAccount.setDisable(true);
            }
            if(!ca.hasSavingsAccount()){
                manageExistingTellerSavingsAccount.setDisable(true);
            }else{
                manageExistingTellerSavingsAccount.setDisable(false);
            }


            tellerManageDispData();
        }

        //

    }


    @FXML
    public void mainScreenTestButton(){
        //
        System.out.println("test");
    }

    @FXML
    public void generalTestButtonAction(){
        System.out.println("\n\nTestbutton action\n");
        //
        System.out.println("\n\n");
    }



    @FXML
    public void addNewAccountEnterButton(ActionEvent event) { // for the Add new user interface
        String fName = fNameTextField.getText();
        String lName = lNameTextField.getText();
        String ssn = socialSecTextField.getText();
        String streetAddress = streetAddressTextField.getText();
        String city = cityTextField.getText();
        String zipCode = zipCodeTextField.getText();
        String state = stateTextField.getText();

        CustomerAccount tempAccount = new CustomerAccount();
        tempAccount.setFirstName(fName);
        tempAccount.setLastName(lName);
        tempAccount.setStreetAddr(streetAddress);
        tempAccount.setCity(city);
        tempAccount.setState(state);
        tempAccount.setZip(zipCode);
        tempAccount.setCustID(DataEntryDriver.stripSSN(ssn));

        // need to tie into main to add data

        boolean sucess = DataEntryDriver.addCustomerAccountToArrayList(tempAccount);

        if(sucess){
            successfulEntryLabel.setText("Data storage for " + fName + " " + lName + " was successful!");
            successfulEntryLabel.visibleProperty().setValue(true);
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" added account: "+
            tempAccount.toString());
        }else{
            unsuccessfulEntryLabel.setText("ERROR CUSTOMER DATA WAS NOT SAVED!!!! PLEASE CONTACT TECH SUPPORT");
        }


    }

    @FXML
    public void addNewUserKeyEvent(){
        System.out.println("event");

        ArrayList<String[]> itemsValid = getNewUserInfoValid();
        if(addNewUserInfoValid(itemsValid)){
            addNewUserInterfaceEnterButton.setDisable(false);
            unsuccessfulEntryLabel.visibleProperty().setValue(false);
        }else{
            addNewUserInterfaceEnterButton.setDisable(true);
            unsuccessfulEntryLabel.visibleProperty().setValue(true);
        }


        //addNewUserInterfaceEnterButton.setDisable(false);
    }

    public void displayDataRadioButtonEvent(){
        // if no account of each type disable radio button for that account
        tellerManageDispData();
    }

    public void tellerManageDispData(){
        CustomerAccount ca = Main.customerAccount;
        System.out.println("display data");
        System.out.println(ca.toString());

        //System.out.println(ca.getSavingsAccount().getCdCloseDate());

        manageDispDataSSN.setText(DataEntryDriver.fixSSN(Main.customerAccount.getCustID()));
        manageDispDataFirst.setText(ca.getFirstName());
        manageDispDataLast.setText(ca.getLastName());
        manageDispDataStreetAddr.setText(ca.getStreetAddr());
        manageDispDataCity.setText(ca.getCity());
        manageDispDataState.setText(ca.getState());
        manageDispDataZip.setText(ca.getZip());
        //manageExistingTellerCheckingAccount.getToggleGroup();

        if(manageExistingTellerCheckingAccount.isSelected()){
            if(ca.hasCheckingAccount()){
                String balanceFormatted = DataEntryDriver.formatAccountBalance(ca.getCheckingAccount().getAccountBalance());
                manageDispDataAcctBalance.setText(balanceFormatted);

                // will probably move this to the DataEntryDriver Class and make it accept the account object and account type
                // checking savings cd or loan and then return proper string object for status.
                if(ca.getCheckingAccount().getAccountBalance()<0.00){
                    manageDispDataAcctStatus.setText("Overdrawn");
                }else{
                    manageDispDataAcctStatus.setText("Current");
                }

                String checkingAcctType = ca.getCheckingAccount().getType();
                manageDispDataAcctType.setText(checkingAcctType);

            }else{
                manageExistingTellerCheckingAccount.setDisable(true);
                manageDispDataAcctBalance.setText("");
                manageDispDataAcctStatus.setText("No account for user");
            }
        }else if(manageExistingTellerSavingsAccount.isSelected()){
            //
            String balanceFormatted = DataEntryDriver.formatAccountBalance(ca.getSavingsAccount().getAccountBalance());
            manageDispDataAcctBalance.setText(balanceFormatted);

            if(ca.getSavingsAccount().getAccountBalance()<0.0){
                manageDispDataAcctStatus.setText("Overdrawn");
            }else{
                manageDispDataAcctStatus.setText("Current");
            }
            manageDispDataAcctType.setText(ca.getSavingsAccount().getType());



        }else{
            manageDispDataAcctBalance.setText("");
            manageDispDataAcctStatus.setText("No account for user");
        }












    }


    // make sure all items are true if not set label warning.
    public boolean addNewUserInfoValid(ArrayList<String[]> itemsArray){
        boolean returnVal = true;

        if(!allFieldsHaveData(getNewUserInfoValid(),1)){
            unsuccessfulEntryLabel.setText("Please fill out all fields");
            return false;
        }

        for(int i=0;i<itemsArray.size();i++){
            System.out.println(Arrays.toString(itemsArray.get(i)));

            if(itemsArray.get(i)[2].equals("false")){// if any other field shows false
                addNewUserInterfaceEnterButton.setDisable(true);
                String[] falseItem = itemsArray.get(i);
                returnVal=false;

                // explain why it's false
                if(falseItem[0].equals("ssn")){
                    System.out.println("The ssn you entered was not valid please enter 9 numbers");
                    unsuccessfulEntryLabel.setText("Please enter a Valid 9 digit SSN with or without the '-'");
                    addNewUserInterfaceEnterButton.setDisable(true);
                }
                if(falseItem[0].equals("zip")){
                    System.out.println("The zip you entered was not valid please enter a 5 digit zip");
                    unsuccessfulEntryLabel.setText("Please enter a 5 digit Zip");
                    addNewUserInterfaceEnterButton.setDisable(true);
                }
                if(falseItem[0].equals("state")){
                    System.out.println("Please enter a 2 character State such as MO or AK");
                    unsuccessfulEntryLabel.setText("Please enter a 2 character State Abbreviation");
                    addNewUserInterfaceEnterButton.setDisable(true);
                }



            }

        }

        return returnVal;
    }

    public boolean allFieldsHaveData(ArrayList<String[]> inputList,int indexToCheck){
        boolean returnVal = true;

        for(int i=0;i<inputList.size();i++){
            if(inputList.get(i)[indexToCheck].length()<1){
                returnVal = false;
            }
        }

        return returnVal;
    }



    public void enterKeyDefaultEvent(KeyEvent e){
        // This allows us to reuse this for key release events on all Nodes to fire the focused node on action event.
        Node focusedNode = Main.primaryStage.getScene().getFocusOwner();
        System.out.println(focusedNode.toString());
        System.out.println(focusedNode.getId().toString());
        if(e.getCode().getName().equals("Enter")){
            if(Main.primaryStage.getScene().getFocusOwner() instanceof Button){
                Button fireButton = (Button) Main.primaryStage.getScene().focusOwnerProperty().get();
                if(!fireButton.isDisabled()){
                    fireButton.fire();
                }
            }else if(Main.primaryStage.getScene().getFocusOwner() instanceof TextField){
                System.out.println(Main.primaryStage.getScene().getFocusOwner().getId());

                TextField fireTextField = (TextField) Main.primaryStage.getScene().focusOwnerProperty().get();
                System.out.println(fireTextField.getId());
                if(fireTextField.getId().equals("manageUserSSNField")){
                    //tellerInterfaceManageLookupButton();

                }
            }
        }
    }


    public void enterKeyLoginDefaultEvent(KeyEvent e){
        // This allows us to reuse this for key release events on all Nodes to fire the focused node on action event.
        Node focusedNode = Main.primaryStage.getScene().getFocusOwner();
        System.out.println("t1"+focusedNode.toString());
        System.out.println("t2"+focusedNode.getId().toString());
        if(e.getCode().getName().equals("Enter")){
            if(Main.primaryStage.getScene().getFocusOwner() instanceof Button){
                System.out.println("loginLogin");

                if(focusedNode.getId().equals("tellerScreen")){
                    tellerLoginButton();
                }
                if(focusedNode.getId().equals("bankManagerScreen")){
                    ManagerLoginButton();
                }

            }
        }
    }

    public void enterKeyLoginExit(KeyEvent e){
        // This allows us to reuse this for key release events on all Nodes to fire the focused node on action event.
        Node focusedNode = Main.primaryStage.getScene().getFocusOwner();
        System.out.println(focusedNode.toString());
        System.out.println(focusedNode.getId().toString());
        if(e.getCode().getName().equals("Enter")){
            if(Main.primaryStage.getScene().getFocusOwner() instanceof Button){
                loginInterfaceExitButton();
                //fireButton.fire();
            }
        }
    }



    @FXML
    public void mainInterfaceTellerButton(){
        System.out.println("hi");
        Parent root = null;
        Parent login = null;

        try {
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
                EmployeeAccount employee = new EmployeeAccount(loginInterUser.getText());
                employee.setType("T");
                Main.loggedInEmployee = employee;
                Main.outEmployee.println(Main.getDateTimeString()+"Teller Account UserName: "+Main.loggedInEmployee.getUserName()+
                " logged into account.");
                closeWindow();
                mainInterfaceTellerButton();
            }
        }

        if(managerPendingLogin){
            managerLogIn = validateLoginCreds("Manager");
            if(managerLogIn){
                managerPendingLogin=false;
                EmployeeAccount employee = new EmployeeAccount(loginInterUser.getText());
                employee.setType("M");
                Main.loggedInEmployee = employee;
                Main.outEmployee.println(Main.getDateTimeString()+"Manager Account UserName: "+Main.loggedInEmployee.getUserName()+
                " logged into account.");
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
        Stage stage = (Stage) loginInterExitButton.getScene().getWindow();
        stage.close();
        goToMainScene();
    }

    public boolean validateLoginCreds(String userType){// userType is either Teller or Manager
        boolean returnVal = false;

        printAllData();//testing

        if(userType == "Teller"){
            if(loginInterUser.getText() == "teller" || loginInterUser.getText().length()>0){
                // here we would validate the credintials but They're always good for now

                if(loginInterPass.getText().length()>0){
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
            if(loginInterUser.getText() == "manager" || loginInterUser.getText().length()>0){
                if(loginInterPass.getText().length()>0){
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
    public void manageUserKeyEvent(KeyEvent e){
        EventType<KeyEvent> keyEventType = e.getEventType();
        String keyCodeName = e.getCode().getName();
        String keyEventTypeName = keyEventType.getName();

        System.out.println(e.getCode().getName());
        String ssn = DataEntryDriver.stripSSN(manageUserSSNField.getText());
        manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

        if(keyCodeName.equals("Enter")){
            System.out.println(keyEventType.toString());
            if(keyEventTypeName.equals("KEY_PRESSED")){
                System.out.println("hello again");
                if(!manageUserLookupButton.isDisabled()){
                    System.out.println("Enter key pressed button not disabled");
                    if(Main.loggedInEmployee.getType().equals("T")){
                        tellerInterfaceManageLookupButton();
                    }
                    if(Main.loggedInEmployee.getType().equals("M")){
                        // PUT THE CODE FOR MANAGER INTERFACE LOOKUP BUTTON HERE
                    }

                }
            }
        }else{ // if enter button was not pressed
            if(keyEventTypeName.equals("KEY_RELEASED")) {
                String s = manageUserSSNField.getText();
                s = s.replaceAll(" ","");
                manageUserSSNField.setText(s);
                manageUserSSNField.positionCaret(manageUserSSNField.getText().length());

                //validateSSNField();


                if(keyCodeName.equals("Backspace")){
                    return;
                }else{
                    System.out.println(keyEventTypeName);
                    validateSSNField();
                    ssn = manageUserSSNField.getText();
                    manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

                    if(!DataEntryDriver.ssnValid(ssn)){// if not 9 digits
                        System.out.println("enter valid number "+ssn);
                        lookupInterErrLabel.setText("Please Enter a Valid 9 digit SSN number");
                    }else{ // if ssn is 9 digits
                        if(!DataEntryDriver.ssnInDatabase(ssn)) {
                            System.out.println("ssn not in database please go to add account");
                            lookupInterErrLabel.setText("The SSN you Entered Is not In the Database.\nGo to Add new Customer or enter" +
                                    " a valid 9 Digit SSN number.");
                        }else{
                            CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(manageUserSSNField.getText());
                            lookupInterErrLabel.setText("Found a Matching account with Last Name: "+ca.getLastName());
                            //Main.outEmployee.println("Employee UserName: "+Main.loggedInEmployee.getUserName()+" Looked Up account: "+ ca.toString());
                            System.out.println("LAST SSN: "+ssn);
                            manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

                        }
                    }
                }




            }else{
                System.out.println("else Key Pressed");
            }
        }







    }

    public void validateSSNField(){ // just checking to include numbers and '-'
        // fixes zip (ssn) field to ONLY allow numbers and limit length to 5
        // [0-9]{3}-?\d\d-?[0-9]{4}
        // example ssn 123-01-0123
        // example ssn 000000000
        // 12354   -4544
        // 123-54-
        // 123-54-1234
        // 123541234


        String ssnFieldText = manageUserSSNField.getText();
        String ssnFieldTextBefore = "";
        System.out.println("SSN Field before fixing: "+ssnFieldText);

        manageUserSSNField.setText(manageUserSSNField.getText().replaceAll("[^\\d-]",""));
        manageUserSSNField.positionCaret(manageUserSSNField.getText().length());


        if(!manageUserSSNField.getText().matches("[0-9]{3}-?\\d\\d-?[0-9]{4}")){ // if not in format 123-45-6789 or 000000000

            String temp = ssnFieldText.replaceAll("[^\\d]", ""); // This removes everything that isn't number
            //manageUserSSNField.setText(temp);
            //manageUserSSNField.positionCaret(manageUserSSNField.getText().length());



            //manageUserSSNField.setText(temp);
            System.out.println("s1: ["+ssnFieldText+"]");
            System.out.println("s2: ["+temp+"]");
            ssnFieldText=manageUserSSNField.getText();

            if(temp.length()==3){
                ssnFieldText=manageUserSSNField.getText();
                manageUserSSNField.setText(temp+"-");
                manageUserSSNField.positionCaret(manageUserSSNField.getText().length());

                System.out.println("s3: ["+ssnFieldText+"]");
                System.out.println("s4: ["+temp+"]");
                System.out.println("BLOCK ONE");
            }else if(temp.length()==5 ){
                ssnFieldText=manageUserSSNField.getText();
                String p1 = temp.substring(0,3);
                String p2 = temp.substring(3,5);
                manageUserSSNField.setText(p1+"-"+p2+"-");
                manageUserSSNField.positionCaret(manageUserSSNField.getText().length());
                System.out.println("s5: ["+ssnFieldText+"]");
                System.out.println("s6: ["+temp+"]");
                System.out.println("BLOCK TWO");
            }else if(temp.length()==9){
                manageUserSSNField.setText(DataEntryDriver.fixSSN(temp));
                manageUserSSNField.positionCaret(manageUserSSNField.getText().length());
                System.out.println("BLOCK THREE");
            }

            //
            //manageUserSSNField.positionCaret(manageUserSSNField.getText().length());

            if(manageUserSSNField.getText().length() >11){
                ssnFieldText = manageUserSSNField.getText().substring(0,11);
                manageUserSSNField.setText(ssnFieldText);
                manageUserSSNField.positionCaret(11);
            }

            //manageUserSSNField.setText(temp);
            //manageUserSSNField.positionCaret(manageUserSSNField.getText().length());

            System.out.println("SSN field after Fix: "+manageUserSSNField.getText());

        }else{
            return;
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

        String ssn = manageUserSSNField.getText();
        String ssnStripped = DataEntryDriver.stripSSN(ssn);

        CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(ssnStripped);
        // pass current customer account back to main
        Main.customerAccount = ca;
        Main.currentCustomerID= ssnStripped;

        Main.outEmployee.println(Main.getDateTimeString()+"Employee UserName: "+Main.loggedInEmployee.getUserName()
                +" Looked Up account: "+ ca.toString());

        // NOTE SSN IS STATICALLY SET IN THE INITIALIZE METHOD ON LINE NUMBER 153

        System.out.println("Main customer ID is: "+Main.currentCustomerID);

        System.out.println("001001001 found user "+ ssnStripped +" launching interface");



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

        // to do record the new customer account in log file and pull data fields here.
        // add code to pull the data for the currentSSN data
        System.out.println("current ssn is: "+Main.currentCustomerID);
        String ssn = Main.currentCustomerID;
        CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(ssn);

        System.out.println(ca.toString());


        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
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
        Stage stage = (Stage) loginInterLoginButton.getScene().getWindow();
        stage.close();

    }


    // this method gets the data from the interface, checks if it's valid, and returns and arraylist with
    // [0]= fieldName, [1]=data, [2]=isValid
    public ArrayList<String[]> getNewUserInfoValid(){
        ArrayList<String[]> validItems = new ArrayList<>();

        String fName = fNameTextField.getText();
        String lName = lNameTextField.getText();
        String ssn = socialSecTextField.getText();
        String streetAddress = streetAddressTextField.getText();
        String city = cityTextField.getText();
        String zipCode = zipCodeTextField.getText();
        String state = stateTextField.getText();


        String[] items = {fName,lName,streetAddress,city};

        for(int i=0;i<items.length;i++){
            if(items[i].length()<1){
                // if nothing was entered in the field save data to display warning
                if(i==0) validItems.add(new String[]{"fName",fName, "false"});
                if(i==1) validItems.add(new String[]{"lName",lName ,"false"});
                if(i==2) validItems.add(new String[]{"streetAddress",streetAddress ,"false"});
                if(i==3) validItems.add(new String[]{"city",city ,"false"});
            }else{
                if(i==0) validItems.add(new String[]{"fName",fName ,"true"});
                if(i==1) validItems.add(new String[]{"lName",lName ,"true"});
                if(i==2) validItems.add(new String[]{"streetAddress",streetAddress ,"true"});
                if(i==3) validItems.add(new String[]{"city",city ,"true"});
            }
        }
        if(DataEntryDriver.ssnValid(ssn)){
            if(DataEntryDriver.ssnInDatabase(ssn)){
                validItems.add(new String[]{"ssnExists",ssn,"false"});
            }else{
                validItems.add(new String[]{"ssn",ssn,"true"});
            }

        }else{
            validItems.add(new String[]{"ssn",ssn,"false"});
        }

        if(DataEntryDriver.zipValid(zipCode)){
            validItems.add(new String[]{"zip",zipCode,"true"});
        }else{
            validItems.add(new String[]{"zip",zipCode,"false"});
        }

        if(state.length()==2){
            validItems.add(new String[]{"state",state,"true"});
        }else{
            validItems.add(new String[]{"state",state,"false"});
        }
        return validItems;
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
                ", loginInterUser=" + loginInterUser +
                ", loginInterPass=" + loginInterPass +
                ", loginInterLoginButton=" + loginInterLoginButton +
                ", loginInterExitButton=" + loginInterExitButton +
                ", addNewUserInterfaceEnterButton=" + addNewUserInterfaceEnterButton +
                ", tellerScreen=" + tellerScreen +
                ", bankManagerScreen=" + bankManagerScreen +
                ", tellerInterAddNew=" + tellerInterAddNew +
                ", tellerInterManage=" + tellerInterManage +
                ", tellerInterPrev=" + tellerInterPrev +
                ", manageUserSSNField=" + manageUserSSNField +
                ", manageUserLookupButton=" + manageUserLookupButton +
                ", manageUserPrevButton=" + manageUserPrevButton +
                ", addNewUserPreviousButton=" + addNewUserPreviousButton +
                ", bankManagerPrevButton=" + bankManagerPrevButton +
                ", manageExistingDispDataPrevButton=" + manageExistingDispDataPrevButton +
                ", manageExistingTellerUpdateDataButton=" + manageExistingTellerUpdateDataButton +
                ", manageExistingTellerViewRecentActivityButton=" + manageExistingTellerViewRecentActivityButton +
                ", manageExistingTellerDebitCreditAccountButton=" + manageExistingTellerDebitCreditAccountButton +
                ", manageExistingTellerCheckingAccount=" + manageExistingTellerCheckingAccount +
                ", manageExistingTellerSavingsAccount=" + manageExistingTellerSavingsAccount +
                ", manageExistingTellerFundsTransferAmount=" + manageExistingTellerFundsTransferAmount +
                ", tellerUpdateDataPreviousButton=" + tellerUpdateDataPreviousButton +
                ", tellerUpdateDataSaveButton=" + tellerUpdateDataSaveButton +
                ", tellerUpdateDataSSN=" + tellerUpdateDataSSN +
                ", tellerUpdateDataFirstName=" + tellerUpdateDataFirstName +
                ", tellerUpdateDataLastName=" + tellerUpdateDataLastName +
                ", tellerUpdateDataStreetAddress=" + tellerUpdateDataStreetAddress +
                ", tellerUpdateDataCity=" + tellerUpdateDataCity +
                ", tellerUpdateDataState=" + tellerUpdateDataState +
                ", tellerUpdateDataZip=" + tellerUpdateDataZip +
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
