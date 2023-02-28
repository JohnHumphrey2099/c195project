package com.humphrey.DAO;
//
import java.sql.DriverManager;

/**
 * The DAO class that manages the connection to the database.
 */
public abstract class JDBC {
    /**
     * The protocol.
     */
    private static final String protocol = "jdbc";
    /**
     * The vendor.
     */
    private static final String vendor = ":mysql:";
    /**
     * The location.
     */
    private static final String location = "//localHost/"; //"//192.168.0.132/"
    /**
     * The name of the database.
     */
    private static final String databaseName = "client_schedule";
    /**
     * The combined string of the protocol, vendor, location, database name, and the time zone.
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**
     * The driver to connect.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * The username to connect to the database.
     */
    private static final String userName = "sqlUser"; // Username
    /**
     * The password to connect to the database.
     */
    private static String password = "Passw0rd!"; // Password
    /**
     * The connection interface
     */
    public static java.sql.Connection connection;  // Connection Interface

    /**
     * Opens the connection to the database.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver.
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Closes the connection to the database.
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
}
