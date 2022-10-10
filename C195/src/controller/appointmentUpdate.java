package controller;

import DAO.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appt;
import model.Customer;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public String selectAMPM (Label labelName) throws ParseException {
        int time = Integer.valueOf(labelName.getText());

        if ( time == 12 || time < 6 ){
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
            startTime.setText(Utilities.getTimeFromAppt(appt));
            AMlabel.setText("11");
            String label = selectAMPM(AMlabel);
            AMlabel.setText(label);
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
        //AMlabel.setText(selectAMPM(appt));

    }

}

