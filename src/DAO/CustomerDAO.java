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


import Model.Customer;
import Utilities.DataProvider;
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
public class CustomerDAO extends DAO<Customer>{
    
    Calendar calendar = Calendar.getInstance();

    public CustomerDAO(com.mysql.jdbc.Connection conn) {
        super(conn);
    }

    @Override
    public ObservableList<Customer> query() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "userId, "
                + "userName, "
                + "password, "
                + "active, "
                + "createDate, "
                + "createdBy, "
                + "lastUpdate, "
                + "lastUpdatedBy "
                + "FROM user")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(result.getInt("customerId"));
                customer.setCustomerName(result.getString("customerName"));
                customer.setAddressId(result.getInt("addressId"));
                customer.setActive(result.getBoolean("active"));
                customer.setCreateDate(result.getDate("createDate"));
                customer.setCreatedBy(result.getString("createdBy"));
                customer.setLastUpdate(result.getDate("lastUpdate"));
                customer.setLastUpdateBy(result.getString("lastUpdateby"));
                customers.add(customer);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }

    @Override
    public void insert(Customer dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO Customer "
                + "customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateby VALUES ?, ?, ?, ?, ?, ?, ?")) {
            stmt.setString(1, dto.getCustomerName());
            stmt.setInt(2, dto.getAddressId());
            stmt.setBoolean(3, dto.getActive());
            stmt.setDate(4, (java.sql.Date) calendar.getTime());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.setDate(6, (java.sql.Date) calendar.getTime());
            stmt.setString(7, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(int id) {
        
        try (PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM customer WHERE customerId = '" + id + "'")) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Customer dto) {

        try (PreparedStatement stmt = this.conn.prepareStatement("UPDATE customer SET"
                + "customerName = ?, "
                + "addressId = ?, "
                + "active = ?, "
                + "lastUpdate = ?, "
                + "lastUpdateBy = ? "
                + "WHERE customerId = '" + dto.getCustomerId() + "'")) {
            stmt.setString(1, dto.getCustomerName());
            stmt.setInt(2, dto.getAddressId());
            stmt.setBoolean(3, dto.getActive());
            stmt.setDate(4, (java.sql.Date) calendar.getTime());
            stmt.setString(5, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
