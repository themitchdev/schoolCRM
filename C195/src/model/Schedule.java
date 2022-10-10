package model;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Schedule extends ApptOrSchedule {


    public Schedule(int apptId, String title, String description, String location, String contact, int custId, Timestamp startDateTime, Timestamp endDateTime, String type, int userId) {
        super(apptId, title, description, location, contact, custId, startDateTime, endDateTime, type, userId);
    }
}
