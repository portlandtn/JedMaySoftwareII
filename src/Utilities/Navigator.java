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
package Utilities;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Jedidiah.May
 */


public class Navigator {
    
    public enum pathOfFXML {
        APPOINTMENT_TYPE_REPORT("/View/AppointmentTypeReport.fxml"),
        APPOINTMENTS_CALENDAR("/View/AppointmentsCalendar.fxml"),
        CREATE_EDIT_APPOINTMENT("/View/CreateEditAppointment.fxml"),
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
    
    public static void displayScreen(ActionEvent event, Parent scene) throws IOException, SQLException {

        Stage stage;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
