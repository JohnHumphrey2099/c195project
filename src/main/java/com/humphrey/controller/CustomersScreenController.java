package com.humphrey.controller;

import com.humphrey.DAO.*;
import com.humphrey.Utilities.Util;
import com.humphrey.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomersScreenController implements Initializable {
    @FXML
    private TableView customersTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private TableColumn phoneColumn;
    @FXML
    private TableColumn postalCodeColumn;
    @FXML
    private TableColumn createDateColumn;
    @FXML
    private TableColumn createdByColumn;
    @FXML
    private TableColumn lastUpdateColumn;
    @FXML
    private TableColumn lastUpdatedByColumn;
    @FXML
    private TableColumn firstLevelColumn;
    @FXML
    private TableColumn divisionNameColumn;
    @FXML
    private TableColumn countryColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<FirstLevelDivision> firstLevelComboBox;
    @FXML
    private Country selectedCountry;
    @FXML
    private FirstLevelDivision selectedDivision;
    @FXML
    private Customer customerToEdit;

    private static User currentUser;

    private boolean customerIsNew = true;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Util.checkConnection()) {
            try {
                customersTable.setItems(CustomerDB.queryCustomersDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            idColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            firstLevelColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionID"));
            divisionNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));

            try {
                ObservableList<Country> countryList = CountriesDB.queryCountriesDB();
                countryComboBox.setItems(countryList);
                countryComboBox.setVisibleRowCount(5);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                ObservableList<FirstLevelDivision> divisionList = FirstLevelDB.queryFirstLevelDB();
                firstLevelComboBox.setItems(divisionList);
                firstLevelComboBox.setVisibleRowCount(5);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
    private void saveChangesButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            if (!customerIsNew) {
                if (Util.checkConnection()) {
                    int customerID = 0;
                    boolean inputValid = validateInput();
                    try {
                        customerID = Integer.parseInt(idField.getText());
                    } catch (Error ignored) {
                    }
                    if (inputValid) {
                        String customerName = nameField.getText();
                        String customerAddress = addressField.getText();
                        String customerPostalCode = postalCodeField.getText();
                        String customerPhone = phoneField.getText();
                        int customerDivision = (firstLevelComboBox.getSelectionModel().getSelectedItem()).getDivisionID();
                        LocalDateTime createDate = customerToEdit.getCreateDate();
                        String createdBy = customerToEdit.getCreatedBy();
                        LocalDateTime lastUpdate = LocalDateTime.now();
                        String lastUpdatedBy = currentUser.getUserName();

                        CustomerDB.updateCustomer(customerID, customerName, customerAddress, customerPostalCode,
                                customerPhone, createDate, createdBy, lastUpdate, lastUpdatedBy, customerDivision);
                        Util.popUp("Success", "Success", "Customer Saved");
                        reloadScreen(actionEvent);
                        customerIsNew = true;
                    }
                }
            } else {
                Util.popUp("Error", "No Customer Selected", "Please Select a Customer First, or Click Save as New Customer");
            }
        }
        else{
            goToMain();
        }
    }

    @FXML
    private void clearButton(ActionEvent actionEvent) throws IOException {
        reloadScreen(actionEvent);
        customerIsNew = true;
    }

    @FXML
    private void countryChosen(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            if (selectedCountry != null) {
                ObservableList<FirstLevelDivision> divisionList = FirstLevelDB.queryFirstLevelDB();
                ObservableList<FirstLevelDivision> divisionFiltered = FXCollections.observableArrayList();

                for (FirstLevelDivision f : divisionList) {
                    if (selectedCountry.getCountryID() == f.getCountryID()) {
                        divisionFiltered.add(f);
                    }
                }
                firstLevelComboBox.setItems(divisionFiltered);
                firstLevelComboBox.setVisibleRowCount(5);
                firstLevelComboBox.getSelectionModel().selectFirst();
            }
        }
        else{
            goToMain();
        }
    }
    @FXML
    private void editCustomerButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            customerIsNew = false;
            Customer selectedItem = (Customer) (customersTable.getSelectionModel().getSelectedItem());
            customerToEdit = (Customer) (customersTable.getSelectionModel().getSelectedItem());

            if (selectedItem == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Customer Selected");
                alert.setHeaderText("ERROR");
                alert.setContentText("No Product Selected");
                alert.show();
            } else {
                ObservableList<Country> countryList = CountriesDB.queryCountriesDB();
                ObservableList<FirstLevelDivision> divisionList = FirstLevelDB.queryFirstLevelDB();
                ObservableList<FirstLevelDivision> divisionFiltered = FXCollections.observableArrayList();

                idField.setText(String.valueOf(selectedItem.getCustomerID()));
                nameField.setText(selectedItem.getCustomerName());
                addressField.setText(selectedItem.getCustomerAddress());
                postalCodeField.setText(selectedItem.getCustomerPostalCode());
                phoneField.setText(selectedItem.getCustomerPhone());
                countryComboBox.setItems(countryList);
                countryComboBox.setVisibleRowCount(5);
                for (Country c : countryList) {
                    if (Objects.equals(selectedItem.getCustomerCountry(), c.getCountry())) {
                        this.selectedCountry = c;
                    }
                }
                countryComboBox.setValue(selectedCountry);
                for (FirstLevelDivision f : divisionList) {
                    if (selectedCountry.getCountryID() == f.getCountryID()) {
                        divisionFiltered.add(f);
                    }
                }
                firstLevelComboBox.setItems(divisionFiltered);
                firstLevelComboBox.setVisibleRowCount(5);
                for (FirstLevelDivision d : divisionFiltered) {
                    if (selectedItem.getCustomerDivisionID() == d.getDivisionID()) {
                        this.selectedDivision = d;
                    }
                }
                firstLevelComboBox.setValue(selectedDivision);
            }
        }
        else{
            goToMain();
        }
    }
    @FXML
    private void saveNewButton(ActionEvent actionEvent) throws IOException, SQLException {
        if (Util.checkConnection()) {
            boolean inputValid = validateInput();
            if (inputValid) {
                String customerName = nameField.getText();
                String customerAddress = addressField.getText();
                String customerPostalCode = postalCodeField.getText();
                String customerPhone = phoneField.getText();
                LocalDateTime createDate = LocalDateTime.now();
                String createdBy = currentUser.getUserName();
                LocalDateTime lastUpdate = LocalDateTime.now();
                String lastUpdatedBy = currentUser.getUserName();
                int customerDivision = (firstLevelComboBox.getSelectionModel().getSelectedItem()).getDivisionID();
                CustomerDB.addNewCustomer(customerName, customerAddress, customerPostalCode,
                        customerPhone, createDate, createdBy, lastUpdate, lastUpdatedBy, customerDivision);
                Util.popUp("Success", "Success", "Customer Saved");
                reloadScreen(actionEvent);

            }
        }
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }

    @FXML
    private void reloadScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/CustomersScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
        customerIsNew = true;

    }

    @FXML
    private void deleteButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            Customer c = (Customer) customersTable.getSelectionModel().getSelectedItem();
            if (c == null) {
                Util.popUp("Error", "No Customer Selected", "Please select a customer in the Table and try again.");
            } else {
                Util.popUp("Appointments Deleted", "Appointments Deleted", "Customer had " + (AppointmentDB.deleteAppointmentByCustomerID(c.getCustomerID())) + " appointments that were also deleted.");
                CustomerDB.deleteCustomer(c.getCustomerID());
                Util.popUp("Success", "Success", "Customer Deleted");

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/CustomersScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
                stage.setTitle("Customers");
                stage.setScene(scene);
                stage.show();
            }
        }
        else{
            goToMain();
        }
    }
    
    private boolean validateInput() {
        boolean valid = true;
        if (nameField.getText() != null && !(nameField.getText().trim().isEmpty())) {

        } else {
            valid = false;
            Util.popUp("Error", "Error", "Name field is empty. Cannot Save");
        }
        if(valid) {
            if (addressField.getText() != null && !(addressField.getText().trim().isEmpty())) {
            } else {
                valid = false;
                Util.popUp("Error", "Error", "Address field is empty. Cannot Save");
            }
        }
        if(valid) {
            if (postalCodeField.getText() != null && !(postalCodeField.getText().trim().isEmpty())) {
            } else {
                valid = false;
                Util.popUp("Error", "Error", "Postal Code field is empty. Cannot Save");
            }
        }
        if(valid) {
            if (phoneField.getText() != null && !(phoneField.getText().trim().isEmpty())) {
            } else {
                valid = false;
                Util.popUp("Error", "Error", "Phone Number field is empty. Cannot Save");
            }
        }
        if(valid){
            if(firstLevelComboBox.getValue() != null){

            }
            else{
                valid = false;
                Util.popUp("Error", "Error", "No First Level Selected. Cannot Save");
            }
        }
        if(valid){
            if(countryComboBox.getSelectionModel().getSelectedItem() != null){

            }
            else{
                Util.popUp("Error", "Error", "No Country Selected. Please choose Country and try again");
                valid = false;
            }
        }
        return valid;
        
    }

    private void goToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) (nameField.getScene().getWindow());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

}
