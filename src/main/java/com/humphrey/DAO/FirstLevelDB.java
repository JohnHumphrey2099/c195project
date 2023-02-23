package com.humphrey.DAO;

import com.humphrey.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class FirstLevelDB {
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
