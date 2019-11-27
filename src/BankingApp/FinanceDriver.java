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

                if(transferAmtDouble>checkingBalance){ // not enough money to make transfer
                    returnVal = false;
                    errLabel.setText("There is not enough money in Checking Account to Complete Transaction! new balance: "+newBal);
                }else{

                    if(isFeeApplicable(ca)){ // Account type that gets the 0.75 transfer fee.
                        newBal = newBal -0.75;
                        String output = String.format("Transfer $%.2f from Checking to Savings with a $0.75 Transfer Fee.\n" +
                                "New Checking Balance: $%.2f\nNew Savings Balance: $%.2f",transferAmtDouble,newBal,newSavingBal);
                        errLabel.setText(output);
                    }else{ // this is a gold account above min balance so no fee
                        String output = String.format("Transfer $%.2f from Checking to Savings.\n" +
                                "New Checking Balance: $%.2f\nNew Savings Balance: $%.2f",transferAmtDouble,newBal,newSavingBal);
                        errLabel.setText(output);
                    }
                }
            }else{ // transfer from savings  to checking
                double newBal = simpleSavingsBalance-transferAmtDouble; // new savings bal
                double newCheckingBal = checkingBalance+transferAmtDouble; // new checking bal
                if(transferAmtDouble>simpleSavingsBalance){ // not enough to make the transfer
                    returnVal=false;
                    String output = String.format("There is not enough money in Savings Account to complete " +
                            "transaction! new balance: $%.2f",newBal);
                    errLabel.setText(output);
                }else{ // so what apply the .50 fee for transactions to checking account if not gold diamond and if gold diamond min bal not met?
                    if(isFeeApplicable(ca)){// if account is type with fee
                        newCheckingBal = newCheckingBal -0.75;
                        String output = String.format("Transfer $%.2f from Savings to Checking with a $0.75 Transfer Fee.\n" +
                                "New Checking Balance: $%.2f\nNew Savings Balance: $%.2f",transferAmtDouble,newCheckingBal,newBal);
                        errLabel.setText(output);
                    }else{
                        String output = String.format("Transfer $%.2f from Savings to Checking.\n" +
                                "New Checking Balance: $%.2f\nNew Savings Balance: $%.2f",transferAmtDouble,newCheckingBal,newBal);
                        errLabel.setText(output);
                    }
                }
            }
        }else{//Transfer funds is not selected.
            if(checkingAccRadio.isSelected()){ // credit debit to checking account
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
                                    // so if checking has 10 and we take 20 then newbal is -10 so you can add that
                                    // to the savings balance and then get the new savings balance then add fee if needed.
                                    double newSavingsBal = ca.getSimpleSavingsAccount().getAccountBalance()+newBal-0.50;
                                    String output = String.format("Backup Savings Warning!\nTake $%.2f from Savings." +
                                            " Plus $0.50 Transaction Fee.\nNew Checking Balance: $0.00\nNew Savings Balance: $%.2f",
                                            newBal, newSavingsBal);
                                    errLabel.setText(output);
                                }else{
                                    double newSavingsBal = ca.getSimpleSavingsAccount().getAccountBalance()+newBal;
                                    String output = String.format("Backup Savings Warning!\nTake $%.2f from Savings." +
                                                    "\nNew Checking Balance: $0.00\nNew Savings Balance: $%.2f",newBal,
                                            newSavingsBal);
                                    errLabel.setText(output);
                                }
                            }else{ // if backup saving enabled but still not enough money in either account give SOL message
                                returnVal = false;
                                String output = String.format("This transaction would overdraw the account by: $%.2f",newBal);
                                errLabel.setText(output);
                            }
                        }else{ // else no backup savings enabled
                            returnVal =false;
                            String output = String.format("This transaction would overdraw the account by: $%.2f and there" +
                                    "is no Backup Savings Enable.",newBal);
                            errLabel.setText(output);
                        }

                    }else{// if new balance is over 0 then it's good
                        if(isFeeApplicable(ca)){
                            String output = String.format("Debit $%.2f from Checking Account Plus $0.50 Transactoin Fee.\n" +
                                    "New Checking Balance: $%.2f",transferAmtDouble,(newBal-0.50));
                            errLabel.setText(output);
                        }else{
                            String output = String.format("Debit $%.2f from Checking Account.\n" +
                                    "New Checking Balance: $%.2f",transferAmtDouble,(newBal-0.50));
                            errLabel.setText(output);
                        }
                    }
                }else{ // we are making a credit everything is cool here no problem.
                    if(isFeeApplicable(ca)){ // transaction fee of 0.50
                        String output = String.format("Credit $%.2f to Checking Account Plus $0.50 Transaction Fee.\n" +
                                "New Checking Balance: $%.2f",transferAmtDouble,(newBal-0.50));
                        errLabel.setText(output);
                    }else{
                        String output = String.format("Credit $%.2f to Checking Account.\n" +
                                "New Checking Balance: $%.2f",transferAmtDouble,(newBal-0.50));
                        errLabel.setText(output);
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
                        String output = String.format("Debit $%.2f from Simple Savings Account.\n" +
                                "New Savings Balance: $%.2f",transferAmtDouble,newBal);
                        errLabel.setText(output);
                    }
                }else{ // making a credit to savings so all good and no fee
                    String output = String.format("Credit $%.2f to Simple Savings Account.\n" +
                            "New Savings Balance: $%.2f",transferAmtDouble,newBal);
                    errLabel.setText(output);
                }
            }
        }
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
            Check depositCheck = new Check(checkNumber,ca.getCheckingAccount().getCheckingAcctID(),DataEntryDriver.getDateString(),transactionAmountDouble,false);

            //System.out.println(depositCheck.toString());
            // make the transaction remember to add fee later and check if gold account and not. I might make a method that does this
            creditDebitCheckingAccount(ca.getCheckingAccount(),transactionAmountDouble,"Atm Deposit");
            ca.addCheckObj(depositCheck); // add the check object. and the creditDebit method handles the Transaction objects

        }

        // both statements above are a transaction to or from the checking account so apply transaction fee as needed
        // applying the fee if needed
        if(isFeeApplicable(ca)){ // if the account meets the requirements for a fee
            applyFeeOnAccount(ca,"transaction");// then apply the fee this adds a Transaction object
        }


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

//        // some debug data
//        ca.printChecks();
//        ca.printTransactions();

    }

    // makes a debit(-500) or credit(500) on LoanAccount Object
    // create Transaction object and record fee if late payment $75 late fee for all
    // returns the double of the amount actually processed. if -1.0 then transaction was REJECTED
    // process fee on make payment
    public static double creditDebitLoanAccount(LoanAccount loanAccount,double debitCreditAmt,String desc){
        double amountProcessed = 0.0;
        Transaction transaction = new Transaction();
        transaction.setDate(DataEntryDriver.getDateString());
        if(loanAccount.getLoanAccountType().equals("CCL")){ // action on CCL
            transaction.setTransactionAccount("CCL");
        }
        if(loanAccount.getLoanAccountType().equals("LTL")){
            transaction.setTransactionAccount("LTL");
        }
        if(loanAccount.getLoanAccountType().equals("STL")){
            transaction.setTransactionAccount("STL");
        }

        if(debitCreditAmt<0){ // making a debit on loan account
            if(loanAccount.getLoanAccountType().equals("CCL")){ // action on CCL
                transaction.setDescription("Debit to Credit Card Account");
                amountProcessed = loanAccount.debitCCLAccount(debitCreditAmt); // debit account
                // process late fee if needed

            }// you can only debit a credit card so nothing more here
        }else{ // making a credit/payment on account
            if(loanAccount.getLoanAccountType().equals("CCL")){ // action on CCL
                transaction.setDescription("Payment on Credit Card Account");
            }else{
                transaction.setDescription("Payment on Loan Account");
            }
            amountProcessed = loanAccount.makePayment(debitCreditAmt);

        }
        if(desc.length()>0){
            transaction.setDescription(desc);
        }

        if(amountProcessed>0.001){ // if amount was processed
            transaction.setAmount(amountProcessed);
            //Main.customerAccount.addTransactionObject(transaction);
        }


        return amountProcessed;
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


    // needs to create transaction
    // and add 75 to the next amount due for each month they are late
    // two ways I can use this. 1. loop over all loan accounts in main or Controller initialize and
    // then call this and find any late accounts
    // or call it manually
    public static void applyLateFeeOnLoanAccount(CustomerAccount ca,LoanAccount loanAccount){
        int monthsLate = loanAccount.isPaymentLate();
        if(monthsLate>0){
            for(int i=0;i<monthsLate;i++){ // loop over every month late and make fee
                Transaction transaction = new Transaction();
                transaction.setTransactionType("F");
                transaction.setAmount(75.00);
                transaction.setDescription("Late Fee");
                if(loanAccount.getLoanAccountType().equals("LTL")){
                    transaction.setTransactionAccount("LTL");
                }
                if(loanAccount.getLoanAccountType().equals("STL")){
                    transaction.setTransactionAccount("STL");
                }
                if(loanAccount.getLoanAccountType().equals("CCL")){
                    transaction.setTransactionAccount("CCL");
                }
                transaction.setDate(DataEntryDriver.getDateString());
                loanAccount.setAmountDue(loanAccount.getAmountDue()+75.00); // set the fee

                ca.addTransactionObject(transaction);



            }


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


        if(!isGold || minBalNotMet){ // if its not gold account or gold account under min balance
            returnVal = true;
        }


        return returnVal;
    }










































// End FINANCE DRIVER CLASS
}




