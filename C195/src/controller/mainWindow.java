package controller;

import Utilities.JDBC;
import Utilities.Misc;
import Utilities.MyTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class mainWindow {

    @FXML
    private TableView<CustomerPerCountry> reportNumCustByCountry;

    @FXML
    private TableColumn<CustomerPerCountry, String> reportCountryCol;

    @FXML
    private TableColumn<CustomerPerCountry, Integer> reportNumCustCol;

    @FXML
    private Label totalApptLabel;

    @FXML
    private ComboBox<String> reportContactCbox;

    @FXML
    private ComboBox<String> reportTypeCbox;

    @FXML
    private ComboBox<String> reportMonthCbox;

    @FXML
    private RadioButton radioAll;

    @FXML
    private ComboBox<String> apptMonthCbox;

    @FXML
    private ComboBox<String> apptWeekCbox;

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
    private TableColumn<Appt, Integer> reportApptIdCol;

    @FXML
    private TableColumn<Appt, String> reportCustIdCol;

    @FXML
    private TableColumn<Appt, String> reportDescripCol;

    @FXML
    private TableColumn<Appt, Timestamp> reportEndTimeCol;

    @FXML
    private TableColumn<Appt, Timestamp> reportStartTimeCol;

    @FXML
    private TableColumn<Appt, String> reportTitleCol;

    @FXML
    private TableColumn<Appt, String> reportTypeCol;

    @FXML
    private TableView<Appt> scheduleTable;

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
    public void reportFilter(){
        try {
            Integer month = reportMonthCbox.getSelectionModel().getSelectedIndex()+1;
            Integer count = 0;
            ObservableList<Appt> searchByMonth = FXCollections.observableArrayList();
            ObservableList<Appt> searchByType = FXCollections.observableArrayList();
            if(reportMonthCbox.getValue() == null){
                for(Appt appt : DataStore.getAllAppointments()){
                    if(appt.getType().contains(reportTypeCbox.getValue())){
                        count++;
                        searchByType.add(appt);
                    }
                }
            } else if(reportTypeCbox.getValue() == null) {
                for (Appt appt : DataStore.getAllAppointments()) {
                    if (appt.getStartDateTime().getMonthValue() == month || appt.getEndDateTime().getMonthValue() == month) {
                        count++;
                        searchByMonth.add(appt);
                    }
                }
            }else{
                for (Appt appt : DataStore.getAllAppointments()) {
                    if (appt.getStartDateTime().getMonthValue() == month || appt.getEndDateTime().getMonthValue() == month)
                        searchByMonth.add(appt);
                }
                for (Appt appt : searchByMonth){
                    if(appt.getType().contains(reportTypeCbox.getValue()))
                        count++;
                }
            }
            totalApptLabel.setText(String.valueOf(count));
        } catch (Exception e) {
            Misc.dialogAlertInfo("Report Filter", "You must select at least 1 filter before clicking the Search button");
        }
    }

    @FXML
    public void clearTypeCbox(){
        reportTypeCbox.setValue(null);
    }

    @FXML
    public void clearMonthCbox(){
        reportMonthCbox.setValue(null);
    }

    @FXML
    public void reportContactwithAppts(){
        ObservableList<Appt> searchResult = FXCollections.observableArrayList();
        for (Appt appt : DataStore.getAllAppointments()){
            if(appt.getContact().contains(reportContactCbox.getValue()))
                searchResult.add(appt);
        }
        scheduleTable.setItems(searchResult);
        reportCustIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        reportApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        reportTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportDescripCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedStartDateTime"));
        reportEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedEndDateTime"));

    }

    @FXML
    public void updateContact(ActionEvent event) throws IOException {

        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/customerUpdate.fxml"));
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
        Customer tempCustomer = customerTable.getSelectionModel().getSelectedItem();
        if( tempCustomer != null) {
            Optional<ButtonType> btnSelected = Misc.dialogAlertConfirm("Delete Customer", "Are you sure want to delete this Customer");
            if (btnSelected.get() == ButtonType.OK){
                if(JDBC.isCustIdInApptTbl(tempCustomer.getCustId())){
                    List<Integer> apptIdList = JDBC.deleteAllApptWithCustId(tempCustomer.getCustId());
                    for(int id : apptIdList){
                        Appt foundAppt = DataStore.searchApptById(id);
                        DataStore.deleteAppt(foundAppt);
                    }
                }
                JDBC.deleteCustomerSQL(tempCustomer);
                DataStore.deleteCustomer(tempCustomer);

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
    void updateAppt(ActionEvent event) throws IOException, ParseException, SQLException {
       if(apptTable.getSelectionModel().getSelectedItem() != null) {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("../view/appointmentUpdate.fxml"));
           loader.load();

           appointmentUpdate myController = loader.getController();
           myController.getSelectedAppt(apptTable.getSelectionModel().getSelectedItem());

           Stage stage = new Stage();
           stage.initModality(Modality.WINDOW_MODAL);
           stage.initOwner(((Button) event.getSource()).getScene().getWindow());

           stage.setScene(new Scene(loader.getRoot(), -1, -1));
           stage.setTitle("Update Contact");
           //stage.setScene(scene);
           stage.show();
       }else{
           Misc.dialogAlertInfo("Appointment Update", "You must select a row before clicking the Update button");

       }
}

    @FXML
    void deleteAppt(ActionEvent event) throws Exception {
        if(apptTable.getSelectionModel().getSelectedItem() != null) {
            Optional<ButtonType> btnSelected = Misc.dialogAlertConfirm("Delete Appointment", "Are you sure want to delete this Appointment");
            if (btnSelected.get() == ButtonType.OK){
                Appt appointment = apptTable.getSelectionModel().getSelectedItem();
                JDBC.deleteAppointmentSQL(appointment.getId());
                DataStore.deleteAppt(appointment);
            }
        }else{
            Misc.dialogAlertInfo("Delete Customer", "You must select a row before clicking the Delete button");

        }

    }

    @FXML
    void filterShowAll() {
        System.out.println("All selected");
        apptTable.setItems(DataStore.getAllAppointments());
        apptMonthCbox.setDisable(true);
        apptWeekCbox.setDisable(true);
    }

    @FXML
    void filterByMonth() {
        System.out.println("Month selected");
        apptMonthCbox.setDisable(false);
        apptWeekCbox.setDisable(true);
        apptMonthCbox.fireEvent(new ActionEvent());

    }

    @FXML
    void filterMonthSelected(){
        try {
            Integer month = apptMonthCbox.getSelectionModel().getSelectedIndex()+1;
            ObservableList<Appt> searchResult = FXCollections.observableArrayList();
            for(Appt appt : DataStore.getAllAppointments()){
                if(appt.getStartDateTime().getMonthValue() == month || appt.getEndDateTime().getMonthValue() == month){
                    searchResult.add(appt);
                }
            }
            apptTable.setItems(searchResult);
        } catch (Exception e) {}
    }

    @FXML
    void filterByWeek() {
        System.out.println("Week selected");
        apptMonthCbox.setDisable(true);
        apptWeekCbox.setDisable(false);
        apptWeekCbox.fireEvent(new ActionEvent());
    }

    @FXML
    void filterWeekSelected(){
        try {
            Integer week = apptWeekCbox.getSelectionModel().getSelectedIndex();
            ObservableList<Appt> searchResult = FXCollections.observableArrayList();

            for(Appt appt : DataStore.getAllAppointments()){
                LocalDateTime apptStartDate = appt.getStartDateTime().toLocalDateTime();
                LocalDateTime apptEndDate = appt.getEndDateTime().toLocalDateTime();
                LocalDateTime weekStartDate = DataStore.getAllWeeks().get(week).getStartDate();
                LocalDateTime weekEndDate = DataStore.getAllWeeks().get(week).getEndDate();
                if(apptStartDate.isAfter(weekStartDate) && apptEndDate.isBefore(weekEndDate)){
                    searchResult.add(appt);
                }
            }
            apptTable.setItems(searchResult);
        } catch (Exception e) {


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
                                       MyTime.fromSQLtoUTC(apptResultSet.getTimestamp("Start")),
                                       MyTime.fromSQLtoUTC(apptResultSet.getTimestamp("End")),
                                       MyTime.fromSQLtoUserTime(apptResultSet.getTimestamp("Start")).toLocalDate(),
                                       MyTime.fromSQLtoUserTime(apptResultSet.getTimestamp("Start")).toLocalTime(),
                                       MyTime.fromSQLtoUserTime(apptResultSet.getTimestamp("End")).toLocalDate(),
                                       MyTime.fromSQLtoUserTime(apptResultSet.getTimestamp("End")).toLocalTime(),
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
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedStartDateTime"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("formattedEndDateTime"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptUsertIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        ResultSet rsContact = JDBC.runStatement("SELECT * FROM contacts");
        while (rsContact.next()){
            DataStore.addContact(new Contact(rsContact.getInt("Contact_ID"),
                                             rsContact.getString("Contact_Name"),
                                             rsContact.getString("Email")));

        }

        LocalDate myDate = LocalDate.now(ZoneId.systemDefault());
        Integer myYear = myDate.getYear();
        myDate = LocalDate.of(myYear, 1, 1);
        while(!(myDate.getDayOfWeek() == DayOfWeek.SUNDAY)){
            myDate = myDate.minusDays(1);
        }
        LocalDate myDate2 = LocalDate.of(myYear, 12, 31);
        while(!(myDate2.getDayOfWeek() == DayOfWeek.SATURDAY)){
            myDate = myDate.plusDays(1);
        }

        int i = 1;
        while (!myDate.isAfter(myDate2)){
            LocalDate startDate = myDate;
            LocalDate endDate = myDate.plusWeeks(1).minusDays(1);
            DataStore.weekStringList.add(startDate.format(DateTimeFormatter.ofPattern("MM/dd/yyy"))+" to "+endDate.format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            LocalDateTime startDT = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0);
            LocalDateTime endDT = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 23, 59);
            DataStore.addWeekToList(new Week(startDT, endDT));
            myDate = myDate.plusWeeks(1);
            i++;
        }

        radioAll.setSelected(true);
        radioAll.fireEvent(new ActionEvent());

        apptWeekCbox.setItems(DataStore.weekStringList);
        apptMonthCbox.setItems(DataStore.months);
        Misc.buildContactNameList();
        reportContactCbox.setItems(DataStore.contactNames);
        reportMonthCbox.setItems(DataStore.months);

        Misc.buildApptTypeList();

        reportTypeCbox.setItems(DataStore.appointmentType);

        ObservableList<CustomerPerCountry> countPerCountry = FXCollections.observableArrayList();
        for(String country : DataStore.countries){
            countPerCountry.add(new CustomerPerCountry(country, 0));
        }
        for (Customer customer : DataStore.getAllCustomers()){
            System.out.println(customer.getCountry());
            switch (customer.getCountry()){
                case "U.S":
                    countPerCountry.get(0).setCustomerCounter(countPerCountry.get(0).getCustomerCounter()+1);
                    break;
                case "UK":
                    countPerCountry.get(1).setCustomerCounter(countPerCountry.get(1).getCustomerCounter()+1);
                    break;
                case "Canada":
                    countPerCountry.get(2).setCustomerCounter(countPerCountry.get(2).getCustomerCounter()+1);
                    break;

            }
        }

        reportNumCustByCountry.setItems(countPerCountry);
        reportCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        reportNumCustCol.setCellValueFactory(new PropertyValueFactory<>("customerCounter"));

        Appt appt = DataStore.getAllAppointments().stream()
                .filter( a -> ChronoUnit.MINUTES.between(a.getStartTime(), LocalTime.now()) <= 15)
                .findFirst().orElse(null);

        if(!(appt == null)){
            Misc.dialogAlertInfo("Appointment  Alert", "You have an appointment with " + "Appointment ID" + appt.getId() + " Starting on: " + appt.getStartDate() + " at " + appt.getStartTime());
        }else{
            Misc.dialogAlertInfo("Appointment Alert", "There are no upcoming appointments");
        }


    }
}
