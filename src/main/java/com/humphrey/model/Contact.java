package com.humphrey.model;

public class Contact {
    private int contactID;
    private String contactName;


    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;

    }

    public int getContactID() {
        return contactID;
    }


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public java.lang.String toString() {
        return contactName;
    }
}
