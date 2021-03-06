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
import Utilities.DataProvider;
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
public class AddressDAO extends DAO<Address> {

    public AddressDAO(Connection conn) {
        super(conn);
    }

    // <editor-fold desc="Queries">
    @Override
    public ObservableList<Address> query() {

        ObservableList<Address> addresses = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "addressId, "
                + "address, "
                + "address2, "
                + "cityId, "
                + "postalCode, "
                + "phone "
                + "FROM address")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Address address = new Address();
                address.setAddressId(result.getInt("addressId"));
                address.setAddress(result.getString("address"));
                address.setAddress2(result.getString("address2"));
                address.setCityId(result.getInt("cityId"));
                address.setPostalCode(result.getString("postalCode"));
                address.setPhone(result.getString("phone"));
                addresses.add(address);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return addresses;
    }
    
    // Searches the database for an address by addressId and only returns the one record
    public Address getAddress(int addressId) {
        
        Address address = new Address();
        
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "addressId, "
                + "address, "
                + "address2, "
                + "cityId, "
                + "postalCode, "
                + "phone "
                + "FROM address WHERE addressId = " + addressId)) {
            
            ResultSet result = stmt.executeQuery();
            
            while (result.next()){
                address.setAddressId(result.getInt("addressId"));
                address.setAddress(result.getString("address"));
                address.setAddress2(result.getString("address2"));
                address.setCityId(result.getInt("cityId"));
                address.setPostalCode(result.getString("postalCode"));
                address.setPhone(result.getString("phone"));  
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return address;
    }
    
    // Method used to determine the most recently saved address. This is used when creating a new customer.
    public int getMostRecentAddressEntered(){
        int addressId = 0;
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT addressId FROM address ORDER BY addressID desc limit 1;")) {
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                addressId = result.getInt("addressId");
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return addressId;
    }
    
    // </editor-fold>

    @Override
    public void insert(Address dto){
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO address ("
                + "address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateby) VALUES (?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)")) {
            stmt.setString(1, dto.getAddress());
            stmt.setString(2, dto.getAddress2());
            stmt.setInt(3, dto.getCityId());
            stmt.setString(4, dto.getPostalCode());
            stmt.setString(5, dto.getPhone());
            stmt.setString(6, DataProvider.getCurrentUser());
            stmt.setString(7, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        try (PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM address WHERE addressId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Address dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("UPDATE address SET "
                + "address = ?, "
                + "address2 = ?, "
                + "cityId = ?, "
                + "postalCode = ?, "
                + "phone = ?, "
                + "lastUpdate = NOW(), "
                + "lastUpdateBy = ? "
                + "WHERE addressId = " + dto.getAddressId())) {
            stmt.setString(1, dto.getAddress());
            stmt.setString(2, dto.getAddress2());
            stmt.setInt(3, dto.getCityId());
            stmt.setString(4, dto.getPostalCode());
            stmt.setString(5, dto.getPhone());
            stmt.setString(6, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
