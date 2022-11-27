package controller;

import Utilities.JDBC;
import Utilities.Login;
import Utilities.Misc;
import Utilities.MyTime;
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


public class appointmentUpdate {

    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptDescription;

    @FXML
    private TextField apptLocation;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private DatePicker apptEndDate;

    @FXML
    private ComboBox<String> typeCbox;

    @FXML
    private ComboBox<String> startHourCbox;

    @FXML
    private ComboBox<String> endHourCbox;

    @FXML
    private ComboBox<String> startMinCbox;

    @FXML
    private ComboBox<String> endMinCbox;

    @FXML
    private ComboBox<String> customerNameCbox;

    @FXML
    private TextField apptCustId;

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
    void cancelUpdateAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private Appt apptFromMainForm;

    @FXML
    void saveUpdateAppt(ActionEvent event) throws Exception {
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

        boolean isTrue= true;
        while (isTrue) {
            try {
                Integer apptID = apptFromMainForm.getId();
                title = apptTitle.getText();
                description = apptDescription.getText();
                location = apptLocation.getText();
                contact = contactComboBox.getValue();
                type = typeCbox.getValue();
                custId = Integer.parseInt(apptCustId.getText());
                userId = Login.getLoggedInUserID();
                startDate = apptStartDate.getValue();
                startTime = LocalTime.of(Integer.parseInt(startHourCbox.getValue()), Integer.parseInt(startMinCbox.getValue()));
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

                //System.out.println("New appt time is: " + appt.getStartDateTime());
                if (!MyTime.isApptOverlap(appt)) {
                    JDBC.updateApptSQL(appt);


                    Integer custObjIndex = DataStore.getAllAppointments().indexOf(apptFromMainForm);
                    DataStore.updateAppt(custObjIndex, appt);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    break;
                }
            } catch (Exception e) {
                Misc.dialogAlertInfo("Input Error", "Invalid input or blank input in form");
                System.out.println(e);
                isTrue = false;
            }
        }

    }

    void getSelectedAppt(Appt appt) throws SQLException {
        apptFromMainForm = appt;
        apptTitle.setText(appt.getTitle());
        apptDescription.setText(appt.getDescription());
        apptLocation.setText(appt.getLocation());
        contactComboBox.getSelectionModel().select(appt.getContact());
        customerNameCbox.getSelectionModel().select(JDBC.getCustNameFromCustId(appt.getCustId()));
        apptStartDate.setValue(appt.getStartDate());
        apptEndDate.setValue(appt.getEndDate());
        startHourCbox.getSelectionModel().select(String.format("%02d", appt.getStartTime().getHour()));
        startMinCbox.getSelectionModel().select(String.format("%02d", appt.getStartTime().getMinute()));
        endHourCbox.getSelectionModel().select(String.format("%02d", appt.getEndTime().getHour()));
        endMinCbox.getSelectionModel().select(String.format("%02d", appt.getEndTime().getMinute()));
        typeCbox.getSelectionModel().select(appt.getType());
        customerNameCbox.fireEvent(new ActionEvent());

    }

    @FXML
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

