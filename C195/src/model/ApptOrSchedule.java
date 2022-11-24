package model;

import Utilities.MyTime;

import java.time.*;

public abstract class ApptOrSchedule {
    private int apptId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private int custId;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String type;
    private int userId ;

    public ApptOrSchedule(int apptId,
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
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.custId = custId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startDate = startDate;
        this.startTime =  startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.type = type;
        this.userId = userId;
    }

    public int getId() {
        return apptId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public int getCustId() {
        return custId;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getFormattedStartDateTime(){
        return MyTime.formatZonedDateTime(getStartDateTime().withZoneSameInstant(ZoneId.systemDefault()));
    }
    public String getFormattedEndDateTime(){
        return MyTime.formatZonedDateTime(getEndDateTime().withZoneSameInstant(ZoneId.systemDefault()));
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int apptId) {
        this.apptId = apptId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

//    public void setStartDateTime(Timestamp startDateTime) {
//        this.startDateTime = startDateTime;
//    }

//    public void setEndDateTime(Timestamp endDateTime) {
//        this.endDateTime = endDateTime;
//    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
