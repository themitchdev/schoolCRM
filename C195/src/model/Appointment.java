package model;

import Utilities.MyTime;

import java.time.*;

/**Abstract class that represents an appointment or a schedule
 */
public class Appointment {
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

    /**Constructor for an appointment or schedule object
     * @param apptId appointment id
     * @param title appointment 's full name
     * @param description appointment's description
     * @param location appointment's location
     * @param contact the contact assigned to appointment
     * @param custId the customer's id number
     * @param startDateTime appointment's start date and time in UTC
     * @param endDateTime appointment's end date and time in UTC
     * @param startDate appointment's start date in UTC
     * @param startTime appointment's start time in UTC
     * @param endDate appointment's end date in UTC
     * @param endTime appointment's end time in UTC
     * @param type appointment's type or reason
     * @param userId user id of the user who created the appointment
     */
    public Appointment(int apptId,
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

    /** Gets the appointment id
     * @return an integer representing the appointment id
     */
    public int getId() {
        return apptId;
    }

    /** Gets the appointment title
     * @return a string containing the title
     */
    public String getTitle() {
        return title;
    }

    /** Gets the appointment description
     * @return a string containing the description
     */
    public String getDescription() {
        return description;
    }

    /** Gets the appointment location
     * @return a string containing the title
     */
    public String getLocation() {
        return location;
    }

    /** Gets the appointment contact
     * @return a string containing the contact's full name
     */
    public String getContact() {
        return contact;
    }

    /** Gets the customer id assigned to appointment
     * @return an integer containing the customer id
     */
    public int getCustId() {
        return custId;
    }

    /** Gets the appointment start date and time in UTC
     * @return a ZonedDateTime object containing the start date and time in UTC
     */
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    /** Gets the appointment end date and time in UTC
     * @return a ZonedDateTime object containing the end date and time in UTC
     */
    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    /** Gets the appointment start date and time in local time formatted as mm/dd/yyyy hh:mm am or pm
     * @return a string containing the start date and time in local time
     */
    public String getFormattedStartDateTime(){
        return MyTime.formatZonedDateTime(getStartDateTime().withZoneSameInstant(ZoneId.systemDefault()));
    }

    /** Gets the appointment end date and time in local time formatted as mm/dd/yyyy hh:mm am or pm
     * @return a string containing the end date and time in local time
     */
    public String getFormattedEndDateTime(){
        return MyTime.formatZonedDateTime(getEndDateTime().withZoneSameInstant(ZoneId.systemDefault()));
    }

    /** Gets the appointment start date
     * @return a LocalDate object containing the start date in UTC
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** Gets the appointment start time
     * @return a LocalTime object containing the start time in UTC
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /** Gets the appointment end date
     * @return a LocalDate object containing the end date in UTC
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** Gets the appointment end time
     * @return a LocalTime object containing the end time in UTC
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /** Gets the appointment type
     * @return a string containing the type of appointment
     */
    public String getType() {
        return type;
    }

    /** Gets the user id assign to appointment
     * @return a integer containing the user id of appointment
     */
    public int getUserId() {
        return userId;
    }

    /** Sets the appointment id
     * @param apptId an integer containing the appointment id
     */
    public void setId(int apptId) {
        this.apptId = apptId;
    }

    /** Sets the appointment title
     * @param title a string containing the customer's id
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Sets the contact name
     * @param contact a string containing the contact's name
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** Sets the appointment type
     * @param type a string containing the appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

}
