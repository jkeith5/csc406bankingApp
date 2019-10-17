package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataEntry {
    File file;//this contains the file name of the related data file
    BufferedWriter bufferedWriter;

    public DataEntry(File file) {
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
}
