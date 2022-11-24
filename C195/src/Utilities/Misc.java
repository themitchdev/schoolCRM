package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import model.Appt;
import model.Contact;
import model.Customer;
import model.DataStore;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.HashMap;
import java.util.Optional;

public class Misc {

    public static ZonedDateTime toLocalDateTime(Timestamp utc){

        LocalDateTime ldt = utc.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime newZdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime newLdt = newZdt.toLocalDateTime();

        return newZdt;

    }

    public static Timestamp toTimeStamp(String date, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = sdf2.format(sdf.parse(date));

        return Timestamp.valueOf(parsedDate+" "+time);
    }

    public static void dialogAlertInfo(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static Optional<ButtonType> dialogAlertConfirm(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }

    public static void buildCustomerNameList (){
        for (Customer customer : DataStore.getAllCustomers()){
            DataStore.customerNames.add(customer.getCustName());
        }
    }

    public static void buildContactNameList (){
        for (Contact contact : DataStore.getAllContacts()){
            DataStore.contactNames.add(contact.getContactName());
        }
    }

    public static void buildApptTypeList() throws SQLException {
        ResultSet rsApptType = JDBC.runStatement("SELECT DISTINCT Type FROM appointments");
        while (rsApptType.next()){
            DataStore.appointmentType.add(rsApptType.getString("Type"));
        }
    }



}
