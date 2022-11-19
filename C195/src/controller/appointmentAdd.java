package controller;

import Utilities.JDBC;
import Utilities.Misc;
import Utilities.Time;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appt;
import model.Contact;
import model.Customer;
import model.DataStore;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class appointmentAdd {

    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptDescription;

    @FXML
    private TextField apptLocation;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private ComboBox<String> customerNameCbox;

    @FXML
    private TextField apptCustId;

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
    void getCustIdfromCustNameCbox(ActionEvent e) throws IOException, SQLException {
        String custName = customerNameCbox.getValue();
        String sql = "SELECT Customer_ID, Customer_Name FROM customers WHERE Customer_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, custName);
        ResultSet rs = ps.executeQuery();
        apptCustId.setText(rs.next() ? rs.getString("Customer_ID") : "error");
    }

    @FXML
    void cancelAddAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveAddAppt(ActionEvent event) throws SQLException {
        Integer apptID = 0;
        String title = apptTitle.getText();
        String description = apptDescription.getText();
        String location = apptLocation.getText();
        String contact = contactComboBox.getValue();
        String type = typeCbox.getValue();
        Integer custId = Integer.parseInt(apptCustId.getText());
        Integer userId = Utilities.login.getLoggedInUserID();
        LocalDate startDate = apptStartDate.getValue();
        LocalTime startTime = LocalTime.of(Integer.parseInt(startHourCbox.getValue()), Integer.parseInt(startMinCbox.getValue()));
        LocalDate endDate = apptEndDate.getValue();
        LocalTime endTime = LocalTime.of(Integer.parseInt(endHourCbox.getValue()), Integer.parseInt(endMinCbox.getValue()));
        ZonedDateTime startDateTime = ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault());
        ZonedDateTime endDateTime = ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault());;

        Appt appt = new Appt(apptID,
                             title,
                             description,
                             location,
                             contact,
                             custId,
                             startDateTime,
                             endDateTime,
                             startDate,
                             startTime,
                             endDate,
                             endTime,
                             type,
                             userId);
        JDBC.saveAppt(appt);
        DataStore.addAppt(appt);

//        ResultSet rs = JDBC.runStatement("SELECT Customer_ID FROM customers WHERE Customer_Name= '" + name + "'");
//        rs.next();
//        customer.setCustId(rs.getInt("Customer_ID"));
//
//
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
        contactComboBox.setItems(DataStore.contactNames);

    }

}
