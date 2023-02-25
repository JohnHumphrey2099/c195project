package com.humphrey.DAO;

import com.humphrey.Utilities.Util;
import com.humphrey.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class UsersDB {

    public static int insert(String userName, String password, LocalDateTime createDate, String createdBy) throws SQLException {

        String sql = "INSERT INTO USERS (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setTimestamp(3,Timestamp.valueOf(createDate));
        ps.setString(4, userName);
        ps.setTimestamp(5,Timestamp.valueOf(createDate));
        ps.setString(6, userName);
        return ps.executeUpdate();
    }
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
    public static String getUserNameFromID(int id) throws SQLException {

        String sql = "SELECT User_Name FROM USERS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String userName = null;
        while (rs.next()) {
            userName = rs.getString("User_Name");
        }
        return userName;

    }
}
