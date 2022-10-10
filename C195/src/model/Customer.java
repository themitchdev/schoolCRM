package model;

public class Customer {
    private int custId;
    private String custName;
    private String address;
    private String state;
    private String zipcode;
    private String phone;
    private String country;

    public Customer(int custId, String custName, String address, String state, String zipcode, String phone, String country) {
        this.custId = custId;
        this.custName = custName;
        this.address = address;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phone;
        this.country = country;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
