package controller;

import Utilities.Misc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class appointmentAdd {

    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptDescription;

    @FXML
    private TextField apptLocation;

    @FXML
    private ComboBox<String> apptContactCboBox;

    @FXML
    private TextField apptContactID;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private DatePicker apptEndDate;

    @FXML
    private ComboBox<String> apptTypeCboBox;

    @FXML
    private Button saveAddApptBtn;

    @FXML
    private Button cancelAddApptBtn;

    @FXML
    private TextField apptStartTime;

    @FXML
    private TextField apptEndTime;

    @FXML
    private Label AMlabel;

    @FXML
    private Label PMlabel;


    @FXML
    void cancelAddAppt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveAddAppt(ActionEvent event) {
        Integer customerID = 0;
        String title = apptTitle.getText();
        String description = apptDescription.getText();
        String location = apptLocation.getText();
        String contact = apptContactCboBox.getValue();
        String contactID = apptContactID.getText();
        String type = apptTypeCboBox.getValue();



//        Customer customer = new Customer(customerID, name, address, state, zipcode, phone, country);
//        DAO.JDBC.saveCustomer(customer);
//        ResultSet rs = JDBC.runStatement("SELECT Customer_ID FROM customers WHERE Customer_Name= '" + name + "'");
//        rs.next();
//        customer.setCustId(rs.getInt("Customer_ID"));
//        DataStore.addCustomer(customer);
//
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.close();
    }
    @FXML
    public void selectAMPM(KeyEvent keyEvent) {
        int startTime1 = Integer.parseInt(apptStartTime.getText().split(":")[0]);
        int endTime2 = Integer.parseInt(apptEndTime.getText().split(":")[0]);

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



    public void initialize() throws SQLException {
        apptStartDate.setDayCellFactory(Misc.dayCellFactory);
        apptEndDate.setDayCellFactory(Misc.dayCellFactory);
    }

}
