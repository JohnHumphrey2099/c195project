package com.humphrey.DAO;

import com.humphrey.Utilities.Util;
import com.humphrey.model.Appointment;
import com.humphrey.model.MonthReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The DAO class that operates on the Appointment Table in the database.
 */
public class AppointmentDB {
    /**
     * Adds a new Appointment to the database.
     * @param title Appointment title .
     * @param description  Appointment description.
     * @param location   Appointment location.
     * @param type   Appointment type.
     * @param start   Appointment start time.
     * @param end   Appointment end time.
     * @param createDate   Today's date.
     * @param createdBy   Current User.
     * @param lastUpdate    Today's date.
     * @param lastUpdatedBy Current User.
     * @param customerID    Customer id of appointment.
     * @param userID    User id of appointment.
     * @param contactID Contact id of appointment.
     * @return "Execute Update" statement. Returns true / false.
     * @throws SQLException SQLException
     */

    public static int addNewAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate,
                                        String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(createDate));
        ps.setString(8, createdBy);
        ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);
        return ps.executeUpdate();
    }

    /**
     * Updates an existing appointment in the database with new information.
     * @param id Appointment of the idea to be modified.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param start Appointment start time.
     * @param end Appointment end time.
     * @param lastUpdate Today's date.
     * @param lastUpdatedBy Current User.
     * @param customerID Customer id of appointment.
     * @param userID User id of appointment.
     * @param contactID Contact id of appointment.
     * @return "Execute Update" statement. Returns true / false.
     * @throws SQLException SQLException
     */
    public static int updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                                        LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdate));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, id);
        return ps.executeUpdate();
    }

    /**
     * Get's all rows from Appointment table along with select columns joined from the Customer, Contact, and Users table.
     * @return A list containing the queried data.
     * @throws SQLException SQLException
     */
    public static ObservableList<Appointment> queryAppointmentsDB() throws SQLException {
        ObservableList<Appointment> results = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*,  contacts.Contact_Name, customers.Customer_Name, users.User_Name FROM " +
                "client_schedule.appointments inner Join contacts on appointments.Contact_ID = contacts.Contact_ID " +
                "inner join customers on appointments.Customer_ID = customers.Customer_ID inner join " +
                "users on appointments.User_ID = users.User_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = (rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = (rs.getTimestamp("End").toLocalDateTime());
            LocalDateTime createDate = (rs.getTimestamp("Create_Date").toLocalDateTime());
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdateDate = (rs.getTimestamp("Last_Update").toLocalDateTime());
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String customerName = rs.getString("Customer_Name");
            String userName = rs.getString("User_Name");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentID, appointmentTitle, description, location, type, start, end,
                    createDate, createdBy, lastUpdateDate, lastUpdatedBy, customerID, userID, contactID, customerName, userName, contactName);
            results.add(newAppointment);
        }
        return results;

    }

    /**
     * Deletes all appointments for a chosen customer. Used when deleting a customer from the customer table.
     * @param id The customer id of the customer to be deleted.
     * @return The "Execute Update" statement. Returns number of rows affected.
     * @throws SQLException SQLException
     */
    public static int deleteAppointmentByCustomerID(int id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }

    /**
     * Deletes appointment that has given appointment id.
     * @param id The id of the appointment to be deleted.
     * @return The "Execute Update" statement. Returns number of rows affected.
     * @throws SQLException SQLException
     */
    public static int deleteAppointmentByID(int id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    }

    /**
     * Gets all appointments between a certain date range.
     * @param date1 First date in the range.
     * @param date2 Second date in the range.
     * @return Returns the list of appointments that occur between the given range.
     * @throws SQLException SQLException
     */
    public static ObservableList<Appointment> getAppointmentsBetween(LocalDate date1, LocalDate date2) throws SQLException {
        LocalTime lt1 = LocalTime.of(00,01);
        LocalTime lt2 = LocalTime.of(11,59);
        LocalDateTime ldt1 = LocalDateTime.of(date1, lt1);
        LocalDateTime ldt2 = LocalDateTime.of(date2, lt2);

        String sql = "SELECT appointments.*,  contacts.Contact_Name, customers.Customer_Name, users.User_Name FROM " +
                "client_schedule.appointments inner Join contacts on appointments.Contact_ID = contacts.Contact_ID " +
                "inner join customers on appointments.Customer_ID = customers.Customer_ID inner join " +
                "users on appointments.User_ID = users.User_ID WHERE Appointments.Start BETWEEN ? AND ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(ldt1));
        ps.setTimestamp(2, Timestamp.valueOf(ldt2));
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> results = FXCollections.observableArrayList();
        while (rs.next()) {

            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdateDate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String customerName = rs.getString("Customer_Name");
            String userName = rs.getString("User_Name");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentID, appointmentTitle, description, location, type, start, end,
                    createDate, createdBy, lastUpdateDate, lastUpdatedBy, customerID, userID, contactID, customerName, userName, contactName);
            results.add(newAppointment);
        }
        return results;
    }

    /**
     * Gets all appointments containing a given contact id.
     * @param contactID The id to search with.
     * @return The list of appointments containing a given contact id.
     * @throws SQLException SQLException
     */

    public static ObservableList<Appointment> getAppointmentsByContact(int contactID) throws SQLException {
        ObservableList<Appointment> results = FXCollections.observableArrayList();
        String sql = "SELECT appointments.*,  contacts.Contact_Name, customers.Customer_Name, users.User_Name FROM " +
                "client_schedule.appointments inner Join contacts on appointments.Contact_ID = contacts.Contact_ID " +
                "inner join customers on appointments.Customer_ID = customers.Customer_ID inner join " +
                "users on appointments.User_ID = users.User_ID where appointments.Contact_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = (rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime end = (rs.getTimestamp("End").toLocalDateTime());
            LocalDateTime createDate = (rs.getTimestamp("Create_Date").toLocalDateTime());
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdateDate = (rs.getTimestamp("Last_Update").toLocalDateTime());
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            String customerName = rs.getString("Customer_Name");
            String userName = rs.getString("User_Name");
            String contactName = rs.getString("Contact_Name");
            Appointment newAppointment = new Appointment(appointmentID, appointmentTitle, description, location, type, start, end,
                    createDate, createdBy, lastUpdateDate, lastUpdatedBy, customerID, userID, contactID, customerName, userName, contactName);
            results.add(newAppointment);
        }
        return results;
    }

    /**
     * Gets a list of all appointments grouped by month and type.
     * @return The list of all appointments grouped by month and type.
     * @throws SQLException SQLException
     */
    public static ObservableList<MonthReport> appointmentsByMonth() throws SQLException {
        ObservableList<MonthReport> results = FXCollections.observableArrayList();
        String sql = "SELECT MONTHNAME(Start), type, COUNT(*) FROM appointments GROUP BY monthname(Start), type;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String month = rs.getString("MONTHNAME(Start)");
            String type = rs.getString("type");
            int count = rs.getInt("COUNT(*)");


            MonthReport report = new MonthReport(month, type, count);
            results.add(report);
        }
        return results;
    }
}