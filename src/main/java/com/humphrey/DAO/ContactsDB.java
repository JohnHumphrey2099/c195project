package com.humphrey.DAO;

import com.humphrey.Utilities.Util;
import com.humphrey.model.Appointment;
import com.humphrey.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The DAO class that operates on the Contacts table in the database.
 */
public abstract class ContactsDB {
    /**
     * Gets all rows from the contacts table.
     * @return The list of all rows from the contacts table.
     * @throws SQLException SQLException.
     */
    public static ObservableList<Contact> queryContactDB() throws SQLException {
        ObservableList<Contact> results = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Contacts;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");


            Contact newContact = new Contact(id, name);
            results.add(newContact);
        }
        return results;

    }
}
