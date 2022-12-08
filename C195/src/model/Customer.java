package model;

/**Represents a customer
 */
public class Customer {
    private int custId;
    private String custName;
    private String address;
    private String state;
    private String zipcode;
    private String phone;
    private String country;

    /**Constructor for a customer object
     * @param custId the customer's Id
     * @param custName the customer's full name
     * @param address the customer's street address
     * @param state the customer's state
     * @param zipcode the customer's zipcode
     * @param phone the customer's phone number
     * @param country the customer's country
     */
    public Customer(int custId, String custName, String address, String state, String zipcode, String phone, String country) {
        this.custId = custId;
        this.custName = custName;
        this.address = address;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phone;
        this.country = country;
    }

    /** Gets the customer's id number
     * @return an integer representing the customer's id
     */
    public int getCustId() {
        return custId;
    }

    /** Sets the customer's id
     * @param custId an integer containing the customer's id
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /** Gets the customer's full name
     * @return a string representing the customer's full name
     */
    public String getCustName() {
        return custName;
    }

    /** Gets the customer's street address
     * @return a string representing the customer's street address
     */
    public String getAddress() {
        return address;
    }

    /** Gets the customer's state
     * @return a string representing the customer's state
     */
    public String getState() {
        return state;
    }

    /** Sets the customer's state
     * @param state a string containing the customer's state
     */
    public void setState(String state) {
        this.state = state;
    }

    /** Gets the customer's zipcode
     * @return a string representing the customer's zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /** Gets the customer's phone number
     * @return a string representing the customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /** Gets the customer's country
     * @return a string representing the customer's country
     */
    public String getCountry() {
        return country;
    }

}
