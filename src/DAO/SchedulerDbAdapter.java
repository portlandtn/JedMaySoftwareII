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

import com.mysql.jdbc.Connection;

/**
 *
 * @author Jedidiah May
 */
public class SchedulerDbAdapter {

    private final Connection _conn;
    private final AddressDAO _addressDAO;
    private final AppointmentDAO _appointmentDAO;
    private final CityDAO _cityDAO;
    private final CountryDAO _countryDAO;
    private final CustomerDAO _customerDAO;
    protected final UserDAO _userDAO;

    //Constructor that uses a single connection 
    public SchedulerDbAdapter(Connection conn) {
        _conn = conn;
        this._addressDAO = new AddressDAO(conn);
        this._appointmentDAO = new AppointmentDAO(conn);
        this._cityDAO = new CityDAO(conn);
        this._countryDAO = new CountryDAO(conn);
        this._customerDAO = new CustomerDAO(conn);
        this._userDAO = new UserDAO(conn);
    }
    
}
