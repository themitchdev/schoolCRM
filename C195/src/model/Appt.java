package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class Appt extends ApptOrSchedule{

    public Appt(int apptId,
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
