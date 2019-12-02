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
import Model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jedidiah May
 */
public class AppointmentDAO implements I_SQL_CRUD<Appointment>{

    @Override
    public ResultSet queryTable() {
        
        try {
            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM appointment;");
            return stmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ResultSet queryTableWithJoins() {

        try {
            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM appointment "
                    + "JOIN customer ON appointment.customerId = customer.customerId "
                    + "JOIN user ON appointment.userId = user.userId;");
            return stmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    @Override
    public void create(Appointment appointment) {
        try {
            PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO appointment ("
                + "customerId, "
                + "userId, "
                + "title, "
                + "description, "
                + "location, "
                + "contact, "
                + "type, "
                + "url, "
                + "start, "
                + "end, "
                + "createDate, "
                + "createdBy, "
                + "lastUpdate, "
                + "lastUpdateBy"
                + ") Values ("
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            stmt.setInt(1, appointment.getCustomerId());
            stmt.setInt(2, appointment.getUserId());
            stmt.setString(3, appointment.getDescription());
            stmt.setString(4, appointment.getLocation());
            stmt.setString(5, appointment.getType());
            stmt.setString(6, appointment.getUrl());
            stmt.setDate(7, new java.sql.Date(appointment.getStart().getTime()));
            stmt.setDate(8, new java.sql.Date(appointment.getEnd().getTime()));
            stmt.setDate(9, new java.sql.Date(System.currentTimeMillis()));
            stmt.setString(10, DataProvider.getCurrentUser());
            stmt.setDate(11, new java.sql.Date(System.currentTimeMillis()));
            stmt.setString(12, DataProvider.getCurrentUser());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Appointment appointment) {
        try {
            PreparedStatement stmt = databaseConnection.prepareStatement("UPDATE appointment SET "
                + "customerId = ?,"
                + "userId = ?, "
                + "title = ?, "
                + "description = ?, "
                + "location = ?, "
                + "contact = ?, "
                + "type = ?, "
                + "url = ?, "
                + "start = ?, "
                + "end = ?, "
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "WHERE appointmentId = " + appointment.getAppointmentId());

            stmt.setInt(1, appointment.getCustomerId());
            stmt.setInt(2, appointment.getUserId());
            stmt.setString(3, appointment.getDescription());
            stmt.setString(4, appointment.getLocation());
            stmt.setString(5, appointment.getType());
            stmt.setString(6, appointment.getUrl());
            stmt.setDate(7, new java.sql.Date(appointment.getStart().getTime()));
            stmt.setDate(8, new java.sql.Date(appointment.getEnd().getTime()));
            stmt.setDate(9, new java.sql.Date(System.currentTimeMillis()));
            stmt.setString(10, DataProvider.getCurrentUser());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Appointment appointment) {
        try {
            PreparedStatement stmt = databaseConnection.prepareStatement("DELETE FROM appointment WHERE appointmentId = ?");
            stmt.setInt(1, appointment.getAppointmentId());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
