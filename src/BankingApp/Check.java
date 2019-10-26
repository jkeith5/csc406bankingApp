package BankingApp;

import java.io.Serializable;

public class Check implements Serializable {

    public String checkNumber;
    public String checkingAcctID;
    public String checkDate;
    public double checkAmount;
    public boolean checkProcessed;

    public Check(){
        //
    }

    public Check(String checkNumber, String checkingAcctID, String checkDate, double checkAmount, boolean checkProcessed) {
        this.checkNumber = checkNumber;
        this.checkingAcctID = checkingAcctID;
        this.checkDate = checkDate;
        this.checkAmount = checkAmount;
        this.checkProcessed = checkProcessed;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckingAcctID() {
        return checkingAcctID;
    }

    public void setCheckingAcctID(String checkingAcctID) {
        this.checkingAcctID = checkingAcctID;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public double getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(double checkAmount) {
        this.checkAmount = checkAmount;
    }

    public boolean isCheckProcessed() {
        return checkProcessed;
    }

    public void setCheckProcessed(boolean checkProcessed) {
        this.checkProcessed = checkProcessed;
    }


    @Override
    public String toString() {
        return "Check{" +
                "checkNumber='" + checkNumber + '\'' +
                ", checkingAcctID='" + checkingAcctID + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", checkAmount=" + checkAmount +
                ", checkProcessed=" + checkProcessed +
                '}';
    }




}
