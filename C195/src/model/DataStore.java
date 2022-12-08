package model;

import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
/** Represents where most data for this application is stored
 */
public class DataStore {

    private static final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    public static final ObservableList<String> contactNames = FXCollections.observableArrayList();
    public static final ObservableList<String> customerNames = FXCollections.observableArrayList();
    public static final ObservableList<String> usStates = FXCollections.observableArrayList();
    public static final ObservableList<String> ukRegions = FXCollections.observableArrayList();
    public static final ObservableList<String> caProvinces = FXCollections.observableArrayList();
    public static final ObservableList<String> countries = FXCollections.observableArrayList("U.S" , "UK", "Canada");
    public static final HashMap<String, Integer> divisionIdHash = new HashMap<>();
    public static final ObservableList<String> appointmentType = FXCollections.observableArrayList();
    public static final ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    public static final ObservableList<String> weekStringList = FXCollections.observableArrayList();
    private static final ObservableList<Week> weeksObjList = FXCollections.observableArrayList();

    /**Adds Week object to list of weeks
     * @param week a Week object that represents a week
     */
    public static void addWeekToList(Week week){ weeksObjList.add(week); }

    /**Adds Customer object to list of customers
     * @param customer a Customer object that represents a customer
     */
    public static void addCustomer (Customer customer){ customers.add(customer); }

    /**Adds Appt object to list of appointments
     * @param appointment an Appt object that represents an appointment
     */
    public static void addAppt (Appointment appointment){
        appointments.add(appointment);
    }

    /**Adds Contact object to list of contacts
     * @param contact a Contact object that represents a contact
     */
    public static void addContact(Contact contact){ contacts.add(contact); }

    /**Replaces a Customer object in the list of customer
     * @param index an integer that represents the index of the customer to be updated with new customer
     * @param customer a Customer object that represent the customer to be updated with new customer
     */
    public static void updateCustomer (int index, Customer customer){ customers.set(index, customer); }

    /**Replaces an Appt object in the list of appointments
     * @param index an integer that represents the index of the appointment to be updated with new appointment
     * @param appointment an Appt object that represent the appointment to be updated
     * */
    public static void updateAppt (int index, Appointment appointment){
        appointments.set(index, appointment);
    }

    /**Replaces a Contact object in the list of contacts
     * @param index an integer that represents the index of the contact to be updated with new contact
     * @param contact a Contact object that represents the contact to be updated
     */
    public static void updateContact (int index, Contact contact){ contacts.set(index, contact); }

    /**Deletes a Customer object from the list of customers
     * @param obj a Customer object that represents the customer to be deleted
     */
    public static boolean deleteCustomer (Customer obj){
        return customers.remove(obj);
     }

    /**Deletes an Appt object from the list of appointments
     * @param obj an Appt object that represents the appointment to be deleted
     */
    public static boolean deleteAppt (Appointment obj){
        return appointments.remove(obj);
    }

    /** Finds the contact id of the contact name.
     * @param contactName a string that represents the contact name to be searched
     * @return an integer representing contact's id
     */
    public static Integer getContactIdfromName(String contactName){
        ObservableList<Contact> contacts = getAllContacts();
        Integer contactID = null;
        for( Contact contact : contacts){
            if (contact.getContactName().contains(contactName)){
                contactID = contact.getContactId();
            }
        }
        return contactID;
    }

    /** Finds the appointment object associated to an appointment id
     * @param apptId an integer that represents the the appointment id to be searched
     * @return an Appt object representing the appointment found
     */
    public static Appointment searchApptById(Integer apptId){
        ObservableList<Appointment> allAppts = getAllAppointments();
        Appointment foundAppt = null;
        for(Appointment appt : allAppts){
            if(appt.getId() == apptId){
                foundAppt = appt;
            }
        }
        return foundAppt;
    }

    /**Gets all customers
     * @return an ObservableList with all customers
     */
    public static ObservableList<Customer> getAllCustomers(){
        return customers;
    }

    /**Gets all appointments
     * @return an ObservableList with all appointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return appointments;
    }


    /**Gets all contacts
     * @return an ObservableList with all contacts
     */
    public static ObservableList<Contact> getAllContacts() {return contacts; }

    /**Gets all weeks of the year
     * @return an ObservableList with all weeks for the year
     */
    public static ObservableList<Week> getAllWeeks() {return weeksObjList; }

    /**Builds a hashmap with division name as key and division id as value.
     */
    public static void buildDivisonIdHash() throws SQLException {
        ResultSet rs = JDBC.runStatement("SELECT Division, Division_ID FROM first_level_divisions");
        while(rs.next()){
            divisionIdHash.put(rs.getString("Division"), rs.getInt("Division_ID"));
        }
    }

}
