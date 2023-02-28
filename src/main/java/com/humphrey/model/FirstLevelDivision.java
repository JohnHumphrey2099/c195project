package com.humphrey.model;

/**
 * The model class for the first level division object
 */
public class FirstLevelDivision {
    /**
     * The division id.
     */
    private int divisionID;
    /**
     * The division name.
     */
    private String division;
    /**
     * The id of the country associated with the division.
     */
    private int countryID;

    /**
     * First Level Division constructor.
     * @param divisionID The division ID number.
     * @param division The division name.
     * @param countryID The id of the country associated with the division.
     */

    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Gets the division ID.
     * @return The division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * Gets the division name.
     * @return The division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Gets the country id associated with the division.
     * @return The country id associated with the division.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Prints the name of the division.
     * @return The name of the division.
     */

    @Override
    public String toString() {
        return this.division;
    }
}
