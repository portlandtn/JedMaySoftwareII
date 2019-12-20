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
    
    public enum pathOfFXML {
        APPOINTMENT_DETAIL("/View/AppointmentDetail.fxml"),
        APPOINTMENT_TYPE_REPORT("/View/AppointmentTypeReport.fxml"),
        APPOINTMENTS_CALENDAR("/View/AppointmentsCalendar.fxml"),
        CREATE_EDIT_CUSTOMER("/View/CreateEditCustomer.fxml"),
        CREATE_EDIT_USER("/View/CreateEditUser.fxml"),
        DASHBOARD("/View/Dashboard.fxml"),
        LOCATION_REPORT("/View/Dashboard.fxml"),
        LOGIN_SCREEN("/View/LoginScreen.fxml"),
        MANAGE_CUSTOMERS("/View/ManageCustomers.fxml"),
        MANAGE_USERS("/View/ManageUsers.fxml"),
        REPORTS_DASHBOARD("/View/ReportsDashboard.fxml"),
        SCHEDULE_REPORT("/View/ScheduleReport.fxml");
        
        private String screen;
        
        pathOfFXML(String screen) {
            this.screen = screen;
        }
        
        public String getPath() {
            return this.screen;
        }
        
    }
    
//    private static String appointmentDetailControllerPath;
//    private static String appointmentTypeReportControllerPath;
//    private static String appointmentsCalendarControllerPath;
//    private static String createEditCustomerControllerPath;
//    private static String createEditUserControllerPath;
//    private static String dashboardControllerPath;
//    private static String locationReportControllerPath;
//    private static String loginScreenControllerPath;
//    private static String manageCustomersControllerPath;
//    private static String reportsDashboardControllerPath;
//    private static String scheduleReportControllerPath;
    
    public static final ObservableList<String> APPOINTMENT_TYPES = FXCollections.observableArrayList("Consultation", "Introduction", "Termination");
    public static final ObservableList<String> LOCATIONS = FXCollections.observableArrayList("Home", "Office");


    public static ObservableList<String> operatingHours = FXCollections.observableArrayList();

    public static void setStartingHours() {
        final int[] HOURS = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        final String[] MINUTES = new String[]{"00", "15", "30", "45"};
        
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j <= 3; j++) {
                operatingHours.add(String.valueOf(HOURS[i] + ":" + MINUTES[j]));
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

}
