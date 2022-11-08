package DAO;

import javafx.collections.ObservableList;
import model.Customer;
import model.DataStore;

import java.sql.*;

public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static String customerGetState(int custId) throws SQLException {
        String sql = "SELECT Division FROM customers, first_level_divisions WHERE customers.Division_ID=first_level_divisions.Division_ID AND Customer_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getString("Division");
    }

    public static String customerGetCountry(int custId) throws SQLException {
        String sql = "SELECT Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID=first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID AND Customer_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getString("Country");
    }
    public static String apptGetContactName(int apptId) throws SQLException {
        String sql = "SELECT Contact_Name FROM appointments, contacts WHERE contacts.Contact_ID = appointments.Contact_ID AND Appointment_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getString("Contact_Name");
    }

    public static ResultSet runStatement(String sql) throws SQLException {
        Statement stmt = JDBC.connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    public static void insert1stLvlDivIntoList(ObservableList<String> list, String countryID) throws SQLException {
        ResultSet rs = DAO.JDBC.runStatement("SELECT Division FROM first_level_divisions WHERE Country_ID=" + countryID);
        while (rs.next()){
            list.add(rs.getString("Division"));
            }
        }
    public static void saveCustomer(Customer customer) throws SQLException {
        Integer division_ID = Utilities.divisionIdHash.get(customer.getState());
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                         "VALUES(?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getCustName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getZipcode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, division_ID);
        ps.execute();

        }

    public static void updateCustomerSQL(Customer customer) throws SQLException {
        Integer division_ID = Utilities.divisionIdHash.get(customer.getState());
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone= ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getCustName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getZipcode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, division_ID);
        ps.setInt(6, customer.getCustId());
        ps.execute();
    }
    public static void deleteCustomerSQL(Customer customer) throws Exception {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(customer.getCustId()));
        ps.execute();
    }


    }


