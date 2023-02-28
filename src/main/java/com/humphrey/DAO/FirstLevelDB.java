package com.humphrey.DAO;

import com.humphrey.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DAO class that operates on the First Level Divisions table.
 */
public abstract class FirstLevelDB {
    /**
     * Returns a list of all rows in the first level divisions table.
     * @return The list of all rows in the first level divisions table.
     * @throws SQLException SQLException
     */
    public static ObservableList<FirstLevelDivision> queryFirstLevelDB() throws SQLException {
        ObservableList<FirstLevelDivision> divisionResults = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");

            FirstLevelDivision newFirstLevel = new FirstLevelDivision(divisionID, divisionName, countryID);
            divisionResults.add(newFirstLevel);
        }
        return divisionResults;
    }
}
