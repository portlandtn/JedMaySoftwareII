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
import Utilities.DateTimeConverter;
import static Utilities.DateTimeConverter.getTimeStampfromLocalDateTime;
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
public class AppointmentDAO extends DAO<Appointment> {

    public AppointmentDAO(Connection conn) {
        super(conn);
    }

    // <editor-fold desc="Queries">
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
                + "end "
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
                // Gets the result as a timestampe, converts the timestampe to localDateTime, then converts that from UTC to the system default localDateTime
                appointment.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }
    
    // Searches database by id for a single appointment. Returns only one appointment (id's are primary keys)
    public Appointment querySingleAppointmenet(int id) {
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
                // Gets the result as a timestampe, converts the timestampe to localDateTime, then converts that from UTC to the system default localDateTime
                appt.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appt.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appt;
    }
    
    // Query used to return data for a table view (joins multiple tables together)
    public ObservableList<Appointment> queryForAppointmentCalendar() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                // Gets the result as a timestampe, converts the timestampe to localDateTime, then converts that from UTC to the system default localDateTime
                appointment.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }
    
    // Queries the same information as above, but only returns the next month's worth of appointments.
    public ObservableList<Appointment> queryForAppointmentCalendarMonthly() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE start <= NOW() + INTERVAL 30 DAY")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                // Gets the result as a timestampe, converts the timestampe to localDateTime, then converts that from UTC to the system default localDateTime
                appointment.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Query for the appointment calendar table view, but only 7 days in advance.
    public ObservableList<Appointment> queryForAppointmentCalendarWeekly() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE start <= NOW() + INTERVAL 7 DAY")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }
    
    // Used to search appointments by id (search function). Should only return one record, as id's are primary keys.
    public ObservableList<Appointment> lookupAppointment(int id) {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId"
                + "WHERE appointmentId = " + id)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Used to find appointments by title.
    public ObservableList<Appointment> lookupAppointment(String title) {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId"
                + "WHERE title like '%" + title + "%'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertFromUTCToLocalTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }
    // </editor-fold>
    
    @Override
    public void insert(Appointment dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO appointment ("
                + "customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateby) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)")) {
            stmt.setInt(1, dto.getCustomerId());
            stmt.setInt(2, dto.getUserId());
            stmt.setString(3, dto.getTitle());
            stmt.setString(4, dto.getDescription());
            stmt.setString(5, dto.getLocation());
            stmt.setString(6, dto.getContact());
            stmt.setString(7, dto.getType());
            stmt.setString(8, dto.getUrl());
            stmt.setTimestamp(9, getTimeStampfromLocalDateTime(dto.getStart()));
            stmt.setTimestamp(10, getTimeStampfromLocalDateTime(dto.getEnd()));
            stmt.setString(11, DataProvider.getCurrentUser());
            stmt.setString(12, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM appointment WHERE appointmentId = " + id)) {
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
                + "lastUpdate = NOW(), "
                + "lastUpdateBy = ? "
                + "WHERE appointmentId = " + dto.getAppointmentId())) {
            stmt.setInt(1, dto.getCustomerId());
            stmt.setInt(2, dto.getUserId());
            stmt.setString(3, dto.getTitle());
            stmt.setString(4, dto.getDescription());
            stmt.setString(5, dto.getLocation());
            stmt.setString(6, dto.getContact());
            stmt.setString(7, dto.getType());
            stmt.setString(8, dto.getUrl());
            stmt.setTimestamp(9, getTimeStampfromLocalDateTime(dto.getStart()));
            stmt.setTimestamp(10, getTimeStampfromLocalDateTime(dto.getEnd()));
            stmt.setString(11, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
