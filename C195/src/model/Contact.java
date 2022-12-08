package model;

/**Represents a contact
 */
public class Contact {
    private Integer contactId;
    private String contactName;
    private String contactEmail;

    /**Constructor for contact object
     * @param contactId the contact's Id
     * @param contactName the contact's full name
     * @param contactEmail the contact's email address
     */
    public Contact(Integer contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
    /** Gets the contact's id number
     * @return an integer representing the contact id
     */
    public Integer getContactId() {
        return contactId;
    }

    /**Get the contact's full name
     * @return a string representing the contact's full name
     */
    public String getContactName() {
        return contactName;
    }




}
