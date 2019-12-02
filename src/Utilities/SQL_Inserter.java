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
public class SQL_Inserter {
//
//    public static void addUser(User user) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "INSERT INTO user ("
//                + "userName, "
//                + "password, "
//                + "active, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?, ?);";
//
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
//            stmt.setDate(4, new java.sql.Date(user.getCreateDate().getTime()));
//            stmt.setString(5, user.getCreatedBy());
//            stmt.setDate(6, new java.sql.Date(user.getLastModifiedDate().getTime()));
//            stmt.setString(7, user.getLastModifiedBy());
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
//    public static void addAppointment(Appointment appointment) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "INSERT INTO appointment ("
//                + "customerId, "
//                + "userId, "
//                + "title, "
//                + "description, "
//                + "location, "
//                + "contact, "
//                + "type, "
//                + "url, "
//                + "start, "
//                + "end, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(insertStatement);
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
//            stmt.setDate(9, new java.sql.Date(appointment.getCreatedOn().getTime()));
//            stmt.setString(10, appointment.getCreatedBy());
//            stmt.setDate(11, new java.sql.Date(appointment.getLastModifiedDate().getTime()));
//            stmt.setString(12, appointment.getLastModifiedBy());
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
//    public static void addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "INSERT INTO customer ("
//                + "customerName, "
//                + "addressId, "
//                + "active, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?, ?);";
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(insertStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, customer.getCustomerName());
//            stmt.setInt(2, customer.getAddressId());
//            stmt.setBoolean(3, customer.getIsActive());
//            stmt.setDate(4, new java.sql.Date(customer.getCreatedDate().getTime()));
//            stmt.setString(5, customer.getCreatedBy());
//            stmt.setDate(6, new java.sql.Date(customer.getLastModifiedDate().getTime()));
//            stmt.setString(7, customer.getLastModifiedBy());
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
//    public static void addAddress(Address address) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "INSERT INTO address ("
//                + "address, "
//                + "address2, "
//                + "cityId, "
//                + "postalCode, "
//                + "phone, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?, ?, ?, ?);";
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(insertStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, address.getAddress());
//            stmt.setString(2, address.getAddress2());
//            stmt.setInt(3, address.getCityId());
//            stmt.setString(4, address.getPostalCode());
//            stmt.setString(5, address.getPhone());
//            stmt.setDate(6, new java.sql.Date(address.getCreatedOn().getTime()));
//            stmt.setString(7, address.getCreatedBy());
//            stmt.setDate(8, new java.sql.Date(address.getLastModifiedDate().getTime()));
//            stmt.setString(9, address.getLastModifiedBy());
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
//    public static void addCity(City city) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "INSERT INTO city ("
//                + "city, "
//                + "countryId, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?, ?);";
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(insertStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, city.getCityName());
//            stmt.setInt(2, city.getCountryId());
//            stmt.setDate(3, new java.sql.Date(city.getCreatedOn().getTime()));
//            stmt.setString(4, city.getCreatedBy());
//            stmt.setDate(5, new java.sql.Date(city.getLastModifiedDate().getTime()));
//            stmt.setString(6, city.getLastModifiedBy());
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
//    public static void addCountry(Country country) throws SQLException, ClassNotFoundException {
//
//        String insertStatement = "INSERT INTO country ("
//                + "country, "
//                + "createDate, "
//                + "createdBy, "
//                + "lastUpdate, "
//                + "lastUpdateBy"
//                + ") Values ("
//                + "?, ?, ?, ?, ?);";
//
//        try {
//            //Open Database Connection
//            DatabaseConnector.createConnection();
//
//            //Setup the prepared statement
//            PreparedStatement stmt = databaseConnection.prepareStatement(insertStatement);
//
//            //Insert the parameters into the statement
//            stmt.setString(1, country.getCountryName());
//            stmt.setDate(2, new java.sql.Date(country.getCreatedOn().getTime()));
//            stmt.setString(3, country.getCreatedBy());
//            stmt.setDate(4, new java.sql.Date(country.getLastModifiedDate().getTime()));
//            stmt.setString(5, country.getLastModifiedBy());
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
