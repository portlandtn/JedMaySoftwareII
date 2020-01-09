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
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
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
                appt.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appt.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appt;
    }

    // Query used to return data for a table view (joins multiple tables together)
    public ObservableList<Appointment> queryForAppointmentCalendar_All() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "description, "
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
                appointment.setDescription(result.getString("description"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                // Gets the result as a timestampe, converts the timestamp to localDateTime, then converts that from UTC to the system default localDateTime
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Queries the same information as above, but only returns the current month's appointments.
    public ObservableList<Appointment> queryForAppointmentCalendar_Monthly() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "description, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()))) + "' "
                + "AND start <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()))) + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setDescription(result.getString("description"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                // Gets the result as a timestampe, converts the timestampe to localDateTime, then converts that from UTC to the system default localDateTime
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Query for the appointment calendar table view, but only returns the current week's appointments.
    public ObservableList<Appointment> queryForAppointmentCalendar_Weekly() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "description, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(DayOfWeek.MONDAY))) + "' "
                + "AND start <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(DayOfWeek.FRIDAY))) + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setDescription(result.getString("description"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Query for the appointment type report for the current month.
    public ObservableList<Appointment> queryForAppointmentByType(String type) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "description, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE type = '" + type + "' AND "
                + "start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()))) + "' "
                + "AND start <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()))) + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setDescription(result.getString("description"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Query for the user report for the current month.
    public ObservableList<Appointment> queryForAppointmentByUser(String user) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "description, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE userName = '" + user + "' AND "
                + "start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()))) + "' "
                + "AND start <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()))) + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setDescription(result.getString("description"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Query for the location report for the current month.
    public ObservableList<Appointment> queryForAppointmentByLocation(String location) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "appointmentId, "
                + "customerName, "
                + "userName, "
                + "contact, "
                + "title, "
                + "location, "
                + "description, "
                + "type, "
                + "start, "
                + "end "
                + "FROM appointment JOIN customer ON "
                + "customer.customerId = appointment.customerId "
                + "JOIN user ON "
                + "user.userId = appointment.userId "
                + "WHERE location = '" + location + "' AND "
                + "start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()))) + "' "
                + "AND start <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()))) + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getInt("appointmentId"));
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setDescription(result.getString("description"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return appointments;
    }

    // Query for the location report for the current month.
    public boolean queryForAppointmentTime() {

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "start "
                + "FROM appointment "
                + "WHERE start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now())) + "' "
                + "AND start <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(
                        DateTimeConverter.convertUserLocalDateTimeToUtcLocalDateTime(
                                LocalDateTime.now().plusMinutes(15))) + "'")) {

            ResultSet result = stmt.executeQuery();
            return result.next();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    // Query for the location report for the current month.
    public boolean queryForDoAppointmentsOverlapForUser(String user, LocalDateTime startTime, LocalDateTime endTime) {

        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "start "
                + "FROM appointment JOIN user ON appointment.userId = user.userId "
                + "WHERE userName = '" + user + "' "
                + "AND (start >= '" + DateTimeConverter.getTimeStampfromLocalDateTime(startTime) + "' "
                + "AND start < '" + DateTimeConverter.getTimeStampfromLocalDateTime(endTime) + "') "
                + "OR "
                + "(end > '" + DateTimeConverter.getTimeStampfromLocalDateTime(startTime) + "' "
                + "AND end <= '" + DateTimeConverter.getTimeStampfromLocalDateTime(endTime) + "')")) {

            ResultSet result = stmt.executeQuery();
            return result.next();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    // Used to find appointments by title or customer name.
    public ObservableList<Appointment> lookupAppointment(String searchString) {

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
                + "user.userId = appointment.userId "
                + "WHERE title like '%" + searchString + "%' "
                + "OR customerName like '%" + searchString + "%'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appointment = new Appointment();
                appointment.setCustomerName(result.getString("customerName"));
                appointment.setUserName(result.getString("userName"));
                appointment.setTitle(result.getString("title"));
                appointment.setLocation(result.getString("location"));
                appointment.setType(result.getString("type"));
                appointment.setContact(result.getString("contact"));
                appointment.setStart(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("start"))));
                appointment.setEnd(DateTimeConverter.convertLocalDateTimeUTCToUserLocaDatelTime(DateTimeConverter.getLocalDateTimeFromTimestamp(result.getTimestamp("end"))));
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
            stmt.setTimestamp(9, DateTimeConverter.getTimeStampfromLocalDateTime(dto.getStart()));
            stmt.setTimestamp(10, DateTimeConverter.getTimeStampfromLocalDateTime(dto.getEnd()));
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
            stmt.setTimestamp(9, DateTimeConverter.getTimeStampfromLocalDateTime(dto.getStart()));
            stmt.setTimestamp(10, DateTimeConverter.getTimeStampfromLocalDateTime(dto.getEnd()));
            stmt.setString(11, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
