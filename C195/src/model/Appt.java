package model;

import java.sql.Timestamp;

public class Appt extends ApptOrSchedule{

    public Appt(int apptId, String title, String description, String location, String contact, int custId, Timestamp startDateTime, Timestamp endDateTime, String type, int userId) {
        super(apptId, title, description, location, contact, custId, startDateTime, endDateTime, type, userId);
    }
}
