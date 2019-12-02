/* 
 * Copyright (C) 2019 Jedidiah May
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
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class Customer {

    private int customerId, addressId;
    private String customerName, createdBy, lastUpdateBy;
    private Boolean active;
    private Date createdDate, lastUpdate;
    
    //CustomerDAO customerDAO = new CustomerDAO();

    public Customer(int customerId, String customerName, int addressId, Boolean active, String createdBy, Date createdDate, String lastUpdatedBy, Date lastUpdate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdatedBy;
    }

    //
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int id) {
        this.customerId = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Boolean getIsActive() {
        return active;
    }

    public void setIsActive(Boolean isActive) {
        this.active = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public static ObservableList<Customer> getAllCustomers(ResultSet results) {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {

            while (results.next()) {
                allCustomers.add(new Customer(
                        results.getInt("customerId"),
                        results.getString("customerName"),
                        results.getInt("addressId"),
                        results.getBoolean("active"),
                        results.getString("createdBy"),
                        results.getDate("createDate"),
                        results.getString("lastUpdateBy"),
                        results.getDate("lastUpdate")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return allCustomers;
    }

}
