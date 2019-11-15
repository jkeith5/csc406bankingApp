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

        //System.out.println(ca.toString());


        try {
            transferAmtDouble = Double.parseDouble(transferAmtString);
        } catch (NumberFormatException e) {
            transferAmtDouble = 0.0;
        }
        //System.out.println("transferAmt double: "+transferAmtDouble);
        boolean isGold = ca.getCheckingAccount().isGoldAccount();
        boolean minBalNotMet = false;

        if(isGold){ // this can only turn ture if it's a gold account and min bal not met
            if(ca.getCheckingAccount().getAccountBalance()<1000){
                minBalNotMet = true;
            }
        }


        if(transferFundsCheckBox.isSelected()){ // transferring funds from account
            double checkingBalance = ca.getCheckingAccount().getAccountBalance();
            double simpleSavingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();

            //double newBal = checkingBalance+transferAmtDouble;

            if(checkingAccRadio.isSelected()){ // transfer from checking APPLY FEE TO CHECKING
                double newBal = checkingBalance-transferAmtDouble;
                double newSavingBal = simpleSavingsBalance + transferAmtDouble;

                if(transferAmtDouble>checkingBalance){
                    returnVal = false;
                    errLabel.setText("There is not enough money in Checking Account to Complete Transaction! new balance: "+newBal);
                }else{
                    if(!ca.getCheckingAccount().isGoldAccount()){ // run on the That's my Bank account .75 transfer fee .50 transaction fee
                        newBal = newBal-0.75;
                        errLabel.setText("transfer "+ transferAmtDouble +" from checking to savings with .75 fee \nNew Checking balance: "+newBal
                                +"\nNew Savings Balance: "+newSavingBal);
                    }else{ // this is a gold diamond account and minimum balance must be met to avoid fee min bal is 1,000 if not I'll apply .75 fee
                        if(checkingBalance<1000.00){// if minimum balance not met on gold diamond apply fee
                            newBal = newBal-0.75; // apply fee because min balance not met
                            errLabel.setText("transfer "+ transferAmtDouble +" from checking to savings with .75 fee.\nNew Checking balance: "+newBal+
                            "\nNew Savings Balance: "+newSavingBal);
                        }else{
                            errLabel.setText("transfer "+ transferAmtDouble +" from checking to savings\nNew Checking balance: "+newBal+
                                    "\nNew Savings Balance: "+newSavingBal);
                        }
                    }
                }

            }else{ // transfer from savings  to checking
                double newBal = simpleSavingsBalance-transferAmtDouble; // new savings bal
                double newCheckingBal = checkingBalance+transferAmtDouble; // new checking bal
                if(transferAmtDouble>simpleSavingsBalance){
                    returnVal=false;
                    errLabel.setText("There is not enough money in Savings account to Complete Transaction! new balance: "+newBal);
                }else{ // so what apply the .50 fee for transactions to checking account if not gold diamond and if gold diamond min bal not met?
                    if(isGold){ // this can only turn ture if it's a gold account and min bal not met
                        if(checkingBalance<1000){
                            minBalNotMet = true;
                        }
                    }

                    if(!isGold || minBalNotMet){ // if standard checking or gold account not meeting min balance
                        newCheckingBal = newCheckingBal -0.75;
                        errLabel.setText("Transfer "+transferAmtDouble+" from Savings to Checking with 0.75 fee on Checking. " +
                                "\nNew Savings Balance: "+newBal+"\nNew Checking Balance: "+newCheckingBal);
                        // might set to red if fee brings acc to under 0
//                        errLabel.setTextFill(Color.web("#ff0000"));
                    }else{ // if gold account meeting min bal
                        errLabel.setText("transfer "+transferAmtDouble +" from Savings to checking.\nNew Savings balance: "+newBal+
                        "\nNew Checking Balance: "+newCheckingBal);
                    }
                }
            }

        }else{
            //Transfer funds is not selected.
            if(checkingAccRadio.isSelected()){
                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;


                //System.out.println("Transfer amt double: "+transferAmtDouble);
                //System.out.println("checking balance: "+checkingBalance);

                if(transferAmtDouble<0.0){ // If we are making a debit
                    // remember to implement the transaction fee on correct type of account
                    //System.out.println("Transfer amt < 0.00");

                    // so taking money from account if new balance is less than 0 then NO

                    if(newBal<0.0){// if new balance would put account under 0 then false // can change to min balance

                        if(backupSavingEnabled){
                            if(ca.getSimpleSavingsAccount().getAccountBalance()+newBal>0.00){ // if amt overdrawn on checking by
                                // transaction is more than what is in backup saving account then take it from savings
                                if(!isGold || minBalNotMet){
                                    // fee of .50 for all transactions
                                    errLabel.setText("Backup Savings Warning. Take $"+newBal+ " from Savings. Plus .50 Transaction Fee\nNew Savings balance: "
                                            +(ca.getSimpleSavingsAccount().getAccountBalance()+newBal-0.50)+" checking Balance $0.00");

                                }
                            }else{
                                returnVal = false;
                                errLabel.setText("This Transaction would overdraw the account by: "+newBal+ " And No Backup Savings Enabled.");
                            }
                        }
                    }else{// if new balance is over 0 then it's good
                        if(!isGold || minBalNotMet){
                            // fee
                            errLabel.setText("Debit "+transferAmtDouble+" from Checking Account plus 0.50 Fee with new Balance of: "+(newBal-0.50));
                        }else{
                            errLabel.setText("Debit "+transferAmtDouble+" from Checking Account with new Balance of: "+newBal);
                        }

                    }

                }else{ // we are making a deposit everything is cool here no problem.

                    if(!isGold || minBalNotMet){
                        //fee
                        errLabel.setText("Credit "+transferAmtDouble+" to Checking Account Plus 0.50 Fee new balance: "+(newBal-0.75));
                    }else{// no fee
                        errLabel.setText("Credit "+transferAmtDouble+" to Checking Account new balance: "+newBal);
                    }

                }
            }
            if(savingsAccRadio.isSelected()){
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                if(transferAmtDouble<0.0){
                    //System.out.println("Transfer amt < 0.00 so its a debit");

                    if(newBal<0.00){
                        returnVal = false;
                        errLabel.setText("There is not enough money in Savings Account to complete transaction");
                    }else{// savings account has no transaction fee.
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


        if(transferFundsCheckBox.isSelected()){ // transferring funds from account
            double checkingBalance = ca.getCheckingAccount().getAccountBalance();
            double simpleSavingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
            boolean isGold = ca.getCheckingAccount().isGoldAccount();
            boolean minBalNotMet = false;

            if(isGold){ // this can only turn ture if it's a gold account and min bal not met
                if(checkingBalance<1000){
                    minBalNotMet = true;
                }
            }

            if(checkingAccRadio.isSelected()){ // transfer from checking to savings APPLY FEE TO CHECKING
                if(transferAmtDouble<checkingBalance){
                    if(!isGold || minBalNotMet){ // run on standard checking account
                        double fee = 0.75;
                        // transfer money from checking to savings and apply transfer fee to checking
                        transferMoneyBetweenCheckingSaving(ca,transferAmtDouble,true);
                        // apply fee
                        creditDebitCheckingAccount(ca.getCheckingAccount(),-0.75,"Transfer Fee");
                    }else{ // if it is a gold account and min balance is met
                        transferMoneyBetweenCheckingSaving(ca,transferAmtDouble,true);
                    }
                }

            }else{ // transfer from savings  to checking
                double newBal = simpleSavingsBalance-transferAmtDouble;
                if(transferAmtDouble<simpleSavingsBalance){

                    if(!isGold || minBalNotMet){// run on standard account or gold account not meeting min bal
                        double fee = -0.75;
                        transferMoneyBetweenCheckingSaving(ca,transferAmtDouble,false);// transfer money from saving
                        creditDebitCheckingAccount(ca.getCheckingAccount(),fee,"Transfer Fee");// apply 0.75 fee to checking
                    }else{
                        transferMoneyBetweenCheckingSaving(ca,transferAmtDouble,false); // just transfer no fee
                    }

                }
            }

        }else{
            //System.out.println("transfer funds: false");
            if(checkingAccRadio.isSelected()){ // credit debit checking account
                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;
                boolean isGold = ca.getCheckingAccount().isGoldAccount();
                boolean minBalNotMet = false;

                if(isGold){ // this can only turn ture if it's a gold account and min bal not met
                    if(checkingBalance<1000){
                        minBalNotMet = true;
                    }
                }




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

                        creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,""); // make the transaction

                        if(!isGold || minBalNotMet){ // if standard or gold not meeting min bal then apply a fee
                            creditDebitCheckingAccount(ca.getCheckingAccount(),-.50,"Transaction Fee");
                        }

                    }

                }else{ // we are making a credit to checking account .50 fee
                    creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,"");
                    if(!isGold || minBalNotMet){
                        creditDebitCheckingAccount(ca.getCheckingAccount(),-.50,"Transaction Fee");
                    }

                }
            }
            if(savingsAccRadio.isSelected()){
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                if(transferAmtDouble<0.0){
                    if(newBal>0.00){// if new balance is not negative
                        creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble,"");
                        //errLabel.setText("Debit "+transferAmtDouble+" from Savings Account new balance: "+newBal);
                    }

                }else{// we are adding money so it doesn't matter at all. Add as much as you want $$$
                    creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble,"");
                    //errLabel.setText("Credit "+transferAmtDouble+" to Savings Account new balance: "+newBal);
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
        System.out.println("Credit debit checking account from ATM: " + checkingAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("C");

        if (transactionAmount > 0.00) {
            transaction.setTransactionType("D");
        } else {
            transaction.setTransactionType("W");
        }


    }



    // This only transfers between simple saving and checking The validity of the transfer should have been verified
    // before this method is called.
    public static void transferMoneyBetweenCheckingSaving(CustomerAccount ca,double amount, boolean fromChecking){
        amount = Math.abs(amount);
        System.out.println("transfer money between accounts ca: "+ca.toString());
        Transaction transactionWithdrawal = new Transaction();
        Transaction transactionDeposit = new Transaction();

        SavingsAccount simpleSaving = ca.getSimpleSavingsAccount();
        CheckingAccount checkingAccount = ca.getCheckingAccount();

        // make a transaction and check for the FEE

        if(fromChecking){ // transfer from checking to savings
            System.out.println("Making a transfer of "+amount+" from checking to savings account");
            transactionWithdrawal.setDescription("Transfer to Savings");
            transactionDeposit.setDescription("Transfer from Checking");

            transactionWithdrawal.setAmount(0.00-amount); //make it negative
            transactionDeposit.setAmount(amount);

            transactionWithdrawal.setTransactionType("TW"); // making a transfer Withdrawal
            transactionDeposit.setTransactionType("TD"); // Transfer Deposit

            transactionWithdrawal.setTransactionAccount("C");// from the Checking Account
            transactionDeposit.setTransactionAccount("S"); // deposit to savings

            checkingAccount.debitCreditAccount(0.00-amount);// debit the checking account
            simpleSaving.debitCreditAccount(amount);// put it into savings

            ca.addTransactionObject(transactionWithdrawal);
            ca.addTransactionObject(transactionDeposit); // add the transaction objects

            // then Put fee here



        }else{ // transfer from savings to checking
            System.out.println("Making a transfer of "+amount+" from Savings to Checking account");
            transactionWithdrawal.setDescription("Transfer to Checking");
            transactionDeposit.setDescription("Transfer from Savings");

            transactionWithdrawal.setAmount(0.00-amount); //make it negative
            transactionDeposit.setAmount(amount);

            transactionWithdrawal.setTransactionType("TW"); // making a transfer Withdrawal
            transactionDeposit.setTransactionType("TD"); // Transfer Deposit

            transactionWithdrawal.setTransactionAccount("S");// from the savings Account
            transactionDeposit.setTransactionAccount("C"); // deposit to checking

            simpleSaving.debitCreditAccount(0.00-amount); // debit savings account
            checkingAccount.debitCreditAccount(amount); // put it in checking account

            ca.addTransactionObject(transactionWithdrawal);
            ca.addTransactionObject(transactionDeposit); // add the transaction objects

            // then Put fee here
        }


//        checkingAccount.setAccountBalance(checkingAccount.getAccountBalance()-transactionAmount);
//        transaction.setAmount(transactionAmount);
//
//        Main.customerAccount.addTransactionObject(transaction);
//        System.out.println("Transaction added: "+checkingAccount.toString());
//
//
//        System.out.println("Taking: "+transactionAmount + " on checking account");

    }













































// End FINANCE DRIVER CLASS
}




