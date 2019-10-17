package sample;

import javafx.fxml.FXML;

import java.awt.*;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
}
