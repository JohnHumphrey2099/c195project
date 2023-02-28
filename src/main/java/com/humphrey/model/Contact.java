package com.humphrey.model;

/**
 * Model class for the Contact object.
 */
public class Contact {
    /**
     * The Id.
     */
    private int contactID;
    /**
     * The name.
     */
    private String contactName;

    /**
     * Contact Constructor
     * @param contactID The ID.
     * @param contactName The name.
     */
    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;

    }

    /**
     * Gets the ID of the contact.
     * @return The ID of the contact.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Gets the name of the contact.
     * @return The name of the contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Prints the contact's name.
     * @return The contact's name.
     */
    @Override
    public java.lang.String toString() {
        return contactName;
    }
}
