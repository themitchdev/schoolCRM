package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Appt extends ApptOrSchedule{

    public Appt(int apptId, String title, String description, String location, String contact, int custId, ZonedDateTime startDateTime, ZonedDateTime endDateTime, String type, int userId) {
        super(apptId, title, description, location, contact, custId, startDateTime, endDateTime, type, userId);
    }


}
