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

import DAO.CityDAO;
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
public class City {

    private int cityId, countryId;
    private String cityName, createdBy, lastUpdateBy;
    private Date createDate, lastUpdate;

    CityDAO cityDAO = new CityDAO();
    
    //Default Constructor
    public City(int cityId, String cityName, int countryId, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryId = countryId;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdate = lastUpdate;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int id) {
        this.cityId = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public static ObservableList<City> getAllCities(ResultSet results) {

        ObservableList<City> allCities = FXCollections.observableArrayList();

        try {

            while (results.next()) {
                allCities.add(new City(
                        results.getInt("cityId"),
                        results.getString("city"),
                        results.getInt("countryId"),
                        results.getDate("createDate"),
                        results.getString("createdBy"),
                        results.getDate("lastUpdate"),             
                        results.getString("lastUpdateBy"))); 
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return allCities;
    }

}
