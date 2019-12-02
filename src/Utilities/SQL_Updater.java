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

import Model.*;
//import static DAO.DatabaseConnector.databaseConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
* THIS WILL ALL LIKELY GET DELETED!!!!
 */

/**
 *
 * @author Jedidiah May
 */
public class SQL_Updater {
//
//    public static void updateUser(User user) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "UPDATE user SET "
//                + "userName = ?, "
//                + "password = ?, "
//                + "active = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE userId = " + user.getUserId();
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//            
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(insertStatement);
//            
//            //Insert the parameters into the statement
//            stmt.setString(1, user.getUserName());
//            stmt.setString(2, user.getPassword());
//            stmt.setBoolean(3, user.getActive());
//            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(5, DataProvider.getCurrentUser());
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }
//
//    public static void updateAppointment(Appointment appointment) throws SQLException, ClassNotFoundException {
//
//        String updateStatement = "UPDATE appointment SET "
//                + "customerId = ?,"
//                + "userId = ?, "
//                + "title = ?, "
//                + "description = ?, "
//                + "location = ?, "
//                + "contact = ?, "
//                + "type = ?, "
//                + "url = ?, "
//                + "start = ?, "
//                + "end = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE appointmentId = " + appointment.getAppointmentId();
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(updateStatement);
//
//            //Insert the parameters into the statement
//            stmt.setInt(1, appointment.getCustomerId());
//            stmt.setInt(2, appointment.getUserId());
//            stmt.setString(3, appointment.getDescription());
//            stmt.setString(4, appointment.getLocation());
//            stmt.setString(5, appointment.getType());
//            stmt.setString(6, appointment.getUrl());
//            stmt.setDate(7, new java.sql.Date(appointment.getStart().getTime()));
//            stmt.setDate(8, new java.sql.Date(appointment.getEnd().getTime()));
//            stmt.setDate(9, new java.sql.Date(appointment.getLastModifiedDate().getTime()));
//            stmt.setString(10, appointment.getLastModifiedBy());
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }
//    
//    
//    public static void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
//
//        String updateStatement = "UPDATE customer set"
//                + "customerName = ?, "
//                + "addressId = ?, "
//                + "active = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE customerId = " + customer.getCustomerId();
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(updateStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, customer.getCustomerName());
//            stmt.setInt(2, customer.getAddressId());
//            stmt.setBoolean(3, customer.getIsActive());
//            stmt.setDate(4, new java.sql.Date(customer.getLastModifiedDate().getTime()));
//            stmt.setString(5, customer.getLastModifiedBy());
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }
//    
//    public static void updateAddress(Address address) throws SQLException, ClassNotFoundException {
//
//        String updateStatement = "UPDATE address SET "
//                + "address = ?, "
//                + "address2 = ?, "
//                + "cityId = ?, "
//                + "postalCode = ?, "
//                + "phone = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE addressId = " + address.getAddressId();
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(updateStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, address.getAddress());
//            stmt.setString(2, address.getAddress2());
//            stmt.setInt(3, address.getCityId());
//            stmt.setString(4, address.getPostalCode());
//            stmt.setString(5, address.getPhone());
//            stmt.setDate(6, new java.sql.Date(address.getLastModifiedDate().getTime()));
//            stmt.setString(7, address.getLastModifiedBy());
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }
//    
//    public static void updateCity(City city) throws SQLException, ClassNotFoundException {
//
//        String updateStatement = "UPDATE city SET "
//                + "city = ?, "
//                + "countryId = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE cityId = " + city.getCityId();
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(updateStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, city.getCityName());
//            stmt.setInt(2, city.getCountryId());
//            stmt.setDate(3, new java.sql.Date(city.getLastModifiedDate().getTime()));
//            stmt.setString(4, city.getLastModifiedBy());
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }
//    
//    public static void updateCountry(Country country) throws SQLException, ClassNotFoundException {
//
//        String updateStatement = "UPDATE country SET"
//                + "country = ?, "
//                + "lastUpdate = ?, "
//                + "lastUpdateBy = ? "
//                + "WHERE countryId = " + country.getCountryId();
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(updateStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, country.getCountryName());
//            stmt.setDate(2, new java.sql.Date(country.getLastModifiedDate().getTime()));
//            stmt.setString(3, country.getLastModifiedBy());
//
//            //Execute the query
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            //Regardless, close DB connection
//            databaseConnection.close();
//        }
//    }
}
