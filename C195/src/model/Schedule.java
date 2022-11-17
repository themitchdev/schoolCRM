package model;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class Schedule extends ApptOrSchedule {

    public Schedule(int apptId,
                    String title,
                    String description,
                    String location,
                    String contact,
                    int custId,
                    ZonedDateTime startDateTime,
                    ZonedDateTime endDateTime,
                    LocalDate startDate,
                    LocalTime startTime,
                    LocalDate endDate,
                    LocalTime endTime,
                    String type,
                    int userId) {

        super(apptId,
              title,
              description,
              location,
              contact,
              custId,
              startDateTime,
              endDateTime,
              startDate,
              startTime,
              endDate,
              endTime,
              type,
              userId);
    }
}
