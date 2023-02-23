package com.humphrey.model;

import java.time.LocalDateTime;

public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerDivisionID;

    private String customerDivisionName;
    private String customerCountry;

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

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    public void setCustomerDivisionID(int customerDivisionID) {
        this.customerDivisionID = customerDivisionID;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getCustomerDivisionName() {
        return customerDivisionName;
    }

    public void setCustomerDivisionName(String customerDivisionName) {
        this.customerDivisionName = customerDivisionName;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    @Override
    public java.lang.String toString() {
        return customerName;
    }

}
