package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appt;
import model.DataStore;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class MyTime {
    private static final LocalDate date = LocalDate.now();
    private static final LocalTime timeOpen = LocalTime.of(8,0);
    private static final LocalTime timeClose = LocalTime.of(22,0);
    private static final ZonedDateTime zdtOpen = ZonedDateTime.of(date, timeOpen, ZoneId.of ("America/New_York" ));
    private static final ZonedDateTime zdtClose = ZonedDateTime.of(date, timeClose, ZoneId.of ("America/New_York" ));
    public static final LocalTime userSysTimeOpen = zdtOpen.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
    public static final LocalTime userSysTimeClose = zdtClose.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();

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

    public static ZonedDateTime fromUserTimetoUTC(ZonedDateTime userTime){
        ZonedDateTime zdt = userTime.withZoneSameInstant(ZoneId.of("UTC"));
        return zdt;
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

    public static boolean isThereAppt15InMins(LocalTime now, LocalTime apptStartTime){
        long timeDiff = ChronoUnit.MINUTES.between(apptStartTime, now);
        if (timeDiff <= 15) return true;
        return false;
    }

    public static boolean isApptOverlap(Appt newAppt){
        ZonedDateTime newStartDateTime = ZonedDateTime.of(newAppt.getStartDate(), newAppt.getStartTime(), ZoneId.systemDefault());
        ZonedDateTime newEndDateTime = ZonedDateTime.of(newAppt.getEndDate(), newAppt.getEndTime(), ZoneId.systemDefault());

        ObservableList<Appt> tempApptList = DataStore.getAllAppointments();
        for(Appt appt : tempApptList){
            if((newStartDateTime.isAfter(appt.getStartDateTime())
                    && newStartDateTime.isBefore(appt.getEndDateTime()))
                    || (newEndDateTime.isAfter(appt.getStartDateTime())
                    && newEndDateTime.isBefore(appt.getEndDateTime()))
                    || (newStartDateTime.isBefore(appt.getStartDateTime())
                    && newEndDateTime.isAfter(appt.getEndDateTime()))){
                Misc.dialogAlertInfo("Appointment Time Overlap", "This appointment overlaps with Appointment ID #" + appt.getId());
                return true;
            }
        }
        return false;
    }



}

