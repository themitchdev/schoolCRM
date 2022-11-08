package controller;

import DAO.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appt;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private TextField customerId;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> type;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label AMlabel;

    @FXML
    private Label PMlabel;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private Button saveUpdateApptBtn;

    @FXML
    private Button cancelUpdateApptBtn;

    @FXML
    void cancelUpdateAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    public String pickAMorPM(TextField textfieldName) throws ParseException {
        int time = Integer.parseInt(textfieldName.getText().split(":")[0]);

        if (time == 12 || time < 6) {
            return "PM";
        }
        return "AM";
    }

    void getSelectedAppt(Appt appt) throws ParseException {
        if (appt != null) {
            title.setText(appt.getTitle());
            description.setText(appt.getDescription());
            apptlocation.setText(appt.getLocation());
            contactComboBOx.getSelectionModel().select(appt.getContact());
            customerId.setText(String.valueOf(appt.getCustId()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate date = LocalDate.parse(Utilities.getDateFromAppt(appt, 0), formatter);
            startDate.setValue(date);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate date2 = LocalDate.parse(Utilities.getDateFromAppt(appt, 1), formatter2);
            endDate.setValue(date2);
            startTime.setText(Utilities.getTimeFromAppt(appt, 0));
            endTime.setText(Utilities.getTimeFromAppt(appt, 1));
            AMlabel.setText(pickAMorPM(startTime));
            PMlabel.setText(pickAMorPM(endTime));
            //System.out.

//            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//            LocalDate date2 = LocalDate.parse(Utilities.getDateFromAppt(appt,1), formatter2);
//            endDate.setValue(date2);
//            endTime.setText(Utilities.getTimeFromAppt(appt));


        }
    }

    @FXML
    void saveUpdateAppt(ActionEvent event) {

    }

    @FXML
    public void initialize() throws ParseException {


    }

    @FXML
    public void selectAMPM(KeyEvent keyEvent) {
        int startTime1 = Integer.parseInt(startTime.getText().split(":")[0]);
        int endTime2 = Integer.parseInt(endTime.getText().split(":")[0]);

        if (startTime1 == 12 || startTime1 < 6) {
            AMlabel.setText("PM");
        }

        if (startTime1 < 12 && startTime1 > 6) {
            AMlabel.setText("AM");
        }

        if (endTime2 == 12 || endTime2 < 6) {
            PMlabel.setText("PM");
        } else {
            PMlabel.setText("AM");
        }
    }


}

