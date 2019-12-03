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

import Model.User;
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
public class UserDAO extends DAO<User> {

    Calendar calendar = Calendar.getInstance();

    public UserDAO(Connection conn) {
        super(conn);

    }

    @Override
    public ObservableList<User> query() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "userId, "
                + "userName, "
                + "password, "
                + "active, "
                + "createDate, "
                + "createdBy, "
                + "lastUpdate, "
                + "lastUpdateBy "
                + "FROM user")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setUserId(result.getInt("userId"));
                user.setUserName(result.getString("userName"));
                user.setPassword(result.getString("password"));
                user.setActive(result.getBoolean("active"));
                user.setCreateDate(result.getDate("createDate"));
                user.setCreatedBy(result.getString("createdBy"));
                user.setLastUpdate(result.getDate("lastUpdate"));
                user.setLastUpdateBy(result.getString("lastUpdateby"));
                users.add(user);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }
    
    public ObservableList<User> queryActiveInactive(Boolean active) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "userId, "
                + "userName, "
                + "password, "
                + "active, "
                + "createDate, "
                + "createdBy, "
                + "lastUpdate, "
                + "lastUpdateBy "
                + "FROM user where active = " + active + "")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setUserId(result.getInt("userId"));
                user.setUserName(result.getString("userName"));
                user.setPassword(result.getString("password"));
                user.setActive(result.getBoolean("active"));
                user.setCreateDate(result.getDate("createDate"));
                user.setCreatedBy(result.getString("createdBy"));
                user.setLastUpdate(result.getDate("lastUpdate"));
                user.setLastUpdateBy(result.getString("lastUpdateby"));
                users.add(user);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    //Used to return username or password to validate login credentials.
    public String getUserName(String value) {
        String result = null;
        String query = "SELECT userName from user where userName = '" + value + "'";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("userName");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public Boolean isUserNameandPasswordValid(String userName, String password) {
        ResultSet result;
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "userName, password from user where userName = '" + userName + "'"
                + " and password = '" + password + "'")) {
            result = stmt.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public Boolean isUserActive(String userName) {
        ResultSet result;
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "active from user where userName = '" + userName + "'")) {
            result = stmt.executeQuery();
            while (result.next()) {
                return result.getBoolean("active");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public void update(User dto) {

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user SET"
                + "userName = ?, "
                + "password = ?, "
                + "active = ?, "
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "WHERE userId = '" + dto.getUserId() + "'")) {
            stmt.setString(1, dto.getUserName());
            stmt.setString(2, dto.getPassword());
            stmt.setBoolean(3, dto.getActive());
            stmt.setDate(4, (java.sql.Date) calendar.getTime());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void insert(User dto) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user "
                + "userName, password, active, createDate, createdBy, lastUpdate, lastUpdateby VALUES ?, ?, ?, ?, ?, ?, ?")) {
            stmt.setString(1, dto.getUserName());
            stmt.setString(2, dto.getPassword());
            stmt.setBoolean(3, dto.getActive());
            stmt.setDate(4, (java.sql.Date) calendar.getTime());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.setDate(6, (java.sql.Date) calendar.getTime());
            stmt.setString(7, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {

        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM user WHERE userId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
