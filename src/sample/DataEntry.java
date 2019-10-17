package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class DataEntry {
    String fileDirectory;//this contains the file name of the related data file
    BufferedWriter bufferedWriter;

    public void DataEntry(String fileDirectory){
        this.fileDirectory=fileDirectory;
    }

    public void printData(ArrayList<String> data){//this function pushes data to the local file as directed above
        try {
            bufferedWriter=new BufferedWriter(new FileWriter(fileDirectory));
            for (int i=0; i<data.size();i++){
                bufferedWriter.write(data.get(i)+", ");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
