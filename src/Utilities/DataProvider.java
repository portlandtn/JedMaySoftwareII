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

import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class DataProvider {
    
    private static String currentUser; // Used to insert into database for auditing
    private static Boolean isLoggedIn = false; // Used to validate a user hasn't logged out (extra security)
    
    // Sets Appointment types and locations available for selection when creating apppointments
    public static final ObservableList<String> APPOINTMENT_TYPES = FXCollections.observableArrayList("Consultation", "Introduction", "Termination");
    public static final ObservableList<String> LOCATIONS = FXCollections.observableArrayList("Home", "Office");
    
    // Sets opening and llsing time for validation
    public static final LocalTime OPENING_TIME = LocalTime.of(07, 0);
    public static final LocalTime CLOSING_TIME = LocalTime.of(19, 0);

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        DataProvider.currentUser = currentUser;
    }

    public static Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(Boolean isLoggedIn) {
        DataProvider.isLoggedIn = isLoggedIn;
    }
    // </editor-fold>

}
