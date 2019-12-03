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
package Utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Jedidiah May
 */
public class DatabaseConnector {

    private final String DATABASE_URL = "jdbc:mysql://3.227.166.251/U056lw";
    private final String DATABASE_USERNAME = "U056lw";
    private final String DATABASE_PASSWORD = "53688428663";
    private final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private final Properties properties;

    public DatabaseConnector() {
        this.properties = new Properties();
        this.properties.setProperty("user", DATABASE_USERNAME);
        this.properties.setProperty("password", DATABASE_PASSWORD);
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DATABASE_DRIVER);
        properties.setProperty(DATABASE_USERNAME, DATABASE_PASSWORD);

        return (Connection) DriverManager.getConnection(DATABASE_URL, properties);
    }
}
