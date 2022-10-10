package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStore {
    private static final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static final ObservableList<Appt> appointments = FXCollections.observableArrayList();
    private static final ObservableList<Schedule> schedules = FXCollections.observableArrayList();

    public static void addCustomer (Customer customer){
        customers.add(customer);
    }

    public static void addAppt (Appt appointment){
        appointments.add(appointment);
    }

    public static void addSchedule (Schedule schedule){
        schedules.add(schedule);
    }

    public static void updateCustomer (int index, Customer customer){
        customers.set(index, customer);
    }

    public static void updateAppt (int index, Appt appointment){
        appointments.set(index, appointment);
    }

     public static boolean deleteCustomer (Customer obj){
        return customers.remove(obj);
     }

    public static boolean deleteAppt (Appt obj){
        return appointments.remove(obj);
    }

    public static ObservableList<Schedule> lookupScheduleByContact(int contactId){
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
}
