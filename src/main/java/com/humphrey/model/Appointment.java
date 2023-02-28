package com.humphrey.model;

import javafx.scene.control.ComboBox;

import java.time.LocalDateTime;

/**
 * The model class of the Appointment object.
 */
public class Appointment {
    /**
     * ID of the appointment.
     */
    private int appointmentID;
    /**
     * Title of the appointment.
     */
    private String appointmentTitle;
    /**
     * Description of the appointment.
     */
    private String appointmentDescription;
    /**
     * Location of the appointment.
     */
    private String appointmentLocation;
    /**
     * Type of the appointment.
     */
    private String appointmentType;
    /**
     * Start time of the appointment.
     */
    private LocalDateTime appointmentStart;
    /**
     * End time of the appointment.
     */
    private LocalDateTime appointmentEnd;
    /**
     * Create Date of the appointment.
     */
    private LocalDateTime createDate;
    /**
     * Who the appointment was created by.
     */
    private String createdBy;
    /**
     * Who updated the appointment last.
     */
    private LocalDateTime lastUpdated;
    /**
     * When the appointment was last updated.
     */
    private String lastUpdatedBy;
    /**
     * The id of the customer associated with the appointment.
     */
    private int customerID;
    /**
     * The id of the user associated with the appointment.
     */
    private int userID;
    /**
     * The id of the contact associated with the appointment.
     */
    private int contactID;
    /**
     * The name of the customer associated with the appointment.
     */
    private String customerName;
    /**
     * The name of the user associated with the appointment.
     */
    private String userName;
    /**
     * The name of the contact associated with the appointment.
     */
    private String contactName;

    /**
     * Appointment constructor.
     * @param appointmentID ID of the appointment.
     * @param appointmentTitle Title of the appointment.
     * @param appointmentDescription Description of the appointment.
     * @param appointmentLocation Location of the appointment.
     * @param appointmentType Type of the appointment.
     * @param appointmentStart Start time of the appointment.
     * @param appointmentEnd End time of the appointment.
     * @param createDate Create Date of the appointment.
     * @param createdBy Who the appointment was created by.
     * @param lastUpdated When the appointment was last updated.
     * @param lastUpdatedBy Who updated the appointment last.
     * @param customerID The id of the user associated with the appointment.
     * @param userID The id of the contact associated with the appointment.
     * @param contactID The id of the contact associated with the appointment.
     * @param customerName The name of the customer associated with the appointment.
     * @param userName The name of the user associated with the appointment.
     * @param contactName The name of the contact associated with the appointment.
     */

    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation,
                       String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerID, int userID, int contactID, String customerName, String userName, String contactName) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.customerName = customerName;
        this.userName = userName;
        this.contactName = contactName;
    }

    /**
     * Gets the appointment ID.
     * @return The appointment ID.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets the appointment title.
     * @return The appointment title.
     */

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * Gets the appointment description.
     * @return The appointment description.
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Gets the appointment location.
     * @return The appointment location.
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    /**
     * Gets the appointment type.
     * @return The appointment type.
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Gets the appointment start time.
     * @return The appointment start time.
     */
    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }
    /**
     * Gets the appointment end time.
     * @return The appointment end time.
     */
    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }
    /**
     * Gets the appointment Customer ID.
     * @return The appointment Customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the appointment User ID.
     * @return The appointment User ID.
     */
    public int getUserID() {
        return userID;
    }
    /**
     * Gets the appointment Contact ID.
     * @return The appointment Contact ID.
     */

    public int getContactID() {
        return contactID;
    }
    /**
     * Gets the appointment Create Date.
     * @return The appointment Create Date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Gets the appointment Create By.
     * @return The appointment Create By.
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * Gets the time the appointment was last updated.
     * @return The time the appointment was last updated.
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Gets the name of the user who last updated the appointment.
     * @return The name of the user who last updated the appointment.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Gets the name of the customer associated with the appointment.
     * @return The name of the customer associated with the appointment.
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * Gets the name of the user associated with the appointment.
     * @return The name of the user associated with the appointment.
     */

    public String getUserName() {
        return userName;
    }

    /**
     * Gets the name of the contact associated with the appointment.
     * @return The name of the contact associated with the appointment.
     */
    public String getContactName() {
        return contactName;
    }

}
