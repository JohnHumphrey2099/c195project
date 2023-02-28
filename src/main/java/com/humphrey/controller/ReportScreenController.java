package com.humphrey.controller;

import com.humphrey.DAO.*;
import com.humphrey.Utilities.Util;
import com.humphrey.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The controller class for the Reports Screen
 */
public class ReportScreenController implements Initializable {
    /**
     * Table to show total appointments by month and type.
     */
    @FXML
    private TableView appointmentsTable;
    /**
     * Column in the appointments totals table.
     */
    @FXML
    private TableColumn monthColumn;
    /**
     * Column in the appointments totals table.
     */
    @FXML
    private TableColumn monthTotalColumn;
    /**
     * Table to show schedule for contacts.
     */
    @FXML
    private TableView contactTable;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn idCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn titleCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn typeCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn descCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn startCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn endCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private TableColumn customerCol;
    /**
     * Column in the contacts schedule table.
     */
    @FXML
    private ComboBox<Contact> contactCombo;
    /**
     * Table to show total first level divisions by country.
     */
    @FXML
    private TableView countryTable;
    /**
     * Column in the first level divisions totals table.
     */
    @FXML
    private TableColumn countryColumn;
    /**
     * Column in the first level divisions totals table.
     */
    @FXML
    private TableColumn divColumn;
    /**
     * Column in the first level divisions totals table.
     */
    @FXML
    private TableColumn typeColumn;

    /**
     * Populates the reports tables.
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Util.checkConnection()) {
            try {
                appointmentsTable.setItems(AppointmentDB.appointmentsByMonth());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            monthTotalColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
            try {
                contactCombo.setItems(ContactsDB.queryContactDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                countryTable.setItems(CountriesDB.countryReport());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            divColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        }
        else{
            try {
                goToMain();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Loads the main menu.
     * @param actionEvent Button click.
     * @throws IOException IOException
     */

    @FXML
    private void backButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the schedule table when a contact is selected.
     * @param actionEvent Contact selected.
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
    @FXML
    private void comboAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            Contact selectedContact = contactCombo.getSelectionModel().getSelectedItem();
            contactTable.setItems(AppointmentDB.getAppointmentsByContact(selectedContact.getContactID()));

            idCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
        else{
            goToMain();
        }
    }

    /**
     * Loads the main menu.
     * @throws IOException IOException
     */
    private void goToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) (contactTable.getScene().getWindow());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


}

