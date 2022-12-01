package model;

public class CustomerPerCountry {
    private String country;
    private Integer customerCounter;

    public CustomerPerCountry(String country, Integer customerCounter) {
        this.country = country;
        this.customerCounter = customerCounter;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCustomerCounter() {
        return customerCounter;
    }

    public void setCustomerCounter(Integer customerCounter) {
        this.customerCounter = customerCounter;
    }
}
