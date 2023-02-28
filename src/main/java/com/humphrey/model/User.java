package com.humphrey.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Model class of the User object.
 */
public class User {
    /**
     * The id of the user.
     */
    private int userID;
    /**
     * The name of the user.
     */
    private String userName;
    /**
     * The password of the user.
     */
    private String password;
    /**
     * The create-date of the user.
     */
    private LocalDateTime createDate;
    /**
     * The person who created the user.
     */
    private String createdBy;
    /**
     * The last time the user was updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * The person that last updated the user.
     */
    private String lastUpdatedBy;

    /**
     * The User object constructor.
     * @param userID The id of the user.
     * @param userName The name of the user.
     * @param password The password of the user.
     * @param createDate The create-date of the user.
     * @param createdBy The person who created the user.
     * @param lastUpdate The last time the user was updated.
     * @param lastUpdatedBy The person that last updated the user.
     */
    public User(int userID, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets id of the user.
     * @return The id of the user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets name of the user.
     * @return The name of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the create-date of the user.
     * @return The create-date of the user.
     */

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Gets the person who created the user.
     * @return the person who created the user.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the last time the user was updated.
     * @return The last time the user was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the person that last updated the user.
     * @return The person that last updated the user.
     */

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Prints the user-name;
     * @return The user-name;
     */
    @Override
    public java.lang.String toString() {
        return userName;
    }
}
