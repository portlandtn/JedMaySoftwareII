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
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 */
public class DataProvider {
    
    private static String currentUser;
    private static Boolean isLoggedIn = false;
    private static String language;
    


    public static final ObservableList<String> APPOINTMENT_TYPES = FXCollections.observableArrayList("Consultation", "Introduction", "Termination");
    public static final ObservableList<String> LOCATIONS = FXCollections.observableArrayList("Home", "Office");


    public static ObservableList<String> operatingHours = FXCollections.observableArrayList();
    public final static ObservableList<String> AMPM = FXCollections.observableArrayList("AM", "PM");
    
    public static final LocalTime OPENING_TIME = LocalTime.of(07, 0);
    public static final LocalTime CLOSING_TIME = LocalTime.of(19, 0);


    public static void setStartingHours() {
        final int[] HOURS = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        final String[] MINUTES = new String[]{"00", "15", "30", "45"};
        
        for (int i = 0; i < HOURS.length; i++) {
            for (String MINUTES1 : MINUTES) {
                operatingHours.add(String.valueOf(HOURS[i] + ":" + MINUTES1));
            }
        }
    }
    
    public static java.sql.Date getCurrentDate(){
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        return sqlDate;
    }

    public static void setTimeZoneToGMT() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

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
    
    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        DataProvider.language = language;
    }

}
