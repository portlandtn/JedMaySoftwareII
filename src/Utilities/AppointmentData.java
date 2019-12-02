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

import Model.Appointment;
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
public class AppointmentData {

    Calendar myCalendar = Calendar.getInstance();
    private static PreparedStatement stmt;
    private static ResultSet result;
    private static final String QUERY = "SELECT * FROM appointment;";
    private static final String DELETE = "DELETE FROM appointment WHERE appointmentId = ?;";
    private static final String INSERT = "INSERT INTO appointment ("
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
            + "lastUpdateBy) "
            + "VALUES ("
            + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE appointment"
            + "SET customerId = ?, "
            + "userId = ?, "
            + "title = ?, "
            + "description = ?, "
            + "location = ?, "
            + "contact = ?, "
            + "type = ?, "
            + "url = ?, "
            + "start = ?"
            + "end = ?, "
            + "lastUpdate = ?, "
            + "lastUpdatedBy = ? "
            + "WHERE appointmentId = ";

    public static ObservableList<Appointment> queryAppointment() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
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
                Appointment appointment = new Appointment(
                        result.getInt("appointmentId"),
                        result.getInt("customerId"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getInt("userId"), 
                        result.getString("location"),
                        result.getString("contact"),
                        result.getString("type"),
                        result.getString("url"), 
                        result.getDate("start"),
                        result.getDate("end"),
                        result.getDate("createDate"),
                        result.getString("createdBy"),
                        result.getDate("lastUpdate"),
                        result.getString("lastUpdatedBy")
                );
                appointments.add(appointment);
            }
            return appointments;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public static void deleteAppointment(int id){
        
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
    
    public static void insertAppointment(Appointment appointment){
        
        try{
            DatabaseConnector.createConnection();
            stmt = dbc.prepareStatement(INSERT);
            stmt.setString(1, String.valueOf(appointment.getCustomerId()));
            stmt.setString(2, String.valueOf(appointment.getUserId()));
            stmt.setString(3, appointment.getTitle());
            stmt.setString(4, appointment.getDescription());
            stmt.setString(5, appointment.getLocation());
            stmt.setString(6, appointment.getContact());
            stmt.setString(7, appointment.getType());
            stmt.setString(8, appointment.getUrl());
            stmt.setString(9, String.valueOf(appointment.getStart()));
            stmt.setString(10, String.valueOf(appointment.getEnd()));
            stmt.setString(11, String.valueOf(appointment.getCreateDate()));
            stmt.setString(12, appointment.getCreatedBy());
            stmt.setString(13, String.valueOf(appointment.getLastUpdate()));
            stmt.setString(14, appointment.getLastUpdateBy());
            stmt.executeUpdate();
            DatabaseConnector.closeConnection();
        }
        catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void updateUser(Appointment appointment) {

        try {
            DatabaseConnector.createConnection();
            stmt = dbc.prepareStatement(UPDATE + String.valueOf(appointment.getAppointmentId()));
            stmt.setString(1, String.valueOf(appointment.getCustomerId()));
            stmt.setString(2, appointment.getPassword());
            stmt.setString(3, String.valueOf(appointment.getActive()));
            stmt.setString(4, String.valueOf(appointment.getLastModifiedDate()));
            stmt.setString(5, String.valueOf(appointment.getLastModifiedBy()));
            stmt.setString(6, String.valueOf(appointment.getUserId()));
            stmt.executeUpdate();
            DatabaseConnector.closeConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
