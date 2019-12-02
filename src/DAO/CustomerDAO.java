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


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class CustomerDAO{
//
//    public CustomerDAO(Connection connection) {
//        super(connection);
//    }
//
//    @Override
//    public Customer findById(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ObservableList<Customer> getAll() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Customer update(Customer dto) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Customer create(Customer dto) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void delete(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
////    @Override
////    public ResultSet queryTable() {
////        try {
////            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM customer;");
////            return stmt.executeQuery();
////        } catch (SQLException ex) {
////            System.out.println(ex.getMessage());
////        }
////        return null;
////    }
////    
////    public ResultSet queryTableWithJoins() {
////        try {
////            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM customer JOIN address on customer.addressId = address.addressId;");
////            return stmt.executeQuery();
////        } catch (SQLException ex) {
////            System.out.println(ex.getMessage());
////        }
////        return null;
////    }
////
////    @Override
////    public void create(Customer customer) {
////        try {
////            PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO customer ("
////                + "customerName, "
////                + "addressId, "
////                + "active, "
////                + "createDate, "
////                + "createdBy, "
////                + "lastUpdate, "
////                + "lastUpdateBy"
////                + ") Values ("
////                + "?, ?, ?, ?, ?, ?, ?);");
////
////            stmt.setString(1, customer.getCustomerName());
////            stmt.setInt(2, customer.getAddressId());
////            stmt.setBoolean(3, customer.getIsActive());
////            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
////            stmt.setString(5, DataProvider.getCurrentUser());
////            stmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
////            stmt.setString(7, DataProvider.getCurrentUser());
////
////            stmt.executeUpdate();
////        } catch (SQLException ex) {
////            System.out.println(ex.getMessage());
////        }
////    }
////
////    @Override
////    public void update(Customer customer) {
////        try {
////            PreparedStatement stmt = databaseConnection.prepareStatement("UPDATE customer set"
////                + "customerName = ?, "
////                + "addressId = ?, "
////                + "active = ?, "
////                + "lastUpdate = ?, "
////                + "lastUpdateBy = ? "
////                + "WHERE customerId = " + customer.getCustomerId());
////
////            stmt.setString(1, customer.getCustomerName());
////            stmt.setInt(2, customer.getAddressId());
////            stmt.setBoolean(3, customer.getIsActive());
////            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
////            stmt.setString(5, DataProvider.getCurrentUser());
////
////            stmt.executeUpdate();
////        } catch (SQLException ex) {
////            System.out.println(ex.getMessage());
////        }
////    }
////
////    @Override
////    public void delete(Customer customer) {
////        try {
////            PreparedStatement stmt = databaseConnection.prepareStatement("DELETE FROM customer WHERE customerId = ?");
////            stmt.setInt(1, customer.getCustomerId());
////            
////            stmt.executeUpdate();
////        } catch (SQLException ex) {
////            System.out.println(ex.getMessage());
////        }
////    }
}
