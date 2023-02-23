package com.humphrey.controller;
//
import com.humphrey.DAO.AppointmentDB;
import com.humphrey.DAO.JDBC;
import com.humphrey.DAO.UsersDB;
import com.humphrey.Utilities.Util;
import com.humphrey.model.Appointment;
import com.humphrey.model.Main;
import com.humphrey.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private ObservableList<User> userQuery = FXCollections.observableArrayList();
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label locationLabel;

    private Appointment appointmentSoon = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(JDBC.connection != null) {
            Platform.runLater(() -> anchorPane.requestFocus());
            try {
                userQuery = UsersDB.queryUsersDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ZoneId zoneId = ZoneId.systemDefault();
            locationLabel.setText(zoneId.toString());
        }
        else{
                Util.popUp("Error", "SQL Server Connection Error", "There was an error connecting to the server. Please check connection and try again");
                System.exit(0);
            }
    }

    @FXML
    private void loginButton(ActionEvent actionEvent) throws IOException, SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("/Nat", Locale.getDefault());
        boolean loginAuthenticated = false;
        String userMatch = userNameField.getText();
        String passwordMatch = passwordField.getText();
        for (User u : userQuery) {
            if (Objects.equals(userMatch, u.getUserName())) {
                if (Objects.equals(passwordMatch, u.getPassword())) {
                    loginAuthenticated = true;
                    CustomersScreenController.setCurrentUser(u);
                    AppointmentsScreenController.setCurrentUser(u);
                }
            }
        }
        if (loginAuthenticated == true) {
            loginLog(userMatch, "successful");
            for (Appointment a : AppointmentDB.queryAppointmentsDB()) {
                if ((ChronoUnit.MINUTES.between(a.getAppointmentStart(), LocalDateTime.now())) >= -15 &&
                        (ChronoUnit.MINUTES.between(a.getAppointmentStart(), LocalDateTime.now())) <= 0) {
                    appointmentSoon = a;
                    break;
                }
            }
            if (!(appointmentSoon == null)) {
                Util.popUp(
                        (bundle.getString("login")),
                        ("ID: " + appointmentSoon.getAppointmentID() + " " + (bundle.getString("startingAt") + " " + appointmentSoon.getAppointmentStart())),
                        ("ID: " + appointmentSoon.getAppointmentID() + " " + (bundle.getString("startingAt") + " " + appointmentSoon.getAppointmentStart()))
                );

            } else {
                Util.popUp((bundle.getString("login")), (bundle.getString("login")), (bundle.getString("loginSuccess")));

            }
            //go to next screen
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
        } else {
            loginLog(userMatch, "not successful");
            Util.popUp(
                    (bundle.getString("fail")),
                    (bundle.getString("fail")),
                    (bundle.getString("loginFailed"))
            );
        }
    }

    private void loginLog(String userName, String loginStatus) {
        try {
            File log = new File("login_activity.txt");
            log.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter logWriter = new FileWriter("login_activity.txt", true);
            logWriter.write((LocalDateTime.now()) + " - Username: " + userName + ". Login was " + loginStatus + ".\n");
            logWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            ;
        }
    }
}