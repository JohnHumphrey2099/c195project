package com.humphrey.model;
/**
 * Model class of the Country object.
 */
public class Country {
    /**
     * The country ID.
     */
    private int countryID;
    /**
     * The country name.
     */
    private String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Gets the ID of the country.
     * @return The ID of the country.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Gets the name of the country.
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Prints the name of the country.
     * @return The name of the country.
     */
    @Override
    public String toString(){
        return country;
    }
}
