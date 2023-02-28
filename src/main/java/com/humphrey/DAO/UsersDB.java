package com.humphrey.DAO;

import com.humphrey.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The DAO class that operates on the Users table in the database.
 */
public abstract class UsersDB {
    /**
     * Returns a list of all rows in the users table.
     * @return the list of all rows in the users table.
     * @throws SQLException SQLException
     */
    public static ObservableList<User> queryUsersDB()throws SQLException {
        ObservableList<User> userResults = FXCollections.observableArrayList();
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            LocalDateTime createDate = (rs.getTimestamp("Create_Date").toLocalDateTime());
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdateDate = (rs.getTimestamp("Last_Update").toLocalDateTime());
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            User newUser = new User(userID, userName, password, createDate, createdBy, lastUpdateDate, lastUpdatedBy);
            userResults.add(newUser);
        }
        return userResults;

    }

}
