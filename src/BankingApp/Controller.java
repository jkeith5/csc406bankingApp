package BankingApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.regex.Pattern;


public class Controller implements Initializable{
    public static String activeFXMLFileName="";
    public static int sessionLogInTimeOutInSeconds = 300;

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
    @FXML Button randomSSN;
    @FXML Button logOut;

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
    @FXML Button customerScreen;
    @FXML Button customerInterCCButton;
    @FXML Button customerCCPurchaseButton;
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
    @FXML Label creditBalance;
    @FXML Label creditLimit;
    @FXML Label creditRemaining;
    @FXML Label creditPaymentDue;
    @FXML Label creditPaymentDueDate;
    @FXML CheckBox customerInterCCMakePaymentCheckBox;


    // Note when I say ManageExistingTeller I mean the ManageExistingUser interface for the Teller account
    @FXML Button manageExistingUpdateDataButton;
    @FXML Button manageExistingViewRecentActivityButton;
    @FXML Button manageExistingDebitCreditAccountButton;
    @FXML RadioButton manageExistingCheckingAccount;
    @FXML RadioButton manageExistingSavingsAccount;
    @FXML CheckBox manageExistingTransferFunds;
    @FXML Label manageExistingCheckingLabel;
    @FXML Label manageExistingSavingsLabel;
    @FXML Button manageExistingAddFinanceAccountButton;

    @FXML TextField manageExistingFundsTransferAmount;
    @FXML Button manageExistingDispActivityPrevButton;

    @FXML Button manageExistingDispDataStopPayment;


    // Update data interface
    @FXML Button updateDataPreviousButton;
    @FXML Button updateDataSaveButton;
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
    @FXML TextField manageLoanPaymentAmount;
    @FXML CheckBox manageLoanMakePaymentCheckBox;


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

    @FXML ComboBox<Check> unprocessedChecksComboBox;
    @FXML TextField checkNumber;
    @FXML TextField checkDate;
    @FXML TextField checkAmount;
    @FXML TextField checkProcessed;
    @FXML TextField checkStatus;
    @FXML Button stopPaymentButton;

    @FXML Button exportDataButton;
    @FXML Button importDataButton;


    @FXML Button testButton;

    @FXML ComboBox<String> openFinancialAccountsComboBox;
    @FXML TextField openFinancialAccountBalanceField;
    @FXML Button openFinancialAccountsCloseAccountButton;
    @FXML Button openFinancialAccountsPreviousButton;





    String fName, lName, socialSec, streetAddress, city, zipCode, state;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.printToConsoleAndLog("Initializing Controller");
        System.out.println("\n\n");

        String locationString = DataEntryDriver.getLocationFileName(location); // holds location of the interface name
        activeFXMLFileName = locationString; // sets a global variable to the active interface name
        System.out.println(locationString);

        Image helpLogo = null; // sets the help logo for the interface
        if(DataEntryDriver.runningFromIDE()){
            helpLogo = new Image("file:src/Resources/help.png");
        }else{
            helpLogo = new Image("file:"+System.getProperty("user.dir")+"/Resources/help.png");
        }


        boolean stackDebug = false; // switch to true to get stack trace debugging
        if(stackDebug){ // if true then print stack trace debug info
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            System.out.println("\n\nStackTrace\n");
            System.out.println("Stack Trace: "+stackTraceElements[10].toString());

            int i =0;
            for(StackTraceElement ste:stackTraceElements){
                System.out.println("Calling Method Name: "+ste.getMethodName());
                System.out.println("Line Number of Caller: "+ste.getLineNumber());
                System.out.println(String.valueOf(i)+" : "+ste.toString());
                i++;
            }
            System.out.println("\nEnd Stack Trace\n");
        }

        String loggedInEmployeeType = "null";
        if(Main.loggedInEmployee!=null){
            loggedInEmployeeType = Main.loggedInEmployee.getType();
        }

        if(locationString.equals("AddNewUser.fxml")){
            stateComboBox.setTooltip(new Tooltip());
            stateComboBox.getItems().clear();
            stateComboBox.getItems().addAll(states);
            autoCombo = new ComboBoxAutoComplete<String>(stateComboBox); // creates and manages the combo box
            Main.defaultSceneButton = addNewUserInterfaceEnterButton;
        }

        if(locationString.equals("CustomerCreditCard.fxml")){
            customerInterErrLabel.setText("");// set error label to nothing
            DataEntryDriver.validateBalanceAmountField(customerInterCCPurchaseAmt,false);

            customerInterCCMakePaymentCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){ // if make payment is selected
                        customerInterCCpurchaseDesc.setDisable(true);
                        customerCCPurchaseButton.setText("Payment");
                    }else{ // if make payment is not selected
                        customerInterCCpurchaseDesc.setDisable(false);
                        customerCCPurchaseButton.setText("Purchase");
                    }
                    customerInterCCPurchaseAmt.setText("");
                    customerInterCCpurchaseDesc.setText("");
                }
            });

            customerAtmDispData();
            creditCardAmountEvent();
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

        if(locationString.equals("ManageExistingUserDisplayData.fxml")){
            CustomerAccount ca = Main.customerAccount;

            accTypeToggleGroup = manageExistingCheckingAccount.getToggleGroup();
            manageExistingTransferFunds.setSelected(false);
            manageExistingTransferFunds.setDisable(true);


            // disable checking readio button if no checking account if it exists then set as default selected
            if(ca.hasCheckingAccount()){
                manageExistingCheckingAccount.setSelected(true);
            }else{ // has no checking
                manageExistingCheckingAccount.setSelected(false);
                manageExistingCheckingAccount.setDisable(true);
            }

            // teller can credit any account but savings CD
            // same as above but with savings account
            if(ca.hasSimpleSavings()){// if they do have a savings account
                manageExistingSavingsAccount.setDisable(false);
                if(!ca.hasCheckingAccount()){// if they have a savings but not a checking account
                    manageExistingSavingsAccount.setSelected(true);
                }
            }else{// if no savings account
                manageExistingSavingsAccount.setSelected(false);
                manageExistingSavingsAccount.setDisable(true);
            }

            manageExistingCheckingLabel.setText("");
            manageExistingSavingsLabel.setText("");

            if(ca.hasSimpleSavings() && ca.hasCheckingAccount()){ // if they have a savings and checking account
                manageExistingTransferFunds.setDisable(false);
            }else{// else they have NO savings or checking account disable all features related
                if(!ca.hasSimpleSavings() && !ca.hasCheckingAccount()){
                    manageExistingFundsTransferAmount.setDisable(true);
                    manageExistingDebitCreditAccountButton.setDisable(true);
                    manageDispDataAcctType.setText("");
                    manageDispDataAcctStatus.setText("");
                }


            }

            manageDispDataErrLabel.setText("");
            manageExistingDebitCreditAccountButton.setDisable(true);

            // sets a changed listener to this object
            DataEntryDriver.validateBalanceAmountField(manageExistingFundsTransferAmount,true);

            // since the manager can see everything anyway just disable the stuff for the teller
            if(Main.loggedInEmployee.getType().equals("T")){
                deleteCustomerAccountButton.setDisable(true);
                manageExistingAddFinanceAccountButton.setDisable(true);
            }

            if(FinanceDriver.hasUnprocessedChecks(ca)){
                manageExistingDispDataStopPayment.setDisable(false);
            }else{
                manageExistingDispDataStopPayment.setDisable(true);
            }



            tellerManageDispData();

        }

        if(locationString.equals("ManageExistingUserUpdateData.fxml")){
            unsuccessfulEntryLabel.setText("");
            stateComboBox.setTooltip(new Tooltip());
            stateComboBox.getItems().clear();
            stateComboBox.getItems().addAll(states);
            autoCombo = new ComboBoxAutoComplete<String>(stateComboBox); // creates and manages the combo box

            Main.defaultSceneButton = updateDataSaveButton;

            populateUpdateDataScreen();
        }
        if(locationString.equals("ManagerLogin.fxml")){
            //
        }
        if(locationString.equals("TellerInterface.fxml")){
            //
        }
        if(locationString.equals("TellerLogin.fxml")){
            //
        }

        if(locationString.equals("CustomerInterface.fxml")){
            customerDispData();
            customerInterErrLabel.setText("");

            customerInterAtmDepositButton.setDisable(true);
            customerInterAtmWithdrawalButton.setDisable(true);

            DataEntryDriver.validateBalanceAmountField(customerInterAtmWithdrawalAmt,false);
            DataEntryDriver.validateBalanceAmountField(customerInterAtmCheckAmt,false);
            DataEntryDriver.validateNumberField(customerInterAtmCheckNum,4);
            CustomerAccount ca = Main.customerAccount;

            if(ca.hasCreditCardAcct()){
                customerInterCCButton.setDisable(false);
            }else{
                customerInterCCButton.setDisable(true);
            }


            if(ca.getAtmWithdrawalsToday()>=2){
                customerInterErrLabel.setText("Only two ATM Withdrawals allowed A Day");
                customerInterAtmWithdrawalAmt.setDisable(true);
            }


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

            if(customerAccount.hasSimpleSavings()){ // if they have a simple saving
                cdCheckBox.setSelected(true);
                cdCheckBox.setDisable(true);
                addSavingsAcctErrLabel.setText("User already has a simple savings account");

            }else{// have no simple savings account
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
                //manageSavingsAccB.setDisable(true);
            }




        }
        if(locationString.equals("ManageCheckingAcct.fxml")){
            addCheckingAcctErrLabel.setText("");
            dispDataUpper();
            DataEntryDriver.validateBalanceAmountField(startingBalance,false);
            displayDataManageFinancialAccounts();
        }

        if(locationString.equals("ManageLoanAcct.fxml")){
            dispDataUpper();
            CustomerAccount ca = Main.customerAccount;
            manageLoanAccSaveB.setDisable(true);
            help.setImage(helpLogo);
            manageLoanPaymentAmount.setDisable(true);
            errLabel.setText("");

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



                DataEntryDriver.validateBalanceAmountField(startingBalance,false);
                DataEntryDriver.validateInterestField(loanInterestRate,true,true);
                DataEntryDriver.validateBalanceAmountField(loanTermYears,false);
                DataEntryDriver.validateBalanceAmountField(manageLoanPaymentAmount,false);


                manageLoanMakePaymentCheckBox.setDisable(true);

                manageLoanAccountsList.valueProperty().addListener(new ChangeListener<String>() {
                    @Override public void changed(ObservableValue ov, String t, String t1) {
                        manageLoanAccountTypeEvent();
                    }
                });

                manageLoanMakePaymentCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        manageLoanAccountsMakePaymentCheckBoxEvent();
                    }
                });

            }// end has loan account


        }
        if(locationString.equals("ManageSavingsAcct.fxml")){
            dispDataUpper();
            errLabel.setText("");
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
                    }
                });




            }


        }
        if(locationString.equals("StopPayment.fxml")){
            dispDataUpper();
            ArrayList<Check> checks = Main.customerAccount.getChecks();

            unprocessedChecksComboBox.getItems().clear(); // clear the box or it will persist
            ArrayList<Check> unprocessedCheckNumbers = new ArrayList<>(); // string arraylist for the id

            for(Check check:checks){
                if(!check.isCheckProcessed()){
                    if(!check.getCheckStatus().equals("hold")){
                        unprocessedCheckNumbers.add(check);
                    }

                }
            }
            unprocessedChecksComboBox.getItems().addAll(unprocessedCheckNumbers);
            unprocessedChecksComboBox.converterProperty().setValue(new StringConverter<Check>() {
                @Override
                public String toString(Check object) {
                    return object.getCheckNumber();
                }

                @Override
                public Check fromString(String string) {
                    return null;
                }
            });

            unprocessedChecksComboBox.valueProperty().addListener(new ChangeListener<Check>() {
                @Override
                public void changed(ObservableValue<? extends Check> observable, Check oldValue, Check newValue) {
                    unprocessedCheckComboBoxEvent();
                }
            });


            stopPaymentButton.setDisable(true); // set to false if check is selected and enough money to pay stop payment fee



        }
        if(locationString.equals("DeleteAccountWarning.fxml")){
            dispDataUpper();
            help.setImage(helpLogo);
            CustomerAccount ca = Main.customerAccount;
            openFinancialAccountsCloseAccountButton.setDisable(true);

            ArrayList<String> financialAccountIDS = new ArrayList<>();
            boolean hasChecking = ca.hasCheckingAccount();
            boolean hasSavings = ca.hasSavingsAccount();
            boolean hasLoan = ca.hasLoanAccount();

            if(hasChecking){
                if(ca.getCheckingAccount().getAccountBalance()>0.001 || ca.getCheckingAccount().getAccountBalance()<0.001){
                    financialAccountIDS.add(ca.getCheckingAccount().getFixedID());
                }
            }
            if(hasSavings){
                for(SavingsAccount sa:ca.getSavingsAccounts()){
                    if(sa.isCdAccount()){
                        if(sa.getCurrentCDValue()>0.001){
                            financialAccountIDS.add(sa.getFixedID());
                        }
                    }else{
                        if(sa.getAccountBalance()>0.001){
                            financialAccountIDS.add(sa.getFixedID());
                        }
                    }
                }
            }
            if(hasLoan){
                for(LoanAccount la:ca.getLoanAccounts()){
                    if(la.getCurrentBalance()>0.001){
                        financialAccountIDS.add(la.getFixedID());
                    }
                }
            }

            openFinancialAccountsComboBox.getItems().clear();
            openFinancialAccountsComboBox.getItems().addAll(financialAccountIDS); // the ones that have a balance
            openFinancialAccountsComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    openAccountsComboBoxEvent();
                }
            });




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

        boolean success = DataEntryDriver.addCustomerAccountToArrayList(tempAccount); // auto writes changes

        if(success){
            successfulEntryLabel.setText("Data storage for " + fName + " " + lName + " was successful!");
            successfulEntryLabel.visibleProperty().setValue(true);
            Main.outEmployee.println(Main.getDateTimeString()+Main.loggedInEmployee.getUserName()+" added account: "+
            tempAccount.toString());

            goToLoggedInEmployeeScene();

        }else{
            unsuccessfulEntryLabel.setText("ERROR CUSTOMER DATA WAS NOT SAVED!!!! PLEASE CONTACT TECH SUPPORT");
        }


    }

    @FXML
    public void addNewUserKeyEvent(){

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
        DataEntryDriver.validateZipField(updateDataZip);
        ArrayList<String[]> itemsValid = getNewUserInfoValidArrayList();
        if(addNewUserInfoValid(itemsValid)){
            Main.defaultSceneButton.setDisable(false);
        }else{
            Main.defaultSceneButton.setDisable(true);
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

    public void unprocessedCheckComboBoxEvent(){
        CustomerAccount ca = Main.customerAccount;
        Check selectedCheck = unprocessedChecksComboBox.getValue();

        checkNumber.setText(selectedCheck.getCheckNumber());
        checkDate.setText(selectedCheck.getCheckDate());
        checkAmount.setText(DataEntryDriver.getStringFromDouble(DataEntryDriver.round(selectedCheck.getCheckAmount(),2)));
        checkProcessed.setText(String.valueOf(selectedCheck.isCheckProcessed()));
        checkStatus.setText(selectedCheck.getCheckStatus());

        CheckingAccount checkingAccount = ca.getCheckingAccount();

        if(checkingAccount.getAccountBalance()>15.00){
            stopPaymentButton.setDisable(false);
        }else{
            stopPaymentButton.setDisable(true);
        }

    }


    public void openAccountsComboBoxEvent(){
        CustomerAccount ca = Main.customerAccount;
        String selectedAccountID = openFinancialAccountsComboBox.getValue();

        Object selectedFinancialAccountObject = DataEntryDriver.getFinancialAccountObjectFromFixedID(selectedAccountID);

        if(selectedFinancialAccountObject!=null){
            if(selectedFinancialAccountObject instanceof CheckingAccount){
                tempLabel.setText("Checking Account");
                CheckingAccount checkingAccount = ca.getCheckingAccount();
                double checkingBal = DataEntryDriver.round(checkingAccount.getAccountBalance(),2);
                openFinancialAccountBalanceField.setText(DataEntryDriver.getStringFromDouble(checkingBal));
                openFinancialAccountsCloseAccountButton.setDisable(false);
            }
            if(selectedFinancialAccountObject instanceof SavingsAccount){
                SavingsAccount savingsAccount = (SavingsAccount) selectedFinancialAccountObject;
                if(savingsAccount.isCdAccount()){
                    tempLabel.setText("CD Savings");
                    double cdBal = DataEntryDriver.round(savingsAccount.getCurrentCDValue(),2);
                    openFinancialAccountBalanceField.setText(DataEntryDriver.getStringFromDouble(cdBal));
                }else{
                    tempLabel.setText("Simple Savings");
                    double simpleSavingBal = DataEntryDriver.round(ca.getSimpleSavingsAccount().getAccountBalance(),2);
                    openFinancialAccountBalanceField.setText(DataEntryDriver.getStringFromDouble(simpleSavingBal));
                }
                openFinancialAccountsCloseAccountButton.setDisable(false);

            }
            if(selectedFinancialAccountObject instanceof LoanAccount){
                LoanAccount loanAccount = (LoanAccount) selectedFinancialAccountObject;
                tempLabel.setText(loanAccount.getTypeFullName());

                double loanBal = DataEntryDriver.round(loanAccount.getCurrentBalance(),2);
                openFinancialAccountBalanceField.setText(DataEntryDriver.getStringFromDouble(loanBal));
                openFinancialAccountsCloseAccountButton.setDisable(false);

            }
        }




    }

    // makes sure that check number doesn't already exist
    public void checkNumberFieldEvent(){
        CustomerAccount ca = Main.customerAccount;
        ArrayList<Check> customerChecks = ca.getChecks();
        String currentCheckNumber = customerInterAtmCheckNum.getText();

        boolean disableButton = false;

        boolean checkNumEmpty = false;
        boolean checkAmtEmpty = false;
        if(customerInterAtmCheckNum.getText().length()==0){
            checkNumEmpty = true;
        }
        if(customerInterAtmCheckAmt.getText().length()==0){
            checkAmtEmpty = true;
        }


        if(ca.hasCheckingAccount()){ // has checking
            if(!checkNumEmpty){ // if something is entered in the check number field
                if(customerChecks.size()>0){
                    for(Check check:customerChecks){
                        if(currentCheckNumber.equals(check.getCheckNumber())){
                            disableButton = true;
                            break;
                        }
                    }
                }
                if(disableButton){
                    customerInterErrLabel.setText("Check Number "+currentCheckNumber+" Already exists");
                }else{
                    customerInterErrLabel.setText("");
                }
            }
        }else{ // no checking account disable button
            disableButton = true;
            customerInterErrLabel.setText("No Checking Account for Customer");
        }


        if(checkNumEmpty && checkAmtEmpty){ // if both fields empty disable button set text to nothing
            disableButton = true;
            customerInterErrLabel.setText("");
        }

        if(!checkNumEmpty && checkAmtEmpty){// something in check number but nothing in amount
            if(!disableButton){ // check number does not exist but amount is empty
                disableButton = true;
                customerInterErrLabel.setText("Please Enter an amount");
            }else{ // check number exist and amount is empty so don't set error label yet just disable button
                disableButton = true;
            }
        }

        double checkAmt = DataEntryDriver.getDoubleFromTextField(customerInterAtmCheckAmt);

        if(!checkNumEmpty){
            if(!disableButton){ // check number does not exist
                if(checkAmt<0.001){
                    disableButton = true;
                    customerInterErrLabel.setText("Enter a Valid amount");
                }else{
                    customerInterErrLabel.setText("");
                }
            }else{ // check number exists don't override the error message
                if(checkAmt<0.001){
                    disableButton = true;
                }
            }
        }

        boolean isFeeApplied = FinanceDriver.isFeeApplicable(ca);
        if(isFeeApplied){
            tempLabel.setVisible(true);
        }else{
            tempLabel.setVisible(false);
        }



        customerInterAtmDepositButton.setDisable(disableButton);



        //
    }

    // make sure they have enough money to make a withdrawal
    public void customerInterWithdrawalAmountEvent(){
        CustomerAccount ca = Main.customerAccount;
        CheckingAccount checkingAccount = new CheckingAccount();
        double checkingBalance = 0.0;


        if(ca.getAtmWithdrawalsToday()>=2){
            customerInterErrLabel.setText("Only two ATM Withdrawals allowed A Day");
            customerInterAtmWithdrawalAmt.setDisable(true);
            return;// exit method
        }


        boolean disableWithdrawalButton = false;

        if(ca.hasCheckingAccount()){
            checkingAccount = ca.getCheckingAccount();
            checkingBalance = checkingAccount.getAccountBalance();

            if(customerInterAtmWithdrawalAmt.getText().length()==0){
                disableWithdrawalButton=true;
            }else{ // something is entered in field
                double withdrawalAmount = DataEntryDriver.getDoubleFromTextField(customerInterAtmWithdrawalAmt);

                if(checkingBalance>0.0){ // not negative balance
                    if(withdrawalAmount>0.001){ // not zero
                        if(withdrawalAmount>checkingBalance){ // taking more than in account
                            disableWithdrawalButton = true;
                            customerInterErrLabel.setText("Not enough Money for Withdrawal");
                        }else{
                            customerInterErrLabel.setText("");
                        }
                    }else{ // zero is entered in withdrawal field so disable button
                        disableWithdrawalButton = true;
                        customerInterErrLabel.setText("Enter a Valid Amount.");
                    }

                }else{ // checking balance is already negative
                    disableWithdrawalButton = true;
                }
            }
        }else{ // no checking account so disable withdrawal button
            disableWithdrawalButton = true;
            customerInterErrLabel.setText("No Checking Account for Customer");
        }

        boolean isFeeApplied = FinanceDriver.isFeeApplicable(ca); // returns false if no checking account

        if(isFeeApplied){
            tempLabel.setVisible(true);
        }else{
            tempLabel.setVisible(false);
        }

        customerInterAtmWithdrawalButton.setDisable(disableWithdrawalButton);





    }


    // fires the login method if the user hits enter on the password or pin fields and it is a key release event type
    public void userLoginFieldFireLoginButtonKeyEvent(KeyEvent e){
        if(e.getEventType().getName().equals("KEY_RELEASED")){
            if(e.getCode().getName().equals("Enter")){
                loginInterfaceLoginButton();
            }
        }
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
        savingInterestRate.setText(DataEntryDriver.getStringFromDouble(interest));





        if(isCd){ // if the selected account is a CD account
            balance = DataEntryDriver.round(selectedSavingAccount.getCurrentCDValue(),2);
            // set balance to current CD value instead of init balance.

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

        startingBalance.setText(DataEntryDriver.getStringFromDouble(balance));

        int selIndex = manageSavingsAccountsList.getSelectionModel().getSelectedIndex();
        if(selIndex<0){
            manageSavingsAccSaveB.setDisable(true);
        }else{
            manageSavingsAccSaveB.setDisable(false);
        }


        double disabledOpacity = 0.60;
        if(Main.loggedInEmployee.getType().equals("T")){
            // disable all values because teller can see all data but not change cd accounts in any way.
            startingBalance.setDisable(true);
            startingBalance.setOpacity(disabledOpacity);
            savingInterestRate.setDisable(true);
            savingInterestRate.setOpacity(disabledOpacity);
            savingCdCloseDatePicker.setDisable(true);
            savingCdCloseDatePicker.setOpacity(disabledOpacity);
            tempBalance.setDisable(true);
            tempBalance.setOpacity(disabledOpacity);
            manageSavingsAccSaveB.setDisable(true);
        }



    }

    public void manageSavingsAccountCDWithdrawalKeyEvent(KeyEvent e){
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


            if(balanceAfterFeeAndWithdrawal>=0.0){ // enough in cd to make withdrawal
                String message = String.format("This would Bring your CD account to $%.2f including a fee of $%.2f.",
                        balanceAfterFeeAndWithdrawal,feeForWithdrawalNow);
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
        int selIndex = manageLoanAccountsList.getSelectionModel().getSelectedIndex();

        if(selIndex!=-1){
            manageLoanMakePaymentCheckBox.setDisable(false);
        }


        String selectedFixedId = manageLoanAccountsList.getSelectionModel().getSelectedItem();
        LoanAccount selectedLoanAccount = customerAccount.getLoanAccountByFixedID(selectedFixedId);


        String loanAccountType = selectedLoanAccount.getLoanAccountType(); // short name

        double balance = selectedLoanAccount.getCurrentBalance();
        double interest = selectedLoanAccount.getInterestRate()*100.00;

        String loanAccountTypeFullName = DataEntryDriver.getLoanFullTypeNameFromAbb(loanAccountType);

        loanAccountTypeComboBox.getSelectionModel().select(loanAccountTypeFullName);




        double monthlyPayment = selectedLoanAccount.getAmountDue();
        startingBalance.setText(DataEntryDriver.getStringFromDouble(balance));
        loanInterestRate.setText(DataEntryDriver.getStringFromDouble(interest));

        if(loanAccountType.equals("LTL") || loanAccountType.equals("STL")){
            loanTermYears.setText("");
            String paymentString = DataEntryDriver.getStringFromDouble(monthlyPayment);
            loanTermYears.setText(paymentString);
        }else{ // else its a credit card make the label a payment due amount
            loanTermLabel.setText("Payment Due:");
            double creditPmtDue = DataEntryDriver.round(selectedLoanAccount.getAmountDue(),2);
            loanTermYears.setText(DataEntryDriver.getStringFromDouble(creditPmtDue));

        }


        if(selIndex>=0){
            manageLoanAccSaveB.setDisable(false);
        }else{
            manageLoanAccSaveB.setDisable(true);
        }


        // make sure only tellers can make a payment on a loan and not directly edit data

        if(Main.loggedInEmployee.getType().equals("T")){
            manageLoanMakePaymentCheckBox.setSelected(true);
            manageLoanMakePaymentCheckBox.setDisable(true);
        }


    }

    public void manageLoanAccountsMakePaymentCheckBoxEvent(){
        if(manageLoanMakePaymentCheckBox.isSelected()){ // making a payment
            manageLoanAccountTypeEvent();// to reset the values to what they were
            manageLoanPaymentAmount.setDisable(false);
            startingBalance.setDisable(true);
            loanInterestRate.setDisable(true);
            loanTermYears.setDisable(true);
            manageLoanAccSaveB.setText("Payment");
        }else{ // not making a payment
            startingBalance.setDisable(false);
            loanInterestRate.setDisable(false);
            loanTermYears.setDisable(false);
            manageLoanPaymentAmount.setDisable(true);
            manageLoanAccSaveB.setText("Save");
            manageLoanAccountTypeEvent();// to reset the values to what they were
        }
    }

    public void manageLoanAccountPaymentAmountEvent(){
        CustomerAccount customerAccount = Main.customerAccount;
        String selectedFixedId = manageLoanAccountsList.getSelectionModel().getSelectedItem();
        LoanAccount selectedLoanAccount = customerAccount.getLoanAccountByFixedID(selectedFixedId);
        double loanBalance = DataEntryDriver.round(selectedLoanAccount.getCurrentBalance(),2);
        double paymentAmt = DataEntryDriver.getDoubleFromTextField(manageLoanPaymentAmount);

        if(paymentAmt<=loanBalance){ // paying under the balance or up to it
            if(paymentAmt<0.001){ // zero
                manageLoanAccSaveB.setDisable(true);
                errLabel.setText("Please Enter an amount to Pay.");
            }else{ // not zero
                manageLoanAccSaveB.setDisable(false);
                errLabel.setText("");
            }
        }else{ // trying to pay off more than loan balance
            manageLoanAccSaveB.setDisable(true);
            errLabel.setText("Please enter an amount under current Balance");
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

        if(manageExistingTransferFunds.isSelected()){ // if box is selected

            if(manageExistingCheckingAccount.isSelected()){
                manageExistingCheckingLabel.setText("From: ");
                manageExistingSavingsLabel.setText("To: ");
                transferFundsKeyEvent();
            }else{
                manageExistingSavingsLabel.setText("From: ");
                manageExistingCheckingLabel.setText("To: ");
                transferFundsKeyEvent();
            }



        }else{// if box was not selected
            manageExistingCheckingLabel.setText("");
            manageExistingSavingsLabel.setText("");
            transferFundsKeyEvent();
        }

    }


    public void transferFundsCheckBoxEvent(){
        if(!manageExistingTransferFunds.isDisabled()){
            if(manageExistingTransferFunds.isSelected()){
                manageExistingFundsTransferAmount.setText("");
                displayDataRadioButtonEvent();
            }else{
                manageExistingFundsTransferAmount.setText("");
                manageExistingCheckingLabel.setText("");
                manageExistingSavingsLabel.setText("");
                transferFundsKeyEvent();
            }
        }

    }


    public void transferFundsKeyEvent(){
        manageExistingDebitCreditAccountButton.setDisable(true);

        if(manageExistingTransferFunds.isSelected()){
            String transferString = manageExistingFundsTransferAmount.getText();
            transferString = transferString.replaceAll("-",""); // can't just set a new listener in an easy way so just hack the - sign off
            manageExistingFundsTransferAmount.setText(transferString);
            manageExistingFundsTransferAmount.positionCaret(transferString.length());
        }


        if(manageExistingFundsTransferAmount.getText().length()<1){
            manageExistingDebitCreditAccountButton.setDisable(true);
            manageDispDataErrLabel.setText("");
        }else{
            // hand it the label as well.
            boolean isValid = FinanceDriver.isTransferAmtValid(manageExistingFundsTransferAmount, manageExistingTransferFunds,
                    manageExistingCheckingAccount, manageExistingSavingsAccount,manageDispDataErrLabel);

            // need to know transfer amt, if transfer is checked, to and from
            // if not checked then debit / credit to account

            // set to value from method in FinanceDriver
            manageExistingDebitCreditAccountButton.setDisable(!isValid); // !isValid because is valid returns True for
            // meaning it is valid and we want to set disable to false if isValid is true
        }
    }

    public void completeTransaction(){ // ONLY USE THIS METHOD FOR A TELLER OR MANAGER ACCOUNT because I can refresh those
        FinanceDriver.completeTransaction(manageExistingFundsTransferAmount, manageExistingTransferFunds, manageExistingCheckingAccount,
                manageExistingSavingsAccount,manageDispDataErrLabel);
        manageExistingFundsTransferAmount.setText("");
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        tellerManageDispData();
        transferFundsKeyEvent();

    }



    public void enterKeyDefaultEvent(KeyEvent e){
        // This allows us to reuse this for key release events on all Nodes to fire the focused node on action event.
        if(Main.activeStage==null){
            return;
        }else{ // if Main.activeStage is not null
            // add to use currently active stage
            Stage activeStage = Main.activeStage;

            if(e.getEventType().getName().equals("KEY_RELEASED")){// only run when releasing the enter key
                if(e.getCode().getName().equals("Enter")){
                    if(activeStage.getScene().getFocusOwner() instanceof Button){
                        Button fireButton = (Button) activeStage.getScene().focusOwnerProperty().get();
                        if(!fireButton.isDisabled()){
                            fireButton.fire();
                        }
                    }
                }
            }


        }
    }



    public void addFinanceAccountEvent(){
        // going to use if statements for all the validation on the add accounts
        // mainly to disable the add button unless it's valid.
        //sort and figure out what scene is active and run validation on that

        if(activeFXMLFileName.equals("AddChecking.fxml")){
            double bal = DataEntryDriver.getDoubleFromTextField(startingBalance);
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


    @FXML
    public void ssnFieldKeyEvent(KeyEvent e){ // a generic and multi use method to validate any ssn textfield hopefully
        EventType<KeyEvent> keyEventType = e.getEventType();
        String keyCodeName = e.getCode().getName();
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
        EventType<KeyEvent> keyEventType = e.getEventType();
        String keyCodeName = e.getCode().getName();
        String keyEventTypeName = keyEventType.getName();

        String ssn = DataEntryDriver.stripSSN(manageUserSSNField.getText());
        manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

        if(keyCodeName.equals("Enter")){ // allows enter button to fire main event
            if(keyEventTypeName.equals("KEY_RELEASED")){ // only on enter key release
                System.out.println("Enter button key release");
                if(!manageUserLookupButton.isDisabled()){
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
            lookupInterErrLabel.setText("Please Enter a Valid 9 digit SSN number");
        }else{ // if ssn is 9 digits
            if(!DataEntryDriver.ssnInDatabase(ssn)) {
                lookupInterErrLabel.setText("The SSN you Entered Is not In the Database.\nGo to Add new Customer or enter" +
                        " a valid 9 Digit SSN number.");
            }else{
                CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(manageUserSSNField.getText());
                lookupInterErrLabel.setText("Found a Matching account with Last Name: "+ca.getLastName());
                manageUserLookupButton.setDisable(!DataEntryDriver.ssnValidAndInDatabase(ssn));

            }
        }

    }


    public void deleteAccountButton(){
        CustomerAccount ca = Main.customerAccount;
        boolean hasChecking = ca.hasCheckingAccount();
        boolean hasSimpleSavings = ca.hasSimpleSavings();
        boolean hasSavingCd = ca.hasCDSavings();
        boolean hasLoan = ca.hasLoanAccount();

        CheckingAccount checkingAccount = null;
        SavingsAccount simpleSaving = null;
        ArrayList<SavingsAccount> savingCDAccounts = new ArrayList<>();
        ArrayList<LoanAccount> loanAccounts = new ArrayList<>();

        boolean displayWarningScreen = false;

        if(hasChecking || hasSavingCd || hasSimpleSavings ||hasLoan){
            if(hasChecking){
                checkingAccount = ca.getCheckingAccount();
                if(checkingAccount.getAccountBalance()<0.001 || checkingAccount.getAccountBalance()>0.001){
                    displayWarningScreen = true;
                }
            }
            if(hasSimpleSavings){
                simpleSaving = ca.getSimpleSavingsAccount();
                if(simpleSaving.getAccountBalance()>0.001){
                    displayWarningScreen = true;
                }
            }
            if(hasSavingCd){
                savingCDAccounts = ca.getCDSavingsAccounts();
                for(SavingsAccount sa:savingCDAccounts){
                    if(sa.getCurrentCDValue()>0.001){
                        displayWarningScreen = true;
                        break;
                    }
                }
            }
            if(hasLoan){
                loanAccounts = ca.getLoanAccounts();
                for(LoanAccount la:loanAccounts){
                    if(la.getCurrentBalance()>0.001){
                        displayWarningScreen = true;
                        break;
                    }
                }
            }
        }


        if(displayWarningScreen){
            // display the warning screen
            loadDeleteCustomerAccountWarningWindow();
        }else{

            // remove all individual accounts and log
            DataEntryDriver.removeCustomerAccount(Main.customerAccount.getCustID()); // auto writes changes to file
            goToLoggedInEmployeeScene();
        }

    }


    public void customerInterAtmWithdrawalButton() {
        if(DataEntryDriver.getDoubleFromString(customerInterAtmWithdrawalAmt.getText()) >0.001){
            FinanceDriver.completeAtmTransaction(customerInterAtmWithdrawalAmt,"null");
            customerDispData();
            customerInterWithdrawalAmountEvent();
            checkNumberFieldEvent(); // so it disables the buttons
            customerInterAtmWithdrawalAmt.requestFocus(); // focus back to the withdrawal field
            DataEntryDriver.serializeCustomerDatabaseToFile(); // writes the changes to database
        }


    }

    public void customerInterAtmDepositButton() {
        double checkDepositAmount = DataEntryDriver.getDoubleFromString(customerInterAtmCheckAmt.getText());
        String checkNumber = customerInterAtmCheckNum.getText();

        if(checkDepositAmount >0.001 && checkNumber.length()>0){
            FinanceDriver.completeAtmTransaction(customerInterAtmCheckAmt,checkNumber);
            customerDispData();
            checkNumberFieldEvent();
            customerInterWithdrawalAmountEvent(); // to disable buttons
            customerInterAtmCheckNum.requestFocus(); // focus back on this field
            DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        }

    }



    public void customerInterCCButton(){
        try {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerCreditCard.fxml"));
        Main.primaryStage.setTitle("Customer Credit Card Interface");
        Main.primaryStage.setScene(new Scene(root, 700, 500));
        Main.primaryStage.show();
        Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mainInterfaceCustomerButton(){
        Parent root = null;
        Parent login = null;
        try {
            if(!customerPendingLogin){ // if customer login window is not already open
                if(customerLogIn){ // if method was called and customer was already logged in .
                    root = FXMLLoader.load(getClass().getResource("CustomerInterface.fxml"));
                    Main.primaryStage.setTitle("Customer Interface");
                    Main.primaryStage.setScene(new Scene(root, 700, 500));
                    Main.primaryStage.show();
                    Main.activeStage=Main.primaryStage;
                }else{// if method called and customer isn't logged in. THIS WILL ALWAYS RUN ON FIRST CALL
                    customerPendingLogin =true; // set pending login to true because the login interface is launched
                    tellerLogIn=false;
                    managerLogIn=false;// log out the teller and manager accounts if logged in
                    Main.loggedInEmployee=null; // make logged in employee null so that they have to log in again.
                    login = FXMLLoader.load(getClass().getResource("CustomerLogin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Customer Login");
                    stage.setScene(new Scene(login,382,420));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    stage.setOnCloseRequest(event -> loginInterfaceExitButton());

                    Main.activeStage=stage;
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
        Parent root = null;
        Parent login = null;
        try {
            if(!tellerPendingLogin){ // if login window is not already active
                if(tellerLogIn){// if teller is logged in after login window closes and it recalls this method
                    managerLogIn = false; // log out the manager
                    root = FXMLLoader.load(getClass().getResource("TellerInterface.fxml"));
                    Main.primaryStage.setTitle("Teller Interface");
                    Main.primaryStage.setScene(new Scene(root,700,500));
                    Main.primaryStage.show();
                    Main.activeStage=Main.primaryStage;
                }else{
                    tellerPendingLogin=true;
                    managerLogIn=false; // log out the manager
                    Main.loggedInEmployee=null; // null the employee logged in so they they can't resume their session
                    login = FXMLLoader.load(getClass().getResource("TellerLogin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Teller Login");
                    stage.setScene(new Scene(login,382,420));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    stage.setOnCloseRequest(event -> loginInterfaceExitButton());// set to exit buton if user clicks x on window
                    Main.activeStage = stage;
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
            if(!managerPendingLogin){ // if manager login window is not already open and pending
                if(managerLogIn){ // if manager is still logged in
                    tellerLogIn=false; // if log in as Manager log out the teller
                    managerPendingLogin = false; // make sure pending is false
                    root = FXMLLoader.load(getClass().getResource("BankManagerInterface.fxml"));
                    Main.primaryStage.setTitle("Bank Manager Interface");
                    Main.primaryStage.setScene(new Scene(root, 700, 500));
                    Main.primaryStage.show();
                    Main.activeStage=Main.primaryStage;
                }else{
                    managerPendingLogin =true;
                    Main.loggedInEmployee=null; // null any logged in employee
                    tellerLogIn=false; // log out the teller if attempting a login as a manager
                    login = FXMLLoader.load(getClass().getResource("ManagerLogin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Bank Manager Login");
                    stage.setScene(new Scene(login,382,420));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                    stage.setOnCloseRequest(event -> loginInterfaceExitButton());

                    Main.activeStage=stage;
                }
            }else{
                System.out.println("Login is pending");
            }


        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }


    @FXML
    public void loginInterfaceLoginButton(){
        if(tellerPendingLogin){ // if pending but not complete login
            tellerLogIn = validateLoginCreds("Teller"); // attempt login of teller and see if true
            if(tellerLogIn){ // if login info was valid
                tellerPendingLogin=false;
                EmployeeAccount employee = new EmployeeAccount(loginInterUser.getText());
                employee.setType("T");
                String pass = loginInterPass.getText();
                employee.setPassword(pass);
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
                String pass = loginInterPass.getText();
                employee.setType("M");
                employee.setPassword(pass);
                Main.loggedInEmployee = employee;
                Main.outEmployee.println(Main.getDateTimeString()+"Manager Account UserName: "+Main.loggedInEmployee.getUserName()+
                " logged into account.");
                closeWindow();
                mainInterfaceManagerButton();
            }

        }

        if(customerPendingLogin){ // customer pending login is true
            managerLogIn=false;
            tellerLogIn=false;
            customerLogIn = validateLoginCreds("Customer"); // validate and determine if they are logged in or not
            // also sets Main.customerAccount which is what is used to display their data

            if(customerLogIn){ // if validate was true and they are now logged in then call the main login button again
                customerPendingLogin=false;
                String userName = Main.customerAccount.getCustID();
                Main.outEmployee.println(Main.getDateTimeString()+"Customer Account UserName: "+userName+" Customer logged into account.");


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
        customerLogIn=false;
        tellerPendingLogin=false;
        managerPendingLogin=false;
        customerPendingLogin=false;
        Main.loggedInEmployee=null; // no logged in employee

        //Stage stage = (Stage) loginInterExitButton.getScene().getWindow();
        Stage stage = Main.activeStage;
        stage.close();
        goToMainScene();
    }

    public void logOutAllAccounts(){
        tellerLogIn = false;
        managerLogIn = false;
        customerLogIn = false;
        tellerPendingLogin=false;
        managerPendingLogin=false;
        customerPendingLogin = false;
        Main.loggedInEmployee = null;
        Main.customerAccount = null; // null everyone
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
        String loggedInEmployeeType = Main.loggedInEmployee.getType();

        manageDispDataSSN.setText(DataEntryDriver.fixSSN(Main.customerAccount.getCustID()));
        manageDispDataFirst.setText(ca.getFirstName());
        manageDispDataLast.setText(ca.getLastName());
        manageDispDataStreetAddr.setText(ca.getStreetAddr());
        manageDispDataCity.setText(ca.getCity());
        manageDispDataState.setText(ca.getState());
        manageDispDataZip.setText(ca.getZip());
        manageDispDataErrLabel.setText("");

        if(manageExistingCheckingAccount.isSelected() && manageExistingCheckingAccount != null){
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
                manageExistingCheckingAccount.setDisable(true);
                manageDispDataAcctBalance.setText("");
                manageDispDataAcctType.setText("");
                manageDispDataAcctStatus.setText("No account for user");
            }
        }else if(manageExistingSavingsAccount.isSelected() && manageExistingSavingsAccount!=null){
            if(ca.hasSimpleSavings()){
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
                manageExistingSavingsAccount.setDisable(true);
                manageDispDataAcctBalance.setText("");
                manageDispDataAcctStatus.setText("No Account for user");
                manageDispDataAcctType.setText("");
            }
        }else{
            manageDispDataAcctBalance.setText("");
            manageDispDataAcctStatus.setText("No account for user");
        }
    }

    @FXML
    public void tellerInterfaceAddNewButton(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
            Main.primaryStage.setTitle("Add a new user Account");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void tellerInterfaceManageButton(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUser.fxml"));
            Main.primaryStage.setTitle("Manage existing user");
            Main.primaryStage.setScene(new Scene(root, 700, 500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }

    @FXML
    public void tellerInterfaceManageLookupButton(){
        String ssn = manageUserSSNField.getText();
        String ssnStripped = DataEntryDriver.stripSSN(ssn);
        CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(ssnStripped);
        // pass current customer account back to main
        Main.customerAccount = ca;
        Main.currentCustomerID= ssnStripped;

        Main.outEmployee.println(Main.getDateTimeString()+"Employee UserName: "+Main.loggedInEmployee.getUserName()
                +" Looked Up account: "+ ca.toString());

        // NOTE SSN IS STATICALLY SET IN THE INITIALIZE METHOD ON LINE NUMBER 153


        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayData.fxml"));

            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }


    @FXML
    public void tellerInterfaceManageUpdateDataButton(){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserUpdateData.fxml"));
            Main.primaryStage.setTitle("Update Customer Data");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;

        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }

    @FXML
    public void tellerInterfaceUpdateDataPreviousButton(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayData.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
        } catch (Exception e) {
            e.printStackTrace();
            Main.activeStage=null;
        }

    }

    @FXML
    public void tellerInterfaceUpdateDataSaveButton(){
        Parent root = null;
        String ssn = Main.currentCustomerID;
        CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerID(ssn);

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
        DataEntryDriver.updateCustomerAccount(ca,ssn); // auto writes changes


        try {
            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayData.fxml"));
            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;
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
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        goToAddFinanceAcc(); // then go back to last screen.
    }


    @FXML
    public void addSavingsAccountSaveButton(){
        CustomerAccount customerAccount=Main.customerAccount;
        boolean isCdAccount = cdCheckBox.isSelected();
        double startingBalanceDouble = DataEntryDriver.getDoubleFromTextField(startingBalance);
        double interestRate = DataEntryDriver.getDoubleFromTextField(savingInterestRate);
        int termInYears=-1;

        if(cdCheckBox.isSelected()){ // cd box selected
            termInYears= DataEntryDriver.getIntFromTextField(savingCDTerm);
        }
        // convert interest rate to decimal.
        interestRate = interestRate/100.00;
        SavingsAccount newSavings = new SavingsAccount(customerAccount,isCdAccount,startingBalanceDouble,interestRate,termInYears);
        customerAccount.addSavingsAccount(newSavings);
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        goToAddFinanceAcc();


    }

    @FXML
    public void addLoanAccountSaveButton(){
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
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        goToAddFinanceAcc();
    }



    public void manageCheckingAccountSaveButton(){
        // value set to 30,000,000 bucks
        CustomerAccount customerAccount = Main.customerAccount;
        boolean goldSelected = goldCheckBox.isSelected();
        boolean backupSelected = backupSavingsCheckBox.isSelected();
        double balance = DataEntryDriver.getDoubleFromTextField(startingBalance);

        CheckingAccount ca = customerAccount.getCheckingAccount();
        ca.setGoldAccount(goldSelected);
        ca.setBackupSavingsEnabled(backupSelected);
        ca.setAccountBalance(balance);
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        goToManageFinanceAcc();
    }

    public void manageSavingsAccountsSaveButton(){

        CustomerAccount customerAccount = Main.customerAccount;
        SavingsAccount savingsAccount = customerAccount.getSavingsAccountByFixedID(
                manageSavingsAccountsList.getSelectionModel().getSelectedItem());
        double balance = DataEntryDriver.round(DataEntryDriver.getDoubleFromTextField(startingBalance),2);
        double interest = DataEntryDriver.round(DataEntryDriver.getDoubleFromTextField(savingInterestRate)/100.0,5); // 5 decimal places

        // going to try a hack here.
        // instead of checking the cd values I've set the text to withdrawal on the type events

        if(manageSavingsAccSaveB.getText().equals("Save")){ // we are just changing data not making a cd withdrawal
            String closeDate = "null";
            if(savingsAccount.isCdAccount()){
                String closeDateString = DataEntryDriver.getStringFromLocalDateFormatted(savingCdCloseDatePicker.getValue());
                closeDate= closeDateString; // should be fixed already.
                savingsAccount.setCdCloseDate(closeDate);
            }

            savingsAccount.setAccountBalance(balance);
            savingsAccount.setInterestRate(interest);
            goToManageFinanceAcc();
        }else{ // we are making a withdrawal and should only get here if it is already a cd account
            // data should be validated already
            // figue current value figure fee take that minus the withdrawal amount
            double withdrawalAmt = DataEntryDriver.getDoubleFromTextField(tempBalance);
            FinanceDriver.debitSavingsCDAccount(customerAccount,savingsAccount,withdrawalAmt,false,"Saving CD Withdrawal");
            DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
            goToManageFinanceAcc();

        }
    }

    public void manageLoanAccountsSaveButton(){ // also the manage loan make payment method
        CustomerAccount ca = Main.customerAccount;
        String loanAccountFixedID = manageLoanAccountsList.getSelectionModel().getSelectedItem();
        LoanAccount selectedLoanAccount = ca.getLoanAccountByFixedID(loanAccountFixedID);
        String loanTypeShort = DataEntryDriver.getLoanTypeAbbFromFullName(loanAccountTypeComboBox.getSelectionModel().getSelectedItem());
        double balance = DataEntryDriver.getDoubleFromTextField(startingBalance);
        double interestRate = DataEntryDriver.getDoubleFromTextField(loanInterestRate)/100.0;


        if(manageLoanMakePaymentCheckBox.isSelected()){ // making a payment
            double paymentAmt = DataEntryDriver.getDoubleFromTextField(manageLoanPaymentAmount);
            // now make the payment
            FinanceDriver.creditDebitLoanAccount(selectedLoanAccount,paymentAmt,"");
            manageLoanMakePaymentCheckBox.setSelected(false);
            manageLoanPaymentAmount.setText("");

            // now check to see if that payment paid off the loan if so close the loan account and return to previous screen
            double newCurrentBal = DataEntryDriver.round(selectedLoanAccount.getCurrentBalance(),2);

            if(newCurrentBal<0.0001){ // PAID OFF LOAN
                if(!selectedLoanAccount.getLoanAccountType().equals("CCL")){
                    int index = ca.getLoanAccountIndexByFixedID(loanAccountFixedID);
                    ca.deleteLoanAccountByIndex(index); // delete the loan account
                    DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
                    goToDisplayDataScene(); // return to the display screen
                }else{
                    manageLoanAccountTypeEvent();
                }
            }else{
                manageLoanAccountTypeEvent();
            }
        }else{ // updating the data
            String loanTermString = "";
            if(loanTypeShort.equals("LTL") || loanTypeShort.equals("STL")){
                loanTermString = DataEntryDriver.fixDateString(loanTermYears.getText());
            }
            selectedLoanAccount.setLoanAccountType(loanTypeShort);
            selectedLoanAccount.setCurrentBalance(balance);
            selectedLoanAccount.setInterestRate(interestRate);
            selectedLoanAccount.setLoanTerm(loanTermString);
            DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
            manageLoanAccountTypeEvent();
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

    public void deleteFinancialAccountsButton(){
        CustomerAccount customerAccount=Main.customerAccount;
        if(deleteAllCheckingAcctCheckBox.isSelected()){
            customerAccount.deleteCheckingAccount();
        }
        if(deleteAllSavingsAcctCheckBox.isSelected()){
            customerAccount.deleteAllSavingsAccounts();
        }
        if(deleteAllLoanAcctCheckBox.isSelected()){
            customerAccount.deleteLoanAccounts();
        }
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
    }

    //CUSTOMER INTERFACE METHODS

    public void customerDispData(){
        CustomerAccount ca = Main.customerAccount;
        String balanceFormatted = "null";
        if(ca.hasCheckingAccount()){
            balanceFormatted = DataEntryDriver.formatAccountBalance(ca.getCheckingAccount().getAccountBalance());
        }

        customerDispDataFirst.setText(ca.getFirstName());
        customerDispDataLast.setText(ca.getLastName());
        customerDispDataAccountBalance.setText(balanceFormatted);
        customerInterAtmWithdrawalAmt.setText("");
        customerInterAtmCheckNum.setText("");
        customerInterAtmCheckAmt.setText("");
    }

    public void customerAtmDispData(){
        CustomerAccount ca = Main.customerAccount; // just use the one that is already made for a customer account
        LoanAccount creditCardLoanAccount = ca.getCreditCardLoanAccount();

        if(creditCardLoanAccount==null){
            return;
        }

        customerDispDataFirst.setText(ca.getFirstName());
        customerDispDataLast.setText(ca.getLastName());
        String creditBalanceString = String.format("$%.2f",creditCardLoanAccount.getCurrentBalance());
        String creditLimitString = String.format("$%.2f",creditCardLoanAccount.getInitialLoanAmt());
        double creditRemainingAmount = creditCardLoanAccount.getInitialLoanAmt() - creditCardLoanAccount.getCurrentBalance();
        String creditRemainingString = String.format("$%.2f",creditRemainingAmount);

        String paymentDueString = String.format("$%.2f",creditCardLoanAccount.getAmountDue());
        String paymentDueDateString = creditCardLoanAccount.getPaymentDueDate();

        creditBalance.setText(creditBalanceString);
        creditLimit.setText(creditLimitString);
        creditRemaining.setText(creditRemainingString);
        creditPaymentDue.setText(paymentDueString);
        creditPaymentDueDate.setText(paymentDueDateString);



        //customerInterCCNum.setText("1234567789008"); //Autofill with actual CC number
        customerInterCCNum.setText(ca.getAtmCardNumber()); // auto fills the actual cc number

        customerInterCCHistory.setText("");
        customerInterCCHistory.appendText("Credit Card Transactions:" + "\n");

        ArrayList<Transaction> allCustomerTransactions = ca.getTransactions();
        for(Transaction transaction: allCustomerTransactions){ // loop through all transactions
            // and find only the ones that are the Credit Card Transactions using Transaction Account
            if(transaction.getTransactionAccount().equals("CCL")){ // if the transaction was on a Credit Card Loan
                customerInterCCHistory.appendText("\n"+transaction.toStringPrettyPrintFormattedForInterface());
            }

        }

    }

    public void processCCPurchase(){
        double transactionAmt = DataEntryDriver.getDoubleFromTextField(customerInterCCPurchaseAmt);
        String purchaseDesc = customerInterCCpurchaseDesc.getText();
        LoanAccount creditCardLoanAccount = Main.customerAccount.getCreditCardLoanAccount();

        if(customerInterCCMakePaymentCheckBox.isSelected()){ // making a payment so make amount positive with validation
            // it is always positive
            FinanceDriver.creditDebitLoanAccount(creditCardLoanAccount,transactionAmt,"Credit Card Payment");
            DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        }else{ // making a purchase so make amount negative
            double debitAmt = 0.0-transactionAmt;
            if(purchaseDesc.length()>0){
                FinanceDriver.creditDebitLoanAccount(creditCardLoanAccount,debitAmt,purchaseDesc);
            }else{
                FinanceDriver.creditDebitLoanAccount(creditCardLoanAccount,debitAmt,"Credit Card Purchase");
            }
            DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        }

        customerInterCCPurchaseAmt.setText("");
        customerInterCCpurchaseDesc.setText("");

        creditCardAmountEvent();
        customerAtmDispData(); // refresh the customer interface

    }


    public void creditCardAmountEvent(){
        // disable button if amount is 0.00 or if they don't have enough money for the purchase
        CustomerAccount ca = Main.customerAccount;
        LoanAccount creditCardAccount = ca.getCreditCardLoanAccount(); // should not return null if validation to get here works
        double transactionAmt = DataEntryDriver.getDoubleFromTextField(customerInterCCPurchaseAmt);
        double remainingCredit = creditCardAccount.getInitialLoanAmt() - creditCardAccount.getCurrentBalance(); // remaining credit
        remainingCredit = DataEntryDriver.round(remainingCredit);
        double currentCCBalance = DataEntryDriver.round(creditCardAccount.getCurrentBalance(),2);



        if(!customerInterCCMakePaymentCheckBox.isSelected()){ // making a purchase

            if(remainingCredit>0.0){ // make sure account is not over limit
                if(transactionAmt<0.001){ // don't process a 0.00 debit
                    customerCCPurchaseButton.setDisable(true);
                    customerInterErrLabel.setText("Please Enter an amount");
                }else{ // amount > 0.00
                    if(transactionAmt<=remainingCredit){ // make sure purchase is not more than remaining credit
                        customerCCPurchaseButton.setDisable(false);
                        customerInterErrLabel.setText("");
                    }else{ // purchase is greater than remaining credit
                        customerCCPurchaseButton.setDisable(true);
                        customerInterErrLabel.setText("Purchase amount is greater than Credit Limit");
                    }
                }

            }else{ // account is over limit
                customerCCPurchaseButton.setDisable(true);
                customerInterErrLabel.setText("Account is over Credit Limit");
            }
        }else{ // making a payment
            if(transactionAmt>0.001){
                if(transactionAmt<=currentCCBalance){ // less than or equal to current credit balance
                    customerCCPurchaseButton.setDisable(false);
                    String message = String.format("Paying $%.2f on Credit Card Account.",transactionAmt);
                    customerInterErrLabel.setText(message);
                }else {
                    customerCCPurchaseButton.setDisable(true);
                    customerInterErrLabel.setText("Please Enter amount less than or equal to credit balance.");
                }


            }else{
                customerCCPurchaseButton.setDisable(true);
                customerInterErrLabel.setText("");
            }
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

        if(Main.activeStage.getTitle().contains("new")){
            interfaceID = "add";
            fName = fNameTextField.getText();
            lName = lNameTextField.getText();
            ssn = socialSecTextField.getText();
            streetAddress = streetAddressTextField.getText();
            city = cityTextField.getText();
            zipCode = zipCodeTextField.getText();
        }
        if(Main.activeStage.getTitle().contains("Update")){
            interfaceID = "update";
            fName = updateDataFirstName.getText();
            lName = updateDataLastName.getText();
            ssn = Main.customerAccount.getCustID();
            streetAddress = updateDataAddress.getText();
            city = updateDataCity.getText();
            zipCode = updateDataZip.getText();
        }

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

        if(userType == "Teller"){
            if(loginInterUser.getText().matches("[a-zA-Z0-9]*")) {
                // here we would validate the credintials but They're always good for now

                if(loginInterPass.getText().equals("Teller")){
                    // here we would validate the password for the user
                    returnVal=true;
                    Main.out.println(Main.getDateTimeString()+ "Teller " + loginInterUser.getText() + " accessed the system ----------");
                }
            }else{
                System.out.println("not a valid username for a Teller Account");
            }
        }
        if(userType == "Manager"){
            // verify the credentials of the Manager account
            if(loginInterUser.getText().matches("[a-zA-Z0-9]*")){
                if(loginInterPass.getText().equals("Manager")){
                    returnVal=true;
                    Main.out.println(Main.getDateTimeString()+ "Manager " + loginInterUser.getText() + " accessed the system ----------");
                }
            }
        }

        if(userType == "Customer") {
           if(loginInterUser.getText().length()>0 && loginInterPass.getText().length()>0) {
               CustomerAccount ca = DataEntryDriver.getCustomerAccountFromCustomerAtmCardNum(loginInterUser.getText());
               if (!ca.isNull()) { // if search was not null
                   String caPin = ca.getPin();

                   if (loginInterPass.getText().equals(caPin)) {
                       Main.customerAccount = ca;
                       returnVal = true;
                   }

               }else{// if search was null
                   //Main.customerAccount = Main.customerAccounts.get(0);// statically set the first account
                   //returnVal = true;

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
            if(itemsArray.get(i)[2].equals("false")){// if any other field shows false
                //addNewUserInterfaceEnterButton.setDisable(true);
                Main.defaultSceneButton.setDisable(true);
                String[] falseItem = itemsArray.get(i);
                returnVal=false;

                // explain why it's false
                if(falseItem[0].equals("ssn")){// display error for invalid ssn number
                    unsuccessfulEntryLabel.setText("Please enter a Valid 9 digit SSN!");
                    //addNewUserInterfaceEnterButton.setDisable(true);
                    Main.defaultSceneButton.setDisable(true);
                }
                if(falseItem[0].equals("zip")){
                    unsuccessfulEntryLabel.setText("Please enter a 5 digit Zip");
                    //addNewUserInterfaceEnterButton.setDisable(true);
                    Main.defaultSceneButton.setDisable(true);
                }
                if(falseItem[0].equals("state")){
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
        updateDataZip.setText(Main.customerAccount.getZip());
    }




    // end general block


    public void stopCheckPaymentAction(){
        CustomerAccount ca = Main.customerAccount;
        Check selectedCheck = unprocessedChecksComboBox.getValue();

        FinanceDriver.applyFeeOnAccount(ca,"stop"); // puts stop payment on account
        selectedCheck.stopCheckPayment(); // stop the payment
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
        closeStage(Main.activeStage);
        goToDisplayDataScene();

    }

    public void closeFinancialAccountAction() throws NullPointerException{
        CustomerAccount ca = Main.customerAccount;
        String selectedFinancialAccountID = openFinancialAccountsComboBox.getValue();

        Object financialAccount = DataEntryDriver.getFinancialAccountObjectFromFixedID(selectedFinancialAccountID);

        if(financialAccount instanceof CheckingAccount){
            CheckingAccount checkingAccount = (CheckingAccount) financialAccount;
            double checkingBalance = checkingAccount.getAccountBalance();
            double amtToClose = 0.0;
            amtToClose = 0.0 - checkingBalance; // 0-50 = -50 for positive bal . or 0.0- (-50) for +50 to close negative balance

            FinanceDriver.creditDebitCheckingAccount(checkingAccount,amtToClose,"Closing Account");

            Main.printToEmployeeLogFile(" Closed Checking Account: "+checkingAccount.toString());
            ca.deleteCheckingAccount();

        }
        if(financialAccount instanceof SavingsAccount){
            SavingsAccount savingsAccount = (SavingsAccount) financialAccount;
            double closeBalance = 0.0;
            if(savingsAccount.isCdAccount()){
                FinanceDriver.debitSavingsCDAccount(ca,savingsAccount,0.00,true,"Closing CD Account");
            }else{
                closeBalance = 0.0- savingsAccount.getAccountBalance();
                FinanceDriver.creditDebitSavingsAccount(ca,savingsAccount,closeBalance,"Closing Account");
            }
            Main.printToEmployeeLogFile(" Closed Savings Account: "+savingsAccount.toString());
            ca.deleteSavingsAccountObject(savingsAccount);

        }
        if(financialAccount instanceof LoanAccount){
            LoanAccount loanAccount = (LoanAccount) financialAccount;
            double closeAmount = 0.0;
            if(!loanAccount.getLoanAccountType().equals("CCL")){
                // close loan accounts not a credit card
                closeAmount = 0.0-loanAccount.getCurrentBalance();
                FinanceDriver.creditDebitLoanAccount(loanAccount,closeAmount,"Closing Account");

            }else{ // a ccl account so make payment on any remaining balance
                loanAccount.makePayment(loanAccount.getCurrentBalance());// pay off any balance

            }
            Main.printToEmployeeLogFile(" Closed Loan Account: "+loanAccount.toString());
            ca.deleteLoanAccountObject(loanAccount);

        }

        String idOfSelectedAcc = openFinancialAccountsComboBox.getValue();
        openFinancialAccountsComboBox.getItems().remove(idOfSelectedAcc);// remove this id


        if(openFinancialAccountsComboBox.getItems().size()!=0){ // if more accounts left to close
            openFinancialAccountsComboBox.getSelectionModel().select(0); // select the first one
            openAccountsComboBoxEvent(); // then trigger an action so it populates the balance field.
        }else{ // if upon clicking close, we now deleted the last open account
            // close window and account
            String customerID = ca.getCustID();
            Main.printToEmployeeLogFile(" DELETED CUSTOMER ACCOUNT: "+ca.toString());
            DataEntryDriver.removeCustomerAccount(customerID); // removes the account
            closeStage(Main.activeStage); // close active stage which is the warning window
            goToLoggedInEmployeeScene();// return to the logged in employee scene
        }
        DataEntryDriver.serializeCustomerDatabaseToFile();// writes changes
    }

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

    public void closeDeleteCustomerAccountWarningWindow(){ // pressing previous or exit button
        closeStage(Main.activeStage); // close the active stage "the warning window"
        goToDisplayDataScene();
    }

    public void closeStopPaymentWindow(){
        closeStage(Main.activeStage);
        goToDisplayDataScene();
    }

    public void closeStage(Stage cStage){
        cStage.close();
        Main.activeStage = Main.primaryStage;
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
            if(Main.loggedInEmployee!=null){
                long loggedInTime = Main.loggedInEmployee.getTimeLoggedIn(); // session log in time in seconds

                if(loggedInTime<=sessionLogInTimeOutInSeconds){ // been logged in for less than 5 minutes
                    System.out.println(Main.loggedInEmployee.getUserName()+" has been logged in for  "+loggedInTime+" seconds");
                }else{
                    tellerLogIn = false; // if returning to the main screen log out all logged in accounts
                    managerLogIn = false;
                }
            }
            customerLogIn = false; // log out the customer regardless
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }

    // used to go to the main interface of the logged in employee
    @FXML
    public void goToLoggedInEmployeeScene(){
        Parent root = null;

        String loggedInEmployeeType = "null";
        if(Main.loggedInEmployee!=null){
            loggedInEmployeeType = Main.loggedInEmployee.getType();
        }

        if(loggedInEmployeeType.equals("T")){
            mainInterfaceTellerButton();
        }
        if(loggedInEmployeeType.equals("M")){
            mainInterfaceManagerButton();
        }

        if(loggedInEmployeeType.equals("null")){
            goToMainScene();
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

        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage=null;
        }
    }


    public void goToDisplayDataScene(){
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("ManageExistingUserDisplayData.fxml"));

            Main.primaryStage.setTitle("Customer Account Data Management Interface");
            Main.primaryStage.setScene(new Scene(root,700,500));
            Main.primaryStage.show();
            Main.activeStage=Main.primaryStage;

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

    @FXML
    public void loadStopCheckPaymentWindow(){
        Parent stopPaymentWindow = null;
        try {
            stopPaymentWindow = FXMLLoader.load(getClass().getResource("StopPayment.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Stop Payment");
            stage.setScene(new Scene(stopPaymentWindow,592,488));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Main.activeStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage = null;
        }
    }

    @FXML
    public void loadDeleteCustomerAccountWarningWindow(){
        Parent deleteCustomerWarning = null;
        try {
            deleteCustomerWarning = FXMLLoader.load(getClass().getResource("DeleteAccountWarning.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Delete Customer Account Warning");
            stage.setScene(new Scene(deleteCustomerWarning,592,488));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnCloseRequest(event -> closeDeleteCustomerAccountWarningWindow());
            Main.activeStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
            Main.activeStage = null;
        }
    }



    public void exportDataToCSVFiles(){
        System.out.println("Exporting all data to csv");

        //File customerBaseOut = new File("src/Resources/outputLog.txt");

        String today = DataEntryDriver.getDateString();
        String cTime = Main.getDateTimeString();
        System.out.println("today Date: "+today);
        System.out.println("current Time: "+cTime);

        String prefixString = getPrefixDateTimeForFileExport();


        File customerBaseOut = Main.createExportFile(prefixString+"CustomersBase.csv");
        File checkingAccOut = Main.createExportFile(prefixString+"CheckingAccounts.csv");
        File savingsOut = Main.createExportFile(prefixString+"SavingsAccounts.csv");
        File loanOut = Main.createExportFile(prefixString+"LoanAccounts.csv");
        File checksOut = Main.createExportFile(prefixString+"Checks.csv");
        File transactionsOut = Main.createExportFile(prefixString+"Transactions.csv");


        PrintWriter customersPW = Main.createPrintWriter(customerBaseOut,false);
        PrintWriter checkingPW = Main.createPrintWriter(checkingAccOut,false);
        PrintWriter savingsPW = Main.createPrintWriter(savingsOut,false);
        PrintWriter loanPW = Main.createPrintWriter(loanOut,false);
        PrintWriter checksPW = Main.createPrintWriter(checksOut,false);
        PrintWriter transactionsPW = Main.createPrintWriter(transactionsOut,false);


        ArrayList<CustomerAccount> customerAccounts = Main.customerAccounts;
        ArrayList<CheckingAccount> checkingAccounts = new ArrayList<>();
        ArrayList<SavingsAccount> savingsAccounts = new ArrayList<>();
        ArrayList<LoanAccount> loanAccounts = new ArrayList<>();
        ArrayList<Check> checksArr = new ArrayList<>();


        customersPW.println(CustomerAccount.toStringCSVHeader());
        for(CustomerAccount ca:customerAccounts){
            customersPW.println(ca.toStringCSV());
            if(ca.hasCheckingAccount()){
                checkingAccounts.add(ca.getCheckingAccount());
            }
            if(ca.hasSavingsAccount()){
                for(SavingsAccount sa:ca.getSavingsAccounts()){
                    savingsAccounts.add(sa);
                }
            }
            if(ca.hasLoanAccount()){
                for(LoanAccount la:ca.getLoanAccounts()){
                    loanAccounts.add(la);
                }
            }
            if(ca.getChecks()!=null){
                if(ca.getChecks().size()!=0){
                    for(Check check:ca.getChecks()){
                        checksArr.add(check);
                    }
                }
            }

        }

        customersPW.close();
        checkingPW.println(CheckingAccount.toStringCSVHeader());
        for(CheckingAccount checkingAccount:checkingAccounts){
            checkingPW.println(checkingAccount.toStringCSV());
        }
        checkingPW.close();

        savingsPW.println(SavingsAccount.toStringCSVHeader());
        for(SavingsAccount sa:savingsAccounts){
            savingsPW.println(sa.toStringCSV());
        }
        savingsPW.close();

        loanPW.println(LoanAccount.toStringCSVHeader());
        for(LoanAccount la:loanAccounts){
            loanPW.println(la.toStringCSV());
        }
        loanPW.close();

        transactionsPW.println(Transaction.toStringCSVHeader());
        for(CustomerAccount ca:customerAccounts){
            if(ca.getTransactions()!=null){
                for(Transaction transaction: ca.getTransactions()){
                    transactionsPW.println(transaction.toStringCSV(ca));
                }
            }
        }
        transactionsPW.close();
        checksPW.println(Check.toStringCSVHeader());
        for(CustomerAccount ca:customerAccounts){
            if(ca.getChecks()!=null){
                if(ca.getChecks().size()!=0){
                    String caCheckingID = ca.getCheckingAccount().getCheckingAcctIDFixed();
                    for(Check check:ca.getChecks()){
                        Check tempCheck = new Check();
                        tempCheck = check;
                        tempCheck.setCheckingAcctID(caCheckingID);
                        checksPW.println(tempCheck.toStringCSV());
                    }
                }

            }
        }

        checksPW.close();
    }


    public void importDataFromExportedFiles(){
        System.out.println("Reading in the exported database Files");

        // have user select one file from the export folder so that we can get the dateTime prefix on the file and read in
        // all files with that same prefix
        File selectedFile = getFileFromExportDir();
        if(selectedFile==null || !selectedFile.exists()){
            System.out.println("null");
            return; // if file is null or does not exist return
        }

        String fileName = selectedFile.getName(); // name of selected file
        String prefixString = ""; // initialize prefix string

        if(fileName.contains("_")){ // if contains _ which separates the prefix and file name in exporting
            if(fileName.contains("-")){ // if it has the very long prefix at all
                String datePart = fileName.split("-")[0];
                if(datePart.length()==8){ // the first part is in format yyyymmdd
                    String[] splitPrefix = fileName.split("_");// split on the _ to get the dateTimePrefix
                    prefixString = splitPrefix[0]+"_"; // add the _ back in
                }
            }
        }

        File selectedFileDirectory = selectedFile.getParentFile(); // directory file of the selected file
        ArrayList<File> filesWithMatchingPrefix = new ArrayList<>(); // will hold all files that match the prefix of selected file

        // arraylist of valid file names
        ArrayList<String> validFileNames = new ArrayList<>(
                Arrays.asList(
                        "CustomersBase.csv",
                        "CheckingAccounts.csv",
                        "SavingsAccounts.csv",
                        "LoanAccounts.csv",
                        "Checks.csv",
                        "Transactions.csv"));

        // fill the matchingFiles array with all files that have the same prefix as the chosen file
        for(File fileEntry : Objects.requireNonNull(selectedFileDirectory.listFiles())){
            if(fileEntry.getName().contains(prefixString)){
                filesWithMatchingPrefix.add(fileEntry); // if it has the prefix of the file the user selected then add to the array
            }
        }

        for(File f:filesWithMatchingPrefix){ // loop through the files
            String fileNameString = f.getName();
            String fileNameWithoutPrefix = fileNameString.replaceAll(prefixString,"");

            if(validFileNames.contains(fileNameWithoutPrefix)){ // if list of valid file names matches this one
                // remove that item from valid file names.
                validFileNames.remove(fileNameWithoutPrefix);
            }
        }

        // validFileNames list should now be empty if all files are there
        if(validFileNames.size()!=0){// if all items were not removed from valid file names list
            // fix the valid files name with prefix so users know exactly what file is missing
            for(int i=0;i<validFileNames.size();i++){
                validFileNames.set(i,prefixString+validFileNames.get(i)); // modifies the valid files with the prefix string
            }

            // now print the result to the user via the console error message
            String errMsg = String.format("You are Missing Files: %s from your chosen directory. " +
                    "Import was not successful",validFileNames);
            System.err.println(errMsg);
            return;
        }



        DataEntryDriver.createCustomerDatabaseFileFromCSVBaseData(true,prefixString); // read csv files and make database file
        Main.customerAccounts = DataEntryDriver.readFileToCustomerAccountsArrayList(); // then reads the database file

        // then fix the id system and customerAccount financial Id if needed
        DataEntryDriver.fixIdSystemForCustomerAccounts();


    }


    public static String getPrefixDateTimeForFileExport(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String localDateTimeString = localDateTime.toString();

        String dateString = localDateTimeString.substring(0,10); // 10 is exclusive so it keeps 9
        String timeString = localDateTimeString.substring(11);
        String[] timeStringSplit = timeString.split("\\.");
        String timeHMSStringFixed = timeStringSplit[0].replaceAll(":","");
        String timeMS = timeStringSplit[1];
        String timeStringFixed = timeHMSStringFixed+timeMS;
        String dateStringFixed = dateString.replaceAll("-","");
        String dateTimeStringFixed = dateStringFixed+"-"+timeStringFixed+"_";

        return dateTimeStringFixed;
    }

    // EVERYTHING BELOW THIS LINE TO END COMMENT IS TESTING PURPOSES ONLY



    public static File getFileFromExportDir(){
        boolean exportDirExists = false;
        File returnFile = null;

        File initialDir = new File(System.getProperty("user.dir")+"/src/Resources/export");
        if(initialDir!=null){
            if(initialDir.exists()){
                System.out.println("init dir: "+initialDir.getPath());
                exportDirExists = true;
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));

        if(exportDirExists){ // if export directory exist start the user there
            fileChooser.setInitialDirectory(initialDir);
        }else{
            initialDir = new File(System.getProperty("user.dir")+"/src/Resources"); // if not start them in resources dir
            fileChooser.setInitialDirectory(initialDir);
        }


        File testFile = fileChooser.showOpenDialog(Main.activeStage);

        if(testFile!=null){
            if(testFile.exists()){
                returnFile = testFile;
            }
        }

        return returnFile;
    }


    @FXML
    public void randomSSNButton(){
        int size = Main.customerAccounts.size();
        Random random = new Random();
        int randomInt = random.nextInt(size);
        manageUserSSNField.setText(Main.customerAccounts.get(randomInt).getCustID());

    }

    @FXML
    public void mainScreenTestButton(){
        System.out.println("Test Today is: "+DataEntryDriver.getDateString()+"\n\n");
        ArrayList<CustomerAccount> customerAccounts = Main.customerAccounts;

        System.out.println("\nPrint all base Customer Data:");
        for(CustomerAccount ca:customerAccounts){
            System.out.println(ca.toStringBaseDataTableFormat());
        }

        System.out.println("\nPrint All Checking Accounts:");
        for(CustomerAccount ca:customerAccounts){
            if(ca.hasCheckingAccount()){
                System.out.println(ca.toStringPrettyPrint()+":");
                System.out.println(ca.getCheckingAccount().toStringTableFormat()+"\n");
            }
        }

        System.out.println("\n\nPrint all savings:");
        for(CustomerAccount ca:customerAccounts){
            if(ca.hasSavingsAccount()){
                System.out.println(ca.toStringPrettyPrint()+":");
                ArrayList<SavingsAccount> savingsAccounts = ca.getSavingsAccounts();
                for(SavingsAccount sa:savingsAccounts){
                    System.out.println(sa.toStringTableFormat());
                }
                System.out.println("");
            }
        }

        System.out.println("\n\nPrint all loan accounts:");
        for(CustomerAccount ca:customerAccounts){
            if(ca.hasLoanAccount()){
                System.out.println(ca.toStringPrettyPrint()+":");
                ArrayList<LoanAccount> loanAccounts = ca.getLoanAccounts();
                for(LoanAccount la:loanAccounts){
                    System.out.println(la.toStringTableFormat());
                }
                System.out.println("");

            }
        }

        System.out.println("\n\nPrint all Transactions:");
        for(CustomerAccount ca:customerAccounts){
            System.out.println(ca.toStringPrettyPrint()+":");
            ArrayList<Transaction> transactions = ca.getTransactions();
            if(transactions.size()!=0){// if size is not 0
                for(Transaction transaction: transactions){
                    System.out.println(transaction.toStringTableFormat());
                }
            }else{
                System.out.println("No Transactions");
            }
            System.out.println("");

        }

        System.out.println("\n\nPrint All Checks:");
        for(CustomerAccount ca:customerAccounts){
            ArrayList<Check> checks = ca.getChecks();
            if(checks.size()>0){
                System.out.println(ca.toStringPrettyPrint()+":");
                for(Check check:checks){
                    System.out.println(check.toStringTableFormat());
                }
                System.out.println("");
            }
        }



        for(CustomerAccount ca:customerAccounts){
            if(ca.hasSavingsAccount()){
                System.out.println("\n\n"+ca.toStringPrettyPrint());
                for(SavingsAccount sa:ca.getSavingsAccounts()){
                    System.out.println(sa.toString());
                }
            }
        }





    }



    // END COMMENT


}
