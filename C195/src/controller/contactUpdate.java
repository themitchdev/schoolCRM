package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.sql.SQLException;

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
    private ComboBox<String> updateState;

    @FXML
    private ComboBox<String> updateCountry;

    @FXML
    private TextField updateTel;

    public void cancelUpdateCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void getSelectedCustomer(Customer customer) {

        if (customer != null){
            updateName.setText(customer.getCustName());
            updateStreet.setText(String.valueOf(customer.getAddress()));
            updateState.getSelectionModel().select(customer.getState());
            updateCountry.getSelectionModel().select(customer.getCountry());
            updateZip.setText(customer.getZipcode());
            updateTel.setText(customer.getPhone());

            String selected = updateCountry.getSelectionModel().getSelectedItem();
            switch (selected) {
                case "U.S":
                    updateState.setItems(DAO.Utilities.usStates);
                    break;
                case "UK":
                    updateState.setItems(DAO.Utilities.ukRegions);
                    break;
                case "Canada":
                    updateState.setItems(DAO.Utilities.caProvinces);
                    break;
            }
        }
    }

    public void setStateComboBox(ActionEvent event) {
        String selected = updateCountry.getSelectionModel().getSelectedItem();
        System.out.println(selected);
        switch (selected) {
            case "U.S":
                updateState.setItems(DAO.Utilities.usStates);
                break;
            case "UK":
                updateState.setItems(DAO.Utilities.ukRegions);
                break;
            case "Canada":
                updateState.setItems(DAO.Utilities.caProvinces);
                break;
        }


    }


    public void initialize() throws SQLException {

    }
}
