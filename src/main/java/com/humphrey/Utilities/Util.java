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

public abstract class Util {

    public static void popUp(String title, String head, String body) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(head);
                alert.setContentText(body);
                alert.showAndWait();
    }

    public static LocalTime convertESTtoLocalZone(LocalTime est){
            LocalDate date = LocalDate.now();
            LocalDateTime ldt = LocalDateTime.of(date, est);
            ZonedDateTime estZonedDateTime = ldt.atZone(ZoneId.of("America/New_York"));
            ZonedDateTime localZonedDateTime = estZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
            return localZonedDateTime.toLocalTime();
    }

    public static boolean checkConnection() {
        boolean isConnectionValid = true;
        if (JDBC.connection != null) {
        } else {
            Util.popUp("Error", "SQL Server Connection Error", "There was an error connecting to the server. Please check connection and try again");
            isConnectionValid = false;
        }
        return isConnectionValid;
    }

    @FunctionalInterface
    public interface lambdaTwo{
        LocalTime convertTime(LocalTime t);
    }
}
