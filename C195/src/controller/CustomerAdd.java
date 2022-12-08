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
import java.sql.SQLException;

/** Represents a form for adding a customer.
 */
public class CustomerAdd {
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


    /** Saves customer form into database.
     * @param event parameter not used
     */
    public void saveCustomer(ActionEvent event) throws Exception{
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

            } catch (Exception e) {
                Misc.dialogAlertInfo("Input Error", "Invalid input or blank input in form");

            }
        }


    /** Closes the Add Customer form, data is not saved.
     * @param event parameter not used
     */
    public void cancelAddCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Sets the states combo box depending on the country selected.
     * @param event parameter not used
     */
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


    /** Initializes data for the Customer Add form
     * @param
     */
    public void initialize() throws SQLException {
        addCountry.setItems(DataStore.countries);



    }

}
