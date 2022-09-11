package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class contactAdd {

    @FXML
    private TextField addName;

    @FXML
    private TextField addStreet;

    @FXML
    private TextField addCity;

    @FXML
    private TextField addZip;

    @FXML
    private ComboBox<?> addState;

    @FXML
    private ComboBox<?> addCountry;

    @FXML
    private TextField addTel;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    public void saveAddCust(ActionEvent event) throws Exception{

    }

    public void cancelAddCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
