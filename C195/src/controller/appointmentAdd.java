package controller;

import Utilities.JDBC;
import Utilities.Login;
import Utilities.Misc;
import Utilities.MyTime;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appt;
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
        String title = null;
        String description = null;
        String location = null;
        String contact = null;
        String type = null;
        Integer custId = null;
        Integer userId = null;
        LocalDate startDate = null;
        LocalTime startTime = null;
        LocalDate endDate = null;
        LocalTime endTime = null;
        ZonedDateTime startDateTime = null;
        ZonedDateTime endDateTime = null;

        try {
            title = apptTitle.getText();
            description = apptDescription.getText();
            location = apptLocation.getText();
            contact = contactComboBox.getValue();
            type = typeCbox.getValue();
            custId = Integer.parseInt(apptCustId.getText());
            userId = Login.getLoggedInUserID();
            startDate = apptStartDate.getValue();
            startTime = LocalTime.of(Integer.parseInt(startHourCbox.getValue()),
                                     Integer.parseInt(startMinCbox.getValue()));
            endDate = apptEndDate.getValue();
            endTime = LocalTime.of(Integer.parseInt(endHourCbox.getValue()), Integer.parseInt(endMinCbox.getValue()));
            startDateTime = MyTime.fromUserTimetoUTC(ZonedDateTime.of(startDate, startTime, ZoneId.systemDefault()));
            endDateTime = MyTime.fromUserTimetoUTC(ZonedDateTime.of(endDate, endTime, ZoneId.systemDefault()));


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


            if (!MyTime.isApptOverlap.test(appt)) {
                JDBC.saveAppt(appt);
                DataStore.addAppt(appt);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

            }

        } catch (Exception e) {
            Misc.dialogAlertInfo("Input Error", "Invalid input or blank input in form");

        }
    }


    public void initialize() throws SQLException {
        startHourCbox.setItems(MyTime.hours);
        startMinCbox.setItems(MyTime.minutes);
        endHourCbox.setItems(MyTime.hours);
        endMinCbox.setItems(MyTime.minutes);
        Misc.buildCustomerNameList();
        Misc.buildApptTypeList();
        Misc.buildContactNameList();
        typeCbox.setItems(DataStore.appointmentType);
        customerNameCbox.setItems(DataStore.customerNames);
        contactComboBox.setItems(DataStore.contactNames);

    }

}
