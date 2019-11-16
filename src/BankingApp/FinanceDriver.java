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


        try {
            transferAmtDouble = Double.parseDouble(transferAmtString);
        } catch (NumberFormatException e) {
            transferAmtDouble = 0.0;
        }

        if(transferAmtDouble <0.001 && transferAmtDouble>-0.001){
            System.out.println("less than zero or it is zero");
            returnVal=false;
            errLabel.setText("Please Enter an Amount.");
            return returnVal;
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

                        if(isFeeApplicable(ca)){ // set label for new balance with fee
                            newBal = newBal-0.75; // apply fee because min balance not met
                            errLabel.setText("transfer "+ transferAmtDouble +" from checking to savings with .75 fee.\nNew Checking balance: "+newBal+
                                    "\nNew Savings Balance: "+newSavingBal);
                        }else{ // set label without fee
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
                    if(isFeeApplicable(ca)){// if account is type with fee
                        newCheckingBal = newCheckingBal -0.75;
                        errLabel.setText("Transfer "+transferAmtDouble+" from Savings to Checking with 0.75 fee on Checking. " +
                                "\nNew Savings Balance: "+newBal+"\nNew Checking Balance: "+newCheckingBal);
                    }else{
                        errLabel.setText("transfer "+transferAmtDouble +" from Savings to checking.\nNew Savings balance: "+newBal+
                                "\nNew Checking Balance: "+newCheckingBal);
                    }
                }
            }

        }else{//Transfer funds is not selected.
            if(checkingAccRadio.isSelected()){
                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;
                double savingsBalance = 0.0;
                if(ca.hasSavingsAccount()){
                    savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                }

                if(transferAmtDouble<0.0){ // If we are making a debit
                    if(newBal<0.0){// if new balance would put account under 0 then false // can change to min balance
                        if(backupSavingEnabled){ // means there is a savings account and its enabled
                            if(savingsBalance+newBal>0.00){ // if amt overdrawn on checking by
                                // transaction is more than what is in backup saving account then take it from savings

                                if(isFeeApplicable(ca)){// if fee account
                                    errLabel.setText("Backup Savings Warning. Take $"+newBal+ " from Savings. Plus .50 Transaction Fee\nNew Savings balance: "
                                            +(ca.getSimpleSavingsAccount().getAccountBalance()+newBal-0.50)+" checking Balance $0.00");
                                }else{
                                    errLabel.setText("Backup Savings Warning. Take $"+newBal+ " from Savings. \nNew Savings balance: "
                                            +(ca.getSimpleSavingsAccount().getAccountBalance()+newBal-0.50)+"\nchecking Balance $0.00");
                                }
                            }else{ // if backup saving enabled but still not enough money in either account give SOL message
                                returnVal = false;
                                errLabel.setText("This Transaction would overdraw the account by: "+newBal);
                            }
                        }else{ // else no backup savings enabled
                            returnVal =false;
                            errLabel.setText("This transaction would overdraw the account by: "+newBal+" and there is no Backup Savings Enabled.");
                        }

                    }else{// if new balance is over 0 then it's good
                        if(isFeeApplicable(ca)){
                            errLabel.setText("Debit "+transferAmtDouble+" from Checking Account plus 0.50 Fee with new Balance of: "+(newBal-0.50));
                        }else{
                            errLabel.setText("Debit "+transferAmtDouble+" from Checking Account with new Balance of: "+newBal);
                        }

                    }
                }else{ // we are making a credit everything is cool here no problem.
                    if(isFeeApplicable(ca)){
                        errLabel.setText("Credit "+transferAmtDouble+" to Checking Account Plus 0.50 Fee new balance: "+(newBal-0.75));
                    }else{
                        errLabel.setText("Credit "+transferAmtDouble+" to Checking Account new balance: "+newBal);
                    }
                }
            }
            if(savingsAccRadio.isSelected()){ // debit credit to savings account
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble; // new bal after transaction

                if(transferAmtDouble<0.0){ // making a debit to savings account
                    if(newBal<0.00){// if new balance would be under 0
                        returnVal = false;
                        errLabel.setText("There is not enough money in Savings Account to complete transaction");
                    }else{// savings account has no transaction fee.
                        errLabel.setText("Debit "+transferAmtDouble+" from Savings Account with new Savings Balance of: "+newBal);
                    }
                }else{ // making a credit to savings so all good and no fee
                    errLabel.setText("Credit "+transferAmtDouble+" to Savings Account new balance: "+newBal);
                }
            }
        }
        System.out.println("Return value is: "+returnVal);
        return returnVal;
    }

    public static void completeAtmTransaction(TextField transactionAmount,String checkNumber){
        String transactionAmountString = transactionAmount.getText();
        Double transactionAmountDouble = 0.0;
        CustomerAccount ca = Main.loggedInCustomer;
        Double newBal = 0.0;
        double checkingBalance = ca.getCheckingAccount().getAccountBalance();

        try {
            transactionAmountDouble = Double.parseDouble(transactionAmountString);
        } catch (NumberFormatException e) {
            transactionAmountDouble = 0.0;
        }
        if(checkNumber.equalsIgnoreCase("null")){// check number null so we are just making a withdrawal. from checking
            if(checkingBalance - transactionAmountDouble > 0) { // if enough money to make transaction
                newBal = checkingBalance - transactionAmountDouble;
                creditDebitCheckingAccount(ca.getCheckingAccount(),(0.00-transactionAmountDouble),"Atm Withdrawal");

            }else{
                System.out.println("insufficient funds");
            }
            System.out.println("newBal = " + newBal);
        }else{ // there is a check number that isn't null so we are making a deposit
            // create check object and make the transaction
            Check depositCheck = new Check(checkNumber,ca.getCheckingAccount().getCheckingAcctIDString(),DataEntryDriver.getDateString(),transactionAmountDouble,true);

            System.out.println(depositCheck.toString());
            // make the transaction remember to add fee later and check if gold account and not. I might make a method that does this
            creditDebitCheckingAccount(ca.getCheckingAccount(),transactionAmountDouble,"Atm Deposit");
            ca.addCheckObj(depositCheck); // add the check object. and the creditDebit method handles the Transaction objects

        }

        // both statements above are a transaction to or from the checking account so apply transaction fee as needed
        // applying the fee if needed
        if(isFeeApplicable(ca)){ // if the account meets the requirements for a fee
            applyFeeOnAccount(ca,"transaction");// then apply the fee this adds a Transaction object
        }

        // print some debug data
        ca.printChecks();
        ca.printTransactions();

    }


    public static void completeTransaction(TextField transferAmt, CheckBox transferFundsCheckBox, RadioButton checkingAccRadio, RadioButton savingsAccRadio, Label errLabel){
        String transferAmtString = transferAmt.getText();
        double transferAmtDouble=0.0;
        CustomerAccount ca = Main.customerAccount;

        boolean backupSavingEnabled = false;

        if(ca.hasCheckingAccount()){
            backupSavingEnabled = ca.getCheckingAccount().isBackupSavingsEnabled();
        }

        try {
            transferAmtDouble = Double.parseDouble(transferAmtString);
        } catch (NumberFormatException e) {
            transferAmtDouble = 0.0;
        }

        if(transferFundsCheckBox.isSelected()){ // transferring funds from account
            double checkingBalance = ca.getCheckingAccount().getAccountBalance();
            double simpleSavingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();

            if(checkingAccRadio.isSelected()){ // transfer from checking to savings APPLY FEE TO CHECKING
                if(transferAmtDouble<checkingBalance){
                    transferMoneyBetweenCheckingSaving(ca,transferAmtDouble,true); // make the transfer
                    // then apply fee using methods if needed
                    if(isFeeApplicable(ca)){ // if the account meets the requirements for a fee
                        applyFeeOnAccount(ca,"transfer");// then apply the transfer fee. Remember the String
                        // value on feeType
                    }
                }

            }else{ // transfer from savings  to checking
                double newBal = simpleSavingsBalance-transferAmtDouble;
                if(transferAmtDouble<simpleSavingsBalance){
                    transferMoneyBetweenCheckingSaving(ca,transferAmtDouble,false); // transfer money from savings to checking

                    // check and apply fee as needed
                    if(isFeeApplicable(ca)){ // if the account meets the requirements for a fee
                        applyFeeOnAccount(ca,"transfer");// then apply the transfer fee
                        System.out.println("Transfer fee");
                    }
                }
            }

        }else{// transfer funds is not selected so basic credit debit actions
            if(checkingAccRadio.isSelected()){ // credit debit checking account
                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;
                if(transferAmtDouble<0.0){  // we are making a debit
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

                                System.out.println("debit to checking account but backup saving used no fee yet");

                            }
                        }// otherwise the button is disabled and you can't complete transaction

                    }else{// else there is enough money in checking account to make debit
                        creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,""); // make the transaction

                        // check and apply fee as needed
                        if(isFeeApplicable(ca)){ // if the account meets the requirements for a fee
                            applyFeeOnAccount(ca,"transaction");// then apply the transaction fee
                            System.out.println("Transaction fee");
                        }
                    }
                }else{ // we are making a credit to checking account .50 fee
                    creditDebitCheckingAccount(ca.getCheckingAccount(),transferAmtDouble,"");

                    // check and apply fee as needed
                    if(isFeeApplicable(ca)){ // if the account meets the requirements for a fee
                        applyFeeOnAccount(ca,"transaction");// then apply the transaction fee
                        System.out.println("Transaction fee");
                    }

                }
            }
            if(savingsAccRadio.isSelected()){ // making an action on savings account
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                // no fee on savings accounts
                if(transferAmtDouble<0.0){ // making a debit to savings account
                    if(newBal>0.00){// if new balance is not negative
                        creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble,"");
                        System.out.println("debit on savings account ");
                    }

                }else{// making a credit to savings account Add as much as you want $$$
                    creditDebitSavingsAccount(ca.getSimpleSavingsAccount(),transferAmtDouble,"");
                    System.out.println("credit on savings account");
                }

            }

        }

        // some debug data
        ca.printChecks();
        ca.printTransactions();

    }



    // just for simple withdraw and deposit on savings account. No Transaction fee from what I read
    // auto adds transaction object to customerAccount
    public static void creditDebitSavingsAccount(SavingsAccount savingsAccount, double transactionAmount,String desc){
        //System.out.println("Crediting a savings account: "+savingsAccount.toString());
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
        //System.out.println("Transaction added: "+savingsAccount.toString());
        //System.out.println("taking: "+transactionAmount +" on Savings account");

    }


    // used to post process checks
    public static void debitCheckingAccountWithCheckObject(CustomerAccount ca, Check checkObject){
        Transaction transaction = new Transaction("W",checkObject.getCheckAmount(),"Check Debit","C");
        transaction.setDate(checkObject.getCheckDate());
        CheckingAccount checkingAccount=ca.getCheckingAccount();
        double debitAmount = 0-checkObject.getCheckAmount();
        checkingAccount.debitCreditAccount(debitAmount);
        ca.addTransactionObject(transaction);


    }

    public static void creditDebitCheckingAccount(CheckingAccount checkingAccount, double transactionAmount,String desc){
        //System.out.println("Credit debit checking account: "+checkingAccount.toString());
        Transaction transaction = new Transaction();
        transaction.setTransactionAccount("C");

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
    }



    public static void applyFeeOnAccount(CustomerAccount ca,String feeType){
        // feeType shall be: transaction, transfer, stop, over
        // so from reading instructions the fees are as listed
        // Transaction Fee for basic accounts of .50
        // Transfer Fee of 0.75 per transfer
        // Stop Payment fee of $15.00
        // OverDraft fee of $20

        CheckingAccount checkingAccount = ca.getCheckingAccount();
        double originalCheckingBalance = ca.getCheckingAccount().getAccountBalance();

        Transaction transaction = new Transaction();
        transaction.setTransactionType("F");
        transaction.setTransactionAccount("C");

        if(feeType.equalsIgnoreCase("transaction")){
            // apply the transaction fee
            transaction.setAmount(0.50);
            transaction.setDescription("Transaction Fee");
            checkingAccount.debitCreditAccount(-0.50);
        }
        if(feeType.equalsIgnoreCase("transfer")){
            // apply transfer fee
            transaction.setAmount(0.75);
            transaction.setDescription("Transfer Fee");
            checkingAccount.debitCreditAccount(-0.75);
        }
        if(feeType.equalsIgnoreCase("stop")){
            // apply stop payment fee
            transaction.setAmount(15.00);
            transaction.setDescription("Stop Payment Fee");
            checkingAccount.debitCreditAccount(-15.00);
        }
        if(feeType.equalsIgnoreCase("over")){
            // apply overdraft fee
            transaction.setAmount(20.00);
            transaction.setDescription("Overdraft Fee");
            checkingAccount.debitCreditAccount(-20.00);
        }

        ca.addTransactionObject(transaction);

        double endingCheckingBalance = ca.getCheckingAccount().getAccountBalance();
        if(originalCheckingBalance>0.00 && endingCheckingBalance<0.00){ // if the fee we just placed overdraws account apply another fee :)
            // the fee on checking account overdrew the account so now we slap on overdraft fee
            applyFeeOnAccount(ca,"over");
        }


    }



    // This only transfers between simple saving and checking The validity of the transfer should have been verified
    // before this method is called. and fee applied externally not in this method use the applyFee method
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

        }

    }




    // returns true if the account is a standard checking account or if Gold Diamond that isn't meeting min balance
    // if Gold diamond not meeting min bal, treat as if its a standard account.
    public static boolean isFeeApplicable(CustomerAccount ca){
        boolean returnVal = false;
        double checkingBalance = ca.getCheckingAccount().getAccountBalance();

        boolean isGold = ca.getCheckingAccount().isGoldAccount();
        boolean minBalNotMet = false;

        if(isGold){ // this can only turn true if it's a gold account and min bal not met
            if(checkingBalance<1000){
                minBalNotMet = true;
            }
        }


        if(!isGold || minBalNotMet){
            returnVal = true;
        }


        return returnVal;
    }











































// End FINANCE DRIVER CLASS
}




