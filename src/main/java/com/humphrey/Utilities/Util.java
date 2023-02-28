package com.humphrey.Utilities;

import com.humphrey.DAO.JDBC;
import com.humphrey.model.CountryReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;

/**
 * The utility class.
 */
public abstract class Util {
    /**
     * Creates and displays a pop-up alert.
     * @param title The title of the alert.
     * @param head The heading of the alert.
     * @param body The body text of the alert.
     */
    public static void popUp(String title, String head, String body) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(head);
                alert.setContentText(body);
                alert.showAndWait();
    }

    /**
     * Converts a localTime object from EST to the local time zone.
     * @param est The localTime object to be converted.
     * @return The converted time.
     */
    public static LocalTime convertESTtoLocalZone(LocalTime est){
            LocalDate date = LocalDate.now();
            LocalDateTime ldt = LocalDateTime.of(date, est);
            ZonedDateTime estZonedDateTime = ldt.atZone(ZoneId.of("America/New_York"));
            ZonedDateTime localZonedDateTime = estZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
            return localZonedDateTime.toLocalTime();
    }

    /**
     * Checks to make sure the JDBC connection to the database exists. If not, it shows an alert notifying the user.
     * @return True if the connection exists.
     */
    public static boolean checkConnection() {
        boolean isConnectionValid = true;
        if (JDBC.connection != null) {
        } else {
            Util.popUp("Error", "SQL Server Connection Error", "There was an error connecting to the server. Please check connection and try again");
            isConnectionValid = false;
        }
        return isConnectionValid;
    }

    /**
     * The functional interface used with a lambda to convert a LocalTime t from EST to the local zone.
     */
    @FunctionalInterface
    public interface lambdaTwo{
        LocalTime convertTime(LocalTime t);
    }
}
