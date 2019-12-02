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

import DAO.AddressDAO;
import Utilities.SQL_Queries;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class Address {

    private int addressId, cityId;
    private String address, address2, postalCode, phone, createdBy, lastUpdateBy;
    private Date createDate, lastUpdate;
    
    AddressDAO addressDAO = new AddressDAO();

    //Default Constructor
    public Address(int addressId, String address, String address2, int cityId, String postalCode,
            String phone, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdate = lastUpdate;
    }
    

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int id) {
        this.addressId = id;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getCityId() {
        return cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createdOn) {
        this.createDate = createdOn;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastModifiedBy) {
        this.lastUpdateBy = lastModifiedBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastModifiedDate) {
        this.lastUpdate = lastModifiedDate;
    }

    public static ObservableList<Address> getAddressData(ResultSet results) {
        
        ObservableList<Address> allAddresses = FXCollections.observableArrayList();
        
        try {
            while (results.next()) {
                allAddresses.add(new Address(
                        results.getInt("addressId"),
                        results.getString("address"),
                        results.getString("address2"),
                        results.getInt("cityId"),
                        results.getString("postalCode"),
                        results.getString("phone"),
                        results.getDate("createDate"),
                        results.getString("createdBy"),
                        results.getDate("lastUpdate"),
                        results.getString("lastUpdateBy")
                        ));    
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return allAddresses;
    }
    
}
