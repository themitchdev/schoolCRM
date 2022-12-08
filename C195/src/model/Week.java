package model;

import java.time.LocalDateTime;

/**Represents a week from Sunday to Saturday
 */
public class Week {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**Constructor for a week object
    * @param startDate the start date of the week
    * @param endDate the end date of that week
    */
    public Week(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    /** Gets the week's start date
     * @return a LocalDateTime object representing the start date and time
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }
    /** Sets the week's start date
     * @param startDate the start date and time of the week
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /** Gets the week's end date
     * @return a LocalDateTime object representing the end date and time
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /** Sets the week's end date
     * @param endDate the end date and time of the week
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
