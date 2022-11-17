package model;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DataStore {

    private static final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static final ObservableList<Appt> appointments = FXCollections.observableArrayList();
    private static final ObservableList<Schedule> schedules = FXCollections.observableArrayList();
    private static final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static final ObservableList<String> contactNames = FXCollections.observableArrayList();
    public static final ObservableList<String> customerNames = FXCollections.observableArrayList();
    public static final ObservableList<String> usStates = FXCollections.observableArrayList();
    public static final ObservableList<String> ukRegions = FXCollections.observableArrayList();
    public static final ObservableList<String> caProvinces = FXCollections.observableArrayList();
    public static final ObservableList<String> countries = FXCollections.observableArrayList("U.S" , "UK", "Canada");
    public static final HashMap<String, Integer> divisionIdHash = new HashMap<>();
    public static final ObservableList<String> appointmentType = FXCollections.observableArrayList();


    public static void addCustomer (Customer customer){
        customers.add(customer);
    }

    public static void addAppt (Appt appointment){
        appointments.add(appointment);
    }

    public static void addSchedule (Schedule schedule){
        schedules.add(schedule);
    }

    public static void addContact(Contact contact){ contacts.add(contact); }

    public static void updateCustomer (int index, Customer customer){ customers.set(index, customer); }

    public static void updateAppt (int index, Appt appointment){
        appointments.set(index, appointment);
    }

    public static void updateContact (int index, Contact contact){ contacts.set(index, contact); }

    public static boolean deleteCustomer (Customer obj){
        return customers.remove(obj);
     }

    public static boolean deleteAppt (Appt obj){
        return appointments.remove(obj);
    }

    public static ObservableList<Schedule> lookupScheduleByContactId(int contactId){
        ObservableList<Schedule> schedulesFound = FXCollections.observableArrayList();
        for (Schedule schedule : schedules) {
            if (schedule.getId() == contactId) {
                schedulesFound.add(schedule);
            }
        }
        return schedulesFound;
    }

    public static ObservableList<Customer> getAllCustomers(){
        return customers;
    }

    public static ObservableList<Appt> getAllAppointments(){
        return appointments;
    }

    public static ObservableList<Schedule> getAllSchedules(){
        return schedules;
    }

    public static ObservableList<Contact> getAllContacts() {return contacts; }

    public static void buildDivisonIdHash() throws SQLException {
        ResultSet rs = JDBC.runStatement("SELECT Division, Division_ID FROM first_level_divisions");
        while(rs.next()){
            divisionIdHash.put(rs.getString("Division"), rs.getInt("Division_ID"));
        }
    }
}
