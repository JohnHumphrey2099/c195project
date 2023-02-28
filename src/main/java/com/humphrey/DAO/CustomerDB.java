package com.humphrey.DAO;

import com.humphrey.Utilities.Util;
import com.humphrey.model.Appointment;
import com.humphrey.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The DAO class that operates on the Customers table in the database.
 */
public class CustomerDB {
    /**
     * Creates a new entry in the customers table.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param createDate Today's date.
     * @param createdBy The current user.
     * @param lastUpdate Today's date.
     * @param updatedBy The current user.
     * @param divisionId the division ID of the customer.
     * @return The "Execute Update" statement. Returns the number of rows affected.
     * @throws SQLException SQLException
     */
    public static int addNewCustomer(String customerName, String address, String postalCode, String phone,
                                     LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, int divisionId) throws SQLException {

        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7,Timestamp.valueOf(lastUpdate));
        ps.setString(8, updatedBy);
        ps.setInt(9, divisionId);
        return ps.executeUpdate();
    }

    /**
     * Updates an entry in the customers table.
     * @param customerID The id of the customer to be updated.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param createDate The original create date.
     * @param createdBy The original created by.
     * @param lastUpdate Today's date.
     * @param updatedBy The currently logged-in user.
     * @param divisionId The division id.
     * @return The Execute Update statement. Returns the number of rows affected.
     * @throws SQLException SQLException
     */
    public static int updateCustomer(int customerID, String customerName, String address, String postalCode, String phone, LocalDateTime createDate,
                                     String createdBy, LocalDateTime lastUpdate, String updatedBy, int divisionId) throws SQLException {

        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7,Timestamp.valueOf(lastUpdate));
        ps.setString(8, updatedBy);
        ps.setInt(9, divisionId);
        ps.setInt(10, customerID);
        return ps.executeUpdate();
    }

    /**
     * Returns a list of all rows in the customer table.
     * @return The list of all rows in the customer table.
     * @throws SQLException SQLException
     */
    public static ObservableList<Customer> queryCustomersDB() throws SQLException {
        ObservableList<Customer> customerResults = FXCollections.observableArrayList();
        String sql = "SELECT customers.*, first_level_divisions.Division, countries.Country FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = " +
                "first_level_divisions.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            LocalDateTime createDate = (rs.getTimestamp("Create_Date").toLocalDateTime());
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdateDate = (rs.getTimestamp("Last_Update").toLocalDateTime());
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            String country = rs.getString("Country");
            Customer newCustomer = new Customer(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdateDate, lastUpdatedBy, divisionID, divisionName, country);
            customerResults.add(newCustomer);
        }
        return customerResults;

    }

    /**
     * Deletes a customer that matches a given customer ID.
     * @param customerID The customer id of the customer to delete.
     * @return The "Execute Update" statement. Returns the number of rows affected.
     * @throws SQLException SQLException
     */
    public static int deleteCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        return ps.executeUpdate();

    }

}
