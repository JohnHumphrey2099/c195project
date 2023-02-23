package com.humphrey.model;
//
import com.humphrey.DAO.JDBC;
import com.humphrey.Utilities.Util;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/Login.fxml"),bundle);
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle(bundle.getString("login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}