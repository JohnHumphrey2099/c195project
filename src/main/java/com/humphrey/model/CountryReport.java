package com.humphrey.model;

/**
 * The model class of the Country Report object.
 */
public class CountryReport {
    /**
     * The name of the country.
     */
    String countryName;
    /**
     * The number of first level divisions the country has.
     */
    int count;

    /**
     * Gets the country name.
     * @return The country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Gets the number of first level divisions the country has.
     * @return The number of first level divisions the country has.
     */
    public int getCount() {
        return count;
    }

    /**
     * Country Report Constructor
     * @param name The name of the country.
     * @param count The number of first level divisions the country has.
     */
    public CountryReport(String name, int count){
        this.countryName = name;
        this.count = count;
    }
}
