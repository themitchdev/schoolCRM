package Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**Represents a login utility
 */
public abstract class Login {

    private static Integer loggedInUserID;

    /** Gets the logged-in user id number
     * @return an integer representing the user id
     */
    public static Integer getLoggedInUserID() {
        return loggedInUserID;
    }

    /** Sets the logged-in user id number
     * @param userId an integer representing the user id that is currently logged-in
     */
    public static void setLoggedInUserId(Integer userId){
        loggedInUserID = userId;
    }

    /** Checks the database if username and password match
     * @return a boolean representing if username and password are valid or not
     */
    public static boolean validateUserPassword(String user, String pass) throws SQLException, IOException {
        Instant now = Instant.now();
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ssa z").withZone(ZoneId.of("UTC"));

        if(user == null || user.isEmpty() || user.isBlank())
            user = "BLANK";

        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("User_Name").equals(user) && rs.getString("Password").equals(pass)) {
                Login.log("On " + customFormatter.format(now) + " user=" + rs.getString("User_Name") + " SUCCESSFULLY logged in\n");
                setLoggedInUserId(rs.getInt("User_ID"));
                return true;
            }
        }
        Login.log("On " + customFormatter.format(now) + " user=" + user + " UNSUCCESSFULLY logged in\n");
        return false;

    }

    /**Logs all login attempts
     * @param lineToAppend string that will be written to file
     */
    public static void log(String lineToAppend) throws IOException {
        String filePath = "..\\schoolCRM\\login_activity.txt";
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(lineToAppend);
        fw.close();
    }
}