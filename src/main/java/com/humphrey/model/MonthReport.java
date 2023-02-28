package com.humphrey.model;

/**
 * The model class of the month report object.
 */
public class MonthReport {
    /**
     * The name of the month
     */
    String month;
    /**
     * The number of the type of appointment in the month.
     */
    int count;
    /**
     * The type of appointments in the month.
     */
    String type;

    /**
     * The month report constructor.
     * @param month The name of the month.
     * @param type The type of appointment of the month.
     * @param count The count of the type of appointment in the month.
     */
    public MonthReport(String month, String type, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    /**
     * Gets the name of the month.
     * @return The name of the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Get the type of appointment for the month.
     * @return The type of appointment for the month.
     */

    public String getType(){return type;}

    /**
     * Get the count of the type of appointments in the month.
     * @return
     */
    public int getCount() {
        return count;
    }

}
