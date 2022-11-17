package controller;

import Utilities.JDBC;
import Utilities.Misc;
import Utilities.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appt;
import model.Contact;
import model.DataStore;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class appointmentUpdate {

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private TextField apptlocation;

    @FXML
    private ComboBox<String> contactComboBOx;

    @FXML
    private ComboBox<String> customerNameCbox;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

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
    private ComboBox<String> type;


    @FXML
    void cancelUpdateAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveUpdateAppt(ActionEvent event) {


    }

    void getSelectedAppt(Appt appt) throws SQLException {
        title.setText(appt.getTitle());
        description.setText(appt.getDescription());
        apptlocation.setText(appt.getLocation());
        contactComboBOx.getSelectionModel().select(appt.getContact());
        customerNameCbox.getSelectionModel().select(JDBC.getCustNameFromCustId(appt.getCustId()));
        startDate.setValue(appt.getStartDate());
        endDate.setValue(appt.getEndDate());
        startHourCbox.getSelectionModel().select(String.format("%02d", appt.getStartTime().getHour()));
        startMinCbox.getSelectionModel().select(String.format("%02d", appt.getStartTime().getMinute()));
        startAmPmCbox.getSelectionModel().select(Time.formatAMorPM(appt.getStartTime()));
        endHourCbox.getSelectionModel().select(String.format("%02d", appt.getEndTime().getHour()));
        endMinCbox.getSelectionModel().select(String.format("%02d", appt.getEndTime().getMinute()));
        endAmPmCbox.getSelectionModel().select(Time.formatAMorPM(appt.getEndTime()));
        type.getSelectionModel().select(appt.getType());

    }

    @FXML
    public void initialize() throws SQLException {
        startHourCbox.setItems(Time.hours);
        startMinCbox.setItems(Time.minutes);
        endHourCbox.setItems(Time.hours);
        endMinCbox.setItems(Time.minutes);
        startAmPmCbox.setItems(Time.amOrPm);
        endAmPmCbox.setItems(Time.amOrPm);


        type.setItems(DataStore.appointmentType);


        customerNameCbox.setItems(DataStore.customerNames);


        contactComboBOx.setItems(DataStore.contactNames);

        }





}

