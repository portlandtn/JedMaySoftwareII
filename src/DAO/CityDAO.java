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
import Model.City;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jedidiah May
 */
public class CityDAO{
//
//    @Override
//    public ResultSet queryTable() {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM city;");
//            return stmt.executeQuery();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//    
//    public ResultSet queryTableWithJoins() {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM city JOIN country on city.countryId = country.countryId;");
//            return stmt.executeQuery();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public void create(City city) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO city ("
//                + "city, "
//                + "countryId, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?);");
//
//            stmt.setString(1, city.getCityName());
//            stmt.setInt(2, city.getCountryId());
//            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(4, DataProvider.getCurrentUser());
//            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(6, DataProvider.getCurrentUser());
//
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void update(City city) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("UPDATE city SET "
//                + "city = ?, "
//                + "countryId = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE cityId = " + city.getCityId());
//
//            stmt.setString(1, city.getCityName());
//            stmt.setInt(2, city.getCountryId());
//            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(4, DataProvider.getCurrentUser());
//
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(City city) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("DELETE FROM city WHERE cityId = ?");
//            stmt.setInt(1, city.getCityId());
//            
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//    
}
