package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class appointmentUpdate {

    @FXML
    private TextField addName;

    @FXML
    private TextField addStreet;

    @FXML
    private TextField addCity;

    @FXML
    private ComboBox<?> addState;

    @FXML
    private TextField addCity1;

    @FXML
    private ComboBox<?> addState1;

    @FXML
    private Button saveUpdateApptBtn;

    @FXML
    private Button cancelUpdateApptBtn;

    @FXML
    void cancelUpdateAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveUpdateAppt(ActionEvent event) {

    }

}

