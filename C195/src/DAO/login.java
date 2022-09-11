package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class login {

    public static boolean validateUserPassword(String user, String pass) throws SQLException {
        String sql = "SELECT User_Name, Password FROM users WHERE User_Name=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            if (rs.getString("Password").equals(pass))
                return true;
        return false;
    }

}
