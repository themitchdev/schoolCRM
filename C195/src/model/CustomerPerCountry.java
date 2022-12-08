package model;

/**Represents a counter of customers per country
 */
public class CustomerPerCountry {
    private String country;
    private Integer numOfCustomers;

    /**Constructor for counter object
     * @param country country name
     * @param numOfCustomers the number of customers in above country
     */
    public CustomerPerCountry(String country, Integer numOfCustomers) {
        this.country = country;
        this.numOfCustomers = numOfCustomers;
    }

    /** Gets the country
     * @return a string representing the country
     */
    public String getCountry() {
        return country;
    }

    /** Sets the country
     * @param country a string containing the country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Gets the customer count in that country
     * @return an integer representing count of customers in specific country
     */
    public Integer getCustomerCounter() {
        return numOfCustomers;
    }

    /** Sets the count of customers in country
     * @param customerCounter an integer containing the count of customers in specific country
     */
    public void setCustomerCounter(Integer customerCounter) {
        this.numOfCustomers = customerCounter;
    }
}
