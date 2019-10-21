package BankingApp;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML TextField fNameTextField;
    @FXML TextField lNameTextField;
    @FXML TextField socialSecTextField;
    @FXML TextField streetAddressTextField;
    @FXML TextField cityTextField;
    @FXML TextField zipCodeTextField;
    @FXML TextField stateTextField;
    @FXML
    Label successfulEntryLabel;
    @FXML Button enterButton;
    @FXML Button TellerScreen;
    @FXML Button BankManagerScreen;
    @FXML Button TellerInterAddNew;
    @FXML Button TellerInterManage;


    String fName, lName, socialSec, streetAddress, city, zipCode, state;

    @FXML
    public void handleEnterButton(javafx.event.ActionEvent event) {
        this.fName = fNameTextField.getText();
        this.lName = lNameTextField.getText();
        this.socialSec = socialSecTextField.getText();
        this.streetAddress = streetAddressTextField.getText();
        this.city = cityTextField.getText();
        this.zipCode = zipCodeTextField.getText();
        this.state = stateTextField.getText();

        Main.dataEntry(fName, lName, socialSec, streetAddress, city, zipCode, state);

        successfulEntryLabel.setText("Data storage for " + fName + " " + lName + " was successful!");
        successfulEntryLabel.visibleProperty().setValue(true);
    }

    @FXML
    public void mainInterfaceTellerButton(){
        System.out.println("hi");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("TellerInterface.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void tellerInterfaceAddNewButton(){
        System.out.println("hi");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AddNewUser.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 700, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
