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

import Model.Appointment;
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
public class AppointmentDAO extends DAO<Appointment>{
    
    Calendar calendar = Calendar.getInstance();

    public AppointmentDAO(Connection conn) {
        super(conn);
    }
    
    @Override
    public ObservableList<Appointment> query() {
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
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
                + "createdBy, "
                + "lastUpdate, "
                + "lastUpdatedBy "
                + "FROM appointment")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerId(result.getInt("customerId"));
                appointment.setUserId(result.getInt("userId"));
                appointment.setTitle(result.getString("title"));
                appointment.setDescription(result.getString("description"));
                appointment.setContact(result.getString("contact"));
                appointment.setType(result.getString("type"));
                appointment.setUrl(result.getString("url"));
                appointment.setStart(result.getDate("start"));
                appointment.setEnd(result.getDate("end"));
                appointment.setCreateDate(result.getDate("createDate"));
                appointment.setCreatedBy(result.getString("createdBy"));
                appointment.setLastUpdate(result.getDate("lastUpdate"));
                appointment.setLastUpdateBy(result.getString("lastUpdateby"));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }
    
    public Appointment querySingleAppointmenet(int id){
        Appointment appt = new Appointment();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "title, "
                + "location, "
                + "type, "
                + "url, "
                + "contact, "
                + "description, "
                + "start apptDate, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId")) {
            
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                appt.setAppointmentId(result.getInt("appointmentId"));
                appt.setCustomerName(result.getString("customerName"));
                appt.setUserName(result.getString("userName"));
                appt.setTitle(result.getString("title"));
                appt.setLocation(result.getString("location"));
                appt.setType(result.getString("type"));
                appt.setUrl(result.getString("url"));
                appt.setContact(result.getString("contact"));
                appt.setDescription(result.getString("description"));
                appt.setAppointmentDate(result.getDate("apptDate"));
                appt.setStart(result.getDate("start"));
                appt.setEnd(result.getDate("end"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appt;
    }
    
    public ObservableList<Appointment> queryForAppointmentCalendar() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerName, "
                + "userName, "
                + "title, "
                + "location, "
                + "type, "
                + "start apptDate, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setUserName(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setLocation(result.getString("type"));
                appointment.setAppointmentDate(result.getDate("apptDate"));
                appointment.setStart(result.getDate("start"));
                appointment.setEnd(result.getDate("end"));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    @Override
    public void insert(Appointment dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO appointment ("
                + "customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateby) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, dto.getCustomerId());
            stmt.setInt(2, dto.getUserId());
            stmt.setString(3, dto.getTitle());
            stmt.setString(4, dto.getDescription());
            stmt.setString(5, dto.getLocation());
            stmt.setString(6, dto.getContact());
            stmt.setString(7, dto.getType());
            stmt.setString(8, dto.getUrl());
            stmt.setDate(9, (java.sql.Date) dto.getStart());
            stmt.setDate(10, (java.sql.Date) dto.getEnd());
            stmt.setDate(11, DataProvider.getCurrentDate());
            stmt.setString(12, DataProvider.getCurrentUser());
            stmt.setDate(13, DataProvider.getCurrentDate());
            stmt.setString(14, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM appointment WHERE appointmentId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
        @Override
    public void update(Appointment dto) {
            try (PreparedStatement stmt = this.conn.prepareStatement("UPDATE appointment SET "
                    + "customerId = ?, "
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
                    + "WHERE address = '" + dto.getAppointmentId() + "'")) {
                stmt.setInt(1, dto.getCustomerId());
                stmt.setInt(2, dto.getUserId());
                stmt.setString(3, dto.getTitle());
                stmt.setString(4, dto.getDescription());
                stmt.setString(5, dto.getLocation());
                stmt.setString(6, dto.getContact());
                stmt.setString(7, dto.getType());
                stmt.setString(8, dto.getUrl());
                stmt.setDate(9, (java.sql.Date) dto.getStart());
                stmt.setDate(10, (java.sql.Date) dto.getEnd());
                stmt.setDate(11, DataProvider.getCurrentDate());
                stmt.setString(12, DataProvider.getCurrentUser());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
}
