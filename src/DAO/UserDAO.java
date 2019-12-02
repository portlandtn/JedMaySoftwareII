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
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jedidiah May
 */
public class UserDAO{
//
//    @Override
//    public ResultSet queryTable() {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM user;");
//            return stmt.executeQuery();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//    
//    public ResultSet queryTableWithUsernameAndPassword(String username, String password) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?;");
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            return stmt.executeQuery();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public void create(User user) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO user ("
//                + "userName, "
//                + "password, "
//                + "active, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?, ?);");
//
//            stmt.setString(1, user.getUserName());
//            stmt.setString(2, user.getPassword());
//            stmt.setBoolean(3, user.getActive());
//            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(5, DataProvider.getCurrentUser());
//            stmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(7, DataProvider.getCurrentUser());
//
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void update(User user) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("UPDATE user SET "
//                + "userName = ?, "
//                + "password = ?, "
//                + "active = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE userId = " + user.getUserId());
//
//            stmt.setString(1, user.getUserName());
//            stmt.setString(2, user.getPassword());
//            stmt.setBoolean(3, user.getActive());
//            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(5, DataProvider.getCurrentUser());
//
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(User user) {
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("DELETE FROM user WHERE userId = ?");
//            stmt.setInt(1, user.getUserId());
//            
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//    
}
