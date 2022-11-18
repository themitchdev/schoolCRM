package controller;

import Utilities.JDBC;
import Utilities.Misc;
import Utilities.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Appt;
import model.Customer;
import model.DataStore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class appointmentAdd {

    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptDescription;

    @FXML
    private TextField apptLocation;

    @FXML
    private ComboBox<String> contactComboBOx;

    @FXML
    private ComboBox<String> customerNameCbox;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private DatePicker apptEndDate;

    @FXML
    private ComboBox<String> startHourCbox;

    @FXML
    private ComboBox<String> endHourCbox;

    @FXML
    private ComboBox<String> startMinCbox;

    @FXML
    private ComboBox<String> endMinCbox;

    @FXML
    private ComboBox<String> startAmPmCbox;

    @FXML
    private ComboBox<String> endAmPmCbox;

    @FXML
    private ComboBox<String> typeCbox;


    @FXML
    void cancelAddAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveAddAppt(ActionEvent event) {
//        Integer customerID = 0;
//        String title = apptTitle.getText();
//        String description = apptDescription.getText();
//        String location = apptLocation.getText();
//        String contact = contactComboBOx.getValue();
//        String customerName = customerNameCbox.getValue();
//        String type = typeCbox.getValue();
//
//        Appt appt = new Customer(customerID, name, address, state, zipcode, phone, country);
//        JDBC.saveCustomer();
//        ResultSet rs = JDBC.runStatement("SELECT Customer_ID FROM customers WHERE Customer_Name= '" + name + "'");
//        rs.next();
//        customer.setCustId(rs.getInt("Customer_ID"));
//        DataStore.addCustomer(customer);
//
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.close();
    }

    public void initialize() throws SQLException {
        startHourCbox.setItems(Time.hours);
        startMinCbox.setItems(Time.minutes);
        endHourCbox.setItems(Time.hours);
        endMinCbox.setItems(Time.minutes);
        startAmPmCbox.setItems(Time.amOrPm);
        endAmPmCbox.setItems(Time.amOrPm);
        Misc.buildCustomerNameList();
        Misc.buildApptTypeList();
        Misc.buildContactNameList();
        typeCbox.setItems(DataStore.appointmentType);
        customerNameCbox.setItems(DataStore.customerNames);
        contactComboBOx.setItems(DataStore.contactNames);

    }

}
