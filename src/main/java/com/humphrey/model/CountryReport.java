package com.humphrey.model;

public class CountryReport {
    String countryName;
    int count;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CountryReport(String name, int count){
        this.countryName = name;
        this.count = count;
    }
}
