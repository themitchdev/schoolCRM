package controller;

import Utilities.JDBC;
import Utilities.Misc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appt;
import model.Customer;
import model.DataStore;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Optional;

public class mainWindow {

    @FXML
    private CheckBox allChkBox;

    @FXML
    private CheckBox weekChkBox;

    @FXML
    private CheckBox monthChkBox;

    @FXML
    private Button addApptBtn;

    @FXML
    private Button addContactBtn;

    @FXML
    private TableView<Appt> apptTable;

    @FXML
    private TableColumn<Appt, String> apptContactCol;

    @FXML
    private TableColumn<Appt, Integer> apptCustdCol;

    @FXML
    private TableColumn<Appt, String> apptDescriptionCol;

    @FXML
    private TableColumn<Appt, Timestamp> apptEndTimeCol;

    @FXML
    private TableColumn<Appt, Integer> apptIdCol;

    @FXML
    private TableColumn<Appt, String> apptLocationCol;

    @FXML
    private TableColumn<Appt, Timestamp> apptStartTimeCol;

    @FXML
    private TableColumn<Appt, String> apptTitleCol;

    @FXML
    private TableColumn<Appt, String> apptTypeCol;

    @FXML
    private TableColumn<Appt, Integer> apptUsertIdCol;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerStateCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerTelCol;

    @FXML
    private TableColumn<Customer, String> customerZipCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private Button deleteApptBtn;

    @FXML
    private Button deleteContactBtn;

    @FXML
    private TableColumn<?, ?> reportApptIdCol;

    @FXML
    private TableColumn<?, ?> reportCustIdCol;

    @FXML
    private TableColumn<?, ?> reportDescripCol;

    @FXML
    private TableColumn<?, ?> reportEndTimeCol;

    @FXML
    private TableColumn<?, ?> reportStartTimeCol;

    @FXML
    private TableColumn<?, ?> reportTitleCol;

    @FXML
    private TableColumn<?, ?> reportTypeCol;

    @FXML
    private TableView<?> scheduleTable;

    @FXML
    private Button updateApptBtn;

    @FXML
    private Button updateContactBtn;

    @FXML
    public void addContact(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/customerAdd.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Add Contact");
        //stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void updateContact(ActionEvent event) throws IOException {

        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/contactUpdate.fxml"));
            loader.load();
            customerUpdate myController = loader.getController();
            myController.getSelectedCustomer(customerTable.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());

            stage.setScene(new Scene(loader.getRoot(), -1, -1));
            stage.setTitle("Update Contact");
            //stage.setScene(scene);
            stage.show();
        }else{
            Misc.dialogAlertInfo("Customer Update", "You must select a row before clicking the Update button");

        }

    }

    @FXML
    void deleteContact(ActionEvent event) throws Exception {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            Optional<ButtonType> btnSelected = Misc.dialogAlertConfirm("Delete Customer", "Are you sure want to delete this Customer");
            if (btnSelected.get() == ButtonType.OK){
                Customer customer = customerTable.getSelectionModel().getSelectedItem();
                JDBC.deleteCustomerSQL(customer);
                DataStore.deleteCustomer(customer);
            }
        }else{
            Misc.dialogAlertInfo("Delete Customer", "You must select a row before clicking the Delete button");

        }

    }

    @FXML
    void addAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/appointmentAdd.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Update Appointment");
        //stage.setScene(scene);
        stage.show();
    }

   @FXML
    void updateAppt(ActionEvent event) throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/appointmentUpdate.fxml"));
        loader.load();

        appointmentUpdate myController = loader.getController();
        myController.getSelectedAppt(apptTable.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Button) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(loader.getRoot(), -1, -1));
        stage.setTitle("Update Appointment");
        //stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteAppt(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modify Part");
        alert.setHeaderText(null);
        alert.setContentText("You must select a part before clicking the Modify button");
        alert.showAndWait();
        DataStore.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());

    }

    @FXML
    void chkBoxToggle(ActionEvent event) throws IOException {
        CheckBox chkbox = (CheckBox) event.getSource();
        if(chkbox.isSelected()) {
            switch(chkbox.getText()) {
                case "ALL":
                    monthChkBox.setSelected(false);
                    weekChkBox.setSelected(false);
                    break;
                case "Month":
                    allChkBox.setSelected(false);
                    weekChkBox.setSelected(false);
                    break;
                case "Week":
                    monthChkBox.setSelected(false);
                    allChkBox.setSelected(false);
                    break;
            }
        }
    }


    @FXML
    public void initialize() throws SQLException, ParseException {

        ResultSet customerResultSet = JDBC.runStatement("SELECT * FROM customers");
        while (customerResultSet.next()) {
            DataStore.addCustomer(new Customer(customerResultSet.getInt("Customer_ID"),
                                               customerResultSet.getString("Customer_Name"),
                                               customerResultSet.getString("Address"),
                                               JDBC.customerGetState(customerResultSet.getInt("Customer_ID")),
                                               customerResultSet.getString("Postal_Code"),
                                               customerResultSet.getString("Phone"),
                                               JDBC.customerGetCountry(customerResultSet.getInt("Customer_ID"))));
        }

        customerTable.setItems(DataStore.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        customerTelCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));


        ResultSet apptResultSet = JDBC.runStatement("SELECT * FROM appointments");
        int index = 0;
        while (apptResultSet.next()) {
            DataStore.addAppt(new Appt(apptResultSet.getInt("Appointment_ID"),
                                       apptResultSet.getString("Title"),
                                       apptResultSet.getString("Description"),
                                       apptResultSet.getString("Location"),
                                       JDBC.apptGetContactName(apptResultSet.getInt("Appointment_ID")),
                                       apptResultSet.getInt("Customer_ID"),
                                       Utilities.Time.fromSQLtoUTC(apptResultSet.getTimestamp("Start")),
                                       Utilities.Time.fromSQLtoUTC(apptResultSet.getTimestamp("End")),
                                       apptResultSet.getString("Type"),
                                       apptResultSet.getInt("User_ID")));

        }
        apptTable.setItems(DataStore.getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptCustdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptUsertIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));



    }




}
