package BankingApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;

import java.io.StreamCorruptedException;
import java.time.LocalDate;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Controller implements Initializable{
    //public static Stage primaryStage = Main.primaryStage;
    //public static Placeholder placeholder = new Placeholder();

    public static String activeFXMLFileName="";

    public static boolean tellerLogIn;
    public static boolean managerLogIn;
    public static boolean customerLogIn;
    public static boolean tellerPendingLogin;
    public static boolean managerPendingLogin;
    public static boolean customerPendingLogin;
    public static ToggleGroup accTypeToggleGroup;

    public ArrayList<String> states = DataEntryDriver.createStatesArray();

    @FXML
    public ComboBox<String> stateComboBox; // the regular combobox

    public ComboBoxAutoComplete<String> autoCombo;


    //= new ComboBoxAutoComplete<>(stateComboBox); // the autoComboBox

    @FXML TextField mainScreenTest;
    @FXML TextField tf1;
    @FXML TextField tf2;
    @FXML TextField tf3;
    @FXML TextField generalTestTextField;
    @FXML Button generalTestButton;
    @FXML Button randomSSN;

    @FXML Button testWindowExitButton;
    @FXML
    public ComboBox<String> testComboBox;


    @FXML
    public ComboBoxAutoComplete<String> autoComboTest;

    @FXML Button mainScreenTestButton;


    @FXML
    public ComboBox<String> testCombo2;

    @FXML Label interfaceName;

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
    @FXML Label manageDispDataErrLabel;

    @FXML Button customerInterAtmDepositButton;
    @FXML Button customerInterAtmWithdrawalButton;
    @FXML Button CustomerInterAtmDepositButton;
    @FXML Button customerScreen;
    @FXML Button customerInterCCButton;
    @FXML Button CustomerCcPurchaseButton;
    @FXML Label customerDispDataFirst;
    @FXML Label customerDispDataLast;
    @FXML Label customerDispDataAccountBalance;
    @FXML Label customerInterErrLabel;
    @FXML Label customerDispDataCCNumber;
    @FXML TextField customerInterAtmWithdrawalAmt;
    @FXML TextField customerInterAtmCheckNum;
    @FXML TextField customerInterAtmCheckAmt;
    @FXML TextField ATMInterErrLabel;
    @FXML TextField customerInterCCPurchaseAmt;
    @FXML TextField customerInterCCpurchaseDesc;
    @FXML TextField customerInterCCNum;
    @FXML TextArea customerInterCCHistory;

    @FXML CheckBox debitSavingCD;




    // Note when I say ManageExistingTeller I mean the ManageExistingUser interface for the Teller account
    @FXML Button manageExistingTellerUpdateDataButton;
    @FXML Button manageExistingTellerViewRecentActivityButton;
    @FXML Button manageExistingTellerDebitCreditAccountButton;
    @FXML RadioButton manageExistingTellerCheckingAccount;
    @FXML RadioButton manageExistingTellerSavingsAccount;
    @FXML CheckBox manageExistingTellerTransferFunds;
    @FXML Label manageExistingTellerCheckingLabel;
    @FXML Label manageExistingTellerSavingsLabel;
    @FXML Button manageExistingTellerAddFinanceAccountButton;

    @FXML TextField manageExistingTellerFundsTransferAmount;
    @FXML Button manageExistingDispActivityPrevButton;


    // Update data interface
    @FXML Button tellerUpdateDataPreviousButton;
    @FXML Button tellerUpdateDataSaveButton;
    @FXML TextField updateDataSSN;
    @FXML TextField updateDataFirstName;
    @FXML TextField updateDataLastName;
    @FXML TextField updateDataAddress;
    @FXML TextField updateDataCity;
    @FXML TextField updateDataState;
    @FXML TextField updateDataZip;
    @FXML Button deleteCustomerAccountButton;

    @FXML TextArea displayActivityTextArea;

    @FXML Button addCheckingAccB; // b for button
    @FXML Button addSavingsAccB;
    @FXML Button addLoanAccB;


    // prev buttons here
    @FXML Button addFinancePrevButton;
    @FXML Button addCheckingPrevB;
    @FXML Button addSavingsPrevB;
    @FXML Button addLoanAccPrevB;

    // data for the add financial accounts
    @FXML CheckBox goldCheckBox;
    @FXML CheckBox backupSavingsCheckBox;
    @FXML TextField startingBalance;
    @FXML CheckBox cdCheckBox;

    @FXML TextField savingInterestRate;
    @FXML TextField savingCDTerm;
    //@FXML ChoiceBox<String> loanAccountTypeChoiceBox;
    @FXML ComboBox<String> loanAccountTypeComboBox;
    @FXML Label savingsCDTermLabel;
    @FXML DatePicker savingCdCloseDatePicker;
    @FXML TextField savingCDCloseDate;

    @FXML TextField loanInterestRate;
    @FXML TextField loanTermYears;
    @FXML Label loanTermLabel;

    @FXML Label addCheckingAcctErrLabel;
    @FXML Label addSavingsAcctErrLabel;
    @FXML Label addLoanAcctErrLabel;


    // save buttons for financial accounts
    @FXML Button addCheckingAccSaveB;
    @FXML Button addSavingsAccSaveB;
    @FXML Button addLoanAccSaveB;



    // manage financial accounts
    @FXML Button manageInterManageFinancialAccountsButton;
    @FXML Button manageCheckingAccB;
    @FXML Button manageSavingsAccB;
    @FXML Button manageLoanAccB;
    @FXML Button manageCheckingAccPrevB;
    @FXML Button manageSavingsAccPrevB;
    @FXML Button manageLoanAccPrevB;
    @FXML Button manageCheckingAccSaveB;
    @FXML Button manageSavingsAccSaveB;
    @FXML Button manageLoanAccSaveB;


    @FXML ComboBox<String> manageSavingsAccountsList;
    @FXML ComboBox<String> manageLoanAccountsList;


    @FXML Button manageFinancialAccountsPrevB;

    @FXML Label tempLabel; // use this on any scene when you might need to hide it.
    @FXML TextField tempBalance; // same as above




    @FXML CheckBox deleteAllCheckingAcctCheckBox;
    @FXML CheckBox deleteAllSavingsAcctCheckBox;
    @FXML CheckBox deleteAllLoanAcctCheckBox;
    @FXML Button manageFinancialAccountsDeleteAccountButton;

    @FXML Label earlyWithdrawl;
    @FXML TextField earlyWithdrawlAmount;
    @FXML Label errLabel; // used as a generic error label not tied to any one account

    @FXML
    ImageView help;

    @FXML Button testButton;




    String fName, lName, socialSec, streetAddress, city, zipCode, state;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("\ninitializing controller");
        Main.printToConsoleAndLog("Initializing Controller");
        System.out.println("\n\n");






        //DataEntryDriver.printCustomerDatabase();

        String locationString = DataEntryDriver.getLocationFileName(location);
        activeFXMLFileName = locationString;
        System.out.println(locationString);

        Image helpLogo = null;
        if(DataEntryDriver.runningFromIDE()){
            helpLogo = new Image("file:src/Resources/help.png");
            //System.out.println(helpLogo.getHeight());
        }else{
            helpLogo = new Image("file:"+System.getProperty("user.dir")+"/Resources/help.png");
        }

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

        String loggedInEmployeeType = "null";
        if(Main.loggedInEmployee!=null){
            loggedInEmployeeType = Main.loggedInEmployee.getType();
        }


        System.out.println("\nEnd Stack Trace\n");

        // so either create a seperate Controller for interfaces that need to pull dynamic data
        // or just make a bunch of if statement blocks here with the location filename and
        // initialize the data for the interface. I'll go with the second method.



        if(locationString.equals("AddNewUser.fxml")){

            stateComboBox.setTooltip(new Tooltip());
            stateComboBox.getItems().clear();
            stateComboBox.getItems().addAll(states);
            autoCombo = new ComboBoxAutoComplete<String>(stateComboBox); // creates and manages the combo box

            Main.defaultSceneButton = addNewUserInterfaceEnterButton;

        }

        if(locationString.equals("customerCreditCard.fxml")){
            customerAtmDispData();
        }

        if(locationString.equals("BankManagerInterface.fxml")){
            //
        }

        if(locationString.equals("MainScreen.fxml")){
            DataEntryDriver.printCustomerDatabase();
        }

        if(locationString.equals("ManageExistingUser.fxml")){
            // set to static ssn for now for testing


            if(!DataEntryDriver.ssnInDatabase(DataEntryDriver.stripSSN(manageUserSSNField.getText()))){
                // if whatever is in the box is not in the database, display this code
                manageUserSSNField.setText("423-45-3245");
                manageUserLookupButton.setDisable(false);
                setManageUserErrLabel(manageUserSSNField.getText());
            }else{

                manageUserKeyEvent(null);
            }

        }

        if(locationString.equals("ManageExistingUserDisplayDataTeller.fxml")){
            System.out.println(Main.loggedInEmployee.getType());
            CustomerAccount ca = Main.customerAccount;
            accTypeToggleGroup = manageExistingTellerCheckingAccount.getToggleGroup();
            manageExistingTellerTransferFunds.setSelected(false);
            manageExistingTellerTransferFunds.setDisable(true);

            // disable checking readio button if no checking account if it exists then set as default selected
            if(ca.hasCheckingAccount()){
                manageExistingTellerCheckingAccount.setSelected(true);
            }else{
                manageExistingTellerCheckingAccount.setSelected(false);
                manageExistingTellerCheckingAccount.setDisable(true);
            }

            // STUFF I NEED TO ADD. differentiate between savings cd and simple savings and display the correct type to teller
            // teller can credit any account but savings CD
            // same as above but with savings account
            if(!ca.hasSavingsAccount()){// if no savings account
                manageExistingTellerSavingsAccount.setSelected(false);
                manageExistingTellerSavingsAccount.setDisable(true);
            }else{// if they do have a savings account
                manageExistingTellerSavingsAccount.setDisable(false);
                if(!ca.hasCheckingAccount()){// if they have a savings but not a checking account
                    manageExistingTellerSavingsAccount.setSelected(true);
                }
            }


            if(ca.hasSavingsAccount() && ca.hasCheckingAccount()){ // if they have a savings and checking account
                manageExistingTellerTransferFunds.setDisable(false);
                manageExistingTellerCheckingLabel.setText("");
                manageExistingTellerSavingsLabel.setText("");
            }else{// else they have NO savings or checking account disable all features related

                if(!ca.hasSavingsAccount() && !ca.hasCheckingAccount()){
                    manageExistingTellerFundsTransferAmount.setDisable(true);
                    manageExistingTellerDebitCreditAccountButton.setDisable(true);
                    manageExistingTellerViewRecentActivityButton.setDisable(true);
                    manageExistingTellerCheckingLabel.setText("");
                    manageExistingTellerSavingsLabel.setText("");
                    manageDispDataAcctType.setText("");
                    manageDispDataAcctStatus.setText("");
                }


            }

            manageDispDataErrLabel.setText("");
            manageExistingTellerDebitCreditAccountButton.setDisable(true);

            // sets a changed listener to this object
            DataEntryDriver.validateBalanceAmountField(manageExistingTellerFundsTransferAmount,true);

            // since the manager can see everything anyway just disable the stuff for the teller
            if(Main.loggedInEmployee.getType().equals("T")){
                deleteCustomerAccountButton.setDisable(true);
            }




            tellerManageDispData();

        }

        if(locationString.equals("ManageExistingUserUpdateData.fxml")){

            stateComboBox.setTooltip(new Tooltip());
            stateComboBox.getItems().clear();
            stateComboBox.getItems().addAll(states);
            autoCombo = new ComboBoxAutoComplete<String>(stateComboBox); // creates and manages the combo box

            Main.defaultSceneButton = tellerUpdateDataSaveButton;

            populateUpdateDataScreen();
        }

        if(locationString.equals("ManagerLogin.fxml")){
            //
        }

        if(locationString.equals("TellerInterface.fxml")){
            System.out.println(locationString);
            System.out.println(Main.loggedInEmployee.getType());

        }

        if(locationString.equals("TellerLogin.fxml")){
            //
        }


        if(locationString.equals("TestWindow.fxml")){

            testComboBox.setTooltip(new Tooltip());
            testComboBox.getItems().clear();
            testComboBox.getItems().addAll(states);
            autoComboTest = new ComboBoxAutoComplete<String>(testComboBox); // creates and manages the combo box


            //   ^-?\d{0,7}([\.]\d{0,4})?
            DataEntryDriver.validateBalanceAmountField(tf1,true); // allow negative
            DataEntryDriver.validateZipField(tf2);
            System.out.println("test ");





        }

        if(locationString.equals("CustomerInterface.fxml")){
            DataEntryDriver.validateBalanceAmountField(customerInterAtmWithdrawalAmt,false);
            DataEntryDriver.validateBalanceAmountField(customerInterAtmCheckAmt,false);
            customerDispData();
        }

        if(locationString.equals("ManageExistingUserDispActivity.fxml")){
            viewRecentActivityDispData();
        }

        if(locationString.equals("ManageExistingUserAddFinanceAcc.fxml")){
            dispDataUpper();
            CustomerAccount customerAccount = Main.customerAccount;
            if(customerAccount.hasCheckingAccount()){// if they have a checking account then disable
                addCheckingAccB.setDisable(true);
            }else{
                addCheckingAccB.setDisable(false);
            }



        }

        if(locationString.equals("AddChecking.fxml")){
            // set the data validation on the fields.
            dispDataUpper();
            addCheckingAccSaveB.setDisable(true);
            addCheckingAcctErrLabel.setText("");
            DataEntryDriver.validateBalanceAmountField(startingBalance,false);

        }

        if(locationString.equals("AddLoan.fxml")){
            // Enter code to run on initialization of the FXML Scene
            dispDataUpper();
            addLoanAccSaveB.setDisable(true);
            CustomerAccount customerAccount= Main.customerAccount;
            ArrayList<String> loanAccountTypes = new ArrayList<String>();
            addLoanAcctErrLabel.setText("");

            DataEntryDriver.validateBalanceAmountField(startingBalance,false);
            DataEntryDriver.validateInterestField(loanInterestRate,true,true);
            DataEntryDriver.validateInterestField(loanTermYears,false,false);



            boolean hasCCL = customerAccount.hasCreditCardAcct();


            loanAccountTypes.add("Long Term Loan");
            loanAccountTypes.add("Short Term Loan");

            if(!hasCCL){ // they do not already have a CCL loan
                loanAccountTypes.add("Credit Card Loan"); // can only have one
            }else{ // they already have a ccl
                //
            }

            loanAccountTypeComboBox.getItems().clear();
            loanAccountTypeComboBox.getItems().addAll(loanAccountTypes);

            // add a change listener to fire the event method

            loanAccountTypeComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override public void changed(ObservableValue ov, String t, String t1) {
                    loanAccountTypeEvent();
                }
            });


            // go ahead and hide the term box unless LTL or STL is selected
            loanTermLabel.setVisible(false);
            loanTermYears.setVisible(false);





        }

        if(locationString.equals("AddSavings.fxml")){
            // Enter code to run on initialization of the FXML Scene
            dispDataUpper();
            addSavingsAccSaveB.setDisable(true);
            addSavingsAcctErrLabel.setText("");
            CustomerAccount customerAccount = Main.customerAccount;

            // data validation
            DataEntryDriver.validateBalanceAmountField(startingBalance,false);
            DataEntryDriver.validateInterestField(savingInterestRate,true,true);
            DataEntryDriver.validateInterestField(savingCDTerm,false,false);

            if(customerAccount.hasSavingsAccount()){ // if they have a savings account see if it's a simple saving
                ArrayList<SavingsAccount> savingsAccounts = customerAccount.getSavingsAccounts();
                boolean hasSimple = false;
                for(SavingsAccount savingsAccount:savingsAccounts){
                    if(!savingsAccount.isCdAccount()){// they have a simple saving already
                        hasSimple=true;
                    }
                }
                // so check the cd account box and lock it so they can only add a cd type
                if(hasSimple){
                    cdCheckBox.setSelected(true);
                    cdCheckBox.setDisable(true);
                    addSavingsAcctErrLabel.setText("User already has a simple savings account");
                    //DataEntryDriver.validateInterestField(savingCDTerm,false,false);
                }

            }else{// have no savings account
                // set these to false until they check the cd box
                savingCDTerm.setVisible(false);
                savingsCDTermLabel.setVisible(false);
            }


        }

        if(locationString.equals("ManageExistingUserManageFinanceAcc.fxml")){
            dispDataUpper(); // displays the top frame data
            // validate interface. Can't manage a loan account you don't have
            CustomerAccount ca = Main.customerAccount;
            manageCheckingAccB.setDisable(!ca.hasCheckingAccount());
            manageSavingsAccB.setDisable(!ca.hasSavingsAccount());
            manageLoanAccB.setDisable(!ca.hasLoanAccount());
            deleteAllCheckingAcctCheckBox.setDisable(!ca.hasCheckingAccount());
            deleteAllSavingsAcctCheckBox.setDisable(!ca.hasSavingsAccount());
            deleteAllLoanAcctCheckBox.setDisable(!ca.hasLoanAccount());
            manageFinancialAccountsDeleteAccountButton.setDisable(true);

            if(loggedInEmployeeType.equals("T")){
                tempLabel.setText("");
                deleteAllCheckingAcctCheckBox.setVisible(false);
                deleteAllSavingsAcctCheckBox.setVisible(false);
                deleteAllLoanAcctCheckBox.setVisible(false);
                manageFinancialAccountsDeleteAccountButton.setVisible(false);


                manageCheckingAccB.setDisable(true);
                manageSavingsAccB.setDisable(true);
            }




        }
        if(locationString.equals("ManageCheckingAcct.fxml")){
            dispDataUpper();
            DataEntryDriver.validateBalanceAmountField(startingBalance,false);
            displayDataManageFinancialAccounts();
        }

        if(locationString.equals("ManageLoanAcct.fxml")){
            dispDataUpper();
            CustomerAccount ca = Main.customerAccount;
            manageLoanAccSaveB.setDisable(true);
            loanTermYears.setVisible(false);
            loanTermLabel.setVisible(false);
            help.setImage(helpLogo);

            if(ca.hasLoanAccount()){
                ArrayList<LoanAccount> loanAccounts = ca.getLoanAccounts();
                manageLoanAccountsList.getItems().clear(); // clear all items
                ArrayList<String> loanAccountsFixedID = new ArrayList<>();

                for(LoanAccount loanAccount:loanAccounts){ // get and add the FixedID to the array
                    loanAccountsFixedID.add(loanAccount.getLoanAccountIDFixed());
                }

                ArrayList<String> loanAccountTypes = new ArrayList<>(); // make array for the type combo box

                loanAccountTypes.add("Long Term Loan");
                loanAccountTypes.add("Short Term Loan");
                loanAccountTypes.add("Credit Card Loan"); // can only have one
                loanAccountTypeComboBox.getItems().clear();
                loanAccountTypeComboBox.getItems().addAll(loanAccountTypes); // add items to the type box

                manageLoanAccountsList.getItems().addAll(loanAccountsFixedID); // add items to the loan accounts box

                manageLoanAccountsList.valueProperty().addListener(new ChangeListener<String>() {
                    @Override public void changed(ObservableValue ov, String t, String t1) {
                        manageLoanAccountTypeEvent();
                    }
                });

                loanAccountTypeComboBox.valueProperty().addListener(new ChangeListener<String>() {
                    @Override public void changed(ObservableValue ov, String t, String t1) {
                        loanAccountTypeEvent();
                    }
                });

                DataEntryDriver.validateBalanceAmountField(startingBalance,false);
                DataEntryDriver.validateInterestField(loanInterestRate,true,true);
                DataEntryDriver.validateBalanceAmountField(loanTermYears,false);



            }// end has loan account


        }
        if(locationString.equals("ManageSavingsAcct.fxml")){
            dispDataUpper();
            help.setImage(helpLogo);
            CustomerAccount ca = Main.customerAccount;
            manageSavingsAccSaveB.setDisable(true);
            savingCdCloseDatePicker.setVisible(false);
            tempBalance.setVisible(false);
            tempLabel.setVisible(false);

            savingsCDTermLabel.setVisible(false);

            if(ca.hasSavingsAccount()){ // ALWAYS DO THIS!!!!!!!!!!!!!!! OR GET NULL POINTER AND SPEND HOURS LOOKING FOR THE CAUSE
                //
                ArrayList<SavingsAccount> savingsAccounts = ca.getSavingsAccounts();

                manageSavingsAccountsList.getItems().clear(); // clear the box or it will persist
                ArrayList<String> savingsAccountFixedID = new ArrayList<>(); // string arraylist for the id

                for(SavingsAccount sa:savingsAccounts){ // fill the string array
                    savingsAccountFixedID.add(sa.getSavingsAcctIDFixed());
                }

                manageSavingsAccountsList.getItems().addAll(savingsAccountFixedID);

                // add a change listener to fire the event method

                manageSavingsAccountsList.valueProperty().addListener(new ChangeListener<String>() {
                    @Override public void changed(ObservableValue ov, String t, String t1) {
                        manageSavingsAccountTypeEvent();
                    }
                });

                DataEntryDriver.validateBalanceAmountField(startingBalance,false);
                DataEntryDriver.validateInterestField(savingInterestRate,true,true);
                DataEntryDriver.validateBalanceAmountField(tempBalance,false);

                savingCdCloseDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
                    @Override
                    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                        if(newValue!=null){
                            LocalDate today = DataEntryDriver.getCurrentDateObject();
                            if(newValue.isBefore(today)){
                                savingCdCloseDatePicker.setValue(today);
                            }
                        }

                        //System.out.println("old: "+oldValue.toString());
                        //System.out.println("new: "+newValue.toString());
                    }
                });




            }


        }
        if(locationString.equals("AddFXMLFileNameHere.fxml")){
            // Enter code to run on initialization of the FXML Scene
            // .... like the methods to populate data
            // or the ones to set listener events to a TextField
        }
        if(locationString.equals("AddFXMLFileNameHere.fxml")){
            // Enter code to run on initialization of the FXML Scene
            // .... like the methods to populate data
        }
        if(locationString.equals("AddFXMLFileNameHere.fxml")){
            // Enter code to run on initialization of the FXML Scene
            // .... like the methods to populate data
        }
        if(locationString.equals("AddFXMLFileNameHere.fxml")){
            // Enter code to run on initialization of the FXML Scene
            // .... like the methods to populate data
        }
        if(locationString.equals("AddFXMLFileNameHere.fxml")){
            // Enter code to run on initialization of the FXML Scene
            // .... like the methods to populate data
        }
        if(locationString.equals("AddFXMLFileNameHere.fxml")){
            // Enter code to run on initialization of the FXML Scene
            // .... like the methods to populate data
        }



    }



    // KEY EVENTS BLOCK
    @FXML
    public void addNewAccountEnterButton(ActionEvent event) { // for the Add new user interface
        String fName = fNameTextField.getText();
        String lName = lNameTextField.getText();
        String ssn = socialSecTextField.getText();
        ssn = DataEntryDriver.fixSSN(ssn);

        String streetAddress = streetAddressTextField.getText();
        String city = cityTextField.getText();
        String zipCode = zipCodeTextField.getText();
        //String state = stateTextField.getText();
        String state = stateComboBox.getValue();

        CustomerAccount tempAccount = new CustomerAccount();
        tempAccount.setFirstName(fName);
        tempAccount.setLastName(lName);
        tempAccount.setStreetAddr(streetAddress);
        tempAccount.setCity(city);
        tempAccount.setState(state);
        tempAccount.setZip(zipCode);
        tempAccount.setCustID(DataEntryDriver.stripSSN(ssn));
        tempAccount.setFinancialAccountIDAuto();
        tempAccount.setAtmCardNumber(Main.generateAtmCardNumber());
        tempAccount.setPinAuto();


        // need to tie into main to add data

        boolean success = DataEntryDriver.addCustomerAccountToArrayList(tempAccount);

        if(success){
            successfulEntryLabel.setText("Data storage for " + fName + " " + lName + " was successful!");
            successfulEntryLabel.visibleProperty().setValue(true);
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" added account: "+
            tempAccount.toString());

            goToTellerScene();

        }else{
            unsuccessfulEntryLabel.setText("ERROR CUSTOMER DATA WAS NOT SAVED!!!! PLEASE CONTACT TECH SUPPORT");
        }


    }

    @FXML
    public void addNewUserKeyEvent(){
        System.out.println("event");

        DataEntryDriver.validateZipField(zipCodeTextField);
        ArrayList<String[]> itemsValid = getNewUserInfoValidArrayList();
        if(addNewUserInfoValid(itemsValid)){
            addNewUserInterfaceEnterButton.setDisable(false);
            unsuccessfulEntryLabel.visibleProperty().setValue(false);
        }else{
            addNewUserInterfaceEnterButton.setDisable(true);

            unsuccessfulEntryLabel.visibleProperty().setValue(true);
        }
    }

    @FXML
    public void updateUserKeyEvent(){
        System.out.println("update user key event");
        DataEntryDriver.validateZipField(updateDataZip);

        ArrayList<String[]> itemsValid = getNewUserInfoValidArrayList();

//        for(String[] el:itemsValid){
//            System.out.println(Arrays.toString(el));
//        }


        if(addNewUserInfoValid(itemsValid)){
            //System.out.println("valid");
            Main.defaultSceneButton.setDisable(false);
            //tellerUpdateDataSaveButton.setDisable(false);
        }else{
            //System.out.println("notvalid");
            Main.defaultSceneButton.setDisable(true);
            //tellerUpdateDataSaveButton.setDisable(true);
        }

    }


    // handles the event for any of the checkboxes and disables run button if not selected
    public void manageFinanceAccountsCheckBoxEvent(){
        ArrayList<CheckBox> checkBoxesArray = new ArrayList<>();
        checkBoxesArray.add(deleteAllCheckingAcctCheckBox);
        checkBoxesArray.add(deleteAllSavingsAcctCheckBox);
        checkBoxesArray.add(deleteAllLoanAcctCheckBox);

        boolean anyBoxSelected = DataEntryDriver.isAnyCheckBoxSelected(checkBoxesArray);

        manageFinancialAccountsDeleteAccountButton.setDisable(!anyBoxSelected);


    }


    public void isCdCheckBoxEvent(){
        //
        if(activeFXMLFileName.equals("AddSavings.fxml")){
            if(!cdCheckBox.isDisabled()){ // if box was not disabled in initial method // disabled = false
                if(cdCheckBox.isSelected()){ // show the CD term label and field
                    savingsCDTermLabel.setVisible(true);
                    savingCDTerm.setVisible(true);

                }else{// the cd box is not selected so hide those items
                    savingsCDTermLabel.setVisible(false);
                    savingCDTerm.setVisible(false);
                }
            }

        }else{
            if(!cdCheckBox.isDisabled()){ // if box was not disabled in initial method // disabled = false
                if(cdCheckBox.isSelected()){ // show the CD close date label and field
                    savingsCDTermLabel.setVisible(true);
                    savingCdCloseDatePicker.setVisible(true);

                }else{// the cd box is not selected so hide those items
                    savingsCDTermLabel.setVisible(false);
                    savingCdCloseDatePicker.setVisible(false);
                }
            }
        }

        addFinanceAccountEvent();
    }

    public void loanAccountTypeEvent(){
        System.out.println("loan account type key event");
        String selectedItem = loanAccountTypeComboBox.getValue();
        if(selectedItem.equals("Short Term Loan")){
            loanTermLabel.setVisible(true);
            loanTermYears.setVisible(true);
        }
        if(selectedItem.equals("Long Term Loan")){
            loanTermLabel.setVisible(true);
            loanTermYears.setVisible(true);
        }
        if(selectedItem.equals("Credit Card Loan")){
            loanTermLabel.setVisible(false);
            loanTermYears.setVisible(false);
        }

    }


    // used to change the display of the savings account in the manage interface
    public void manageSavingsAccountTypeEvent(){
        System.out.println("manage savings account type event");
        CustomerAccount customerAccount = Main.customerAccount;
        String selectedFixedId = manageSavingsAccountsList.getSelectionModel().getSelectedItem();
        SavingsAccount selectedSavingAccount = customerAccount.getSavingsAccountByFixedID(selectedFixedId);
        boolean isCd = selectedSavingAccount.isCdAccount();
        double balance = selectedSavingAccount.getAccountBalance();
        double interest = selectedSavingAccount.getInterestRate()*100.00;
        String cdCloseDate = selectedSavingAccount.getCdCloseDate();
        cdCheckBox.setSelected(isCd);

        LocalDate cdCloseDateObj = DataEntryDriver.getDateObjectFromString(cdCloseDate);
        startingBalance.setDisable(false);
        startingBalance.setOpacity(1.0);
        savingInterestRate.setDisable(false);
        savingInterestRate.setOpacity(1.0);


        startingBalance.setText(DataEntryDriver.getStringFromDouble(balance));
        System.out.println("test: "+DataEntryDriver.getStringFromDouble(interest));
        //savingInterestRate.setText(DataEntryDriver.getStringFromDouble(interest));

        //savingInterestRate.setText(DataEntryDriver.getStringFromInt(DataEntryDriver.getRandomInt()));
        //savingInterestRate.setText(DataEntryDriver.getStringFromDouble(5.8));
        savingInterestRate.setText(DataEntryDriver.getStringFromDouble(interest));

        if(isCd){ // if the selected account is a CD account
            savingsCDTermLabel.setVisible(true);
            savingCdCloseDatePicker.setDisable(false);
            savingCdCloseDatePicker.setVisible(true);
            savingCdCloseDatePicker.setOpacity(1.0);

            tempLabel.setVisible(true);
            tempBalance.setVisible(true); // withdrawal amount
            savingCdCloseDatePicker.setValue(cdCloseDateObj);
        }else{ // not a cd account
            savingCdCloseDatePicker.setVisible(false);
            savingsCDTermLabel.setVisible(false);
            tempLabel.setVisible(false);
            tempBalance.setText("");
            tempBalance.setVisible(false);
        }
        int selIndex = manageSavingsAccountsList.getSelectionModel().getSelectedIndex();
        System.out.println("sel index: "+selIndex);
        if(selIndex<0){
            manageSavingsAccSaveB.setDisable(true);
        }else{
            manageSavingsAccSaveB.setDisable(false);
        }




    }

    public void manageSavingsAccountCDWithdrawalKeyEvent(KeyEvent e){
        System.out.println("manage savings key event");

        String keyCodeName = e.getCode().getName();
        double opacityDisabled = 0.60;
        double opacityEnabled = 1.00;
        CustomerAccount customerAccount = Main.customerAccount;
        String selectedFixedId = manageSavingsAccountsList.getSelectionModel().getSelectedItem();
        SavingsAccount selectedSavingAccount = customerAccount.getSavingsAccountByFixedID(selectedFixedId);

        if(keyCodeName.equals("Tab") || keyCodeName.equals("Left") || keyCodeName.equals("Down")||keyCodeName.equals("Right")||keyCodeName.equals("Up")){
            return;
        }

        if(tempBalance.getText().length()!=0){ // if something is typed in the field
            Double withdrawalAmount = DataEntryDriver.getDoubleFromTextField(tempBalance);
            Double currentSavingsCDBalance = selectedSavingAccount.getCurrentCDValue();
            if(!startingBalance.isDisabled()){ // something in withdrawal field and starting bal was not disabled
                manageSavingsAccSaveB.setText("Withdrawal");
                manageSavingsAccountTypeEvent();
                startingBalance.setDisable(true);
                startingBalance.setOpacity(opacityDisabled);
                savingInterestRate.setDisable(true);
                savingInterestRate.setOpacity(opacityDisabled);
                savingCdCloseDatePicker.setDisable(true);
                savingCdCloseDatePicker.setOpacity(opacityDisabled);
            }
            double feeForWithdrawalNow = selectedSavingAccount.getFeeForWithdrawalOfCDonThisDay();
            double savingsCDBalanceAfterFee = currentSavingsCDBalance - feeForWithdrawalNow;
            double balanceAfterFeeAndWithdrawal = savingsCDBalanceAfterFee - withdrawalAmount;

            System.out.println("\n");
            System.out.println(selectedSavingAccount.toStringPrettyPrint());
            System.out.println("savingsOriginalBalance: "+selectedSavingAccount.getAccountBalance());
            System.out.println("cd years owned: "+selectedSavingAccount.getCurrentYearsOfCD());
            System.out.println("Saving Curr Value: "+selectedSavingAccount.getCurrentCDValue());
            System.out.println("savings open date: "+selectedSavingAccount.getDateOpened());
            System.out.println("Fee for wd: "+feeForWithdrawalNow);
            System.out.println("saving bal after fee: "+savingsCDBalanceAfterFee);

            if(balanceAfterFeeAndWithdrawal>=0.0){ // enough in cd to make withdrawal
                String message = String.format("This would Bring your CD account to $%.2f including a fee of $%.2f.",
                        balanceAfterFeeAndWithdrawal,feeForWithdrawalNow);
                System.out.println(message);
                errLabel.setText(message);
                manageSavingsAccSaveB.setDisable(false);
            }else{ // not enough for withdrawal
                errLabel.setText("Not Enough Money In CD account to make withdrawal");
                manageSavingsAccSaveB.setDisable(true);
            }


        }else{ // nothing is typed in the field
            manageSavingsAccSaveB.setDisable(false);
            manageSavingsAccSaveB.setText("Save");
            startingBalance.setDisable(false);
            startingBalance.setOpacity(opacityEnabled);
            savingInterestRate.setDisable(false);
            savingInterestRate.setOpacity(opacityEnabled);

            savingCdCloseDatePicker.setDisable(false);
            savingCdCloseDatePicker.setOpacity(opacityEnabled);
        }


    }


    public void manageCheckingAccountEvent(){
        if(startingBalance.getText().length()==0){
            manageCheckingAccSaveB.setDisable(true);
        }else{
            manageCheckingAccSaveB.setDisable(false);
        }
    }

    public void manageLoanAccountTypeEvent(){
        CustomerAccount customerAccount = Main.customerAccount;
        String selectedFixedId = manageLoanAccountsList.getSelectionModel().getSelectedItem();
        LoanAccount selectedLoanAccount = customerAccount.getLoanAccountByFixedID(selectedFixedId);

        String loanAccountType = selectedLoanAccount.getLoanAccountType(); // short name

        double balance = selectedLoanAccount.getCurrentBalance();
        double interest = selectedLoanAccount.getInterestRate()*100.00;

        String loanAccountTypeFullName = DataEntryDriver.getLoanFullTypeNameFromAbb(loanAccountType);

        loanAccountTypeComboBox.getSelectionModel().select(loanAccountTypeFullName);




        double monthlyPayment = selectedLoanAccount.getAmountDue();
        System.out.println(monthlyPayment);
        startingBalance.setText(DataEntryDriver.getStringFromDouble(balance));
        loanInterestRate.setText(DataEntryDriver.getStringFromDouble(interest));

        if(loanAccountType.equals("LTL") || loanAccountType.equals("STL")){
            loanTermLabel.setVisible(true);
            loanTermYears.setVisible(true);
            loanTermYears.setText("");
            String paymentString = DataEntryDriver.getStringFromDouble(monthlyPayment);
            loanTermYears.setText(paymentString);
            System.out.println("MONTHLY PAYMENT: "+monthlyPayment);
            System.out.println("MONTHLY PAYMENT DED: "+DataEntryDriver.getStringFromDouble(monthlyPayment));
        }else{ // else its a credit card and hide the value
            loanTermYears.setVisible(false);
            loanTermLabel.setVisible(false);
        }


        int selIndex = manageLoanAccountsList.getSelectionModel().getSelectedIndex();
        if(selIndex>=0){
            manageLoanAccSaveB.setDisable(false);
        }else{
            manageLoanAccSaveB.setDisable(true);
        }
    }


    public void displayDataManageFinancialAccounts(){
        String location = activeFXMLFileName;
        CustomerAccount ca = Main.customerAccount;

        if(location.equals("ManageCheckingAcct.fxml")){
            CheckingAccount checkingAccount = ca.getCheckingAccount();
            boolean isGold =checkingAccount.isGoldAccount();
            boolean backupSaving = checkingAccount.isBackupSavingsEnabled();
            double balance = checkingAccount.getAccountBalance();

            goldCheckBox.setSelected(isGold);
            backupSavingsCheckBox.setSelected(backupSaving);
            startingBalance.setText(DataEntryDriver.getStringFromDouble(balance));


        }



    }

    public void displayDataRadioButtonEvent(){
        // if no account of each type disable radio button for that account
        tellerManageDispData();

        //System.out.println(accTypeToggleGroup.toString());

        if(manageExistingTellerTransferFunds.isSelected()){ // if box is selected

            if(manageExistingTellerCheckingAccount.isSelected()){
                manageExistingTellerCheckingLabel.setText("From: ");
                manageExistingTellerSavingsLabel.setText("To: ");
                transferFundsKeyEvent();
            }else{
                manageExistingTellerSavingsLabel.setText("From: ");
                manageExistingTellerCheckingLabel.setText("To: ");
                transferFundsKeyEvent();
            }



        }else{// if box was not selected
            manageExistingTellerCheckingLabel.setText("");
            manageExistingTellerSavingsLabel.setText("");
            transferFundsKeyEvent();
        }

    }


    public void transferFundsCheckBoxEvent(){
        System.out.println("transfer funds block event");

        if(!manageExistingTellerTransferFunds.isDisabled()){
            if(manageExistingTellerTransferFunds.isSelected()){
                System.out.println("selected");
                manageExistingTellerFundsTransferAmount.setText("");
                displayDataRadioButtonEvent();
            }else{
                System.out.println("not selected");
                manageExistingTellerFundsTransferAmount.setText("");
                manageExistingTellerCheckingLabel.setText("");
                manageExistingTellerSavingsLabel.setText("");
                transferFundsKeyEvent();
            }
        }

    }


    public void transferFundsKeyEvent(){
        // THINGS THIS NEEDS TO DO
        // READ IF WE ARE CONDUCTING AN ACTION ON A CHECKING OR SAVINGS ACCOUNT
        // CHECK AND DISABLE TRANSFER BUTTON IF THERE ISN'T ENOUGH MONEY TO COMPLETE THE DEBIT
        // ^^^^^^^ UNLESS THERE IS A BACKUP SAVING ACCOUNT ENABLED. IF SO TAKE REMAINDER FROM SAVING ACCOUNT
        // CREATE A TRANSACTION OBJECT FOR EACH ACTION AND RECORD IN ACCOUNT
        // GENERATE FINAL ALERT WINDOW TO CONFIRM TRANSACTION

        System.out.println("\n\n");
        manageExistingTellerDebitCreditAccountButton.setDisable(true);

        if(manageExistingTellerTransferFunds.isSelected()){
            String transferString = manageExistingTellerFundsTransferAmount.getText();
            transferString = transferString.replaceAll("-",""); // can't just set a new listener in an easy way so just hack the - sign off
            manageExistingTellerFundsTransferAmount.setText(transferString);
            manageExistingTellerFundsTransferAmount.positionCaret(transferString.length());
        }


        if(manageExistingTellerFundsTransferAmount.getText().length()<1){
            manageExistingTellerDebitCreditAccountButton.setDisable(true);
        }else{
            // hand it the label as well.
            boolean isValid = FinanceDriver.isTransferAmtValid(manageExistingTellerFundsTransferAmount,manageExistingTellerTransferFunds,
                    manageExistingTellerCheckingAccount,manageExistingTellerSavingsAccount,manageDispDataErrLabel);

            // need to know transfer amt, if transfer is checked, to and from
            // if not checked then debit / credit to account

            // set to value from method in FinanceDriver
            manageExistingTellerDebitCreditAccountButton.setDisable(!isValid); // !isValid because is valid returns True for
            // meaning it is valid and we want to set disable to false if isValid is true




            System.out.println("is valid: "+isValid);



        }
    }

    public void completeTransaction(){ // ONLY USE THIS METHOD FOR A TELLER OR MANAGER ACCOUNT because I can refresh those
        System.out.println("Complete Transaction");

        FinanceDriver.completeTransaction(manageExistingTellerFundsTransferAmount,manageExistingTellerTransferFunds,manageExistingTellerCheckingAccount,
                manageExistingTellerSavingsAccount,manageDispDataErrLabel);
        manageExistingTellerFundsTransferAmount.setText("");
        tellerManageDispData();
        transferFundsKeyEvent();

//        if(Main.loggedInEmployee.getType().equalsIgnoreCase("T")){
//
//        }
//
//        if(Main.loggedInEmployee.getType().equalsIgnoreCase("M")){
//            // DISPLAY DATA METHOD FOR MANAGER DISPLAY
//        }
        //
    }



    public void enterKeyDefaultEvent(KeyEvent e){
        // This allows us to reuse this for key release events on all Nodes to fire the focused node on action event.
        if(Main.activeStage==null){
            return;
        }else{ // if Main.activeStage is not null
            // add to use currently active stage
            Stage activeStage = Main.activeStage;

            if(e.getCode().getName().equals("Enter")){
                if(activeStage.getScene().getFocusOwner() instanceof Button){
                    Button fireButton = (Button) activeStage.getScene().focusOwnerProperty().get();
                    if(!fireButton.isDisabled()){
                        fireButton.fire();
                    }
                }else if(activeStage.getScene().getFocusOwner() instanceof TextField){
                    //System.out.println(activeStage.getScene().getFocusOwner().getId());

                    TextField fireTextField = (TextField) activeStage.getScene().focusOwnerProperty().get();
                    //System.out.println(fireTextField.getId());
                    if(fireTextField.getId().equals("manageUserSSNField")){
                        //tellerInterfaceManageLookupButton();

                    }
                }
            }


        }



    }




    public void addFinanceAccountEvent(){
        // going to use if statements for all the validation on the add accounts
        // mainly to disable the add button unless it's valid.
        //sort and figure out what scene is active and run validation on that
        System.out.println(activeFXMLFileName);

        if(activeFXMLFileName.equals("AddChecking.fxml")){
            double bal = DataEntryDriver.getDoubleFromTextField(startingBalance);
            System.out.println(bal);
            if(bal<=0.001){
                addCheckingAccSaveB.setDisable(true);
            }else{
                addCheckingAccSaveB.setDisable(false);
            }
        }

        if(activeFXMLFileName.equals("AddLoan.fxml")){
            boolean valid = true;

            String selectedItem = loanAccountTypeComboBox.getValue();
            double startingBalCredLimit = DataEntryDriver.getDoubleFromTextField(startingBalance);
            double interestRate = DataEntryDriver.getDoubleFromTextField(loanInterestRate);
            int term = -1;
            boolean itemIsSelected =false;
            boolean cclSelected = false;
            System.out.println("sel item: "+selectedItem);

            if(selectedItem!=null){
                if(selectedItem.equals("Short Term Loan")){
                    itemIsSelected=true;
                    term = DataEntryDriver.getIntFromTextField(loanTermYears);
                }
                if(selectedItem.equals("Long Term Loan")){
                    itemIsSelected=true;
                    term= DataEntryDriver.getIntFromTextField(loanTermYears);
                }
                if(selectedItem.equals("Credit Card Loan")){
                    cclSelected=true;
                    itemIsSelected=true;
                }

                if(!itemIsSelected || startingBalCredLimit<0.001 || interestRate<0.0001){
                    valid = false;
                }

                if(!cclSelected && itemIsSelected){ // if item selected but Not CCL
                    if(term<=0){
                        valid=false;
                    }
                }
            }else{
                valid = false;
            }



            addLoanAccSaveB.setDisable(!valid);


        }

        if(activeFXMLFileName.equals("AddSavings.fxml")){
            double bal = DataEntryDriver.getDoubleFromTextField(startingBalance);
            double interest = DataEntryDriver.getDoubleFromTextField(savingInterestRate);
            int term = -1;
            if(cdCheckBox.isSelected()){
                term = DataEntryDriver.getIntFromTextField(savingCDTerm);
            }

            boolean valid = true;

            if(bal<0.001){
                valid=false;
            }
            if(interest<0.0001){
                valid=false;
            }
            if(cdCheckBox.isSelected() && term <=0){
                valid=false;
            }
            addSavingsAccSaveB.setDisable(!valid);
        }

        if(activeFXMLFileName.equals("ManageSavingsAcct.fxml")){
            boolean disableButton = false;

            ArrayList<TextField> textFields = new ArrayList<>();
            textFields.add(startingBalance);
            textFields.add(savingInterestRate);
            boolean textFieldsHaveData = DataEntryDriver.allTextFieldsHaveData(textFields);
            if(savingCdCloseDatePicker.getEditor().getText().isEmpty() || !textFieldsHaveData){
                disableButton = true;
            }


            manageSavingsAccSaveB.setDisable(disableButton);
        }



    }



    public <T> T getFocusedObject(){ // so far returns a TextField or Button of focused object
        // use caution with how and when you call this.
        Node focusedNode = getFocusedNode();

        if(focusedNode instanceof TextField){
            TextField returnVal = (TextField) Main.activeStage.getScene().focusOwnerProperty().get();
            return (T) returnVal;
        }else if(focusedNode instanceof Button){
            Button returnVal = (Button) Main.activeStage.getScene().focusOwnerProperty().get();
            return (T) returnVal;
        }else{
            return null;
        }

    }

    public boolean focusedNodeButton(){ // true if focused node is a button
        boolean returnVal = false;
        Node focusedNode = getFocusedNode();

        if(focusedNode instanceof Button){
            returnVal = true;
        }

        return returnVal;
    }

    @FXML
    public void testWindowExitButtonAction(){
        System.out.println("testwindowtest");
        //Stage activeStage = Main.activeStage;
        //System.out.println(activeStage.isFocused());

        closeWindow();

    }


    public Node getFocusedNode(){ // of active stage
        Node returnNode;

        if(Main.activeStage!=null){
            returnNode = Main.activeStage.getScene().getFocusOwner();
        }else{
            returnNode = null;
        }

        return returnNode;
        //return Main.activeStage.getScene().getFocusOwner();
    }


    public void enterKeyLoginExit(KeyEvent e){
        if(e.getCode().getName().equals("Enter") && Main.primaryStage.getScene().getFocusOwner() instanceof Button ){
            loginInterfaceExitButton();
        }
    }


    @FXML
    public void ssnFieldKeyEvent(KeyEvent e){ // a generic and multi use method to validate any ssn textfield hopefully
        //System.out.println("Start SSN field key event");
        EventType<KeyEvent> keyEventType = e.getEventType();
        String keyCodeName = e.getCode().getName();
        String keyEventTypeName = keyEventType.getName();
        //System.out.println(e.getCode().getName());

        TextField focusedTextField = getFocusedObject();

        if(keyCodeName.equals("Up") || keyCodeName.equals("Down") || keyCodeName.equals("Left")
                || keyCodeName.equals("Right")){
            return;
        }

        // only fired if all text is selected and full ssn length of 11 is entered. Launch of interface with textfield selected
        if(!keyCodeName.equals("Backspace")){ // if users types number 5 at start of interface
            if(focusedTextField.getCaretPosition()==0 && focusedTextField.getText().length()==11){ // allow users to enter new numbers
                return;
            }

        }

        validateSSNField(e,focusedTextField);



    }


    @FXML
    public void manageUserKeyEvent(KeyEvent e){
        //System.out.println("Start Manage User Key Event");
        EventType<KeyEvent> keyEventType = e.getEventType();
        String keyCodeName = e.getCode().getName();
        String keyEventTypeName = keyEventType.getName();

        //System.out.println(e.getCode().getName());
        String ssn = DataEntryDriver.stripSSN(manageUserSSNField.getText());
        manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

        if(keyCodeName.equals("Enter")){ // allows enter button to fire main event
            //System.out.println("Keycode was enter");
            //System.out.println(keyEventType.toString());
            if(keyEventTypeName.equals("KEY_PRESSED")){
                //System.out.println("Key Event Type Key Pressed");
                if(!manageUserLookupButton.isDisabled()){
                    //System.out.println("Enter key pressed button not disabled");
                    if(Main.loggedInEmployee.getType().equals("T")){
                        tellerInterfaceManageLookupButton();
                    }
                    if(Main.loggedInEmployee.getType().equals("M")){
                        // PUT THE CODE FOR MANAGER INTERFACE LOOKUP BUTTON HERE
                        tellerInterfaceManageLookupButton(); // go ahead and copy it
                    }

                }
            }
        }else{ // if enter button was not pressed
            ssnFieldKeyEvent(e);
            manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(manageUserSSNField.getText()));
            setManageUserErrLabel(manageUserSSNField.getText());
        }

    }

    // END KEY EVENTS BLOCK

    // sets the err label in manage user accordingly
    public void setManageUserErrLabel(String ssn){
        ssn = DataEntryDriver.stripSSN(ssn);

        if(!DataEntryDriver.ssnValid(ssn)){// if not 9 digits
            //System.out.println("enter valid number "+ssn);
            lookupInterErrLabel.setText("Please Enter a Valid 9 digit SSN number");
        }else{ // if ssn is 9 digits
            if(!DataEntryDriver.ssnInDatabase(ssn)) {
                //System.out.println("ssn not in database please go to add account");
                lookupInterErrLabel.setText("The SSN you Entered Is not In the Database.\nGo to Add new Customer or enter" +
                        " a valid 9 Digit SSN number.");
            }else{
                CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(manageUserSSNField.getText());
                lookupInterErrLabel.setText("Found a Matching account with Last Name: "+ca.getLastName());
                //Main.outEmployee.println("Employee UserName: "+Main.loggedInEmployee.getUserName()+" Looked Up account: "+ ca.toString());
                //System.out.println("LAST SSN: "+ssn);
                manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

            }
        }

    }

    public void setAtmErrMsg(){
        ATMInterErrLabel.setText("Insufficient Funds");
    }


    public void deleteAccountButton(){
        DataEntryDriver.removeCustomerAccount(Main.customerAccount.getCustID());

        goToTellerScene();


    }


    public void customerInterAtmWithdrawalButton() {
        System.out.println("Complete ATM Transaction");

        if(DataEntryDriver.getDoubleFromString(customerInterAtmWithdrawalAmt.getText()) >0.001){
            FinanceDriver.completeAtmTransaction(customerInterAtmWithdrawalAmt,"null");
            customerDispData();
        }else{
            customerInterErrLabel.setText("Plese enter a valid amount");
        }


    }

    public void customerInterAtmDepositButton() {
        System.out.println("Complete ATM Transaction");

        double checkDepositAmount = DataEntryDriver.getDoubleFromString(customerInterAtmCheckAmt.getText());
        String checkNumber = customerInterAtmCheckNum.getText();

        if(checkDepositAmount >0.001 && checkNumber.length()>0){
            FinanceDriver.completeAtmTransaction(customerInterAtmCheckAmt,checkNumber);
            customerDispData();
        }else{
            customerInterErrLabel.setText("Please enter a valid amount and check number");
        }

    }



    public void customerInterCCButton(){

        try {
        Parent root = FXMLLoader.load(getClass().getResource("customerCreditCard.fxml"));
        Main.primaryStage.setTitle("Customer Credit Card Interface");
        Main.primaryStage.setScene(new Scene(root, 700, 500));
        Main.primaryStage.show();
        Main.activeStage=Main.primaryStage;
        System.out.println("set active stage to primary in Customer Credit Card Button");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Main.loggedInCustomer.toString());
    }


    public void mainInterfaceCustomerButton(){
        Parent root = null;
        Parent login = null;
        try {

            if(!customerPendingLogin){
                if(customerLogIn){ // if method was called and customer was already logged in .
                    //customerPendingLogin = false;
                    root = FXMLLoader.load(getClass().getResource("CustomerInterface.fxml"));
                    Main.primaryStage.setTitle("Customer Interface");
                    Main.primaryStage.setScene(new Scene(root, 700, 500));
                    Main.primaryStage.show();
                    Main.activeStage=Main.primaryStage;
                    System.out.println("set active stage to primary in Customer button");
                }else{// if method called and customer isn't logged in. THIS WILL ALWAYS RUN ON FIRST CALL
                    customerPendingLogin =true; // set pending login to true because the login interface is launched
                    System.out.println("checking in");
                    login = FXMLLoader.load(getClass().getResource("CustomerLogin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Customer Login");
                    stage.setScene(new Scene(login,382,420));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    stage.setOnCloseRequest(event -> loginInterfaceExitButton());

                    Main.activeStage=stage;
                    System.out.println("set active stage to Customer login");
                }
            }else{
                System.out.println("login already pending");
            }



            // so now this method is done and the program is waiting for the login interface to finish

        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }




    // Main Screen Buttons and Login Methods
    @FXML
    public void mainInterfaceTellerButton(){
        //System.out.println("hi");
        Parent root = null;
        Parent login = null;
        //System.out.println("tellerLogIn value: "+tellerLogIn);
        //System.out.println("teller Pending value: "+tellerPendingLogin);

        try {

            if(!tellerPendingLogin){ // if login window is not already active
                if(tellerLogIn){// if teller is logged in after login window closes and it recalls this method
                    root = FXMLLoader.load(getClass().getResource("TellerInterface.fxml"));
                    Main.primaryStage.setTitle("Teller Interface");
                    Main.primaryStage.setScene(new Scene(root,700,500));
                    Main.primaryStage.show();
                    Main.activeStage=Main.primaryStage;
                }else{
                    tellerPendingLogin=true;
                    login = FXMLLoader.load(getClass().getResource("TellerLogin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Teller Login");
                    stage.setScene(new Scene(login,382,420));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    stage.setOnCloseRequest(event -> loginInterfaceExitButton());


                    Main.activeStage = stage;
                    System.out.println("set active stage in tellerbutton: "+stage.getTitle());
                    System.out.println("main active stage title is: "+Main.activeStage.getTitle());



                }
            }else{
                System.out.println("teller already pending login");

            }




        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

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
                Main.activeStage=Main.primaryStage;
                System.out.println("set active stage to primary in manager button");
            }else{
                managerPendingLogin =true;
                login = FXMLLoader.load(getClass().getResource("ManagerLogin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Bank Manager Login");
                stage.setScene(new Scene(login,382,420));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

                stage.setOnCloseRequest(event -> loginInterfaceExitButton());

                Main.activeStage=stage;
                System.out.println("set active stage to manager login");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }


    @FXML
    public void loginInterfaceLoginButton(){
        System.out.println("login inter login pending teller : "+tellerPendingLogin);
        System.out.println("login inter login button pending customer "+customerPendingLogin);



        if(tellerPendingLogin){ // if pending but not complete login
            tellerLogIn = validateLoginCreds("Teller");
            if(tellerLogIn){ // if login info was valid
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

        if(customerPendingLogin){ // customer pending login is true

            customerLogIn = validateLoginCreds("Customer"); // validate and determine if they are logged in or not
            System.out.println("in verify");

            System.out.println("Customer logged in after validate method called is: "+customerLogIn);
            if(customerLogIn){ // if validate was true and they are now logged in then call the main login button again
                customerPendingLogin=false;
                Main.outEmployee.println(Main.getDateTimeString()+"Customer Account UserName: Customer logged into account.");
                //CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerAtmCardNum(loginInterUser.getText());


                Main.loggedInCustomer = Main.customerAccount;
                //Main.customerAccount = ca;

                closeWindow();
                mainInterfaceCustomerButton();

            }
        }


        // now the tellerLogIn and managerLogIn booleans let us know if, and of what type, a user is logged in as.
    }

    @FXML
    public void loginInterfaceExitButton(){
        tellerLogIn=false;
        managerLogIn=false;
        tellerPendingLogin=false;
        managerPendingLogin=false;
        customerLogIn=false;
        customerPendingLogin=false;

        //Stage stage = (Stage) loginInterExitButton.getScene().getWindow();
        Stage stage = Main.activeStage;
        stage.close();
        goToMainScene();
    }



    //End Main Screen Block


    // TELLER INTERFACE BLOCK

    public void viewRecentActivityDispData(){
        CustomerAccount ca = Main.customerAccount;// still set from the lookup interface
        dispDataUpper();
        displayActivityTextArea.setText("");
        // now add part to display the activity

        // print all checks
        // print all transactions

        displayActivityTextArea.appendText("Checks:\n");
        for(Check check:ca.getChecks()){
            displayActivityTextArea.appendText(check.toStringPrettyPrint()+"\n");
        }

        displayActivityTextArea.appendText("\n\nTransactions: \n");
        for(Transaction transaction:ca.getTransactions()){
            displayActivityTextArea.appendText(transaction.toStringPrettyPrint()+"\n");
        }

    }

    // used to display the top data in the manage user scenes
    public void dispDataUpper(){
        CustomerAccount ca = Main.customerAccount;// still set from the lookup interface
        manageDispDataSSN.setText(DataEntryDriver.fixSSN(Main.customerAccount.getCustID()));
        manageDispDataFirst.setText(ca.getFirstName());
        manageDispDataLast.setText(ca.getLastName());
        manageDispDataStreetAddr.setText(ca.getStreetAddr());
        manageDispDataCity.setText(ca.getCity());
        manageDispDataState.setText(ca.getState());
        manageDispDataZip.setText(ca.getZip());
    }

    public void tellerManageDispData(){
        CustomerAccount ca = Main.customerAccount;
        System.out.println("display data");
        String loggedInEmployeeType = Main.loggedInEmployee.getType();

        manageDispDataSSN.setText(DataEntryDriver.fixSSN(Main.customerAccount.getCustID()));
        manageDispDataFirst.setText(ca.getFirstName());
        manageDispDataLast.setText(ca.getLastName());
        manageDispDataStreetAddr.setText(ca.getStreetAddr());
        manageDispDataCity.setText(ca.getCity());
        manageDispDataState.setText(ca.getState());
        manageDispDataZip.setText(ca.getZip());

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
            SavingsAccount simple = ca.getSimpleSavingsAccount();
            String balanceFormatted = DataEntryDriver.formatAccountBalance(simple.getAccountBalance());
            manageDispDataAcctBalance.setText(balanceFormatted);

            if(simple.getAccountBalance()<0.0){
                manageDispDataAcctStatus.setText("Overdrawn");
            }else{
                manageDispDataAcctStatus.setText("Current");
            }
            manageDispDataAcctType.setText(simple.getType());



        }else{
            manageDispDataAcctBalance.setText("");
            manageDispDataAcctStatus.setText("No account for user");
        }







    }







    @FXML
    public void tellerInterfaceAddNewButton(){
        System.out.println("tellerInterfaceAddNewButton");
        Parent root = null;


        try {
            root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
            Main.primaryStage.setTitle("Add a new user Account");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("set active to primary in teller add new");
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void tellerInterfaceManageButton(){
        System.out.println("tellerInterfaceManageButton");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUser.fxml"));
            Main.primaryStage.setTitle("Manage existing user");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("set active to pri teller int manage");
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
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
            Main.activeStage=Main.primaryStage;
            System.out.println("set active stage to primary in lookup ssn button");

        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
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
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in teller update data button");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
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
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in teller update data prev");
            // add code to pull the data for the currentSSN data
            System.out.println("current ssn is: "+Main.currentCustomerID);

            // use placeholder object if needed to display data.

        } catch (Exception e) {
            e.printStackTrace();
            Main.activeStage=null;
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

        ca.setCustID(DataEntryDriver.fixSSN(updateDataSSN.getText()));
        ca.setFirstName(updateDataFirstName.getText().trim());
        ca.setLastName(updateDataLastName.getText().trim());
        ca.setStreetAddr(updateDataAddress.getText().trim());
        ca.setCity(updateDataCity.getText().trim());

        String state;
        try {
            state = stateComboBox.getValue();
        } catch (Exception e) {
            state = "";
        }
        state = DataEntryDriver.fullStateToAbb(state);

        ca.setState(state);
        //ca.setState(updateDataState.getText().trim().toUpperCase());
        ca.setZip(updateDataZip.getText().trim());

        DataEntryDriver.updateCustomerAccount(ca,ssn);



        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in teller update save");
        } catch (Exception e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }




    // validation of checking account should be done before this is called.
    // DISABLE add account button if they already have a checking account
    // because you can only have one.
    @FXML
    public void addCheckingAccountSaveButton() throws InterruptedException {
        // add checking account object to current customer can only have one checking account per user
        CustomerAccount customerAccount = Main.customerAccount;
        boolean isGoldAccountSelected = goldCheckBox.isSelected();
        boolean backupSavingSelected = backupSavingsCheckBox.isSelected();
        double startingBalanceDouble = DataEntryDriver.getDoubleFromTextField(startingBalance);
        CheckingAccount checkingAccount = new CheckingAccount(customerAccount,isGoldAccountSelected,backupSavingSelected,startingBalanceDouble);
        customerAccount.addCheckingAccount(checkingAccount);
        goToAddFinanceAcc(); // then go back to last screen.
    }


    @FXML
    public void addSavingsAccountSaveButton(){
        System.out.println("savings save button");
        CustomerAccount customerAccount=Main.customerAccount;
        boolean isCdAccount = cdCheckBox.isSelected();
        double startingBalanceDouble = DataEntryDriver.getDoubleFromTextField(startingBalance);
        double interestRate = DataEntryDriver.getDoubleFromTextField(savingInterestRate);
        int termInYears=-1;

        if(cdCheckBox.isSelected()){ // cd box selected
            System.out.println("selected");
            termInYears= DataEntryDriver.getIntFromTextField(savingCDTerm);
        }else{ // cd box not selected
            System.out.println("not selected");
            //termInYears=-1;
        }

        // convert interest rate to decimal.
        interestRate = interestRate/100.00;

        SavingsAccount newSavings = new SavingsAccount(customerAccount,isCdAccount,startingBalanceDouble,interestRate,termInYears);


        // TESTING
        if(customerAccount.getCustID().equals("111-11-1111")){
            if(isCdAccount){
                newSavings.setDateOpened("05/05/2005"); // for testing
            }
        }
        // TESTING

        customerAccount.addSavingsAccount(newSavings);
        goToAddFinanceAcc();


    }

    @FXML
    public void addLoanAccountSaveButton(){
        System.out.println("Add a loan account save button click.");
        CustomerAccount customerAccount = Main.customerAccount;
        double initialLoanAmt = DataEntryDriver.getDoubleFromTextField(startingBalance);
        double interestRate = DataEntryDriver.getDoubleFromTextField(loanInterestRate);

        // fix interest to decimal from whole number percent
        interestRate = interestRate / 100.00;

        String selectedItem = loanAccountTypeComboBox.getValue();
        String loanType = "";

        if(selectedItem.equals("Short Term Loan")){
            loanType="STL";
        }
        if(selectedItem.equals("Long Term Loan")){
            loanType="LTL";
        }
        if(selectedItem.equals("Credit Card Loan")){
            loanType="CCL";
        }


        String loanTermString = "-1";
        if(loanType.equals("LTL") || loanType.equals("STL")){
            loanTermString=loanTermYears.getText();
        }

        // now create a loan account object using the second constructor
        LoanAccount loanAccount = new LoanAccount(customerAccount,initialLoanAmt,interestRate,loanType,loanTermString);
        customerAccount.addLoanAccountObject(loanAccount);
        goToAddFinanceAcc();




    }



    public void manageCheckingAccountSaveButton(){
        // value set to 30,000,000 bucks

        System.out.println("save button on checking");
        CustomerAccount customerAccount = Main.customerAccount;

        boolean goldSelected = goldCheckBox.isSelected();
        boolean backupSelected = backupSavingsCheckBox.isSelected();
        double balance = DataEntryDriver.getDoubleFromTextField(startingBalance);

        CheckingAccount ca = customerAccount.getCheckingAccount();

        ca.setGoldAccount(goldSelected);
        ca.setBackupSavingsEnabled(backupSelected);
        ca.setAccountBalance(balance);

        goToManageFinanceAcc();
    }

    public void manageSavingsAccountsSaveButton(){

        CustomerAccount customerAccount = Main.customerAccount;
        SavingsAccount savingsAccount = customerAccount.getSavingsAccountByFixedID(
                manageSavingsAccountsList.getSelectionModel().getSelectedItem());
        boolean isCd = cdCheckBox.isSelected();
        double balance = DataEntryDriver.getDoubleFromTextField(startingBalance);
        double interest = DataEntryDriver.getDoubleFromTextField(savingInterestRate)/100.0;


        // going to try a hack here.
        // instead of checking the cd values I've set the text to withdrawal on the type events
        // so after wasting time explaining how to code in java...................................................
        System.out.println("save button text: "+manageSavingsAccSaveB.getText());

        if(manageSavingsAccSaveB.getText().equals("Save")){ // we are just changing data not making a cd withdrawal
            String closeDate = "null";
            if(savingsAccount.isCdAccount()){
                String closeDateString = DataEntryDriver.getStringFromLocalDateFormatted(savingCdCloseDatePicker.getValue());
                closeDate= closeDateString; // should be fixed already.
                savingsAccount.setCdCloseDate(closeDate);
            }

            savingsAccount.setCdAccount(isCd);
            savingsAccount.setAccountBalance(balance);
            savingsAccount.setInterestRate(interest/100.0);
            goToManageFinanceAcc();
        }else{ // we are making a withdrawal and should only get here if it is already a cd account
            System.out.println("making a cd withdrawal of: "+DataEntryDriver.getDoubleFromTextField(tempBalance));
            System.out.println(savingsAccount.toStringPrettyPrint());
            // data should be validated already
            // figue current value figure fee take that minus the withdrawal amount
            double withdrawalAmt = DataEntryDriver.getDoubleFromTextField(tempBalance);

            double savingsCDCurrentValue = savingsAccount.getCurrentCDValue();
            double feeForWithdrawalNow = savingsAccount.getFeeForWithdrawalOfCDonThisDay();
            double savingsCDBalanceAfterFee = savingsCDCurrentValue - feeForWithdrawalNow;
            double savingsBalAfterWithdrawal = savingsCDBalanceAfterFee - withdrawalAmt;


            // should be a transaction object here and should be using a method in FinanceDriver But I'm not coding that
            // right now
            savingsAccount.setAccountBalance(savingsBalAfterWithdrawal);
            goToManageFinanceAcc();

        }


        //

    }

    public void manageLoanAccountsSaveButton(){
        CustomerAccount ca = Main.customerAccount;
        String loanAccountFixedID = manageLoanAccountsList.getSelectionModel().getSelectedItem();
        LoanAccount selectedLoanAccount = ca.getLoanAccountByFixedID(loanAccountFixedID);
        String loanTypeShort = DataEntryDriver.getLoanTypeAbbFromFullName(loanAccountTypeComboBox.getSelectionModel().getSelectedItem());
        double balance = DataEntryDriver.getDoubleFromTextField(startingBalance);
        double interestRate = DataEntryDriver.getDoubleFromTextField(loanInterestRate)/100.0;
        String loanTermString = "";
        if(loanTypeShort.equals("LTL") || loanTypeShort.equals("STL")){
            loanTermString = DataEntryDriver.fixDateString(loanTermYears.getText());
        }

        selectedLoanAccount.setLoanAccountType(loanTypeShort);
        selectedLoanAccount.setCurrentBalance(balance);
        selectedLoanAccount.setInterestRate(interestRate);
        selectedLoanAccount.setLoanTerm(loanTermString);

        goToManageFinanceAcc();


    }




    // BANK MANAGER INTERFACES

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
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in manager lookup");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }



    @FXML
    public void viewRecentActivityButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDispActivity.fxml"));
            Main.primaryStage.setTitle("View Recent Activity");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in manager lookup");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void addFinanceAccountButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserAddFinanceAcc.fxml"));
            Main.primaryStage.setTitle("Add Finance Account");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in manager lookup");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void addCheckingAccountButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddChecking.fxml"));
            Main.primaryStage.setTitle("Add Checking Account");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in manager lookup");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void addSavingsAccountButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddSavings.fxml"));
            Main.primaryStage.setTitle("Add Savings Account");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in manager lookup");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void addLoanAccountButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddLoan.fxml"));
            Main.primaryStage.setTitle("Add Loan Account");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to primary in manager lookup");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }


    @FXML
    public void manageFinancialAccountsButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserManageFinanceAcc.fxml"));
            Main.primaryStage.setTitle("Manage Financial Accounts");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }


    @FXML
    public void manageCheckingAccountsButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageCheckingAcct.fxml"));
            Main.primaryStage.setTitle("Manage Checking Accounts");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void manageSavingsAccountsButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageSavingsAcct.fxml"));
            Main.primaryStage.setTitle("Manage Savings Accounts");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }



    @FXML
    public void manageLoanAccountsButton(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageLoanAcct.fxml"));
            Main.primaryStage.setTitle("Manage Loan Accounts");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }




    //Test so that I can commit merge.

    public void deleteFinancialAccountsButton(){
        System.out.println("deleting accounts");
        CustomerAccount customerAccount=Main.customerAccount;
        if(deleteAllCheckingAcctCheckBox.isSelected()){
            customerAccount.deleteCheckingAccount();
            // disable buttons
        }
        if(deleteAllSavingsAcctCheckBox.isSelected()){
            customerAccount.deleteAllSavingsAccounts();
        }
        if(deleteAllLoanAcctCheckBox.isSelected()){
            customerAccount.deleteLoanAccounts();
        }



    }










    //CUSTOMER INTERFACE METHODS

    public void customerDispData(){
        CustomerAccount ca = Main.loggedInCustomer;
        System.out.println("customer disp data ca: "+Main.customerAccount.getCustID());

        //System.out.println(ca.toString());
        String balanceFormatted = DataEntryDriver.formatAccountBalance(ca.getCheckingAccount().getAccountBalance());

        customerDispDataFirst.setText(ca.getFirstName());
        customerDispDataLast.setText(ca.getLastName());
        customerDispDataAccountBalance.setText(balanceFormatted);
        customerInterAtmWithdrawalAmt.setText("");
        customerInterAtmCheckNum.setText("");
        customerInterAtmCheckAmt.setText("");
    }

    public void customerAtmDispData(){
        CustomerAccount ca = Main.loggedInCustomer;
        System.out.println("customer disp data ca: "+Main.customerAccount.getCustID());

        customerDispDataFirst.setText(ca.getFirstName());
        customerDispDataLast.setText(ca.getLastName());
        customerInterCCNum.setText("1234567789008"); //Autofill with actual CC number

        customerInterCCHistory.setText("");

        customerInterCCHistory.appendText("Credit Card Transactions:" + "\n");


        for(int i = 1; i < 5; i++){            //loop with real transactions when we have them
            customerInterCCHistory.appendText("Here is a fake CC transaction 11-12-19, $55.00, Shoes" + "\n");
        }

    }

    public void processCCPurchase(){
        double purchaseAMT = DataEntryDriver.getDoubleFromTextField(customerInterCCPurchaseAmt);
        String purchaseDesc = (customerInterCCpurchaseDesc.getText());
        ArrayList<LoanAccount> loanAccounts = Main.customerAccount.getLoanAccounts();
        for(int i = 0;i <= loanAccounts.size();i++) {


            System.out.println("Amount in: " + purchaseAMT);
            System.out.println("For: " + purchaseDesc);

            // FinanceDriver.creditDebitLoanAccount(Main.customerAccount.loanAccounts.get(0),purchaseAMT,purchaseDesc);
            }
    }















    // GENERAL VALIDATION TYPE METHODS

    public static int positionAtKeyPress = -1;
    public static String lastEventTypeName="";

    public void validateSSNField(KeyEvent e,TextField textField){ // fixes ssn and adds - when needed. bulletproof method of data validation
        // see DataEntryDriver
        int position = textField.getCaretPosition();
        String keyCodeName = e.getCode().getName().toLowerCase();
        EventType keyEventType = e.getEventType();
        String keyEventTypeName = keyEventType.getName();

        if(keyCodeName.matches("[^\\d]")){// if not a digit return true or if digit false // if letter block

            if(keyEventTypeName.equals("KEY_PRESSED")){ // if key is pressed
                if(!lastEventTypeName.equals("KEY_PRESSED")){ // if last event type was not a key press ie. key release true or if first keypress
                    positionAtKeyPress = textField.getCaretPosition(); // get the position at the first key press
                    lastEventTypeName = keyEventTypeName; // set last event type
                }else{ // if last event is key pressed and this event is also key pressed
                    textField.setText(DataEntryDriver.fixSSN(textField.getText()));
                    textField.positionCaret(positionAtKeyPress);
                    return;
                }

            }else if(keyEventTypeName.equals("KEY_RELEASED")){ // if key is released
                // just set position to whatever it was when first keypress was recorded.
                textField.setText(DataEntryDriver.fixSSN(textField.getText()));
                textField.positionCaret(positionAtKeyPress);
                lastEventTypeName=keyEventTypeName;
            }


        }else{ // else entered key was a digit
            positionAtKeyPress=textField.getCaretPosition();
            textField.setText(DataEntryDriver.fixSSN(textField.getText()));
            if(e.getCode().getName().equals("Backspace")){
                if(position==7){
                    textField.positionCaret(6);
                }
                if(position==4){
                    textField.positionCaret(3);
                }else{
                    textField.positionCaret(position);
                }
            }else{
                if(position==3){
                    textField.positionCaret(4);
                }else if(position==6){
                    textField.positionCaret(7);
                }else{
                    textField.positionCaret(position);
                }
            }
        }
    }




    // this method gets the data from the interface, checks if it's valid, and returns and arraylist with
    // [0]= fieldName, [1]=data, [2]=isValid
    public ArrayList<String[]> getNewUserInfoValidArrayList(){
        ArrayList<String[]> validItems = new ArrayList<>();
        String interfaceID = "";

        String fName="";
        String lName="";
        String ssn="";
        String streetAddress="";
        String city="";
        String zipCode="";
        String state = "";

        //System.out.println("MAIN ACTIVE TITLE IS: "+Main.activeStage.getTitle());
        if(Main.activeStage.getTitle().contains("new")){
            interfaceID = "add";
            //System.out.println("new");
            fName = fNameTextField.getText();
            lName = lNameTextField.getText();
            ssn = socialSecTextField.getText();
            streetAddress = streetAddressTextField.getText();
            city = cityTextField.getText();
            zipCode = zipCodeTextField.getText();
        }
        if(Main.activeStage.getTitle().contains("Update")){
            interfaceID = "update";
            //System.out.println("update");
            fName = updateDataFirstName.getText();
            lName = updateDataLastName.getText();
            ssn = Main.customerAccount.getCustID();
            streetAddress = updateDataAddress.getText();
            city = updateDataCity.getText();
            zipCode = updateDataZip.getText();
        }

        //String state = autoCombo.getSelectionModel().getSelectedItem().toString();



        try {
            state = stateComboBox.getValue().toString();
        } catch (Exception e) {
            state = "";
        }


        state = DataEntryDriver.fullStateToAbb(state);





        String[] items = {fName,lName,streetAddress,city};

        for(int i=0;i<items.length;i++){
            if(items[i].length()<1){// if anything was not entered in first last name or city or address
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

        if(DataEntryDriver.ssnValid(ssn)){ // generic is the ssn a valid format number yes or no
            validItems.add(new String[]{"ssn",ssn,"true"});
        }else{
            validItems.add(new String[]{"ssn",ssn,"false"});
        }

        if(DataEntryDriver.ssnValidAndInDatabase(ssn)){

            if(interfaceID.equals("add")){
                validItems.add(new String[]{"ssnDoesNotExist",ssn,"false"});//ssn does not exist false means it does exist
            }
            if(interfaceID.equals("update")){// override the value for this interface because it will always exist
                validItems.add(new String[]{"ssnDoesNotExist",ssn,"true"});//ssn does not exist false means it does exist
            }


        }else{
            validItems.add(new String[]{"ssnDoesNotExist",ssn,"true"});// ssn is not in database go got a true value
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

    public boolean validateLoginCreds(String userType){// userType is either Teller or Manager
        boolean returnVal = false;

        //printAllData();//testing

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

        if(userType == "Customer") {
           if(loginInterUser.getText().length()>0 && loginInterPass.getText().length()>0){
               CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerAtmCardNum(loginInterUser.getText());
               if(!ca.isNull()){ // if search was not null
                   String caPin = ca.getPin();

                   if(loginInterPass.getText().equals(caPin)){
                       // put the code here to validate pin
                   }

                   Main.customerAccount = ca;
                   System.out.println("Selected ca from atm card is: "+ca.toString());
                   returnVal=true;
               }else{// if search was null
                   Main.customerAccount=Main.customerAccounts.get(0);// statically set the first account
                   returnVal=true;

                   // DELETE ALL ABOVE THIS LINE AND CHANGE BACK TO BELOW THIS TO REMOVE THE STATIC SET ACCOUNT FOR ANY LOGIN
                   //returnVal=false;
               }
           }else{
               returnVal=false;
           }
        }


        return returnVal;
    }



    // ADD NEW USER INTERFACE
    // make sure all items are true if not set label warning.
    public boolean addNewUserInfoValid(ArrayList<String[]> itemsArray){
        boolean returnVal = true;

        if(!allFieldsHaveData(getNewUserInfoValidArrayList(),1)){
            unsuccessfulEntryLabel.setText("Please fill out all fields");
            Main.defaultSceneButton.setDisable(true);


        }

        for(int i=0;i<itemsArray.size();i++){
            //System.out.println(Arrays.toString(itemsArray.get(i)));

            if(itemsArray.get(i)[2].equals("false")){// if any other field shows false
                //addNewUserInterfaceEnterButton.setDisable(true);
                Main.defaultSceneButton.setDisable(true);
                String[] falseItem = itemsArray.get(i);
                returnVal=false;

                // explain why it's false
                if(falseItem[0].equals("ssn")){// display error for invalid ssn number
                    System.out.println("The ssn you entered was not valid please enter 9 numbers");
                    unsuccessfulEntryLabel.setText("Please enter a Valid 9 digit SSN!");
                    //addNewUserInterfaceEnterButton.setDisable(true);
                    Main.defaultSceneButton.setDisable(true);
                }
                if(falseItem[0].equals("zip")){
                    System.out.println("The zip you entered was not valid please enter a 5 digit zip");
                    unsuccessfulEntryLabel.setText("Please enter a 5 digit Zip");
                    //addNewUserInterfaceEnterButton.setDisable(true);
                    Main.defaultSceneButton.setDisable(true);
                }
                if(falseItem[0].equals("state")){
                    System.out.println("Please enter a 2 character State such as MO or AK");
                    unsuccessfulEntryLabel.setText("Please enter a 2 character State Abbreviation");
                    //addNewUserInterfaceEnterButton.setDisable(true);
                    Main.defaultSceneButton.setDisable(true);
                }
                if(falseItem[0].equals("ssnDoesNotExist")){
                    unsuccessfulEntryLabel.setText("This Customer Already Exist. Go To Manage User Interface to manage this user" +
                            " or enter another SSN!");
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


    // GENERAL METHODS SUCH AS INTERFACES THAT ARE REUSED LIKE THE UPDATE BASE DATA


    public void populateUpdateDataScreen(){
        updateDataSSN.setText(DataEntryDriver.fixSSN(Main.customerAccount.getCustID()));
        updateDataFirstName.setText(Main.customerAccount.getFirstName());
        updateDataLastName.setText(Main.customerAccount.getLastName());
        updateDataAddress.setText(Main.customerAccount.getStreetAddr());
        updateDataCity.setText(Main.customerAccount.getCity());

        String stateAbb = Main.customerAccount.getState();
        String stateFull = DataEntryDriver.stateAbbToFullName(stateAbb);

        stateComboBox.getEditor().setText(stateFull);
        stateComboBox.getSelectionModel().select(stateFull);

        //updateDataState.setText(Main.customerAccount.getState());


        updateDataZip.setText(Main.customerAccount.getZip());
    }




    // end general block






    // GENERAL NAVIGATION AND GOTO METHODS


    @FXML
    public void closeWindow(){ // use this to CLOSE A WINDOW so will exit whole program if used on a primaryStage

        try {
            Node activeNode = getFocusedNode();
            //Stage stage = (Stage) loginInterLoginButton.getScene().getWindow();
            Stage stage = (Stage) activeNode.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.err.println("ERROR IN CLOSE WINDOW");
        }


    }


    public void closeStage(Stage cStage){
        cStage.close();
    }


    @FXML
    public void goToMainScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Main.primaryStage.setTitle("Teller Bank Application(WIP)");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
            System.out.println("active to pri in goToMainScene");

            tellerLogIn = false;
            managerLogIn = false;
            customerLogIn = false;

        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
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
            Main.activeStage=Main.primaryStage;
            System.out.println("active to pri goToTellerScene");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
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
            Main.activeStage=Main.primaryStage;
            System.out.println("active to prim in goToManageLookupScene");


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }


    public void goToDisplayDataScene(){
        Parent root = null;
        try {

            if(Main.loggedInEmployee.getType().equalsIgnoreCase("T")){// load teller scene
                root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));

                Main.primaryStage.setTitle("Customer Account Data Management Interface");
                Main.primaryStage.setScene(new Scene(root,700,500));
                Main.primaryStage.show();
                Main.activeStage=Main.primaryStage;
                System.out.println("set active stage to primary in lookup ssn button");
            }

            if(Main.loggedInEmployee.getType().equalsIgnoreCase("M")){
                // load the manager display screen
                root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayDataTeller.fxml"));

                Main.primaryStage.setTitle("Customer Account Data Management Interface");
                Main.primaryStage.setScene(new Scene(root,700,500));
                Main.primaryStage.show();
                Main.activeStage=Main.primaryStage;
                System.out.println("set active stage to primary in lookup ssn button");
            }


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }



    public void goToAddFinanceAcc(){
        addFinanceAccountButton();// just simulate the button press
    }



    public void goToManageFinanceAcc(){
        manageFinancialAccountsButton();
    }







    @FXML
    public void loadHelpWindowAccountIDSystem(){ // loads a simple help window
        Parent help = null;
        try {
            help = FXMLLoader.load(getClass().getResource("Help.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(new Scene(help,382,420));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    // EVERYTHING BELOW THIS LINE TO END COMMENT IS TESTING PURPOSES ONLY


    @FXML
    public void mainInterfaceTestButton(){
        Parent root = null;
        Parent test = null;
        try {
            test = FXMLLoader.load(getClass().getResource("TestWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Bank Manager Login");
            stage.setScene(new Scene(test,660,532));
            stage.show();
            Main.activeStage=stage;
            System.out.println("active stage to stage of Test window");
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }





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


    @FXML
    public void randomSSNButton(){
        int size = Main.customerAccounts.size();
        Random random = new Random();
        int randomInt = random.nextInt(size);
        System.out.println(randomInt);
        manageUserSSNField.setText(Main.customerAccounts.get(randomInt).getCustID());

    }

    @FXML
    public void mainScreenTestButton(){
        System.out.println("test");
        System.out.println("Printing all loan Accounts");

        LocalDate today = DataEntryDriver.getCurrentDateObject();
        LocalDate yesterday = DataEntryDriver.getDateObjectFromString("11/23/2019");
        LocalDate future = DataEntryDriver.getDateObjectFromString("11/28/2019");

        LocalDate dueDate = today.withDayOfMonth(27);

        boolean todayBeforeDue = today.isBefore(dueDate);
        System.out.println("test result: "+todayBeforeDue);

        System.out.println("Print all loan accounts");
        for(CustomerAccount ca:Main.customerAccounts){
            if(ca.hasLoanAccount()){
                System.out.println("\n\n"+ca.toStringPrettyPrint());
                System.out.println("Loan Accounts:");
                ArrayList<LoanAccount> loanAccounts = ca.getLoanAccounts();
                for(LoanAccount la:loanAccounts){
                    System.out.println(la.toStringPrettyPrint());
                }

            }
        }

        System.out.println("Print all savings:");
        for(CustomerAccount ca:Main.customerAccounts){
            if(ca.hasSavingsAccount()){
                System.out.println("\n\n"+ca.toStringPrettyPrint());
                System.out.println("Savings Accounts:");
                ArrayList<SavingsAccount> savingsAccounts = ca.getSavingsAccounts();
                for(SavingsAccount sa:savingsAccounts){
                    System.out.println(sa.toStringPrettyPrint());
                }
            }
        }






    }

    @FXML
    public void generalTestButtonAction(){
        System.out.println("\n\nTestbutton action\n");
        String testString = DataEntryDriver.getDateString();
        System.out.println(testString);

        String testFixed = DataEntryDriver.fixDateString("7/8/2008");
        String test2 = DataEntryDriver.fixDateString("07/08/2008");
        String test3 = DataEntryDriver.fixDateString("null");

        for(CustomerAccount ca: Main.customerAccounts){
            if(ca.hasCheckingAccount()){
                System.out.println("ca checking acct id: "+ca.getCheckingAccount().getCheckingAcctID()+" Ca checks array: "+ca.getChecks().toString());
            }
        }


        System.out.println("Testing");
        for(CustomerAccount ca: Main.customerAccounts){
            ArrayList<Check> caChecks = ca.getChecks();

            for(Check check:caChecks){
                System.out.println(check.getCheckNumber());
            }
        }


    }

    public String toStringValues(){
        try {
            return "Controller{" +
                    "tellerLogIn=" + tellerLogIn +
                    ", managerLogIn=" + managerLogIn +
                    ", customerLogIn=" + customerLogIn +
                    ", customerPendingLogin=" + customerPendingLogin +
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
                ", CustomerInterAtmDepositButton=" + customerInterAtmDepositButton +
                ", CustomerInterAtmWithdrawalButton=" + customerInterAtmWithdrawalButton +
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
                ", updateDataSSN=" + updateDataSSN +
                ", tellerUpdateDataFirstName=" + updateDataFirstName +
                ", updateDataLastName=" + updateDataLastName +
                ", updateDataAddress=" + updateDataAddress +
                ", updateDataCity=" + updateDataCity +
                ", updateDataState=" + updateDataState +
                ", updateDataZip=" + updateDataZip +
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
