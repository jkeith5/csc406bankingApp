package BankingApp;

import java.io.Serializable;

public class Check implements Serializable {

    public String checkNumber;
    public String checkingAcctID;
    public String checkDate;
    public double checkAmount;
    public boolean checkProcessed;
    public String checkStatus; // we can use this to put a stop payment when we get that far so normal or hold

    public Check(){
        //
        setCheckStatusNormal();
    }

    public Check(String checkNumber, String checkingAcctID, String checkDate, double checkAmount, boolean checkProcessed) {
        setCheckNumber(checkNumber);
        setCheckingAcctID(checkingAcctID);
        setCheckDate(checkDate);
        setCheckAmount(checkAmount);
        setCheckProcessed(checkProcessed);

        setCheckStatusNormal();
    }

    public Check(String checkNumber, String checkingAcctID, String checkDate, String checkAmount, String checkProcessed) {
        setCheckNumber(checkNumber);
        setCheckingAcctID(checkingAcctID);
        setCheckDate(checkDate);
        setCheckAmount(checkAmount);
        setCheckProcessed(checkProcessed);

        setCheckStatusNormal();
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

    private void setCheckStatusNormal(){
        this.checkStatus="normal";
    }


    public String getCheckStatus(){
        return this.checkStatus;
    }


    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = DataEntryDriver.fixDateString(checkDate);
    }

    public double getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(double checkAmount) {
        this.checkAmount = checkAmount;
    }
    public void setCheckAmount(String checkAmount) {
        try {
            this.checkAmount = Double.parseDouble(checkAmount);
        } catch (NumberFormatException e) {
            this.checkAmount = 0.0;
            Main.out.println(Main.getDateTimeString()+" Error in setting check amount "+e.toString());
        }
    }

    public boolean isCheckProcessed() {
        return checkProcessed;
    }

    public void setCheckProcessed(boolean checkProcessed) {
        this.checkProcessed = checkProcessed;
    }
    public void setCheckProcessed(String checkProcessed) {
        this.checkProcessed = Boolean.parseBoolean(checkProcessed);
    }


    // changes check status to hold to indicate that a stop payment was placed on the check.
    public boolean stopCheckPayment(){
        String result = "";
        if(!checkProcessed){
            this.checkStatus="hold";
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String toString(){
        return "Check{" +
                "checkNumber='" + checkNumber + '\'' +
                ", checkingAcctID=" + checkingAcctID +
                ", checkDate='" + checkDate + '\'' +
                ", checkAmount=" + checkAmount +
                ", checkProcessed=" + checkProcessed +
                ", checkStatus='" + checkStatus + '\'' +
                '}';
    }

    public String toStringPrettyPrint(){
        return "Check Number: "+checkNumber+
                " Checking ID: "+checkingAcctID+
                " Check Date: "+checkDate+
                " Check Amount: "+checkAmount+
                " Processed: "+checkProcessed+
                " Check Status: "+checkStatus;
    }

    // as would appear in csv file
    public String toStringTableFormat(){
        String result = String.format("CheckNumber: %-7.7s CheckingID: %-7.7s Date: %-11.11s Amount: %-10.2f" +
                " Processed: %-5.5b",checkNumber,checkingAcctID,checkDate,checkAmount,checkProcessed);

        return result;
    }


}
