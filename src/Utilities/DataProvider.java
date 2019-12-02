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

/**
 *
 * @author Jedidiah May
 */

public class DataProvider {
    
    //Really wanted to use enumbs, but it just wasn't in the cards for me. So, final string arrays it is.
    public static final String[] APPOINTMENT_TYPES = new String[]{"Consultation","Introduction","Termination"};
    
    public static final String[] LOCATIONS = new String[]{"Home","Office"};


    //Hours of operation will be Monday - Friday, 7:00AM - 7:00PM (07:00 - 19:00)
    //There's a better way to do this. Going to figure it out.
    public static final int[] HOURS_OF_OPERATION = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};

    public static final int[] MINUTES_OF_OPERATION = new int[]{0, 15, 30, 45};

    //These may not be used. For now, however, I've included them. Possibly will iterate through them to create a single method that can crawl through the 
    //columns of a database and return everything, instead of separate columns. Not sure, yet.
    public static final String[] USER_COLUMNS = new String[]{"userName", "password", "active", "createDate", "createdBy", "lastUpdate", "lastUpdateBy"};

    public static final String[] APPOINTMENT_COLUMNS = new String[]{"customerId", "userId", "title", "description", "location", "contact", "type", "url", "start", "end", "createDate", "createdBy", "lastUpdate", "lastUpdateBy"};

    public static final String[] ADDRESS_COLUMNS = new String[]{"address", "address2", "cityId", "postalCode", "phone", "createDate", "createdBy", "lastUpdate", "lastUpdateBy"};

    public static final String[] CUSTOMER_COLUMNS = new String[]{"customerName", "addressId", "active", "createDate", "createdBy", "lastUpdate", "lastUpdateBy"};

    public static final String[] CITY_COLUMNS = new String[]{"city", "countryId", "createDate", "createdBy", "lastUpdate", "lastUpdateBy"};

    public static final String[] COUNTRY_COLUMNS = new String[]{"country", "createDate", "createdBy", "lastUpdate", "lastUpdateBy"};

    private static String currentUser;

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        DataProvider.currentUser = currentUser;
    }

    private static Boolean isLoggedIn = false;

    public static Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(Boolean isLoggedIn) {
        DataProvider.isLoggedIn = isLoggedIn;
    }

}
