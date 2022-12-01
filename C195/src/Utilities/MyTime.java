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
import java.util.function.Predicate;

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

//    public static boolean isThereApptIn15Mins(LocalTime now, LocalTime apptStartTime){
//        long timeDiff = ChronoUnit.MINUTES.between(apptStartTime, now);
//        if (timeDiff <= 15) return true;
//        return false;
//    }

    public static Predicate<Appt> isApptOverlap = newAppt-> {
        ZonedDateTime tempStartDateTime = ZonedDateTime.of(newAppt.getStartDate(), newAppt.getStartTime(), ZoneId.systemDefault());
        ZonedDateTime tempEndDateTime = ZonedDateTime.of(newAppt.getEndDate(), newAppt.getEndTime(), ZoneId.systemDefault());
        ZonedDateTime newStartDateTime = fromUserTimetoUTC(tempStartDateTime);
        ZonedDateTime newEndDateTime = fromUserTimetoUTC(tempEndDateTime);
        System.out.println(newStartDateTime);
        System.out.println(newEndDateTime);

        for(Appt appt : DataStore.getAllAppointments()){
            System.out.println(appt.getStartDateTime());
            System.out.println(appt.getEndDateTime());
            if(!(appt.getId() == newAppt.getId())){
            if((newStartDateTime.isAfter(appt.getStartDateTime())
                    && newStartDateTime.isBefore(appt.getEndDateTime()))
                    || (newEndDateTime.isAfter(appt.getStartDateTime())
                    && newEndDateTime.isBefore(appt.getEndDateTime()))
                    || (newStartDateTime.isBefore(appt.getStartDateTime())
                    && newEndDateTime.isAfter(appt.getEndDateTime()))
                    || (newStartDateTime.isEqual(appt.getStartDateTime())
                    || newEndDateTime.isEqual(appt.getEndDateTime()))){
                Misc.dialogAlertInfo("Appointment Time Overlap", "This appointment overlaps with Appointment ID #" + appt.getId());
                return true;
            }
            }
        }
        return false;
    };




}

