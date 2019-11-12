package BankingApp;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class FinanceDriver {
    // this will have all the methods that conduct financial transactions on the Customer Account objects.
    // most, if not all, methods here will be called from the Controller class, and will hold things like
    // transfer debit credit methods.



    public FinanceDriver(){ // not sure I'll need this.
        //this.customerAccount = Main.customerAccount;
    }



    public static boolean isTransferAmtValid(TextField transferAmt, CheckBox transferFundsCheckBox, RadioButton checkingAccRadio,RadioButton savingsAccRadio){
        boolean returnVal = false;
        String transferAmtString = transferAmt.getText();
        double transferAmtDouble=0.0;
        CustomerAccount ca = Main.customerAccount;

        System.out.println(ca.toString());


        try {
            transferAmtDouble = Double.parseDouble(transferAmtString);
        } catch (NumberFormatException e) {
            transferAmtDouble = 0.0;
        }
        System.out.println("transferAmt double: "+transferAmtDouble);


        if(transferFundsCheckBox.isSelected()){
            System.out.println("transfer funds: true");

            if(checkingAccRadio.isSelected()){
                System.out.println("transfer from checking to savings");
            }else{
                System.out.println("transfer from savings to checkings");
            }

        }else{
            System.out.println("transfer funds: false");

            if(checkingAccRadio.isSelected()){
                System.out.println("credit/debit checking account");
            }
            if(savingsAccRadio.isSelected()){
                System.out.println("credit/debit savings account");
            }

        }

//        if(checkingAccRadio.isSelected()){
//            System.out.println("checking acc: true");
//        }else{
//            System.out.println("checking acc: false");
//        }
//
//        if(savingsAccRadio.isSelected()){
//            System.out.println("savings acc: true");
//        }else{
//            System.out.println("savings acc: false");
//        }










        return returnVal;
    }











}
