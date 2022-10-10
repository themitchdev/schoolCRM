package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appt;
import model.DataStore;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utilities {

    public static final ObservableList<String> usStates = FXCollections.observableArrayList();
    public static final ObservableList<String> ukRegions = FXCollections.observableArrayList();
    public static final ObservableList<String> caProvinces = FXCollections.observableArrayList();
    public static final ObservableList<String> countries = FXCollections.observableArrayList("U.S" , "UK", "Canada");
    public static final ObservableList<String> contacts = FXCollections.observableArrayList();

    public static Timestamp toLocalDateTime(Timestamp utc){

        LocalDateTime ldt = utc.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime newZdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime newLdt = newZdt.toLocalDateTime();

        return Timestamp.valueOf(newLdt);

    }

    public static Timestamp toUTC(Timestamp ts){

        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime newZdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime newLdt = newZdt.toLocalDateTime();

        return Timestamp.valueOf(newLdt);

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

    public static String getTimeFromAppt(Appt appt) throws ParseException {
        //Convert from sql UTC to LocalDateTime
        String dateTime = String.valueOf(toLocalDateTime(appt.getStartDateTime()));
        //Split string date and string time
        String[] splitz = dateTime.split(" ");

        String tempSplit = splitz[1];
        String[] splitz2 = tempSplit.split(":");
        String[] splitz3 = {splitz2[0], ":", splitz2[1]};



        //returns time
        return String.join("", splitz3);

    }

    public static Timestamp toTimeStamp(String date, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = sdf2.format(sdf.parse(date));

        return Timestamp.valueOf(parsedDate+" "+time);
    }

    interface isNoon{

        public void someFunc();

    }


}
