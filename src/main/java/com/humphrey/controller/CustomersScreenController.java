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

/**
 * The controller class for the Customers screen.
 */
public class CustomersScreenController implements Initializable {
    /**
     * The table showing all customer data.
     */
    @FXML
    private TableView customersTable;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn idColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn nameColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn addressColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn phoneColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn postalCodeColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn createDateColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn createdByColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn lastUpdateColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn lastUpdatedByColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn firstLevelColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn divisionNameColumn;
    /**
     * Column in the Customer Table.
     */
    @FXML
    private TableColumn countryColumn;
    /**
     * Field to get user-input.
     */
    @FXML
    private TextField idField;
    /**
     * Field to get user-input.
     */
    @FXML
    private TextField nameField;
    /**
     * Field to get user-input.
     */
    @FXML
    private TextField addressField;
    /**
     * Field to get user-input.
     */
    @FXML
    private TextField postalCodeField;
    /**
     * Field to get user-input.
     */
    @FXML
    private TextField phoneField;
    /**
     * A combo-box to select a country.
     */

    @FXML
    private ComboBox<Country> countryComboBox;
    /**
     * A combo-box to select a first level division.
     */
    @FXML
    private ComboBox<FirstLevelDivision> firstLevelComboBox;
    /**
     * Used to select the correct country in the country combo-box when a customer is being edited.
     * Also used to populate the division combo-box based on the country above. selected in the combo-box.
     */
    @FXML
    private Country selectedCountry;
    /**
     * Used to select the correct division combo-box based on the country above.
     */
    @FXML
    private FirstLevelDivision selectedDivision;
    /**
     * Used to hold the customer object of the customer to be edited.
     */
    @FXML
    private Customer customerToEdit;
    /**
     * Holds the user object of the currently logged in user.
     */

    private static User currentUser;
    /**
     * Flag to determine if there is a customer currently being edited.
     */

    private boolean customerIsNew = true;

    /**
     * Populates the customer data table, the country combo-box, and the first level division combo-box.
     * @param url url
     * @param resourceBundle resourceBundle
     */
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

    /**
     * Loads the main menu screen.
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
     * Overwrites the saved data for a selected customer in the database.
     * @param actionEvent Button click.
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
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

    /**
     * Reloads the customers screen, effectively clearing the user input fields and resetting the combo-boxes.
     * @param actionEvent Button click.
     * @throws IOException IOException
     */
    @FXML
    private void clearButton(ActionEvent actionEvent) throws IOException {
        reloadScreen(actionEvent);
        customerIsNew = true;
    }

    /**
     * Filters the first level division combo-box when a country is chosen in the country combo-box.
     * @param actionEvent Button click.
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
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

    /**
     * Gets the data from the selected customer in the customer table and populates the user input fields and combo-boxes with existing data.
     * @param actionEvent When a customer is selected.
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
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

    /**
     * Gets the customer data from the user input fields and combo-boxes and sends it to the database as a new entry.
     * @param actionEvent Button click.
     * @throws IOException IOException
     * @throws SQLException SQLException
     */
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

    /**
     * Sets the current user object to the currentUser variable.
     * @param user The current user object.
     */

    public static void setCurrentUser(User user){
        currentUser = user;
    }

    /**
     * Reloads the customers screen.
     * @param actionEvent A button click.
     * @throws IOException IOException
     */
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

    /**
     * Deletes the currently selected customer from the database.
     * @param actionEvent A button click.
     * @throws SQLException SQLException
     * @throws IOException IOException
     */
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

    /**
     * Checks to make sure no text field is empty or blank and every combo-box has a selected item. Launches an alert when it reaches a problem.
     * @return True if all data is valid.
     */
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

    /**
     * Loads the Main Menu.
     * @throws IOException IOException
     */
    private void goToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) (nameField.getScene().getWindow());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

}
