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

import Model.Address;
import com.mysql.jdbc.Connection;
import java.util.Calendar;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class AddressDAO extends DAO<Address> {
    
    Calendar myCalendar = Calendar.getInstance();

    public AddressDAO(Connection conn) {
        super(conn);
    }
    
        @Override
    public ObservableList<Address> query() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Address dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void update(Address dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
//    @Override
//    public ResultSet queryTable() {
//
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM address;");
//            return stmt.executeQuery();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    public ResultSet queryTableWithJoins() {
//
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("SELECT * FROM address JOIN city ON address.cityId = city.cityId;");
//            return stmt.executeQuery();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public void create(Address address) {
//        
//        //will need to add a method to check if country exists. If not, add it prior to adding city and address.
//        //will need to check if city exists as well.
//
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO address ("
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
//                + "?, ?, ?, ?, ?, ?, ?, ?, ?);");
//            
//            stmt.setString(1, address.getAddress());
//            stmt.setString(2, address.getAddress2());
//            stmt.setInt(3, address.getCityId());
//            stmt.setString(4, address.getPostalCode());
//            stmt.setString(5, address.getPhone());
//            stmt.setDate(6, (Date) myCalendar.getTime());
//            stmt.setString(7, DataProvider.getCurrentUser());
//            stmt.setDate(8, (Date) myCalendar.getTime());
//            stmt.setString(9, DataProvider.getCurrentUser());
//            
//
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void update(Address address) {
//
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("UPDATE address SET "
//                    + "address = ?, "
//                    + "address2 = ?, "
//                    + "cityId = ?, "
//                    + "postalCode = ?, "
//                    + "phone = ?, "
//                    + "lastUpdate = ?, "
//                    + "lastUpdatedBy = ?"
//                    + "WHERE addressID = " + address.getAddressId());
//
//            stmt.setString(1, address.getAddress());
//            stmt.setString(2, address.getAddress2());
//            stmt.setInt(3, address.getCityId());
//            stmt.setString(4, address.getPostalCode());
//            stmt.setString(5, address.getPhone());
//            stmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
//            stmt.setString(7, DataProvider.getCurrentUser());
//
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void delete(Address address) {
//        
//        try {
//            PreparedStatement stmt = databaseConnection.prepareStatement("DELETE FROM address WHERE addressID = ?");
//            stmt.setInt(1, address.getAddressId());
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }




}
