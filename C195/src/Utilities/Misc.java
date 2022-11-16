package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import model.Appt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.HashMap;
import java.util.Optional;

public class Misc {

    public static final ObservableList<String> usStates = FXCollections.observableArrayList();
    public static final ObservableList<String> ukRegions = FXCollections.observableArrayList();
    public static final ObservableList<String> caProvinces = FXCollections.observableArrayList();
    public static final ObservableList<String> countries = FXCollections.observableArrayList("U.S" , "UK", "Canada");
    public static final ObservableList<String> contacts = FXCollections.observableArrayList();
    public static final HashMap<String, Integer> divisionIdHash = new HashMap<>();
    public static final Callback<DatePicker, DateCell> dayCellFactory = getDayCellFactory();


    public static ZonedDateTime toLocalDateTime(Timestamp utc){

        LocalDateTime ldt = utc.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime newZdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime newLdt = newZdt.toLocalDateTime();

        return newZdt;

    }



    public static String getDateFromAppt(Appt appt, int zeroForStartDate) throws ParseException {

        if (zeroForStartDate == 0) {
            String dateTime = String.valueOf(appt.getStartDateTime());
            String[] split = dateTime.split(" ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");
            return sdf2.format(sdf.parse(split[0]));
        } else {
            String dateTime = String.valueOf(appt.getEndDateTime());
            String[] split = dateTime.split(" ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");
            return sdf2.format(sdf.parse(split[0]));
        }
        //String dateTime = String.valueOf(appt.getEndDateTime());


    }

    public static String getTimeFromAppt(Appt appt, int zeroForStartTime) throws ParseException {

        if (zeroForStartTime == 0) {
            //Convert from sql UTC to LocalDateTime
            String dateTime = String.valueOf(appt.getStartDateTime());
            //Split string date and string time
            String[] splitz = dateTime.split(" ");

            String tempSplit = splitz[1];
            String[] splitz2 = tempSplit.split(":");
            String[] splitz3 = {splitz2[0], ":", splitz2[1]};
            return String.join("", splitz3);
        } else {
            //Convert from sql UTC to LocalDateTime
            String dateTime = String.valueOf(appt.getEndDateTime());
            //Split string date and string time
            String[] splitz = dateTime.split(" ");

            String tempSplit = splitz[1];
            String[] splitz2 = tempSplit.split(":");
            String[] splitz3 = {splitz2[0], ":", splitz2[1]};
            return String.join("", splitz3);
        }

    }

    public static Timestamp toTimeStamp(String date, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = sdf2.format(sdf.parse(date));

        return Timestamp.valueOf(parsedDate+" "+time);
    }

    public static void buildDivisonIdHash() throws SQLException {
        ResultSet rs = JDBC.runStatement("SELECT Division, Division_ID FROM first_level_divisions");
        while(rs.next()){
            divisionIdHash.put(rs.getString("Division"), rs.getInt("Division_ID"));
        }
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

    public static Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable Monday, Tueday, Wednesday.
                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY //
                                || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                            setStyle("-fx-background-color: #6B6868;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }




}
