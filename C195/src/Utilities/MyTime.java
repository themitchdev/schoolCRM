package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.DataStore;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
/** Represents a utility class that manages time conversion and formatting
 */
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

    /** Converts Timestamp to a ZonedDateTime object in UTC
     * @return a ZonedDateTime object in UTC timezone
     */
    public static ZonedDateTime fromSQLtoUTC(Timestamp ts) {
        ZonedDateTime zdt = ts.toLocalDateTime().atZone(ZoneId.of("UTC"));
        return zdt;
    }

    /** Converts Timestamp to a ZonedDateTime object in UTC then converts it to user's system local timezone
     * @return a ZonedDateTime object in user's system local timezone
     */
    public static ZonedDateTime fromSQLtoUserTime(Timestamp ts) {
        ZonedDateTime zdt = ts.toLocalDateTime().atZone(ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    /** Converts ZonedDateTime in user's localtime to a ZonedDateTime object in UTC
     * @return a ZonedDateTime object in UTC timezone
     */
    public static ZonedDateTime fromUserTimetoUTC(ZonedDateTime userTime){
        ZonedDateTime zdt = userTime.withZoneSameInstant(ZoneId.of("UTC"));
        return zdt;
    }

    /** Converts ZonedDateTime into a formatted string MM/dd/yyyy hh:mm am or pm
     * @return a string containing date and time
     */
    public static String formatZonedDateTime(ZonedDateTime dateTime){
        String formattedDateTime = dateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        return formattedDateTime;
    }

    /** Lambda Function that checks if there exists an appointment within 15 minutes
     * @return a boolean true if appointment within 15 minutes, false if no appointments within 15 minutes
     */
    public static Predicate<Appointment> isApptOverlap = newAppt-> {
        ZonedDateTime tempStartDateTime = ZonedDateTime.of(newAppt.getStartDate(), newAppt.getStartTime(), ZoneId.systemDefault());
        ZonedDateTime tempEndDateTime = ZonedDateTime.of(newAppt.getEndDate(), newAppt.getEndTime(), ZoneId.systemDefault());
        ZonedDateTime newStartDateTime = fromUserTimetoUTC(tempStartDateTime);
        ZonedDateTime newEndDateTime = fromUserTimetoUTC(tempEndDateTime);
        for(Appointment appt : DataStore.getAllAppointments()){
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

