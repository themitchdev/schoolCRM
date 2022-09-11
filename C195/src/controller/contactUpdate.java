package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class contactUpdate {
    @FXML
    private TextField updateName;

    @FXML
    private TextField updateStreet;

    @FXML
    private TextField updateCity;

    @FXML
    private TextField updateZip;

    @FXML
    private ComboBox<?> updateState;

    @FXML
    private ComboBox<?> updateCountry;

    @FXML
    private TextField updateTel;

    public void cancelUpdateCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveUpdateCust(ActionEvent event) {

    }
}
