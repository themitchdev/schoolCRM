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
import java.util.List;
import java.util.Optional;

/** Represents the main form for interacting with application.
 */
public class MainWindow {

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
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, String> apptContactCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustdCol;

    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;

    @FXML
    private TableColumn<Appointment, Timestamp> apptEndTimeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> apptLocationCol;

    @FXML
    private TableColumn<Appointment, Timestamp> apptStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptUsertIdCol;

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
    private TableColumn<Appointment, Integer> reportApptIdCol;

    @FXML
    private TableColumn<Appointment, String> reportCustIdCol;

    @FXML
    private TableColumn<Appointment, String> reportDescripCol;

    @FXML
    private TableColumn<Appointment, Timestamp> reportEndTimeCol;

    @FXML
    private TableColumn<Appointment, Timestamp> reportStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> reportTitleCol;

    @FXML
    private TableColumn<Appointment, String> reportTypeCol;

    @FXML
    private TableView<Appointment> scheduleTable;


    /**Opens the Contact Add form in Customer Tab
     * @param
     */
    public void addContact(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/customerAdd.fxml"));

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Add Contact");
        stage.show();


    }


    /**Filters and counts all the appointments according to the filters selected, method is attached to "search" button in Reports tab.
     */
    public void reportFilter(){
        try {
            Integer month = reportMonthCbox.getSelectionModel().getSelectedIndex()+1;
            Integer count = 0;
            ObservableList<Appointment> searchByMonth = FXCollections.observableArrayList();
            ObservableList<Appointment> searchByType = FXCollections.observableArrayList();
            if(reportMonthCbox.getValue() == null){
                for(Appointment appt : DataStore.getAllAppointments()){
                    if(appt.getType().contains(reportTypeCbox.getValue())){
                        count++;
                        searchByType.add(appt);
                    }
                }
            } else if(reportTypeCbox.getValue() == null) {
                for (Appointment appt : DataStore.getAllAppointments()) {
                    if (appt.getStartDateTime().getMonthValue() == month || appt.getEndDateTime().getMonthValue() == month) {
                        count++;
                        searchByMonth.add(appt);
                    }
                }
            }else{
                for (Appointment appt : DataStore.getAllAppointments()) {
                    if (appt.getStartDateTime().getMonthValue() == month || appt.getEndDateTime().getMonthValue() == month)
                        searchByMonth.add(appt);
                }
                for (Appointment appt : searchByMonth){
                    if(appt.getType().contains(reportTypeCbox.getValue()))
                        count++;
                }
            }
            totalApptLabel.setText(String.valueOf(count));
        } catch (Exception e) {
            Misc.dialogAlertInfo("Report Filter", "You must select at least 1 filter before clicking the Search button");
        }
    }


    /**Clears the combo box for the Type filter in Reports Tab.
     */
    public void clearTypeCbox(){
        reportTypeCbox.setValue(null);
    }


    /**Clears the combo box for the Month filter in Reports Tab.
     */
    public void clearMonthCbox(){
        reportMonthCbox.setValue(null);
    }


    /**Populates tableview with customer objects with specific contact selected from contact combo box in Reports tab.
     */
    public void reportContactWithAppts(){
        ObservableList<Appointment> searchResult = FXCollections.observableArrayList();
        for (Appointment appt : DataStore.getAllAppointments()){
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


    /**Opens Contact Update window in Customer Tab
     * @param event parameter not used.
     */
    public void updateContact(ActionEvent event) throws IOException {

        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/customerUpdate.fxml"));
            loader.load();
            CustomerUpdate myController = loader.getController();
            myController.getSelectedCustomer(customerTable.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Button) event.getSource()).getScene().getWindow());

            stage.setScene(new Scene(loader.getRoot(), -1, -1));
            stage.setTitle("Update Contact");
            stage.show();
        }else{
            Misc.dialogAlertInfo("Customer Update", "You must select a row before clicking the Update button");

        }

    }


    /** Deletes selected customer from database and tableview when user clicks delete button in Customer Tab
     * @param event parameter not used
     */
    public void deleteCustomer(ActionEvent event) throws Exception {
        Customer tempCustomer = customerTable.getSelectionModel().getSelectedItem();
        if( tempCustomer != null) {
            Optional<ButtonType> btnSelected = Misc.dialogAlertConfirm("Delete Customer", "Are you sure want to delete this Customer");
            if (btnSelected.get() == ButtonType.OK){
                if(JDBC.isCustIdInApptTbl(tempCustomer.getCustId())){
                    List<Integer> apptIdList = JDBC.deleteAllApptWithCustId(tempCustomer.getCustId());
                    for(int id : apptIdList){
                        Appointment foundAppt = DataStore.searchApptById(id);
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


    /**Opens the Add Appointment window form in Appointment Tab
     * @param event parameter not used
     */
    public void addAppt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/appointmentAdd.fxml"));

        Stage stage = new Stage();//(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());

        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
        stage.setTitle("Update Appointment");
        stage.show();
    }


   /**Opens the Update Appointment window form in Appointment Tab
    * @param event parameter not used
    */
   public void updateAppt(ActionEvent event) throws IOException, ParseException, SQLException {
       if(apptTable.getSelectionModel().getSelectedItem() != null) {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("../view/appointmentUpdate.fxml"));
           loader.load();

           AppointmentUpdate myController = loader.getController();
           myController.getSelectedAppt(apptTable.getSelectionModel().getSelectedItem());

           Stage stage = new Stage();
           stage.initModality(Modality.WINDOW_MODAL);
           stage.initOwner(((Button) event.getSource()).getScene().getWindow());

           stage.setScene(new Scene(loader.getRoot(), -1, -1));
           stage.setTitle("Update Contact");
           stage.show();
       }else{
           Misc.dialogAlertInfo("Appointment Update", "You must select a row before clicking the Update button");

       }
}


    /**Deletes selected appointment from database and tableview in Appointments Tab
     * @param event parameter not used
     */
    public void deleteAppt(ActionEvent event) throws Exception {
        if(apptTable.getSelectionModel().getSelectedItem() != null) {
            Optional<ButtonType> btnSelected = Misc.dialogAlertConfirm("Delete Appointment", "Are you sure want to delete this Appointment");
            if (btnSelected.get() == ButtonType.OK){
                Appointment appointment = apptTable.getSelectionModel().getSelectedItem();
                JDBC.deleteAppointmentSQL(appointment.getId());
                DataStore.deleteAppt(appointment);
            }
        }else{
            Misc.dialogAlertInfo("Delete Customer", "You must select a row before clicking the Delete button");

        }

    }


    /**Populates appointments tableview with all appointments and disables both month and week combo boxes.
     */
    public void filterShowAll() {
        apptTable.setItems(DataStore.getAllAppointments());
        apptMonthCbox.setDisable(true);
        apptWeekCbox.setDisable(true);
    }


    /**Disables the week combo box and fires event for filtering appointments by week.
     */
    public void filterByMonth() {
        apptMonthCbox.setDisable(false);
        apptWeekCbox.setDisable(true);
        apptMonthCbox.fireEvent(new ActionEvent());

    }


    /**Disables the month combo box and fires event for filtering appointments by month.
     */
    public void filterByWeek() {
        apptMonthCbox.setDisable(true);
        apptWeekCbox.setDisable(false);
        apptWeekCbox.fireEvent(new ActionEvent());
    }


    /**Populates appointments tableview with appointments that match with selected month in combo box
     */
    public void filterMonthSelected(){
        try {
            Integer month = apptMonthCbox.getSelectionModel().getSelectedIndex()+1;
            ObservableList<Appointment> searchResult = FXCollections.observableArrayList();
            for(Appointment appt : DataStore.getAllAppointments()){
                if(appt.getStartDate().getMonthValue() == month || appt.getEndDate().getMonthValue() == month){
                    searchResult.add(appt);
                }
            }
            apptTable.setItems(searchResult);
        } catch (Exception e) {}
    }


    /**Populates appointments tableview with appointments that match with selected month in combo box
     */
    public void filterWeekSelected(){
        try {
            Integer week = apptWeekCbox.getSelectionModel().getSelectedIndex();
            ObservableList<Appointment> searchResult = FXCollections.observableArrayList();

            for(Appointment appt : DataStore.getAllAppointments()){
                LocalDateTime apptStartDate = appt.getStartDateTime().toLocalDateTime();
                LocalDateTime apptEndDate = appt.getEndDateTime().toLocalDateTime();
                LocalDateTime weekStartDate = DataStore.getAllWeeks().get(week).getStartDate();
                LocalDateTime weekEndDate = DataStore.getAllWeeks().get(week).getEndDate();
                if(apptStartDate.isAfter(weekStartDate) && apptEndDate.isBefore(weekEndDate)){
                    searchResult.add(appt);
                }
            }
            apptTable.setItems(searchResult);
        } catch (Exception e) {}
    }


    /**Initializes all relevant data into loaded window.
     */
    public void initialize() throws SQLException {

        //Loads all customer data from database into customer objects and saved into a list
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

        //Loads all appointment data from database into appointment objects and saved into a list
        ResultSet apptResultSet = JDBC.runStatement("SELECT * FROM appointments");
        int index = 0;
        while (apptResultSet.next()) {
            DataStore.addAppt(new Appointment(apptResultSet.getInt("Appointment_ID"),
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

        //Loads contact data from database into contact objects and stored in a list
        ResultSet rsContact = JDBC.runStatement("SELECT * FROM contacts");
        while (rsContact.next()){
            DataStore.addContact(new Contact(rsContact.getInt("Contact_ID"),
                                             rsContact.getString("Contact_Name"),
                                             rsContact.getString("Email")));

        }

        //Creates all weeks for the current year and stores it into a simple string list and into a list of week objects
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

        //initializes radio button, and fire an event so that all appointments are loaded into tableview
        radioAll.setSelected(true);
        radioAll.fireEvent(new ActionEvent());

        //sets all combo boxes with their respective lists
        apptWeekCbox.setItems(DataStore.weekStringList);
        apptMonthCbox.setItems(DataStore.months);
        Misc.buildContactNameList();
        reportContactCbox.setItems(DataStore.contactNames);
        reportMonthCbox.setItems(DataStore.months);
        Misc.buildApptTypeList();
        reportTypeCbox.setItems(DataStore.appointmentType);

        //counts all customers in each country and stores in list
        ObservableList<CustomerPerCountry> countPerCountry = FXCollections.observableArrayList();
        for(String country : DataStore.countries){
            countPerCountry.add(new CustomerPerCountry(country, 0));
        }
        for (Customer customer : DataStore.getAllCustomers()){
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
        //sets customer per country list in tableview
        reportNumCustByCountry.setItems(countPerCountry);
        reportCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        reportNumCustCol.setCellValueFactory(new PropertyValueFactory<>("customerCounter"));

        //Lambda function that checks if there exists an appointment "a" with start time less than or equal to 15 minutes
        //An alert will be displayed stating if there is an appointment starting within 15 minutes or not
        Appointment appt = DataStore.getAllAppointments().stream()
                .filter( a -> ChronoUnit.MINUTES.between(a.getStartTime(), LocalTime.now()) <= 15)
                .findFirst().orElse(null);
        if(!(appt == null)){
            Misc.dialogAlertInfo("Appointment  Alert", "You have an appointment with " + "Appointment ID" + appt.getId() + " Starting on: " + appt.getStartDate() + " at " + appt.getStartTime());
        }else{
            Misc.dialogAlertInfo("Appointment Alert", "There are no upcoming appointments");
        }


    }
}
