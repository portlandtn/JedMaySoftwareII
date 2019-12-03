/*
 * Copyright (C) 2019 Jedidiah May.
 *
 * This program is free software and part of an academic course of study
 * with Western Governor's University. This program is stored on my
 * personal git accounts so that I can collaborate from multiple computers
 * easily. If you find this, feel free to use it for general concept and
 * as a guidepost for your own coursework.
 *
 * This program is distributed in the hope that it will be useful,
 * but please do not copy any of the code verbatim without first
 * understanding how it works. If you're a student, I wish you the best
 * and hope this is of value to you. If you're not a student, I hope you
 * enjoy irregardless.
 *
 * Look for other projects on my github account at <https://github.com/portlandtn/>.
 */
package DAO;

import Utilities.DataProvider;
import Model.Country;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class CountryDAO extends DAO<Country> {
    
    Calendar calendar = Calendar.getInstance();

    public CountryDAO(Connection conn) {
        super(conn);
    }
    
        @Override
    public ObservableList<Country> query() {
            ObservableList<Country> countries = FXCollections.observableArrayList();
            try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                    + "userId, "
                    + "userName, "
                    + "password, "
                    + "active, "
                    + "createDate, "
                    + "createdBy, "
                    + "lastUpdate, "
                    + "lastUpdatedBy "
                    + "FROM user")) {

                ResultSet result = stmt.executeQuery();

                while (result.next()) {
                    Country country = new Country();
                    country.setCountryId(result.getInt("countryId"));
                    country.setCountry(result.getString("country"));
                    country.setCreateDate(result.getDate("createDate"));
                    country.setCreatedBy(result.getString("createdBy"));
                    country.setLastUpdate(result.getDate("lastUpdate"));
                    country.setLastUpdateBy(result.getString("lastUpdateby"));
                    countries.add(country);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return countries;
    }

    @Override
    public void insert(Country dto) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO country "
                + "country, createDate, createdBy, lastUpdate, lastUpdateby VALUES ?, ?, ?, ?, ?")) {
            stmt.setString(1, dto.getCountry());
            stmt.setDate(2, (java.sql.Date) calendar.getTime());
            stmt.setString(3, DataProvider.getCurrentUser());
            stmt.setDate(4, (java.sql.Date) calendar.getTime());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM country WHERE countryId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void update(Country dto) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE country SET"
                + "country = ?, "
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "WHERE countryId = '" + dto.getCountryId() + "'")) {
            stmt.setString(1, dto.getCountry());
            stmt.setDate(2, (java.sql.Date) calendar.getTime());
            stmt.setString(3, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
