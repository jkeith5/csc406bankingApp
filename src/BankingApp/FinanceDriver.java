package BankingApp;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class FinanceDriver {
    // this will have all the methods that conduct financial transactions on the Customer Account objects.
    // most, if not all, methods here will be called from the Controller class, and will hold things like
    // transfer debit credit methods.



    public FinanceDriver(){ // not sure I'll need this.
        //this.customerAccount = Main.customerAccount;
    }



    // returns true or false on if the combination of settings and amount are valid options if false use to disable transfer button
    public static boolean isTransferAmtValid(TextField transferAmt, CheckBox transferFundsCheckBox, RadioButton checkingAccRadio, RadioButton savingsAccRadio, Label errLabel){
        boolean returnVal = true;
        String transferAmtString = transferAmt.getText();
        double transferAmtDouble=0.0;
        CustomerAccount ca = Main.customerAccount;

        boolean backupSavingEnabled = false;

        if(ca.hasCheckingAccount()){
            backupSavingEnabled = ca.getCheckingAccount().isBackupSavingsEnabled();
        }

        System.out.println(ca.toString());


        try {
            transferAmtDouble = Double.parseDouble(transferAmtString);
        } catch (NumberFormatException e) {
            transferAmtDouble = 0.0;
        }
        //System.out.println("transferAmt double: "+transferAmtDouble);


        if(transferFundsCheckBox.isSelected()){ // transferring funds from account
            double checkingBalance = ca.getCheckingAccount().getAccountBalance();
            double simpleSavingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();

            //double newBal = checkingBalance+transferAmtDouble;

            if(checkingAccRadio.isSelected()){ // transfer from checking APPLY FEE TO CHECKING
                double newBal = checkingBalance-transferAmtDouble;
                if(transferAmtDouble>checkingBalance){
                    returnVal = false;
                    errLabel.setText("There is not enough money in Checking Account to Complete Transaction! new balance: "+newBal);
                }else{
                    errLabel.setText("transfer "+ transferAmtDouble +" from checking to savings new balance: "+newBal);
                }

            }else{ // transfer from savings  to checking
                double newBal = simpleSavingsBalance-transferAmtDouble;
                if(transferAmtDouble>simpleSavingsBalance){
                    returnVal=false;
                    errLabel.setText("There is not enough money in Savings account to Complete Transaction! new balance: "+newBal);
                }else{
                    errLabel.setText("transfer "+transferAmtDouble +" from savings to checkings new balance: "+newBal);
                }
            }

        }else{
            //Transfer funds is not selected.
            if(checkingAccRadio.isSelected()){
                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;


                System.out.println("Transfer amt double: "+transferAmtDouble);
                System.out.println("checking balance: "+checkingBalance);

                if(transferAmtDouble<0.0){ // If we are making a debit
                    // remember to implement the transaction fee on correct type of account
                    System.out.println("Transfer amt < 0.00");

                    // so taking money from account if new balance is less than 0 then NO

                    if(newBal<0.0){// if new balance would put account under 0 then false // can change to min balance

                        if(backupSavingEnabled){
                            if(ca.getSimpleSavingsAccount().getAccountBalance()+newBal>0.00){ // if amt overdrawn on checking by
                                // transaction is more than what is in backup saving account then take it from savings
                                errLabel.setText("Backup Savings Warning. Take $"+newBal+ " from Savings. New Savings balance: "
                                +ca.getSimpleSavingsAccount().getAccountBalance()+newBal+" checking Balance $0.00");
                            }else{
                                returnVal = false;
                                errLabel.setText("This Transaction would overdraw the account by: "+newBal+ " And No Backup Savings Enabled.");
                            }
                        }
                    }else{// if new balance is over 0 then it's good
                        errLabel.setText("Debit "+transferAmtDouble+" from Checking Account with new Balance of: "+newBal);
                    }

                }else{ // we are making a deposit everything is cool here no problem.
                    System.out.println("Transfer amt > 0.00 = credit to checking account");
                    errLabel.setText("Credit "+transferAmtDouble+" to Checking Account new balance: "+newBal);
                }
            }
            if(savingsAccRadio.isSelected()){
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                if(transferAmtDouble<0.0){
                    System.out.println("Transfer amt < 0.00 so its a debit");

                    if(newBal<0.00){
                        returnVal = false;
                        errLabel.setText("There is not enough money in Savings Account to complete transaction");
                    }else{
                        errLabel.setText("Debit "+transferAmtDouble+" from Savings Account with new Savings Balance of: "+newBal);
                    }

                }else{
                    errLabel.setText("Credit "+transferAmtDouble+" to Savings Account new balance: "+newBal);
                }

            }

        }

        System.out.println("Return value is: "+returnVal);

        return returnVal;
    }

    public static void completeAtmTransaction(TextField withdrawalAmount){
        String withdrawalAmtString = withdrawalAmount.getText();
        Double withdrawalAmtDouble = 0.0;
        CustomerAccount ca = Main.loggedInCustomer;
        Double newBal = 0.0;

        try {
            withdrawalAmtDouble = Double.parseDouble(withdrawalAmtString);
        } catch (NumberFormatException e) {
            withdrawalAmtDouble = 0.0;
        }

        double checkingBalance = ca.getCheckingAccount().getAccountBalance();

        if(checkingBalance - withdrawalAmtDouble > 0) {
             newBal = checkingBalance - withdrawalAmtDouble;
            creditDebitCheckingAccountAtm(ca.getCheckingAccount(),withdrawalAmtDouble);
        }else{
            System.out.println("insufficient funds");
        }

        System.out.println("newBal = " + newBal);


    }


    public static void completeTransaction(TextField transferAmt, CheckBox transferFundsCheckBox, RadioButton checkingAccRadio, RadioButton savingsAccRadio, Label errLabel){
        String transferAmtString = transferAmt.getText();
        double transferAmtDouble=0.0;
        CustomerAccount ca = Main.customerAccount;

        boolean backupSavingEnabled = false;

        if(ca.hasCheckingAccount()){
            backupSavingEnabled = ca.getCheckingAccount().isBackupSavingsEnabled();
        }

        System.out.println(ca.toString());


        try {
            transferAmtDouble = Double.parseDouble(transferAmtString);
        } catch (NumberFormatException e) {
            transferAmtDouble = 0.0;
        }
        //System.out.println("transferAmt double: "+transferAmtDouble);


        if(transferFundsCheckBox.isSelected()){ // transferring funds from account
            double checkingBalance = ca.getCheckingAccount().getAccountBalance();
            double simpleSavingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();

            //double newBal = checkingBalance+transferAmtDouble;

            if(checkingAccRadio.isSelected()){ // transfer from checking APPLY FEE TO CHECKING
                double newBal = checkingBalance-transferAmtDouble;
                if(transferAmtDouble<checkingBalance){
                    errLabel.setText("transfer "+ transferAmtDouble +" from checking to savings new balance: "+newBal);
                }
            }else{ // transfer from savings  to checking
                double newBal = simpleSavingsBalance-transferAmtDouble;
                if(transferAmtDouble>simpleSavingsBalance){
                    errLabel.setText("transfer "+transferAmtDouble +" from savings to checkings new balance: "+newBal);
                }
            }

        }else{
            //System.out.println("transfer funds: false");
            if(checkingAccRadio.isSelected()){ // credit debit checking account

                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;

                System.out.println("Transfer amount: "+transferAmtDouble);
                System.out.println("checking bal: "+checkingBalance);
                System.out.println("new bal: "+newBal);
                System.out.println("backup enabled: "+backupSavingEnabled);

                if(transferAmtDouble<0.0){  // we are making a debit
                    // remember to implement the transaction fee on correct type of account

                    if(newBal<0.00){// if new balance would put account at less than 0.00
                        // check backup savings

                        if(backupSavingEnabled){// if they have backup savings enabled
                            if(ca.getSimpleSavingsAccount().getAccountBalance()+newBal>0.00){ // if amt overdrawn on checking by
                                // transaction is more than what is in backup saving account then take it from savings
                                errLabel.setText("Backup Savings Warning. Take $"+newBal+ " from Savings. New Savings balance: "
                                        +ca.getSimpleSavingsAccount().getAccountBalance()+newBal+" checking Balance $0.00");

                                // transfer the amount that is negative in checking account from savings
                                creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),newBal,"Transfer to Checking Backup Savings");
                                System.out.println("newbal 1: "+newBal);
                                double transferAmountTemp = Math.abs(newBal); // take the absolute value of newbal which was a negative
                                // then put it in the checking account.
                                System.out.println("transfer amt temp: "+transferAmountTemp);
                                creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmountTemp,"Transfer from Savings Backup Savings");
                                // then take it out again for now until I make a transfer method and put this into one transaction object
                                creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,"");
                                System.out.println("newbal 2: "+newBal);

                            }
                        }// otherwise the button is disabled and you can't complete transaction

                    }else{// else there is enough money in checking account to make debit
                        creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,"");
                    }

                }else{
                    creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,"");
                    errLabel.setText("Credit "+transferAmtDouble+" to Checking Account new balance: "+newBal);
                }
            }
            if(savingsAccRadio.isSelected()){
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                if(transferAmtDouble<0.0){
                    if(newBal>0.00){// if new balance is not negative
                        creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble,"");
                        errLabel.setText("Debit "+transferAmtDouble+" from Savings Account new balance: "+newBal);
                    }

                }else{// we are adding money so it doesn't matter at all. Add as much as you want $$$
                    creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble,"");
                    errLabel.setText("Credit "+transferAmtDouble+" to Savings Account new balance: "+newBal);
                }

            }

        }


    }



    // just for simple withdraw and deposit on savings account. No Transaction fee from what I read
    // auto adds transaction object to customerAccount
    public static void creditDebitSavingsAccount(SavingsAccount savingsAccount, double transactionAmount,String desc){


        System.out.println("Crediting a savings account: "+savingsAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("S"); // a transaction on the Savings Account

        if(transactionAmount>0.00){
            transaction.setTransactionType("D"); // this is a deposit
        }else{
            transaction.setTransactionType("W");// a withdraw
        }

        if(desc.length()>0){
            transaction.setDescription(desc);
        }else{
            if(transactionAmount>0.00){
                transaction.setDescription("Deposit on Savings Account");
            }else{
                transaction.setDescription("Withdraw on Savings Account");
            }
        }



        savingsAccount.setAccountBalance(savingsAccount.getAccountBalance()+transactionAmount); // just add + or - will work correctly
        transaction.setAmount(transactionAmount); // should only be positive

        // record transaction in log

        Main.customerAccount.addTransactionObject(transaction);
        System.out.println("Transaction added: "+savingsAccount.toString());
        System.out.println("taking: "+transactionAmount +" on Savings account");

    }

    public static void creditDebitCheckingAccount(CheckingAccount checkingAccount, double transactionAmount,String desc){
        System.out.println("Credit debit checking account: "+checkingAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("C");

        // need to find fee and apply to either account and find if account balance is met
        // also need to be able to take from savings if backup enabled.

        if(transactionAmount>0.00){
            transaction.setTransactionType("D");
        }else{
            transaction.setTransactionType("W");
        }

        if(desc.length()>0){
            transaction.setDescription(desc);
        }else{
            if(transactionAmount>0.00){
                transaction.setDescription("Deposit on Checking Account");
            }else{
                transaction.setDescription("Withdraw on Checking Account");
            }
        }

        checkingAccount.setAccountBalance(checkingAccount.getAccountBalance()+transactionAmount);
        transaction.setAmount(transactionAmount);

        Main.customerAccount.addTransactionObject(transaction);
        System.out.println("Transaction added: "+checkingAccount.toString());


        System.out.println("Taking: "+transactionAmount+ " on checking account");


    }

    public static void creditDebitCheckingAccountAtm(CheckingAccount checkingAccount, double transactionAmount) {
        System.out.println("Credit debit checking account from ATM: "+checkingAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("C");

        if(transactionAmount>0.00){
            transaction.setTransactionType("D");
        }else{
            transaction.setTransactionType("W");
        }

        checkingAccount.setAccountBalance(checkingAccount.getAccountBalance()-transactionAmount);
        transaction.setAmount(transactionAmount);

        Main.customerAccount.addTransactionObject(transaction);
        System.out.println("Transaction added: "+checkingAccount.toString());


        System.out.println("Taking: "+transactionAmount + " on checking account");
    }

}
