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
                + "countryId, "
                + "createDate, "
                + "createdBy, "
                + "lastUpdate, "
                + "lastUpdatedBy "
                + "FROM user")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                City city = new City();
                city.setCityId(result.getInt("cityId"));
                city.setCity(result.getString("city"));
                city.setCountryId(result.getInt("countryId"));
                city.setCreateDate(result.getDate("createDate"));
                city.setCreatedBy(result.getString("createdBy"));
                city.setLastUpdate(result.getDate("lastUpdate"));
                city.setLastUpdateBy(result.getString("lastUpdateby"));
                cities.add(city);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cities;
    }
    
    public int getCityId(String cityName){
        
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT cityId FROM city WHERE city = '" + cityName + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                return result.getInt("cityId");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }
    
    public ObservableList<String> queryAllCities() {
        ObservableList<String> cities = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT city FROM city")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                cities.add(result.getString("city"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cities;
    }
    
    public Boolean doesCityExist(String city) {
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT city FROM city INNER JOIN country ON city.countryId = country.countryId"
                + "WHERE city = '" + city + "'")) {

            ResultSet result = stmt.executeQuery();

            return result.next();

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return false;
    }

    @Override
    public void insert(City dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO city ("
                + "city, countryId, createDate, createdBy, lastUpdate, lastUpdateby VALUES ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, dto.getCity());
            stmt.setInt(2, dto.getCountryId());
            stmt.setDate(3, DataProvider.getCurrentDate());
            stmt.setString(4, DataProvider.getCurrentUser());
            stmt.setDate(5, DataProvider.getCurrentDate());
            stmt.setString(6, DataProvider.getCurrentUser());
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
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "WHERE cityId = '" + dto.getCityId() + "'")) {
            stmt.setString(1, dto.getCity());
            stmt.setInt(2, dto.getCountryId());
            stmt.setDate(4, DataProvider.getCurrentDate());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
