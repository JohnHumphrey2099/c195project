package com.humphrey.model;

import java.time.LocalDateTime;

/**
 * The model class for the Customer object
 */
public class Customer {
    /**
     * The customer ID.
     */
    private int customerID;
    /**
     * The customer name.
     */
    private String customerName;
    /**
     * The customer address.
     */
    private String customerAddress;
    /**
     * The customer postal code.
     */
    private String customerPostalCode;
    /**
     * The customer phone number.
     */
    private String customerPhone;
    /**
     * The date the customer was created.
     */
    private LocalDateTime createDate;
    /**
     * Who the customer was created by.
     */
    private String createdBy;
    /**
     * The time that the customer was last updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * Who the customer was last updated by.
     */
    private String lastUpdatedBy;
    /**
     * The division ID associated with the customer.
     */
    private int customerDivisionID;
    /**
     * The name of the division that is associated with the customer.
     */

    private String customerDivisionName;
    /**
     * The name of the country associated with the customer.
     */

    private String customerCountry;

    /**
     * The Customer constructor
     * @param customerID Customer ID.
     * @param customerName Customer name.
     * @param customerAddress Customer address.
     * @param customerPostalCode Customer Postal code.
     * @param customerPhone Customer phone number.
     * @param createDate Date the customer was created.
     * @param createdBy Who created the customer.
     * @param lastUpdate When the customer was last updated.
     * @param lastUpdatedBy Who last updated the customer.
     * @param customerDivisionID The first level division id of the customer.
     * @param customerDivisionName The first level division name of the customer.
     * @param customerCountry The country of the customer.
     */

    public Customer(int customerID, String customerName, String customerAddress,
                    String customerPostalCode, String customerPhone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                    String lastUpdatedBy, int customerDivisionID, String customerDivisionName, String customerCountry) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerDivisionID = customerDivisionID;
        this.customerDivisionName = customerDivisionName;
        this.customerCountry = customerCountry;
    }

    /**
     * Gets the customer id.
     * @return The customer id.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the customer name.
     * @return The customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * The customer address.
     * @return The customer address.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * The customer postal code.
     * @return The postal code.
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * The customer phone number.
     * @return The customer phone number.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * The customer division.
     * @return The customer division.
     */
    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    /**
     * The date the customer was created.
     * @return The date the customer was created.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Gets the person who created the customer.
     * @return The person who created the customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }


    /**
     * Gets the last time the customer was updated.
     * @return The last time the customer was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }


    /**
     * Gets the last person to update the customer.
     * @return The last person to update the customer.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }


    /**
     * Gets the first level division name of the customer.
     * @return The first level division name of the customer.
     */
    public String getCustomerDivisionName() {
        return customerDivisionName;
    }

    /**
     * Gets the country associated with the customer.
     * @return The country associated with the customer.
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     * Prints the name of the customer.
     * @return The name of the customer.
     */
    @Override
    public java.lang.String toString() {
        return customerName;
    }

}
