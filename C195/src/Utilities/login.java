package Utilities;

import model.loginlogger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class login {

    public static boolean validateUserPassword(String user, String pass) throws SQLException, IOException {
        Instant now = Instant.now();
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ssa z").withZone(ZoneId.of("UTC"));

        if(user == null || user.isEmpty() || user.isBlank())
            user = "BLANK";

        String sql = "SELECT User_Name, Password FROM users WHERE User_Name=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (rs.getString("User_Name").equals(user) && rs.getString("Password").equals(pass)) {
                loginlogger.log("On " + customFormatter.format(now) + " user=" + rs.getString("User_Name") + " SUCCESSFULLY logged in\n");
                return true;
            }
        }
        loginlogger.log("On " + customFormatter.format(now) + " user=" + user + " UNSUCCESSFULLY logged in\n");
        return false;

    }
}