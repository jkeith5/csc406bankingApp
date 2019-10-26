package BankingApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataEntryDriver {
    File file;//this contains the file name of the related data file
    BufferedWriter bufferedWriter;

    public DataEntryDriver(File file) {
        this.file = file;
    }

    public void printData(ArrayList<String> data){//this function pushes data to the local file as directed above

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            for (int i=0; i<data.size();i++){
                if (data.size() - 1 == i) {
                    bufferedWriter.write(data.get(i) + "\n");
                } else {
                    bufferedWriter.write(data.get(i) + ", ");
                }

            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // this method will create and return an ArrayList of CustomerAccount objects from the csv files located
    // in the Resources Directory and then save it as a file in the Resources folder
    public static void createCustomerAccountsArray(){
        ArrayList<CustomerAccount> result = new ArrayList<CustomerAccount>();
        // just creating a few temp objects for now

        CustomerAccount acc1 = new CustomerAccount("548-68-8745","Bob","Jones",
                "123 nowhere drive","WonderLand","KS","58744","1950/04/22","2899653222933490");

        CustomerAccount acc2 = new CustomerAccount("687-69-8941","Tom","Diddle",
                "7487 somewhere lane","St Mosis","MO","32568","2007/08/08","4856523569854785");

        CustomerAccount acc3 = new CustomerAccount("987-70-9747","Tim","Toe",
                "1245 anywhere street Apt. 88","Bullhead","AZ","87459","2018/12/18");

        //
        SavingsAccount saving1 = new SavingsAccount(acc1.custID,"1",457.58,0.022,"1969/02/17",true,"2022/05/18");
        SavingsAccount saving2 = new SavingsAccount(acc2.custID,"2",7485.24,0.028,"2007/08/08",true,"2019/12/11");
        SavingsAccount saving3 = new SavingsAccount(acc3.custID,"3",274.12,0.017,"2018/12/18",false);

        CheckingAccount checking1 = new CheckingAccount(acc1.custID,"1",147.58,"2019/05/05",false,true);
        CheckingAccount checking2 = new CheckingAccount(acc2.custID,"2",5787.54,acc2.dateCreated,true,true);
        CheckingAccount checking3 = new CheckingAccount(acc3.custID,"3",57.14,acc3.dateCreated,false,false,3);


        LoanAccount loan1 = new LoanAccount("CCL",acc1.custID,10000,874.58,0.018,false);
        LoanAccount loan2 = new LoanAccount("STL",acc2.custID,8750.00,1244.58,0.011,true);

        Check check1 = new Check("150","1","2019/10/20",287.89,false);
        Check check2 = new Check("478","2","2019/10/22",2145.58,true);


        // could read each file in and then do this in a loop for each account to set the data
        // would be easier to just make the checks database include the custID field instead of having to lookup the acct number

        // tie them together
        acc1.addSavingsAccount(saving1);
        acc1.addCheckingAccount(checking1);
        acc1.addLoanAccountObject(loan1);
        acc1.addCheckObj(check1);

        acc2.addSavingsAccount(saving2);
        acc2.addCheckingAccount(checking2);
        acc2.addLoanAccountObject(loan2);
        acc2.addCheckObj(check2);

        acc3.addSavingsAccount(saving3);
        acc3.addCheckingAccount(checking3);


        result.add(acc1);
        result.add(acc2);
        result.add(acc3);



        System.out.println("\n\n");

        System.out.println(acc1.toString());
        System.out.println("\n\n");
        String acc1String = acc1.toString();

        String acc1StringArr[] = acc1String.split("~");
        List<String> acc1ArrList = new ArrayList<String>();
        acc1ArrList = Arrays.asList(acc1StringArr);

        for(String s:acc1ArrList){
            System.out.println(s);
        }

        serializeArrayListToFile(result);
    }

    public static void serializeArrayListToFile(ArrayList<CustomerAccount> customerAccounts){

        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("src/Resources/customerDatabase"));

            for(CustomerAccount ca:customerAccounts){
                objectOutputStream.writeObject(ca);
            }
            objectOutputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static ArrayList<CustomerAccount> readFileToCustomerAccountsArrayList() {
        ArrayList<CustomerAccount> result = new ArrayList<CustomerAccount>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/Resources/customerDatabase"))) {
            while (true) {
                Object read = objectInputStream.readObject();
                if(read == null){
                    break;
                }
                CustomerAccount customerAccountRead = (CustomerAccount) read;
                result.add(customerAccountRead);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        System.out.println("READ IN FILE AS ARRAYLIST\n\n\n");

        for(CustomerAccount ca: result){
            System.out.println(ca.toString());
        }

        System.out.println("End of read in file\n\n\n");

        System.out.println("Testing\n");

        System.out.println(result.get(0).toString());

        double test1 = result.get(0).getSavingsAccount().accountBalance;
        double test2 = test1 - 457.00;

        String idStrip = result.get(0).getCustID().replace("-","");
        System.out.println(idStrip);

        // should be equal to 0.58
        System.out.println(Math.round(test2*100.0)/100.0);



        return result;
    }


    public static CustomerAccount getCustomerAccountFromCustomerID(ArrayList<CustomerAccount> accountsList,String customerID){
        CustomerAccount result = new CustomerAccount();
        String searchIDStripped = customerID.replace("-","");

        for(CustomerAccount ca:accountsList){
            String custIDStripped = ca.getCustID().replace("-","");
            if(custIDStripped.equals(searchIDStripped)){
                result = ca;
            }
        }

        return result;
    }


}
