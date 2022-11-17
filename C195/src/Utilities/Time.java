package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Time {

    private static final LocalDate date = LocalDate.now();
    private static final LocalTime timeOpen = LocalTime.of(8,0);
    private static final LocalTime timeClose = LocalTime.of(22,0);
    private static final ZonedDateTime zdtOpen = ZonedDateTime.of(date, timeOpen, ZoneId.of ("America/New_York" ));
    private static final ZonedDateTime zdtClose = ZonedDateTime.of(date, timeClose, ZoneId.of ("America/New_York" ));
    public static final LocalTime businessTimeOpen = zdtOpen.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
    public static final LocalTime businessTimeClose = zdtClose.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();

    public static final ObservableList<String> minutes = FXCollections.observableArrayList("00", "15", "30", "45");
    public static final ObservableList<String> hours = FXCollections.observableArrayList();
    public static final ObservableList<String> amOrPm = FXCollections.observableArrayList("AM", "PM");

    public static ZonedDateTime fromSQLtoUTC(Timestamp ts) {
        ZonedDateTime zdt = ts.toLocalDateTime().atZone(ZoneId.of("UTC"));
        return zdt;
    }

    public static ZonedDateTime fromSQLtoUserTime(Timestamp ts) {
        ZonedDateTime zdt = ts.toLocalDateTime().atZone(ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public static String formatZonedDateTime(ZonedDateTime dateTime){
        String formattedDateTime = dateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        return formattedDateTime;
    }

    public static String formatDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public static String formatTime(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern("hh:mm"));
    }

    public static String formatAMorPM(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern("a"));
    }



}

