package Utilities;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.DataStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**Represents the database connection and other database utilities
 */
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

    /** Connects the application to the database
     */
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

    /** Closes the connection to the database
    */
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

    /** Gets the State name of the customer id
     * @param custId an integer containing the customer's id
     * @return a string representing customer's State
     */
    public static String customerGetState(int custId) throws SQLException {
        String sql = "SELECT Division FROM customers, first_level_divisions WHERE customers.Division_ID=first_level_divisions.Division_ID AND Customer_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getString("Division");
    }

    /** Gets the Country name of the customer id
     * @param custId an integer containing the customer's id
     * @return a string representing customer's Country
     */
    public static String customerGetCountry(int custId) throws SQLException {
        String sql = "SELECT Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID=first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID AND Customer_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getString("Country");
    }

    /** Gets the name of the contact
     * @param apptId an integer containing the appointment id
     * @return a string representing contact name
     */
    public static String apptGetContactName(int apptId) throws SQLException {
        String sql = "SELECT Contact_Name FROM appointments, contacts WHERE contacts.Contact_ID = appointments.Contact_ID AND Appointment_ID =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getString("Contact_Name");
    }

    /** Runs a MySQL statement query
     * @param sql a string containing the SQL statement
     * @return a ResultSet representing the rows retrieved from the database
     */
    public static ResultSet runStatement(String sql) throws SQLException {
        Statement stmt = JDBC.connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    /** Runs a MySQL query and inserts first level divisions/states/provinces into an ObservableList
     * @param list an ObservableList where the first level divisions will be inserted
     * @param countryID an integer that represents the country
     */
    public static void insert1stLvlDivIntoList(ObservableList<String> list, String countryID) throws SQLException {
        ResultSet rs = JDBC.runStatement("SELECT Division FROM first_level_divisions WHERE Country_ID=" + countryID);
        while (rs.next()){
            list.add(rs.getString("Division"));
            }
        }

    /** Saves a customer into the database
     * @param customer a Customer obj that represents a customer
     */
    public static void saveCustomer(Customer customer) throws SQLException {
        Integer division_ID = DataStore.divisionIdHash.get(customer.getState());
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                         "VALUES(?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getCustName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getZipcode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, division_ID);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
            customer.setCustId(rs.getInt(1));

        }

    /** Saves an appointment into the database
     * @param appt an Appointment obj that represents an appointment
     */
    public static void saveAppt(Appointment appt) throws SQLException {
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appt.getStartDateTime().toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appt.getEndDateTime().toLocalDateTime()));
        ps.setInt(7, appt.getCustId());
        ps.setInt(8, Login.getLoggedInUserID());
        ps.setInt(9, DataStore.getContactIdfromName(appt.getContact()));
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
            appt.setId(rs.getInt(1));

    }

    /** Updates an existing customer in the database
     * @param customer a Customer obj that represents a customer
     */
    public static void updateCustomerSQL(Customer customer) throws SQLException {
        Integer division_ID = DataStore.divisionIdHash.get(customer.getState());
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

    /** Updates an existing appointment in the database
     * @param appt an Appointment obj that represents an appointment
     */
    public static void updateApptSQL(Appointment appt) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type= ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ?  WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appt.getStartDateTime().toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appt.getEndDateTime().toLocalDateTime()));
        ps.setInt(7, appt.getCustId());
        ps.setInt(8, DataStore.getContactIdfromName(appt.getContact()));
        ps.setInt(9, appt.getId());
        ps.execute();
    }

    /** Deletes a customer from the database
     * @param customer the Customer obj that will be deleted
     */
    public static void deleteCustomerSQL(Customer customer) throws Exception {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(customer.getCustId()));
        ps.execute();
    }

    /** Deletes an appointment from the database
     * @param appointmentId an integer representing the appointment id to be deleted
     */
    public static void deleteAppointmentSQL(Integer appointmentId) throws Exception {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(appointmentId));
        ps.execute();
    }

    /** Gets customer's name using customer's id
     * @param custId an integer representing the customer id
     * @return a string that represents the customer's name
     */
    public static String getCustNameFromCustId(Integer custId) throws SQLException {
        //check is there an appointment with customerid
        String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(custId));
        ResultSet rs = ps.executeQuery();
        rs.next();
        return  rs.getString("Customer_Name");
    }

    /** Checks if a customer id exists in the appointment table
     * @param custId an integer representing the customer id
     * @return a boolean that represents whether customer id is in appointment table or not
     */
    public static boolean isCustIdInApptTbl(Integer custId) throws SQLException {
        String sql = "SELECT Customer_ID FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(custId));
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return true;
        }
        return false;
    }

    /** Deletes all appointments associated with a customer id from appointments table
     * @param custId an integer representing the customer id
     * @return a List with the appointment id of the appointments that were deleted
     */
    public static List<Integer> deleteAllApptWithCustId(Integer custId) throws Exception {
        String sql = "SELECT Appointment_ID FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, String.valueOf(custId));
        ResultSet rs = ps.executeQuery();
        List<Integer> apptIds = new ArrayList<>();
        while(rs.next()){
            apptIds.add(rs.getInt("Appointment_ID"));
            deleteAppointmentSQL(rs.getInt("Appointment_ID"));
        }
        return apptIds;
    }

}


