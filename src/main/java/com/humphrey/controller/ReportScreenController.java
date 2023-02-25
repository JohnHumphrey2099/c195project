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

public class ReportScreenController implements Initializable {

    @FXML
    private TableView appointmentsTable;
    @FXML
    private TableColumn monthColumn;
    @FXML
    private TableColumn monthTotalColumn;
    @FXML
    private TableView contactTable;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn typeCol;
    @FXML
    private TableColumn descCol;
    @FXML
    private TableColumn startCol;
    @FXML
    private TableColumn endCol;
    @FXML
    private TableColumn customerCol;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private TableView countryTable;
    @FXML
    private TableColumn countryColumn;
    @FXML
    private TableColumn divColumn;
    @FXML
    private TableColumn typeColumn;

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


    @FXML
    private void backButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

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
    private void goToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) (contactTable.getScene().getWindow());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


}

