package com.humphrey.controller;


import com.humphrey.model.Main;
import com.humphrey.model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The controller class for the main menu screen.
 */
public class MainMenuController {
    /**
     * Closes the application
     * @param actionEvent Button click.
     */
    @FXML
    private void exitButton(ActionEvent actionEvent){
        Platform.exit();
    }

    /**
     * Loads the customers screen.
     * @param actionEvent Button click.
     * @throws IOException IOException
     */
    @FXML
    private void customersButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Loads the appointments screen.
     * @param actionEvent Button click.
     * @throws IOException IOException
     */

    @FXML
    private void appointmentsButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/AppointmentsScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Loads the reports screen.
     * @param actionEvent Button click.
     * @throws IOException IOException
     */

    @FXML
    private void reportsButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/ReportScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
