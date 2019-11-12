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

            }else{
                double newBal = simpleSavingsBalance-transferAmtDouble;
                if(transferAmtDouble>simpleSavingsBalance){
                    returnVal=false;
                    errLabel.setText("There is not enough money in Savings account to Complete Transaction! new balance: "+newBal);
                }else{
                    errLabel.setText("transfer "+transferAmtDouble +" from savings to checkings new balance: "+newBal);
                }
            }

        }else{
            //System.out.println("transfer funds: false");
            if(checkingAccRadio.isSelected()){

                double checkingBalance = ca.getCheckingAccount().getAccountBalance();
                double newBal = checkingBalance+transferAmtDouble;

                if(transferAmtDouble<0.0){
                    if(transferAmtDouble>checkingBalance){
                        errLabel.setText("This Transaction will overdraw the Checking account by "+newBal);
                    }else{
                        errLabel.setText("debit "+transferAmtDouble+" from Checking Account new balance: "+newBal);
                    }
                }else{
                    errLabel.setText("Credit "+transferAmtDouble+" to Checking Account new balance: "+newBal);
                }
            }
            if(savingsAccRadio.isSelected()){
                double savingsBalance = ca.getSimpleSavingsAccount().getAccountBalance();
                double newBal = savingsBalance + transferAmtDouble;

                if(transferAmtDouble<0.0){
                    if(transferAmtDouble>savingsBalance){
                        errLabel.setText("This transaction will overdraw the Savings Account by "+newBal);
                    }else{
                        errLabel.setText("Debit "+transferAmtDouble+" from Savings Account new balance: "+newBal);
                    }

                }else{
                    errLabel.setText("Credit "+transferAmtDouble+" to Savings Account new balance: "+newBal);
                }

            }

        }










        return returnVal;
    }











}
