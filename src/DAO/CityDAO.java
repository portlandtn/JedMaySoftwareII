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

import Model.City;
import Utilities.DataProvider;
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
public class CityDAO extends DAO<City> {

    Calendar calendar = Calendar.getInstance();

    public CityDAO(Connection conn) {
        super(conn);
    }

    @Override
    public ObservableList<City> query() {
        ObservableList<City> cities = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "cityId, "
                + "city, "
                + "countryId "
                + "FROM user")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                City city = new City();
                city.setCityId(result.getInt("cityId"));
                city.setCity(result.getString("city"));
                city.setCountryId(result.getInt("countryId"));
                cities.add(city);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cities;
    }

    public int getCityId(String cityName, int countryId) {

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT cityId FROM city WHERE city = '" + cityName + "' AND countryId = " + countryId )) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return result.getInt("cityId");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }

    public ObservableList<String> queryCities() {
        ObservableList<String> cities = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT DISTINCT city FROM city GROUP BY city")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                cities.add(result.getString("city"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cities;
    }

    public ObservableList<String> queryCities(String countryName) {
        ObservableList<String> cities = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT DISTINCT city FROM city "
                + "JOIN country ON city.countryId = country.countryId "
                + "GROUP BY city, country "
                + "HAVING country = '" + countryName + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                cities.add(result.getString("city"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cities;
    }

    public Boolean doesCityExist(String city, int countryId) {
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT city FROM city JOIN country ON city.countryId = country.countryId"
                + " WHERE city = '" + city + "'"
                + " AND city.countryId = " + countryId + ";")) {

            ResultSet result = stmt.executeQuery();

            return result.next();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public int getCountryIdFromCity(String city, String country) {

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT country.countryId FROM city JOIN country ON city.countryId = country.countryId"
                + "WHERE city = '" + city + "'" + " AND country.country = '" + country + "'")) {

            ResultSet result = stmt.executeQuery();
            return result.getInt("country.countryId");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    @Override
    public void insert(City dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO city ("
                + "city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, NOW(), ?, NOW(), ?)")) {
            stmt.setString(1, dto.getCity());
            stmt.setInt(2, dto.getCountryId());
            stmt.setString(3, DataProvider.getCurrentUser());
            stmt.setString(4, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM city WHERE cityId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(City dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("UPDATE city SET "
                + "city = ?, "
                + "countryid = ?, "
                + "lastUpdate = NOW(), "
                + "lastUpdateBy = ? "
                + "WHERE cityId = '" + dto.getCityId() + "'")) {
            stmt.setString(1, dto.getCity());
            stmt.setInt(2, dto.getCountryId());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
