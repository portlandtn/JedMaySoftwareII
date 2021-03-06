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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class CountryDAO extends DAO<Country> {

    public CountryDAO(Connection conn) {
        super(conn);
    }

    // <editor-fold desc="Queries">
    @Override
    public ObservableList<Country> query() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "countryId, "
                + "country "
                + "FROM country")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Country country = new Country();
                country.setCountryId(result.getInt("countryId"));
                country.setCountry(result.getString("country"));
                countries.add(country);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return countries;
    }

    //Returns only a list of String of countries to populate combo boxes
    public ObservableList<String> queryAllCountries() {
        ObservableList<String> countries = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT DISTINCT country from country GROUP BY country")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                countries.add(result.getString("country"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return countries;
    }

    // Checks to see if the country exists. If it does not, it'll have to be inserted into the table.
    public boolean doesCountryExist(String country) {
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT country FROM country WHERE country = '" + country + "'")) {

            ResultSet result = stmt.executeQuery();

            return result.next();

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return false;
    }

    // Simply retrieves countryId from the countryName (might be able to combine with the query above)
    public int getCountryId(String countryName) {

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT countryId FROM country WHERE country = '" + countryName + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return result.getInt("countryId");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }
    // </editor-fold>
    
    @Override
    public void insert(Country dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO country ("
                + "country, createDate, createdBy, lastUpdate, lastUpdateby) VALUES (?, NOW(), ?, NOW(), ?)")) {
            stmt.setString(1, dto.getCountry());
            stmt.setString(2, DataProvider.getCurrentUser());
            stmt.setString(3, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM country WHERE countryId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Country dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("UPDATE country SET "
                + "country = ?, "
                + "lastUpdate = NOW(), "
                + "lastUpdateBy = ? "
                + "WHERE countryId = '" + dto.getCountryId() + "'")) {
            stmt.setString(1, dto.getCountry());
            stmt.setString(2, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
