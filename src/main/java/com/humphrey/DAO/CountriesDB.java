package com.humphrey.DAO;


import com.humphrey.model.Country;
import com.humphrey.model.CountryReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class CountriesDB {

    public static ObservableList<Country> queryCountriesDB()throws SQLException {
        ObservableList<Country> countryResults = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            Country newCountry = new Country(countryID, countryName);
            countryResults.add(newCountry);
        }
        return countryResults;
    }
    public static ObservableList<CountryReport> countryReport() throws SQLException {
        ObservableList<CountryReport> results = FXCollections.observableArrayList();
        String sql = "Select divs.Country, count(*) FROM ( Select first_level_divisions.Division, countries.Country " +
                "FROM first_level_divisions JOIN countries on countries.country_id = first_level_divisions.country_id) " +
                "AS divs group by Country;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String name = rs.getString("COUNTRY");
            int count = rs.getInt("count(*)");
            CountryReport report = new CountryReport(name, count);
            results.add(report);
        }

        return results;
    }
}
