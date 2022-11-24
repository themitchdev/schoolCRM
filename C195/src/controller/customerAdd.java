package controller;

import Utilities.JDBC;
import Utilities.Misc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.DataStore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerAdd {
    @FXML
    private TextField addName;

    @FXML
    private TextField addAddress;

    @FXML
    private TextField addZip;

    @FXML
    private ComboBox<String> addState;

    @FXML
    private ComboBox<String> addCountry;

    @FXML
    private TextField addTel;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    public void saveCustomer(ActionEvent event) throws Exception{
        boolean isTrue= true;
        while (isTrue) {
            try {
                Integer customerID = 0;
                String name = addName.getText();
                String address = addAddress.getText();
                String state = addState.getValue();
                String zipcode = addZip.getText();
                String phone = addTel.getText();
                String country = addCountry.getValue();

                Customer customer = new Customer(customerID, name, address, state, zipcode, phone, country);
                JDBC.saveCustomer(customer);
                DataStore.addCustomer(customer);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                break;
            } catch (Exception e) {
                Misc.dialogAlertInfo("Input Error", "Invalid input or blank input in form");
                isTrue = false;
            }
        }

    }

    public void cancelAddCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setStateComboBox(ActionEvent event) {
        String selected = addCountry.getSelectionModel().getSelectedItem();
        System.out.println(selected);
        switch (selected) {
            case "U.S":
                addState.setItems(DataStore.usStates);
                break;
            case "UK":
                addState.setItems(DataStore.ukRegions);
                break;
            case "Canada":
                addState.setItems(DataStore.caProvinces);
                break;
        }
    }

    @FXML
    public void initialize() throws SQLException {
        addCountry.setItems(DataStore.countries);



    }

}
