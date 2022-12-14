package controller;

import Utilities.JDBC;
import Utilities.Misc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.DataStore;
import java.io.IOException;
import java.sql.SQLException;

/** Represents a form for updating an appointment.
 */
public class CustomerUpdate {
    @FXML
    private TextField updateName;

    @FXML
    private TextField customerID;

    @FXML
    private TextField updateAddress;

    @FXML
    private TextField updateZip;

    @FXML
    private ComboBox<String> updateState;

    @FXML
    private ComboBox<String> updateCountry;

    @FXML
    private TextField updateTel;

    private Customer customerFromMainForm;

    /** Saves data in database.
     * @param event parameter not used
     */
    public void updateCustomer(ActionEvent event) throws Exception {
        Integer custID = Integer.parseInt(customerID.getText());
        String name = updateName.getText();
        String address = updateAddress.getText();
        String state = updateState.getValue();
        String zipcode = updateZip.getText();
        String phone = updateTel.getText();
        String country = updateCountry.getValue();
        Customer customer = new Customer(custID, name, address, state, zipcode, phone, country);

        Integer custObjIndex = DataStore.getAllCustomers().indexOf(customerFromMainForm);
        DataStore.updateCustomer(custObjIndex, customer);

        JDBC.updateCustomerSQL(customer);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Closes "Update Customer" form without saving data.
     * @param event parameter not used
     */
    public void cancelUpdateCust(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Grabs the data from the selected customer in the customer table and populates the fields
     * in the Customer Update form.
     * @param customer the customer object selected from the main customer table
     */
    public void getSelectedCustomer(Customer customer) {

        if (customer != null){
            customerFromMainForm = customer;
            customerID.setText(String.valueOf(customer.getCustId()));
            updateName.setText(customer.getCustName());
            updateAddress.setText(String.valueOf(customer.getAddress()));
            updateState.getSelectionModel().select(customer.getState());
            updateCountry.getSelectionModel().select(customer.getCountry());
            updateZip.setText(customer.getZipcode());
            updateTel.setText(customer.getPhone());

            String selected = updateCountry.getSelectionModel().getSelectedItem();
            switch (selected) {
                case "U.S":
                    updateState.setItems(DataStore.usStates);
                    break;
                case "UK":
                    updateState.setItems(DataStore.ukRegions);
                    break;
                case "Canada":
                    updateState.setItems(DataStore.caProvinces);
                    break;
            }
        }
    }
    /** Sets the states combo box depending on the country selected.
     * @param event parameter not used
     */
    public void setStateComboBox(ActionEvent event) {
        String selected = updateCountry.getSelectionModel().getSelectedItem();

        switch (selected) {
            case "U.S":
                updateState.setItems(DataStore.usStates);
                break;
            case "UK":
                updateState.setItems(DataStore.ukRegions);
                break;
            case "Canada":
                updateState.setItems(DataStore.caProvinces);
                break;
        }


    }


    public void initialize() throws SQLException {

    }
}
