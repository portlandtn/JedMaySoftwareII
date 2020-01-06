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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class CustomerDAO extends DAO<Customer> {

    public CustomerDAO(com.mysql.jdbc.Connection conn) {
        super(conn);
    }

    // <editor-fold desc="Queries">
    @Override
    public ObservableList<Customer> query() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerId, "
                + "customerName, "
                + "addressId, "
                + "active "
                + "FROM customer ORDER BY customer.customerId")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(result.getInt("customerId"));
                customer.setCustomerName(result.getString("customerName"));
                customer.setAddressId(result.getInt("addressId"));
                customer.setActive(result.getBoolean("active"));
                customers.add(customer);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }

    //This returns all of the customers with their complete addresses for the Manage Customers screen.
    public ObservableList<Customer> queryWithAddress() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customer.customerId, "
                + "customerName, "
                + "active, "
                + "customer.addressId, "
                + "address, "
                + "address2, "
                + "city.city, "
                + "city.cityId, "
                + "country.countryId, "
                + "postalCode, "
                + "country.country, "
                + "phone "
                + "FROM customer JOIN address ON "
                + "customer.addressId = address.addressId JOIN "
                + "city ON address.cityId = city.cityId JOIN "
                + "country ON city.countryId = country.countryId ORDER BY customer.customerId;")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(result.getInt("customer.customerId"));
                customer.setCustomerName(result.getString("customerName"));
                customer.setAddressId(result.getInt("customer.addressId"));
                customer.setCountryId(result.getInt("country.countryId"));
                customer.setCityId(result.getInt("city.cityId"));
                customer.setActive(result.getBoolean("active"));
                customer.setAddress(result.getString("address"));
                customer.setAddress2(result.getString("address2"));
                customer.setCity(result.getString("city.city"));
                customer.setPostalCode(result.getString("postalCode"));
                customer.setCountry(result.getString("country.country"));
                customer.setPhone(result.getString("phone"));
                customers.add(customer);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }

    // Checks to see if a customer exists. If it doesn't, it will have to be inserted.
    public Boolean doesCustomerExist(String customerName) {
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerName "
                + "FROM customer WHERE customerName = '" + customerName + "'")) {

            ResultSet result = stmt.executeQuery();

            return result.next();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //Returns list of string to populate combo boxes
    public ObservableList<String> queryAllCustomers() {

        ObservableList<String> customerNames = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT customerName FROM customer")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                customerNames.add(result.getString("customerName"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customerNames;
    }

    // Simply gets the customerId from the customerName in the database.
    public int getCustomerId(String customerName) {

        int id = 0;
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT customerId FROM customer WHERE customerName = '" + customerName + "'")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                id = result.getInt("customerId");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    // Used for search function. Looks up customer by customerId (should retrive one record - id = primary key).
    public ObservableList<Customer> lookupCustomer(int id) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerId, "
                + "customerName, "
                + "customer.addressId, "
                + "active, "
                + "address, "
                + "address2, "
                + "city, "
                + "postalCode, "
                + "country, "
                + "phone "
                + "FROM customer JOIN address ON "
                + "customer.addressId = address.addressID JOIN "
                + "city ON address.cityId = city.cityId JOIN "
                + "country ON city.countryId = country.countryID "
                + "WHERE customer.customerId like '" + id + "' ORDER BY customer.customerId")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(result.getInt("customer.customerId"));
                cust.setCustomerName(result.getString("customerName"));
                cust.setAddressId(result.getInt("customer.addressId"));
                cust.setActive(result.getBoolean("active"));
                cust.setAddress(result.getString("address"));
                cust.setAddress2(result.getString("address2"));
                cust.setCity(result.getString("city"));
                cust.setPostalCode(result.getString("postalCode"));
                cust.setCountry(result.getString("country"));
                cust.setPhone(result.getString("phone"));
                customers.add(cust);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }

    // Returns a list of customers that match the customerName inserted (using %like%)
    public ObservableList<Customer> lookupCustomer(String customerName) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerId, "
                + "customerName, "
                + "customer.addressId, "
                + "active, "
                + "address, "
                + "address2, "
                + "city, "
                + "postalCode, "
                + "country, "
                + "phone "
                + "FROM customer JOIN address ON "
                + "customer.addressId = address.addressID JOIN "
                + "city ON address.cityId = city.cityId JOIN "
                + "country ON city.countryId = country.countryID "
                + "WHERE customer.customerName like '" + customerName + "%' "
                + "ORDER BY customer.customerId")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(result.getInt("customer.customerId"));
                cust.setCustomerName(result.getString("customerName"));
                cust.setAddressId(result.getInt("customer.addressId"));
                cust.setActive(result.getBoolean("active"));
                cust.setAddress(result.getString("address"));
                cust.setAddress2(result.getString("address2"));
                cust.setCity(result.getString("city"));
                cust.setPostalCode(result.getString("postalCode"));
                cust.setCountry(result.getString("country"));
                cust.setPhone(result.getString("phone"));
                customers.add(cust);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }

    // Returns either active or inactive customers (filter based on radio boxes)
    public ObservableList<Customer> queryActiveInactive(Boolean active) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT "
                + "customerId, "
                + "customerName, "
                + "customer.addressId, "
                + "active, "
                + "address, "
                + "address2, "
                + "city, "
                + "postalCode, "
                + "country, "
                + "phone "
                + "FROM customer JOIN address ON "
                + "customer.addressId = address.addressId JOIN "
                + "city ON address.cityId = city.cityId JOIN "
                + "country ON city.countryId = country.countryId "
                + "WHERE customer.active = " + active + " ORDER BY customer.customerId")) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(result.getInt("customer.customerId"));
                cust.setCustomerName(result.getString("customerName"));
                cust.setAddressId(result.getInt("customer.addressId"));
                cust.setActive(result.getBoolean("active"));
                cust.setAddress(result.getString("address"));
                cust.setAddress2(result.getString("address2"));
                cust.setCity(result.getString("city"));
                cust.setPostalCode(result.getString("postalCode"));
                cust.setCountry(result.getString("country"));
                cust.setPhone(result.getString("phone"));
                customers.add(cust);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return customers;
    }
    // </editor-fold>
    
    @Override
    public void insert(Customer dto) {
        try (PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO customer ("
                + "customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateby) VALUES (?, ?, ?, NOW(), ?, NOW(), ?)")) {
            stmt.setString(1, dto.getCustomerName());
            stmt.setInt(2, dto.getAddressId());
            stmt.setBoolean(3, dto.getActive());
            stmt.setString(4, DataProvider.getCurrentUser());
            stmt.setString(5, DataProvider.getCurrentUser());
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

        try (PreparedStatement stmt = this.conn.prepareStatement("UPDATE customer SET "
                + "customerName = ?, "
                + "addressId = ?, "
                + "active = ?, "
                + "lastUpdate = NOW(), "
                + "lastUpdateBy = ? "
                + "WHERE customerId = '" + dto.getCustomerId() + "'")) {

            stmt.setString(1, dto.getCustomerName());
            stmt.setInt(2, dto.getAddressId());
            stmt.setBoolean(3, dto.getActive());
            stmt.setString(4, DataProvider.getCurrentUser());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
