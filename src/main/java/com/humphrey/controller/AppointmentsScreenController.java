package com.humphrey.controller;

import com.humphrey.DAO.AppointmentDB;
import com.humphrey.DAO.ContactsDB;
import com.humphrey.DAO.CustomerDB;
import com.humphrey.DAO.UsersDB;
import com.humphrey.Utilities.Util;
import com.humphrey.model.*;
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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;
/**
 * Controller for Appointments Screen
 * */
public class AppointmentsScreenController implements Initializable {
    /**
     * Table that shows appointments
     */
    @FXML
    private TableView appointmentsTable;
    /**
     * Column that holds appointment data
     */
    @FXML
    private TableColumn idColumn, titleColumn, descriptionColumn, locationColumn, contactColumn, typeColumn, startColumn, endColumn, customerColumn, userColumn;
    /**
     * Field to get appointment data from user
     */
    @FXML
    private TextField idField, titleField, descriptionField, locationField, typeField;
    /**
     * Dropdown menu that holds a list of users to choose from
     */
    @FXML
    private ComboBox<User> userCombo;
    /**
     * Dropdown menu that holds a list of contacts to choose from
     */
    @FXML
    private ComboBox<Contact> contactCombo;
    /**
     * Dropdown menu that holds a list of customers to choose from
     */
    @FXML
    private ComboBox<Customer> customerCombo;
    /**
     * Dropdown menu that holds a list of times to choose from
     */
    @FXML
    private ComboBox<LocalTime> startTime, endTime;
    /**
     * Lets the user choose a date for their appointment
     */
    @FXML private DatePicker datePicker;
    /**
     * Holds the user object for the current logged-in user
     */
    @FXML
    private static User currentUser;
    /**
     * A flag to show whether there is an appointment being currently edited
     */
    @FXML
    boolean appointmentIsNew;
    /**
     * The selected appointment to be edited
     */
    @FXML
    Appointment selectedAppointment;

    /**
     * Initializes the Appointments Screen.
     * @param url javafx setting
     * @param resourceBundle language bundles
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Util.checkConnection()) {
            selectedAppointment = null;
            appointmentIsNew = true;
            try {
                appointmentsTable.setItems(AppointmentDB.queryAppointmentsDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

            datePicker.setValue(LocalDate.now());

            try {
                userCombo.setItems(UsersDB.queryUsersDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                contactCombo.setItems(ContactsDB.queryContactDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                customerCombo.setItems(CustomerDB.queryCustomersDB());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            startTime.setItems(generateAppointmentTimes());
            endTime.setItems(generateAppointmentTimes());
        }
        else {
            selectedAppointment = null;
            appointmentIsNew = true;
            try {
                goToMain();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Launches the main menu screen upon button click.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void backButton(ActionEvent actionEvent) throws IOException {
        selectedAppointment = null;
        appointmentIsNew = true;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void setCurrentUser(User user){
        currentUser = user;
    }
    @FXML
    private void saveNewButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            boolean dataValid;
            boolean overlapping = false;
            int customerID;
            int userID;
            int contactID;

            String title, description, location, type, createdBy, lastUpdatedBy;
            LocalDateTime appointmentStart, appointmentEnd, createDate, lastUpdated;
            createDate = LocalDateTime.now();
            createdBy = currentUser.getUserName();
            lastUpdated = LocalDateTime.now();
            lastUpdatedBy = currentUser.getUserName();
            dataValid = dataValidation();
            if (dataValid) {
                appointmentStart = LocalDateTime.of(datePicker.getValue(), startTime.getValue());
                appointmentEnd = LocalDateTime.of(datePicker.getValue(), endTime.getValue());
                isOverlapping lambda = (start1, end1, start2, end2) -> start1.isBefore(end2) && start2.isBefore(end1);
                for (Appointment appointment : AppointmentDB.queryAppointmentsDB()) {
                    if (lambda.check(appointmentStart, appointmentEnd, appointment.getAppointmentStart(), appointment.getAppointmentEnd())) {
                        overlapping = true;
                        break;
                    }
                }
            }
            if (dataValid) {
                if (!overlapping) {
                    appointmentStart = LocalDateTime.of(datePicker.getValue(), startTime.getValue());
                    appointmentEnd = LocalDateTime.of(datePicker.getValue(), endTime.getValue());
                    title = titleField.getText();
                    description = descriptionField.getText();
                    location = locationField.getText();
                    type = typeField.getText();
                    customerID = customerCombo.getValue().getCustomerID();
                    userID = userCombo.getValue().getUserID();
                    contactID = contactCombo.getValue().getContactID();

                    AppointmentDB.addNewAppointment(title, description, location, type, appointmentStart, appointmentEnd, createDate, createdBy,
                            lastUpdated, lastUpdatedBy, customerID, userID, contactID);

                    Util.popUp("Success", "Success", "Appointment Saved");
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/AppointmentsScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
                    stage.setTitle("Appointments");
                    stage.setScene(scene);
                    stage.show();
                    selectedAppointment = null;
                    appointmentIsNew = true;
                } else {
                    Util.popUp("Fail", "Overlapping Appointments", "The selected date and time for this appointment overlaps with an existing appointment. Please choose another date / time and try again.");
                }
            }
        }
    }
    @FXML
    private void clearButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/AppointmentsScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
        selectedAppointment = null;
        appointmentIsNew = true;
    }
    @FXML
    private void editButton(ActionEvent actionEvent) throws IOException {
        if(Util.checkConnection()) {
            selectedAppointment = (Appointment) (appointmentsTable.getSelectionModel().getSelectedItem());
            if (!(appointmentsTable.getSelectionModel().getSelectedItem() == null)) {
                appointmentIsNew = false;
                LocalTime localStart = selectedAppointment.getAppointmentStart().toLocalTime();
                LocalTime localEnd = selectedAppointment.getAppointmentEnd().toLocalTime();
                LocalDate localDate = selectedAppointment.getAppointmentStart().toLocalDate();
                idField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                titleField.setText(selectedAppointment.getAppointmentTitle());
                descriptionField.setText(selectedAppointment.getAppointmentDescription());
                locationField.setText(selectedAppointment.getAppointmentLocation());
                typeField.setText(selectedAppointment.getAppointmentType());
                for (LocalTime t : startTime.getItems()) {
                    if (localStart.getHour() == t.getHour() && localStart.getMinute() == t.getMinute()) {
                        startTime.getSelectionModel().select(t);
                    }
                }
                for (LocalTime t : endTime.getItems()) {
                    if (localEnd.getHour() == t.getHour() && localStart.getMinute() == t.getMinute()) {
                        endTime.getSelectionModel().select(t);
                    }
                }
                datePicker.setValue(localDate);
                for (User u : userCombo.getItems()) {
                    if (selectedAppointment.getUserID() == u.getUserID()) {
                        userCombo.getSelectionModel().select(u);
                    }
                }
                for (Contact c : contactCombo.getItems()) {
                    if (selectedAppointment.getContactID() == c.getContactID()) {
                        contactCombo.getSelectionModel().select(c);
                    }
                }
                for (Customer c : customerCombo.getItems()) {
                    if (selectedAppointment.getCustomerID() == c.getCustomerID()) {
                        customerCombo.getSelectionModel().select(c);
                    }
                }
            } else {
                Util.popUp("Error", "No Appointment Selected", "Please select an appointment and try again");
            }
        }
        else{
            goToMain();
        }
    }
    @FXML
    private void deleteButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            selectedAppointment = (Appointment) (appointmentsTable.getSelectionModel().getSelectedItem());
            if (!(selectedAppointment == null)) {
                int id = selectedAppointment.getAppointmentID();
                AppointmentDB.deleteAppointmentByID(id);
                Util.popUp("Success", "Success", "Appointment ID" + id + " Deleted. Type:" + " " + selectedAppointment.getAppointmentType());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/AppointmentsScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
                stage.setTitle("Appointments");
                stage.setScene(scene);
                stage.show();
                selectedAppointment = null;
                appointmentIsNew = true;
            } else {
                Util.popUp("ERROR", "ERROR", "No Appointment selected. Please select an appointment and try again.");
            }
        }
        else{
            goToMain();
        }
    }
    @FXML
    private void radioAll(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            selectedAppointment = null;
            appointmentIsNew = true;

            appointmentsTable.setItems(AppointmentDB.queryAppointmentsDB());

            idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        }
        else{
            goToMain();
        }

    }
    @FXML
    private void radioWeek(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            LocalDate today = LocalDate.now();
            LocalDate firstOfTheWeek = (today.with(ChronoField.DAY_OF_WEEK, 1)).minusDays(1);
            LocalDate lastDayOfTheWeek = (today.with(ChronoField.DAY_OF_WEEK, 7)).minusDays(1);
            selectedAppointment = null;
            appointmentIsNew = true;

            appointmentsTable.setItems(AppointmentDB.getAppointmentsBetween(firstOfTheWeek, lastDayOfTheWeek));

            idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        }
        else{
            goToMain();
        }

    }
    @FXML
    private void radioMonth(ActionEvent actionEvent) throws SQLException,IOException {
        if(Util.checkConnection()) {
            LocalDate today = LocalDate.now();
            LocalDate first = today.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate last = today.with(TemporalAdjusters.lastDayOfMonth());
            selectedAppointment = null;
            appointmentIsNew = true;

            appointmentsTable.setItems(AppointmentDB.getAppointmentsBetween(first, last));

            idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        }
        else{
            goToMain();
        }


    }
    @FXML
    private void saveChangesButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Util.checkConnection()) {
            int id = 0;
            boolean dataValid;
            if (!appointmentIsNew) {
                try {
                    if (idField.getText() != null && !((idField.getText().trim().isEmpty()))) {
                        id = Integer.parseInt(idField.getText());
                    }
                } catch (Error e) {
                }
                dataValid = dataValidation();
                LocalDateTime appointmentStart;
                LocalDateTime appointmentEnd;
                LocalDateTime lastUpdated = LocalDateTime.now();
                String lastUpdatedBy = currentUser.getUserName();
                Boolean overlapping = false;
                if (dataValid) {
                    appointmentStart = LocalDateTime.of(datePicker.getValue(), startTime.getValue());
                    appointmentEnd = LocalDateTime.of(datePicker.getValue(), endTime.getValue());
                    isOverlapping lambda = (start1, end1, start2, end2) -> start1.isBefore(end2) && start2.isBefore(end1);
                    for (Appointment appointment : AppointmentDB.queryAppointmentsDB()) {
                        if (id != appointment.getAppointmentID()) {
                            if (lambda.check(appointmentStart, appointmentEnd, appointment.getAppointmentStart(), appointment.getAppointmentEnd())) {
                                overlapping = true;
                                break;
                            }
                        }
                    }
                }
                if (dataValid) {
                    if (!overlapping) {
                        String title = titleField.getText();
                        String description = descriptionField.getText();
                        String location = locationField.getText();
                        String type = typeField.getText();
                        appointmentStart = LocalDateTime.of(datePicker.getValue(), startTime.getValue());
                        appointmentEnd = LocalDateTime.of(datePicker.getValue(), endTime.getValue());
                        int customerID = customerCombo.getValue().getCustomerID();
                        int userID = userCombo.getValue().getUserID();
                        int contactID = contactCombo.getValue().getContactID();
                        AppointmentDB.updateAppointment(id, title, description, location, type, appointmentStart, appointmentEnd,
                                lastUpdated, lastUpdatedBy, customerID, userID, contactID);

                        Util.popUp("Success", "Success", "Appointment Saved");

                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/AppointmentsScreen.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
                        stage.setTitle("Appointments");
                        stage.setScene(scene);
                        stage.show();
                        appointmentIsNew = true;
                        selectedAppointment = null;
                    } else {
                        Util.popUp("Fail", "Overlapping Appointments", "The selected date and time for this appointment overlaps with an existing appointment. Please choose another date / time and try again.");
                    }
                }
            } else {
                Util.popUp("Error", "No Appointment Selected", "Please Select an Appointment and To Edit First, or Click Save as New Appointment");

            }
        }
    }
    private ObservableList<LocalTime> generateAppointmentTimes(){
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
        LocalTime startingTime = LocalTime.of(8, 0);
        LocalTime lastTime = LocalTime.of(22, 0);
        boolean loop = true;
        while (loop){
            if (startingTime.isBefore(lastTime)){
                Util.lambdaTwo l2 = (t) -> Util.convertESTtoLocalZone(t);
                timeList.add((l2.convertTime(startingTime)));
                startingTime= startingTime.plusMinutes(30);
            }
            else {
                loop = false;
            }
        }
        return timeList;
    }
    private boolean dataValidation(){
        boolean dataValid = true;
        if(titleField.getText() != null && !((titleField.getText()).trim().isEmpty())){

        }
        else{
            Util.popUp("Error", "Error", "Title Field is Empty. Cannot Save");
            dataValid = false;
        }
        if(dataValid){
            if(descriptionField.getText() != null && !((descriptionField.getText()).trim().isEmpty())){

            }
            else{
                Util.popUp("Error", "Error", "Description Field is Empty. Cannot Save");
                dataValid = false;
            }
        }
        if(dataValid) {
            if (locationField.getText() != null && !((locationField.getText()).trim().isEmpty())) {

            }
            else {
                Util.popUp("Error", "Error", "Location Field is Empty. Cannot Save");
                dataValid = false;
            }
        }
        if(dataValid){
            if (typeField.getText() != null && !((typeField.getText()).trim().isEmpty())){

            }
            else{
                Util.popUp("Error", "Error", "Type Field is Empty. Cannot Save" ); dataValid = false;
            }
        }
        if(dataValid) {
            if (datePicker.getValue() != null) {
                if (startTime.getValue() != null && endTime.getValue() != null) {
                    if ((LocalDateTime.of(datePicker.getValue(), startTime.getValue())).isAfter((LocalDateTime.of(datePicker.getValue(), endTime.getValue())))) {
                        Util.popUp("Error", "Error", "End Time cannot be Before Start Time. Cannot Save.");
                        dataValid = false;
                    }
                }
                else {
                    Util.popUp("Error", "Error", "Start and End Times are not selected. Cannot Save");
                    dataValid = false;
                }
            }
            else{
                Util.popUp("Error", "Error", "No Date Selected. Cannot Save");
                dataValid = false;
            }
        }
        if(dataValid){
            if (customerCombo.getValue() != null){

            }
            else{
                Util.popUp("Error", "Error", " No Customer Selected. Cannot Save");
                dataValid = false;
            }
        }
        if(dataValid){
            if (userCombo.getValue() != null){

            }
            else{
                Util.popUp("Error", "Error", "No User Selected. Cannot Save");
                dataValid = false;
            }
        }
        if(dataValid){
            if (customerCombo.getValue() != null){

            }
            else{
                Util.popUp("Error", "Error", "No Contact Selected. Cannot Save");
                dataValid = false;
            }
        }

        return dataValid;
    }
    private void goToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/humphrey/view/MainMenu.fxml"));
        Stage stage = (Stage) (typeField.getScene().getWindow());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    @FunctionalInterface
    public interface isOverlapping{
       boolean check(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2);
    }

}

