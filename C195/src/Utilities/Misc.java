package Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.DataStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Optional;

/**Represents miscellaneous utilities for application
 */
public class Misc {

    /** Creates a dialog window of type Information
     * @param title a string containing the title of the window
     * @param contentText a string containing the main text of the dialog window
     */
    public static void dialogAlertInfo(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /** Creates a dialog window of type Confirmation
     * @param title a string containing the title of the window
     * @param contentText a string containing the main text of the dialog window
     */
    public static Optional<ButtonType> dialogAlertConfirm(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    /** Adds all customer names into a customer name only list
     */
    public static void buildCustomerNameList (){
        for (Customer customer : DataStore.getAllCustomers()){
            DataStore.customerNames.add(customer.getCustName());
        }
    }

    /** Adds all contact names into a contact name only list
     */
    public static void buildContactNameList (){
        for (Contact contact : DataStore.getAllContacts()){
            DataStore.contactNames.add(contact.getContactName());
        }
    }

    /** Adds all appointment type specifier into a type only list
     */
    public static void buildApptTypeList() throws SQLException {
        ResultSet rsApptType = JDBC.runStatement("SELECT DISTINCT Type FROM appointments");
        while (rsApptType.next()){
            DataStore.appointmentType.add(rsApptType.getString("Type"));
        }
    }



}
