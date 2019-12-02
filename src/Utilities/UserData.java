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
package Utilities;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static Utilities.DatabaseConnector.dbc;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class UserData {

    Calendar myCalendar = Calendar.getInstance();
    private static PreparedStatement stmt;
    private static ResultSet result;
    private static final String QUERY = "SELECT * FROM user;";
    private static final String DELETE = "DELETE FROM user WHERE userId = ?;";
    private static final String INSERT = "INSERT INTO user ("
            + "userName, "
            + "password, "
            + "active, "
            + "createDate, "
            + "createdBy, "
            + "lastUpdate, "
            + "lastUpdateBy) "
            + "VALUES ("
            + "?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE user"
            + "SET userName = ?, "
            + "password = ?, "
            + "active = ?, "
            + "lastUpdate = ?, "
            + "lastUpdatedBy = ? "
            + "WHERE userId = ";

    public static ObservableList<User> queryUser() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            stmt = dbc.prepareStatement(QUERY);
            DatabaseConnector.createConnection();
            result = stmt.executeQuery();
            DatabaseConnector.closeConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            while (result.next()) {
                User user = new User(
                        result.getInt("userId"),
                        result.getString("userName"),
                        result.getString("password"),
                        result.getBoolean("active"),
                        result.getDate("createDate"),
                        result.getString("createdBy"),
                        result.getDate("lastUpdate"),
                        result.getString("lastUpdatedBy")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public static void deleteUser(int id){
        
        try{
            DatabaseConnector.createConnection();
            stmt = dbc.prepareStatement(DELETE);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            DatabaseConnector.closeConnection();
        }
        catch(SQLException | ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static void insertUser(User user){
        
        try{
            DatabaseConnector.createConnection();
            stmt = dbc.prepareStatement(INSERT);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, String.valueOf(user.getActive()));
            stmt.setString(4, String.valueOf(user.getCreateDate()));
            stmt.setString(5, String.valueOf(user.getCreatedBy()));
            stmt.setString(6, String.valueOf(user.getLastModifiedDate()));
            stmt.setString(7, String.valueOf(user.getLastModifiedBy()));
            stmt.executeUpdate();
            DatabaseConnector.closeConnection();
        }
        catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void updateUser(User user) {

        try {
            DatabaseConnector.createConnection();
            stmt = dbc.prepareStatement(UPDATE + String.valueOf(user.getUserId()));
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, String.valueOf(user.getActive()));
            stmt.setString(4, String.valueOf(user.getLastModifiedDate()));
            stmt.setString(5, String.valueOf(user.getLastModifiedBy()));
            stmt.setString(6, String.valueOf(user.getUserId()));
            stmt.executeUpdate();
            DatabaseConnector.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
