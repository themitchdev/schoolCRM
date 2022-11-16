package model;

import org.w3c.dom.html.HTMLImageElement;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

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

    public ApptOrSchedule(int apptId, String title, String description, String location, String contact, int custId, ZonedDateTime startDateTime, ZonedDateTime endDateTime, String type, int userId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.custId = custId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
