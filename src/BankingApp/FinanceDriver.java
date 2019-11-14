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
                        returnVal = false;
                        errLabel.setText("This transaction will overdraw the account by: "+newBal);
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


    public static void completeTransaction(TextField transferAmt, CheckBox transferFundsCheckBox, RadioButton checkingAccRadio, RadioButton savingsAccRadio, Label errLabel){
        String transferAmtString = transferAmt.getText();
        double transferAmtDouble=0.0;
        CustomerAccount ca = Main.customerAccount;

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
            if(checkingAccRadio.isSelected()){

                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;

                if(transferAmtDouble<0.0){ // remember to implement the transaction fee on correct type of account
                    if(transferAmtDouble<checkingBalance){
                        creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble);
                        errLabel.setText("debit "+transferAmtDouble+" from Checking Account new balance: "+newBal);
                    }
                }else{
                    creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble);
                    errLabel.setText("Credit "+transferAmtDouble+" to Checking Account new balance: "+newBal);
                }
            }
            if(savingsAccRadio.isSelected()){
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                if(transferAmtDouble<0.0){
                    if(transferAmtDouble<savingsBalance){
                        creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble);
                        errLabel.setText("Debit "+transferAmtDouble+" from Savings Account new balance: "+newBal);
                    }
                }else{
                    creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble);
                    errLabel.setText("Credit "+transferAmtDouble+" to Savings Account new balance: "+newBal);
                }

            }

        }


    }



    // just for simple withdraw and deposit on savings account. No Transaction fee from what I read
    // auto adds transaction object to customerAccount
    public static void creditDebitSavingsAccount(SavingsAccount savingsAccount, double transactionAmount){
        System.out.println("Crediting a savings account: "+savingsAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("S"); // a transaction on the Savings Account

        if(transactionAmount>0.00){
            transaction.setTransactionType("D"); // this is a deposit
            transaction.setDescription("Deposit on Savings Account");
        }else{
            transaction.setTransactionType("W");// a withdraw
            transaction.setDescription("Withdraw on Savings Account");
        }
        savingsAccount.setAccountBalance(savingsAccount.getAccountBalance()+transactionAmount); // just add + or - will work correctly
        transaction.setAmount(transactionAmount); // should only be positive

        // record transaction in log

        Main.customerAccount.addTransactionObject(transaction);
        System.out.println("Transaction added: "+savingsAccount.toString());

    }

    public static void creditDebitCheckingAccount(CheckingAccount checkingAccount, double transactionAmount){
        System.out.println("Credit debit checking account: "+checkingAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("C");

        // need to find fee and apply to either account and find if account balance is met
        // also need to be able to take from savings if backup enabled.

        if(transactionAmount>0.00){
            transaction.setTransactionType("D");
            transaction.setDescription("Deposit on Checking Account");
        }else{
            transaction.setTransactionType("W");
            transaction.setDescription("Withdraw on Checking Account");
        }
        checkingAccount.setAccountBalance(checkingAccount.getAccountBalance()+transactionAmount);
        transaction.setAmount(transactionAmount);

        Main.customerAccount.addTransactionObject(transaction);
        System.out.println("Transaction added: "+checkingAccount.toString());





    }







}
